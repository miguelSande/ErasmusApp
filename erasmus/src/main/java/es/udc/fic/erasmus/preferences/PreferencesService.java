package es.udc.fic.erasmus.preferences;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PreferencesService {

	@Autowired
	private PreferencesRepository repo;
	
	@PostConstruct
	protected void initialize() {
		create(new Preferences("default", new int[]{3,4,12,20,19,21,14,15,16,17,18}, 
				new int[]{7,8,10,22,11}, new int[]{7,8,0,1,2,3,6}));
	}
	
	@Transactional
	public Preferences create(Preferences preference) {
		Preferences actual = repo.findOne(preference.getId());
		if (actual == null) {
			repo.save(preference);
			return preference;
		}
		actual.setRequestCols(preference.getRequestCols());
		actual.setStudentCols(preference.getStudentCols());
		actual.setUniversityCols(preference.getUniversityCols());
		repo.save(actual);
		return actual;
	}
	
	public Preferences find(String name) {
		return repo.findOne(name);
	}
}
