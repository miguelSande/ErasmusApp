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

import es.udc.fic.erasmus.Language;
import es.udc.fic.erasmus.university.University;

public class ReaderUni {
	
	private String path;
	
	private int nameC = 8;
	private int languageC = 9;
	private int countryC = 1;
	private int yearC = 2;
	private int numberC = 3;
	private int durationC = 4;
	private int postsC = 7;

	public ReaderUni(String path) {
		this.path = path;
	}
	
	private Workbook getWorkbook(FileInputStream input) throws IOException {
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
		while (headerIt.hasNext()) {
			Cell cell = headerIt.next();
			String value = cell.getStringCellValue();
			switch(value) {
			case "Relation: Country":
				countryC = cell.getColumnIndex(); 
				break;
			case "Academic year":
				yearC = cell.getColumnIndex();
				break;
			case "Number":
				numberC = cell.getColumnIndex();
				break;
			case "Total duration":
				durationC = cell.getColumnIndex();
				break;
			case "Remaining seats":
				postsC = cell.getColumnIndex();
				break;
			case "Relation: External institutions":
				nameC = cell.getColumnIndex();
				break;
			case "Idioma":
				languageC = cell.getColumnIndex();
				break;
			}
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
	
	public List<University> readUniExcel() throws IOException {
		List<University> result = new ArrayList<>();
		FileInputStream inputStream = new FileInputStream(new File(path));
		Workbook wb = getWorkbook(inputStream);
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> iterator = sheet.iterator();
		
		Row header_row = iterator.next();
		Iterator<Cell> headerIt = header_row.cellIterator();
		setColumns(headerIt);
		
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
