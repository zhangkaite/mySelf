package com.ttmv.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.MapType;
import org.codehaus.jackson.map.type.SimpleType;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TextUtils {

	private static Logger logger = LoggerFactory.getLogger(TextUtils.class);
	private static ObjectMapper mapper = new ObjectMapper();
	private static JavaType map = MapType.construct(HashMap.class, SimpleType.construct(String.class),
			SimpleType.construct(String.class));

	private static JavaType embeddedMap = MapType.construct(HashMap.class, SimpleType.construct(String.class),
			MapType.construct(HashMap.class, SimpleType.construct(String.class), SimpleType.construct(String.class)));

	public static <T> T parseJson(String json, Class<T> targetClass) {
		try {
			if (json != null)
				return mapper.readValue(json, targetClass);
		} catch (IOException e) {
			logger.warn("json cannot be parsed: {}, cause: {}", json, e.getMessage());
		}
		return null;
	}

	public static String toJson(Object source) {
		try {
			return mapper.writeValueAsString(source);
		} catch (IOException e) {
			logger.warn("source cannot be stringified: {}, cause: {}", source, e.getMessage());
		}
		return null;
	}

	public static Map<String, String> parseJson(String json) {
		try {
			if (json != null)
				return mapper.readValue(json, map);
		} catch (IOException e) {
			logger.warn("json cannot be parsed: {}, cause: {}", json, e.getMessage());
		}
		return null;
	}

	public static Map<String, Map<String, String>> parseJsonToEmbeddedMap(String json) {
		try {
			if (json != null)
				return mapper.readValue(json, embeddedMap);
		} catch (IOException e) {
			logger.warn("json cannot be parsed: {}, cause: {}", json, e.getMessage());
		}
		return null;
	}

	public static long crc32(String string) {
		CRC32 crc32 = new CRC32();
		crc32.update(string.getBytes());
		return crc32.getValue();
	}

}
