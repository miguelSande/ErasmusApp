package es.udc.fic.erasmus.request;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fic.erasmus.Language;
import es.udc.fic.erasmus.State;
import es.udc.fic.erasmus.config.WebAppConfigurationAware;
import es.udc.fic.erasmus.student.Student;
import es.udc.fic.erasmus.student.StudentRepository;
import es.udc.fic.erasmus.university.University;
import es.udc.fic.erasmus.university.UniversityRepository;

@Transactional
public class RequestServiceTest extends WebAppConfigurationAware {
	
	@Autowired
	private RequestService rqService;
	
	@Autowired
	private StudentRepository stRepo;
	
	@Autowired
	private UniversityRepository uniRepo;
	
	@Autowired
	private RequestRepository rqRepo;
	
	private University createUni(Language language, Long posts) {
		University university = new University("universidad de prueba", language, "2018",
				10L, "españa", posts);
		university = uniRepo.save(university);
		return university;
	}
	
	private Student createStudent(String dni, Double note, String others, String language, Boolean lang_test) {
		Student student = new Student(dni, "john doe", note, others, language, lang_test);
		student = stRepo.save(student);
		return student;
	}
	
	@Test
	public void testCreate() {
		Student student = createStudent("7", 6.25, null, "EN - B1", false);
		University university = createUni(Language.EN_A2,6L);
		Request rq = new Request(student, university, 1L, "f");
		rq = rqService.create(rq);
		Request expected = rqRepo.findOne(rq.getRe_id());
		assertEquals(expected,rq);
		stRepo.delete(student);
		uniRepo.delete(university);
		rqRepo.delete(rq);
	}
	
	@Test
	public void testLanguage() {
		Student student1 = createStudent("1",6.25, null, "EN - B2", null);
		Student student2 = createStudent("2",6.25, null, "EN - B1", null);
		Student student3 = createStudent("3",6.25, null, "EN - A2", null);
		Student student4 = createStudent("4",6.25, null, null, true);
		Student student5 = createStudent("5",6.25, null, null, false);
		Student student6 = createStudent("6",6.25, null, null, null);
		University university = createUni(Language.EN_B1,6L);
		University university2 = createUni(null,6L);
		
		assertEquals(true, rqService.checkLanguage(student1, university.getLanguage()));
		assertEquals(true, rqService.checkLanguage(student2, university.getLanguage()));
		assertEquals(false, rqService.checkLanguage(student3, university.getLanguage()));
		assertEquals(true, rqService.checkLanguage(student4, university.getLanguage()));
		assertEquals(false, rqService.checkLanguage(student5, university.getLanguage()));
		assertEquals(false, rqService.checkLanguage(student6, university.getLanguage()));
		
		assertEquals(true, rqService.checkLanguage(student1, university2.getLanguage()));
		assertEquals(true, rqService.checkLanguage(student2, university2.getLanguage()));
		assertEquals(true, rqService.checkLanguage(student3, university2.getLanguage()));
		assertEquals(true, rqService.checkLanguage(student4, university2.getLanguage()));
		assertEquals(true, rqService.checkLanguage(student5, university2.getLanguage()));
		assertEquals(true, rqService.checkLanguage(student6, university2.getLanguage()));
		
		stRepo.delete(student1);
		stRepo.delete(student2);
		stRepo.delete(student3);
		stRepo.delete(student4);
		stRepo.delete(student5);
		stRepo.delete(student6);
		uniRepo.delete(university);
		uniRepo.delete(university2);
	}
	
	@Test
	public void testProcessBothAccepted() {
		Student student1 = createStudent("6",6.25, "cosa", "EN_A2", null);
		Student student2 = createStudent("7",5.25, null, "EN_A2", null);
		University university = createUni(Language.EN_A2, 2L);
		Request rq = new Request(student1, university, 1L, "f");
		rq = rqService.create(rq);
		Request rq2 = new Request(student2, university, 1L, "f");
		rq2 = rqService.create(rq2);
		rqService.preprocess();
		assertEquals(State.ACCEPTED, rq.getState());
		assertEquals(State.ACCEPTED,rq2.getState());
		stRepo.delete(student1);
		stRepo.delete(student2);
		uniRepo.delete(university);
		rqRepo.delete(rq);
		rqRepo.delete(rq2);
	}
	
