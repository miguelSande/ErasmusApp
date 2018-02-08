package es.udc.fic.erasmus.student;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class Student.
 */
@SuppressWarnings("serial")
@Entity
@Table(name="student")
public class Student implements Serializable {
	
	/** The dni. */
	@Id
	private String dni;
	
	/** The name. */
	private String name;
	
	/** The note. */
	private Double note;
	
	/** The val. */
	private Double val;
	
	/** The others. */
	private String others;
	
	/** The language. */
	private String language;
	
	/** The lang test en. */
	private boolean lang_test_en;
	
	/** The lang test pt. */
	private boolean lang_test_pt;
	
	/** The lang test ge. */
	private boolean lang_test_ge;
	
	/** The lang test it. */
	private boolean lang_test_it;
	
	/** The lang test fr. */
	private boolean lang_test_fr;

	/**
	 * Instantiates a new student.
	 * Necessary for Hibernate.
	 */
	public Student() {}

	/**
	 * Instantiates a new student.
	 *
	 * @param dni the dni
	 * @param name the name
	 * @param note the note
	 * @param others the others
	 * @param language the language
	 * @param lang_test_en the lang test en
	 * @param lang_test_pt the lang test pt
	 * @param lang_test_ge the lang test ge
	 * @param lang_test_it the lang test it
	 * @param lang_test_fr the lang test fr
	 */
	public Student(String dni, String name, Double note, String others, String language,
			boolean lang_test_en, boolean lang_test_pt, boolean lang_test_ge, boolean lang_test_it,
			boolean lang_test_fr) {
		this.dni = dni;
		this.name = name;
		this.note = note;
		this.val = null;
		this.others = others;
		this.language = language;
		this.lang_test_en = lang_test_en;
		this.lang_test_pt = lang_test_pt;
		this.lang_test_ge = lang_test_ge;
		this.lang_test_it = lang_test_it;
		this.lang_test_fr = lang_test_fr;
	}

	/**
	 * Gets the dni.
	 *
	 * @return the dni
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * Sets the dni.
	 *
	 * @param dni the new dni
	 */
	public void setDni(String dni) {
		this.dni = dni;
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
	 * Gets the note.
	 *
	 * @return the note
	 */
	public Double getNote() {
		return note;
	}

	/**
	 * Sets the note.
	 *
	 * @param note the new note
	 */
	public void setNote(Double note) {
		this.note = note;
	}

	/**
	 * Gets the val.
	 *
	 * @return the val
	 */
	public Double getVal() {
		return val;
	}

	/**
	 * Sets the val.
	 *
	 * @param val the new val
	 */
	public void setVal(Double val) {
		this.val = val;
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
	 * Checks if is lang test en.
	 *
	 * @return true, if is lang test en
	 */
	public boolean isLang_test_en() {
		return lang_test_en;
	}

	/**
	 * Sets the lang test en.
	 *
	 * @param lang_test_en the new lang test en
	 */
	public void setLang_test_en(boolean lang_test_en) {
		this.lang_test_en = lang_test_en;
	}

	/**
	 * Checks if is lang test pt.
	 *
	 * @return true, if is lang test pt
	 */
	public boolean isLang_test_pt() {
		return lang_test_pt;
	}

	/**
	 * Sets the lang test pt.
	 *
	 * @param lang_test_pt the new lang test pt
	 */
	public void setLang_test_pt(boolean lang_test_pt) {
		this.lang_test_pt = lang_test_pt;
	}

	/**
	 * Checks if is lang test ge.
	 *
	 * @return true, if is lang test ge
	 */
	public boolean isLang_test_ge() {
		return lang_test_ge;
	}

	/**
	 * Sets the lang test ge.
	 *
	 * @param lang_test_ge the new lang test ge
	 */
	public void setLang_test_ge(boolean lang_test_ge) {
		this.lang_test_ge = lang_test_ge;
	}

	/**
	 * Checks if is lang test it.
	 *
	 * @return true, if is lang test it
	 */
	public boolean isLang_test_it() {
		return lang_test_it;
	}

	/**
	 * Sets the lang test it.
	 *
	 * @param lang_test_it the new lang test it
	 */
	public void setLang_test_it(boolean lang_test_it) {
		this.lang_test_it = lang_test_it;
	}

	/**
	 * Checks if is lang test fr.
	 *
	 * @return true, if is lang test fr
	 */
	public boolean isLang_test_fr() {
		return lang_test_fr;
	}

	/**
	 * Sets the lang test fr.
	 *
	 * @param lang_test_fr the new lang test fr
	 */
	public void setLang_test_fr(boolean lang_test_fr) {
		this.lang_test_fr = lang_test_fr;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		result = prime * result + (lang_test_en ? 1231 : 1237);
		result = prime * result + (lang_test_fr ? 1231 : 1237);
		result = prime * result + (lang_test_ge ? 1231 : 1237);
		result = prime * result + (lang_test_it ? 1231 : 1237);
		result = prime * result + (lang_test_pt ? 1231 : 1237);
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((others == null) ? 0 : others.hashCode());
		result = prime * result + ((val == null) ? 0 : val.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		if (lang_test_en != other.lang_test_en)
			return false;
		if (lang_test_fr != other.lang_test_fr)
			return false;
		if (lang_test_ge != other.lang_test_ge)
			return false;
		if (lang_test_it != other.lang_test_it)
			return false;
		if (lang_test_pt != other.lang_test_pt)
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (others == null) {
			if (other.others != null)
				return false;
		} else if (!others.equals(other.others))
			return false;
		if (val == null) {
			if (other.val != null)
				return false;
		} else if (!val.equals(other.val))
			return false;
		return true;
	}
}
