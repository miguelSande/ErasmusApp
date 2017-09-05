package es.udc.fic.erasmus.preferences;

public class PreferencesStringStudentForm {
	
	private String name;
	private String dni;
	private String language;
	private String others;
	private String note;
	private String val;
	private String test1;
	private String test2;
	private String test3;
	private String test4;
	private String test5;
	
	private String uni;
	private String priority;
	private String start;
	private String motive;
	private String state;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
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
	public String getTest1() {
		return test1;
	}
	public void setTest1(String test1) {
		this.test1 = test1;
	}
	public String getTest2() {
		return test2;
	}
	public void setTest2(String test2) {
		this.test2 = test2;
	}
	public String getTest3() {
		return test3;
	}
	public void setTest3(String test3) {
		this.test3 = test3;
	}
	public String getTest4() {
		return test4;
	}
	public void setTest4(String test4) {
		this.test4 = test4;
	}
	public String getTest5() {
		return test5;
	}
	public void setTest5(String test5) {
		this.test5 = test5;
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

	public String[] getStudentColums() {
		return new String[] {getName(), getDni(), getLanguage(), getOthers(),
				getNote(), getVal(), getTest1(), getTest2(), getTest3(),
				getTest4(), getTest5()};
	}
	
	public String[] getRequestColums() {
		return new String[] {getUni(), getPriority(), getStart(), getMotive(), getState()};
	}

}
