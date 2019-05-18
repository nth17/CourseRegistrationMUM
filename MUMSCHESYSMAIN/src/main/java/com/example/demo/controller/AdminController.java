package com.example.demo.controller;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.model.Admin;
import com.example.demo.model.Block;
import com.example.demo.model.Course;
import com.example.demo.model.CourseClass;
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
public class AdminController 
{
			@Autowired
			UserRepository userrepo;
			@Autowired
			StudentRepository studentrepo;
			@Autowired 
			CourseRepository courserepo;
			@Autowired
			ScheduleRepository schedulerepo;
			@Autowired
			CourseClassRepository courseclassrepo;
			@Autowired
			BlockRepository blockrepo;
			@Autowired
			FacultyRepository facultyrepo;
			
			
			@GetMapping("/admindashboard")
			public String adminhome(HttpSession session, Model model) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Admin))
					return "Login";
				model.addAttribute("Success", "");
				model.addAttribute("numberofstudent", ((List<Student>) studentrepo.findAll()).size());
				model.addAttribute("numberoffaculty", ((List<Faculty>) facultyrepo.findAll()).size());
				model.addAttribute("numberofcourse", ((List<Course>) courserepo.findAll()).size());
				model.addAttribute("users", userrepo.findAll());
				return "AdminHome";
			}
			
			@GetMapping("/removestudentfromcourse/{id}/{courseid}")
			public String removestudentfromcourse(@PathVariable("id") String id, @PathVariable("courseid") String courseid, HttpSession session, Model model) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Admin))
					return "Login";
				CourseClass courseclass = courseclassrepo.findById(courseid).get();
				Student student = studentrepo.findById(id).get();
				
				model.addAttribute("Success", "");
				return "AddUsers";
			}
			@GetMapping("/addnewuser")
			public String addnewstudent(HttpSession session, Model model) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Admin))
					return "Login";
				model.addAttribute("Success", "");
				return "AddUsers";
			}
			
			@GetMapping("/ManageUsers")
			public String viewallusers(@PageableDefault(size = 100) Pageable pageable, Model model, HttpSession session) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Admin))
					return "Login";
				model.addAttribute("users", userrepo.findAll());
				return "ManageUsers";
			}
			
			@GetMapping("/edit/user/{id}")
			public String Edituser(@PathVariable("id") String id, Model model, HttpSession session) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Admin))
					return "Login";
				else {
					model.addAttribute("user", model.addAttribute(userrepo.findById(id)));
					if(userrepo.findById(id).get() instanceof Student)	
					{	
						return "studentprofileeditpage";
					}
					else if(userrepo.findById(id).get() instanceof Faculty) {
						return "facultyprofileeditpage";
					}
					else
						return "adminprofileeditpage";
				}
			}
			
			@PutMapping("/update/user/{id}")
			public String UpdateUser(@ModelAttribute("user") User user, Model model, HttpSession session) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Admin))
					return "Login";
				else {
					userrepo.save(user);
					model.addAttribute("users", userrepo.findAll());
					return "viewallusers";
				}
			}
			
			@PostMapping("/savenewstudent")
			public String savenewstudent(@ModelAttribute("student") Student student, @RequestParam("EntryMonth") String entrymonth, Model model, HttpSession session) {
				if(userrepo.existsById(student.getUserID())) {
					String usererror = "user ID Exsist";
					model.addAttribute("errormsg", usererror);
					return "AddUsers";
				}
				else {
					System.out.print("--------------------------------------" + entrymonth);
				    Date date1 = null;
					try {
						date1 = new SimpleDateFormat("yyyy-mm-dd").parse(entrymonth);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					student.setEntryDate(date1);
					studentrepo.save(student);
					if(student.getAdvisor() != null)
					{
						student.getAdvisor().addAdvisee(student);
					}
					model.addAttribute("Sucess", "Student Added Sucessfully");
					return "AddUsers";
				}
			}
			
			@PostMapping("/savenewfaculty")
			public String savenewfaculty(@ModelAttribute("faculty") Faculty faculty, Model model, HttpSession session) {
				if(userrepo.existsById(faculty.getUserID())) {
					String usererror = "user ID Exsist";
					model.addAttribute("errormsg", usererror);
					return "AddUsers";
				}
				else {
					facultyrepo.save(faculty);
					model.addAttribute("Sucess", "Faculty Added Sucessfully");
					return "AddUsers";
				}
			}
			@GetMapping("/delete/user/{id}")
			public String Deleteuser(@PathVariable("id") String id, Model model, HttpSession session) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Admin))
					return "Login";
				else {
					userrepo.deleteById(id);
					model.addAttribute("users", userrepo.findAll());
					return "ManageUsers";
				}
			}
			
			
			
			
			
			/*-----------------------------------------Course CRUD-------------------------------------------*/
			
			
			@GetMapping("/addnewcourse")
			public String addnewcourse(HttpSession session) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Admin))
					return "Login";
				return "AddCourse";
			}
			
			@GetMapping("/viewallcourses")
			public String viewallcourses(@PageableDefault(size = 100) Pageable pageable, Model model, HttpSession session) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Admin))
					return "Login";
				model.addAttribute("courses", courserepo.findAll());
				return "ManageCourses";
			}
			
			@GetMapping("/edit/course/{id}")
			public String Editcourse(@PathVariable("id") String id, Model model, HttpSession session) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Admin))
					return "Login";
				else {
					model.addAttribute("course", model.addAttribute(courserepo.findById(id)));
					return "courseinfoeditpage";
				}
			}
			
			@PostMapping("/savenewcourse")
			public String savenewCourse(@RequestParam("CourseCredit") String coursecredit, @ModelAttribute("course") Course course, Model model) {
				if(courserepo.existsById(course.getCourseCode())) {
					String courseerror = "Course Code Exists";
					model.addAttribute("errormsg", courseerror);
					return "addnewcourse";
				}
				else {
					course.setCourseCredit(Integer.parseInt(coursecredit));
					courserepo.save(course);
					model.addAttribute("successmsg", "course successufully saved ");
					return "AddCourse";
				}
			}
			
			@PutMapping("/update/course/{id}")
			public String UpdateCourse(@ModelAttribute("course") Course course, Model model, HttpSession session) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Admin))
					return "Login";
				else {
					courserepo.save(course);
					model.addAttribute("course", courserepo.findAll());
					model.addAttribute("successmsg", "course info updated sucessfully");
					return "ManageCourses";
				}
			}
			
			
			@GetMapping("/delete/course/{id}")
			public String DeleteCourse(@PathVariable("id") String id, Model model, HttpSession session) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Admin))
					return "Login";
				else {
					courserepo.deleteById(id);
					model.addAttribute("courses", courserepo.findAll());
					model.addAttribute("successmsg", "course deleted sucessfully");
					return "ManageCourses";
				}
			}
			
			
			/*-----------------------------------------Create Schedule-------------------------------------------*/
			
			@GetMapping("/createschedule")
			public String CreateSchedule( Model model, HttpSession session) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Admin))
					return "Login";
				else {
					int availableyears = LocalDate.now().getYear() + 1;
					
					List <String> years =new ArrayList<>();
					years.add(String.valueOf(availableyears));
					years.add(String.valueOf(availableyears + 1));
					model.addAttribute("years", years);
					return "CreateSchedule";
				}
			}
			
			@GetMapping("/manageschedule")
			public String ManageSchedule( Model model, HttpSession session) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Admin))
					return "Login";
				else {
					List <Schedule> schedules = (List<Schedule>) schedulerepo.findAll();
					
					
					
					for(Schedule sch : schedules)
					{
						List<Block> blks = new ArrayList<>(sch.getBlocks());
						blks = blks.stream().sorted((b1, b2) -> b1.getStartDate().compareTo(b2.getStartDate())).collect(Collectors.toList());
//						blks = blks.stream().sorted((b1,b2) -> {
//							if (b1.getStartDate().before(b2.getStartDate()))
//								return 1;
//							else if (b2.getStartDate().before(b1.getStartDate()))
//								return -1;
//							return 0;
//						}).collect(Collectors.toList());
						sch.setBlocks(new HashSet<>(blks));
						System.out.println(sch.getBlocks());
					}
					model.addAttribute("schedules", schedules);
					return "ManageSchedule";
				}
			}
			
			@PostMapping("/createschedule")
			public String CreateSchedule(@RequestParam("ScheduleID") String id, HttpSession session, Model model) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Admin))
					return "Login";
				else {
					if(schedulerepo.findById(id).isPresent()) {
						model.addAttribute("errormsg", "schedule for that year exists");
						return "CreateSchedule";
					}
					else {
						//Create new Schedule Instance 
						Schedule sch = new Schedule();
						sch.setScheduleId(id);
						String Startdate = "01 Jan " + id;
						String Enddate = "31 Dec " + id;
						Date startdate = null;
						Date enddate = null;
						try {
							startdate = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).parse(Startdate);;
							enddate = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).parse(Enddate);;
						} catch (ParseException e) {
							e.printStackTrace();
						}
						System.out.println(startdate);
						System.out.println(startdate);
						sch.setStartDate(startdate);
						sch.setEndDate(enddate);
						
						//create blocks for the schedule
						for(int month =  sch.getStartDate().getMonth(); month<12;  month++) 
						{
							//create new block for each month
							Block block = new Block();
							DateFormatSymbols x =new DateFormatSymbols();
							block.setBlockId(x.getMonths()[month]+" "+id);
							String BlockStartdate = "01 " +  month + " " + id;
							String BlockEnddate = "28 " + month + " " + id;
							Date Blockstartdate = null;
							Date Blockenddate = null;
							try {
								Blockstartdate = new SimpleDateFormat("dd M yyyy", Locale.ENGLISH).parse(BlockStartdate);;
								Blockenddate = new SimpleDateFormat("dd M yyyy", Locale.ENGLISH).parse(BlockEnddate);;
							} catch (ParseException e) {
								e.printStackTrace();
							}
							block.setStartDate(Blockstartdate);
							block.setEndDate(Blockenddate);
							
							
							//case 1 for createing new classes for that block january - may - june - october
							if(month  == 0 || month == 5 || month == 6 || month == 9)
							{
								List <CourseClass> classes = createcoursesforblock("First", x.getMonths()[month] + id);
								for(CourseClass c : classes) {
										block.addcourseclass(c);
								}
							}
							//case 2 for creating new classes for that block february - april - august - november
							else if(month  == 1 || month == 3 || month == 7 || month == 10) {
								List <CourseClass> classes = createcoursesforblock("Second", x.getMonths()[month] + id);
								for(CourseClass c : classes) {
									block.addcourseclass(c);
								}
							}
							
							//case 3 for creating new classes for that block 
							else {
								List <CourseClass> classes = createcoursesforblock("Third", x.getMonths()[month] + id);
								for(CourseClass c : classes) {
									block.addcourseclass(c);
								}
							}
			
							//save block
							blockrepo.save(block);
							for(CourseClass c : block.getClasses()) {
								c.setBlock(block);
								courseclassrepo.save(c);
							}
							blockrepo.save(block);
							
							//add block to schedule 
							sch.addblock(block);
			
						} 
						List<Block> blks = new ArrayList<>(sch.getBlocks());
						blks.sort((Block b1, Block b2) -> b1.getStartDate().compareTo(b2.getStartDate()));
						Set<Block> set = new HashSet<>(blks);
						sch.setBlocks(set);			
						schedulerepo.save(sch);
						model.addAttribute("schedule", sch);
						return "CreateSchedule";
					}
				}
			}
			public List<CourseClass> createcoursesforblock(String id, String month)
			{
				List<CourseClass> blockclasses = new ArrayList<>();
				
				if(id == "First") {
					CourseClass MPP = new CourseClass();
					MPP.setCourseClassId(courserepo.findById("CS401").get().getCourseCode() + month);
					MPP.setCourse(courserepo.findById("CS401").get());
					MPP.setAvailableseats(25);
					courseclassrepo.save(MPP);
					
					Course c = courserepo.findById("CS401").get();
					c.addcourseclass(MPP);
					courserepo.save(c);
					blockclasses.add(MPP);
				}
				
				else if(id == "Second") {
					CourseClass MPP = new CourseClass();
					MPP.setCourseClassId("1" + courserepo.findById("CS401").get().getCourseCode() + month);
					MPP.setCourse(courserepo.findById("CS401").get());
					MPP.setAvailableseats(25);
					courseclassrepo.save(MPP);
					
					CourseClass MPP2 = new CourseClass();
					MPP2.setCourseClassId("2" + courserepo.findById("CS401").get().getCourseCode() + month);
					MPP2.setCourse(courserepo.findById("CS401").get());
					MPP2.setAvailableseats(25);
					courseclassrepo.save(MPP2);
					
					CourseClass FPP1 = new CourseClass();
					FPP1.setCourseClassId("1" + courserepo.findById("CS390").get().getCourseCode() + month);
					FPP1.setCourse(courserepo.findById("CS390").get());
					FPP1.setAvailableseats(25);
					courseclassrepo.save(FPP1);
					
					CourseClass FPP2 = new CourseClass();
					FPP2.setCourseClassId("2" + courserepo.findById("CS390").get().getCourseCode() + month);
					FPP2.setCourse(courserepo.findById("CS390").get());
					FPP2.setAvailableseats(25);
					courseclassrepo.save(FPP2);
					
					Course c = courserepo.findById("CS401").get();
					c.addcourseclass(MPP);
					c.addcourseclass(MPP2);
					courserepo.save(c);
					
					c = courserepo.findById("CS390").get();
					c.addcourseclass(FPP1);
					c.addcourseclass(FPP2);
					courserepo.save(c);
					
					blockclasses.add(MPP);
					blockclasses.add(MPP2);
					blockclasses.add(FPP1);
					blockclasses.add(FPP2);
				}
				else if(id == "Third") {
					CourseClass MPP = new CourseClass();
					MPP.setCourseClassId("1" + courserepo.findById("CS401").get().getCourseCode() + month);
					MPP.setCourse(courserepo.findById("CS401").get());
					MPP.setAvailableseats(25);
					courseclassrepo.save(MPP);
					
					CourseClass MPP2 = new CourseClass();
					MPP2.setCourseClassId("2" + courserepo.findById("CS401").get().getCourseCode() + month);
					MPP2.setCourse(courserepo.findById("CS401").get());
					MPP2.setAvailableseats(25);
					courseclassrepo.save(MPP2);
					
					CourseClass FPP1 = new CourseClass();
					FPP1.setCourseClassId("1" + courserepo.findById("CS390").get().getCourseCode() + month);
					FPP1.setCourse(courserepo.findById("CS390").get());
					FPP1.setAvailableseats(25);
					courseclassrepo.save(FPP1);
					
					Course c = courserepo.findById("CS401").get();
					c.addcourseclass(MPP);
					c.addcourseclass(MPP2);
					courserepo.save(c);
					
					c = courserepo.findById("CS390").get();
					c.addcourseclass(FPP1);
					
					blockclasses.add(MPP);
					blockclasses.add(MPP2);
					blockclasses.add(FPP1);
					
				}
				
				List <Course> courses = (List<Course>) courserepo.findAll();
				int count = 0;
				for(Course c : courses) {
					if(count <= 8 && !c.getCourseCode().equals(courserepo.findById("CS401").get().getCourseCode()) && !c.getCourseCode().equals(courserepo.findById("CS390").get().getCourseCode()))
					{
						CourseClass courseclass = new CourseClass();
						courseclass.setCourseClassId(c.getCourseCode() + month);
						courseclass.setCourse(c);
						courseclass.setAvailableseats(25);
						courseclassrepo.save(courseclass);
						
						c.addcourseclass(courseclass);
						courserepo.save(c);
						blockclasses.add(courseclass);
					}	
				}
				return blockclasses;
			}
			
			@GetMapping("/schedule/delete/{id}")
			public RedirectView DeleteSchedule(@PathVariable("id") String id, Model model, HttpSession session) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Admin))
					return new RedirectView("/login");
				else {
					for(Schedule sch : schedulerepo.findAll()) {
						for(Block blk : sch.getBlocks()) {
							for(CourseClass cls : blk.getClasses()) {
								courseclassrepo.deleteById(cls.getCourseClassId());
							}
							blockrepo.save(blk);
							blockrepo.deleteById(blk.getBlockId());
						}
						schedulerepo.save(sch);
						schedulerepo.deleteById("id");
					}
					return new RedirectView("/manageschedule");
				}
			}
			
			
			/*------------------------------------------Class Delete------------------------------------------------------------*/
			@GetMapping("/delete/schedule/{id}")
			public String Deleteclass(@PathVariable("id") String id, Model model, HttpSession session) {
				if(session.getAttribute("userid") == null || !(session.getAttribute("userid") instanceof Admin))
					return "Login";
				else {
					courseclassrepo.deleteById(id);
					model.addAttribute("schedules", schedulerepo.findAll());
					return "ManageSchedule";
				}
			}
}
