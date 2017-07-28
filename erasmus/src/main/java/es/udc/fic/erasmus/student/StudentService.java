package es.udc.fic.erasmus.student;

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
	
	@Transactional
	public Student create(Student student) {
		studentRepo.save(student);
		return student;
	}
	
	public Student calculateVal(Student student) {
		double val = student.getNote()*0.95;
		if (student.getOthers() != null)
			val = val + 0.25;
		if (student.getLanguage() != null && student.getLanguage().split(",").length > 1)
			val = val + 0.25;
		//comprobar que el grado es superior al que piden la oferta
		student.setVal(val);
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
