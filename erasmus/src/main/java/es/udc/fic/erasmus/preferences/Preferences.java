package es.udc.fic.erasmus.preferences;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class Preferences. preferences for the index of columns.
 */
@SuppressWarnings("serial")
@Entity
@Table(name="preferences")
public class Preferences implements Serializable {
	
	/** The id. */
	@Id
	private String id;
	
	/** The active. check if the preferences is active*/
	private boolean active;
	/** Necessary columns for the process of the documents*/
	
	/** The name C. */
	private int nameC;
	
	/** The dni C. */
	private int dniC;
	
	/** The language C. */
	private int languageC;
	
	/** The others C. */
	private int othersC;
	
	/** The note C. */
	private int noteC;
	
	/** The val C. */
	private int valC;
	
	/** The lang test 1 C. */
	private int lang_test1C;
	
	/** The lang test 2 C. */
	private int lang_test2C;
	
	/** The lang test 3 C. */
	private int lang_test3C;
	
	/** The lang test 4 C. */
	private int lang_test4C;
	
	/** The lang test 5 C. */
	private int lang_test5C;
	
	/** The uni C. */
	private int uniC;
	
	/** The priority C. */
	private int priorityC;
	
	/** The start C. */
	private int startC;
	
	/** The motive C. */
	private int motiveC;
	
	/** The state C. */
	private int stateC;
	
	/** The institution C. */
	private int institutionC;
	
	/** The lang C. */
	private int langC;
	
	/** The country C. */
	private int countryC;
	
	/** The year C. */
	private int yearC;
	
	/** The number C. */
	private int numberC;
	
	/** The duration C. */
	private int durationC;
	
	/** The posts C. */
	private int postsC;
	
	/**
	 * Instantiates a new preferences. 
	 * Necessary for Hibernate.
	 */
	public Preferences() {}
	
	/**
	 * Instantiates a new preferences.
	 * Constructor using arrays for the index of columns.
	 *
	 * @param id the id
	 * @param studentC the student C
	 * @param requestC the request C
	 * @param univerC the univer C
	 */
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
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
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
	 * Gets the university cols.
	 *
	 * @return the university cols
	 */
	public int[] getUniversityCols() {
		int[] result = {institutionC, langC, countryC, yearC, numberC, durationC, postsC};
		return result;
	}
	
	/**
	 * Gets the student cols.
	 *
	 * @return the student cols
	 */
	public int[] getStudentCols() {
		int[] result = {nameC, dniC, languageC, othersC, noteC, valC,
				lang_test1C, lang_test2C, lang_test3C, lang_test4C, lang_test5C};
		return result;
	}
	
	/**
	 * Gets the request cols.
	 *
	 * @return the request cols
	 */
	public int[] getRequestCols() {
		int[] result = {uniC, priorityC, startC, motiveC, stateC};
		return result;
	}
	
	/**
	 * Sets the university cols.
	 *
	 * @param univerC the new university cols
	 */
	public void setUniversityCols(int[] univerC) {
		this.institutionC = univerC[0];
		this.langC = univerC[1];
		this.countryC = univerC[2];
		this.yearC = univerC[3];
		this.numberC = univerC[4];
		this.durationC = univerC[5];
		this.postsC = univerC[6];
	}
	
	/**
	 * Sets the student cols.
	 *
	 * @param studentC the new student cols
	 */
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

	/**
	 * Sets the request cols.
	 *
	 * @param requestC the new request cols
	 */
	public void setRequestCols(int[] requestC) {
		this.uniC = requestC[0];
		this.priorityC = requestC[1];
		this.startC = requestC[2];
		this.motiveC = requestC[3];
		this.stateC = requestC[4];
	}

	/**
	 * Gets the name C.
	 *
	 * @return the name C
	 */
	public int getNameC() {
		return nameC;
	}

	/**
	 * Sets the name C.
	 *
	 * @param nameC the new name C
	 */
	public void setNameC(int nameC) {
		this.nameC = nameC;
	}

	/**
	 * Gets the dni C.
	 *
	 * @return the dni C
	 */
	public int getDniC() {
		return dniC;
	}

	/**
	 * Sets the dni C.
	 *
	 * @param dniC the new dni C
	 */
	public void setDniC(int dniC) {
		this.dniC = dniC;
	}

	/**
	 * Gets the language C.
	 *
	 * @return the language C
	 */
	public int getLanguageC() {
		return languageC;
	}

	/**
	 * Sets the language C.
	 *
	 * @param languageC the new language C
	 */
	public void setLanguageC(int languageC) {
		this.languageC = languageC;
	}

	/**
	 * Gets the others C.
	 *
	 * @return the others C
	 */
	public int getOthersC() {
		return othersC;
	}

	/**
	 * Sets the others C.
	 *
	 * @param othersC the new others C
	 */
	public void setOthersC(int othersC) {
		this.othersC = othersC;
	}

	/**
	 * Gets the note C.
	 *
	 * @return the note C
	 */
	public int getNoteC() {
		return noteC;
	}

	/**
	 * Sets the note C.
	 *
	 * @param noteC the new note C
	 */
	public void setNoteC(int noteC) {
		this.noteC = noteC;
	}

	/**
	 * Gets the val C.
	 *
	 * @return the val C
	 */
	public int getValC() {
		return valC;
	}

	/**
	 * Sets the val C.
	 *
	 * @param valC the new val C
	 */
	public void setValC(int valC) {
		this.valC = valC;
	}

	/**
	 * Gets the lang test 1 C.
	 *
	 * @return the lang test 1 C
	 */
	public int getLang_test1C() {
		return lang_test1C;
	}

