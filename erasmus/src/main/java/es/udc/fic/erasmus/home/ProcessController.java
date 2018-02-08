package es.udc.fic.erasmus.home;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
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

import es.udc.fic.erasmus.error.UniversityNotFoundException;
import es.udc.fic.erasmus.reader.ReaderStudent;
import es.udc.fic.erasmus.reader.ReaderUni;
import es.udc.fic.erasmus.request.Request;
import es.udc.fic.erasmus.request.RequestService;
import es.udc.fic.erasmus.student.Student;
import es.udc.fic.erasmus.student.StudentService;
import es.udc.fic.erasmus.support.web.MessageHelper;
import es.udc.fic.erasmus.university.University;
import es.udc.fic.erasmus.university.UniversityService;

/**
 * The Class ProcessController.
 */
@Controller
public class ProcessController {
	
	/** The Constant PROCESS_VIEW_NAME. */
	private static final String PROCESS_VIEW_NAME = "process/process";
	
	/** The Constant RESULT_VIEW_NAME. */
	private static final String RESULT_VIEW_NAME = "process/result";
	
	/** The uni service. */
	@Autowired
	private UniversityService uniService;
	
	/** The student service. */
	@Autowired
	private StudentService studentService;
	
	/** The request service. */
	@Autowired
	private RequestService requestService;
	
	/** The university reader. */
	@Autowired
	private ReaderUni readerU;
	
	/** The student reader. */
	@Autowired
	private ReaderStudent readerS;
	
	/** The Constant tmpDir. */
	private static final String tmpDir = System.getProperty("java.io.tmpdir");
	
	/**
	 * Convert. turns the multipartFile into a file located in the tmp directoty.
	 *
	 * @param file the file
	 * @return the file
	 * @throws IllegalStateException the illegal state exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private File convert(MultipartFile file) throws IllegalStateException, IOException {
		File f = new File(tmpDir + File.separator + file.getOriginalFilename());
		f.createNewFile();
		FileOutputStream fo = new FileOutputStream(f);
		fo.write(file.getBytes());
		fo.close();
		return f;
	}
	
	/**
	 * Shows the process form with error.
	 *
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("process")
	String process(Model model, @ModelAttribute("uni") String uni) {
		model.addAttribute("uni", uni);
		model.addAttribute(new ProcessForm());
		return PROCESS_VIEW_NAME;	
	}
	
	/**
	 * Process. process the documents and shows the result, redirects in case of errors.
	 *
	 * @param model the model
	 * @param processForm the process form
	 * @param error the error
	 * @param ra the ra
	 * @return the string
	 */
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
			List<Request> requests;
			requests = readerS.readRequestExcel(convert(processForm.getPathRequest()), processForm.getPathRequest().getOriginalFilename());
			
			for (Request r: requests) {
				requestService.create(r);
			}
			requestService.preprocess();
			File file = new File(tmpDir + File.separator + processForm.getPathRequest().getOriginalFilename());
			readerS.writeChanges(file, processForm.getPathRequest().getOriginalFilename());			
			model.addAttribute("requests", requestService.returnRequests(requests));
			model.addAttribute("file", processForm.getPathRequest().getOriginalFilename());
			return RESULT_VIEW_NAME;
		} catch (UniversityNotFoundException e) {
			MessageHelper.addErrorAttribute(ra, "process.uniFail");
			ra.addFlashAttribute("uni", e.getName());
			return "redirect:/process";
		} catch (IllegalStateException e) {
			MessageHelper.addErrorAttribute(ra, "process.fileError");
			model.addAttribute(new ProcessForm());
			return "redirect:/process";
		} catch (IOException e) {
			MessageHelper.addErrorAttribute(ra, "process.fileError");
			model.addAttribute(new ProcessForm());
			return "redirect:/process";
		} finally {}
	}
	
	
	/**
	 * Download the excel with the results.
	 *
	 * @param response the response
	 * @param name the name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping(value="download", params = {"name"})
	void download(HttpServletResponse response, @RequestParam(value="name") String name) throws IOException {
		File file = new File(tmpDir+File.separator+name);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));
		response.setContentLength((int) file.length());
		InputStream is = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(is, response.getOutputStream());
	}
}
