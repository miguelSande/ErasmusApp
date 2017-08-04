package es.udc.fic.erasmus.student;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	
	List<Student> findByNameContaining(String name);
	
	Student findByName(String name);
	
	List<Student> findByValIsNull();
	
}
