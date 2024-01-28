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

import intelli.uno.dto.DtoPrincipleCustomer;
import intelli.uno.dto.DtoRealTimeMargin;
import intelli.uno.entity.EntityEngineerMaster;
import intelli.uno.service.entityprincipalcustomermaster.ServiceEntityPrincipalcustomerMaster;

@Controller
public class ControlRealTimeMargin {

	
	@Autowired
	private ServiceEntityPrincipalcustomerMaster serviceEntityPrincipalcustomerMaster;
	
	
	@ModelAttribute("dtorealtimeapproximation")
	public DtoRealTimeMargin userRegistrationDTO() {
		return new DtoRealTimeMargin();
	}
	
	
	@GetMapping("/realtimecalculation")
	public String realBIMarginCalculation(Model model,HttpSession session) {
		EntityEngineerMaster entityEngineerMaster = (EntityEngineerMaster) session.getAttribute("userinfo");
		if (entityEngineerMaster == null) {	return "redirect:/login"; }
		else {  session.setAttribute("previousURL", "/margin/directapproximation"); }  //String currentURL = request.getRequestURL().toString();
		
		model.addAttribute("username", entityEngineerMaster.getFnameEm()+" "+entityEngineerMaster.getLnameEm());
		
		List<DtoPrincipleCustomer> l_objPCustList=new ArrayList<>();
	
		model.addAttribute("INR","INR");
		
		List<String> m_strPFYMain = this.generateYearRanges();
		Collections.reverse(m_strPFYMain);
		model.addAttribute("m_strPFY",m_strPFYMain);
		
		String m_strPrevious_Current=m_strPFYMain.get(0);
        model.addAttribute("m_strPrevious_Current",m_strPrevious_Current);
        
        model.addAttribute("periodicType","YTD");
        
		
		 l_objPCustList=serviceEntityPrincipalcustomerMaster.returnListOfAllPcustomer("N");
	     model.addAttribute("dropPClist",l_objPCustList);
	        
		//return "views/view_realtimemargin";
	     return "webpages/page_calculationreal";
	}
	
	@GetMapping("/realtimemargin")
	public String realBIMarginApproximation(Model model,HttpSession session) {
		EntityEngineerMaster entityEngineerMaster = (EntityEngineerMaster) session.getAttribute("userinfo");
		if (entityEngineerMaster == null) {	return "redirect:/login"; }
		else {  session.setAttribute("previousURL", "/margin/directapproximation"); }  //String currentURL = request.getRequestURL().toString();
		model.addAttribute("username", entityEngineerMaster.getFnameEm()+" "+entityEngineerMaster.getLnameEm());
		
		List<DtoPrincipleCustomer> l_objPCustList=new ArrayList<>();
		
		 l_objPCustList=serviceEntityPrincipalcustomerMaster.returnListOfAllPcustomer("N");
	     model.addAttribute("dropPClist",l_objPCustList);
	        
		return "webpages/page_realtimezingrid";
	     
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
