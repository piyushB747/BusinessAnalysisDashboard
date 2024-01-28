package intelli.uno.control;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import intelli.uno.dto.margin.DtoBI_MarginApproximation;
import intelli.uno.service.entitycustomermaster.ServiceEntityCustomerMaster;
import intelli.uno.service.entityprincipalcustomermaster.ServiceEntityPrincipalcustomerMaster;
import intelli.uno.dto.DtoPrincipleCustomer;
import intelli.uno.entity.EntityCustomerMaster;
import intelli.uno.entity.EntityEngineerMaster;


@Controller
@RequestMapping("/margin")
public class ControlMarginCalculation {

	
	@Autowired
	private ServiceEntityPrincipalcustomerMaster serviceEntityPrincipalcustomerMaster;
	
	@Autowired
	private ServiceEntityCustomerMaster serviceEntityCustomerMaster;
	
	@ModelAttribute("dtobimarginapproximation")
	public DtoBI_MarginApproximation userRegistrationDTO() {
		return new DtoBI_MarginApproximation();
	}

	@GetMapping("/directapproximation")
	public String getDirectMarginPageView(HttpSession session,Model model) {
		EntityEngineerMaster entityEngineerMaster = (EntityEngineerMaster) session.getAttribute("userinfo");
		
		if (entityEngineerMaster == null) {	return "redirect:/login"; }
		else {  session.setAttribute("previousURL", "/margin/directapproximation"); }  //String currentURL = request.getRequestURL().toString();
		
		model.addAttribute("username", entityEngineerMaster.getFnameEm()+" "+entityEngineerMaster.getLnameEm());
		
		model.addAttribute("INR","INR");
		model.addAttribute("costperticket","1000");
		
		List<String> m_strPFYMain = this.generateYearRanges();
		Collections.reverse(m_strPFYMain);
		model.addAttribute("m_strPFY",m_strPFYMain);
		
		
        String m_strPrevious_Current=m_strPFYMain.get(0);
        model.addAttribute("m_strPrevious_Current",m_strPrevious_Current);
        
        
        model.addAttribute("periodicType","YTD");
        
		List<DtoPrincipleCustomer> l_objPCustList=new ArrayList<>();
		List<EntityCustomerMaster> l_objCustomerList=new ArrayList<>();
		try {
			l_objPCustList=serviceEntityPrincipalcustomerMaster.returnListOfAllPcustomer("N");
	        model.addAttribute("dropPClist",l_objPCustList);
	        
	        l_objCustomerList=serviceEntityCustomerMaster.returnListOfAllCustomer("N");
	        model.addAttribute("dropCustlist",l_objCustomerList);
		}catch(Exception ex) { ex.printStackTrace(); }		
       
		return "webpages/page_directmargin";     
    		
		
	}
	
	
	@GetMapping("/indirectapproximation")
	public String getINDirectMarginPageView(HttpSession session,Model model) {
		EntityEngineerMaster entityEngineerMaster = (EntityEngineerMaster) session.getAttribute("userinfo");
		
		if (entityEngineerMaster == null) {	return "redirect:/login"; }
		else {  session.setAttribute("previousURL", "/margin/directapproximation"); }  //String currentURL = request.getRequestURL().toString();
		
		model.addAttribute("username", entityEngineerMaster.getFnameEm()+" "+entityEngineerMaster.getLnameEm());
		
		model.addAttribute("INR","INR");
		model.addAttribute("costperticket","1000");
		
		model.addAttribute("travelcost","1000");
		model.addAttribute("sparecost","1000");
		
		List<String> m_strPFYMain = this.generateYearRanges();
		Collections.reverse(m_strPFYMain);
		model.addAttribute("m_strPFY",m_strPFYMain);
		
		
        String m_strPrevious_Current=m_strPFYMain.get(0);
        model.addAttribute("m_strPrevious_Current",m_strPrevious_Current);
        
        
        model.addAttribute("periodicType","YTD");
        
		List<DtoPrincipleCustomer> l_objPCustList=new ArrayList<>();
		List<EntityCustomerMaster> l_objCustomerList=new ArrayList<>();
		try {
			l_objPCustList=serviceEntityPrincipalcustomerMaster.returnListOfAllPcustomer("N");
	        model.addAttribute("dropPClist",l_objPCustList);
	        
	        l_objCustomerList=serviceEntityCustomerMaster.returnListOfAllCustomer("N");
	        model.addAttribute("dropCustlist",l_objCustomerList);
		}catch(Exception ex) { ex.printStackTrace(); }		
       
		return "webpages/page_indirectmargin";     
    		
		
	}
	
	 public  List<String> generateYearRanges() {
		    int currentYear = Year.now().getValue();
	        List<String> yearRanges = new ArrayList<>();

	        for (int startYear = 2018; startYear < currentYear; startYear++) {
	            int endYear = startYear + 1;
	            String yearRange = startYear + "-" + endYear;
	            yearRanges.add(yearRange);
	        }
	        return yearRanges;
	    }
}
