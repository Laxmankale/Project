package com.jspiders.springmvc6.cotroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.jspiders.springmvc6.pojo.StudentPojo;
import com.jspiders.springmvc6.service.StudentService;

@Controller
public class StudentController {

	@Autowired
	private StudentService service;

	// Home Controller
	@GetMapping("/home")
	public String home(@SessionAttribute(name = "login", required = false) StudentPojo login, ModelMap map) {
		if (login != null) {
			return "Home";
		}
		map.addAttribute("msg", "login to proceed...!");
		return "Login";
	}

	// Login Page
	@GetMapping("/login")
	public String login() {
		return "Login";
	}

	// Login Controller
	@PostMapping("/login")
	public String loginData(HttpServletRequest request, @RequestParam String username, @RequestParam String password,
			ModelMap map) {

		StudentPojo student = service.login(username, password);
		if (student != null) {
			HttpSession session = request.getSession();
			session.setAttribute("login", student);
			session.setMaxInactiveInterval(150);
			return "Home";
		}
		map.addAttribute("msg", "invalid password...");
		return "Login";
	}

	// Add Controller
	@GetMapping("/add")
	public String add(@SessionAttribute(name = "login", required = false) StudentPojo login, ModelMap map) {
		if (login != null) {
			return "Add";
		}
		map.addAttribute("msg", "Login to proceed..!!");
		return "Login";
	}

	// Add response controller
	@PostMapping("/add")
	public String addStudent(@SessionAttribute(name = "login", required = false) StudentPojo login,
			@RequestParam String name, @RequestParam String email, @RequestParam long contact,
			@RequestParam String city, @RequestParam String username, @RequestParam String password, ModelMap map) {
		if (login != null) {

			StudentPojo student = service.add(name, email, contact, city, username, password);
			if (student != null) {
				map.addAttribute("student", student);
				map.addAttribute("msg", "student Add Succesfully...!");
			} else {
				map.addAttribute("msg", "student Not add...!");
			}
			return "Add";
		}
		map.addAttribute("msg", "Login to proceed..!");
		return "Login";
	}

	// Search Controller
	@GetMapping("/search")
	public String search(@SessionAttribute(name = "login", required = false) StudentPojo login, ModelMap map) {
		if (login != null) {
			return "Search";
		}
		map.addAttribute("msg", "Login to proceed..!!");
		return "Login";

	}

	// search response cotroller
	@PostMapping("/search")
	public String searchData(@SessionAttribute(name = "login", required = false) StudentPojo login,
			@RequestParam int id, ModelMap map) {
		if (login != null) {

			StudentPojo student = service.search(id);
			if (student != null) {
				// record found
				map.addAttribute("student", student);
				return "Search";
			}
			// record not found
			map.addAttribute("msg", "student not found....");
			return "Search";
		}
		map.addAttribute("msg", "Login to Proceed..!");
		return "Login";
	}

	// Remove From Controller
	@GetMapping("/remove")
	public String remove(@SessionAttribute(name = "login", required = false) StudentPojo login, ModelMap map) {
		if (login != null) {
			List<StudentPojo> students = service.searchAll();
			map.addAttribute("students", students);
			return "Remove";
		}
		map.addAttribute("msg", "Login to proceed..!!");
		return "Login";
	}

	// Remove response controller
	@PostMapping("/remove")
	public String removeAll(@SessionAttribute(name = "login", required = false) StudentPojo login, @RequestParam int id,
			ModelMap map) {
		if (login != null) {

			StudentPojo Student = service.remove(id);
			// Remove Success
			if (Student != null) {
				List<StudentPojo> students = service.searchAll();
				map.addAttribute("students", students);
				map.addAttribute("msg", "Student removed....!");
				return "Remove";
			}
			// Remove fail
			List<StudentPojo> students = service.searchAll();
			map.addAttribute("students", students);
			map.addAttribute("msg", "Student not removed...?");
			return "Remove";
		}
		map.addAttribute("msg", "Login to proceed..!!");
		return "Login";

	}

	// update page Controller
	@GetMapping("/update")
	public String update(@SessionAttribute(name = "login", required = false) StudentPojo login, ModelMap map) {
		if (login != null) {

			List<StudentPojo> students = service.searchAll();
			map.addAttribute("students", students);
			return "Update";
		}
		map.addAttribute("msg", "Login to proceed..!!");
		return "Login";

	}

	// update form controlller
	@PostMapping("/update")
	public String updatForm(@SessionAttribute(name = "login", required = false) StudentPojo login, @RequestParam int id,
			ModelMap map) {
		if (login != null) {

			StudentPojo student = service.search(id);
			if (student != null) {
				// succces
				map.addAttribute("student", student);
			}
			List<StudentPojo> students = service.searchAll();
			map.addAttribute("students", students);
			map.addAttribute("msg", "students data deoes not exist....");
			return "Update";
		}
		map.addAttribute("msg", "Login to proceed..!!");
		return "Login";

	}

	// update data controller
	@PostMapping("/updateData")
	public String updateData(@SessionAttribute(name = "login", required = false) StudentPojo login,
			@RequestParam int id, @RequestParam String name, @RequestParam String email, @RequestParam long contact,
			@RequestParam String city, @RequestParam String username, @RequestParam String password, ModelMap map) {
		if (login != null) {

			StudentPojo student = service.update(id, name, email, contact, city, username, password);
			if (student != null) {
				// success
				map.addAttribute("msg", "Student data updated..");
				List<StudentPojo> students = service.searchAll();
				map.addAttribute("students", students);
				return "Update";
			}
			// Fail
			map.addAttribute("msg", "student not update..");
			List<StudentPojo> students = service.searchAll();
			map.addAttribute("students", students);
			return "Update";
		}
		map.addAttribute("msg", "Login to proceed..!!");
		return "Login";

	}

	// logout Controller
	@GetMapping("/logout")
	public String logout(HttpSession Session, ModelMap map) {
		Session.invalidate();
		map.addAttribute("msg", "Logged out successfully...");
		return "Login";

	}
}
