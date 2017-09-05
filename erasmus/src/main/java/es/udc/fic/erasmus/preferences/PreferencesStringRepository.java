package es.udc.fic.erasmus.preferences;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferencesStringRepository extends JpaRepository<PreferencesString, String> {

}
