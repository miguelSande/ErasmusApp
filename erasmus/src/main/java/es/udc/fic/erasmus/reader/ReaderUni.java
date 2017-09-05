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
import es.udc.fic.erasmus.preferences.PreferencesService;
import es.udc.fic.erasmus.preferences.PreferencesString;
import es.udc.fic.erasmus.university.University;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ReaderUni {
	
	@Autowired
	private PreferencesService service;
	
	private int nameC;
	private int languageC;
	private int countryC;
	private int yearC;
	private int numberC ;
	private int durationC;
	private int postsC;
	
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
	
	private University processRow(Iterator<Cell> cellIt) {
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
				s = s.toUpperCase();
				s = s.replaceAll("\\s", "");
				s = s.replace("-", "_");
				language = Language.valueOf(s);
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
