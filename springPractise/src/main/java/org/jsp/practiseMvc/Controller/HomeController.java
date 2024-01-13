package org.jsp.practiseMvc.Controller;

import org.jsp.practiseMvc.Dao.PersonDao;
import org.jsp.practiseMvc.Dto.Person;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

//	@Autowired
	private PersonDao dao = new PersonDao();

	@RequestMapping(value = "/open-register")
	public ModelAndView savePerson(ModelAndView m) {
		m.addObject("p", new Person());
		m.setViewName("register");
		return m;
	}

	@ResponseBody
	@PostMapping(value = "/save")
	public String save(@ModelAttribute(name = "p") Person p) {
		p = dao.savePerson(p);
		return "person saved with id : " + p.getId();
	}

	@RequestMapping(value = "/update-person")
	public ModelAndView updatePerson(ModelAndView m) {
		m.addObject("p", new Person());
		m.setViewName("update");
		return m;
	}

	@ResponseBody
	@PostMapping(value = "/update")
	public String update(@ModelAttribute(name = "p") Person p) {
		p = dao.updatePerson(p);
		if (p != null) {
			return "person updated with id : " + p.getId();
		}
		return "Person id you mention id not exist ";
	}

	@RequestMapping(value = "/open-views")
	public String returnViewName(@RequestParam String view) {
		return view;
	}

	@PostMapping(value = "/verifyByPhone")
	public ModelAndView verifyByPhoneAndPassword(@RequestParam long phone, @RequestParam String password,
			ModelAndView m) {
		Person p = dao.verifyByPhoneAndPassword(phone, password);
		if (p != null) {
			m.addObject("p", p);
			m.setViewName("details");
			return m;
		}
		m.addObject("msg", "verification declined invalid phone number or password retry...!!!");
		m.setViewName("error");
		return m;
	}

	@PostMapping(value = "/verifyByEmail")
	public ModelAndView verifyByEmailAndPassword(@RequestParam String email, @RequestParam String password,
			ModelAndView m) {
		Person p = dao.verifyByEmailAndPassword(email, password);
		if (p != null) {
			m.addObject("p", p);
			m.setViewName("details");
			return m;
		}
		m.addObject("msg", "verification declined invalid email or password retry...!!!");
		m.setViewName("error");
		return m;
	}

	@PostMapping(value = "/verifyById")
	public ModelAndView verifyByIdAndPassword(@RequestParam int id, @RequestParam String password, ModelAndView m) {
		Person p = dao.verifyByIdAndPassword(id, password);
		if (p != null) {
			m.addObject("p", p);
			m.setViewName("details");
			return m;
		}
		m.addObject("msg", "verification declined invalid id or password retry...!!!");
		m.setViewName("error");
		return m;
	}
	
	@GetMapping(value="/delete")
	public ModelAndView delete(@RequestParam int id, ModelAndView m) {
		boolean p = dao.deleteById(id);
		if (p) {
			m.addObject("msg","successfully person deleted by id : "+id);
			m.setViewName("error");
			return m;
		}
		m.addObject("msg", "No person exist in this id...!!!");
		m.setViewName("error");
		return m;
	}
	
	@GetMapping(value="/findById")
	public ModelAndView findById(@RequestParam int id, ModelAndView m) {
		Person p = dao.findById(id);
		if (p != null) {
			m.addObject("p", p);
			m.setViewName("details");
			return m;
		}
		m.addObject("msg", "Invalid id no person exist in this id...!!!");
		m.setViewName("error");
		return m;
	}
	
}
