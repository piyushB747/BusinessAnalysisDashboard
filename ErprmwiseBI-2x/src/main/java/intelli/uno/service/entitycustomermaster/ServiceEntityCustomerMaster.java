package intelli.uno.service.entitycustomermaster;

import java.util.List;

import intelli.uno.entity.EntityCustomerMaster;


public interface ServiceEntityCustomerMaster {

	public abstract List<EntityCustomerMaster> returnListOfAllCustomer(String deleteflagCm);
	
	public abstract List<EntityCustomerMaster> returnListOfAllCustomerByPc(String pcIntId,String deleteflagCm);
	
	public abstract List<EntityCustomerMaster> returnListOfAllCustomerByBu(String BuId,String deleteflagCm);
}
