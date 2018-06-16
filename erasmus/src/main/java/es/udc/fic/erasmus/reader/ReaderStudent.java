package es.udc.fic.erasmus.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import es.udc.fic.erasmus.State;
import es.udc.fic.erasmus.error.UniversityNotFoundException;
import es.udc.fic.erasmus.parser.Parser;
import es.udc.fic.erasmus.preferences.PreferencesService;
import es.udc.fic.erasmus.preferences.PreferencesString;
import es.udc.fic.erasmus.request.Request;
import es.udc.fic.erasmus.request.RequestService;
import es.udc.fic.erasmus.student.Student;
import es.udc.fic.erasmus.student.StudentService;
import es.udc.fic.erasmus.university.UniversityRepository;

/**
 * The Class ReaderStudent.
 * 
 * Reads the student document and writes the result of the process.
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ReaderStudent {	
	
	/** The uni repo. */
	@Autowired
	private UniversityRepository uniRepo;
	
	/** The rq service. */
	@Autowired
	private RequestService rqService;
	
	/** The student service. */
	@Autowired
	private StudentService studentService;
	
	/** The service. */
	@Autowired
	private PreferencesService service;
		
	/** The name C. */
	private int nameC;
	
	/** The dni C. */
	private int dniC;
	
	/** The language C. */
	private int languageC;
	
	/** The others C. */
	private int othersC;
	
	/** The note C. */
	private int noteC;
	
	/** The val C. */
	private int valC;
	
	/** The lang test 1 C. */
	private int lang_testENC;
	
	/** The lang test 2 C. */
	private int lang_testFRC;
	
	/** The lang test 3 C. */
	private int lang_testGEC;
	
	/** The lang test 4 C. */
	private int lang_testITC;
	
	/** The lang test 5 C. */
	private int lang_testPTC;
	
	/** The uni C. */
	private int uniC;
	
	/** The priority C. */
	private int priorityC;
	
	/** The start C. */
	private int startC;
	
	/** The motive C. */
	private int motiveC;
	
	/** The state C. */
	private int stateC;
	
	/**
	 * Initialize the index for the needed columns.
	 */
	private void init() {
		int[] preferencesR = service.find("default").getRequestCols();
		int[] preferencesS = service.find("default").getStudentCols();
		
		nameC = preferencesS[0];
		dniC = preferencesS[1];
		languageC = preferencesS[2];
		othersC = preferencesS[3];
		noteC = preferencesS[4];
		valC = preferencesS[5];
		lang_testENC = preferencesS[6];
		lang_testFRC = preferencesS[7];
		lang_testGEC = preferencesS[8];
		lang_testITC = preferencesS[9];
		lang_testPTC = preferencesS[10];
		
		uniC = preferencesR[0];
		priorityC = preferencesR[1];
		startC = preferencesR[2];
		motiveC = preferencesR[3];
		stateC = preferencesR[4];
	}

	 /**
 	 * Gets the workbook.
 	 *
 	 * @param input the input
 	 * @param path the path
 	 * @return the workbook
 	 * @throws IOException Signals that an I/O exception has occurred.
 	 * @throws IllegalArgumentException Signals that the final is not a excel.
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
		 * Sets the columns with the preferences string.
		 *
		 * @param headerIt the new columns
		 */
		private void setColumns(Iterator<Cell> headerIt) {
			PreferencesString pre = service.findString("default");
			while (headerIt.hasNext()) {
				Cell cell = headerIt.next();
				String value = cell.getStringCellValue();
				
				if (value.equals(pre.getName()))
					nameC = cell.getColumnIndex();
				if (value.equals(pre.getDni()))
					dniC = cell.getColumnIndex();
				if (value.equals(pre.getUni()))
					uniC = cell.getColumnIndex();
				if (value.equals(pre.getPriority()))
					priorityC = cell.getColumnIndex();
				if (value.equals(pre.getStart()))
					startC = cell.getColumnIndex();
				if (value.equals(pre.getState()))
					stateC = cell.getColumnIndex();
				if (value.equals(pre.getLanguage()))
					languageC = cell.getColumnIndex();
				if (value.equals(pre.getNote()))
					noteC = cell.getColumnIndex();
				if (value.equals(pre.getOthers()))
					othersC = cell.getColumnIndex();
				if (value.equals(pre.getVal()))
					valC = cell.getColumnIndex();
				if (value.equals(pre.getMotive()))
					motiveC = cell.getColumnIndex();				
				if (value.toUpperCase().contains("INGLES") || value.toUpperCase().contains("ENGLISH"))
					lang_testENC = cell.getColumnIndex();
				if (value.toUpperCase().contains("FRANCÉS") || value.toUpperCase().contains("FRENCH"))
					lang_testFRC = cell.getColumnIndex();
				if (value.toUpperCase().contains("ALEMÁN") || value.toUpperCase().contains("GERMAN"))
					lang_testGEC = cell.getColumnIndex();
				if (value.toUpperCase().contains("ITALIANO") || value.toUpperCase().contains("ITALY"))
					lang_testITC = cell.getColumnIndex();
				if (value.toUpperCase().contains("PORTUGUÉS") || value.toUpperCase().contains("PORTUGUESS"))
					lang_testPTC = cell.getColumnIndex();
			}
		}
		
		/**
		 * Process student row. reads a row to get the student information.
		 *
		 * @param cellIt the cell it
		 * @return the student
		 */
		private Student processStudentRow(Iterator<Cell> cellIt) {
			String dni = null, name = null, others = null, language = null;
			Long priority = null;
			Double aux, note = null, lanEn = null,lanFr=null,lanGe=null,lanIt=null,lanPt=null;
			while (cellIt.hasNext()) {
				Cell cell = cellIt.next();
				int column = cell.getColumnIndex();
				if (column == nameC) 
					name = (String) getCellValue(cell);
				if (column == dniC)
					dni = (String) getCellValue(cell);
				if (column == othersC)
					others = (String) getCellValue(cell);
				if (column == languageC)
					language = (String) getCellValue(cell);
				if (column == lang_testENC)
					lanEn = (Double) getCellValue(cell);
				if (column == lang_testFRC)
					lanFr = (Double) getCellValue(cell);
				if (column == lang_testGEC)
					lanGe = (Double) getCellValue(cell);
				if (column == lang_testITC)
					lanIt = (Double) getCellValue(cell);
				if (column == lang_testPTC)
					lanPt = (Double) getCellValue(cell);
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
			if (priority != null && priority == 1) {
				Parser parser = new Parser();
				return new Student(dni, name, note, others, language, (parser.isTestEnglish(language) || (lanEn != null && lanEn >= 5)),
						(parser.isTestPortuguess(language) || (lanPt != null && lanPt >= 5)),
						(parser.isTestGerman(language) || (lanGe != null && lanGe >= 5)),
						(parser.isTestItalian(language) || (lanIt != null && lanIt >= 5)),
						(parser.isTestFrench(language) || (lanFr != null && lanFr >= 5)));
			}
			else
				return null;
		}
		
		/**
		 * Process request row. reads a row to get the request information.
		 *
		 * @param cellIt the cell it
		 * @return the request
		 * @throws UniversityNotFoundException the university not found exception
		 */
		private Request processRequestRow(Iterator<Cell> cellIt) throws UniversityNotFoundException {
			String dni = null, university = null, start = null;
			Long priority = null;
			while (cellIt.hasNext()) {
				Cell cell = cellIt.next();
				int column = cell.getColumnIndex();
				if (column == dniC) 
					dni = (String) getCellValue(cell);
				if (column == uniC) 
					university = (String) getCellValue(cell);
				if (column == startC)
					start = (String) getCellValue(cell);
				if (column == priorityC) {
					 Double aux = (Double) getCellValue(cell);
					//solucion problema de parada
					if (aux == null)
						return null;
					priority = aux.longValue();
				}
			}
			if (dni == null) //nueva condicion de parada ???
				return null;
			if (uniRepo.findByName(university) == null)
				throw new UniversityNotFoundException(university);
			return new Request(studentService.find(dni), uniRepo.findByName(university), priority, start);
		}
		
		/**
		 * Read student excel. process the document searching for all the students.
		 *
		 * @param file the file
		 * @param name the name
		 * @return the list
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		public List<Student> readStudentExcel(File file, String name) throws IOException {			
			List<Student> result = new ArrayList<>();
			int header = 0, aux = 0;
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
				Student st = processStudentRow(cellIt);
				if (st != null)
					result.add(st);
			}
			
			wb.close();
			inputStream.close();
			return result;
		}
		
		/**
		 * Read request excel. process the document searching for requests.
		 *
		 * @param file the file
		 * @param name the name
		 * @return the list
		 * @throws IOException Signals that an I/O exception has occurred.
		 * @throws UniversityNotFoundException the university not found exception
		 */
		public List<Request> readRequestExcel(File file, String name) throws IOException, UniversityNotFoundException {
			List<Request> result = new ArrayList<>();
			int header = 0, aux = 0;
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
				Request rq = processRequestRow(cellIt); 
				if (rq != null)
				result.add(rq);
			}
			
			wb.close();
			inputStream.close();
			return result;
		}
		
	/**
	 * Write changes. writes the results of the process.
	 *
	 * @param file the file
	 * @param name the name
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws UniversityNotFoundException the university not found exception
	 */
	public void writeChanges(File file, String name) throws IOException, UniversityNotFoundException {
		int header = 0, aux = 0;
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
	    
	    for (int i = header + 1; i < sheet.getPhysicalNumberOfRows(); i++) {
	    	Row row = sheet.getRow(i);
	    	Cell cell = row.getCell(dniC);
		    if (cell == null)
		    	break;
		    String cellName = (String) getCellValue(cell);
		    Student student = studentService.find(cellName);
		    Cell valCell = row.getCell(valC);
		    if (valCell ==  null)
		    	valCell = row.createCell(valC);
		    valCell.setCellType(CellType.NUMERIC);
		    valCell.setCellValue(student.getVal());
		    Double aux2 = (Double) getCellValue(row.getCell(priorityC));
		    long prio = aux2.longValue();
		    List<Request> requests = rqService.findByStudent(student);
		    Request rq = null;
		    for (Request r: requests) {
		    	if (r.getPriority() == prio)
		    		rq = r;
		    }
		    Cell stateCell = row.getCell(stateC);
		    String state = rq.getState().getValue();
		    if (rq.getState() == State.WAITING)
		    	state = state + " " + rq.getWaitingNum();
		    stateCell.setCellType(CellType.STRING);
		    stateCell.setCellValue(state);
		    if (rq.getState() == State.REJECTED) {
		    	Cell motCell = row.getCell(motiveC);
		    	if (motCell == null)
		    		motCell = row.createCell(motiveC);
			    motCell.setCellType(CellType.STRING);
			    motCell.setCellValue(rq.getMotive());
		    }
	    }
	    		
		inputStream.close();
		FileOutputStream out = new FileOutputStream(file);
		wb.write(out);
		wb.close();
		out.close();
	}

}
