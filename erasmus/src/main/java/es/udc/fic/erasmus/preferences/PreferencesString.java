package es.udc.fic.erasmus.preferences;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="preferencesString")
public class PreferencesString implements Serializable {
	
	@Id
	private String id;
	
	private boolean active;
	
	private String name;
	private String dni;
	private String language;
	private String others;
	private String note;
	private String val;
	private String lang_test1;
	private String lang_test2;
	private String lang_test3;
	private String lang_test4;
	private String lang_test5;
	
	private String uni;
	private String priority;
	private String start;
	private String motive;
	private String state;
	
	private String institution;
	private String lang;
	private String country;
	private String year;
	private String number;
	private String duration;
	private String posts;

	public PreferencesString() {}

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
	
	public void setRequest(String[] request) {
		this.uni = request[0];
		this.priority = request[1];
		this.start = request[2];
		this.motive = request[3];
		this.state = request[4];
	}
	
	public void setUniversity(String[] uni) {
		this.institution = uni[0];
		this.lang = uni[1];
		this.country = uni[2];
		this.year = uni[3];
		this.number = uni[4];
		this.duration = uni[5];
		this.posts = uni[6];
	}
	
	public String[] getUniversityCols() {
		String[] result = {institution, lang, country, year, number, duration, posts};
		return result;
	}
	
	public String[] getStudentCols() {
		String[] result = {name, dni, language, others, note, val,
				lang_test1, lang_test2, lang_test3, lang_test4, lang_test5};
		return result;
	}
	
	public String[] getRequestCols() {
		String[] result = {uni, priority, start, motive, state};
		return result;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public String getLang_test1() {
		return lang_test1;
	}

	public void setLang_test1(String lang_test1) {
		this.lang_test1 = lang_test1;
	}

	public String getLang_test2() {
		return lang_test2;
	}

	public void setLang_test2(String lang_test2) {
		this.lang_test2 = lang_test2;
	}

	public String getLang_test3() {
		return lang_test3;
	}

	public void setLang_test3(String lang_test3) {
		this.lang_test3 = lang_test3;
	}

	public String getLang_test4() {
		return lang_test4;
	}

	public void setLang_test4(String lang_test4) {
		this.lang_test4 = lang_test4;
	}

	public String getLang_test5() {
		return lang_test5;
	}

	public void setLang_test5(String lang_test5) {
		this.lang_test5 = lang_test5;
	}

	public String getUni() {
		return uni;
	}

	public void setUni(String uni) {
		this.uni = uni;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getMotive() {
		return motive;
	}

	public void setMotive(String motive) {
		this.motive = motive;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getPosts() {
		return posts;
	}

	public void setPosts(String posts) {
		this.posts = posts;
	}

	public String getDni() {
		return dni;
	}
}
