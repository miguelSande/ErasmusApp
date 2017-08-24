package es.udc.fic.erasmus;

public enum Language {	
	GE_A2 (01),
	GE_B1 (11),
	GE_B2 (21),
	PT_A2 (02),
	PT_B1 (12),
	PT_B2 (22),
	IT_A2 (03),
	IT_B1 (13),
	IT_B2 (23),
	EN_A2 (04),
	EN_B1 (14),
	EN_B2 (24);
	
	private final int mCode;
	
	private Language(int code) {
		mCode = code;
	}

	public int getmCode() {
		return mCode;
	}
	
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
	
	public boolean superior(String lan) {
		int lan1, lan2, grade1, grade2;
		if (lan == null)
			return false;
		if (lan.toUpperCase().contains("PROBA")  || lan.toUpperCase().contains("PRUEBA"))
			return false;
		lan = lan.replaceAll("\\s", "");
		lan = lan.toUpperCase().replaceAll("-", "_");
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
