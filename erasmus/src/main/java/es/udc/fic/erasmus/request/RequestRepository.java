package es.udc.fic.erasmus.request;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.udc.fic.erasmus.State;
import es.udc.fic.erasmus.student.Student;
import es.udc.fic.erasmus.university.University;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long>{
	
	List<Request> findByStudent(Student s);
	
	List<Request> findByUniversity(University u);
	
	List<Request> findByState(State state);
	
	@Query("select distinct s from Request r inner join r.student s where r.state = :state")
	List<Student> pendingRequests(@Param("state") State state);

}
