package intelli.uno.apiweb.margin;


import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import intelli.uno.commons.CommonUtils;
import intelli.uno.dto.margin.realtime.DtoJsonEngRealTimeMargin;
import intelli.uno.dto.margin.realtime.DtoRealTimeJsonResponse;
import intelli.uno.dto.margin.realtime.DtoTableHeader;
import intelli.uno.dto.margin.realtime.JsonResponseRealTime;
import intelli.uno.repository.RepositoryEntityContractDetailsnewMaster;
import intelli.uno.repository.RepositoryEntityCustomerMaster;
import intelli.uno.repository.RepositoryEntityEngineerMaster;
import intelli.uno.repository.RepositoryEntityPrincipalcustomerMaster;
import intelli.uno.repositorycustom.CustomRepostitory;
import intelli.uno.service.entitycustomermaster.ServiceEntityCustomerMaster;
import intelli.uno.service.entityprincipalcustomermaster.ServiceEntityPrincipalcustomerMaster;

@RestController
@RequestMapping(value="/getrealtimemarginapi")
@CrossOrigin(origins="*")
@SuppressWarnings("unused")
public class APIRealTimeCalculationMargin {
    

	@Autowired
	private RepositoryEntityPrincipalcustomerMaster repositoryEntityPrincipalcustomerMaster;
	
	@Autowired
	private ServiceEntityPrincipalcustomerMaster serviceEntityPrincipalcustomerMaster;
	
	@Autowired
	private ServiceEntityCustomerMaster serviceEntityCustomerMaster;
	
	@Autowired
	private CustomRepostitory customRepoEntityIncidentMaster;
	
	@Autowired
	private RepositoryEntityContractDetailsnewMaster repositoryEntityContractDetailsnewMaster;
	

	@Autowired
	private RepositoryEntityCustomerMaster repoCustomer;
	
	@Autowired
	private RepositoryEntityEngineerMaster repositoryEntityEngineerMaster;
	
	@Autowired
	private RepositoryEntityEngineerMaster repoEngineerMaster;
	

	static String m_strCurrencySymbolFormat="",booleanCustListIsEmpty="";
			
    Integer n_intTotalTicketsForCustomer=0,n_intTotalClaimTickets=0,n_intTotalClaimApprovedAmountCustomer=0,n_intSpareConsumedCostForCustomer=0;
    Integer n_intTotalCostOfEngForCustomer=0;
    
    static int staticIntCurrentMonthNumber=0; static int staticIntCurrentDate;static int staticIntCurrentYear=0;
    
	static String static_strTillMonthDivisionNumber="";
	static String static_strMonthNameForDivisionCalculation="";
	
