package intelli.uno.apiweb.margin;

import java.util.ArrayList;
import java.util.Arrays;
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

import intelli.uno.repository.RepositoryEntityClaimtypeMaster;
import intelli.uno.repository.RepositoryEntityContractDetailsnewMaster;
import intelli.uno.repository.RepositoryEntityEngineerMaster;

import intelli.uno.repositorycustom.CustomRepostitory;
import intelli.uno.service.entitycustomermaster.ServiceEntityCustomerMaster;
import intelli.uno.dto.DtoJsonRealTimeMarginApproximation;
import intelli.uno.dto.JsonResponseRealTimeApproximation;
import intelli.uno.entity.EntityCustomerMaster;

@RestController
@RequestMapping(value="/realtimemarginapi")
@CrossOrigin(origins="*")
@SuppressWarnings("unused")
public class APIControlRealTimeMargin {	

	@Autowired
	private RepositoryEntityEngineerMaster repositoryEntityEngineerMaster;
	
	@Autowired
	private ServiceEntityCustomerMaster serviceEntityCustomerMaster;
	
	//@Autowired 
	//private RepositoryEntityPrincipalcustomerMaster repoPrincipalCustomer;
	
	@Autowired
	private RepositoryEntityEngineerMaster repoEngineerMaster;
	
	@Autowired
	private CustomRepostitory customRepoEntityIncidentMaster;
	
	@Autowired
	private RepositoryEntityClaimtypeMaster repoEntityClaimType;
	
	@Autowired
	private RepositoryEntityContractDetailsnewMaster repositoryEntityContractDetailsnewMaster;
	
	static Integer n_intClaimApprovedAmountOfEngineer=0,n_intClaimPendingAmountOfEngineer=0, n_intnoOfClaimForEng=0, m_strClaimApprovedTotalTicket=0;
	static Integer n_strClaimPendingTickets=0;
	static String m_strClaimTypeOfEngineer="";
	static Integer n_intTotalTicketsForCustomer=0;
	
	static Integer n_intnoOfSparesConsumedForEng=0;
	static Integer n_intSparesConsumedCostOfEng=0;
	
	static Integer n_intTotalClaimApprovedAmountForCustomer=0,n_intTotalClaimTicketsForCustomer=0,n_intTotalClaimApprovedTicketsForCustomer=0;
	static Integer n_intTotalClaimPendingAmountForCustomer=0,n_intTotalClaimPendingTicketsForCustomer=0;
	static String m_strClaimTypeOfCustomer="";
	static Double n_dblTotalTicketCostOfEngForCustomer=0.0;
	static Integer n_intTotalSparedConsumedCostForCustomer=0;static Integer n_intTotalSparedTicketForCustomer=0;
	
