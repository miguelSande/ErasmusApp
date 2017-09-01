package es.udc.fic.erasmus.preferences;

public class PreferencesStudentForm {

	private int name;
	private int dni;
	private int language;
	private int others;
	private int note;
	private int val;
	private int test1;
	private int test2;
	private int test3;
	private int test4;
	private int test5;
	
	private int uni;
	private int priority;
	private int start;
	private int motive;
	private int state;
	
	public int getName() {
		return name;
	}
	public void setName(int name) {
		this.name = name;
	}
	public int getDni() {
		return dni;
	}
	public void setDni(int dni) {
		this.dni = dni;
	}
	public int getLanguage() {
		return language;
	}
	public void setLanguage(int language) {
		this.language = language;
	}
	public int getOthers() {
		return others;
	}
	public void setOthers(int others) {
		this.others = others;
	}
	public int getNote() {
		return note;
	}
	public void setNote(int note) {
		this.note = note;
	}
	public int getVal() {
		return val;
	}
	public void setVal(int val) {
		this.val = val;
	}
	public int getTest1() {
		return test1;
	}
	public void setTest1(int test1) {
		this.test1 = test1;
	}
	public int getTest2() {
		return test2;
	}
	public void setTest2(int test2) {
		this.test2 = test2;
	}
	public int getTest3() {
		return test3;
	}
	public void setTest3(int test3) {
		this.test3 = test3;
	}
	public int getTest4() {
		return test4;
	}
	public void setTest4(int test4) {
		this.test4 = test4;
	}
	public int getTest5() {
		return test5;
	}
	public void setTest5(int test5) {
		this.test5 = test5;
	}
	public int getUni() {
		return uni;
	}
	public void setUni(int uni) {
		this.uni = uni;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getMotive() {
		return motive;
	}
	public void setMotive(int motive) {
		this.motive = motive;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}

	public int[] getStudentColums() {
		return new int[] {getName() - 1, getDni() - 1, getLanguage() - 1, getOthers() - 1,
				getNote() - 1, getVal() - 1, getTest1() - 1, getTest2() - 1, getTest3() - 1,
				getTest4() - 1, getTest5() - 1};
	}
	
	public int[] getRequestColums() {
		return new int[] {getUni() - 1, getPriority() - 1, getStart() - 1, getMotive() - 1, getState() - 1};
	}
	
}
