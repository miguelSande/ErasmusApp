package es.udc.fic.erasmus.preferences;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.udc.fic.erasmus.home.ProcessForm;

@Controller
public class PreferencesController {

	@Autowired
	private PreferencesService service;
	
	@RequestMapping(value = "preferences", params = {"type"})
	String preferences(@RequestParam(value="type") boolean type, Model model) {
		model.addAttribute("type", type);
		model.addAttribute("actual", service.find("default"));
		if (type) 
			model.addAttribute(new PreferencesUniForm());
		else 
			model.addAttribute(new PreferencesStudentForm());
		return "preference/preferences";
	}
	
	@RequestMapping(value = "preferencesU", method = RequestMethod.POST)
	String configU(Model model, @ModelAttribute PreferencesUniForm form) {
		Preferences actual = service.find("default");
		actual.setUniversityCols(form.getColums());
		service.create(actual);
		model.addAttribute(new ProcessForm());
		return "process/process";
	}
	
	@RequestMapping(value = "preferencesS", method = RequestMethod.POST)
	String configS(Model model, @ModelAttribute PreferencesStudentForm form) {
		Preferences actual = service.find("default");
		actual.setStudentCols(form.getStudentColums());
		actual.setRequestCols(form.getRequestColums());
		service.create(actual);
		model.addAttribute(new ProcessForm());
		return "process/process";
	}
}
