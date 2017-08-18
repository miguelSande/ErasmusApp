package es.udc.fic.erasmus.student;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="student")
public class Student implements Serializable {
	
	@Id
	@GeneratedValue
	private Long idS;
	
	private String name;
	
	private Double note;
	
	private Double val;
	
	private String others;
	
	private String language;
	
	private Boolean lang_test;

	public Student() {}
	
	public Student(String name, Double note, String others, String language, Boolean lang_test) {
		this.name = name;
		this.note = note;
		this.others = others;
		this.language = language;
		this.lang_test = lang_test;
		this.val = null;
	}

	public Long getIdS() {
		return idS;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getNote() {
		return note;
	}

	public void setNote(Double note) {
		this.note = note;
	}

	public Double getVal() {
		return val;
	}

	public void setVal(Double val) {
		this.val = val;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Boolean getLang_test() {
		return lang_test;
	}

	public void setLang_test(Boolean lang_test) {
		this.lang_test = lang_test;
	}
}