	@GetMapping
	public ResponseEntity<?> returnListOfRealTimeMargin(
			@RequestParam(value="pcId",required=true)String m_strPrincipalID,
			@RequestParam(value="year",required=true)String m_strYear,
			HttpServletRequest request){
		
		System.out.println(" REALTIME MARGIN \n REQUESTED PRINCIPAL CUSTOMER= "+m_strPrincipalID);
		
		List<EntityCustomerMaster> l_strListOfCustomer=new ArrayList<>();
				
				String m_strCustomerID="";String m_strCustomerName="";
				int n_intTicketCountPerEng=0;int n_intEngIds=0;
				
				if(m_strPrincipalID!=null && m_strPrincipalID.equals("")==false) {

					try {
						l_strListOfCustomer=serviceEntityCustomerMaster.returnListOfAllCustomerByPc(m_strPrincipalID,"N");	
					}catch(Exception ex) { ex.printStackTrace(); }
						
					if(l_strListOfCustomer.isEmpty()==false) {
						List<JsonResponseRealTimeApproximation> l_objListJsonResponse=new ArrayList<JsonResponseRealTimeApproximation>();
						int n_intSrNoIndex=1;
				    	for (EntityCustomerMaster entityCustomer : l_strListOfCustomer) {
				    		System.out.println("-------------- EVERYTHINGS BEGINS HERE -----------------");
				    	   
							JsonResponseRealTimeApproximation jsonBody = new JsonResponseRealTimeApproximation();
							Map<Integer, Integer> l_mapObj1 = new HashMap<>();
							m_strCustomerID = String.valueOf("" + entityCustomer.getTypeidCm());
							m_strCustomerName = String.valueOf("" + entityCustomer.getCustomernameCm());

							String m_strQuery = "SELECT count(*) as count,engineer_engm_im \r\n"
									+ "FROM erprmwise.incidentmaster WHERE engineer_engm_im IS NOT NULL "
									+ "AND principal_cm_im IS NOT NULL   AND customer_cm_im IS NOT NULL \r\n"
									+ "AND YEAR(incdate_im) = " + m_strYear + " and principal_cm_im=" + m_strPrincipalID
									+ " " + " and deleteflag_im='N' and customer_cm_im=" + m_strCustomerID + " "
									+ "group by engineer_engm_im";
							

							System.out.println("MAIN QUERY:=\n " + m_strQuery);
							List<Object[]> l1 = customRepoEntityIncidentMaster.returnListOfEngineerAttendance(m_strQuery);
							
							
							if(l1!=null && l1.isEmpty()==false) {
								 try {
							          for(Object[] m_strRmData : l1) {
							        	n_intTicketCountPerEng=Integer.parseInt(String.valueOf(m_strRmData[0]));
							        	n_intEngIds=Integer.parseInt(String.valueOf(m_strRmData[1])); 
							        	l_mapObj1.put(n_intEngIds, n_intTicketCountPerEng);                 //PUTTING IN MAP ENGID AND TICKET COUNTS PER ENG
							        	n_intTicketCountPerEng=0;n_intEngIds=0;
							         }
							     }catch(Exception ex) { ex.printStackTrace(); }
							}
							
							 
						    List<DtoJsonRealTimeMarginApproximation> l_objListDtoJsonRealTimeMarginApproximation=
										new ArrayList<DtoJsonRealTimeMarginApproximation>();
						    
						    if (l_mapObj1.isEmpty()==false && l_mapObj1!=null) {
						    	     System.out.println("The HashMap is Not Empty for customer= "+m_strCustomerID);
					        	     l_objListDtoJsonRealTimeMarginApproximation = this.findEngineerSallerySparesConsumed(l_mapObj1,m_strPrincipalID,m_strCustomerID,m_strYear);
					        } 
						    
						    /**CONTRACT VALUE START**/
					        String m_intContractValue="";
					        try {
					           m_intContractValue=repositoryEntityContractDetailsnewMaster.returnContractValueFromCustomerId(String.valueOf(m_strCustomerID));
					        }catch(Exception ex) { m_intContractValue="0"; }
					        
					        if(m_intContractValue!=null && m_intContractValue.equals("")==false) {    
					        	m_intContractValue=m_intContractValue.replaceAll("\\s", "");
					        }else {
					        	m_intContractValue="0";
					        }
					        /**CONTRACT VALUE ENDS**/
						    
					        Double dbl_avegCostPerTicketEngForCust=0.0;Double dblActualcostPerTicket=0.0;
					        Double dbl_TotalSpareClaimEngTickets=0.0;
					        
					        /**MAIN CALCULATION BEGINS HERE**/
					        /*USED THIS FOR CALCULATION*/
					        Double dbl_avgSpareClaimEngTicketsCost=0.0;
					        
					        
					        dbl_avegCostPerTicketEngForCust=n_dblTotalTicketCostOfEngForCustomer/l_objListDtoJsonRealTimeMarginApproximation.size();
					        if (Double.isInfinite(dbl_avegCostPerTicketEngForCust)) {
								dbl_avegCostPerTicketEngForCust=0.0;
							}else if (Double.isNaN(dbl_avegCostPerTicketEngForCust)) {
								dbl_avegCostPerTicketEngForCust=0.0;
							}
					        System.out.println("dbl_avegCostPerTicketEngForCust= "+dbl_avegCostPerTicketEngForCust);
					        //dblActualcostPerTicket=dbl_avegCostPerTicketEngForCust*n_intTotalTicketsForCustomer;
					        
					        try {
					        	dbl_TotalSpareClaimEngTickets=dbl_avegCostPerTicketEngForCust+n_intTotalClaimApprovedAmountForCustomer+n_intTotalSparedConsumedCostForCustomer;
					            dbl_avgSpareClaimEngTicketsCost=dbl_TotalSpareClaimEngTickets*n_intTotalTicketsForCustomer;
					        }catch(Exception ex) { ex.printStackTrace(); }
					        /**MAIN CALCULATION ENDS HERE**/
					        
					       
					        System.out.println("Calculation Begins For Eng Avg Cost");
					        System.out.println("Total Cost Of Engineer= "+n_dblTotalTicketCostOfEngForCustomer+ "/ Overall Eng "+l_objListDtoJsonRealTimeMarginApproximation.size());
					        System.out.println("dbl_avegCostPerTicketEngForCust="+(n_dblTotalTicketCostOfEngForCustomer/l_objListDtoJsonRealTimeMarginApproximation.size()));
					        System.out.println("dbl_TotalSpareClaimEngTickets=dbl_avegCostPerTicketEngForCust+n_intTotalClaimApprovedAmountForCustomer+n_intTotalSparedConsumedCostForCustomer");
					        System.out.println(dbl_avegCostPerTicketEngForCust+" + "+n_intTotalClaimApprovedAmountForCustomer+" + "+n_intTotalSparedConsumedCostForCustomer);
					        System.out.println(" dbl_TotalSpareClaimEngTickets "+(dbl_avegCostPerTicketEngForCust+n_intTotalClaimApprovedAmountForCustomer+n_intTotalSparedConsumedCostForCustomer));
					        System.out.println("dbl_avgSpareClaimEngTicketsCost "+dbl_TotalSpareClaimEngTickets+" x "+n_intTotalTicketsForCustomer);
					        System.out.println(dbl_avgSpareClaimEngTicketsCost);
					       
					        
					        /** Margin Of Customer Starts**/
					        Double n_intContractValue=0.0;Double dblMarginOfCustomer=0.0;
					        try {
					            n_intContractValue=Double.parseDouble(m_intContractValue.replaceAll(",", ""));
					            try { 
					               dblMarginOfCustomer=n_intContractValue-dbl_avgSpareClaimEngTicketsCost;
					            }catch(Exception ex) { ex.printStackTrace(); dblMarginOfCustomer=0.0; }
					        }catch(Exception ex) { ex.printStackTrace();  }
					        
					        /** Margin of Customer Ends**/
					        
					        /*Percentage Calculation*/
					        try {
						         double percentage = (double) dblMarginOfCustomer/n_intContractValue; // Calculate the percentage
						         double d_blPercentage=(double)percentage*100;
						       
						         if (Double.isInfinite(d_blPercentage)) {
						        	 jsonBody.setM_strPercentage("0%");
						         }else if (Double.isNaN(d_blPercentage)) {
						        	 d_blPercentage=0.0;
						        	 jsonBody.setM_strPercentage(d_blPercentage+"%");	 
							    }else {
						        	 jsonBody.setM_strPercentage(Math.ceil(d_blPercentage)+"%");	 
						         }
							     percentage=0;
					        }catch(Exception ex) { ex.printStackTrace(); double percentage=0; jsonBody.setM_strPercentage(percentage+"%"); percentage=0; }
					        /*Percentage Calculation*/
					        
					        
					        String m_strClaimType="";
							int maxFrequency = 0;
							int mostRepeatedNumber = 0;
							if (m_strClaimTypeOfCustomer != null && m_strClaimTypeOfCustomer.equals("") == false) {

								String[] x111 = m_strClaimTypeOfCustomer.split(",");

								
								int[] m_strClaimTypeArray = Arrays.stream(x111).mapToInt(Integer::parseInt).toArray();
								Map<Integer, Integer> frequencyMap = new HashMap<>();

								for (int num : m_strClaimTypeArray) {
									int frequency = frequencyMap.getOrDefault(num, 0) + 1;
									frequencyMap.put(num, frequency);

									if (frequency > maxFrequency) {
										maxFrequency = frequency;
										mostRepeatedNumber = num;
									}
								}
							}
							m_strClaimType = repoEntityClaimType.returnClaimTypeForId(Long.valueOf(mostRepeatedNumber),"N");
					        
					        
							jsonBody.setM_strCustomerName(m_strCustomerName);
							jsonBody.setM_strTotalTicketsForCustomer(""+n_intTotalTicketsForCustomer);
							jsonBody.setM_strContractValueOfCustomer(""+m_intContractValue);
							jsonBody.setM_strDblcostPerTicketTotal(""+Math.ceil(n_dblTotalTicketCostOfEngForCustomer));
							jsonBody.setM_strTotalAvgCostOfEngForCustomer(""+Math.ceil(dbl_avgSpareClaimEngTicketsCost));
							jsonBody.setM_strMargin(""+Math.ceil(dblMarginOfCustomer));
							jsonBody.setM_strSparesConsumedCostForCustomer(""+n_intTotalSparedConsumedCostForCustomer);
							jsonBody.setApproximateMarginJson(l_objListDtoJsonRealTimeMarginApproximation);
							jsonBody.setM_strClaimTypeMost(""+m_strClaimType);
							jsonBody.setN_intTotalSparedTicketForCustomer(""+n_intTotalSparedTicketForCustomer);
							jsonBody.setM_strTotalClaimTicketsForCustomer(""+n_intTotalClaimTicketsForCustomer);
							jsonBody.setM_strTotalClaimApprovedTicketForCustomer(""+n_intTotalClaimApprovedTicketsForCustomer);
							jsonBody.setM_strTotalClaimApprovedAmountForCustomer(""+n_intTotalClaimApprovedAmountForCustomer);
							jsonBody.setM_strTotalClaimPendingTicketsForCustomer(""+n_intTotalClaimPendingTicketsForCustomer);
							jsonBody.setM_strTotalClaimPendingAmountForCustomer(""+n_intTotalClaimPendingAmountForCustomer);
							jsonBody.setM_strIndexNo(""+n_intSrNoIndex);
                            n_intSrNoIndex++;
                            
                            String contextPath = request.getContextPath();
                            if (dblMarginOfCustomer < 0.0) {
                            	 System.out.println(dblMarginOfCustomer+" LOSSSS  ");
                            	jsonBody.setM_strIndexProfitLossBoth(contextPath+"/BI_images/png-down.png");
                            } else if (dblMarginOfCustomer == 0.0) {
                            	System.out.println(dblMarginOfCustomer+" BOTH  ");
                            	jsonBody.setM_strIndexProfitLossBoth(contextPath+"/BI_images/png-down.png");
                            } else if(dblMarginOfCustomer >0.0) {
                            	System.out.println(dblMarginOfCustomer+" PROFIT  ");
                            	jsonBody.setM_strIndexProfitLossBoth(contextPath+"/BI_images/png-transparent-green-arrow-illustration-arrow-up.png");
                            }
                            
							l_objListJsonResponse.add(jsonBody);
							m_strQuery="";
						    l_mapObj1=null;
						    
							n_intTotalTicketsForCustomer=0;
							n_intTotalClaimApprovedAmountForCustomer=0;n_intTotalClaimTicketsForCustomer=0;n_intTotalClaimApprovedTicketsForCustomer=0;
							n_intTotalClaimPendingAmountForCustomer=0;n_intTotalClaimPendingTicketsForCustomer=0;
							m_strClaimTypeOfCustomer="";
						    n_intTotalTicketsForCustomer=0;
						    n_dblTotalTicketCostOfEngForCustomer=0.0;
						    n_intTotalSparedConsumedCostForCustomer=0;
						    n_intTotalSparedTicketForCustomer=0;
						    dbl_avegCostPerTicketEngForCust=0.0;dblActualcostPerTicket=0.0;
						    m_strClaimType="";
						    System.out.println("--------------  ENDS EVERYTHINGS HERE -----------------\n\n");
				    	}
				    	return ResponseEntity.status(HttpStatus.OK).header("custom-header", "RealTimeMargin")
						 		.contentType(MediaType.APPLICATION_JSON).body(l_objListJsonResponse);
					}
					
				}
				
		return null;
	}
		