	@GetMapping("/yearwise")
	public ResponseEntity<?> returnListOfRealTimeMargin(
			@RequestParam(value="m_strYear",required=false)String m_strYear,
			@RequestParam(value="m_strPageSize",required=false)String m_strPageSize,
			@RequestParam(name="m_strPeriodictype",required=false)String m_strPeriodictype,
			@RequestParam(name="m_strQarter",required=false)String m_strQarter,
			HttpServletRequest request){
		
		DtoRealTimeJsonResponse dtoJsonResponse=new DtoRealTimeJsonResponse();
		List<JsonResponseRealTime> jsonResponse=new ArrayList<JsonResponseRealTime>();
		
		
		System.out.println(m_strYear+" YEAR");
		System.out.println(m_strPageSize+" m_strPageSize");
		System.out.println(m_strPeriodictype+" m_strPeriodictype");
		System.out.println(m_strQarter+" m_strQarter");
		
		
		LocalDate obj_CurrentDate = LocalDate.now();
	    staticIntCurrentDate = obj_CurrentDate.getDayOfMonth();                                     //Current Day Date 26
	    staticIntCurrentMonthNumber = obj_CurrentDate.getMonthValue();                              //current Month 2
	    staticIntCurrentYear=obj_CurrentDate.getYear();                                             //current Year
		
		Currency m_strCurrencySymbol = null;
		
		try {
			m_strCurrencySymbol = Currency.getInstance("INR");
			if(m_strCurrencySymbol.getSymbol().equals("INR")) {
				 m_strCurrencySymbolFormat="Rs";
			}else {
				 m_strCurrencySymbolFormat=m_strCurrencySymbol.getSymbol();
			}
		} catch (Exception ex) { System.out.println("Error In Currency");  m_strCurrencySymbolFormat = Currency.getInstance("USD").getSymbol();  }
		
	
		
	    List<Object[]> l1=repoCustomer.returnListOfAllCustomerForRealTime("N");
	    
	    if(!l1.isEmpty()) {
	    	int n_intSrNo=1;
            for(Object[] m_strRmData : l1) {
            	   
            	   String m_strTypeidCustomer="",m_strCustomerName="",m_strPcId="";
            	   n_intTotalTicketsForCustomer=0;     n_intTotalClaimTickets=0;    n_intTotalClaimApprovedAmountCustomer=0;n_intSpareConsumedCostForCustomer=0;
            	   n_intTotalCostOfEngForCustomer=0;
            	   
	               m_strTypeidCustomer=CommonUtils.nullToBlank(String.valueOf(m_strRmData[0]), false);
	               m_strCustomerName=CommonUtils.nullToBlank(String.valueOf(m_strRmData[1]), false);
	               m_strPcId=CommonUtils.nullToBlank(String.valueOf(m_strRmData[2]), false);
	               JsonResponseRealTime js1=new JsonResponseRealTime();
	               
	               js1=this.returnListOfRealTimeMarginCalculationBegains(m_strYear,m_strCustomerName,m_strPcId,m_strTypeidCustomer,m_strPeriodictype,m_strQarter);
	               js1.setN_intSrNo(n_intSrNo);
                   jsonResponse.add(js1);	
                   
                   
                   n_intSrNo++;
                   js1=null;

	        }
            
	    }
	    
	    if(jsonResponse.size()!=0) {
	    	dtoJsonResponse.setJsonResponse(jsonResponse);
			if(m_strPageSize.equals("All")) {
				dtoJsonResponse.setPageinationBoolean("False");
			}else {
				dtoJsonResponse.setPageNo("1");
				dtoJsonResponse.setN_intTotalPages(1);
				dtoJsonResponse.setPageSize(""+m_strPageSize);
				dtoJsonResponse.setPageinationBoolean("True");
				//n_intTotalRecordsCounts=0;
			}
	    	List<DtoTableHeader> tableRow=new ArrayList<>();
	    	tableRow.add(new DtoTableHeader("SrNo"));
			tableRow.add(new DtoTableHeader("Principal Customer"));
			tableRow.add(new DtoTableHeader("Customer Name"));
			tableRow.add(new DtoTableHeader("Contract Value"));
			tableRow.add(new DtoTableHeader("Total Tickets"));
			tableRow.add(new DtoTableHeader("Claim Approved Amount"));
			tableRow.add(new DtoTableHeader("Spares Consumed Cost"));
			tableRow.add(new DtoTableHeader("Avg Ticket Cost Of Eng"));
			tableRow.add(new DtoTableHeader("CostOfOperation"));
			tableRow.add(new DtoTableHeader("YTD Cost"));
			tableRow.add(new DtoTableHeader("Avg Per Month Cost"));
			tableRow.add(new DtoTableHeader("EOY Cost"));
			tableRow.add(new DtoTableHeader("Profit Expected"));
			tableRow.add(new DtoTableHeader("Percentage"));
			
			
			dtoJsonResponse.setDtoTableHeaderList(tableRow);
	    }
	    
	    
	    return ResponseEntity.status(HttpStatus.OK).header("custom-header", "RealTimeMargin")
		 		.contentType(MediaType.APPLICATION_JSON).body(dtoJsonResponse);
		
	
	}
	
	
	public JsonResponseRealTime  returnListOfRealTimeMarginCalculationBegains(String m_strYear,String m_strCustomerName,String m_strPrincipalID,String m_strTypeidCustomer,
			String m_strPeriodictype,String m_strQarter){
		JsonResponseRealTime js1=new JsonResponseRealTime();
		List<DtoJsonEngRealTimeMargin> l_objEngList=new ArrayList<DtoJsonEngRealTimeMargin>();
	     
   	       Map<Integer, Integer> l_mapObj1 = new HashMap<>();
		   int n_intTicketCountPerEng=0;int n_intEngIds=0;
		   
		 
		   
			js1.setM_strPcName(m_strPrincipalID);
			js1.setM_strCustomerName(m_strCustomerName+" "+m_strTypeidCustomer);
			
		     /**CONTRACT VALUE**/   
			 String m_intContractValue="";
			 Double doubleContractValue=0.0;
			     try {
			            m_intContractValue=CommonUtils.nullToBlank(repositoryEntityContractDetailsnewMaster.returnContractValueFromCustomerId(String.valueOf(m_strTypeidCustomer)), false);
			     }catch(Exception ex) { m_intContractValue="0"; }
			 
			     if(m_intContractValue!=null && m_intContractValue.equals("")==false) {    
			        	m_intContractValue=m_intContractValue.replaceAll("\\s", "");
			     }else {
			        	m_intContractValue="0";
			     }
			     try {
			    	 doubleContractValue=Double.valueOf(m_intContractValue);
			     }catch(Exception ex) {  doubleContractValue=0.0;  }
			    js1.setCustomerContractValue(m_strCurrencySymbolFormat+""+Math.round(doubleContractValue));
			    js1.setN_IntCustomerContractValueTotal(Math.round(doubleContractValue.intValue()));
	        /**CONTRACT VALUE ENDS**/
		
			    String m_strQuery="";
			    
			 /*
		    String m_strQuery = "SELECT count(*) as count,engineer_engm_im \r\n"
					+ "FROM erprmwise.incidentmaster WHERE engineer_engm_im IS NOT NULL "
					+ "AND principal_cm_im IS NOT NULL   AND customer_cm_im IS NOT NULL \r\n"
					+ "AND YEAR(incdate_im) = "+m_strYear+" and principal_cm_im="+m_strPrincipalID+" "
					+ " and deleteflag_im='N' and customer_cm_im="+m_strTypeidCustomer+" "
					+ "group by engineer_engm_im";
					
					*/
			    
			     m_strQuery = "SELECT count(*) as count,engineer_engm_im \r\n"
						+ "FROM erprmwise.incidentmaster WHERE engineer_engm_im IS NOT NULL "
						+ "AND principal_cm_im IS NOT NULL   AND customer_cm_im IS NOT NULL \r\n"
						+ " and principal_cm_im="+m_strPrincipalID+" "
						+ " and deleteflag_im='N' and customer_cm_im="+m_strTypeidCustomer+" ";
						
			     m_strQuery=this.returnQueryForOpertaion(m_strPeriodictype,m_strQarter,m_strYear,m_strQuery);
			     m_strQuery=m_strQuery+" group by engineer_engm_im";
		    System.out.println("\n\nMAIN QUERY:=\n "+m_strQuery);
		    
			List<Object[]> l1=customRepoEntityIncidentMaster.returnListOfEngineerAttendance(m_strQuery);
			if(!l1.isEmpty()) {
			
			    try {
			          for(Object[] m_strRmData : l1) {
			        	n_intTicketCountPerEng=Integer.parseInt(String.valueOf(m_strRmData[0]));
			        	n_intEngIds=Integer.parseInt(String.valueOf(m_strRmData[1])); 
			        	l_mapObj1.put(n_intEngIds, n_intTicketCountPerEng);                 //PUTTING IN MAP ENGID AND TICKET COUNTS PER ENG
			        	n_intTicketCountPerEng=0;n_intEngIds=0;
			         }
			     }catch(Exception ex) { ex.printStackTrace(); }      
			
			    
			     //List<DtoJsonRealTimeMarginApproximation> l_objListDtoJsonRealTimeMargin=new ArrayList<DtoJsonRealTimeMarginApproximation>();
				 
				 if (!l_mapObj1.isEmpty() || l_mapObj1!=null) { 
					 l_objEngList=this.findEngineerSalarySparesConsumedAndClaimed(l_mapObj1,m_strPrincipalID,m_strTypeidCustomer,m_strYear);
				 }else {  n_intTotalTicketsForCustomer=0; } 
			    
			    
			}
	    js1.setM_strTotalTicketsForCustomer(n_intTotalTicketsForCustomer+"");
	    js1.setN_intTotalTicketsForCustomer(n_intTotalTicketsForCustomer);
	    //js1.setTotalClaimTickets(n_intTotalClaimTickets+"");
	    js1.setTotalClaimApprovedAmountCustomer(m_strCurrencySymbolFormat+n_intTotalClaimApprovedAmountCustomer+"");
	    js1.setN_intTotalClaimApprovedAmountCustomer(n_intTotalClaimApprovedAmountCustomer);
	    js1.setSpareConsumedCostForCustomer(m_strCurrencySymbolFormat+""+n_intSpareConsumedCostForCustomer);
	    js1.setN_intSpareConsumedCostForCustomer(n_intSpareConsumedCostForCustomer);
	    
	    Double n_IntAvgTicketCostOfEng=0.0;Integer AVGCOST=0;
	    try {
	    	System.out.println("Total Eng= "+l_objEngList.size());
	    	n_IntAvgTicketCostOfEng =(double)n_intTotalCostOfEngForCustomer/l_objEngList.size();	
	    	  AVGCOST=(int)Math.round(n_IntAvgTicketCostOfEng);
	    }catch(Exception ex) { ex.printStackTrace(); }
	    js1.setAvgTicketCostOfEng(m_strCurrencySymbolFormat+""+Math.round(n_IntAvgTicketCostOfEng));
	    
	    
	    Integer CostOfOperation=0;
	    CostOfOperation=AVGCOST+n_intSpareConsumedCostForCustomer+n_intTotalClaimApprovedAmountCustomer;
	    
	    js1.setN_IntAvgTicketCostOfEng(AVGCOST);
	    
	    js1.setCostOfOperation(m_strCurrencySymbolFormat+""+CostOfOperation);
	    
	    
	    /*YTD COST*/
	    double YTDCOST=0;
	    YTDCOST=AVGCOST*n_intTotalTicketsForCustomer;
	    /*YTD COST*/
	    js1.setM_strYTDCost(m_strCurrencySymbolFormat+""+YTDCOST);
	    
		 /**AVG OF REMAINS MONTH **/
	    Integer AVGREMAIN=0;
	    System.out.println(staticIntCurrentMonthNumber+" STATIC MONTH FOR DIVISION"); 
        Double dblRemainMonthAvg=0.0;
	      try{
	    	  
	    	  dblRemainMonthAvg=(double)YTDCOST/staticIntCurrentMonthNumber;
	    	  System.out.println(dblRemainMonthAvg+"="+YTDCOST+"/"+staticIntCurrentMonthNumber);
	      }catch(Exception ex) { dblRemainMonthAvg=0.0;  }
	      
	      AVGREMAIN=(int) Math.round(dblRemainMonthAvg);
	      if(Math.round(doubleContractValue)==0) {
	    	 js1.setAvgPerMonth(m_strCurrencySymbolFormat+"0");
	      }else {
	    	 js1.setAvgPerMonth(m_strCurrencySymbolFormat+""+Math.round(AVGREMAIN));
	      }
	      /**AVG OF PER REMAINS MONTH**/
	      
	      
	      /**EOY PER MONTH**/
	      Double dbl_EoyMargin=0.0;Integer EOYMARGIN=0;
	      try {
	        	dbl_EoyMargin=(double) (AVGREMAIN*12);
	        	EOYMARGIN=(int)Math.round(dbl_EoyMargin);
	       }catch(Exception ex) { ex.printStackTrace();  }
	       if(Math.round(doubleContractValue)==0) {
	    	   js1.setEoyMargin("0");
	       }else {
	    	   js1.setEoyMargin(m_strCurrencySymbolFormat+""+EOYMARGIN);
	       }
	      /**EOY PER MONTH**/
	       
	       /**Profit Expected  Margin  **/
	        double dbl_ProfitExpected=0;Integer PROFITEXPECTED=0;
	        try {
	        	if(dbl_EoyMargin<0){
	        		dbl_ProfitExpected=Math.round(doubleContractValue)+Math.round(dbl_EoyMargin);	
	        	}else {
	        		dbl_ProfitExpected=Math.round(doubleContractValue)-Math.round(dbl_EoyMargin);	
	        	}
	        }catch(Exception ex) { ex.printStackTrace();  }
	        
	        PROFITEXPECTED=(int)Math.round(dbl_ProfitExpected);
	        
	        if(Math.round(doubleContractValue)==0) {
	        	js1.setProfitExpected(m_strCurrencySymbolFormat+"0");
	        }else {
	        	js1.setProfitExpected(m_strCurrencySymbolFormat+""+PROFITEXPECTED);
	        }
	       /**Profit Expected Margin **/    
	        
	        
	        /** CALCULATE THE PERCENTAGE START**/ 
	         try {
			         double percentage = (double) Math.round(dbl_ProfitExpected)/Math.round(doubleContractValue); // Calculate the percentage
			         double d_blPercentage=(double)percentage*100;
			         
			         if (Double.isInfinite(d_blPercentage)) {
			        	 js1.setM_strPercentageOverall("0%");
			         }else if (Double.isNaN(d_blPercentage)) {
			        	 js1.setM_strPercentageOverall("0%");
			         }else{
			        	 js1.setM_strPercentageOverall(d_blPercentage+"%");
			         }
				     percentage=0;
		      }catch(Exception ex) { ex.printStackTrace();
		         js1.setM_strPercentageOverall("0%");
		      }  
	         /** CALCULATE THE PERCENTAGE ENDS**/ 
       
	     /** SHOWING INDEX **/
	         if (Math.round(dbl_ProfitExpected) < 0) {
              	js1.setM_strIndexShown("IN_LOSS");
              } else if (Math.round(dbl_ProfitExpected) == 0) {
             	 if(Math.round(doubleContractValue)==0) {
             		js1.setM_strIndexShown("IN_LOSS"); 
             	 }else {
             		js1.setM_strIndexShown("IN_BOTH");	 
             	 }
              } else if(Math.round(dbl_ProfitExpected) >0) {
            	  js1.setM_strIndexShown("IN_PROFIT");
              }
	         /** SHOWING INDEX **/
	         
	         
	    js1.setL_objEngList(l_objEngList);
	    return js1;	
	}
	
	
	public  List<DtoJsonEngRealTimeMargin> findEngineerSalarySparesConsumedAndClaimed(Map<Integer,Integer> l_mapObj1,
            String m_strPrincipalID,String m_strCustomerID,String m_strYear) {
		
		String m_strQueryForEngDetails="";
		List<DtoJsonEngRealTimeMargin> lobjListEng=new ArrayList<DtoJsonEngRealTimeMargin>();
		
		for (Map.Entry<Integer, Integer> ii1 : l_mapObj1.entrySet()) {
			DtoJsonEngRealTimeMargin d1=new DtoJsonEngRealTimeMargin();
			   String m_strEngName="",m_strSalary="";
			   try {
				  int n_intTotalTickets=0;
				  n_intTotalTickets=ii1.getValue();
				  n_intTotalTicketsForCustomer+=n_intTotalTickets;
			   }catch(Exception ex) { ex.printStackTrace(); }
			
			   
			    try {  /**RETURN NAME OF ENGINEER**/
			         m_strEngName=CommonUtils.nullToBlank(repositoryEntityEngineerMaster.returnValueForIdIncludedResignedIgnoreDeleteflag(Long.valueOf(ii1.getKey()), "N"), false);
			         d1.setM_strEngName(m_strEngName+" "+ii1.getKey());
			    }catch(Exception ex) {  }
	    	  

		    	  try {    /**RETURN SALARY OF ENGINEER**/
		    		  m_strSalary=CommonUtils.nullToBlank(repoEngineerMaster.returnSalaryForIdIncludedResigned(Long.valueOf(ii1.getKey()), "N"), false);
					  if(m_strSalary.equals("")==true || m_strSalary==null) {
						  m_strSalary="0";
					  }
		    		  d1.setM_strEngSalary(m_strSalary);
				  }catch(Exception ex) {   d1.setM_strEngSalary("0");  }
		    	  
		    	  /**ENG TOTAL TICKETS**/
		    	  if(String.valueOf(ii1.getValue()).equals("")==true || String.valueOf(ii1.getValue())==null) {
		    		  d1.setM_strEngTotalTickets("0");
		    		  d1.setN_intEngTotalTickets(0);
		    	  }else {
		    		  d1.setM_strEngTotalTickets(String.valueOf(ii1.getValue()));
		    		  d1.setN_intEngTotalTickets(Integer.parseInt(String.valueOf(ii1.getValue())));
		    	  }
		    	  
		    	  
		    	  m_strQueryForEngDetails="SELECT typeid_im,sparesconsumed_im,status_sm_im "
					  		+ "FROM erprmwise.incidentmaster \r\n"
					  		+ "WHERE engineer_engm_im IS NOT NULL "
					  		+ "  AND principal_cm_im IS NOT NULL  AND customer_cm_im IS NOT NULL \r\n"
					  		+ "  AND  YEAR(incdate_im) = "+m_strYear+" "
					  		+ "  AND principal_cm_im = "+m_strPrincipalID+" "
					  		+ "  AND deleteflag_im = 'N' "
					  		+ "  AND customer_cm_im = "+m_strCustomerID+" "
					  		+ "  AND engineer_engm_im = "+ii1.getKey()+" \r\n"
					  		+ "  AND (sparesconsumed_im = 'Yes' OR sparesconsumed_im = 'N' OR sparesconsumed_im = 'No')   "; 
		    	System.out.println("m_strQueryForEngDetails=\n "+m_strQueryForEngDetails+ " \n");
		    	List<Object[]> l1_m_strQueryForEngDetails=customRepoEntityIncidentMaster.returnListOfSelectStarFromConcept(m_strQueryForEngDetails);
		    	
		    	 if(!l1_m_strQueryForEngDetails.isEmpty()){
		    		 
		    		 for(Object[] m_strRmData : l1_m_strQueryForEngDetails) {
		    			 String m_strTicketId="",m_strSparedConsumed="";		    			 
		    			 try {
		    				    m_strTicketId=String.valueOf(m_strRmData[0]);
					    	    m_strSparedConsumed=String.valueOf(m_strRmData[1]);
					    	    
					    	    if(String.valueOf(ii1.getKey())!=null && String.valueOf(ii1.getKey()).equals("")==false
					    	    		&& m_strTicketId!=null && m_strTicketId.equals("")==false) {
					    	    	 this.getClaimAmmount(m_strTicketId,String.valueOf(ii1.getKey()));
					    	    } 
					    	    
					    	    if(String.valueOf(ii1.getKey())!=null && String.valueOf(ii1.getKey()).equals("")==false && m_strSparedConsumed!=null && m_strSparedConsumed.equals("")==false) {
					    	    	if(m_strSparedConsumed.equals("Yes") || m_strSparedConsumed.equals("Y")) {
                                             
					    	        }
					    	    }
					    }catch(Exception ex) {  System.out.println("Error Here In Iteration"); }
		    			 
		    		 }
		    	 }
		    	
		    	 /**Engineer Average Cost per tickets**/
                 String m_strAvgCostPerTickets="";
                 String m_strQueryEngAvgCostPerTicket="select avgticketcost_esm from engineersalarymaster_esm "
                 		+ "  where engid_esm="+ii1.getKey()+" order by id_esm desc limit 1";
                 try {
                    m_strAvgCostPerTickets=CommonUtils.nullToBlank(customRepoEntityIncidentMaster.returnAvgTicketCostForEngineerString(m_strQueryEngAvgCostPerTicket), false);
                 }catch(Exception ex) {  }
 			     
                 if(m_strAvgCostPerTickets==null || m_strAvgCostPerTickets.equals("")==true) {
 			    	 m_strAvgCostPerTickets="0";
 			     }else if(m_strAvgCostPerTickets.equals("NaN")){
 			    	 m_strAvgCostPerTickets="0";
 			     }
		
                 /*
         	    try { 
         	    	Double n_IntAvgCostPerTickets=0.0;
         	    	n_IntAvgCostPerTickets=Double.parseDouble(m_strAvgCostPerTickets);
  			        n_intTotalCostOfEngForCustomer+=Math.round(n_IntAvgCostPerTickets);
  			    }catch(Exception ex) { ex.printStackTrace(); n_intTotalCostOfEngForCustomer=0; }   
                 */
                 
         	       double n_IntAvgCostPerTickets = 0.0;Integer n_IntAvgCostPerTicketConverted=0;
	         	 try {
	         	      n_IntAvgCostPerTickets = Double.parseDouble(m_strAvgCostPerTickets);
	         	      n_IntAvgCostPerTicketConverted=(int) Math.round(n_IntAvgCostPerTickets);
	         	      
	         	      n_intTotalCostOfEngForCustomer += n_IntAvgCostPerTicketConverted;
	         	  }catch (NumberFormatException e) {   e.printStackTrace();  }
         	    
         	    
                 d1.setM_strAvgCostPerTicketsForEng(""+m_strAvgCostPerTickets);
		    	 
                 d1.setM_strNoOfClaimForEng(""+n_intnoOfClaimForEng);
                 d1.setM_strClaimApprovedTotalTicket(""+m_strClaimApprovedTotalTicket);
		    	 d1.setM_strClaimTotalApprovedAmount(m_strCurrencySymbolFormat+""+n_intClaimApprovedAmountOfEngineer);
		    	 d1.setN_IntClaimTotalApprovedAmount(n_intClaimApprovedAmountOfEngineer);
		    	 
		    	 d1.setSparesConsumedCostOfEng(m_strCurrencySymbolFormat+""+n_intSparesConsumedCostOfEng);
		    	 d1.setN_intSparesConsumedCostOfEng(n_intSparesConsumedCostOfEng);
		    	 
		    	 
		    	 lobjListEng.add(d1);
		    	 
		    	 n_intTotalClaimTickets+=n_intnoOfClaimForEng;
		    	 n_intTotalClaimApprovedAmountCustomer+=n_intClaimApprovedAmountOfEngineer;
		    	 n_intSpareConsumedCostForCustomer+=n_intSparesConsumedCostOfEng;
		    	 
		    	 m_strClaimApprovedTotalTicket=0;
		    	 n_intClaimApprovedAmountOfEngineer=0;
		    	 n_intnoOfClaimForEng=0;n_intSparesConsumedCostOfEng=0;
		    	 d1=null;
		}
		
		
		
	  return lobjListEng;
	}
	

