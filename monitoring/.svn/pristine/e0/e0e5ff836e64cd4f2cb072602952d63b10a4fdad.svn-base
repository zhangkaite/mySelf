package com.ttmv.monitoring.msgNotification.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.CharSetUtils;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.WordUtils;

@SuppressWarnings({"rawtypes","unchecked"})
public class StringUtils {

	public static final String EMPTY = "";

	public static final int INDEX_NOT_FOUND = -1;

	private static final int PAD_LIMIT = 8192;

	public StringUtils() {
		super();
	}

	// Empty checks
	// -----------------------------------------------------------------------

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean isNotEmpty(String str) {
		return !StringUtils.isEmpty(str);
	}

	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(String str) {
		return !StringUtils.isBlank(str);
	}

	// Trim
	// -----------------------------------------------------------------------

	public static String clean(String str) {
		return str == null ? EMPTY : str.trim();
	}

	public static String trim(String str) {
		return str == null ? null : str.trim();
	}

	public static String trimToNull(String str) {
		String ts = trim(str);
		return isEmpty(ts) ? null : ts;
	}

	public static String trimToEmpty(String str) {
		return str == null ? EMPTY : str.trim();
	}

	// Stripping
	// -----------------------------------------------------------------------

	public static String strip(String str) {
		return strip(str, null);
	}

	public static String stripToNull(String str) {
		if (str == null) {
			return null;
		}
		str = strip(str, null);
		return str.length() == 0 ? null : str;
	}

	public static String stripToEmpty(String str) {
		return str == null ? EMPTY : strip(str, null);
	}

	public static String strip(String str, String stripChars) {
		if (isEmpty(str)) {
			return str;
		}
		str = stripStart(str, stripChars);
		return stripEnd(str, stripChars);
	}

