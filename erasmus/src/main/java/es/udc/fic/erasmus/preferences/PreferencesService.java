package es.udc.fic.erasmus.preferences;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class PreferencesService.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PreferencesService {

	/** The repo. */
	@Autowired
	private PreferencesRepository repo;
	
	/** The repo string. */
	@Autowired
	private PreferencesStringRepository repoString;
	
	/**
	 * Initialize both preferences as default.
	 */
	@PostConstruct
	protected void initialize() {
		save(new Preferences("default", new int[]{3,4,12,20,19,21,14,15,16,17,18}, 
				new int[]{7,8,10,22,11}, new int[]{7,8,0,1,2,3,6}));
		save(new PreferencesString("default", new String[] {"Nombre", "D.N.I.", "Certificación  de idiomas", "OUTROS MERITOS", "NOTA MEDIA",
				"Valoración", "test1", "test2", "test3", "test4", "test5"},
				new String[] {"Institución", "Orden", "Inicio (semestre)", "MOTIVOS DE REXEITAMENTO", "Estado de solicitud - interno"},
				new String[] {"Relation: External institutions", "Idioma", "Relation: Country", "Academic year", "Number", "Total duration", "Remaining seats"}));
	}
	
	/**
	 * Save.
	 *
	 * @param preference the preference
	 * @return the preferences
	 */
	@Transactional
	public Preferences save(Preferences preference) {
		Preferences actual = repo.findOne(preference.getId());
		if (actual == null) {
			repo.save(preference);
			return preference;
		}
		actual.setRequestCols(preference.getRequestCols());
		actual.setStudentCols(preference.getStudentCols());
		actual.setUniversityCols(preference.getUniversityCols());
		actual.setActive(preference.isActive());
		repo.save(actual);
		return actual;
	}
	
	/**
	 * Save.
	 *
	 * @param preference the preference
	 * @return the preferences string
	 */
	@Transactional
	public PreferencesString save(PreferencesString preference) {
		PreferencesString actual = repoString.findOne(preference.getId());
		if (actual == null) {
			repoString.save(preference);
			return preference;
		}
		actual.setRequest(preference.getRequestCols());
		actual.setStudent(preference.getStudentCols());
		actual.setUniversity(preference.getUniversityCols());
		actual.setActive(preference.isActive());
		repoString.save(actual);
		return actual;
	}
	
	/**
	 * Find.
	 *
	 * @param name the name
	 * @return the preferences
	 */
	public Preferences find(String name) {
		return repo.findOne(name);
	}
	
	/**
	 * Find string.
	 *
	 * @param name the name
	 * @return the preferences string
	 */
	public PreferencesString findString(String name) {
		return repoString.findOne(name);
	}
}
