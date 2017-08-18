package es.udc.fic.erasmus.home;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.udc.fic.erasmus.reader.ReaderStudent;
import es.udc.fic.erasmus.reader.ReaderUni;
import es.udc.fic.erasmus.request.Request;
import es.udc.fic.erasmus.request.RequestService;
import es.udc.fic.erasmus.student.Student;
import es.udc.fic.erasmus.student.StudentService;
import es.udc.fic.erasmus.support.web.MessageHelper;
import es.udc.fic.erasmus.university.University;
import es.udc.fic.erasmus.university.UniversityService;

@Controller
public class ProcessController {
	
	private static final String PROCESS_VIEW_NAME = "process/process";
	private static final String RESULT_VIEW_NAME = "process/result";
	
	@Autowired
	private UniversityService uniService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private RequestService requestService;
	
	@Autowired
	private ReaderUni readerU;
	
	@Autowired
	private ReaderStudent readerS;
	
	private static final String tmpDir = System.getProperty("java.io.tmpdir");
	
	private File convert(MultipartFile file) throws IllegalStateException, IOException {
		File f = new File(tmpDir + File.separator + file.getOriginalFilename());
		f.createNewFile();
		FileOutputStream fo = new FileOutputStream(f);
		fo.write(file.getBytes());
		fo.close();
		return f;
	}
	
	@GetMapping("process")
	String process(Model model) {
		model.addAttribute(new ProcessForm());
		return PROCESS_VIEW_NAME;	
	}
	
	@RequestMapping(value="process", method=RequestMethod.POST)
	String process(Model model,@ModelAttribute ProcessForm processForm, Errors error, RedirectAttributes ra) {
		if (error.hasErrors())
			return PROCESS_VIEW_NAME;
		try {
			List<University> unis = readerU.readUniExcel(convert(processForm.getPathUni()), processForm.getPathUni().getOriginalFilename());
			List<Student> students = readerS.readStudentExcel(convert(processForm.getPathRequest()), processForm.getPathRequest().getOriginalFilename());
			for (University u: unis) {
				uniService.create(u);
			}
			for (Student s: students) {
				studentService.create(s);
			}
			List<Request> requests = readerS.readRequestExcel(convert(processForm.getPathRequest()), processForm.getPathRequest().getOriginalFilename());
			for (Request r: requests) {
				requestService.create(r);
			}
			requestService.preprocess();
//			if (!processForm.writeResults())
//			readerS.writeChanges(convert(getPathRequest()), getPathRequest().getOriginalFilename());
//				MessageHelper.addWarningAttribute(ra, "process.warning");
			File file = new File(tmpDir + File.separator + processForm.getPathRequest().getOriginalFilename());
			readerS.writeChanges(file, processForm.getPathRequest().getOriginalFilename());			
			model.addAttribute("requests", requestService.returnRequests(requests));
			model.addAttribute("file", processForm.getPathRequest().getOriginalFilename());
			return RESULT_VIEW_NAME;
		} catch (Exception e) {
			e.printStackTrace();
			MessageHelper.addErrorAttribute(ra, "process.fileError");
			return PROCESS_VIEW_NAME;
		}
	}
	
	@RequestMapping(value="download", params = {"name"})
	void download(HttpServletResponse response, @RequestParam(value="name") String name) throws IOException {
		System.out.println(name);
		File file = new File(tmpDir+File.separator+name);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));
		response.setContentLength((int) file.length());
		InputStream is = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(is, response.getOutputStream());
	}
}
