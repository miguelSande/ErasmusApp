package es.udc.fic.erasmus.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import es.udc.fic.erasmus.Language;
import es.udc.fic.erasmus.parser.Parser;
import es.udc.fic.erasmus.preferences.PreferencesService;
import es.udc.fic.erasmus.preferences.PreferencesString;
import es.udc.fic.erasmus.university.University;

/**
 * The Class ReaderUni.
 * 
 * Reads the university document.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ReaderUni {
	
	/** The service. */
	@Autowired
	private PreferencesService service;
	
	/** The name C. */
	private int nameC;
	
	/** The language C. */
	private int languageC;
	
	/** The country C. */
	private int countryC;
	
	/** The year C. */
	private int yearC;
	
	/** The number C. */
	private int numberC ;
	
	/** The duration C. */
	private int durationC;
	
	/** The posts C. */
	private int postsC;
	
	/**
	 * Initializes the columns.
	 */
	private void init() {
		int[] preferences = service.find("default").getUniversityCols();
		
		nameC = preferences[0];
		languageC = preferences[1];
		countryC = preferences[2];
		yearC = preferences[3];
		numberC = preferences[4];
		durationC = preferences[5];
		postsC = preferences[6];
	}
	
	/**
	 * Gets the workbook.
	 *
	 * @param input the input
	 * @param path the path
	 * @return the workbook
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws IllegalArgumentException Signals that the file is not an Excel.
	 */
	private Workbook getWorkbook(FileInputStream input, String path) throws IOException {
		Workbook wb;
		if (path.endsWith("xlsx")) {
			wb = new XSSFWorkbook(input);
		} else if ((path.endsWith("xls")) || (path.endsWith("XLS"))) {
			wb = new HSSFWorkbook(input);
		} else {
			throw new IllegalArgumentException("The file is not Excel type");
		}
		return wb;
	}
	
	/**
	 * Gets the cell value.
	 *
	 * @param cell the cell
	 * @return the cell value
	 */
	@SuppressWarnings("deprecation")
	private Object getCellValue(Cell cell) {
		switch(cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();
		case Cell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue();
		}
		
		return null;
	}
	
	/**
	 * Sets the columns by the string preferences.
	 *
	 * @param headerIt the new columns
	 */
	private void setColumns(Iterator<Cell> headerIt) {
		PreferencesString pre = service.findString("default");
		while (headerIt.hasNext()) {
			Cell cell = headerIt.next();
			String value = cell.getStringCellValue();
			if (value.equals(pre.getInstitution()))
				nameC = cell.getColumnIndex();
			if (value.equals(pre.getCountry()))
				countryC = cell.getColumnIndex();
			if (value.equals(pre.getLang()))
				languageC = cell.getColumnIndex();
			if (value.equals(pre.getYear()))
				yearC = cell.getColumnIndex();
			if (value.equals(pre.getNumber()))
				numberC = cell.getColumnIndex();
			if (value.equals(pre.getDuration()))
				durationC = cell.getColumnIndex();
			if (value.equals(pre.getPosts()))
				postsC = cell.getColumnIndex();
		}
	}
	
	/**
	 * Process row. reads the row to get the university information.
	 *
	 * @param cellIt the cell it
	 * @return the university
	 */
	private University processRow(Iterator<Cell> cellIt) {
		Parser parser = new Parser();
		String name = null, country = null, year = null;
		Long number = null, duration = null, posts = null;
		Language language = null;
		Double aux;
		while (cellIt.hasNext()) {
			Cell cell = cellIt.next();
			int column = cell.getColumnIndex();
			if (column == nameC) 
				name = (String) getCellValue(cell);
			if (column == countryC)
				country = (String) getCellValue(cell);
			if (column == yearC)
				year = getCellValue(cell).toString();
			if (column == languageC) {
				String s = (String) getCellValue(cell);
				language = parser.parse(s);
			}
			if (column == numberC) {
				aux = (Double) getCellValue(cell);
				number = aux.longValue();
			}
			if (column == durationC) {
				String s = getCellValue(cell).toString();
				aux = Double.parseDouble(s.replace(",", "."));
				duration = aux.longValue();
			}
			if (column == postsC) {				
				aux = (Double) getCellValue(cell);
				posts = aux.longValue();
			}
		}
		return new University(name, language, year, (duration/number), country, posts);
	}
	
	/**
	 * Read uni excel. process the document searching for all the universities.
	 *
	 * @param file the file
	 * @param name the name
	 * @return the list
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public List<University> readUniExcel(File file,String name) throws IOException {
		List<University> result = new ArrayList<>();
		int aux = 0, header = 0;
		FileInputStream inputStream = new FileInputStream(file);
		Workbook wb = getWorkbook(inputStream, name);
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		
		for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
			if (sheet.getRow(i).getPhysicalNumberOfCells() > aux) {
				aux = sheet.getRow(i).getPhysicalNumberOfCells();
				header = i;
			}
		}			
		for (int i = 0; i < header || !iterator.hasNext(); i++) {
			iterator.next();
		}			
		if (iterator.hasNext()) {
			Row header_row = iterator.next();
			Iterator<Cell> headerIt = header_row.cellIterator();
			if (service.find("default").isActive())
				init();
			if (service.findString("default").isActive()) 	
				setColumns(headerIt);
		}	
		
		while (iterator.hasNext()) {
			Row row = iterator.next();
			Iterator<Cell> cellIt = row.cellIterator();
			result.add(processRow(cellIt));
		}
		
		wb.close();
		inputStream.close();
		return result;
	}

}
