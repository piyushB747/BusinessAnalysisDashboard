package intelli.uno.service.entitycitymaster;

import java.util.List;

import intelli.uno.entity.EntityCityMaster;


public interface ServiceEntityCityMaster {
	
	public List<EntityCityMaster> returnListOfCityByPoplocationWithKeyValue(String stateId,String deleteFlagLm);
	
	public List<EntityCityMaster> returnListOfAllCityWithKeyValue(String deleteFlagLm);
	
	public List<EntityCityMaster> returnListOfAllCityByRegionViaStateIdWithKeyValue(List<String> l_strStateId,String deleteFlagLm);
}