	public  List<DtoJsonRealTimeMarginApproximation> findEngineerSallerySparesConsumed(Map<Integer,Integer> l_mapObj1,
            String m_strPrincipalID,String m_strCustomerID,String m_strYear) {

			List<DtoJsonRealTimeMarginApproximation> l_objListDtoJsonRealTimeMarginApproximation = new ArrayList<DtoJsonRealTimeMarginApproximation>();
			
			int n_intTicketCountPerEng=0;String m_strEngName="";String m_strSallery="",m_strCustomerName="";
			String m_strQueryForEngDetails="";
			for (Map.Entry<Integer, Integer> ii1 : l_mapObj1.entrySet()) {
			DtoJsonRealTimeMarginApproximation d1=new DtoJsonRealTimeMarginApproximation();
			
			try {
			n_intTotalTicketsForCustomer+=ii1.getValue();
			}catch(Exception ex) { ex.printStackTrace(); }
			
			
			try {    /**RETURN SALARY OF ENGINEER**/
			m_strSallery=repoEngineerMaster.returnSalaryForIdIncludedResigned(Long.valueOf(ii1.getKey()), "N");
			d1.setM_strEngSallery(m_strSallery);
			}catch(Exception ex) {  m_strSallery="0";   d1.setM_strEngSallery(m_strSallery);  }
			
			try {  /**RETURN NAME OF ENGINEER**/
			m_strEngName=repositoryEntityEngineerMaster.returnValueForIdIncludedResigned(Long.valueOf(ii1.getKey()), "N");
			d1.setM_strEngName(m_strEngName+" "+ii1.getKey());
			}catch(Exception ex) {  }
			
			d1.setM_strEngTotalTickets(String.valueOf(ii1.getValue()));
			
			
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
			if(l1_m_strQueryForEngDetails.isEmpty()){
			System.out.println("l1_m_strQueryForEngDetails is Empty ");
			}else {
			
			for(Object[] m_strRmData : l1_m_strQueryForEngDetails) {
			String m_strTicketId="",m_strSparedConsumed="";
			
			try {
			m_strTicketId=String.valueOf(m_strRmData[0]);
			m_strSparedConsumed=String.valueOf(m_strRmData[1]);
			
			if(String.valueOf(ii1.getKey())!=null && String.valueOf(ii1.getKey()).equals("")==false
					&& m_strTicketId!=null && m_strTicketId.equals("")==false) {
				this.getClaimAmmount(m_strTicketId,String.valueOf(ii1.getKey()));
			} 
			
			if(String.valueOf(ii1.getKey())!=null && String.valueOf(ii1.getKey()).equals("")==false
					&& m_strSparedConsumed!=null && m_strSparedConsumed.equals("")==false) {
				
				if(m_strSparedConsumed.equals("Yes") || m_strSparedConsumed.equals("Y")) {
				  this.getSpareConsumedDetails(m_strTicketId,String.valueOf(ii1.getKey()));
				}
				
			}
			}catch(Exception ex) {  System.out.println("Error Here"); }
			
			}
			
			}
			
			
			/**Engineer Average Cost per tickets**/
			String m_strAvgCostPerTickets="";
			String m_strQueryEngAvgCostPerTicket="select avgticketcost_esm from engineersalarymaster_esm "
			+ "  where engid_esm="+ii1.getKey()+" order by id_esm desc limit 1";
			try {
			m_strAvgCostPerTickets=customRepoEntityIncidentMaster.returnAvgTicketCostForEngineerString(m_strQueryEngAvgCostPerTicket);
			}catch(Exception ex) { ex.printStackTrace(); }
			if(m_strAvgCostPerTickets==null || m_strAvgCostPerTickets.equals("")==true) {
			m_strAvgCostPerTickets="0";
			}else if(m_strAvgCostPerTickets.equals("NaN")){
			m_strAvgCostPerTickets="0";
			}
			
			try { 
			n_dblTotalTicketCostOfEngForCustomer+=Double.parseDouble(m_strAvgCostPerTickets);
			}catch(Exception ex) { ex.printStackTrace(); n_dblTotalTicketCostOfEngForCustomer=0.0; }    
			
			d1.setM_strClaimsTotalTicketByEng(""+n_intnoOfClaimForEng);
			d1.setM_strClaimApprovedTotalTicket(""+m_strClaimApprovedTotalTicket);
			d1.setM_strClaimAmmountTotal(""+n_intClaimApprovedAmountOfEngineer);
			d1.setM_strClaimPendingAmmounts(""+n_intClaimPendingAmountOfEngineer);
			d1.setM_strClaimPendingTickets(""+n_strClaimPendingTickets);     
			d1.setM_strSpareConsumedCost(""+n_intSparesConsumedCostOfEng);
			d1.setM_strSpareConsumedTotalTickets(""+n_intnoOfSparesConsumedForEng);;
			d1.setM_strAvgCostPerTicket(m_strAvgCostPerTickets+"");
			
			l_objListDtoJsonRealTimeMarginApproximation.add(d1);
			
			try {
			if(m_strClaimTypeOfEngineer!=null & m_strClaimTypeOfEngineer.equals("")==false) {
				if(m_strClaimTypeOfCustomer!=null && m_strClaimTypeOfCustomer.equals("")==false) {
				m_strClaimTypeOfCustomer=m_strClaimTypeOfCustomer+","+m_strClaimTypeOfEngineer;
				}else {
				m_strClaimTypeOfCustomer=m_strClaimTypeOfEngineer;
				}
			}
			}catch(Exception ex) { ex.printStackTrace(); }
			
			
			
			n_intTotalClaimTicketsForCustomer+=n_intnoOfClaimForEng;
			n_intTotalClaimApprovedTicketsForCustomer+=m_strClaimApprovedTotalTicket;
			n_intTotalClaimApprovedAmountForCustomer += n_intClaimApprovedAmountOfEngineer;
			
			n_intTotalSparedConsumedCostForCustomer+=n_intSparesConsumedCostOfEng;
			
			n_intTotalClaimPendingAmountForCustomer+=n_intClaimPendingAmountOfEngineer;
			n_intTotalClaimPendingTicketsForCustomer+=n_strClaimPendingTickets;
			
			n_intTotalSparedTicketForCustomer+=n_intnoOfSparesConsumedForEng;
			
			n_intnoOfClaimForEng=0;m_strClaimApprovedTotalTicket=0;n_intClaimApprovedAmountOfEngineer=0;
			n_intClaimPendingAmountOfEngineer=0;n_strClaimPendingTickets=0;
			m_strClaimTypeOfEngineer="";
			
			n_intSparesConsumedCostOfEng=0;n_intnoOfSparesConsumedForEng=0;
			m_strAvgCostPerTickets="";
			d1=null;
			
			}
			
			return l_objListDtoJsonRealTimeMarginApproximation;
			}
			

