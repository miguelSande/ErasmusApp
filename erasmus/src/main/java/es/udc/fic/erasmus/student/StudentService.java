package es.udc.fic.erasmus.student;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fic.erasmus.request.Request;
import es.udc.fic.erasmus.request.RequestService;

/**
 * The Class StudentService.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StudentService {
	
	/** The student repo. */
	@Autowired
	private StudentRepository studentRepo;
	
	/** The rq service. */
	@Autowired
	private RequestService rqService;
	
	/**
	 * Round. round a double.
	 * Used for round the note to 4 decimals.
	 *
	 * @param value the value
	 * @param places the places
	 * @return the double
	 */
	private static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
	
	/**
	 * Saves the student in DB.
	 *
	 * @param student the student
	 * @return the student
	 */
	@Transactional
	public Student create(Student student) {
		if (studentRepo.exists(student.getDni())) {
			Student actual = studentRepo.findByDni(student.getDni());
			actual.setVal(null);
			actual.setLang_test_en(student.isLang_test_en());
			actual.setLang_test_pt(student.isLang_test_pt());
			actual.setLang_test_it(student.isLang_test_it());
			actual.setLang_test_ge(student.isLang_test_ge());
			actual.setLang_test_fr(student.isLang_test_fr());
			actual.setLanguage(student.getLanguage());
			actual.setNote(student.getNote());
			actual.setOthers(student.getOthers());
			studentRepo.save(actual);
			return actual;
		}
		studentRepo.save(student);
		return student;
	}
	
	/**
	 * Exists. checks if the student already exists in DB.
	 *
	 * @param dni the dni
	 * @return true, if successful
	 */
	public boolean exists(String dni) {
		return studentRepo.exists(dni);
	}
	
	/**
	 * Find by DNI.
	 *
	 * @param dni the dni
	 * @return the student
	 */
	public Student find(String dni) {
		return studentRepo.findByDni(dni);
	}
	
	/**
	 * Calculate the value of the student.
	 * 0.95*note
	 * +0.25: has other courses, superior language requirements, knows additional languages.
	 * @param student the student
	 * @return the student
	 */
	public Student calculateVal(Student student) {
		double val = student.getNote()*0.95;
		if (student.getOthers() != null)
			val = val + 0.25;
		if (student.getLanguage() != null && student.getLanguage().split(",").length > 1)
			val = val + 0.25;
		List<Request> rqs = rqService.findByStudent(student);
		for (Request r: rqs) {
			if (r.getUniversity().getLanguage() == null && student.getLanguage() != null && !student.getLanguage().toUpperCase().contains("PROBA")) {
				val = val + 0.25;
				break;
			}
			if (r.getUniversity().getLanguage() != null && r.getUniversity().getLanguage().superior(student.getLanguage())) {
				val = val + 0.25;
				break;
			}					
		}
		student.setVal(round(val,4));
		return student;
	}
	
	/**
	 * Calculates the value of all the new students (new student = student without a value).
	 */
	@Transactional
	public void calculateVal() {
		List<Student> students = studentRepo.findByValIsNull();
		for (Student s: students) {
			s = calculateVal(s);
			studentRepo.save(s);
		}
	}

}
