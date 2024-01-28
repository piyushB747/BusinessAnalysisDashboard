package intelli.uno.service.entitycitymaster;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import intelli.uno.entity.EntityCityMaster;
import intelli.uno.repository.RepositoryEntityCityMaster;


@Service
public class ServiceimplEntityCityMaster implements ServiceEntityCityMaster{

	@Autowired
	private RepositoryEntityCityMaster repoEntityCityMaster;
	
	@Override
	public List<EntityCityMaster> returnListOfCityByPoplocationWithKeyValue(String stateId, String deleteFlagLm) {
		List<EntityCityMaster> l_objCityList = new ArrayList<>();
			try {
				List<Object[]> ListCustomerData = repoEntityCityMaster.returnListOfCityByPoplocationWithKeyValue(stateId, deleteFlagLm);
				for(Object[] customerData : ListCustomerData) {
					EntityCityMaster buObject = new EntityCityMaster();
					buObject.setTypeidCm((Long) customerData[0]);
					buObject.setTypevalueCm((String) customerData[1]);
					
					l_objCityList.add(buObject);
			    }
			}catch(Exception ex) { ex.printStackTrace(); }
		return l_objCityList;
	}

	@Override
	public List<EntityCityMaster> returnListOfAllCityWithKeyValue(String deleteFlagLm) {
		List<EntityCityMaster> l_objCityList = new ArrayList<>();
		try {
			List<Object[]> ListCustomerData = repoEntityCityMaster.returnListOfAllCityWithKeyValue(deleteFlagLm);
			for(Object[] customerData : ListCustomerData) {
				EntityCityMaster buObject = new EntityCityMaster();
				buObject.setTypeidCm((Long) customerData[0]);
				buObject.setTypevalueCm((String) customerData[1]);
				
				l_objCityList.add(buObject);
		    }
		}catch(Exception ex) { ex.printStackTrace(); }
	return l_objCityList;
	}

	@Override
	public List<EntityCityMaster> returnListOfAllCityByRegionViaStateIdWithKeyValue(List<String> l_strStateId,
			String deleteFlagLm) {
		List<EntityCityMaster> l_objCityList = new ArrayList<>();
		try {
			List<Object[]> ListCustomerData = repoEntityCityMaster.returnListOfAllCityByRegionViaStateIdWithKeyValue(l_strStateId,deleteFlagLm);
			for(Object[] customerData : ListCustomerData) {
				EntityCityMaster buObject = new EntityCityMaster();
				buObject.setTypeidCm((Long) customerData[0]);
				buObject.setTypevalueCm((String) customerData[1]);
				
				l_objCityList.add(buObject);
		    }
		}catch(Exception ex) { ex.printStackTrace(); }
	return l_objCityList;
	}

}