	 public void getSpareConsumedDetails(String m_strTicketId,String m_strEngId) {
		 String m_strSpareCost="";
		 
		 String m_strQuerySparedConsumed="select typeid_pm_im,price_pm,partcode_pm,partmodel_pm,partname_pm "
			  		+ " from erprmwise.inventorymst_im i,erprmwise.partmst_pm p "
			  		+ " where i.typeid_pm_im=p.partid_pm and ticketid_im_im='"+m_strTicketId+"' ";
		 
	     System.out.println("Spared Consumed Query "+m_strQuerySparedConsumed);
	     List<Object[]> l_objList=new ArrayList<Object[]>();
	     try {
			  l_objList=customRepoEntityIncidentMaster.returnListOfSelectStarFromConcept(m_strQuerySparedConsumed);
			      if(l_objList.isEmpty()) {
			    	System.out.println("Spare Is Empty for engId "+m_strEngId+" and ticket id "+m_strTicketId);  
			      }else {
			    	  n_intnoOfSparesConsumedForEng++;
			    	  for(Object[] m_strRmData : l_objList) {
			    		  try {
			    		     m_strSpareCost=String.valueOf(m_strRmData[1]);
			    		  }catch(Exception ex) { ex.printStackTrace(); m_strSpareCost="0"; }
			    		  n_intSparesConsumedCostOfEng += Integer.parseInt(m_strSpareCost);
			    	  }
			      }
		  }catch(Exception ex) {  ex.printStackTrace(); }
	 }
	 
