package es.udc.fic.erasmus.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import es.udc.fic.erasmus.Language;
import es.udc.fic.erasmus.config.WebAppConfigurationAware;

public class ParserTest extends WebAppConfigurationAware {
	
	private Parser parser = new Parser();
	
	@Test
	public void parserTest() {
		assertEquals(Language.EN_A2, parser.parse("EN - SDSA - A2"));
		assertEquals(Language.FR_B1, parser.parse("fr-test-B1"));
		assertEquals(Language.GE_B1, parser.parse("ge-test-b1"));
		assertEquals(Language.IT_B2, parser.parse("IT - HSHS-B2"));
		assertEquals(Language.PT_B2, parser.parse("PT- nsns- b2"));
		assertEquals(null, parser.parse("PT - prueba"));
		assertEquals(null, parser.parse("en-b1,pt-a2"));
		assertEquals(null, parser.parse(null));
	}
	
	@Test
	public void parsingTestTest() {
		assertFalse(parser.isTestEnglish(null));
		assertFalse(parser.isTestEnglish("EN-b2,PT-prueba"));
		assertTrue(parser.isTestEnglish("en-prueba"));
		assertTrue(parser.isTestEnglish("en-prueba,fr-b2"));
	}
	

}
