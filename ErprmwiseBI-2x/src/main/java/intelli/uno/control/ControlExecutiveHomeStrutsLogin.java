package intelli.uno.control;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import intelli.uno.dto.DtoExecutiveHome;
import intelli.uno.dto.DtoPrincipleCustomer;
import intelli.uno.entity.EntityBusinessunitMaster;
import intelli.uno.entity.EntityCityMaster;
import intelli.uno.entity.EntityCustomerMaster;
import intelli.uno.entity.EntityEngineerMaster;
import intelli.uno.entity.EntityLocationMaster;
import intelli.uno.entity.EntityRegionMaster;
import intelli.uno.entity.EntityStateMaster;
import intelli.uno.repository.RepositoryEntityIncidentMaster;
import intelli.uno.service.ServicesUserCommonSessionContainer;
import intelli.uno.service.entitybusinessunitmaster.ServiceEntityBusinsessunitMaster;
import intelli.uno.service.entitycitymaster.ServiceEntityCityMaster;
import intelli.uno.service.entitycustomermaster.ServiceEntityCustomerMaster;
import intelli.uno.service.entityengineermaster.ServiceEntityEngineerMaster;
import intelli.uno.service.entitylocationmaster.ServiceEntityLocationMaster;
import intelli.uno.service.entityprincipalcustomermaster.ServiceEntityPrincipalcustomerMaster;
import intelli.uno.service.entityregionmaster.ServiceEntityRegionMaster;
import intelli.uno.service.entitystatemaster.ServiceEntityStateMaster;

@Controller
@RequestMapping(value = "/executivehomes", method = {RequestMethod.POST, RequestMethod.GET},name = "EXECUTIVE HOME SCREEN")
public class ControlExecutiveHomeStrutsLogin {

	@Autowired
	private ServiceEntityEngineerMaster serviceEntityEngineerMaster;

	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private ServicesUserCommonSessionContainer workArea;

	@Autowired
	private ServiceEntityBusinsessunitMaster serviceEntityBusinsessunitMaster;
	
	@Autowired
	private ServiceEntityRegionMaster serviceEntityRegionMaster;
	
	@Autowired
	private ServiceEntityStateMaster serviceEntityStateMaster;
	
	@Autowired
	private ServiceEntityLocationMaster serviceEntityLocationMaster;
	
	@Autowired
	private ServiceEntityCityMaster serviceEntityCityMaster;
	
	@Autowired
	private ServiceEntityPrincipalcustomerMaster serviceEntityPrincipalcustomerMaster;
	
	@Autowired
	private ServiceEntityCustomerMaster serviceEntityCustomerMaster;
	
	@Autowired
	private RepositoryEntityIncidentMaster repositoryEntityIncidentMaster;
	
