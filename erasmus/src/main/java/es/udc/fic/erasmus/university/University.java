package es.udc.fic.erasmus.university;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import es.udc.fic.erasmus.Language;

/**
 * The Class University.
 */
@SuppressWarnings("serial")
@Entity
@Table(name="university")
public class University implements Serializable {
	
	/** The un id. */
	@Id
	@GeneratedValue
	private Long un_id;
	
	/** The name. */
	private String name;
	
	/** The language. */
	private Language language;
	
	/** The year. */
	private String year;
	
	/** The duration. */
	private Long duration;
	
	/** The country. */
	private String country;
	
	/** The posts. */
	private Long posts;
	
	/** The empty. */
	private boolean empty;
	
	/** The waiting. */
	private int waiting;
	
	/**
	 * Instantiates a new university.
	 * Necessary for Hibernate.
	 */
	public University() {}

	/**
	 * Instantiates a new university.
	 *
	 * @param name the name
	 * @param language the language
	 * @param year the year
	 * @param duration the duration
	 * @param country the country
	 * @param posts the posts
	 */
	public University(String name, Language language, String year, Long duration, String country, Long posts) {
		this.name = name;
		this.language = language;
		this.year = year;
		this.duration = duration;
		this.country = country;
		this.posts = posts;
		this.empty = (posts == 0);
		this.waiting = 0;
	}

	/**
	 * Gets the un id.
	 *
	 * @return the un id
	 */
	public Long getUn_id() {
		return un_id;
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
	public Language getLanguage() {
		return language;
	}

	/**
	 * Sets the language.
	 *
	 * @param language the new language
	 */
	public void setLanguage(Language language) {
		this.language = language;
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
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public Long getDuration() {
		return duration;
	}

	/**
	 * Sets the duration.
	 *
	 * @param duration the new duration
	 */
	public void setDuration(Long duration) {
		this.duration = duration;
	}

	/**
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty() {
		return empty;
	}

	/**
	 * Sets the empty.
	 *
	 * @param empty the new empty
	 */
	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	/**
	 * Gets the waiting.
	 *
	 * @return the waiting
	 */
	public int getWaiting() {
		return waiting;
	}

	/**
	 * Sets the waiting.
	 *
	 * @param waiting the new waiting
	 */
	public void setWaiting(int waiting) {
		this.waiting = waiting;
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
	 * Gets the posts.
	 *
	 * @return the posts
	 */
	public Long getPosts() {
		return posts;
	}

	/**
	 * Sets the posts.
	 *
	 * @param posts the new posts
	 */
	public void setPosts(Long posts) {
		this.posts = posts;
	}

}
