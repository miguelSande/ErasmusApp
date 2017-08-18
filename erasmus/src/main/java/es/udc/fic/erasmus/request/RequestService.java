package es.udc.fic.erasmus.request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fic.erasmus.Language;
import es.udc.fic.erasmus.State;
import es.udc.fic.erasmus.student.Student;
import es.udc.fic.erasmus.student.StudentService;
import es.udc.fic.erasmus.university.University;
import es.udc.fic.erasmus.university.UniversityRepository;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestService {
	
	@Autowired
	private RequestRepository requestRepo;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private UniversityRepository uniRepo;
	
	@Transactional
	public Request create(Request request) {
		requestRepo.save(request);
		return request;
	}
	
	public List<Request> returnRequests(List<Request> rqs) {
		List<Request> result = new ArrayList<>();
		for (Request r: rqs) {
			result.add(requestRepo.findOne(r.getRe_id()));
		}
		return result;
	}
	
	public List<Request> findByStudent(Student student) {
		return requestRepo.findByStudent(student);
	}
	
	public void preprocess() {
		studentService.calculateVal();
		List<Student> allReq = requestRepo.pendingRequests(State.PENDING);
		allReq.sort((o1,o2) -> o1.getVal().compareTo(o2.getVal()));
		Collections.reverse(allReq);
		for (Student st: allReq) {
			process(st);
		}
	}
	
	public boolean checkLanguage(Student student, Language language) {
		if (language == null)
			return true;
		if (student.getLanguage() != null) {
			if (student.getLanguage().toUpperCase().contains("PROBA"))
				return true;
			String[] lans = student.getLanguage().split(",");
			List<Language> languages = new ArrayList<>();
			for (String s: lans) {
				s = s.toUpperCase();
				s = s.replaceAll("\\s", "");
				s = s.replace("-", "_");
				languages.add(Language.valueOf(s));
			}
			for (Language l: languages) {
				if (language.superior(l)) 
					return true;		
			}
		}
		if (student.getLang_test() != null)
			return student.getLang_test();
		else
			return false;
	}

	@Transactional
	public void process(Student student) {
		List<Request> request = requestRepo.findByStudent(student);
		boolean pending = true;
		request.sort((r1,r2) -> r1.getPriority().compareTo(r2.getPriority()));
		for (Request r: request) {
			r.setStudent(student);
			//hay otro aceptado
			if (pending) {
				//supera el requisito de idioma pedido
				if (checkLanguage(student, r.getUniversity().getLanguage())) {
					University uni = r.getUniversity();
					//hay plazas disponibles en el erasmus
					if (uni.getPosts() > 0) {
						r.setState(State.ACCEPTED);
						requestRepo.save(r);
						pending=false;
						uni.setPosts(uni.getPosts()-1);
						uniRepo.save(uni);
					}
					else {
						r.setState(State.REJECTED);
						r.setMotive("Prazas agotadas");
						requestRepo.save(r);
					}
				}
				else {
					r.setState(State.REJECTED);
					r.setMotive("Non xustifica idiomas");
					requestRepo.save(r);
				}
			}
			else {
				r.setState(State.REJECTED);
				r.setMotive("Asignada opción preferente");
				requestRepo.save(r);
			}
		}		
	}

}
