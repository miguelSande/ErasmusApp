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
import es.udc.fic.erasmus.preferences.PreferencesService;
import es.udc.fic.erasmus.preferences.PreferencesString;
import es.udc.fic.erasmus.request.Request;
import es.udc.fic.erasmus.request.RequestService;
import es.udc.fic.erasmus.student.Student;
import es.udc.fic.erasmus.student.StudentService;
import es.udc.fic.erasmus.university.UniversityRepository;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ReaderStudent {	
	
	@Autowired
	private UniversityRepository uniRepo;
	
	@Autowired
	private RequestService rqService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private PreferencesService service;
		
	private int nameC;
	private int dniC;
	private int languageC;
	private int othersC;
	private int noteC;
	private int valC;
	private int lang_test1C;
	private int lang_test2C;
	private int lang_test3C;
	private int lang_test4C;
	private int lang_test5C;
	
	private int uniC;
	private int priorityC;
	private int startC;
	private int motiveC;
	private int stateC;
	
	private void init() {
		int[] preferencesR = service.find("default").getRequestCols();
		int[] preferencesS = service.find("default").getStudentCols();
		
		nameC = preferencesS[0];
		dniC = preferencesS[1];
		languageC = preferencesS[2];
		othersC = preferencesS[3];
		noteC = preferencesS[4];
		valC = preferencesS[5];
		lang_test1C = preferencesS[6];
		lang_test2C = preferencesS[7];
		lang_test3C = preferencesS[8];
		lang_test4C = preferencesS[9];
		lang_test5C = preferencesS[10];
		
		uniC = preferencesR[0];
		priorityC = preferencesR[1];
		startC = preferencesR[2];
		motiveC = preferencesR[3];
		stateC = preferencesR[4];
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
					lang_test1C = cell.getColumnIndex();
				if (value.toUpperCase().contains("FRANCÉS") || value.toUpperCase().contains("FRENCH"))
					lang_test2C = cell.getColumnIndex();
				if (value.toUpperCase().contains("ALEMÁN") || value.toUpperCase().contains("GERMAN"))
					lang_test3C = cell.getColumnIndex();
				if (value.toUpperCase().contains("ITALIANO") || value.toUpperCase().contains("ITALY"))
					lang_test4C = cell.getColumnIndex();
				if (value.toUpperCase().contains("PORTUGUÉS") || value.toUpperCase().contains("PORTUGUESS"))
					lang_test5C = cell.getColumnIndex();
			}
		}
		
		private Student processStudentRow(Iterator<Cell> cellIt) {
			String dni = null, name = null, others = null, language = null;
			Long priority = null;
			Double aux, note = null, lan1 = null,lan2=null,lan3=null,lan4=null,lan5=null;
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
				return new Student(dni, name, note, others, language, checkLanTest(lan1,lan2,lan3,lan4,lan5));
			else
				return null;
		}
		
		private Request processRequestRow(Iterator<Cell> cellIt) {
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
		
		public List<Request> readRequestExcel(File file, String name) throws IOException {
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
		
	public void writeChanges(File file, String name) throws IOException {
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
		    	valCell = row.createCell(dniC);
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
