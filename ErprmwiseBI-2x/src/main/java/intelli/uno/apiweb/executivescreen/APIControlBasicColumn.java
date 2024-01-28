package intelli.uno.apiweb.executivescreen;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import intelli.uno.bean.BeansGlobalUserVariable;
import intelli.uno.commons.CommonConstants;
import intelli.uno.commons.CommonUtils;
import intelli.uno.dto.DtoRequestObject;
import intelli.uno.dto.executivescreen.DtoCount;
import intelli.uno.repository.RepositoryEntityLocationMaster;
import intelli.uno.repository.RepositoryEntityRegionMaster;
import intelli.uno.repository.RepositoryEntityStateMaster;
import intelli.uno.repositorycustom.CustomRepostitory;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(
        name = "APIControlBasicColumn",
        description = "For ExecutiveHomeScreen, the api will fill json data to the basic column high chart"
)
@RestController
@RequestMapping(value="/getdataforbasicChartexecutivehome", method = {RequestMethod.POST, RequestMethod.GET},
		 headers = "content-type=application/json",consumes = "application/json", produces = "application/json"
          ,name = "BASIC COLUMN CHARTS DATA")
@CrossOrigin(origins="*")
public class APIControlBasicColumn {
	
	@Autowired
	private CustomRepostitory customRepoEntityIncidentMaster;

	
	@Autowired
	private RepositoryEntityRegionMaster repositoryEntityRegionMaster;
	
	@Autowired
	private RepositoryEntityStateMaster repositoryEntityStateMaster;
	
	@Autowired
	private RepositoryEntityLocationMaster repoEntityLocationMaster;
	
	@Autowired
	private  BeansGlobalUserVariable globalVariable;
	
