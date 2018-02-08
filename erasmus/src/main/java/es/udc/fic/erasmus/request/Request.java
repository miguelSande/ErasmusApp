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

/**
 * The Class Request.
 */
@SuppressWarnings("serial")
@Entity
@Table(name="request")
public class Request implements Serializable {
	
	/** The re id. */
	@Id
	@GeneratedValue
	private Long re_id;
	
	/** The student. */
	@ManyToOne(fetch=FetchType.EAGER, optional = false)
	@JoinColumn(name="idS")
	private Student student;
	
	/** The university. */
	@ManyToOne(fetch=FetchType.EAGER, optional = false)
	@JoinColumn(name="un_id")
	private University university;
	
	/** The priority. */
	private Long priority;
	
	/** The start date. */
	private String startDate;
	
	/** The motive. */
	private String motive;
	
	/** The state. */
	@Enumerated(EnumType.STRING)
	private State state;
	
	/** The waiting num.
	 *  Used to asign a waiting list to the student */
	private int waitingNum;

	/**
	 * Instantiates a new request.
	 * Necessary for Hibernate.
	 */
	public Request() {}
	
	/**
	 * Instantiates a new request.
	 *
	 * @param student the student
	 * @param university the university
	 * @param priority the priority
	 * @param startDate the start date
	 */
	public Request(Student student, University university, Long priority, String startDate) {
		this.student = student;
		this.university = university;
		this.priority = priority;
		this.startDate = startDate;
		this.state = State.PENDING;
		this.waitingNum = 0;
	}

	/**
	 * Gets the re id.
	 *
	 * @return the re id
	 */
	public Long getRe_id() {
		return re_id;
	}

	/**
	 * Gets the student.
	 *
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * Sets the student.
	 *
	 * @param student the new student
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/**
	 * Gets the university.
	 *
	 * @return the university
	 */
	public University getUniversity() {
		return university;
	}

	/**
	 * Sets the university.
	 *
	 * @param university the new university
	 */
	public void setUniversity(University university) {
		this.university = university;
	}

	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 */
	public Long getPriority() {
		return priority;
	}

	/**
	 * Sets the priority.
	 *
	 * @param priority the new priority
	 */
	public void setPriority(Long priority) {
		this.priority = priority;
	}

	/**
	 * Gets the start date.
	 *
	 * @return the start date
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * Sets the start date.
	 *
	 * @param startDate the new start date
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * Gets the motive.
	 *
	 * @return the motive
	 */
	public String getMotive() {
		return motive;
	}

	/**
	 * Sets the motive.
	 *
	 * @param motive the new motive
	 */
	public void setMotive(String motive) {
		this.motive = motive;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * Gets the waiting num.
	 *
	 * @return the waiting num
	 */
	public int getWaitingNum() {
		return waitingNum;
	}

	/**
	 * Sets the waiting num.
	 *
	 * @param waitingNum the new waiting num
	 */
	public void setWaitingNum(int waitingNum) {
		this.waitingNum = waitingNum;
	}

}
