package es.udc.fic.erasmus.preferences;

public class PreferencesStringUniForm {
	
	private String name;
	private String language;
	private String country;
	private String year;
	private String number;
	private String duration;
	private String posts;
	
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
	
	public String[] getColums() {
		return new String[] {getName(), getLanguage(), getCountry(),
				getYear(), getNumber(), getDuration(), getPosts()};
	}
}