	  public void getClaimAmmount(String m_strTicketId,String m_strEngId) {
		  
		  String m_strApprovedClaimAmount="";
		  String m_strPendingClaimAmount="";
		  String m_strApprovedflag="",m_strClaimType="";
		  String m_strClaimQuery="select approvedflag_cm,substatus_cm,claimtype_ctm_cm,approvedamount_cm,billamount_cm,incidentid_cm "
			  		+ " "
			  		+ " from claimmst_cm where deleteflag_cm='N' and incidentid_cm='"+m_strTicketId+"' and claimedby_em_cm='"+m_strEngId+"' ";
		  System.out.println("claim QUery "+m_strClaimQuery);
		  List<Object[]> l_objListClaim=new ArrayList<Object[]>();
		  
		      l_objListClaim=customRepoEntityIncidentMaster.returnListOfSelectStarFromConcept(m_strClaimQuery);
		      if(l_objListClaim.isEmpty()) {
		    	
		    	  System.out.println("Claim Is Empty for Engineer "+m_strEngId+ " And his ticketid "+m_strTicketId);  
		      }else {
		    	  n_intnoOfClaimForEng++;
		    	  for(Object[] m_strRmData : l_objListClaim) { 
		    		 
		    		  try {
			    			
			    		  try {
  			    		     m_strApprovedflag=String.valueOf(m_strRmData[0]);
			    		   }catch(Exception ex) { m_strApprovedflag="N"; }
			    		  
			    		      m_strClaimType=String.valueOf(m_strRmData[2]);
			    		      
			    		   try {   
			    		      if(m_strClaimTypeOfEngineer!=null && m_strClaimTypeOfEngineer.equals("")==false) {
			    		    	  m_strClaimTypeOfEngineer=m_strClaimTypeOfEngineer+","+m_strClaimType;
			    		      }else {
			    		    	  m_strClaimTypeOfEngineer=m_strClaimType;
			    		      }
			    		   }catch(Exception ex) {  } 
			    		      
			    		      
			    		   if(m_strApprovedflag.equals("N") || m_strApprovedflag.equals("No")) {
			    			   try {
			    			     m_strPendingClaimAmount=String.valueOf(m_strRmData[3]);
			    			   }catch(Exception ex) { ex.printStackTrace(); m_strPendingClaimAmount="0"; }
			    			   
			    			    n_strClaimPendingTickets++;
			    			   
			    			   try {
			    			      n_intClaimPendingAmountOfEngineer+= Integer.parseInt(m_strPendingClaimAmount);
			    			   }catch(Exception ex) {  }
			    			  
			    	    	}else if(m_strApprovedflag.equals("Y") || m_strApprovedflag.equals("Yes")) {
			    	    		try {
			    	    	    	m_strApprovedClaimAmount=String.valueOf(m_strRmData[3]);
			    	    		}catch(Exception ex) {  ex.printStackTrace(); m_strApprovedClaimAmount="0";  }
			    	    	   
			    	    		m_strClaimApprovedTotalTicket++;
			    	    		try {
			    			        n_intClaimApprovedAmountOfEngineer += Integer.parseInt(m_strApprovedClaimAmount);
			    	    		}catch(Exception ex) {  }
			    	    	}
			    		
			    		 
		    		 }catch(Exception ex) {  ex.printStackTrace(); }  
		    		 
		    	  }
 
		      }  
	  }


		
	
}