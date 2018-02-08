package es.udc.fic.erasmus.preferences;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class PreferencesString.
 */
@SuppressWarnings("serial")
@Entity
@Table(name="preferencesString")
public class PreferencesString implements Serializable {
	
	/** The id. */
	@Id
	private String id;
	
	/** The active. */
	private boolean active;
	/** the name for the necessary columns */
	
	/** The name. */
	private String name;
	
	/** The dni. */
	private String dni;
	
	/** The language. */
	private String language;
	
	/** The others. */
	private String others;
	
	/** The note. */
	private String note;
	
	/** The val. */
	private String val;
	
	/** The lang test 1. */
	private String lang_test1;
	
	/** The lang test 2. */
	private String lang_test2;
	
	/** The lang test 3. */
	private String lang_test3;
	
	/** The lang test 4. */
	private String lang_test4;
	
	/** The lang test 5. */
	private String lang_test5;
	
	/** The uni. */
	private String uni;
	
	/** The priority. */
	private String priority;
	
	/** The start. */
	private String start;
	
	/** The motive. */
	private String motive;
	
	/** The state. */
	private String state;
	
	/** The institution. */
	private String institution;
	
	/** The lang. */
	private String lang;
	
	/** The country. */
	private String country;
	
	/** The year. */
	private String year;
	
	/** The number. */
	private String number;
	
	/** The duration. */
	private String duration;
	
	/** The posts. */
	private String posts;

	/**
	 * Instantiates a new preferences string.
	 * Necessary for Hibernate.
	 */
	public PreferencesString() {}

	/**
	 * Instantiates a new preferences string.
	 * Using arrays for the groups of columns.
	 *
	 * @param id the id
	 * @param student the student
	 * @param request the request
	 * @param uni the uni
	 */
	public PreferencesString(String id, String[] student, String[] request, String[] uni) {
		this.id = id;
		this.active = true;
		
		this.name = student[0];
		this.dni = student[1];
		this.language = student[2];
		this.others = student[3];
		this.note = student[4];
		this.val = student[5];
		this.lang_test1 = student[6];
		this.lang_test2 = student[7];
		this.lang_test3 = student[8];
		this.lang_test4 = student[9];
		this.lang_test5 = student[10];
		
		this.uni = request[0];
		this.priority = request[1];
		this.start = request[2];
		this.motive = request[3];
		this.state = request[4];
		
		this.institution = uni[0];
		this.lang = uni[1];
		this.country = uni[2];
		this.year = uni[3];
		this.number = uni[4];
		this.duration = uni[5];
		this.posts = uni[6];
	}

	/**
	 * Sets the student columns.
	 *
	 * @param student the new student
	 */
	public void setStudent(String[] student) {
		this.name = student[0];
		this.dni = student[1];
		this.language = student[2];
		this.others = student[3];
		this.note = student[4];
		this.val = student[5];
		this.lang_test1 = student[6];
		this.lang_test2 = student[7];
		this.lang_test3 = student[8];
		this.lang_test4 = student[9];
		this.lang_test5 = student[10];
	}
	
	/**
	 * Sets the request columns.
	 *
	 * @param request the new request
	 */
	public void setRequest(String[] request) {
		this.uni = request[0];
		this.priority = request[1];
		this.start = request[2];
		this.motive = request[3];
		this.state = request[4];
	}
	
	/**
	 * Sets the university columns.
	 *
	 * @param uni the new university
	 */
	public void setUniversity(String[] uni) {
		this.institution = uni[0];
		this.lang = uni[1];
		this.country = uni[2];
		this.year = uni[3];
		this.number = uni[4];
		this.duration = uni[5];
		this.posts = uni[6];
	}
	
	/**
	 * Gets the university cols.
	 *
	 * @return the university cols
	 */
	public String[] getUniversityCols() {
		String[] result = {institution, lang, country, year, number, duration, posts};
		return result;
	}
	
	/**
	 * Gets the student cols.
	 *
	 * @return the student cols
	 */
	public String[] getStudentCols() {
		String[] result = {name, dni, language, others, note, val,
				lang_test1, lang_test2, lang_test3, lang_test4, lang_test5};
		return result;
	}
	
