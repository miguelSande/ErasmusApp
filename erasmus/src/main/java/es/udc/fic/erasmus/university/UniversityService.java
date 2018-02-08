package es.udc.fic.erasmus.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class UniversityService.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UniversityService {
	
	/** The university repo. */
	@Autowired
	private UniversityRepository universityRepo;
	
	/**
	 * Creates the university.
	 * If already exists, updates its info
	 *
	 * @param university the university
	 * @return the university
	 */
	@Transactional
	public University create(University university) {
		if (!universityRepo.exists(university.getName())) {
			universityRepo.save(university);
			return university;
		}
		else {
			University actual = universityRepo.findByName(university.getName());
			actual.setYear(university.getYear());
			actual.setDuration(university.getDuration());
			actual.setLanguage(university.getLanguage());
			actual.setPosts(university.getPosts());
			universityRepo.save(actual);
			return actual;
		}
	}
	
}
