package intelli.uno.service.entitylocationmaster;

import java.util.List;

import intelli.uno.entity.EntityLocationMaster;

public interface ServiceEntityLocationMaster {

	public List<EntityLocationMaster> returnListOfPoplocationByStateWithKeyValue(String stateId,String deleteFlagLm);

	public List<EntityLocationMaster> returnListOfAllPoplocationWithKeyValue(String deleteFlagLm);

	public List<EntityLocationMaster> returnListOfAllPoplocationByRegionStateIdWithKeyValue(List<String> l_strStateId,String deleteFlagLm);
	
}
