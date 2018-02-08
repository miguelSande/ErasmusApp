package es.udc.fic.erasmus;

/**
 * The Enum State.
 */
public enum State {
	
	PENDING ("Pending"),
	
	ACCEPTED ("Adxudicado"),
	
	REJECTED ("Rexeitado"),
	
	WAITING ("Lista de agarda");
	
	/** The value. */
	private final String mValue;
	
	/**
	 * Instantiates a new state.
	 *
	 * @param value the value
	 */
	private State(String value) {
		mValue = value;
	}
	
	/**
	 * Gets the translation.
	 *
	 * @return the value
	 */
	public String getValue() {
		return mValue;
	}
}
