package es.udc.fic.erasmus.university;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
		
	University findByName(String name);
	
	@Query("select count(u) > 0 from University u where u.name = :name")
	boolean exists(@Param("name") String name);

}
