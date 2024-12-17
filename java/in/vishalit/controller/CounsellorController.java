package in.vishalit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import in.vishalit.dto.DashBoardResponse;
import in.vishalit.entity.Counsellor;
import in.vishalit.service.CounsellorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {

	private CounsellorService counsellorService;

	public CounsellorController(CounsellorService counsellorService) {
		this.counsellorService = counsellorService;
	}

	@GetMapping("/")
	public String index(Model model) {
		Counsellor cobj = new Counsellor();

		// sending the data from controller to UI
		model.addAttribute("counsellor", cobj);

		// returning view name
		return "index";
	}

	@PostMapping("/login")
	public String handleLogin(Counsellor counsellor, HttpServletRequest request, Model model) {

		Counsellor c = counsellorService.login(counsellor.getEmail(), counsellor.getPwd());

		if (c == null) {
			model.addAttribute("emsg", "Invalid Credentials");
			return "index";

		} else {

			// valid login,store counsellorId in session for future purpose
			HttpSession session = request.getSession(true);
			session.setAttribute("counsellorId", c.getCounsellorId());

			
			return "redirect:/dashboard";
		}

	}
	
	@GetMapping("/dashboard")
	public String displayDashboard(HttpServletRequest req,Model model) {
		//get existing session obj
		
				HttpSession session = req.getSession(false);
				Integer counsellorId =(Integer) session.getAttribute("counsellorId");
		
				DashBoardResponse dbobj = counsellorService.getDashboardInfo(counsellorId);
				model.addAttribute("dashboardInfo", dbobj);
		return "dashboard";
	}
	
	@GetMapping("/register")
	public String registerPage(Model model) {
		Counsellor cobj = new Counsellor();
		// sending the data from controller to UI
		model.addAttribute("counsellor", cobj);
		
		return "register";

	}
	
	@PostMapping("register")
	public String handleRegistration(Counsellor counsellor,Model model) {
		
		Counsellor byEmail = counsellorService.findByEmail(counsellor.getEmail());
		
		if(byEmail != null) {
			model.addAttribute("emsg", "Duplicate Email");
			return "register";
		}
		
		boolean isRegistered = counsellorService.register(counsellor);
		
		if(isRegistered) {
			//success
			model.addAttribute("smsg", "Registration Success..!!");
		}else {
			//failure
			model.addAttribute("emsg", "Registration Failed..!!");
		}
		
		return "register";
	}

	@GetMapping("logout")
	public String logout(HttpServletRequest req) {

		// get existing session and invalidate it
		HttpSession session = req.getSession(false);
		session.invalidate();

		// redirect to login page
		return "redirect:/";
	}
}
