package intelli.uno.service.entityprincipalcustomermaster;

import java.util.List;

import intelli.uno.dto.DtoPrincipleCustomer;


public interface ServiceEntityPrincipalcustomerMaster {

	
	public abstract List<DtoPrincipleCustomer> returnListOfAllPcustomer(String deleteflagPcm);
	
	public abstract List<DtoPrincipleCustomer> returnListOfAllPcustomerByBuIds(String businessunitidbumPcm,String deleteFlag);
}