	@Test
	public void testProcessRejectedLanguage() {
		Student student1 = createStudent("7",6.25, "cosa", "EN_B2", null);
		Student student2 = createStudent("8",5.25, null, null, null);
		University university = createUni(Language.EN_B1, 2L);
		Request rq = new Request(student1, university, 1L, "f");
		rq = rqService.create(rq);
		Request rq2 = new Request(student2, university, 1L, "f");
		rq2 = rqService.create(rq2);
		rqService.preprocess();
		assertEquals(State.ACCEPTED, rq.getState());
		assertEquals(State.REJECTED,rq2.getState());
		assertEquals("Non xustifica idiomas", rq2.getMotive());
		stRepo.delete(student1);
		stRepo.delete(student2);
		uniRepo.delete(university);
		rqRepo.delete(rq);
		rqRepo.delete(rq2);
	}
	
	@Test
	public void testProcessSimpleAcepted() {
		Student student = createStudent("7",5.25, null, null, null);
		Student student2 = createStudent("8",5.00, null, "EN - B2", null);
		University university = createUni(null, 2L);
		Request rq = new Request(student, university, 1L, "f");
		Request rq2 = new Request(student2, university, 1L, "f");
		rq = rqService.create(rq);
		rq2 = rqService.create(rq2);
		rqService.preprocess();
		assertEquals(State.ACCEPTED, rq.getState());
		assertEquals(State.ACCEPTED, rq2.getState());
		stRepo.delete(student);
		stRepo.delete(student2);
		uniRepo.delete(university);
		rqRepo.delete(rq);
		rqRepo.delete(rq2);
	}
	
	@Test
	public void testProcessRejectedSecondOption() {
		Student student1 = createStudent("7",6.25, "cosa", "EN_B2", null);
		Student student2 = createStudent("8",5.25, null, "EN_B2", null);
		University university = createUni(Language.EN_B1, 2L);
		Request rq = new Request(student1, university, 1L, "f");
		rq = rqService.create(rq);
		Request rq2 = new Request(student2, university, 1L, "f");
		rq2 = rqService.create(rq2);
		Request rq3 = new Request(student2, university, 2L, "f");
		rq3 = rqService.create(rq3); 
		rqService.preprocess();
		assertEquals(State.ACCEPTED, rq.getState());
		assertEquals(State.ACCEPTED, rq2.getState());
		assertEquals(State.REJECTED,rq3.getState());
		assertEquals("Asignada opción preferente", rq3.getMotive());
		stRepo.delete(student1);
		stRepo.delete(student2);
		uniRepo.delete(university);
		rqRepo.delete(rq);
		rqRepo.delete(rq2);
		rqRepo.delete(rq3);
	}
	
	@Test
	public void testProcessRejectedOutOfPosts() {
		Student student1 = createStudent("7",6.25, "cosa", "EN_B2", null);
		Student student2 = createStudent("8",5.25, null, "EN_B2", null);
		Student student3 = createStudent("9",5.00, null, "EN_B2", null);
		University university = createUni(Language.EN_B1, 2L);
		Request rq = new Request(student1, university, 1L, "f");
		rq = rqService.create(rq);
		Request rq2 = new Request(student2, university, 1L, "f");
		rq2 = rqService.create(rq2);
		Request rq3 = new Request(student3, university, 1L, "f");
		rq3 = rqService.create(rq3); 
		rqService.preprocess();
		assertEquals(State.ACCEPTED, rq.getState());
		assertEquals(State.ACCEPTED, rq2.getState());
		assertEquals(State.WAITING,rq3.getState());
		stRepo.delete(student1);
		stRepo.delete(student2);
		stRepo.delete(student3);
		uniRepo.delete(university);
		rqRepo.delete(rq);
		rqRepo.delete(rq2);
		rqRepo.delete(rq3);
	}

}
