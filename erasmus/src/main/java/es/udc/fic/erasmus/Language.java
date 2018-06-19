package es.udc.fic.erasmus;

/**
 * The Enum Language.
 */
public enum Language {	
	
	/** The ge a2. */
	GE_A2 (01),
	
	/** The ge b1. */
	GE_B1 (11),
	
	/** The ge b2. */
	GE_B2 (21),
	
	/** The pt a2. */
	PT_A2 (02),
	
	/** The pt b1. */
	PT_B1 (12),
	
	/** The pt b2. */
	PT_B2 (22),
	
	/** The it a2. */
	IT_A2 (03),
	
	/** The it b1. */
	IT_B1 (13),
	
	/** The it b2. */
	IT_B2 (23),
	
	/** The en a2. */
	EN_A2 (04),
	
	/** The en b1. */
	EN_B1 (14),
	
	/** The en b2. */
	EN_B2 (24),
	
	/** The fr a2. */
	FR_A2 (05),
	
	/** The fr b1. */
	FR_B1 (15),
	
	/** The fr b2. */
	FR_B2 (25);
	
	/** The code. */
	private final int mCode;
	
	/**
	 * Instantiates a new language.
	 *
	 * @param code the code
	 */
	private Language(int code) {
		mCode = code;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public int getmCode() {
		return mCode;
	}
	
	/**
	 * Gets the languge.
	 *
	 * @return the languge
	 */
	public int getLanguge() {
		return mCode%10;
	}
	
	/**
	 * Checking. checks if the given language is
	 *           equal or superior to the current language.
	 *           
	 * @param language the language
	 * @return true, if successful
	 */
	public boolean checking(Language language) {
		int lan1, lan2, grade1, grade2;
		if (language == null)
			return false;
		lan1=mCode%10; lan2=language.mCode%10;
		grade1=mCode/10; grade2=language.mCode/10;
		if ((lan1 == lan2) && (grade2 >= grade1))
			return true;
		else
			return false;		
	}
	
	/**
	 * Superior. checks if exists a superior language thna the current one in 
	 *           the String of languages.
	 *
	 * @param  String lan language in string mode
	 * @return true, if successful
	 */
	public boolean superior(String lan) {
		int lan1, lan2, grade1, grade2;
		if (lan == null)
			return false;
		if (lan.toUpperCase().contains("PROBA")  || lan.toUpperCase().contains("PRUEBA"))
			return false;
		lan = lan.replaceAll("\\s", "");
		lan = lan.toUpperCase().replaceAll("-", "_");
		lan = lan.toUpperCase().replaceAll("–", "_");
		for (String s: lan.split(",")) {
			Language language = Language.valueOf(s);
			lan1=mCode%10; lan2=language.mCode%10;
			grade1=mCode/10; grade2=language.mCode/10;
			if ((lan1 == lan2) && (grade2 > grade1))
				return true;
		}
		return false;		
	}
	
}
