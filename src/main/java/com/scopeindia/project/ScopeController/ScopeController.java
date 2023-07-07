package com.scopeindia.project.ScopeController;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.scopeindia.project.Model.ContactForm;
import com.scopeindia.project.Model.Course;
import com.scopeindia.project.Model.User;

import com.scopeindia.project.Respository.UserRepo;
import com.scopeindia.project.Service.CourseService;
import com.scopeindia.project.Service.ServiceClass;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@EnableAutoConfiguration
public class ScopeController {
	 @Autowired
	 private CourseService courseService;

	@Autowired
	ServiceClass service;
	private final JavaMailSender javaMailSender;
	@Autowired
	private UserRepo urepo;
	@Autowired
    public ScopeController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
        
    }
	@RequestMapping("/")
	public String index(Model model,HttpServletRequest request,HttpSession session) {
		  
        if (service.isLoggedIn(request)||session.getAttribute("user")!=null) { // Check if the user is logged in
        	
            return "redirect:/Dashboard";    // User is already logged in, redirect to profile
        }
		return"index";						// User is not logged in, show the index page
	}
	@RequestMapping("/About")
	public String About(HttpServletRequest request,HttpSession session) {
		
        if (service.isLoggedIn(request)||session.getAttribute("user")!=null) { // Check if the user is logged in
        	
            return "redirect:/Dashboard";    // User is already logged in, redirect to profile
        }
		return"About us";
	}
	@RequestMapping("/Contact")
	public String Contact(Model model,HttpServletRequest request,HttpSession session) {
		  if (service.isLoggedIn(request)||session.getAttribute("user")!=null) { // Check if the user is logged in
	        	
	            return "redirect:/Dashboard";    // User is already logged in, redirect to profile
	        }
		model.addAttribute("contactForm",new ContactForm());
		return"Contact";
	}
	 @PostMapping("/sendemail")
	    public String sendEmail( @ModelAttribute("contactForm") ContactForm contactForm, Model model)throws MailException {
	        
	            // Create a SimpleMailMessage and set the relevant information
	            SimpleMailMessage message = new SimpleMailMessage();
	            message.setTo("iamameer37@gmail.com"); // Set your email address here
	            message.setSubject(contactForm.getSubject()+" : " + contactForm.getUsername());
	            message.setText(contactForm.getMessage()+ "\n\nFrom: " + contactForm.getEmail());

	            // Send the email
	            javaMailSender.send(message);
	           
	            model.addAttribute("successMessage", "Your message has been sent successfully!");
	            return "Mailsuccess";
	     
	    }
	@GetMapping("/Registration")
	public String Registration(Model model) {
		  

		model.addAttribute("user",new User());
		return"Registration";
	}
	@PostMapping("/Registration")
	public String PRegistration(@ModelAttribute("user")User user,BindingResult result,Model model,@RequestParam("avatar") MultipartFile file) throws IOException {
	    try {
	        if (result.hasErrors()) {
	          
	            return "redirect:/Registration";
	        }
	        user.setPass(service.generateRandomString());
	        user.setImage( file.getBytes());
	        urepo.save(user);
	        //password sending
	        SimpleMailMessage mailMessage = new SimpleMailMessage();
	        mailMessage.setTo(user.getEmail());
	        mailMessage.setSubject("Password");
	        mailMessage.setText("Your password is :"+user.getPass());
	        javaMailSender.send(mailMessage);
	        //password sending
	        return "Success";
	    } catch (Exception e) {
	        e.printStackTrace(); // Print the exception stack trace to the console
	        return "Error"; // or return an error view
	    }
	}
	@RequestMapping("/Login")
	public String Login(Model model,HttpServletRequest request,HttpSession session) {
		  if (service.isLoggedIn(request)||session.getAttribute("user")!=null) { // Check if the user is logged in
	        	
	            return "redirect:/Dashboard";    // User is already logged in, redirect to profile
	        }
		model.addAttribute("user",new User());
		return"Login";
	}
	@RequestMapping("/Forgot")
	public String Forgot(Model model,HttpSession session) {
		model.addAttribute("user",new User());
		return"Forgot";
	}
	@PostMapping("/Forgot")
	public String PForgot(User user,BindingResult result,Model model) {
		User fuser=urepo.findByemail(user.getEmail());
		if(fuser==null) {
			
			return"redirect:/Forgot";
		}
		fuser.setPass(service.generateRandomString());
		 SimpleMailMessage mailMessage = new SimpleMailMessage();
	        mailMessage.setTo(fuser.getEmail());
	        mailMessage.setSubject("New Password");
	        mailMessage.setText("Your New password is :"+fuser.getPass());
	        javaMailSender.send(mailMessage);
	        urepo.save(fuser);
		return"PasswordSuccess";
	}

	@PostMapping("/Login")
	public String Dashboard(@ModelAttribute("user")User user, @RequestParam(value = "keepLoggedIn", required = false)boolean keepLoggedIn,BindingResult result,Model model,HttpSession session,HttpServletResponse response,HttpServletRequest request){ 
		System.out.println(keepLoggedIn);
        if (keepLoggedIn) {
            // Create a cookie with the user's email and set it to expire in a desired duration
            Cookie cookie = new Cookie("loggedInUser", user.getEmail());
            cookie.setMaxAge(30 * 24 * 60 * 60); // Cookie expires in 30 days (adjust as needed)
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        

        
		String logpass=user.getPass();
		User luser=urepo.findByemail(user.getEmail());
		if(luser!=null) {
			String p=luser.getPass();
			if(logpass.equals(p)) {
				session.setAttribute("user", luser);//session setting

				return"redirect:/Dashboard";
			}		
			return"redirect:/Login";
		}
		System.out.println("no user");
		return"redirect:/Login";
		
	}
	@GetMapping("/Dashboard")
	public String GDashboard(Model model,HttpSession session,HttpServletRequest request) {//added session
		User user=null;
    	Cookie []cookies=request.getCookies();
     	if(service.isLoggedIn(request)) {
     		List<String> email=service.getCookieValues(cookies);
     		String gemail = "";
     		for (String item : email) {
     		    if (item.contains("@")) {
     		        gemail = item;
     		        break;
     		    }
     		    }
     		    System.out.println(gemail);
     			User loginuser=urepo.findByemail(gemail);
     			List<Course> courses = courseService.getAllCourses();
     			byte[] imageData = loginuser.getImage();
     			String base64Image = Base64.getEncoder().encodeToString(imageData);
     			model.addAttribute("base64Image", base64Image);
     	        model.addAttribute("courses",courses);
     	        model.addAttribute("user",loginuser);
     		
     		return"Dashboard";
     	}else {
		   user = (User) session.getAttribute("user");//getting session
		 if(user==null) {
				return"redirect:/Login";
		}	
		 String usermail=user.getEmail();
		User loginuser=urepo.findByemail(usermail);
		List<Course> courses = courseService.getAllCourses();
		 byte[] imageData = loginuser.getImage();
		    String base64Image = Base64.getEncoder().encodeToString(imageData);
		    	model.addAttribute("base64Image", base64Image);
		    	model.addAttribute("courses",courses);
		    	model.addAttribute("user",loginuser);
		return"Dashboard";
     	}
	}
	@RequestMapping("/Profile")
	public String Profile(Model model,HttpSession session,HttpServletRequest request) {
		User user=null;
    	Cookie []cookies=request.getCookies();

     	if(service.isLoggedIn(request)) {
     		List<String> email=service.getCookieValues(cookies);
     		String gemail = "";
     		for (String item : email) {
     		    if (item.contains("@")) {
     		        gemail = item;
     		        break;
     		    }
     		}
     		System.out.println(gemail);
    		User loginuser=urepo.findByemail(gemail);
    		 byte[] imageData = loginuser.getImage();
    		 String base64Image = Base64.getEncoder().encodeToString(imageData);
    		model.addAttribute("base64Image", base64Image);
    		model.addAttribute("user",loginuser);
    		List<String> hobbies = loginuser.getHobbies();
    		String hobbiesString = String.join(", ", hobbies);
    		model.addAttribute("hobbiesString", hobbiesString);
    		
    		return"Profile";
     	}else {
     		 user = (User) session.getAttribute("user");
      		if(user==null) {
     			return"redirect:/Login";
     				}
//     		 String usermail=user.getEmail();
     			User loginuser=urepo.findByemail(user.getEmail());
     		 byte[] imageData = user.getImage();
     		 String base64Image = Base64.getEncoder().encodeToString(imageData);
     		model.addAttribute("base64Image", base64Image);
     		model.addAttribute("user",loginuser);
     		List<String> hobbies = loginuser.getHobbies();
     		String hobbiesString = String.join(", ", hobbies);
     		model.addAttribute("hobbiesString", hobbiesString);
     		
     		return"Profile";
     	}
//		System.out.println(service.isCookiesEmpty(cookies));
	
	}
	@RequestMapping("/addcourse/{name}")
	public String addcourse(@PathVariable("name")String cname,Model model,HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user==null) {
			return"redirect:/Login";
				}
		String email=user.getEmail();
		System.out.println(email);

		user.setCourse(cname);
		urepo.save(user);
		return"redirect:/Profile";
	}
	@RequestMapping("/Verify")
	public String Verify(Model model,HttpSession session) {
		User user = (User) session.getAttribute("user");
//		System.out.println(user.getEmail());
		user.setOtp(service.generateRandomNumber());
		 SimpleMailMessage mailMessage = new SimpleMailMessage();
	        mailMessage.setTo(user.getEmail());
	        mailMessage.setSubject("Otp");
	        mailMessage.setText("Your Otp is :"+user.getOtp());
	        javaMailSender.send(mailMessage);
	        byte[] imageData = user.getImage();
			 String base64Image = Base64.getEncoder().encodeToString(imageData);
			 model.addAttribute("base64Image", base64Image);
			 model.addAttribute("user",user);
	        urepo.save(user);
	    	
		    return"Verify";
		
	}
	@PostMapping("/Verify/{email}")
	public String VerifyP(@ModelAttribute("user")User user,@PathVariable("email")String mail,HttpSession session) {

		System.out.println(user.getOtp());
		User loguser=urepo.findByemail(mail);
		if(user.getOtp()==loguser.getOtp()) {
			loguser.setVerified(true);
			urepo.save(loguser);
			return"redirect:/Profile";
		}
		return"error";
	}
	@RequestMapping("/Edit")
	public String EditProfile(Model model,HttpSession session) {
		User luser = (User) session.getAttribute("user");
		String email=luser.getEmail();
		User user = urepo.findByemail(email);
		 byte[] imageData = user.getImage();
		 String base64Image = Base64.getEncoder().encodeToString(imageData);
		 model.addAttribute("base64Image", base64Image);
		 
		 model.addAttribute("user",user);
		
		return"Edit";
	}
	@PostMapping("/Edit/{email}")
	public String EditProfileP(@PathVariable("email")String email,HttpSession session,User user,BindingResult result,Model model,@RequestParam("avatar") MultipartFile file) throws IOException { 
		
		User updateduser=urepo.findByemail(email);
		System.out.println(file);
		
		  if (!file.isEmpty()) {
		        updateduser.setImage(file.getBytes());
		    }
		
		updateduser.setFirstName(user.getFirstName());
		updateduser.setLastName(user.getLastName());	
		updateduser.setPhoneNumber(user.getPhoneNumber());
		updateduser.setDob(user.getDob());
        urepo.save(updateduser);
        session.setAttribute("user", updateduser);
		return"redirect:/Profile";
	}
	@RequestMapping("/LogOut")
	public String LogOut(HttpSession session,HttpServletRequest request,HttpServletResponse response) {
		   // Remove session attribute
	    session.removeAttribute("user");
//	    session=null;
	    // Clear cookies
	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	    	service.removeCookies(cookies, response);
	    }
		return"redirect:/";
	}
	@GetMapping("/ChangePassword")
	public String Change(Model model,HttpSession session) {
		User user=(User)session.getAttribute("user");
		System.out.println(user.getEmail());
		String email=user.getEmail();
		User luser = urepo.findByemail(email);
		 byte[] imageData = luser.getImage();
		 String base64Image = Base64.getEncoder().encodeToString(imageData);
		 model.addAttribute("base64Image", base64Image);
		model.addAttribute("user",luser);
		return"ChangePassword";
		
	
	}
	@PostMapping("/ChangePassword")
	public String ChangeP(@ModelAttribute("user")User user,Model model,HttpSession session) {
		User loguser=(User)session.getAttribute("user");
		User updateduser =urepo.findByemail(loguser.getEmail());
		updateduser.setPass(user.getPass());
		urepo.save(updateduser);
		session.removeAttribute("user");
		return"redirect:/";
	}

}
