package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.model.Admin;
import com.example.demo.model.Faculty;
import com.example.demo.model.Student;
import com.example.demo.model.User;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.UserRepository;


@Controller
@SessionAttributes("userid")
public class LoginController {
	@Autowired
	UserRepository userrepo;
	StudentRepository studentrepo;
	
	@GetMapping("/login")
	public String login(HttpSession session, Model model) 
	{
		System.out.print(session.getAttribute("userid"));
		if(session.getAttribute("userid") != null && (session.getAttribute("userid") instanceof Admin)) {
			System.out.print("admin -----------------------------------------------------------------------------------");
			User user = (User) session.getAttribute("userid");
			model.addAttribute("userid", userrepo.findById(user.getUserID()).get());
			return "AdminHome";
		}
		else if(session.getAttribute("userid") != null && (session.getAttribute("userid") instanceof Student))
		{	
			System.out.print("student -----------------------------------------------------------------------------------");
			User user = (User) session.getAttribute("userid");
			model.addAttribute("userid", userrepo.findById(user.getUserID()).get());
			return "StudentHome";
		}
		else if(session.getAttribute("userid") != null && (session.getAttribute("userid") instanceof Faculty))
		{	
			System.out.print("faculty -----------------------------------------------------------------------------------");
			User user = (User) session.getAttribute("userid");
			model.addAttribute("userid", userrepo.findById(user.getUserID()).get());
			return "FacultyHome";
		}
		return "Login";
	}
	@GetMapping("/loginnn")
	public String loginnn(Model model, HttpSession session) {
		return "Login";
	}
	
	
	@RequestMapping(value = "/loginprocess", method = RequestMethod.POST)
	public RedirectView processlogin(@RequestParam("userid") String userid, @RequestParam("password") String password, Model model, HttpSession session) 
	{			
		if(!userrepo.existsById(userid))
		{
			String error = "Wrong Credentials";
			model.addAttribute("error", error);
			return new RedirectView("/login");
		}
		else {
			User user = userrepo.findById(userid).get();
			if(user.getPassword().equals(password)) 
			{
				if(user instanceof Admin) {
					model.addAttribute("userid", userrepo.findById(userid).get());
					return new RedirectView("/admindashboard");
				}
				else if(user instanceof Faculty) {
					model.addAttribute("userid", userrepo.findById(userid).get());
					return new RedirectView("/facultydashboard");
				}
				else if(user instanceof Student) {
					model.addAttribute("userid", userrepo.findById(userid).get());
					return new RedirectView("/studentdashboard");
				}else {
					model.addAttribute("userid", userrepo.findById(userid).get());
					return new RedirectView("/stdashboard");
				}
			}
			else {
				String error = "Wrong Credentials please";
				model.addAttribute("error", error);
				new RedirectView("/login");
			}
		}
		return null;
	}
}
