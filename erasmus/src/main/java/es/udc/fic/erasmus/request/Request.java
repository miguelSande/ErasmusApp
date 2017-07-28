package es.udc.fic.erasmus.request;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import es.udc.fic.erasmus.State;
import es.udc.fic.erasmus.student.Student;
import es.udc.fic.erasmus.university.University;

@SuppressWarnings("serial")
@Entity
@Table(name="request")
public class Request implements Serializable {
	
	@Id
	@GeneratedValue
	private Long re_id;
	
	@ManyToOne(fetch=FetchType.EAGER, optional = false)
	@JoinColumn(name="idS")
	private Student student;
	
	@ManyToOne(fetch=FetchType.EAGER, optional = false)
	@JoinColumn(name="un_id")
	private University university;
	
	private Long priority;
	
	private String startDate;
	
	private String motive;
	
	@Enumerated(EnumType.STRING)
	private State state;

	public Request(Student student, University university, Long priority, String startDate) {
		this.student = student;
		this.university = university;
		this.priority = priority;
		this.startDate = startDate;
		this.state = State.PENDING;
	}

	public Long getRe_id() {
		return re_id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public University getUniversity() {
		return university;
	}

	public void setUniversity(University university) {
		this.university = university;
	}

	public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getMotive() {
		return motive;
	}

	public void setMotive(String motive) {
		this.motive = motive;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

}