	/**
	 * Gets the request cols.
	 *
	 * @return the request cols
	 */
	public String[] getRequestCols() {
		String[] result = {uni, priority, start, motive, state};
		return result;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Checks if is active.
	 *
	 * @return true, if is active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets the active.
	 *
	 * @param active the new active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the language.
	 *
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Sets the language.
	 *
	 * @param language the new language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * Gets the others.
	 *
	 * @return the others
	 */
	public String getOthers() {
		return others;
	}

	/**
	 * Sets the others.
	 *
	 * @param others the new others
	 */
	public void setOthers(String others) {
		this.others = others;
	}

	/**
	 * Gets the note.
	 *
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Sets the note.
	 *
	 * @param note the new note
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * Gets the val.
	 *
	 * @return the val
	 */
	public String getVal() {
		return val;
	}

	/**
	 * Sets the val.
	 *
	 * @param val the new val
	 */
	public void setVal(String val) {
		this.val = val;
	}

	/**
	 * Gets the lang test 1.
	 *
	 * @return the lang test 1
	 */
	public String getLang_test1() {
		return lang_test1;
	}

	/**
	 * Sets the lang test 1.
	 *
	 * @param lang_test1 the new lang test 1
	 */
	public void setLang_test1(String lang_test1) {
		this.lang_test1 = lang_test1;
	}

	/**
	 * Gets the lang test 2.
	 *
	 * @return the lang test 2
	 */
	public String getLang_test2() {
		return lang_test2;
	}

	/**
	 * Sets the lang test 2.
	 *
	 * @param lang_test2 the new lang test 2
	 */
	public void setLang_test2(String lang_test2) {
		this.lang_test2 = lang_test2;
	}

	/**
	 * Gets the lang test 3.
	 *
	 * @return the lang test 3
	 */
	public String getLang_test3() {
		return lang_test3;
	}

	/**
	 * Sets the lang test 3.
	 *
	 * @param lang_test3 the new lang test 3
	 */
	public void setLang_test3(String lang_test3) {
		this.lang_test3 = lang_test3;
	}

	/**
	 * Gets the lang test 4.
	 *
	 * @return the lang test 4
	 */
	public String getLang_test4() {
		return lang_test4;
	}

	/**
	 * Sets the lang test 4.
	 *
	 * @param lang_test4 the new lang test 4
	 */
	public void setLang_test4(String lang_test4) {
		this.lang_test4 = lang_test4;
	}

	/**
	 * Gets the lang test 5.
	 *
	 * @return the lang test 5
	 */
	public String getLang_test5() {
		return lang_test5;
	}

	/**
	 * Sets the lang test 5.
	 *
	 * @param lang_test5 the new lang test 5
	 */
	public void setLang_test5(String lang_test5) {
		this.lang_test5 = lang_test5;
	}

	/**
	 * Gets the uni.
	 *
	 * @return the uni
	 */
	public String getUni() {
		return uni;
	}

	/**
	 * Sets the uni.
	 *
	 * @param uni the new uni
	 */
	public void setUni(String uni) {
		this.uni = uni;
	}

	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * Sets the priority.
	 *
	 * @param priority the new priority
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * Sets the start.
	 *
	 * @param start the new start
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * Gets the motive.
	 *
	 * @return the motive
	 */
	public String getMotive() {
		return motive;
	}

	/**
	 * Sets the motive.
	 *
	 * @param motive the new motive
	 */
	public void setMotive(String motive) {
		this.motive = motive;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Gets the institution.
	 *
	 * @return the institution
	 */
	public String getInstitution() {
		return institution;
	}

	/**
	 * Sets the institution.
	 *
	 * @param institution the new institution
	 */
	public void setInstitution(String institution) {
		this.institution = institution;
	}

	/**
	 * Gets the lang.
	 *
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * Sets the lang.
	 *
	 * @param lang the new lang
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country the new country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets the year.
	 *
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * Sets the year.
	 *
	 * @param year the new year
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * Gets the number.
	 *
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * Sets the number.
	 *
	 * @param number the new number
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * Sets the duration.
	 *
	 * @param duration the new duration
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * Gets the posts.
	 *
	 * @return the posts
	 */
	public String getPosts() {
		return posts;
	}

	/**
	 * Sets the posts.
	 *
	 * @param posts the new posts
	 */
	public void setPosts(String posts) {
		this.posts = posts;
	}

	/**
	 * Gets the dni.
	 *
	 * @return the dni
	 */
	public String getDni() {
		return dni;
	}
}
