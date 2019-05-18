package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.model.Admin;
import com.example.demo.model.Block;
import com.example.demo.model.Course;
import com.example.demo.model.CourseClass;
import com.example.demo.model.Entry;
import com.example.demo.model.Faculty;
import com.example.demo.model.Schedule;
import com.example.demo.model.Student;
import com.example.demo.model.User;
import com.example.demo.repository.BlockRepository;
import com.example.demo.repository.CourseClassRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.FacultyRepository;
import com.example.demo.repository.ScheduleRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.UserRepository;


@Controller
@SessionAttributes("userid")
public class StudentController 
{
			@Autowired
			private ScheduleRepository schedulerepo;
			@Autowired
			private CourseRepository courserepo;
			@Autowired
			private CourseClassRepository courseclassrepo;
			@Autowired
			private UserRepository userrepo;
			@Autowired
			private StudentRepository studentrepo;
			@Autowired
			private BlockRepository blockrepo;
			@Autowired
			private FacultyRepository facultyrepo;
			
			
			@GetMapping("/studentdashboard")
			public String studenthome(HttpSession session, Model model) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Student))
					return "Login";
				return "StudentHome";
			}
			
			@GetMapping("/profile/user/{id}")
			public String studentprofile(HttpSession session, Model model) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Student))
					return "Login";
				return "StudentProfile";
			}
			
			
			@GetMapping("/ViewApprovedWaivers")
			public String approvedwaivers(HttpSession session, Model model) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Student))
					return "Login";
				Student user = (Student) session.getAttribute("userid");
				model.addAttribute("courses", user.getApprovedprerequisits());
				return "ViewApprovedWaivers";
			}
			
			@GetMapping("/viewcoursesneedingprerequisites")
			public String  coursesneedingprerequisites(HttpSession session, Model model) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Student))
					return "Login";
				return "CoursesNeedingPrerequisites";
			}
			
			@GetMapping("/viewAvailableclasses")
			public String viewavailableClasses(HttpSession session, Model model) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Student))
					return "Login";
				List<Block> block = (List<Block>) blockrepo.findAll();
				Student student = studentrepo.findById(((Student)session.getAttribute("userid")).getUserID()).get();
				
				if(student.getEntry() == Entry.CPT)
				{
					int count = student.getClassesToAttend().size();
					for(CourseClass crs : student.getClassesToAttend()) {
						block.remove(crs.getBlock());
					}
					block = block.stream().filter(x -> x.getStartDate().after(student.getEntryDate())).limit(4-count).collect(Collectors.toList());
					model.addAttribute("blocks", block);
				}
				else if(student.getEntry() == Entry.OPT)
				{
					int count = student.getClassesToAttend().size();
					for(CourseClass crs : student.getClassesToAttend()) {
						block.remove(crs.getBlock());
					}
					block = block.stream().filter(x -> x.getStartDate().after(student.getEntryDate())).limit(5-count).collect(Collectors.toList());
					model.addAttribute("blocks", block);
				}
				else if(student.getEntry() == Entry.US)
				{
					int count = student.getClassesToAttend().size();
					for(CourseClass crs : student.getClassesToAttend()) {
						block.remove(crs.getBlock());
					}
					block = block.stream().filter(x -> x.getStartDate().after(student.getEntryDate())).limit(8-count).collect(Collectors.toList());
					model.addAttribute("blocks", block);
				}
				return "ViewAvailableclasses";
			}
		
			@GetMapping("/viewRegisteredCourses")
			public String viewRegisteredCourses(HttpSession session, Model model) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Student))
					return "Login";
				Student student = studentrepo.findById(((Student)session.getAttribute("userid")).getUserID()).get();
				List<CourseClass> classes = (List<CourseClass>) student.getClassesToAttend();
				model.addAttribute("registeredcourses", classes);
				return "ViewRegisteredCourses";
			}
			@GetMapping("/viewallcoursesstudent")
			public String viewallcourses(@PageableDefault(size = 100) Pageable pageable, Model model, HttpSession session) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Student))
					return "Login";
				model.addAttribute("courses", courserepo.findAll());
				return "ViewCoursesStudent";
			}
			@GetMapping("/student/viewprofile")
			public String viewprofile(HttpSession session, Model model) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Student))
					return "Login";
				Student student = studentrepo.findById(((Student)session.getAttribute("userid")).getUserID()).get();
				model.addAttribute("user", student);
				return "StudentProfile";
			}
			@GetMapping("/viewclassestotake")
			public String viewclassestotake(HttpSession session, Model model) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Student))
					return "Login";
				Student student = studentrepo.findById(((Student)session.getAttribute("userid")).getUserID()).get();
				model.addAttribute("classes", student.getClassesToAttend());
				return "ViewClassesToTake";
			}
			
			@GetMapping("/viewcoursesneedingprerequisite")
			public String viewcoursesneedingprerequisite(HttpSession session, Model model) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Student))
					return "Login";
				Student student = studentrepo.findById(((Student)session.getAttribute("userid")).getUserID()).get();
				model.addAttribute("classes", student.getClassesToAttend());
				return "CoursesNeedingPrerequisites";
			}
			
			@GetMapping("/dropcourse/course/{courseid}")
			public RedirectView dropclass(@PathVariable("courseid") String id, Model model, HttpSession session) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Student))
					return new RedirectView("/login");	
				else {
					CourseClass deletedcourse = courseclassrepo.findById(id).get();
					Student currentuser = (Student) session.getAttribute("userid");
					List<CourseClass> classestoattend = new ArrayList<>(currentuser.getClassesToAttend());
					Set<CourseClass> updatedclasses = new HashSet<>();
					for(CourseClass cl : classestoattend) {
						if(cl.getCourseClassId().equalsIgnoreCase(deletedcourse.getCourseClassId()))
							continue;
						else
							updatedclasses .add(cl);
					}
					currentuser.setClassesToAttend(updatedclasses);
					Set<Student> stds = deletedcourse.getStudents();
					stds.remove(currentuser);
					deletedcourse.setStudents(stds);
					
					studentrepo.save(currentuser);
					courseclassrepo.save(deletedcourse);
					return new RedirectView("/viewclassestotake");
				}
			}
			
			@GetMapping("/registerforcourse/course/{id}")
			public RedirectView registerForCourse(@PathVariable("id") String id , Model model, HttpSession session) 
			{
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Student))
					return new RedirectView("/login");	
				
				//get entities
				Student student = studentrepo.findById(((Student)session.getAttribute("userid")).getUserID()).get();
				CourseClass courseclass = courseclassrepo.findById(id).get();
				
				if(courseclass.getAvailableseats()<0) {
					model.addAttribute("errormsg", "No avilable seats");
					return new RedirectView("/viewAvailableclasses");
				}
					
				for(CourseClass c : student.getClassesToAttend())
					if(c.getCourseClassId().equals(courseclass.getCourseClassId()))
					{	
						model.addAttribute("errormsg", "You Have already registered for this class");
						return new RedirectView("/viewAvailableclasses");
					}
				
				if(courseclass.getCourse().getCourseName().equalsIgnoreCase("MPP") || courseclass.getCourse().getCourseName().equalsIgnoreCase("FPP"))
				{
					model.addAttribute("errormsg", "You can not register for this course");
				}
				
				if(student.getClassesToAttend().size() == 0 && student.getApprovedprerequisits() == null) {
					courseclass.addsttudent(student);
					student.addclass(courseclass);
					courseclass.setAvailableseats(courseclass.getAvailableseats() - 1);
					courseclassrepo.save(courseclass);
					studentrepo.save(student);
					return new RedirectView("/viewclassestotake");
				}
					
				for(CourseClass c : student.getClassesToAttend()) 
				{
						if(c.getBlock().equals(courseclass.getBlock())) {
							model.addAttribute("errormsg", "You Have registered for a class to teach in that block");
							return new RedirectView("/viewAvailableclasses");
						}
						else if(c.getCourse().getCourseName().equals(courseclass.getCourse().getCourseName())) {
							model.addAttribute("errormsg", "You Have registered for a class that belongs to same course");
							return new RedirectView("/viewAvailableclasses");
						}
						else if(student.getApprovedprerequisits() == null && courseclass.getAvailableseats()>0)
						{
							if(courseclass.getCourse().getCourseName().equalsIgnoreCase("MWA") && (c.getCourse().getCourseName().equalsIgnoreCase("WAP") && c.getBlock().getStartDate().before(courseclass.getBlock().getStartDate())) && courseclass.getAvailableseats()>0) {
								courseclass.addsttudent(student);
								student.addclass(courseclass);
								courseclass.setAvailableseats(courseclass.getAvailableseats() - 1);
								courseclassrepo.save(courseclass);
								studentrepo.save(student);
								return new RedirectView("/viewclassestotake");
							}
							else if((courseclass.getCourse().getCourseName().equalsIgnoreCase("EA") || (courseclass.getCourse().getCourseName().equalsIgnoreCase("WAA")) && (c.getCourse().getCourseName().equalsIgnoreCase("WAP") && c.getBlock().getStartDate().before(courseclass.getBlock().getStartDate()))) && courseclass.getAvailableseats()>0) {
								courseclass.addsttudent(student);
								student.addclass(courseclass);
								courseclass.setAvailableseats(courseclass.getAvailableseats() - 1);
								courseclassrepo.save(courseclass);
								studentrepo.save(student);
								return new RedirectView("/viewclassestotake");
							}
							else if((courseclass.getCourse().getCourseName().equalsIgnoreCase("EA") && (c.getCourse().getCourseName().equalsIgnoreCase("SWE") && c.getBlock().getStartDate().before(courseclass.getBlock().getStartDate()))) && courseclass.getAvailableseats()>0) {
								courseclass.addsttudent(student);
								student.addclass(courseclass);
								courseclass.setAvailableseats(courseclass.getAvailableseats() - 1);
								courseclassrepo.save(courseclass);
								studentrepo.save(student);
								return new RedirectView("/viewclassestotake");
							}
							else if((courseclass.getCourse().getCourseName().equalsIgnoreCase("EA") && (c.getCourse().getCourseName().equalsIgnoreCase("ASD") && c.getBlock().getStartDate().before(courseclass.getBlock().getStartDate()))) && courseclass.getAvailableseats()>0) {
								courseclass.addsttudent(student);
								student.addclass(courseclass);
								courseclass.setAvailableseats(courseclass.getAvailableseats() - 1);
								courseclassrepo.save(courseclass);
								studentrepo.save(student);
								return new RedirectView("/viewclassestotake");
							}
							else {
								courseclass.addsttudent(student);
								student.addclass(courseclass);
								courseclass.setAvailableseats(courseclass.getAvailableseats() - 1);
								courseclassrepo.save(courseclass);
								studentrepo.save(student);
								return new RedirectView("/viewclassestotake");
							}
						}
						else if(student.getApprovedprerequisits() != null)
						{
							for(Course cls : student.getApprovedprerequisits().getCourse()) 
							{
								if(cls.getCourseName().equalsIgnoreCase("WAP"))
								{	
									courseclass.addsttudent(student);
									student.addclass(courseclass);
									courseclass.setAvailableseats(courseclass.getAvailableseats() - 1);
									courseclassrepo.save(courseclass);
									studentrepo.save(student);
									return new RedirectView("/viewclassestotake");
								}
							}
						}
				}
				return new RedirectView("/viewAvailableclasses");
			}
}
