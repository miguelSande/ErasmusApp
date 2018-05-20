package es.udc.fic.erasmus.parser;

import es.udc.fic.erasmus.Language;

// TODO: Auto-generated Javadoc
/**
 * The Class Parser.
 */
public class Parser {

	/**
	 * Instantiates a new parser.
	 */
	public Parser() {}
	
	/**
	 * Compares the given code to all codes.
	 *
	 * @param code the code
	 * @return the language
	 */
	private Language compareCodes(int code) {
		for (Language lan: Language.values()) {
			if (lan.getmCode() == code)
				return lan;
		}
		return null;
	}
	
	/**
	 * Checks if the string contains the word test.
	 *
	 * @param string the string
	 * @return true, if is test
	 */
	private boolean isTest(String string) {
		boolean result = false;
		string = string.toUpperCase();
		if (string.contains("PROBA") || string.contains("PRUEBA") || string.contains("TEST"))
			result = true;
		return result;
	}
	
	/**
	 * Checks if the string contains a test english.
	 *
	 * @param string the string
	 * @return true, if is test english
	 */
	public boolean isTestEnglish(String string) {
		if (string == null)
			return false;
		string = string.toUpperCase();
		for (String s: string.split(",")) {
			if (s.contains("EN") && isTest(s))
				return true;
		}
		return false;
	}
	
	/**
	 * Checks if the string contains a test french.
	 *
	 * @param string the string
	 * @return true, if is test french
	 */
	public boolean isTestFrench(String string) {
		if (string == null)
			return false;
		string = string.toUpperCase();
		for (String s: string.split(",")) {
			if (s.contains("FR") && isTest(s))
				return true;
		}
		return false;
	}
	
	/**
	 * Checks if the string contains a test german.
	 *
	 * @param string the string
	 * @return true, if is test german
	 */
	public boolean isTestGerman(String string) {
		if (string == null)
			return false;
		string = string.toUpperCase();
		for (String s: string.split(",")) {
			if (s.contains("GE") && isTest(s))
				return true;
		}
		return false;
	}
	
	/**
	 * Checks if the string contains a test italian.
	 *
	 * @param string the string
	 * @return true, if is test italian
	 */
	public boolean isTestItalian(String string) {
		if (string == null)
			return false;
		string = string.toUpperCase();
		for (String s: string.split(",")) {
			if (s.contains("IT") && isTest(s))
				return true;
		}
		return false;
	}
	
	/**
	 * Checks if the string contains a test portuguess.
	 *
	 * @param string the string
	 * @return true, if is test portuguess
	 */
	public boolean isTestPortuguess(String string) {
		if (string == null)
			return false;
		string = string.toUpperCase();
		for (String s: string.split(",")) {
			if (s.contains("PT") && isTest(s))
				return true;
		}
		return false;
	}
	
	/**
	 * Parses the string, only works for a single string
	 * on strings with a ',' returns null.
	 *
	 * @param string the string
	 * @return the language
	 */
	public Language parse(String string) {
		if (string == null)
			return null;
		int code = 90;
		string = string.toUpperCase();
		if (string.contains("EN"))
			code = code + 4;
		if (string.contains("IT"))
			code = code + 3;
		if (string.contains("PT"))
			code = code + 2;
		if (string.contains("GE"))
			code = code + 1;
		if (string.contains("FR"))
			code = code + 5;
		if (string.contains("A2"))
			code = (code%10) + 00;
		if (string.contains("B1"))
			code = (code%10) + 10;
		if (string.contains("B2"))
			code = (code%10) + 20;
		return compareCodes(code);
	}
}