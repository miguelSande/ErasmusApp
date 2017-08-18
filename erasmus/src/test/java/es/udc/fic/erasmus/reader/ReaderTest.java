package es.udc.fic.erasmus.reader;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fic.erasmus.Language;
import es.udc.fic.erasmus.State;
import es.udc.fic.erasmus.config.WebAppConfigurationAware;
import es.udc.fic.erasmus.request.Request;
import es.udc.fic.erasmus.request.RequestRepository;
import es.udc.fic.erasmus.request.RequestService;
import es.udc.fic.erasmus.student.Student;
import es.udc.fic.erasmus.student.StudentRepository;
import es.udc.fic.erasmus.university.University;
import es.udc.fic.erasmus.university.UniversityRepository;

@Transactional
public class ReaderTest extends WebAppConfigurationAware {
	
	@Autowired
	private ReaderUni readerU;
	
	@Autowired
	private ReaderStudent readerS;
	
	@Autowired
	private UniversityRepository uniRepo;
	
	@Autowired
	private StudentRepository stRepo;
	
	@Autowired
	private RequestRepository rqRepo;
	
	@Autowired
	private RequestService rqService;
	
	private void delete() {
		for (Request r: rqRepo.findAll())
			rqRepo.delete(r);
		for (Student s: stRepo.findAll())
			stRepo.delete(s);
		for (University u: uniRepo.findAll())
			uniRepo.delete(u);
	}
	
	@Test
	public void testReaderUni() throws IOException {
		String path = this.getClass().getResource("/destinos.XLS").getFile();
		List<University> expected = readerU.readUniExcel(new File(path), path);
		assertEquals(18, expected.size());
		University sample = expected.get(0);
		assertEquals("Portugal", sample.getCountry());
		assertEquals("2017/18", sample.getYear());
		assertEquals(10, sample.getDuration().longValue());
		assertEquals("Universidade Lusíada de Lisboa", sample.getName());
		assertEquals(4, sample.getPosts().longValue());
		assertEquals(Language.PT_A2, sample.getLanguage());
	}
	
	@Test
	public void testReaderStudent() throws IOException {
		String path = this.getClass().getResource("/pedidos.xlsx").getFile();
		List<Request> expected = readerS.readRequestExcel(new File(path), path);
		assertEquals(37, expected.size());
		List<Student> expec = readerS.readStudentExcel(new File(path), path);
		assertEquals(15, expec.size());
	}
	
	@Test  //problemas con el excel universidad inexistente
	public void testProcess() throws IOException {
		String path1 = this.getClass().getResource("/destinos.XLS").getFile();
		String path2 = this.getClass().getResource("/pedidos.xlsx").getFile();
		List<University> universities = readerU.readUniExcel(new File(path1), path1);
		for (University u: universities) {
			uniRepo.save(u);
		}
		University une = uniRepo.findByName("Kookmin University");
		assertEquals("Kookmin University", une.getName());
		List<Student> students = readerS.readStudentExcel(new File(path2), path2);
		for (Student s: students) {
			stRepo.save(s);
		}
		Student sample = stRepo.findByName("Gallardo Pedrosa, María");
		assertEquals("7.0783", sample.getNote().toString());
		List<Request> requests = readerS.readRequestExcel(new File(path2), path2);
		for (Request r: requests) {
			rqService.create(r);
		}
		rqService.preprocess();
		sample = stRepo.findByName("Gallardo Pedrosa, María");
		List<Request> rqs = rqService.findByStudent(sample);
		Request request = rqs.get(0);
		Request otherRq = rqService.findByStudent(stRepo.findByName("Miguel Sánchez, Elisa")).get(0);
		Request request2 = rqService.findByStudent(stRepo.findByName("Lorenzo Lago, Sofía")).get(0);
		assertEquals("6.7244", sample.getVal().toString());
		assertEquals(State.REJECTED, request.getState());
		assertEquals("Prazas agotadas", request.getMotive());
		assertEquals(State.REJECTED, otherRq.getState());
		assertEquals("Non xustifica idiomas", otherRq.getMotive());
		assertEquals(State.ACCEPTED, request2.getState());
		delete();
	}

}
