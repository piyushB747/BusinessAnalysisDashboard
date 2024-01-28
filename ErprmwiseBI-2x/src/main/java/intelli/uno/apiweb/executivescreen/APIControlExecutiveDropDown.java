package intelli.uno.apiweb.executivescreen;


import java.util.ArrayList;
import java.util.Currency;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import intelli.uno.dto.DtoPrincipleCustomer;
import intelli.uno.entity.EntityCustomerMaster;
import intelli.uno.entity.EntityCityMaster;
import intelli.uno.entity.EntityLocationMaster;
import intelli.uno.entity.EntityStateMaster;
import intelli.uno.service.entitycitymaster.ServiceEntityCityMaster;
import intelli.uno.service.entitycustomermaster.ServiceEntityCustomerMaster;
import intelli.uno.service.entitylocationmaster.ServiceEntityLocationMaster;
import intelli.uno.service.entityprincipalcustomermaster.ServiceEntityPrincipalcustomerMaster;
import intelli.uno.service.entitystatemaster.ServiceEntityStateMaster;

@RestController
@CrossOrigin(origins="*")
public class APIControlExecutiveDropDown {

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
	
	@GetMapping("/getstatesbyregion")
	public ResponseEntity<List<EntityStateMaster>> getStatesByRegion(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="regionaction",required=true)String m_strregionIdRequest) {
		 
		 List<EntityStateMaster> l_strListOfCustomer=new ArrayList<>();
		 System.out.println(m_strregionIdRequest+" RegionAction");
		  try {
				if (m_strregionIdRequest != null && m_strregionIdRequest.equals("All") == false) {

					l_strListOfCustomer = serviceEntityStateMaster.returnListOfStateByRegionWithKeyValue(m_strregionIdRequest,"N");
				} else {
					//EntityStateMaster e1 = new EntityStateMaster();  l_strListOfCustomer.add(e1);
					l_strListOfCustomer = serviceEntityStateMaster.returnListOfAllStateWithKeyValue("N");
				}
		  }catch(Exception ex) {ex.printStackTrace(); }
		return ResponseEntity.ok(l_strListOfCustomer);
	}
	
	
	@GetMapping("/getlocationbystate")
	public ResponseEntity<List<EntityLocationMaster>> getLocationByState(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="stateaction",required=false)String m_stStateIdRequest,
			@RequestParam(value="regionIdaction",required=false)String m_stStateRegionIdRequest) {
		 List<EntityLocationMaster> l_strListOfCustomer=new ArrayList<>();
		 System.out.println("Piyush Data");
		  try {
				if (m_stStateIdRequest != null && m_stStateIdRequest.equals("All") == false) {
					 System.out.println(m_stStateIdRequest+" StateAction");
					l_strListOfCustomer = serviceEntityLocationMaster.returnListOfPoplocationByStateWithKeyValue(m_stStateIdRequest,"N");
				}else if(m_stStateRegionIdRequest != null && m_stStateRegionIdRequest.equals("All") == false) {
					 System.out.println(m_stStateRegionIdRequest+" m_stStateRegionIdRequest");
					 List<String> l_strOfStateId=serviceEntityStateMaster.returnListOfStateIdByRegionWithKeyValue(m_stStateRegionIdRequest, "N");
					 l_strListOfCustomer = serviceEntityLocationMaster.returnListOfAllPoplocationByRegionStateIdWithKeyValue(l_strOfStateId,"N");
					/**Here Getting Region Id**/ 
					 
				}else {
					//EntityStateMaster e1 = new EntityStateMaster();  l_strListOfCustomer.add(e1);
					l_strListOfCustomer = serviceEntityLocationMaster.returnListOfAllPoplocationWithKeyValue("N");
				}
		  }catch(Exception ex) {ex.printStackTrace(); }
		return ResponseEntity.ok(l_strListOfCustomer);
	}
	
	@GetMapping("/getcitybypop")
	public ResponseEntity<List<EntityCityMaster>> getCityByLocation(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="popaction",required=false)String m_strPopId,
			@RequestParam(value="regionIdaction",required=false)String m_stStateRegionIdRequest,
			@RequestParam(value="popstateactionId",required=false)String m_stStateIdRequest) {
		
		  List<EntityCityMaster> l_objListOfCity=new ArrayList<>();
		
		  try {
				if (m_strPopId != null && m_strPopId.equals("All") == false) {
					 System.out.println(m_strPopId+" getCityByLocation");
					l_objListOfCity = serviceEntityCityMaster.returnListOfCityByPoplocationWithKeyValue(m_strPopId,"N");
				}else if(m_stStateRegionIdRequest != null && m_stStateRegionIdRequest.equals("All") == false) {
					 
					 System.out.println(m_stStateRegionIdRequest+" m_stStateRegionIdRequest In CITY");
					 /**Here we are returning state Id when we are selecting particular region then we are returning all states**/
					 List<String> l_strOfStateId=serviceEntityStateMaster.returnListOfStateIdByRegionWithKeyValue(m_stStateRegionIdRequest, "N");
					 l_objListOfCity = serviceEntityCityMaster.returnListOfAllCityByRegionViaStateIdWithKeyValue(l_strOfStateId,"N");
					 
				}else if(m_stStateIdRequest != null && m_stStateIdRequest.equals("All") == false) {
					 System.out.println(m_stStateIdRequest+" m_stStateIdRequest By State Id In CITY");
					List<String>  l_strOfStateId=new ArrayList<String>();
					try {
						l_strOfStateId.add(m_stStateIdRequest);
						l_objListOfCity = serviceEntityCityMaster.returnListOfAllCityByRegionViaStateIdWithKeyValue(l_strOfStateId,"N");
					}catch(Exception ex) { ex.printStackTrace(); }
				}
				else {
					//EntityStateMaster e1 = new EntityStateMaster();  l_strListOfCustomer.add(e1);
					l_objListOfCity = serviceEntityCityMaster.returnListOfAllCityWithKeyValue("N");
				}
		  }catch(Exception ex) {ex.printStackTrace(); }
		return ResponseEntity.ok(l_objListOfCity);
	}
	
	
	@GetMapping("/getPricipalcust")
	public ResponseEntity<List<DtoPrincipleCustomer>> getPrincipalCustomers(
			@RequestParam(value="buIdaction",required=false)String pc) {
		
		 List<DtoPrincipleCustomer> l_strListOfCustomer=new ArrayList<>();
		 
		 try {
				if(pc!=null && pc.equals("All")==false) {
					l_strListOfCustomer=serviceEntityPrincipalcustomerMaster.returnListOfAllPcustomerByBuIds(pc,"N");	
				}else {
					l_strListOfCustomer=serviceEntityPrincipalcustomerMaster.returnListOfAllPcustomer("N");
				}
		 }catch(Exception ex) { ex.printStackTrace(); }
	    return ResponseEntity.ok(l_strListOfCustomer);
	}
	
	
	@GetMapping("/getCustomers")
	public ResponseEntity<List<EntityCustomerMaster>> getCustomers(
			@RequestParam(value="pcId",required=false)String m_strpcActionRequest,
			@RequestParam(value="buIdaction",required=false)String m_strBuIdActionRequest) {
		
		 List<EntityCustomerMaster> l_strListOfCustomer=new ArrayList<>();
		
		 try {
				if(m_strpcActionRequest!=null && m_strpcActionRequest.equals("All")==false) {
					l_strListOfCustomer=serviceEntityCustomerMaster.returnListOfAllCustomerByPc(m_strpcActionRequest,"N");	
				}else if(m_strBuIdActionRequest != null && m_strBuIdActionRequest.equals("All") == false) {
					
					l_strListOfCustomer=serviceEntityCustomerMaster.returnListOfAllCustomerByBu(m_strBuIdActionRequest,"N");	
				}else {
					l_strListOfCustomer=serviceEntityCustomerMaster.returnListOfAllCustomer("N");
				}
		 }catch(Exception ex) { ex.printStackTrace(); }
	    return ResponseEntity.ok(l_strListOfCustomer);
	}

	
	@GetMapping("/getCurrency")
	public String getCurrencyForApproximateMarginDetailMargins(
			@RequestParam(value="currencyId",required=false)String m_strCurrencyId,
			@RequestParam(value="buIdaction",required=false)String m_strBuIdActionRequest) {
		Currency m_strCurrencySymbol=null;
		
		
			try {
				if(m_strCurrencyId!=null && m_strCurrencyId.equals("")==false) {
					try {
						m_strCurrencySymbol = Currency.getInstance(m_strCurrencyId);	
					}catch(Exception ex) {   System.out.println( "ERROR IN CURRENCY" +m_strCurrencyId);  }
				   
				}else {		 
			         m_strCurrencySymbol = Currency.getInstance("INR");
				}
			}catch(Exception ex) {
			    m_strCurrencySymbol = Currency.getInstance("USD");   
			}  
			
			if(m_strCurrencySymbol.getSymbol().equals("INR")) {
				return "Rs";
			}else {
				return m_strCurrencySymbol.getSymbol();		
			}
			
		 
	}

}