	public static String stripStart(String str, String stripChars) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		int start = 0;
		if (stripChars == null) {
			while ((start != strLen)
					&& Character.isWhitespace(str.charAt(start))) {
				start++;
			}
		} else if (stripChars.length() == 0) {
			return str;
		} else {
			while ((start != strLen)
					&& (stripChars.indexOf(str.charAt(start)) != -1)) {
				start++;
			}
		}
		return str.substring(start);
	}

	public static String stripEnd(String str, String stripChars) {
		int end;
		if (str == null || (end = str.length()) == 0) {
			return str;
		}

		if (stripChars == null) {
			while ((end != 0) && Character.isWhitespace(str.charAt(end - 1))) {
				end--;
			}
		} else if (stripChars.length() == 0) {
			return str;
		} else {
			while ((end != 0)
					&& (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
				end--;
			}
		}
		return str.substring(0, end);
	}

	// StripAll
	// -----------------------------------------------------------------------

	public static String[] stripAll(String[] strs) {
		return stripAll(strs, null);
	}

	public static String[] stripAll(String[] strs, String stripChars) {
		int strsLen;
		if (strs == null || (strsLen = strs.length) == 0) {
			return strs;
		}
		String[] newArr = new String[strsLen];
		for (int i = 0; i < strsLen; i++) {
			newArr[i] = strip(strs[i], stripChars);
		}
		return newArr;
	}

	// Equals
	// -----------------------------------------------------------------------

	public static boolean equals(String str1, String str2) {
		return str1 == null ? str2 == null : str1.equals(str2);
	}

	public static boolean equalsIgnoreCase(String str1, String str2) {
		return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
	}

	// IndexOf
	// -----------------------------------------------------------------------

	public static int indexOf(String str, char searchChar) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.indexOf(searchChar);
	}

	public static int indexOf(String str, char searchChar, int startPos) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.indexOf(searchChar, startPos);
	}

	public static int indexOf(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return -1;
		}
		return str.indexOf(searchStr);
	}

	public static int ordinalIndexOf(String str, String searchStr, int ordinal) {
		if (str == null || searchStr == null || ordinal <= 0) {
			return INDEX_NOT_FOUND;
		}
		if (searchStr.length() == 0) {
			return 0;
		}
		int found = 0;
		int index = INDEX_NOT_FOUND;
		do {
			index = str.indexOf(searchStr, index + 1);
			if (index < 0) {
				return index;
			}
			found++;
		} while (found < ordinal);
		return index;
	}

	public static int indexOf(String str, String searchStr, int startPos) {
		if (str == null || searchStr == null) {
			return -1;
		}
		// JDK1.2/JDK1.3 have a bug, when startPos > str.length for "", hence
		if (searchStr.length() == 0 && startPos >= str.length()) {
			return str.length();
		}
		return str.indexOf(searchStr, startPos);
	}

	// LastIndexOf
	// -----------------------------------------------------------------------

	public static int lastIndexOf(String str, char searchChar) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.lastIndexOf(searchChar);
	}

	public static int lastIndexOf(String str, char searchChar, int startPos) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.lastIndexOf(searchChar, startPos);
	}

	public static int lastIndexOf(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return -1;
		}
		return str.lastIndexOf(searchStr);
	}

	public static int lastIndexOf(String str, String searchStr, int startPos) {
		if (str == null || searchStr == null) {
			return -1;
		}
		return str.lastIndexOf(searchStr, startPos);
	}

	// Contains
	// -----------------------------------------------------------------------

	public static boolean contains(String str, char searchChar) {
		if (isEmpty(str)) {
			return false;
		}
		return str.indexOf(searchChar) >= 0;
	}

	public static boolean contains(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}
		return str.indexOf(searchStr) >= 0;
	}

	public static boolean containsIgnoreCase(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}
		return contains(str.toUpperCase(), searchStr.toUpperCase());
	}

	// IndexOfAny chars
	// -----------------------------------------------------------------------

	public static int indexOfAny(String str, char[] searchChars) {
		if (isEmpty(str) || ArrayUtils.isEmpty(searchChars)) {
			return -1;
		}
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			for (int j = 0; j < searchChars.length; j++) {
				if (searchChars[j] == ch) {
					return i;
				}
			}
		}
		return -1;
	}

	public static int indexOfAny(String str, String searchChars) {
		if (isEmpty(str) || isEmpty(searchChars)) {
			return -1;
		}
		return indexOfAny(str, searchChars.toCharArray());
	}

	// ContainsAny
	// -----------------------------------------------------------------------

	public static boolean containsAny(String str, char[] searchChars) {
		if (str == null || str.length() == 0 || searchChars == null
				|| searchChars.length == 0) {
			return false;
		}
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			for (int j = 0; j < searchChars.length; j++) {
				if (searchChars[j] == ch) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean containsAny(String str, String searchChars) {
		if (searchChars == null) {
			return false;
		}
		return containsAny(str, searchChars.toCharArray());
	}

	// IndexOfAnyBut chars
	// -----------------------------------------------------------------------

	public static int indexOfAnyBut(String str, char[] searchChars) {
		if (isEmpty(str) || ArrayUtils.isEmpty(searchChars)) {
			return -1;
		}
		outer: for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			for (int j = 0; j < searchChars.length; j++) {
				if (searchChars[j] == ch) {
					continue outer;
				}
			}
			return i;
		}
		return -1;
	}

	public static int indexOfAnyBut(String str, String searchChars) {
		if (isEmpty(str) || isEmpty(searchChars)) {
			return -1;
		}
		for (int i = 0; i < str.length(); i++) {
			if (searchChars.indexOf(str.charAt(i)) < 0) {
				return i;
			}
		}
		return -1;
	}

	// ContainsOnly
	// -----------------------------------------------------------------------

	public static boolean containsOnly(String str, char[] valid) {
		// All these pre-checks are to maintain API with an older version
		if ((valid == null) || (str == null)) {
			return false;
		}
		if (str.length() == 0) {
			return true;
		}
		if (valid.length == 0) {
			return false;
		}
		return indexOfAnyBut(str, valid) == -1;
	}

	public static boolean containsOnly(String str, String validChars) {
		if (str == null || validChars == null) {
			return false;
		}
		return containsOnly(str, validChars.toCharArray());
	}

	// ContainsNone
	// -----------------------------------------------------------------------

	public static boolean containsNone(String str, char[] invalidChars) {
		if (str == null || invalidChars == null) {
			return true;
		}
		int strSize = str.length();
		int validSize = invalidChars.length;
		for (int i = 0; i < strSize; i++) {
			char ch = str.charAt(i);
			for (int j = 0; j < validSize; j++) {
				if (invalidChars[j] == ch) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean containsNone(String str, String invalidChars) {
		if (str == null || invalidChars == null) {
			return true;
		}
		return containsNone(str, invalidChars.toCharArray());
	}

	// IndexOfAny strings
	// -----------------------------------------------------------------------

	public static int indexOfAny(String str, String[] searchStrs) {
		if ((str == null) || (searchStrs == null)) {
			return -1;
		}
		int sz = searchStrs.length;

		// String's can't have a MAX_VALUEth index.
		int ret = Integer.MAX_VALUE;

		int tmp = 0;
		for (int i = 0; i < sz; i++) {
			String search = searchStrs[i];
			if (search == null) {
				continue;
			}
			tmp = str.indexOf(search);
			if (tmp == -1) {
				continue;
			}

			if (tmp < ret) {
				ret = tmp;
			}
		}

		return (ret == Integer.MAX_VALUE) ? -1 : ret;
	}

	public static int lastIndexOfAny(String str, String[] searchStrs) {
		if ((str == null) || (searchStrs == null)) {
			return -1;
		}
		int sz = searchStrs.length;
		int ret = -1;
		int tmp = 0;
		for (int i = 0; i < sz; i++) {
			String search = searchStrs[i];
			if (search == null) {
				continue;
			}
			tmp = str.lastIndexOf(search);
			if (tmp > ret) {
				ret = tmp;
			}
		}
		return ret;
	}

	// Substring
	// -----------------------------------------------------------------------

	public static String substring(String str, int start) {
		if (str == null) {
			return null;
		}

		// handle negatives, which means last n characters
		if (start < 0) {
			start = str.length() + start; // remember start is negative
		}

		if (start < 0) {
			start = 0;
		}
		if (start > str.length()) {
			return EMPTY;
		}

		return str.substring(start);
	}

	public static String substring(String str, int start, int end) {
		if (str == null) {
			return null;
		}

		// handle negatives
		if (end < 0) {
			end = str.length() + end; // remember end is negative
		}
		if (start < 0) {
			start = str.length() + start; // remember start is negative
		}

		// check length next
		if (end > str.length()) {
			end = str.length();
		}

		// if start is greater than end, return ""
		if (start > end) {
			return EMPTY;
		}

		if (start < 0) {
			start = 0;
		}
		if (end < 0) {
			end = 0;
		}

		return str.substring(start, end);
	}

	// Left/Right/Mid
	// -----------------------------------------------------------------------

	public static String left(String str, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0) {
			return EMPTY;
		}
		if (str.length() <= len) {
			return str;
		}
		return str.substring(0, len);
	}

	public static String right(String str, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0) {
			return EMPTY;
		}
		if (str.length() <= len) {
			return str;
		}
		return str.substring(str.length() - len);
	}

	public static String mid(String str, int pos, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0 || pos > str.length()) {
			return EMPTY;
		}
		if (pos < 0) {
			pos = 0;
		}
		if (str.length() <= (pos + len)) {
			return str.substring(pos);
		}
		return str.substring(pos, pos + len);
	}

	// SubStringAfter/SubStringBefore
	// -----------------------------------------------------------------------

	public static String substringBefore(String str, String separator) {
		if (isEmpty(str) || separator == null) {
			return str;
		}
		if (separator.length() == 0) {
			return EMPTY;
		}
		int pos = str.indexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	public static String substringAfter(String str, String separator) {
		if (isEmpty(str)) {
			return str;
		}
		if (separator == null) {
			return EMPTY;
		}
		int pos = str.indexOf(separator);
		if (pos == -1) {
			return EMPTY;
		}
		return str.substring(pos + separator.length());
	}

	public static String substringBeforeLast(String str, String separator) {
		if (isEmpty(str) || isEmpty(separator)) {
			return str;
		}
		int pos = str.lastIndexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	public static String substringAfterLast(String str, String separator) {
		if (isEmpty(str)) {
			return str;
		}
		if (isEmpty(separator)) {
			return EMPTY;
		}
		int pos = str.lastIndexOf(separator);
		if (pos == -1 || pos == (str.length() - separator.length())) {
			return EMPTY;
		}
		return str.substring(pos + separator.length());
	}

	// Substring between
	// -----------------------------------------------------------------------

	public static String substringBetween(String str, String tag) {
		return substringBetween(str, tag, tag);
	}

	public static String substringBetween(String str, String open, String close) {
		if (str == null || open == null || close == null) {
			return null;
		}
		int start = str.indexOf(open);
		if (start != -1) {
			int end = str.indexOf(close, start + open.length());
			if (end != -1) {
				return str.substring(start + open.length(), end);
			}
		}
		return null;
	}

	public static String[] substringsBetween(String str, String open,
			String close) {
		if (str == null || isEmpty(open) || isEmpty(close)) {
			return null;
		}
		int strLen = str.length();
		if (strLen == 0) {
			return ArrayUtils.EMPTY_STRING_ARRAY;
		}
		int closeLen = close.length();
		int openLen = open.length();
		List list = new ArrayList();
		int pos = 0;
		while (pos < (strLen - closeLen)) {
			int start = str.indexOf(open, pos);
			if (start < 0) {
				break;
			}
			start += openLen;
			int end = str.indexOf(close, start);
			if (end < 0) {
				break;
			}
			list.add(str.substring(start, end));
			pos = end + closeLen;
		}
		if (list.isEmpty()) {
			return null;
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	// Nested extraction
	// -----------------------------------------------------------------------

	public static String getNestedString(String str, String tag) {
		return substringBetween(str, tag, tag);
	}

	public static String getNestedString(String str, String open, String close) {
		return substringBetween(str, open, close);
	}

	// Splitting
	// -----------------------------------------------------------------------

	public static String[] split(String str) {
		return split(str, null, -1);
	}

	public static String[] split(String str, char separatorChar) {
		return splitWorker(str, separatorChar, false);
	}

	public static String[] split(String str, String separatorChars) {
		return splitWorker(str, separatorChars, -1, false);
	}

	public static String[] split(String str, String separatorChars, int max) {
		return splitWorker(str, separatorChars, max, false);
	}

	public static String[] splitByWholeSeparator(String str, String separator) {
		return splitByWholeSeparatorWorker(str, separator, -1, false);
	}

	public static String[] splitByWholeSeparator(String str, String separator,
			int max) {
		return splitByWholeSeparatorWorker(str, separator, max, false);
	}

	public static String[] splitByWholeSeparatorPreserveAllTokens(String str,
			String separator) {
		return splitByWholeSeparatorWorker(str, separator, -1, true);
	}

	public static String[] splitByWholeSeparatorPreserveAllTokens(String str,
			String separator, int max) {
		return splitByWholeSeparatorWorker(str, separator, max, true);
	}

	private static String[] splitByWholeSeparatorWorker(String str,
			String separator, int max, boolean preserveAllTokens) {
		if (str == null) {
			return null;
		}

		int len = str.length();

		if (len == 0) {
			return ArrayUtils.EMPTY_STRING_ARRAY;
		}

		if ((separator == null) || (EMPTY.equals(separator))) {
			// Split on whitespace.
			return splitWorker(str, null, max, preserveAllTokens);
		}

		int separatorLength = separator.length();

		ArrayList substrings = new ArrayList();
		int numberOfSubstrings = 0;
		int beg = 0;
		int end = 0;
		while (end < len) {
			end = str.indexOf(separator, beg);

			if (end > -1) {
				if (end > beg) {
					numberOfSubstrings += 1;

					if (numberOfSubstrings == max) {
						end = len;
						substrings.add(str.substring(beg));
					} else {
						// The following is OK, because String.substring( beg,
						// end ) excludes
						// the character at the position 'end'.
						substrings.add(str.substring(beg, end));

						// Set the starting point for the next search.
						// The following is equivalent to beg = end +
						// (separatorLength - 1) + 1,
						// which is the right calculation:
						beg = end + separatorLength;
					}
				} else {
					// We found a consecutive occurrence of the separator, so
					// skip it.
					if (preserveAllTokens) {
						numberOfSubstrings += 1;
						if (numberOfSubstrings == max) {
							end = len;
							substrings.add(str.substring(beg));
						} else {
							substrings.add(EMPTY);
						}
					}
					beg = end + separatorLength;
				}
			} else {
				// String.substring( beg ) goes from 'beg' to the end of the
				// String.
				substrings.add(str.substring(beg));
				end = len;
			}
		}

		return (String[]) substrings.toArray(new String[substrings.size()]);
	}

	// -----------------------------------------------------------------------

	public static String[] splitPreserveAllTokens(String str) {
		return splitWorker(str, null, -1, true);
	}

	public static String[] splitPreserveAllTokens(String str, char separatorChar) {
		return splitWorker(str, separatorChar, true);
	}


	private static String[] splitWorker(String str, char separatorChar,
			boolean preserveAllTokens) {
		// Performance tuned for 2.0 (JDK1.4)

		if (str == null) {
			return null;
		}
		int len = str.length();
		if (len == 0) {
			return ArrayUtils.EMPTY_STRING_ARRAY;
		}
		List list = new ArrayList();
		int i = 0, start = 0;
		boolean match = false;
		boolean lastMatch = false;
		while (i < len) {
			if (str.charAt(i) == separatorChar) {
				if (match || preserveAllTokens) {
					list.add(str.substring(start, i));
					match = false;
					lastMatch = true;
				}
				start = ++i;
				continue;
			}
			lastMatch = false;
			match = true;
			i++;
		}
		if (match || (preserveAllTokens && lastMatch)) {
			list.add(str.substring(start, i));
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	public static String[] splitPreserveAllTokens(String str,
			String separatorChars) {
		return splitWorker(str, separatorChars, -1, true);
	}

	public static String[] splitPreserveAllTokens(String str,
			String separatorChars, int max) {
		return splitWorker(str, separatorChars, max, true);
	}

	private static String[] splitWorker(String str, String separatorChars,
			int max, boolean preserveAllTokens) {
		// Performance tuned for 2.0 (JDK1.4)
		// Direct code is quicker than StringTokenizer.
		// Also, StringTokenizer uses isSpace() not isWhitespace()

		if (str == null) {
			return null;
		}
		int len = str.length();
		if (len == 0) {
			return ArrayUtils.EMPTY_STRING_ARRAY;
		}
		List list = new ArrayList();
		int sizePlus1 = 1;
		int i = 0, start = 0;
		boolean match = false;
		boolean lastMatch = false;
		if (separatorChars == null) {
			// Null separator means use whitespace
			while (i < len) {
				if (Character.isWhitespace(str.charAt(i))) {
					if (match || preserveAllTokens) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				}
				lastMatch = false;
				match = true;
				i++;
			}
		} else if (separatorChars.length() == 1) {
			// Optimise 1 character case
			char sep = separatorChars.charAt(0);
			while (i < len) {
				if (str.charAt(i) == sep) {
					if (match || preserveAllTokens) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				}
				lastMatch = false;
				match = true;
				i++;
			}
		} else {
			// standard case
			while (i < len) {
				if (separatorChars.indexOf(str.charAt(i)) >= 0) {
					if (match || preserveAllTokens) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				}
				lastMatch = false;
				match = true;
				i++;
			}
		}
		if (match || (preserveAllTokens && lastMatch)) {
			list.add(str.substring(start, i));
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	public static String[] splitByCharacterType(String str) {
		return splitByCharacterType(str, false);
	}

	public static String[] splitByCharacterTypeCamelCase(String str) {
		return splitByCharacterType(str, true);
	}

	private static String[] splitByCharacterType(String str, boolean camelCase) {
		if (str == null) {
			return null;
		}
		if (str.length() == 0) {
			return ArrayUtils.EMPTY_STRING_ARRAY;
		}
		char[] c = str.toCharArray();
		List list = new ArrayList();
		int tokenStart = 0;
		int currentType = Character.getType(c[tokenStart]);
		for (int pos = tokenStart + 1; pos < c.length; pos++) {
			int type = Character.getType(c[pos]);
			if (type == currentType) {
				continue;
			}
			if (camelCase && type == Character.LOWERCASE_LETTER
					&& currentType == Character.UPPERCASE_LETTER) {
				int newTokenStart = pos - 1;
				if (newTokenStart != tokenStart) {
					list.add(new String(c, tokenStart, newTokenStart
							- tokenStart));
					tokenStart = newTokenStart;
				}
			} else {
				list.add(new String(c, tokenStart, pos - tokenStart));
				tokenStart = pos;
			}
			currentType = type;
		}
		list.add(new String(c, tokenStart, c.length - tokenStart));
		return (String[]) list.toArray(new String[list.size()]);
	}

	// Joining
	// -----------------------------------------------------------------------

	public static String concatenate(Object[] array) {
		return join(array, null);
	}

	public static String join(Object[] array) {
		return join(array, null);
	}

	public static String join(Object[] array, char separator) {
		if (array == null) {
			return null;
		}

		return join(array, separator, 0, array.length);
	}

	public static String join(Object[] array, char separator, int startIndex,
			int endIndex) {
		if (array == null) {
			return null;
		}
		int bufSize = (endIndex - startIndex);
		if (bufSize <= 0) {
			return EMPTY;
		}

		bufSize *= ((array[startIndex] == null ? 16 : array[startIndex]
				.toString().length()) + 1);
		StringBuffer buf = new StringBuffer(bufSize);

		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	public static String join(Object[] array, String separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}

	public static String join(Object[] array, String separator, int startIndex,
			int endIndex) {
		if (array == null) {
			return null;
		}
		if (separator == null) {
			separator = EMPTY;
		}

		// endIndex - startIndex > 0: Len = NofStrings *(len(firstString) +
		// len(separator))
		// (Assuming that all Strings are roughly equally long)
		int bufSize = (endIndex - startIndex);
		if (bufSize <= 0) {
			return EMPTY;
		}

		bufSize *= ((array[startIndex] == null ? 16 : array[startIndex]
				.toString().length()) + separator.length());

		StringBuffer buf = new StringBuffer(bufSize);

		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	public static String join(Iterator iterator, char separator) {

		// handle null, zero and one elements before building a buffer
		if (iterator == null) {
			return null;
		}
		if (!iterator.hasNext()) {
			return EMPTY;
		}
		Object first = iterator.next();
		if (!iterator.hasNext()) {
			return ObjectUtils.toString(first);
		}

		// two or more elements
		StringBuffer buf = new StringBuffer(256); // Java default is 16,
													// probably too small
		if (first != null) {
			buf.append(first);
		}

		while (iterator.hasNext()) {
			buf.append(separator);
			Object obj = iterator.next();
			if (obj != null) {
				buf.append(obj);
			}
		}

		return buf.toString();
	}

	public static String join(Iterator iterator, String separator) {

		// handle null, zero and one elements before building a buffer
		if (iterator == null) {
			return null;
		}
		if (!iterator.hasNext()) {
			return EMPTY;
		}
		Object first = iterator.next();
		if (!iterator.hasNext()) {
			return ObjectUtils.toString(first);
		}

		// two or more elements
		StringBuffer buf = new StringBuffer(256); // Java default is 16,
													// probably too small
		if (first != null) {
			buf.append(first);
		}

		while (iterator.hasNext()) {
			if (separator != null) {
				buf.append(separator);
			}
			Object obj = iterator.next();
			if (obj != null) {
				buf.append(obj);
			}
		}
		return buf.toString();
	}

	public static String join(Collection collection, char separator) {
		if (collection == null) {
			return null;
		}
		return join(collection.iterator(), separator);
	}

	public static String join(Collection collection, String separator) {
		if (collection == null) {
			return null;
		}
		return join(collection.iterator(), separator);
	}

	// Delete
	// -----------------------------------------------------------------------

	public static String deleteSpaces(String str) {
		if (str == null) {
			return null;
		}
		return CharSetUtils.delete(str, " \t\r\n\b");
	}

	public static String deleteWhitespace(String str) {
		if (isEmpty(str)) {
			return str;
		}
		int sz = str.length();
		char[] chs = new char[sz];
		int count = 0;
		for (int i = 0; i < sz; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				chs[count++] = str.charAt(i);
			}
		}
		if (count == sz) {
			return str;
		}
		return new String(chs, 0, count);
	}

	// Remove
	// -----------------------------------------------------------------------

	public static String removeStart(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		if (str.startsWith(remove)) {
			return str.substring(remove.length());
		}
		return str;
	}

	public static String removeStartIgnoreCase(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		if (startsWithIgnoreCase(str, remove)) {
			return str.substring(remove.length());
		}
		return str;
	}

	public static String removeEnd(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		if (str.endsWith(remove)) {
			return str.substring(0, str.length() - remove.length());
		}
		return str;
	}

	public static String removeEndIgnoreCase(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		if (endsWithIgnoreCase(str, remove)) {
			return str.substring(0, str.length() - remove.length());
		}
		return str;
	}

	public static String remove(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		return replace(str, remove, EMPTY, -1);
	}

	public static String remove(String str, char remove) {
		if (isEmpty(str) || str.indexOf(remove) == -1) {
			return str;
		}
		char[] chars = str.toCharArray();
		int pos = 0;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] != remove) {
				chars[pos++] = chars[i];
			}
		}
		return new String(chars, 0, pos);
	}

	// Replacing
	// -----------------------------------------------------------------------

	public static String replaceOnce(String text, String searchString,
			String replacement) {
		return replace(text, searchString, replacement, 1);
	}

	public static String replace(String text, String searchString,
			String replacement) {
		return replace(text, searchString, replacement, -1);
	}

	public static String replace(String text, String searchString,
			String replacement, int max) {
		if (isEmpty(text) || isEmpty(searchString) || replacement == null
				|| max == 0) {
			return text;
		}
		int start = 0;
		int end = text.indexOf(searchString, start);
		if (end == -1) {
			return text;
		}
		int replLength = searchString.length();
		int increase = replacement.length() - replLength;
		increase = (increase < 0 ? 0 : increase);
		increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
		StringBuffer buf = new StringBuffer(text.length() + increase);
		while (end != -1) {
			buf.append(text.substring(start, end)).append(replacement);
			start = end + replLength;
			if (--max == 0) {
				break;
			}
			end = text.indexOf(searchString, start);
		}
		buf.append(text.substring(start));
		return buf.toString();
	}

	public static String replaceEach(String text, String[] searchList,
			String[] replacementList) {
		return replaceEach(text, searchList, replacementList, false, 0);
	}

	public static String replaceEachRepeatedly(String text,
			String[] searchList, String[] replacementList) {
		// timeToLive should be 0 if not used or nothing to replace, else it's
		// the length of the replace array
		int timeToLive = searchList == null ? 0 : searchList.length;
		return replaceEach(text, searchList, replacementList, true, timeToLive);
	}

	private static String replaceEach(String text, String[] searchList,
			String[] replacementList, boolean repeat, int timeToLive) {

		// mchyzer Performance note: This creates very few new objects (one
		// major goal)
		// let me know if there are performance requests, we can create a
		// harness to measure

		if (text == null || text.length() == 0 || searchList == null
				|| searchList.length == 0 || replacementList == null
				|| replacementList.length == 0) {
			return text;
		}

		// if recursing, this shouldnt be less than 0
		if (timeToLive < 0) {
			throw new IllegalStateException("TimeToLive of " + timeToLive
					+ " is less than 0: " + text);
		}

		int searchLength = searchList.length;
		int replacementLength = replacementList.length;

		// make sure lengths are ok, these need to be equal
		if (searchLength != replacementLength) {
			throw new IllegalArgumentException(
					"Search and Replace array lengths don't match: "
							+ searchLength + " vs " + replacementLength);
		}

		// keep track of which still have matches
		boolean[] noMoreMatchesForReplIndex = new boolean[searchLength];

		// index on index that the match was found
		int textIndex = -1;
		int replaceIndex = -1;
		int tempIndex = -1;

		// index of replace array that will replace the search string found
		// NOTE: logic duplicated below START
		for (int i = 0; i < searchLength; i++) {
			if (noMoreMatchesForReplIndex[i] || searchList[i] == null
					|| searchList[i].length() == 0
					|| replacementList[i] == null) {
				continue;
			}
			tempIndex = text.indexOf(searchList[i]);

			// see if we need to keep searching for this
			if (tempIndex == -1) {
				noMoreMatchesForReplIndex[i] = true;
			} else {
				if (textIndex == -1 || tempIndex < textIndex) {
					textIndex = tempIndex;
					replaceIndex = i;
				}
			}
		}
		// NOTE: logic mostly below END

		// no search strings found, we are done
		if (textIndex == -1) {
			return text;
		}

		int start = 0;

		// get a good guess on the size of the result buffer so it doesnt have
		// to double if it goes over a bit
		int increase = 0;

		// count the replacement text elements that are larger than their
		// corresponding text being replaced
		for (int i = 0; i < searchList.length; i++) {
			int greater = replacementList[i].length() - searchList[i].length();
			if (greater > 0) {
				increase += 3 * greater; // assume 3 matches
			}
		}
		// have upper-bound at 20% increase, then let Java take over
		increase = Math.min(increase, text.length() / 5);

		StringBuffer buf = new StringBuffer(text.length() + increase);

		while (textIndex != -1) {

			for (int i = start; i < textIndex; i++) {
				buf.append(text.charAt(i));
			}
			buf.append(replacementList[replaceIndex]);

			start = textIndex + searchList[replaceIndex].length();

			textIndex = -1;
			replaceIndex = -1;
			tempIndex = -1;
			// find the next earliest match
			// NOTE: logic mostly duplicated above START
			for (int i = 0; i < searchLength; i++) {
				if (noMoreMatchesForReplIndex[i] || searchList[i] == null
						|| searchList[i].length() == 0
						|| replacementList[i] == null) {
					continue;
				}
				tempIndex = text.indexOf(searchList[i], start);

				// see if we need to keep searching for this
				if (tempIndex == -1) {
					noMoreMatchesForReplIndex[i] = true;
				} else {
					if (textIndex == -1 || tempIndex < textIndex) {
						textIndex = tempIndex;
						replaceIndex = i;
					}
				}
			}
			// NOTE: logic duplicated above END

		}
		int textLength = text.length();
		for (int i = start; i < textLength; i++) {
			buf.append(text.charAt(i));
		}
		String result = buf.toString();
		if (!repeat) {
			return result;
		}

		return replaceEach(result, searchList, replacementList, repeat,
				timeToLive - 1);
	}

	// Replace, character based
	// -----------------------------------------------------------------------

	public static String replaceChars(String str, char searchChar,
			char replaceChar) {
		if (str == null) {
			return null;
		}
		return str.replace(searchChar, replaceChar);
	}

	public static String replaceChars(String str, String searchChars,
			String replaceChars) {
		if (isEmpty(str) || isEmpty(searchChars)) {
			return str;
		}
		if (replaceChars == null) {
			replaceChars = EMPTY;
		}
		boolean modified = false;
		int replaceCharsLength = replaceChars.length();
		int strLength = str.length();
		StringBuffer buf = new StringBuffer(strLength);
		for (int i = 0; i < strLength; i++) {
			char ch = str.charAt(i);
			int index = searchChars.indexOf(ch);
			if (index >= 0) {
				modified = true;
				if (index < replaceCharsLength) {
					buf.append(replaceChars.charAt(index));
				}
			} else {
				buf.append(ch);
			}
		}
		if (modified) {
			return buf.toString();
		}
		return str;
	}

	// Overlay
	// -----------------------------------------------------------------------

	public static String overlayString(String text, String overlay, int start,
			int end) {
		return new StringBuffer(start + overlay.length() + text.length() - end
				+ 1).append(text.substring(0, start)).append(overlay)
				.append(text.substring(end)).toString();
	}

	public static String overlay(String str, String overlay, int start, int end) {
		if (str == null) {
			return null;
		}
		if (overlay == null) {
			overlay = EMPTY;
		}
		int len = str.length();
		if (start < 0) {
			start = 0;
		}
		if (start > len) {
			start = len;
		}
		if (end < 0) {
			end = 0;
		}
		if (end > len) {
			end = len;
		}
		if (start > end) {
			int temp = start;
			start = end;
			end = temp;
		}
		return new StringBuffer(len + start - end + overlay.length() + 1)
				.append(str.substring(0, start)).append(overlay)
				.append(str.substring(end)).toString();
	}

	// Chomping
	// -----------------------------------------------------------------------

	public static String chomp(String str) {
		if (isEmpty(str)) {
			return str;
		}

		if (str.length() == 1) {
			char ch = str.charAt(0);
			if (ch == CharUtils.CR || ch == CharUtils.LF) {
				return EMPTY;
			}
			return str;
		}

		int lastIdx = str.length() - 1;
		char last = str.charAt(lastIdx);

		if (last == CharUtils.LF) {
			if (str.charAt(lastIdx - 1) == CharUtils.CR) {
				lastIdx--;
			}
		} else if (last != CharUtils.CR) {
			lastIdx++;
		}
		return str.substring(0, lastIdx);
	}

	public static String chomp(String str, String separator) {
		if (isEmpty(str) || separator == null) {
			return str;
		}
		if (str.endsWith(separator)) {
			return str.substring(0, str.length() - separator.length());
		}
		return str;
	}

	public static String chompLast(String str) {
		return chompLast(str, "\n");
	}

	public static String chompLast(String str, String sep) {
		if (str.length() == 0) {
			return str;
		}
		String sub = str.substring(str.length() - sep.length());
		if (sep.equals(sub)) {
			return str.substring(0, str.length() - sep.length());
		}
		return str;
	}

	public static String getChomp(String str, String sep) {
		int idx = str.lastIndexOf(sep);
		if (idx == str.length() - sep.length()) {
			return sep;
		} else if (idx != -1) {
			return str.substring(idx);
		} else {
			return EMPTY;
		}
	}

	public static String prechomp(String str, String sep) {
		int idx = str.indexOf(sep);
		if (idx == -1) {
			return str;
		}
		return str.substring(idx + sep.length());
	}

	public static String getPrechomp(String str, String sep) {
		int idx = str.indexOf(sep);
		if (idx == -1) {
			return EMPTY;
		}
		return str.substring(0, idx + sep.length());
	}

	// Chopping
	// -----------------------------------------------------------------------

	public static String chop(String str) {
		if (str == null) {
			return null;
		}
		int strLen = str.length();
		if (strLen < 2) {
			return EMPTY;
		}
		int lastIdx = strLen - 1;
		String ret = str.substring(0, lastIdx);
		char last = str.charAt(lastIdx);
		if (last == CharUtils.LF) {
			if (ret.charAt(lastIdx - 1) == CharUtils.CR) {
				return ret.substring(0, lastIdx - 1);
			}
		}
		return ret;
	}

	public static String chopNewline(String str) {
		int lastIdx = str.length() - 1;
		if (lastIdx <= 0) {
			return EMPTY;
		}
		char last = str.charAt(lastIdx);
		if (last == CharUtils.LF) {
			if (str.charAt(lastIdx - 1) == CharUtils.CR) {
				lastIdx--;
			}
		} else {
			lastIdx++;
		}
		return str.substring(0, lastIdx);
	}

	// Conversion
	// -----------------------------------------------------------------------

	public static String escape(String str) {
		return StringEscapeUtils.escapeJava(str);
	}

	// Padding
	// -----------------------------------------------------------------------

	public static String repeat(String str, int repeat) {
		// Performance tuned for 2.0 (JDK1.4)

		if (str == null) {
			return null;
		}
		if (repeat <= 0) {
			return EMPTY;
		}
		int inputLength = str.length();
		if (repeat == 1 || inputLength == 0) {
			return str;
		}
		if (inputLength == 1 && repeat <= PAD_LIMIT) {
			return padding(repeat, str.charAt(0));
		}

		int outputLength = inputLength * repeat;
		switch (inputLength) {
		case 1:
			char ch = str.charAt(0);
			char[] output1 = new char[outputLength];
			for (int i = repeat - 1; i >= 0; i--) {
				output1[i] = ch;
			}
			return new String(output1);
		case 2:
			char ch0 = str.charAt(0);
			char ch1 = str.charAt(1);
			char[] output2 = new char[outputLength];
			for (int i = repeat * 2 - 2; i >= 0; i--, i--) {
				output2[i] = ch0;
				output2[i + 1] = ch1;
			}
			return new String(output2);
		default:
			StringBuffer buf = new StringBuffer(outputLength);
			for (int i = 0; i < repeat; i++) {
				buf.append(str);
			}
			return buf.toString();
		}
	}

	private static String padding(int repeat, char padChar)
			throws IndexOutOfBoundsException {
		if (repeat < 0) {
			throw new IndexOutOfBoundsException(
					"Cannot pad a negative amount: " + repeat);
		}
		final char[] buf = new char[repeat];
		for (int i = 0; i < buf.length; i++) {
			buf[i] = padChar;
		}
		return new String(buf);
	}

	public static String rightPad(String str, int size) {
		return rightPad(str, size, ' ');
	}

	public static String rightPad(String str, int size, char padChar) {
		if (str == null) {
			return null;
		}
		int pads = size - str.length();
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (pads > PAD_LIMIT) {
			return rightPad(str, size, String.valueOf(padChar));
		}
		return str.concat(padding(pads, padChar));
	}

	public static String rightPad(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}
		if (isEmpty(padStr)) {
			padStr = " ";
		}
		int padLen = padStr.length();
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return rightPad(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return str.concat(padStr);
		} else if (pads < padLen) {
			return str.concat(padStr.substring(0, pads));
		} else {
			char[] padding = new char[pads];
			char[] padChars = padStr.toCharArray();
			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}
			return str.concat(new String(padding));
		}
	}

	public static String leftPad(String str, int size) {
		return leftPad(str, size, ' ');
	}

	public static String leftPad(String str, int size, char padChar) {
		if (str == null) {
			return null;
		}
		int pads = size - str.length();
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (pads > PAD_LIMIT) {
			return leftPad(str, size, String.valueOf(padChar));
		}
		return padding(pads, padChar).concat(str);
	}

	public static String leftPad(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}
		if (isEmpty(padStr)) {
			padStr = " ";
		}
		int padLen = padStr.length();
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return leftPad(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return padStr.concat(str);
		} else if (pads < padLen) {
			return padStr.substring(0, pads).concat(str);
		} else {
			char[] padding = new char[pads];
			char[] padChars = padStr.toCharArray();
			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}
			return new String(padding).concat(str);
		}
	}

	public static int length(String str) {
		return str == null ? 0 : str.length();
	}

	// Centering
	// -----------------------------------------------------------------------

	public static String center(String str, int size) {
		return center(str, size, ' ');
	}

	public static String center(String str, int size, char padChar) {
		if (str == null || size <= 0) {
			return str;
		}
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str;
		}
		str = leftPad(str, strLen + pads / 2, padChar);
		str = rightPad(str, size, padChar);
		return str;
	}

	public static String center(String str, int size, String padStr) {
		if (str == null || size <= 0) {
			return str;
		}
		if (isEmpty(padStr)) {
			padStr = " ";
		}
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str;
		}
		str = leftPad(str, strLen + pads / 2, padStr);
		str = rightPad(str, size, padStr);
		return str;
	}

	// Case conversion
	// -----------------------------------------------------------------------

	public static String upperCase(String str) {
		if (str == null) {
			return null;
		}
		return str.toUpperCase();
	}

	public static String lowerCase(String str) {
		if (str == null) {
			return null;
		}
		return str.toLowerCase();
	}

	public static String capitalize(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		return new StringBuffer(strLen)
				.append(Character.toTitleCase(str.charAt(0)))
				.append(str.substring(1)).toString();
	}

	public static String capitalise(String str) {
		return capitalize(str);
	}

	public static String uncapitalize(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		return new StringBuffer(strLen)
				.append(Character.toLowerCase(str.charAt(0)))
				.append(str.substring(1)).toString();
	}

	public static String uncapitalise(String str) {
		return uncapitalize(str);
	}

	public static String swapCase(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		StringBuffer buffer = new StringBuffer(strLen);

		char ch = 0;
		for (int i = 0; i < strLen; i++) {
			ch = str.charAt(i);
			if (Character.isUpperCase(ch)) {
				ch = Character.toLowerCase(ch);
			} else if (Character.isTitleCase(ch)) {
				ch = Character.toLowerCase(ch);
			} else if (Character.isLowerCase(ch)) {
				ch = Character.toUpperCase(ch);
			}
			buffer.append(ch);
		}
		return buffer.toString();
	}

	public static String capitaliseAllWords(String str) {
		return WordUtils.capitalize(str);
	}

	// Count matches
	// -----------------------------------------------------------------------

	public static int countMatches(String str, String sub) {
		if (isEmpty(str) || isEmpty(sub)) {
			return 0;
		}
		int count = 0;
		int idx = 0;
		while ((idx = str.indexOf(sub, idx)) != -1) {
			count++;
			idx += sub.length();
		}
		return count;
	}

	// Character Tests
	// -----------------------------------------------------------------------

	public static boolean isAlpha(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isLetter(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	public static boolean isAlphaSpace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((Character.isLetter(str.charAt(i)) == false)
					&& (str.charAt(i) != ' ')) {
				return false;
			}
		}
		return true;
	}

	public static boolean isAlphanumeric(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isLetterOrDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	public static boolean isAlphanumericSpace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((Character.isLetterOrDigit(str.charAt(i)) == false)
					&& (str.charAt(i) != ' ')) {
				return false;
			}
		}
		return true;
	}

	public static boolean isAsciiPrintable(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (CharUtils.isAsciiPrintable(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNumericSpace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((Character.isDigit(str.charAt(i)) == false)
					&& (str.charAt(i) != ' ')) {
				return false;
			}
		}
		return true;
	}

	public static boolean isWhitespace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	// Defaults
	// -----------------------------------------------------------------------

	public static String defaultString(String str) {
		return str == null ? EMPTY : str;
	}

	public static String defaultString(String str, String defaultStr) {
		return str == null ? defaultStr : str;
	}

	public static String defaultIfEmpty(String str, String defaultStr) {
		return StringUtils.isEmpty(str) ? defaultStr : str;
	}

	// Reversing
	// -----------------------------------------------------------------------

	public static String reverse(String str) {
		if (str == null) {
			return null;
		}
		return new StringBuffer(str).reverse().toString();
	}

	public static String reverseDelimited(String str, char separatorChar) {
		if (str == null) {
			return null;
		}
		// could implement manually, but simple way is to reuse other,
		// probably slower, methods.
		String[] strs = split(str, separatorChar);
		ArrayUtils.reverse(strs);
		return join(strs, separatorChar);
	}

	public static String reverseDelimitedString(String str,
			String separatorChars) {
		if (str == null) {
			return null;
		}
		// could implement manually, but simple way is to reuse other,
		// probably slower, methods.
		String[] strs = split(str, separatorChars);
		ArrayUtils.reverse(strs);
		if (separatorChars == null) {
			return join(strs, ' ');
		}
		return join(strs, separatorChars);
	}

	// Abbreviating
	// -----------------------------------------------------------------------

	public static String abbreviate(String str, int maxWidth) {
		return abbreviate(str, 0, maxWidth);
	}

	public static String abbreviate(String str, int offset, int maxWidth) {
		if (str == null) {
			return null;
		}
		if (maxWidth < 4) {
			throw new IllegalArgumentException(
					"Minimum abbreviation width is 4");
		}
		if (str.length() <= maxWidth) {
			return str;
		}
		if (offset > str.length()) {
			offset = str.length();
		}
		if ((str.length() - offset) < (maxWidth - 3)) {
			offset = str.length() - (maxWidth - 3);
		}
		if (offset <= 4) {
			return str.substring(0, maxWidth - 3) + "...";
		}
		if (maxWidth < 7) {
			throw new IllegalArgumentException(
					"Minimum abbreviation width with offset is 7");
		}
		if ((offset + (maxWidth - 3)) < str.length()) {
			return "..." + abbreviate(str.substring(offset), maxWidth - 3);
		}
		return "..." + str.substring(str.length() - (maxWidth - 3));
	}

	// Difference
	// -----------------------------------------------------------------------

	public static String difference(String str1, String str2) {
		if (str1 == null) {
			return str2;
		}
		if (str2 == null) {
			return str1;
		}
		int at = indexOfDifference(str1, str2);
		if (at == -1) {
			return EMPTY;
		}
		return str2.substring(at);
	}

	public static int indexOfDifference(String str1, String str2) {
		if (str1 == str2) {
			return -1;
		}
		if (str1 == null || str2 == null) {
			return 0;
		}
		int i;
		for (i = 0; i < str1.length() && i < str2.length(); ++i) {
			if (str1.charAt(i) != str2.charAt(i)) {
				break;
			}
		}
		if (i < str2.length() || i < str1.length()) {
			return i;
		}
		return -1;
	}

	public static int indexOfDifference(String[] strs) {
		if (strs == null || strs.length <= 1) {
			return -1;
		}
		boolean anyStringNull = false;
		boolean allStringsNull = true;
		int arrayLen = strs.length;
		int shortestStrLen = Integer.MAX_VALUE;
		int longestStrLen = 0;

		// find the min and max string lengths; this avoids checking to make
		// sure we are not exceeding the length of the string each time through
		// the bottom loop.
		for (int i = 0; i < arrayLen; i++) {
			if (strs[i] == null) {
				anyStringNull = true;
				shortestStrLen = 0;
			} else {
				allStringsNull = false;
				shortestStrLen = Math.min(strs[i].length(), shortestStrLen);
				longestStrLen = Math.max(strs[i].length(), longestStrLen);
			}
		}

		// handle lists containing all nulls or all empty strings
		if (allStringsNull || (longestStrLen == 0 && !anyStringNull)) {
			return -1;
		}

		// handle lists containing some nulls or some empty strings
		if (shortestStrLen == 0) {
			return 0;
		}

		// find the position with the first difference across all strings
		int firstDiff = -1;
		for (int stringPos = 0; stringPos < shortestStrLen; stringPos++) {
			char comparisonChar = strs[0].charAt(stringPos);
			for (int arrayPos = 1; arrayPos < arrayLen; arrayPos++) {
				if (strs[arrayPos].charAt(stringPos) != comparisonChar) {
					firstDiff = stringPos;
					break;
				}
			}
			if (firstDiff != -1) {
				break;
			}
		}

		if (firstDiff == -1 && shortestStrLen != longestStrLen) {
			// we compared all of the characters up to the length of the
			// shortest string and didn't find a match, but the string lengths
			// vary, so return the length of the shortest string.
			return shortestStrLen;
		}
		return firstDiff;
	}

	public static String getCommonPrefix(String[] strs) {
		if (strs == null || strs.length == 0) {
			return EMPTY;
		}
		int smallestIndexOfDiff = indexOfDifference(strs);
		if (smallestIndexOfDiff == -1) {
			// all strings were identical
			if (strs[0] == null) {
				return EMPTY;
			}
			return strs[0];
		} else if (smallestIndexOfDiff == 0) {
			// there were no common initial characters
			return EMPTY;
		} else {
			// we found a common initial character sequence
			return strs[0].substring(0, smallestIndexOfDiff);
		}
	}

	// Misc
	// -----------------------------------------------------------------------

	public static int getLevenshteinDistance(String s, String t) {
		if (s == null || t == null) {
			throw new IllegalArgumentException("Strings must not be null");
		}

		int n = s.length(); // length of s
		int m = t.length(); // length of t

		if (n == 0) {
			return m;
		} else if (m == 0) {
			return n;
		}

		if (n > m) {
			// swap the input strings to consume less memory
			String tmp = s;
			s = t;
			t = tmp;
			n = m;
			m = t.length();
		}

		int p[] = new int[n + 1]; // 'previous' cost array, horizontally
		int d[] = new int[n + 1]; // cost array, horizontally
		int _d[]; // placeholder to assist in swapping p and d

		// indexes into strings s and t
		int i; // iterates through s
		int j; // iterates through t

		char t_j; // jth character of t

		int cost; // cost

		for (i = 0; i <= n; i++) {
			p[i] = i;
		}

		for (j = 1; j <= m; j++) {
			t_j = t.charAt(j - 1);
			d[0] = j;

			for (i = 1; i <= n; i++) {
				cost = s.charAt(i - 1) == t_j ? 0 : 1;
				// minimum of cell to the left+1, to the top+1, diagonally left
				// and up +cost
				d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1]
						+ cost);
			}

			// copy current distance counts to 'previous row' distance counts
			_d = p;
			p = d;
			d = _d;
		}

		// our last action in the above loop was to switch d and p, so p now
		// actually has the most recent cost counts
		return p[n];
	}

	// startsWith
	// -----------------------------------------------------------------------

	public static boolean startsWith(String str, String prefix) {
		return startsWith(str, prefix, false);
	}

	public static boolean startsWithIgnoreCase(String str, String prefix) {
		return startsWith(str, prefix, true);
	}

	private static boolean startsWith(String str, String prefix,
			boolean ignoreCase) {
		if (str == null || prefix == null) {
			return (str == null && prefix == null);
		}
		if (prefix.length() > str.length()) {
			return false;
		}
		return str.regionMatches(ignoreCase, 0, prefix, 0, prefix.length());
	}

	// endsWith
	// -----------------------------------------------------------------------

	public static boolean endsWith(String str, String suffix) {
		return endsWith(str, suffix, false);
	}

	public static boolean endsWithIgnoreCase(String str, String suffix) {
		return endsWith(str, suffix, true);
	}

	private static boolean endsWith(String str, String suffix,
			boolean ignoreCase) {
		if (str == null || suffix == null) {
			return (str == null && suffix == null);
		}
		if (suffix.length() > str.length()) {
			return false;
		}
		int strOffset = str.length() - suffix.length();
		return str.regionMatches(ignoreCase, strOffset, suffix, 0,
				suffix.length());
	}
}