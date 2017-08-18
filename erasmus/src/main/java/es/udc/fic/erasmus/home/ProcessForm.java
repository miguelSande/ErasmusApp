package es.udc.fic.erasmus.home;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public class ProcessForm {
	
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	
	
	@NotBlank(message = ProcessForm.NOT_BLANK_MESSAGE)
	private MultipartFile pathUni;
	
	@NotBlank(message = ProcessForm.NOT_BLANK_MESSAGE)
	private MultipartFile pathRequest;

	public MultipartFile getPathUni() {
		return pathUni;
	}

	public void setPathUni(MultipartFile pathUni) {
		this.pathUni = pathUni;
	}

	public MultipartFile getPathRequest() {
		return pathRequest;
	}

	public void setPathRequest(MultipartFile pathRequest) {
		this.pathRequest = pathRequest;
	}
}