	@SuppressWarnings("unused")
	@PostMapping
	public List<DtoCount> getBasicChartData(HttpServletRequest request, HttpServletResponse response,
			@RequestBody DtoRequestObject requestObject)
			 throws IOException {

		System.out.println(CommonConstants.cyan + " INr API APIControlBasicColumn "   +CommonConstants.reset);
		
		int year=0;
	        try {
		       year=Integer.parseInt(CommonUtils.nullToBlank(requestObject.getM_strYear(), false));
		    }catch(Exception ex) { ex.printStackTrace(); year=2023; }
		
		
		/**This  Global Variable For User Starts**/
	    String m_strBusinessUnits=globalVariable.getM_strBusinessUnit();
		String m_strProductCategory=globalVariable.getM_strMultiProductCategory(); String m_strPoplocation=globalVariable.getM_strMultiPoplocation();
		String m_strRole=globalVariable.getM_strRoleId();String m_strUserId=globalVariable.getM_strUserId();
		String m_strState=globalVariable.getM_strStateSmIm();
		List<Integer> l_intBusinesssUnit=globalVariable.getL_intBusinessUnit();
		/**This  Global Variable For User Starts**/
		
		LocalDate obj_CurrentDate = LocalDate.now();
		LocalTime currentTime = LocalTime.now();     
		
		List<String> x_Open=new ArrayList<String>(Arrays.asList("1"));
		List<String> x_Paused=new ArrayList<String>(Arrays.asList("3"));
		List<String> x_TechClosed=new ArrayList<String>(Arrays.asList("5"));
		List<String> x_Cancelled=new ArrayList<String>(Arrays.asList("7"));
		
		int openCount=0;int cancelledCount=0;int techClosedCount=0;int pausedCount=0;int slaCount=0;int n_intAllCalledLogs=0;
		
		
		
		
		String m_strRegion="",m_strStateName="",m_strPoplocationName="";
		
		String uniquerKey="";String mainViewRequestParam="";
		
		if (requestObject.getM_strRegion() != null && !requestObject.getM_strRegion().equals("undefined") && requestObject.getM_strRegion().equals("")==false
        		&&	requestObject.getM_strRegion().equals("All")==false) {
			m_strRegion=repositoryEntityRegionMaster.returnValueFromId("N", Long.parseLong(requestObject.getM_strRegion()));
			
        }
		

		if (requestObject.getM_strState() != null && !requestObject.getM_strState().equals("undefined") && requestObject.getM_strState().equals("")==false
        		&&	requestObject.getM_strState().equals("All")==false) {
			    m_strStateName=repositoryEntityStateMaster.returnValueFromId(Long.parseLong(requestObject.getM_strState()), "N");
        }
		

		
		if (requestObject.getM_strPoplocation() != null && !requestObject.getM_strPoplocation().equals("undefined") && requestObject.getM_strPoplocation().equals("")==false
        		&&	requestObject.getM_strState().equals("All")==false) {
			    m_strPoplocationName=repoEntityLocationMaster.returnValueForID(Long.parseLong(requestObject.getM_strPoplocation()), "N");
        }
		
		
		uniquerKey=obj_CurrentDate+"|65|"+currentTime;
		
		List<DtoCount> l1 = new ArrayList<>();
		for (int currentMonthNumber = 1; currentMonthNumber < 13; currentMonthNumber++) {

			DtoCount d1 = new DtoCount();
			
			String m_strOpenCancelledQuery = "select count(distinct(typeid_im)) from "+CommonConstants.db_Name+".incidentmaster where deleteflag_im='N'  "
		    		+ " and month(incdate_im)="+currentMonthNumber+" and year(incdate_im)="+year+" ";
			
			 String m_strPausedQuery = "select count(distinct(typeid_im)) from "+CommonConstants.db_Name+".incidentmaster, "+CommonConstants.db_Name+".pausemst_pm "
			    		+ " where typeid_im=incidentid_pm and deleteflag_im='N' and status_sm_im=3 "
			    		+ " and month(pausestartdate_pm)="+currentMonthNumber+" and year(pausestartdate_pm)="+year+" ";
			
			
			String m_strTechClosedQuery=" select count(*) from "+CommonConstants.db_Name+".incidentmaster, "
	        		+ " "+CommonConstants.db_Name+".incidentlogtimemst where typeid_im=incidentid "
	        		+ " and month(technicianclosedate)="+currentMonthNumber+" and year(technicianclosedate)="+year+" "
	        		+ " and technicianclosedate is not null and status_sm_im=5 ";
			
			String m_strSLAViolatedQuery=" select count(*) from "+CommonConstants.db_Name+".incidentmaster "
	        		+ " where month(incdate_im)="+currentMonthNumber+" and year(incdate_im)="+year+" "
	        		+ "   and resolutionflag_im = 'V' and engineer_engm_im is Not Null ";
			
			String m_strAllCallsLogged=" select count(*) from "+CommonConstants.db_Name+".incidentmaster "
	        		+ " where month(incdate_im)="+currentMonthNumber+" and year(incdate_im)="+year+" "
	        		+ "   ";
			
			
			if (requestObject.getM_strRegion() != null && !requestObject.getM_strRegion().equals("undefined") && requestObject.getM_strRegion().equals("")==false
	        		&&	requestObject.getM_strRegion().equals("All")==false) {
				
				
				m_strOpenCancelledQuery += " AND region_im = '" +m_strRegion+"'";
				m_strPausedQuery += " AND region_im= '" +m_strRegion+"'";
				m_strTechClosedQuery += " AND region_im = '" +m_strRegion+"'";
				m_strSLAViolatedQuery += " AND region_im = '" +m_strRegion+"'";
				m_strAllCallsLogged += " AND region_im = '" +m_strRegion+"'";
				
				if(currentMonthNumber==1) {
				mainViewRequestParam="region="+m_strRegion+",";}
				
			 }
			
			if (requestObject.getM_strState() != null && !requestObject.getM_strState().equals("undefined") && requestObject.getM_strState().equals("")==false
	        		&&	requestObject.getM_strState().equals("All")==false) {
			
				
				m_strOpenCancelledQuery += " AND state_sm_im = '" +m_strStateName+"'";
				m_strPausedQuery += " AND state_sm_im= '" +m_strStateName+"'";
				m_strTechClosedQuery += " AND state_sm_im = '" +m_strStateName+"'";
				m_strSLAViolatedQuery += " AND state_sm_im = '" +m_strStateName+"'";
				m_strAllCallsLogged += " AND state_sm_im = '" +m_strStateName+"'";
				
				if(currentMonthNumber==1) {
				mainViewRequestParam=mainViewRequestParam+"state="+m_strStateName+",";}
			}
			
			if (requestObject.getM_strPoplocation() != null && !requestObject.getM_strPoplocation().equals("undefined") && requestObject.getM_strPoplocation().equals("")==false
	        		&&	requestObject.getM_strState().equals("All")==false) {
				
				
				m_strOpenCancelledQuery += " AND location_lm_im = '" +m_strPoplocationName+"'";
				m_strPausedQuery += " AND location_lm_im= '" +m_strPoplocationName+"'";
				m_strTechClosedQuery += " AND location_lm_im = '" +m_strPoplocationName+"'";
				m_strSLAViolatedQuery += " AND location_lm_im = '" +m_strPoplocationName+"'";
				m_strAllCallsLogged += " AND location_lm_im = '" +m_strPoplocationName+"'";
				
				if(currentMonthNumber==1) {
				mainViewRequestParam=mainViewRequestParam+"location="+m_strPoplocationName+",";}
			}
			
			if (requestObject.getM_strCity() != null && !requestObject.getM_strCity().equals("undefined") && requestObject.getM_strCity().equals("")==false
	        		&&	requestObject.getM_strCity().equals("All")==false) {

				m_strOpenCancelledQuery += " AND city_cm_im = '" +requestObject.getM_strCity()+"'";
				m_strPausedQuery += " AND city_cm_im= '" +requestObject.getM_strCity()+"'";
				m_strTechClosedQuery += " AND city_cm_im = '" +requestObject.getM_strCity()+"'";
				m_strSLAViolatedQuery += " AND city_cm_im = '" +requestObject.getM_strCity()+"'";
				m_strAllCallsLogged += " AND city_cm_im = '" +requestObject.getM_strCity()+"'";
				
				
				if(currentMonthNumber==1) {
				mainViewRequestParam=mainViewRequestParam+"city="+requestObject.getM_strCity()+",";}
	        }
			
			if (requestObject.getM_strBuId() != null && !requestObject.getM_strBuId().equals("undefined") && requestObject.getM_strBuId().equals("")==false
        			&& requestObject.getM_strBuId().equals("All")==false) {
			
				m_strOpenCancelledQuery += " AND businessunit_bum_im in (" +requestObject.getM_strBuId()+")";
				m_strPausedQuery += " AND businessunit_bum_im in (" +requestObject.getM_strBuId()+")";
				m_strTechClosedQuery+= " AND businessunit_bum_im in (" +requestObject.getM_strBuId()+")";
				m_strSLAViolatedQuery+= " AND businessunit_bum_im in (" +requestObject.getM_strBuId()+")";
				m_strAllCallsLogged+= " AND businessunit_bum_im in (" +requestObject.getM_strBuId()+")";
				
				
				if(currentMonthNumber==1) {
				mainViewRequestParam=mainViewRequestParam+"bu="+requestObject.getM_strBuId()+",";}
        	} else if(m_strBusinessUnits.equals("remove")){  

        		m_strOpenCancelledQuery += " AND businessunit_bum_im in (" +m_strBusinessUnits+")";
				m_strPausedQuery+= " AND businessunit_bum_im in (" +m_strBusinessUnits+")";
				m_strTechClosedQuery+= " AND businessunit_bum_im in (" +m_strBusinessUnits+")";
				m_strSLAViolatedQuery+= " AND businessunit_bum_im in (" +m_strBusinessUnits+")";
				m_strAllCallsLogged+= " AND businessunit_bum_im in (" +m_strBusinessUnits+")";
        	}
			
			if (requestObject.getM_strPcId() != null && !requestObject.getM_strPcId().equals("undefined") && requestObject.getM_strPcId().equals("")==false
	        		&&	requestObject.getM_strPcId().equals("All")==false) {

				m_strOpenCancelledQuery += " AND principal_cm_im = '" +requestObject.getM_strPcId()+"'";
				m_strPausedQuery += " AND principal_cm_im= '" +requestObject.getM_strPcId()+"'";
				m_strTechClosedQuery += " AND principal_cm_im = '" +requestObject.getM_strPcId()+"'";
				m_strSLAViolatedQuery += " AND principal_cm_im = '" +requestObject.getM_strPcId()+"'";
				m_strAllCallsLogged += " AND principal_cm_im = '" +requestObject.getM_strPcId()+"'";
				
				if(currentMonthNumber==1) {
				mainViewRequestParam=mainViewRequestParam+"pc="+requestObject.getM_strPcId()+",";}
	        }
			
			if (requestObject.getM_strCustId() != null && !requestObject.getM_strCustId().equals("undefined") && requestObject.getM_strCustId().equals("")==false
	        		&&	requestObject.getM_strCustId().equals("All")==false) {

				m_strOpenCancelledQuery += " AND customer_cm_im = '" + requestObject.getM_strCustId()+"' ";
				m_strPausedQuery += " AND customer_cm_im= '" + requestObject.getM_strCustId()+"' ";
				m_strTechClosedQuery += " AND customer_cm_im= '" + requestObject.getM_strCustId()+"' ";
				m_strSLAViolatedQuery += " AND customer_cm_im= '" + requestObject.getM_strCustId()+"' ";
				m_strAllCallsLogged += " AND customer_cm_im= '" + requestObject.getM_strCustId()+"' ";
				
				if(currentMonthNumber==1) {
				mainViewRequestParam=mainViewRequestParam+"cust="+requestObject.getM_strCustId()+",";}
	        }

			
			try {
        		String openQuery = m_strOpenCancelledQuery + " AND status_sm_im = '1' ";
        		openCount = customRepoEntityIncidentMaster.getCountsParameterizedNativeQuery(openQuery);
        	} catch (Exception ex) {  ex.printStackTrace();}
			
			try {
        		String cancelledQuery=m_strOpenCancelledQuery + " AND status_sm_im = '7' ";
        		cancelledCount=customRepoEntityIncidentMaster.getCountsParameterizedNativeQuery(cancelledQuery);   
			} catch (Exception ex) {  ex.printStackTrace();}
			
			try {
				
				pausedCount=customRepoEntityIncidentMaster.getCountsParameterizedNativeQuery(m_strPausedQuery);
			} catch (Exception ex) {  ex.printStackTrace();}
			
			try {
				techClosedCount=customRepoEntityIncidentMaster.getCountsParameterizedNativeQuery(m_strTechClosedQuery);
			} catch (Exception ex) {  ex.printStackTrace();}
			
           try {
        	   slaCount=customRepoEntityIncidentMaster.getCountsParameterizedNativeQuery(m_strSLAViolatedQuery);
			} catch (Exception ex) {  ex.printStackTrace();}
          
            try {
            	 n_intAllCalledLogs=customRepoEntityIncidentMaster.getCountsParameterizedNativeQuery(m_strAllCallsLogged);
			} catch (Exception ex) {  ex.printStackTrace();}
			
			
			
            d1.setYear(String.valueOf(year));
	        d1.setMonth(currentMonthNumber);
	        d1.setOpen(openCount); 
	        d1.setCancelled(cancelledCount); 
	        d1.setPaused(pausedCount); 
	        d1.setTechclosed(techClosedCount);
	        d1.setAllCalledLogs(n_intAllCalledLogs);
	        d1.setSla(slaCount);	
	        String encodedParam ="";
	        String encodedStringUniqueId = "";
	        
	        try {
	          encodedParam = URLEncoder.encode(mainViewRequestParam, StandardCharsets.UTF_8.toString());
	          encodedStringUniqueId=URLEncoder.encode(uniquerKey, StandardCharsets.UTF_8.toString());
	        }catch(Exception ex) { ex.printStackTrace(); }
	        
	        d1.setViewRequestParam(encodedParam);
	        d1.setUniqueId(encodedStringUniqueId);
	        
	        l1.add(d1);
	        d1=null;
			  
		}
		  return l1;
	}
	
}
