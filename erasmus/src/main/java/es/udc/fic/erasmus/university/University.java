package es.udc.fic.erasmus.university;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import es.udc.fic.erasmus.Language;

@SuppressWarnings("serial")
@Entity
@Table(name="university")
public class University implements Serializable {
	
	@Id
	@GeneratedValue
	private Long un_id;
	
	private String name;
	
	private Language language;
	
	private String year;
	
	private Long duration;
	
	private String country;
	
	private Long posts;
	
	public University() {}

	public University(String name, Language language, String year, Long duration, String country, Long posts) {
		this.name = name;
		this.language = language;
		this.year = year;
		this.duration = duration;
		this.country = country;
		this.posts = posts;
	}

	public Long getUn_id() {
		return un_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Long getPosts() {
		return posts;
	}

	public void setPosts(Long posts) {
		this.posts = posts;
	}

}
