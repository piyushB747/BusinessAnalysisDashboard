package intelli.uno.service.entitycustomermaster;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import intelli.uno.entity.EntityCustomerMaster;
import intelli.uno.repository.RepositoryEntityCustomerMaster;

@Service
public class ServiceImplEntityCustomerMaster implements ServiceEntityCustomerMaster{

	@Autowired
	private RepositoryEntityCustomerMaster repositoryEntityCustomerMaster;
	
	@Override
	public List<EntityCustomerMaster> returnListOfAllCustomer(String deleteflagCm) {
		List<Object[]> ListCustomerData = repositoryEntityCustomerMaster.returnListOfAllCustomer(deleteflagCm);
		List<EntityCustomerMaster> buCustomerList = new ArrayList<>();
		for(Object[] customerData : ListCustomerData) {
			EntityCustomerMaster buObject = new EntityCustomerMaster();
			buObject.setCustomernameCm((String) customerData[0]);
			buObject.setTypeidCm((Long) customerData[1]);
			buCustomerList.add(buObject);
	    }
		return buCustomerList;
	}

	@Override
	public List<EntityCustomerMaster> returnListOfAllCustomerByPc(String pcIntId, String deleteflagCm) {
		List<Object[]> ListCustomerData = repositoryEntityCustomerMaster.returnListOfAllCustomerByPc(pcIntId,deleteflagCm);
		List<EntityCustomerMaster> buCustomerList = new ArrayList<>();
		for(Object[] customerData : ListCustomerData) {
			EntityCustomerMaster buObject = new EntityCustomerMaster();
			buObject.setCustomernameCm((String) customerData[0]);
			buObject.setTypeidCm((Long) customerData[1]);
			buCustomerList.add(buObject);
	    }
		return buCustomerList;
	}

	@Override
	public List<EntityCustomerMaster> returnListOfAllCustomerByBu(String BuId, String deleteflagCm) {
		List<Object[]> ListCustomerData = repositoryEntityCustomerMaster.returnListOfAllCustomerByBu(BuId,deleteflagCm);
		List<EntityCustomerMaster> buCustomerList = new ArrayList<>();
		for(Object[] customerData : ListCustomerData) {
			EntityCustomerMaster buObject = new EntityCustomerMaster();
			buObject.setCustomernameCm((String) customerData[0]);
			buObject.setTypeidCm((Long) customerData[1]);
			buCustomerList.add(buObject);
	    }
		return buCustomerList;
	}


}
