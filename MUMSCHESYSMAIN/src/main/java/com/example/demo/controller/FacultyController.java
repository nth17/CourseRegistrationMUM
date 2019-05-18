package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.model.Admin;
import com.example.demo.model.Course;
import com.example.demo.model.CourseClass;
import com.example.demo.model.Faculty;
import com.example.demo.model.Schedule;
import com.example.demo.model.Student;
import com.example.demo.model.User;
import com.example.demo.repository.CourseClassRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.FacultyRepository;
import com.example.demo.repository.ScheduleRepository;
import com.example.demo.repository.UserRepository;

@Controller
@SessionAttributes("userid")
public class FacultyController {
	@Autowired
	UserRepository userrepo;
	
	@Autowired
	CourseClassRepository classrepo;
	
	@Autowired
	FacultyRepository facultyrepo;
	
	@Autowired
	ScheduleRepository schedulerepo;
	
	@Autowired
	CourseRepository courserepo;
	
	@GetMapping("/facultydashboard")
	public String facultyhome(HttpSession session, Model model) {
		if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Faculty))
			return "Login";
		model.addAttribute("classes", facultyrepo.findById(((Faculty)session.getAttribute("userid")).getUserID()).get() .getClassesToTeach());
		model.addAttribute("courses", facultyrepo.findById(((Faculty)session.getAttribute("userid")).getUserID()).get() .getCoursesToTeach());
		return "FacultyHome";
	}
	
	@GetMapping("/viewyearlyschedule")
	public String ViewYearlySchedule(HttpSession session, Model model) {
		if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Faculty))
			return "Login";
		Faculty fac = (Faculty) session.getAttribute("userid");
		List<Schedule> sch = (List<Schedule>) schedulerepo.findAll();
		for(Schedule s : sch) {
			
		}
		model.addAttribute("schedules", schedulerepo.findAll());
		return "ViewYearlySchedule";
	}
	
	@GetMapping("/viewavailableclassestoteach")
	
	public String addnewstudent( HttpSession session, Model model) {
		if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Faculty))
			return "Login";
		List<CourseClass> classes = (List<CourseClass>) classrepo.findAll();
		classes = classes.stream().filter(x -> x.getProfessor() != null).collect(Collectors.toList());
		model.addAttribute("availableclassestoteach", classes);
		
		return "ViewClassesToTeach";
	}
	@GetMapping("/viewallcoursesfaculty")
	public String viewallcourses(@PageableDefault(size = 100) Pageable pageable, Model model, HttpSession session) {
		if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Faculty))
			return "Login";
		model.addAttribute("courses", courserepo.findAll());
		return "ViewCoursesFaculty";
	}
	@GetMapping("/viewclassestoteach")
	public String viewclassestoteach(HttpSession session, Model model) {
		if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Faculty))
			return "Login";
		else {
			Faculty faculty =  facultyrepo.findById(((Faculty)session.getAttribute("userid")).getUserID()).get();
			System.out.println(faculty.getFirstName() + "------------------------------------------------------------");
			model.addAttribute("classes", faculty.getClassesToTeach());
			return "ViewClassesToTeach";
		}
		
	}
	@GetMapping("/assignprofessor/course/{id}")
	public RedirectView registerforclasstoteach(@PathVariable("id") String id, HttpSession session, Model model) {
		if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Faculty))
			return new RedirectView("/login");
		else {
			if(classrepo.findById(id).get() .getProfessor() != null)
			{	model.addAttribute("errormsg", "You Have registered for a class to teach in that block");
				return new RedirectView("/listofclasseserror");
			}
			Faculty faculty =  facultyrepo.findById(((Faculty)session.getAttribute("userid")).getUserID()).get();
			CourseClass courseclass =  classrepo.findById(id).get();
			
			for(CourseClass c : faculty.getClassesToTeach()) {
				if(c.getBlock().equals(courseclass.getBlock())) {
					model.addAttribute("errormsg", "You Have registered for a class to teach in that block");
					return new RedirectView("/listofclasseserror");
				}
			}
			courseclass.setProfessor(faculty);
			faculty.addclass(courseclass);
			classrepo.save(courseclass);
			facultyrepo.save(faculty);
			return new RedirectView("/viewclassestoteach");
		}
	}	
}
