package es.udc.fic.erasmus.preferences;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="preferences")
public class Preferences implements Serializable {
	
	@Id
	private String id;
	
	private boolean active;
	
	private int nameC;
	private int dniC;
	private int languageC;
	private int othersC;
	private int noteC;
	private int valC;
	private int lang_test1C;
	private int lang_test2C;
	private int lang_test3C;
	private int lang_test4C;
	private int lang_test5C;
	
	private int uniC;
	private int priorityC;
	private int startC;
	private int motiveC;
	private int stateC;
	
	private int institutionC;
	private int langC;
	private int countryC;
	private int yearC;
	private int numberC;
	private int durationC;
	private int postsC;
	
	public Preferences() {}
	
	public Preferences(String id, int[] studentC, int[] requestC, int[] univerC) {
		this.id = id;
		this.active = false;
		
		this.nameC = studentC[0];
		this.dniC = studentC[1];
		this.languageC = studentC[2];
		this.othersC = studentC[3];
		this.noteC = studentC[4];
		this.valC = studentC[5];
		this.lang_test1C = studentC[6];
		this.lang_test2C = studentC[7];
		this.lang_test3C = studentC[8];
		this.lang_test4C = studentC[9];
		this.lang_test5C = studentC[10];
		
		this.uniC = requestC[0];
		this.priorityC = requestC[1];
		this.startC = requestC[2];
		this.motiveC = requestC[3];
		this.stateC = requestC[4];
		
		this.institutionC = univerC[0];
		this.langC = univerC[1];
		this.countryC = univerC[2];
		this.yearC = univerC[3];
		this.numberC = univerC[4];
		this.durationC = univerC[5];
		this.postsC = univerC[6];
	}
	
	public String getId() {
		return id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int[] getUniversityCols() {
		int[] result = {institutionC, langC, countryC, yearC, numberC, durationC, postsC};
		return result;
	}
	
	public int[] getStudentCols() {
		int[] result = {nameC, dniC, languageC, othersC, noteC, valC,
				lang_test1C, lang_test2C, lang_test3C, lang_test4C, lang_test5C};
		return result;
	}
	
	public int[] getRequestCols() {
		int[] result = {uniC, priorityC, startC, motiveC, stateC};
		return result;
	}
	
	public void setUniversityCols(int[] univerC) {
		this.institutionC = univerC[0];
		this.langC = univerC[1];
		this.countryC = univerC[2];
		this.yearC = univerC[3];
		this.numberC = univerC[4];
		this.durationC = univerC[5];
		this.postsC = univerC[6];
	}
	
	public void setStudentCols(int[] studentC) {
		this.nameC = studentC[0];
		this.dniC = studentC[1];
		this.languageC = studentC[2];
		this.othersC = studentC[3];
		this.noteC = studentC[4];
		this.valC = studentC[5];
		this.lang_test1C = studentC[6];
		this.lang_test2C = studentC[7];
		this.lang_test3C = studentC[8];
		this.lang_test4C = studentC[9];
		this.lang_test5C = studentC[10];
	}

	public void setRequestCols(int[] requestC) {
		this.uniC = requestC[0];
		this.priorityC = requestC[1];
		this.startC = requestC[2];
		this.motiveC = requestC[3];
		this.stateC = requestC[4];
	}

	public int getNameC() {
		return nameC;
	}

	public void setNameC(int nameC) {
		this.nameC = nameC;
	}

	public int getDniC() {
		return dniC;
	}

	public void setDniC(int dniC) {
		this.dniC = dniC;
	}

	public int getLanguageC() {
		return languageC;
	}

	public void setLanguageC(int languageC) {
		this.languageC = languageC;
	}

	public int getOthersC() {
		return othersC;
	}

	public void setOthersC(int othersC) {
		this.othersC = othersC;
	}

	public int getNoteC() {
		return noteC;
	}

	public void setNoteC(int noteC) {
		this.noteC = noteC;
	}

	public int getValC() {
		return valC;
	}

	public void setValC(int valC) {
		this.valC = valC;
	}

	public int getLang_test1C() {
		return lang_test1C;
	}

	public void setLang_test1C(int lang_test1C) {
		this.lang_test1C = lang_test1C;
	}

	public int getLang_test2C() {
		return lang_test2C;
	}

	public void setLang_test2C(int lang_test2C) {
		this.lang_test2C = lang_test2C;
	}

	public int getLang_test3C() {
		return lang_test3C;
	}

	public void setLang_test3C(int lang_test3C) {
		this.lang_test3C = lang_test3C;
	}

	public int getLang_test4C() {
		return lang_test4C;
	}

	public void setLang_test4C(int lang_test4C) {
		this.lang_test4C = lang_test4C;
	}

	public int getLang_test5C() {
		return lang_test5C;
	}

	public void setLang_test5C(int lang_test5C) {
		this.lang_test5C = lang_test5C;
	}

	public int getUniC() {
		return uniC;
	}

	public void setUniC(int uniC) {
		this.uniC = uniC;
	}

	public int getPriorityC() {
		return priorityC;
	}

	public void setPriorityC(int priorityC) {
		this.priorityC = priorityC;
	}

	public int getStartC() {
		return startC;
	}

	public void setStartC(int startC) {
		this.startC = startC;
	}

	public int getMotiveC() {
		return motiveC;
	}

	public void setMotiveC(int motiveC) {
		this.motiveC = motiveC;
	}

	public int getStateC() {
		return stateC;
	}

	public void setStateC(int stateC) {
		this.stateC = stateC;
	}

	public int getInstitutionC() {
		return institutionC;
	}

	public void setInstitutionC(int institutionC) {
		this.institutionC = institutionC;
	}

	public int getLangC() {
		return langC;
	}

	public void setLangC(int langC) {
		this.langC = langC;
	}

	public int getCountryC() {
		return countryC;
	}

	public void setCountryC(int countryC) {
		this.countryC = countryC;
	}

	public int getYearC() {
		return yearC;
	}

	public void setYearC(int yearC) {
		this.yearC = yearC;
	}

	public int getNumberC() {
		return numberC;
	}

	public void setNumberC(int numberC) {
		this.numberC = numberC;
	}

	public int getDurationC() {
		return durationC;
	}

	public void setDurationC(int durationC) {
		this.durationC = durationC;
	}

	public int getPostsC() {
		return postsC;
	}

	public void setPostsC(int postsC) {
		this.postsC = postsC;
	}

	public void setId(String id) {
		this.id = id;
	}
}