	@ModelAttribute("dtoexhome")
	public DtoExecutiveHome userRegistrationDTO() {
		return new DtoExecutiveHome();
	}

	
	/* FOR EXECUTIVE LOGIN MEANS USER MUST HAVE SD ROLE MANDATORY TO ACCESS THIS*/
	@SuppressWarnings("unused")
	@GetMapping
	public String getExecutiveHome(HttpSession session, Model model,HttpServletRequest request) {
		
		EntityEngineerMaster entityEngineerMaster = (EntityEngineerMaster) session.getAttribute("userinfo");

		if (entityEngineerMaster == null) {	
			
			
			String m_strAction = request.getParameter("login");
			if(m_strAction!=null && m_strAction.equals("")==false) {
				  try {
					  /*Developed For LOGIN between erprmwise */
						String m_strUserId = request.getParameter("userid");
						String m_strUserLogin = request.getParameter("username");
						String m_strUserPassword = request.getParameter("password");
						String m_strRoleName = request.getParameter("roleid");
						String m_strGeneratedToken = request.getParameter("generatedToken");
						if(m_strUserLogin != null && m_strUserLogin.equals("")==false) {
							  try {
								  entityEngineerMaster = serviceEntityEngineerMaster.loadUserByUsername2(m_strUserLogin,m_strUserPassword);  
							  }catch(Exception ex) { ex.printStackTrace();  }
						}
						session.setAttribute("userinfo", entityEngineerMaster);  // Create session and store session information
				    /*Developed For LOGIN between erprmwise */
				  }catch(Exception ex) {  ex.printStackTrace(); }
			}else { 	return "redirect:/login"; }
			
	    }else {  session.setAttribute("previousURL", "/executivehome"); }  //String currentURL = request.getRequestURL().toString();
		
		/*Setting GlobalVariable For Users*/
		model.addAttribute("username", entityEngineerMaster.getFnameEm()+" "+entityEngineerMaster.getLnameEm());
		workArea.getUserDetailsForService(applicationContext,entityEngineerMaster);
		
		List<EntityStateMaster> l_objStateList=new ArrayList<>();
		List<EntityLocationMaster> l_objPopList = new ArrayList<>();
		List<EntityCityMaster> l_objCityList=new ArrayList<>();
		List<EntityBusinessunitMaster> l_objBuList=new ArrayList<>();
		List<DtoPrincipleCustomer> l_objPCustList=new ArrayList<>();
		List<EntityRegionMaster> l_objRegionList=new ArrayList<>();
		List<EntityCustomerMaster> l_objCustomerList=new ArrayList<>();
		
		int n_OpenCount=0;
		int n_PausedCount=0;		
		int n_TechClosedCount=0;
		int n_ClosedCount=0;
		int n_ReopenCount=0;
		int n_cancelledCount=0;
		int n_unassignedCount=0;
		int n_slaViolatedCount=0;
		
		
		
        /**Getting BusinessUnit From Login User**/
		
			String m_strWorkAreaBuId=entityEngineerMaster.getTypeidBumEm();
			String[] strArray = m_strWorkAreaBuId.split(",");
			
			ArrayList<Long> l_longBuIdList = new ArrayList<>(strArray.length);
			for (String s : strArray) {
			    l_longBuIdList.add(Long.parseLong(s));
			}
			
			try {
				
			}catch(Exception ex) { ex.printStackTrace(); }
		  
		    
		/**Getting BusinessUnit From Login User**/
		 
		try {
			l_objRegionList=serviceEntityRegionMaster.returnListOfRegionWithKeyValue();
			model.addAttribute("regionlist", l_objRegionList);

			l_objStateList = serviceEntityStateMaster.returnListOfAllStateWithKeyValue("N");
			model.addAttribute("statelist", l_objStateList);
			
			l_objPopList=serviceEntityLocationMaster.returnListOfAllPoplocationWithKeyValue("N");
			model.addAttribute("poplist", l_objPopList);
			
			l_objCityList = serviceEntityCityMaster.returnListOfAllCityWithKeyValue("N");
			model.addAttribute("citylist", l_objCityList);
			
			try {
				l_objBuList=serviceEntityBusinsessunitMaster.returnListOfBuForSpecificUser(l_longBuIdList,"N");
				model.addAttribute("bulist", l_objBuList);
			}catch(Exception ex) { ex.printStackTrace(); }

		    l_objPCustList=serviceEntityPrincipalcustomerMaster.returnListOfAllPcustomer("N");
	        model.addAttribute("dropPClist",l_objPCustList);
	        
	        l_objCustomerList=serviceEntityCustomerMaster.returnListOfAllCustomer("N");
	        model.addAttribute("dropCustlist",l_objCustomerList);
	        
	        n_OpenCount=repositoryEntityIncidentMaster.retunCountOfExecuitveDashboardHearderAll_OPEN_Count();
	        model.addAttribute("n_OpenCount",n_OpenCount);
	        
	        n_PausedCount=repositoryEntityIncidentMaster.retunCountOfExecutiveDashboardHeaderAll_PAUSED_Count();
	        model.addAttribute("n_PausedCount",n_PausedCount);
	        
	        n_TechClosedCount=repositoryEntityIncidentMaster.returnCountOfExecutiveDashboardHeaderAll_TECHCLOSED_Count();
	        model.addAttribute("n_TechClosedCount",n_TechClosedCount);
	        
	        n_ClosedCount=repositoryEntityIncidentMaster.returnCountOfExecutiveDashboardHeaderAll_CLOSED_Count();
	        model.addAttribute("n_ClosedCount",n_ClosedCount);
	        
	        n_ReopenCount=repositoryEntityIncidentMaster.returnCountOfExecutiveDashboardHeaderAll_REOPEN_Count();
	        model.addAttribute("n_ReopenCount",n_ReopenCount);
	        
	        n_cancelledCount=repositoryEntityIncidentMaster.returnCountOfExecutveDashboardHeaderAll_CANCELLED_COUNT();
	        model.addAttribute("n_cancelledCount",n_cancelledCount);
	        
	        n_unassignedCount=repositoryEntityIncidentMaster.returnCountOfExecutveDashboardHeaderAll_UNASSIGNED_COUNT();
	        model.addAttribute("n_unassignedCount",n_unassignedCount);
	        
	        n_slaViolatedCount=repositoryEntityIncidentMaster.returnCountOfExecutveDashboardHeaderAll_SLA_VIOLATED_COUNT();
	        model.addAttribute("n_slaViolatedCount",n_slaViolatedCount);
		}catch(Exception ex) { ex.printStackTrace(); }

		return "webpages/page_homeexecutive";
	}

	
}
