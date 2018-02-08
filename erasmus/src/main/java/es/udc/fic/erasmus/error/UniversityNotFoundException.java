package es.udc.fic.erasmus.error;

public class UniversityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7150622781655456727L;
	
	private String name;
	
	public UniversityNotFoundException(String name) {
		super("University " + name + " not found");
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

}
