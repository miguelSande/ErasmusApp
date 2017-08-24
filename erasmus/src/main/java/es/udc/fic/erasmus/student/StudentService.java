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

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepo;
	
	@Autowired
	private RequestService rqService;
	
	private static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
	
	@Transactional
	public Student create(Student student) {
		if (studentRepo.exists(student.getDni())) {
			Student actual = studentRepo.findByDni(student.getDni());
			actual.setVal(null);
			actual.setLang_test(student.getLang_test());
			actual.setLanguage(student.getLanguage());
			actual.setNote(student.getNote());
			actual.setOthers(student.getOthers());
			studentRepo.save(actual);
			return actual;
		}
		studentRepo.save(student);
		return student;
	}
	
	public boolean exists(String dni) {
		return studentRepo.exists(dni);
	}
	
	public Student find(String dni) {
		return studentRepo.findByDni(dni);
	}
	
	public Student calculateVal(Student student) {
		double val = student.getNote()*0.95;
		if (student.getOthers() != null)
			val = val + 0.25;
		if (student.getLanguage() != null && student.getLanguage().split(",").length > 1)
			val = val + 0.25;
		List<Request> rqs = rqService.findByStudent(student);
		for (Request r: rqs) {
			if (r.getUniversity().getLanguage() == null && student.getLanguage() != null && student.getLanguage().toUpperCase().contains("PROBA")) {
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
	
	@Transactional
	public void calculateVal() {
		List<Student> students = studentRepo.findByValIsNull();
		for (Student s: students) {
			s = calculateVal(s);
			studentRepo.save(s);
		}
	}

}
