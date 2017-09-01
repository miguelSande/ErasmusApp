package es.udc.fic.erasmus.preferences;

public class PreferencesUniForm {
	
	private int name;
	private int language;
	private int country;
	private int year;
	private int number;
	private int duration;
	private int posts;
	public int getName() {
		return name;
	}
	public void setName(int name) {
		this.name = name;
	}
	public int getLanguage() {
		return language;
	}
	public void setLanguage(int language) {
		this.language = language;
	}
	public int getCountry() {
		return country;
	}
	public void setCountry(int country) {
		this.country = country;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getPosts() {
		return posts;
	}
	public void setPosts(int posts) {
		this.posts = posts;
	}
	
	public int[] getColums() {
		return new int[] {getName() - 1, getLanguage() - 1, getCountry() - 1,
				getYear() - 1, getNumber() - 1, getDuration() - 1, getPosts() - 1};
	}

}