	static String m_strClaimTypeOfEngineer="";
	static Integer n_intClaimApprovedAmountOfEngineer=0,n_intClaimPendingAmountOfEngineer=0, n_intnoOfClaimForEng=0, m_strClaimApprovedTotalTicket=0;
	static Integer n_strClaimPendingTickets=0,n_intSparesConsumedCostOfEng=0,n_intnoOfSparesConsumedForEng=0;
	
	 public void getSpareConsumedDetails(String m_strTicketId,String m_strEngId) {
		 String m_strSpareCost="";
		 String m_strQuerySparedConsumed="select typeid_pm_im,price_pm,partcode_pm,partmodel_pm,partname_pm "
			  		+ " from erprmwise.inventorymst_im i,erprmwise.partmst_pm p "
			  		+ " where i.typeid_pm_im=p.partid_pm and ticketid_im_im='"+m_strTicketId+"' ";
	     System.out.println("Spared Consumed Query "+m_strQuerySparedConsumed);
	     List<Object[]> l_objList=new ArrayList<Object[]>();
	     try {
			  l_objList=customRepoEntityIncidentMaster.returnListOfSelectStarFromConcept(m_strQuerySparedConsumed);
			      
			  if(!l_objList.isEmpty()) {
			    	n_intnoOfSparesConsumedForEng++;
			    	  for(Object[] m_strRmData : l_objList) {
			    		  Integer n_intSparesCost= 0;
			    		  try {
			    		     
			    			  m_strSpareCost=CommonUtils.nullToBlank(String.valueOf(m_strRmData[1]), false);
			    		  
			    			   n_intSparesCost= Math.round(Integer.parseInt(m_strSpareCost));
			    		  }catch(Exception ex) { ex.printStackTrace(); m_strSpareCost="0"; }
			    		  
			    		  n_intSparesConsumedCostOfEng += n_intSparesCost;
			    	  }
			      }
			      
		  }catch(Exception ex) {  ex.printStackTrace(); }
	 }
	
	
	public void getClaimAmmount(String m_strTicketId,String m_strEngId) {
		  
		  String m_strApprovedClaimAmount="";String m_strPendingClaimAmount="";String m_strApprovedflag="",m_strClaimType="";
		  
		  String m_strClaimQuery="select approvedflag_cm,substatus_cm,claimtype_ctm_cm,approvedamount_cm,billamount_cm,incidentid_cm "
			  		+ " from claimmst_cm where deleteflag_cm='N' and incidentid_cm='"+m_strTicketId+"' and claimedby_em_cm='"+m_strEngId+"' ";
		  
		  /**System.out.println("claim Query F "+m_strClaimQuery);**/
		  
		  List<Object[]> l_objListClaim=new ArrayList<Object[]>();
		  
		      l_objListClaim=customRepoEntityIncidentMaster.returnListOfSelectStarFromConcept(m_strClaimQuery);
		      if(!l_objListClaim.isEmpty()) 
		    	  System.out.println("CLAIM APPROVED");
		    	  n_intnoOfClaimForEng++;
		    	  for(Object[] m_strRmData : l_objListClaim) { 
		    		 
		    		  try {
			    			
			    		   try {
 			    		     m_strApprovedflag=CommonUtils.nullToBlank(String.valueOf(m_strRmData[0]), false);
			    		   }catch(Exception ex) { m_strApprovedflag="N"; }
			    		   
			    		   
			    		   try {
			    			  m_strClaimType=CommonUtils.nullToBlank(String.valueOf(m_strRmData[2]), false);
			    		      if(m_strClaimTypeOfEngineer!=null && m_strClaimTypeOfEngineer.equals("")==false) {
			    		    	  m_strClaimTypeOfEngineer=m_strClaimTypeOfEngineer+","+m_strClaimType;
			    		      }else {
			    		    	  m_strClaimTypeOfEngineer=m_strClaimType;
			    		      }
			    		   }catch(Exception ex) {  } 
			    		      
			    		      
			    		   if(m_strApprovedflag.equals("N") || m_strApprovedflag.equals("No")) {
				    			   try {
				    			     m_strPendingClaimAmount=CommonUtils.nullToBlank(String.valueOf(m_strRmData[3]), false);
				    			   }catch(Exception ex) { ex.printStackTrace(); m_strPendingClaimAmount="0"; }
				    			    n_strClaimPendingTickets++;   
				    			   try {
				    			      n_intClaimPendingAmountOfEngineer+= Integer.parseInt(m_strPendingClaimAmount);
				    			   }catch(Exception ex) {  }
			    			  
			    	    	}else if(m_strApprovedflag.equals("Y") || m_strApprovedflag.equals("Yes")) {
				    	    		try {
				    	    	    	m_strApprovedClaimAmount=CommonUtils.nullToBlank(String.valueOf(m_strRmData[3]), false);
				    	    	    	if(m_strApprovedClaimAmount.equals("")==true) { m_strApprovedClaimAmount="0";   }
				    	    		}catch(Exception ex) {  ex.printStackTrace(); m_strApprovedClaimAmount="0";  }
				    	    		
				    	    		m_strClaimApprovedTotalTicket++;
				    	    		try {
				    			        n_intClaimApprovedAmountOfEngineer += Math.round(Integer.parseInt(m_strApprovedClaimAmount));
				    	    		}catch(Exception ex) {  }
			    	    	}
			    		
			    		 
		    		 }catch(Exception ex) {  ex.printStackTrace(); }  
		    		 
		    	  }  //FOR LOOPS ENDS HERE

		      }  
	
	
	
	
	public String returnQueryForOpertaion(String m_strPeriodictype,String m_strQarter,String m_strYear,String m_strQuery) {
		
		int m_strCurrentYear=0;
		m_strCurrentYear=Integer.parseInt(m_strYear);
		String m_strTillMonthDivision="";
		String m_strQuery2="";
		LocalDate obj_CurrentDate = LocalDate.now();
	    staticIntCurrentDate = obj_CurrentDate.getDayOfMonth();                                     //Current Day Date 26
	    staticIntCurrentMonthNumber = obj_CurrentDate.getMonthValue();                              //current Month 2
	    staticIntCurrentYear=obj_CurrentDate.getYear();                                             //current Year
	    
		if(m_strPeriodictype!=null && m_strPeriodictype.equals("")==false) {
			   try {
				   if(m_strPeriodictype.equals("")==false && m_strPeriodictype!=null  && m_strPeriodictype.equals("FY")) {	
					   
					   if(m_strQarter.equals("")==false && m_strQarter!=null && m_strQarter.equals("All")) {
			        		
			        	}else {
			        		
			        	}
					   
					   
			        }else if(m_strPeriodictype.equals("")==false && m_strPeriodictype!=null  && m_strPeriodictype.equals("YTD")) {
			        	
			        	if(m_strQarter.equals("")==false && m_strQarter!=null && m_strQarter.equals("All")) {
			        		if(staticIntCurrentYear==m_strCurrentYear) {
			                    int n_intTillMonth=staticIntCurrentMonthNumber-1;
			                    int n_intStartMonth=1; 
			                    if(n_intTillMonth==0) {
			                    	n_intStartMonth=1;
			                    	n_intTillMonth=2;
			                    }
			                    m_strTillMonthDivision=String.valueOf(n_intTillMonth);
			                    //static_strMonthNameForDivisionCalculation=m_strTillMonthDivision;
			                    Month month = Month.of(Integer.parseInt(m_strTillMonthDivision));
			    				static_strMonthNameForDivisionCalculation=month.name();
			                      m_strQuery=m_strQuery + "    AND YEAR(incdate_im) = "+m_strCurrentYear+" "
			                    		+ "    AND MONTH(incdate_im) BETWEEN "+n_intStartMonth+" AND "+n_intTillMonth+" \r\n"
			                    		+ "    AND customer_cm_im IS NOT NULL  ";
			                    		
			                    		
			    			}else {
			    				m_strTillMonthDivision=String.valueOf("12");
			    				Month month = Month.of(12);
			    				static_strMonthNameForDivisionCalculation=month.name();
			    			    	m_strQuery=m_strQuery+" and year(incdate_im)="+m_strCurrentYear+"  \n ";
			    						
			    			}
			        		
			        	}else {
			        		
			        	}
			        	
			        	
			        }else if(m_strPeriodictype.equals("")==false && m_strPeriodictype!=null  && m_strPeriodictype.equals("custom")) {
			        	
			        }  		   
			   }catch(Exception ex) { ex.printStackTrace(); }
			   staticIntCurrentMonthNumber=Integer.parseInt(m_strTillMonthDivision);
		}
		
		return m_strQuery;
	}
	
}
	