	/**
	 * Sets the lang test 1 C.
	 *
	 * @param lang_test1C the new lang test 1 C
	 */
	public void setLang_test1C(int lang_test1C) {
		this.lang_test1C = lang_test1C;
	}

	/**
	 * Gets the lang test 2 C.
	 *
	 * @return the lang test 2 C
	 */
	public int getLang_test2C() {
		return lang_test2C;
	}

	/**
	 * Sets the lang test 2 C.
	 *
	 * @param lang_test2C the new lang test 2 C
	 */
	public void setLang_test2C(int lang_test2C) {
		this.lang_test2C = lang_test2C;
	}

	/**
	 * Gets the lang test 3 C.
	 *
	 * @return the lang test 3 C
	 */
	public int getLang_test3C() {
		return lang_test3C;
	}

	/**
	 * Sets the lang test 3 C.
	 *
	 * @param lang_test3C the new lang test 3 C
	 */
	public void setLang_test3C(int lang_test3C) {
		this.lang_test3C = lang_test3C;
	}

	/**
	 * Gets the lang test 4 C.
	 *
	 * @return the lang test 4 C
	 */
	public int getLang_test4C() {
		return lang_test4C;
	}

	/**
	 * Sets the lang test 4 C.
	 *
	 * @param lang_test4C the new lang test 4 C
	 */
	public void setLang_test4C(int lang_test4C) {
		this.lang_test4C = lang_test4C;
	}

	/**
	 * Gets the lang test 5 C.
	 *
	 * @return the lang test 5 C
	 */
	public int getLang_test5C() {
		return lang_test5C;
	}

	/**
	 * Sets the lang test 5 C.
	 *
	 * @param lang_test5C the new lang test 5 C
	 */
	public void setLang_test5C(int lang_test5C) {
		this.lang_test5C = lang_test5C;
	}

	/**
	 * Gets the uni C.
	 *
	 * @return the uni C
	 */
	public int getUniC() {
		return uniC;
	}

	/**
	 * Sets the uni C.
	 *
	 * @param uniC the new uni C
	 */
	public void setUniC(int uniC) {
		this.uniC = uniC;
	}

	/**
	 * Gets the priority C.
	 *
	 * @return the priority C
	 */
	public int getPriorityC() {
		return priorityC;
	}

	/**
	 * Sets the priority C.
	 *
	 * @param priorityC the new priority C
	 */
	public void setPriorityC(int priorityC) {
		this.priorityC = priorityC;
	}

	/**
	 * Gets the start C.
	 *
	 * @return the start C
	 */
	public int getStartC() {
		return startC;
	}

	/**
	 * Sets the start C.
	 *
	 * @param startC the new start C
	 */
	public void setStartC(int startC) {
		this.startC = startC;
	}

	/**
	 * Gets the motive C.
	 *
	 * @return the motive C
	 */
	public int getMotiveC() {
		return motiveC;
	}

	/**
	 * Sets the motive C.
	 *
	 * @param motiveC the new motive C
	 */
	public void setMotiveC(int motiveC) {
		this.motiveC = motiveC;
	}

	/**
	 * Gets the state C.
	 *
	 * @return the state C
	 */
	public int getStateC() {
		return stateC;
	}

	/**
	 * Sets the state C.
	 *
	 * @param stateC the new state C
	 */
	public void setStateC(int stateC) {
		this.stateC = stateC;
	}

	/**
	 * Gets the institution C.
	 *
	 * @return the institution C
	 */
	public int getInstitutionC() {
		return institutionC;
	}

	/**
	 * Sets the institution C.
	 *
	 * @param institutionC the new institution C
	 */
	public void setInstitutionC(int institutionC) {
		this.institutionC = institutionC;
	}

	/**
	 * Gets the lang C.
	 *
	 * @return the lang C
	 */
	public int getLangC() {
		return langC;
	}

	/**
	 * Sets the lang C.
	 *
	 * @param langC the new lang C
	 */
	public void setLangC(int langC) {
		this.langC = langC;
	}

	/**
	 * Gets the country C.
	 *
	 * @return the country C
	 */
	public int getCountryC() {
		return countryC;
	}

	/**
	 * Sets the country C.
	 *
	 * @param countryC the new country C
	 */
	public void setCountryC(int countryC) {
		this.countryC = countryC;
	}

	/**
	 * Gets the year C.
	 *
	 * @return the year C
	 */
	public int getYearC() {
		return yearC;
	}

	/**
	 * Sets the year C.
	 *
	 * @param yearC the new year C
	 */
	public void setYearC(int yearC) {
		this.yearC = yearC;
	}

	/**
	 * Gets the number C.
	 *
	 * @return the number C
	 */
	public int getNumberC() {
		return numberC;
	}

	/**
	 * Sets the number C.
	 *
	 * @param numberC the new number C
	 */
	public void setNumberC(int numberC) {
		this.numberC = numberC;
	}

	/**
	 * Gets the duration C.
	 *
	 * @return the duration C
	 */
	public int getDurationC() {
		return durationC;
	}

	/**
	 * Sets the duration C.
	 *
	 * @param durationC the new duration C
	 */
	public void setDurationC(int durationC) {
		this.durationC = durationC;
	}

	/**
	 * Gets the posts C.
	 *
	 * @return the posts C
	 */
	public int getPostsC() {
		return postsC;
	}

	/**
	 * Sets the posts C.
	 *
	 * @param postsC the new posts C
	 */
	public void setPostsC(int postsC) {
		this.postsC = postsC;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
}
