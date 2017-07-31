package es.udc.fic.erasmus.reader;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fic.erasmus.Language;
import es.udc.fic.erasmus.config.WebAppConfigurationAware;
import es.udc.fic.erasmus.university.University;

@Transactional
public class ReaderTest extends WebAppConfigurationAware {
	
	@Test
	public void testReaderUni() throws IOException {
		ReaderUni reader = new ReaderUni(this.getClass().getResource("/destinos.XLS").getFile());
		List<University> expected = reader.readUniExcel();
		assertEquals(18, expected.size());
		University sample = expected.get(0);
		assertEquals("Portugal", sample.getCountry());
		assertEquals("2017/18", sample.getYear());
		assertEquals(10, sample.getDuration().longValue());
		assertEquals("Universidade Lusíada de Lisboa", sample.getName());
		assertEquals(4, sample.getPosts().longValue());
		assertEquals(Language.PT_A2, sample.getLanguage());
	}

}
