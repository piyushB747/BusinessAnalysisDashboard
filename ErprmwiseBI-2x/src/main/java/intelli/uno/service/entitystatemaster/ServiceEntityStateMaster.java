package intelli.uno.service.entitystatemaster;

import java.util.List;

import intelli.uno.entity.EntityStateMaster;


public interface ServiceEntityStateMaster {

	public List<EntityStateMaster> returnListOfStateByRegionWithKeyValue(String regionId,String deleteflagRm);

	public List<EntityStateMaster> returnListOfAllStateWithKeyValue(String deleteflagRm);   
	
	public List<String> returnListOfStateIdByRegionWithKeyValue(String regionId,String deleteflagRm);
}
