package es.udc.fic.erasmus;

public enum State {
	PENDING ("Pending"),
	ACCEPTED ("Adxudicado"),
	REJECTED ("Rexeitado"),
	WAITING ("Lista de agarda");
	
	private final String mValue;
	
	private State(String value) {
		mValue = value;
	}
	
	public String getValue() {
		return mValue;
	}
}
