package es.udc.fic.erasmus.preferences;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.udc.fic.erasmus.home.ProcessForm;

/**
 * The Class PreferencesController.
 */
@Controller
public class PreferencesController {

	/** The service. */
	@Autowired
	private PreferencesService service;
	
	/**
	 * Preferences. shows the form for the index columns.
	 *
	 * @param type the type
	 * @param model the model
	 * @return the string
	 */
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
	
	/**
	 * Preferences S. shows the form for the name of the columns
	 *
	 * @param type the type
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = "preferencesString", params = {"type"})
	String preferencesS(@RequestParam(value="type") boolean type, Model model) {
		model.addAttribute("type", type);
		model.addAttribute("actual", service.findString("default"));
		if (type) 
			model.addAttribute(new PreferencesStringUniForm());
		else 
			model.addAttribute(new PreferencesStringStudentForm());
		return "preference/preferencesString";
	}
	
	/**
	 * Config U. configures the index of columns for the university document.
	 *           Sets the preferences as active.
	 *
	 * @param model the model
	 * @param form the form
	 * @return the string
	 */
	@RequestMapping(value = "preferencesU", method = RequestMethod.POST)
	String configU(Model model, @ModelAttribute PreferencesUniForm form) {
		Preferences actual = service.find("default");
		PreferencesString pre = service.findString("default");
		actual.setUniversityCols(form.getColums());
		actual.setActive(true);
		service.save(actual);
		pre.setActive(false);
		service.save(pre);
		model.addAttribute(new ProcessForm());
		return "process/process";
	}
	
	/**
	 * Config S. configures the preferences for the index of the columns for the student document.
	 *           sets the preferences as active.
	 *
	 * @param model the model
	 * @param form the form
	 * @return the string
	 */
	@RequestMapping(value = "preferencesS", method = RequestMethod.POST)
	String configS(Model model, @ModelAttribute PreferencesStudentForm form) {
		Preferences actual = service.find("default");
		PreferencesString pre = service.findString("default");
		actual.setStudentCols(form.getStudentColums());
		actual.setRequestCols(form.getRequestColums());
		actual.setActive(true);
		service.save(actual);
		pre.setActive(false);
		service.save(pre);
		model.addAttribute(new ProcessForm());
		return "process/process";
	}
	
	/**
	 * Config U. configures the names of the columns for the university document.
	 *           sets the preferences string as active.
	 *
	 * @param model the model
	 * @param form the form
	 * @return the string
	 */
	@RequestMapping(value = "preferencesSU", method = RequestMethod.POST)
	String configU(Model model, @ModelAttribute PreferencesStringUniForm form) {
		PreferencesString actual = service.findString("default");
		Preferences pre = service.find("default");
		actual.setUniversity(form.getColums());
		actual.setActive(true);
		service.save(actual);
		pre.setActive(false);
		service.save(pre);
		model.addAttribute(new ProcessForm());
		return "process/process";
	}
	
	/**
	 * Config S. configures the names of the columns for the student document.
	 *           sets the prefences string as active.
	 *
	 * @param model the model
	 * @param form the form
	 * @return the string
	 */
	@RequestMapping(value = "preferencesSS", method = RequestMethod.POST)
	String configS(Model model, @ModelAttribute PreferencesStringStudentForm form) {
		PreferencesString actual = service.findString("default");
		Preferences pre = service.find("default");
		actual.setStudent(form.getStudentColums());
		actual.setRequest(form.getRequestColums());
		actual.setActive(true);
		service.save(actual);
		pre.setActive(false);
		service.save(pre);
		model.addAttribute(new ProcessForm());
		return "process/process";
	}
}
