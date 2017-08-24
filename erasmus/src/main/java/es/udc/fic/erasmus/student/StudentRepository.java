package es.udc.fic.erasmus.student;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	
	List<Student> findByNameContaining(String name);
	
	Student findByName(String name);
	
	Student findByDni(String dni);
	
	List<Student> findByValIsNull();
	
	@Query("select count(s) > 0 from Student s where s.dni = :dni")
	boolean exists(@Param("dni") String dni);
	
}
