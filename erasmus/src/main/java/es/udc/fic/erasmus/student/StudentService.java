package es.udc.fic.erasmus.student;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepo;
	
	private static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
	
	@Transactional
	public Student create(Student student) {
		studentRepo.save(student);
		return student;
	}
	
	public boolean exists(String name) {
		Student student = studentRepo.findByName(name);
		if (student == null)
			return false;
		return true;
	}
	
	public Student find(String name) {
		return studentRepo.findByName(name);
	}
	
	public Student calculateVal(Student student) {
		double val = student.getNote()*0.95;
		if (student.getOthers() != null)
			val = val + 0.25;
		if (student.getLanguage() != null && student.getLanguage().split(",").length > 1)
			val = val + 0.25;
		//comprobar que el grado es superior al que piden la oferta
		student.setVal(round(val,4));
		return student;
	}
	
	@Transactional
	public void calculateVal() {
		List<Student> students = studentRepo.findByValIsNull();
		//comprobar que solo se aplica a los de la solicitud
		for (Student s: students) {
			s = calculateVal(s);
			studentRepo.save(s);
		}
	}

}
