package es.udc.fic.erasmus.request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fic.erasmus.Language;
import es.udc.fic.erasmus.State;
import es.udc.fic.erasmus.parser.Parser;
import es.udc.fic.erasmus.student.Student;
import es.udc.fic.erasmus.student.StudentService;
import es.udc.fic.erasmus.university.University;
import es.udc.fic.erasmus.university.UniversityRepository;

/**
 * The Class RequestService.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestService {
	
	/** The request repo. */
	@Autowired
	private RequestRepository requestRepo;
	
	/** The student service. */
	@Autowired
	private StudentService studentService;
	
	/** The uni repo. */
	@Autowired
	private UniversityRepository uniRepo;
	
	/**
	 * Creates a request.
	 *
	 * @param request the request
	 * @return the request
	 */
	@Transactional
	public Request create(Request request) {
		requestRepo.save(request);
		return request;
	}
	
	/**
	 * Search for the requests in DB.
	 *
	 * @param rqs the rqs
	 * @return the list
	 */
	public List<Request> returnRequests(List<Request> rqs) {
		List<Request> result = new ArrayList<>();
		for (Request r: rqs) {
			result.add(requestRepo.findOne(r.getRe_id()));
		}
		return result;
	}
	
	/**
	 * Find requests by student.
	 *
	 * @param student the student
	 * @return the list
	 */
	public List<Request> findByStudent(Student student) {
		return requestRepo.findByStudent(student);
	}
	
	/**
	 * Preprocess.
	 */
	public void preprocess() {
		studentService.calculateVal();
		List<Student> allReq = requestRepo.pendingRequests(State.PENDING);
		allReq.sort((o1,o2) -> o1.getVal().compareTo(o2.getVal()));
		Collections.reverse(allReq);
		for (Student st: allReq) {
			process(st);
			postprocess(st);
		}
	}
	
	/**
	 * Accepted. checks if exists an accepted request in a list.
	 *
	 * @param request the requests
	 * @return true, if successful
	 */
	private boolean accepted(List<Request> request) {
		for (Request r: request) {
			if (r.getState() == State.ACCEPTED)
				return true;
		}
		return false;
	}
	
	/**
	 * Waiting. Checks if exists a waiting request in a list.
	 *
	 * @param request the request
	 * @return true, if successful
	 */
	private boolean waiting(List<Request> request) {
		for (Request r: request) {
			if (r.getState() == State.WAITING)
				return true;
		}
		return false;
	}
	
	/**
	 * Filter. Return the waiting request and sorted by the waiting number.
	 *
	 * @param request the request
	 * @return the list
	 */
	private List<Request> filter(List<Request> request) {
		List<Request> result = new ArrayList<>();
		for (Request r: request) {
			if (r.getState() == State.WAITING) 
				result.add(r);
		}
		Collections.sort(result, new Comparator<Request>() {
			@Override public int compare(Request r1, Request r2) {
				return r1.getWaitingNum() - r2.getWaitingNum();
			}
		});
		return result;
	}
	
	/**
	 * Postprocess. changes the waiting request to rejected.
	 *
	 * @param st the st
	 */
	private void postprocess(Student st) {
		List<Request> rqs = requestRepo.findByStudent(st);
		if (waiting(rqs)) {
			if (accepted(rqs)) {
				for (Request r: rqs) {
					if (r.getState() == State.WAITING) {
						r.setState(State.REJECTED);
						r.setMotive("Prazas agotadas");
						requestRepo.save(r);
					}
				}
			}
			else {
				List<Request> rs = filter(rqs);
				if (rs.size() > 1) {
					for (int i = 1; i < rs.size(); i++) {
						Request r = rs.get(i);
						r.setState(State.REJECTED);
						r.setMotive("Asignada mellor opción");
						requestRepo.save(r);
					}
				}
			}
		}
	}

	/**
	 * Check language. checks if a student meets the  requested language. 
	 *
	 * @param student the student
	 * @param language the language required
	 * @return true, if the student has a valid test or meets the requirements.
	 */
	public boolean checkLanguage(Student student, Language language) {
		Parser parser = new Parser();
		if (language == null)
			return true;
		if (student.getLanguage() != null) {
			if (student.getLanguage().toUpperCase().contains("PROBA"))
				return true;
			String[] lans = student.getLanguage().split(",");
			List<Language> languages = new ArrayList<>();
			for (String s: lans) {
				languages.add(parser.parse(s));
			}
			for (Language l: languages) {
				if (language.checking(l)) 
					return true;		
			}
		}
		switch (language.getLanguge()) {
		case 1:
			return student.isLang_test_ge();
		case 2:
			return student.isLang_test_pt();
		case 3:
			return student.isLang_test_it();
		case 4:
			return student.isLang_test_en();
		case 5:
			return student.isLang_test_fr();
		default: return false;
		}
	}

	/**
	 * Process. process the student requests.
	 *
	 * @param student the student
	 */
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
					if (!uni.isEmpty()) {
						//comprobar la lista de espera
						if (uni.getPosts() > 0) {
							r.setState(State.ACCEPTED);
							requestRepo.save(r);
							pending=false;
							uni.setPosts(uni.getPosts()-1);
							uniRepo.save(uni);
						}
						else {
							r.setState(State.WAITING);
							r.setWaitingNum(uni.getWaiting()+1);
							requestRepo.save(r);
							uni.setWaiting(uni.getWaiting()+1);
							uniRepo.save(uni);
						}
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
