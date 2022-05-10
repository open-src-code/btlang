package osc.btlang.core.parser;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import osc.btlang.core.FunctionExecutor;
import osc.btlang.core.exception.FunctionParseException;
import osc.btlang.core.templates.CreateInstance;

/**
 * A Loader which can load passed functions and executors associated with it. 
 * The loaded executors are cached. 
 * Functions that are not cached are always reloaded.
 *
 */
public class FunctionLoader {

	private final Map<String, FunctionExecutor> cache = new HashMap<String, FunctionExecutor>(); 
	private MessageDigest digest;

	
	public FunctionLoader() {
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new FunctionParseException("Could not load FunctionLoader",e);
		}
	}

	/**
	 * Parses the given text into a {@link FunctionExecutor} class capable of being run
	 * @param function
	 * @return
	 */
	public synchronized FunctionExecutor loadFunction(String function) {
		if (null == function) {
			throw new FunctionParseException("Passed function is null");
		}
		String spaceNormalizedFunction = StringUtils.normalizeSpace(function);
		String hash = getHash(spaceNormalizedFunction);
		FunctionExecutor executor = cache.get(hash);
		if (null != executor) {
			return executor;
		}
		executor = parseAndValidate(spaceNormalizedFunction,hash);
		cache.put(hash, executor);
		return executor;
	}
	
	private FunctionExecutor parseAndValidate(String spaceNormalizedFunction, String hash) {
		return CreateInstance.getInstance(spaceNormalizedFunction, hash);
	}
	
	/**
	 * Generates SHA-265 hash for the given string
	 * @param string
	 * @return
	 */
	private  String getHash(String string) {
		byte[] encodedhash = digest.digest(string.getBytes(StandardCharsets.UTF_8));
		return bytesToHex(encodedhash);
	}

	private String bytesToHex(byte[] hash) {
		StringBuilder hexString = new StringBuilder();
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if(hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}
}
