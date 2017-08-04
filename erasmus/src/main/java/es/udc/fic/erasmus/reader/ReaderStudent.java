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

import es.udc.fic.erasmus.request.Request;
import es.udc.fic.erasmus.student.Student;
import es.udc.fic.erasmus.student.StudentService;
import es.udc.fic.erasmus.university.UniversityRepository;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ReaderStudent {	
	
	@Autowired
	private UniversityRepository uniRepo;
	
	@Autowired
	private StudentService studentService;
	
	private int nameC = 3;
	private int languageC = 12;
	private int othersC = 20;
	private int noteC = 19;
	private int valC = 21;
	private int lang_test1C = 13;
	private int lang_test2C = 14;
	private int lang_test3C = 15;
	private int lang_test4C = 16;
	private int lang_test5C = 17;
	
	private int uniC = 7;
	private int priorityC = 8;
	private int startC = 10;
	private int motiveC = 22;
	private int stateC = 11;

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
		
		private Boolean checkLanTest(Double lan1, Double lan2, Double lan3, Double lan4, Double lan5) {
			Double[] results = {lan1,lan2,lan3,lan4,lan5};
			boolean result = false;
			for (Double d: results) {
				if (d != null && d >= 5)
					result = true;					
			}
			return result;
		}
		
		private void setColumns(Iterator<Cell> headerIt) {
			while (headerIt.hasNext()) {
				Cell cell = headerIt.next();
				String value = cell.getStringCellValue();
				switch(value) {
				case "Nombre":
					nameC = cell.getColumnIndex();
					break;
				case "Institución":
					uniC = cell.getColumnIndex();
					break;
				case "Orden":
					priorityC = cell.getColumnIndex();
					break;
				case "Inicio (semestre)":
					startC = cell.getColumnIndex();
					break;
				case "Estado de solicitud - interno":
					stateC = cell.getColumnIndex();
					break;
				case "Certificación de idiomas":
					languageC = cell.getColumnIndex();
					break;
				case "NOTA MEDIA":
					noteC = cell.getColumnIndex();
					break;
				case "OUTROS MERITOS":
					othersC = cell.getColumnIndex();
					break;
				case "Valoración":
					valC = cell.getColumnIndex();
					break;
				case "MOTIVOS DE REXEITAMENTO":
					motiveC = cell.getColumnIndex();
					break;
				}
				if (value.toUpperCase().contains("ENGLISH"))
					lang_test1C = cell.getColumnIndex();
				if (value.toUpperCase().contains("FRANCÉS"))
					lang_test2C = cell.getColumnIndex();
				if (value.toUpperCase().contains("ALEMÁN"))
					lang_test3C = cell.getColumnIndex();
				if (value.toUpperCase().contains("ITALIANO"))
					lang_test4C = cell.getColumnIndex();
				if (value.toUpperCase().contains("PORTUGUÉS"))
					lang_test5C = cell.getColumnIndex();
			}
		}
		
		private Student processStudentRow(Iterator<Cell> cellIt) {
			String name = null, others = null, language = null;
			Long priority = null;
			Double aux, note = null, lan1 = null,lan2=null,lan3=null,lan4=null,lan5=null;
			while (cellIt.hasNext()) {
				Cell cell = cellIt.next();
				int column = cell.getColumnIndex();
				if (column == nameC) 
					name = (String) getCellValue(cell);
				if (column == othersC)
					others = (String) getCellValue(cell);
				if (column == languageC)
					language = (String) getCellValue(cell);
				if (column == lang_test1C)
					lan1 = (Double) getCellValue(cell);
				if (column == lang_test2C)
					lan2 = (Double) getCellValue(cell);
				if (column == lang_test3C)
					lan3 = (Double) getCellValue(cell);
				if (column == lang_test4C)
					lan4 = (Double) getCellValue(cell);
				if (column == lang_test5C)
					lan5 = (Double) getCellValue(cell);
				if (column == noteC) {
					note = (Double) getCellValue(cell);
				}
				if (column == priorityC) {
					aux = (Double) getCellValue(cell);
					if (aux == null)
						return null;
					priority = aux.longValue();
				}
			}
			if (priority != null && priority == 1)
				return new Student(name, note, others, language, checkLanTest(lan1,lan2,lan3,lan4,lan5));
			else
				return null;
		}
		
		private Request processRequestRow(Iterator<Cell> cellIt) {
			String name = null, university = null, start = null;
			Long priority = null;
			Double aux;
			while (cellIt.hasNext()) {
				Cell cell = cellIt.next();
				int column = cell.getColumnIndex();
				if (column == nameC) 
					name = (String) getCellValue(cell);
				if (column == uniC) 
					university = (String) getCellValue(cell);
				if (column == startC)
					start = (String) getCellValue(cell);
				if (column == priorityC) {
					aux = (Double) getCellValue(cell);
					//solucion problema de parada
					if (aux == null)
						return null;
					priority = aux.longValue();
				}
			}
			if (name == null) //nueva condicion de parada ???
				return null;
			return new Request(studentService.find(name), uniRepo.findByName(university), priority, start);
		}
		
		public List<Student> readStudentExcel(String path) throws IOException {
			List<Student> result = new ArrayList<>();
			int header = 0, aux = 0;
			FileInputStream inputStream = new FileInputStream(new File(path));
			Workbook wb = getWorkbook(inputStream, path);
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
				setColumns(headerIt);
			}
			
			while (iterator.hasNext()) {
				Row row = iterator.next();
				Iterator<Cell> cellIt = row.cellIterator();
				Student st = processStudentRow(cellIt);
				if (st != null)
					result.add(st);
			}
			
			wb.close();
			inputStream.close();
			return result;
		}
		
		public List<Request> readRequestExcel(String path) throws IOException {
			List<Request> result = new ArrayList<>();
			int header = 0, aux = 0;
			FileInputStream inputStream = new FileInputStream(new File(path));
			Workbook wb = getWorkbook(inputStream, path);
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
				setColumns(headerIt);
			}
			
			while (iterator.hasNext()) {
				Row row = iterator.next();
				Iterator<Cell> cellIt = row.cellIterator();
				Request rq = processRequestRow(cellIt); 
				if (rq != null)
				result.add(rq);
			}
			
			wb.close();
			inputStream.close();
			return result;
		}

}