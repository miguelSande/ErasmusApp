package es.udc.fic.erasmus.university;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fic.erasmus.Language;
import es.udc.fic.erasmus.config.WebAppConfigurationAware;

@Transactional
public class UniversityServiceTest extends WebAppConfigurationAware {
	
	@Autowired
	private UniversityService uniService;
	
	@Autowired
	private UniversityRepository uniRepo;
	
	@Test
	public void testCreateUni() {
		University uni = new University("universidad de prueba", Language.EN_B2, "2018"
				,(long) 10, "españa",(long) 6);
		uni = uniService.create(uni);
		University expected = uniRepo.findOne(uni.getUn_id());
		assertEquals(expected,uni);
		uniRepo.delete(uni);
	}
	
	@Test
	public void testUpdateUni() {
		University uni = new University("universidad de prueba", Language.EN_B2, "2018"
				,(long) 10, "españa",(long) 6);
		uni = uniService.create(uni);
		University uni2 = new University("universidad de prueba", Language.EN_B2, "2018"
				,(long) 10, "españa",(long) 10);
		uni2 = uniService.create(uni2);
		University expected = uniRepo.findOne(uni.getUn_id());
		assertEquals(1, uniRepo.findAll().size());
		assertEquals(10, expected.getPosts().longValue());
		uniRepo.delete(uni);
	}

}
