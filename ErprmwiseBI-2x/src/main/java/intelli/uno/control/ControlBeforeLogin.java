package intelli.uno.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import intelli.uno.entity.EntityEngineerMaster;
import intelli.uno.repository.RepositoryEntityEngineerMaster;
import intelli.uno.repositorycustom.CustomRepostitory;
import intelli.uno.service.entityengineermaster.ServiceEntityEngineerMaster;
import java.util.Optional;

@Controller
public class ControlBeforeLogin {

	@Autowired
	private ServiceEntityEngineerMaster serviceEntityEngineerMaster;

	@Autowired
	private RepositoryEntityEngineerMaster repositoryEntityEngineerMaster;

	@Autowired
	private CustomRepostitory customRepoEntityIncidentMaster;
	
	@GetMapping("/")
	public String loginPageHome(HttpSession session,HttpServletRequest request,HttpServletResponse response) {
		
		EntityEngineerMaster entityEngineerMaster = (EntityEngineerMaster) session.getAttribute("userinfo");
        if (entityEngineerMaster != null) {
        	String previousURL = (String) session.getAttribute("previousURL");
        	if (previousURL != null) {
        		return "redirect:"+previousURL;
            } 
        }
		return "webpages/page_login";
	}
	
	@GetMapping("/login")
	public String loginPage(HttpSession session,HttpServletRequest request,HttpServletResponse response) {
		
		EntityEngineerMaster entityEngineerMaster = (EntityEngineerMaster) session.getAttribute("userinfo");
        if (entityEngineerMaster != null) {
        	String previousURL = (String) session.getAttribute("previousURL");
        	if (previousURL != null) {
        		return "redirect:"+previousURL;
            } 
        }

		return "webpages/page_login";
	}

	@GetMapping("/passwordupdate")
	public String passwordUpdatePage(HttpSession session,HttpServletRequest request,HttpServletResponse response) {
		EntityEngineerMaster entityEngineerMaster = (EntityEngineerMaster) session.getAttribute("userinfo");
        if (entityEngineerMaster != null) {
        	String previousURL = (String) session.getAttribute("previousURL");
        	if (previousURL != null) {
        		return "redirect:"+previousURL;
            } 
        }
		return "webpages/page_updatepassword";
	}


	


	@PostMapping("/updatepasswordprocess")
	public String passwordUpdateProcess(@RequestParam(value = "userid", required = true) String userId,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password) {

		Optional<EntityEngineerMaster> e1 = repositoryEntityEngineerMaster.findById(Long.valueOf(userId));
		if (e1.isPresent()) {
			EntityEngineerMaster entityEngineerMst = e1.get();
			try {
				customRepoEntityIncidentMaster.insertEngineerRoleNativeQuery(userId, entityEngineerMst.getRoleRmEm());
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			try {
				serviceEntityEngineerMaster.updateExistingEngineerWithNewPassword(Long.valueOf(userId), password);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return "redirect:/passwordupdate";
	}

	
	
	@GetMapping("/succeshome")
	public String defaultSuccessHome(HttpSession session) {
		EntityEngineerMaster entityEngineerMaster = (EntityEngineerMaster) session.getAttribute("userinfo");
		System.out.println(entityEngineerMaster + " Information");
		if (entityEngineerMaster == null) {
			return "redirect:/login";
		}
		System.out.println("Login SuccessFull");
		return "successhome1";
	}

}
