package es.udc.fic.erasmus.student;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fic.erasmus.config.WebAppConfigurationAware;

@Transactional
public class StudentServiceTest extends WebAppConfigurationAware {
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private StudentRepository studentRepo;
	
	private void delete(Student student) {
		studentRepo.delete(student);
	}
	
	@Test
	public void testCreate() {
		Student student = new Student("nombre apellido apellido", 6.57, null, "EN - B2", false);
		student = studentService.create(student);
		Student expected = studentRepo.findOne(student.getIdS());
		assertEquals(student,expected);		
		delete(student);
	}
	
	@Test
	public void testCalcSimpleVal() {
		Student student = new Student("nombre apellido apellido", 6.57, null, "EN - B2", false);
		student = studentService.create(student);
		Double expected = 6.57 * 0.95;
		student = studentService.calculateVal(student);
		assertEquals(expected, student.getVal());
		delete(student);
	}
	
	@Test
	public void testCalcValWithOthers() {
		Student student = new Student("nombre apellido apellido", 6.57, "curso en cosas", "EN - B2", false);
		student = studentService.create(student);
		Double expected = 6.57 * 0.95 + 0.25;
		student = studentService.calculateVal(student);
		assertEquals(expected, student.getVal());
		delete(student);
	}
	
	@Test
	public void testCalcValWithLanguages() {
		Student student = new Student("nombre apellido apellido", 6.57, null, "EN - B2,PT - A2", false);
		student = studentService.create(student);
		Double expected = 6.57 * 0.95 + 0.25;
		student = studentService.calculateVal(student);
		assertEquals(expected, student.getVal());
		delete(student);
	}
	
	@Test
	public void testCalcTotalVal() {
		Student student = new Student("nombre apellido apellido", 6.57, "curso", "EN - B2,PT - A2", false);
		student = studentService.create(student);
		Double expected = 6.57 * 0.95 + 0.5;
		student = studentService.calculateVal(student);
		assertEquals(expected, student.getVal());
		delete(student);
	}
	
	@Test
	public void testAutoVal () {
		Student student = new Student("student1", 6.57, null, "EN - B2", false);
		student = studentService.create(student);
		student = studentService.calculateVal(student);
		studentRepo.save(student);
		
		Student student1 = new Student("student1", 6.57, null, "EN - B2", false);
		student1 = studentService.create(student1);
		Double expected1 = 6.57 * 0.95;
		Student student2 = new Student("student2", 7.28, "curso", null, true);
		student2 = studentService.create(student2);
		Double expected2 = 7.166;
		Student student3 = new Student("student3", 5.25, "curso", "EN - B2,PT - A2", false);
		student3 = studentService.create(student3);
		Double expected3 = 5.25 * 0.95 + 0.5;
		
		assertEquals(3, studentRepo.findByValIsNull().size());
		studentService.calculateVal();
		assertEquals(expected1, studentRepo.findOne(student1.getIdS()).getVal());
		assertEquals(expected2, studentRepo.findOne(student2.getIdS()).getVal());
		assertEquals(expected3, studentRepo.findOne(student3.getIdS()).getVal());
		delete(student);
		delete(student1);
		delete(student2);
		delete(student3);
	}
		
}
