package intelli.uno.service.entitybusinessunitmaster;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import intelli.uno.entity.EntityBusinessunitMaster;
import intelli.uno.repository.RepositoryEntityBusinessunitMaster;

@Service
public class ServiceImplEntityBusinsessunitMaster implements ServiceEntityBusinsessunitMaster{

	@Autowired
	private RepositoryEntityBusinessunitMaster repoEntityBusinessunitMaster;
	
	@Override
	public List<EntityBusinessunitMaster> returnListOfBuForSpecificUser(List<Long> l_longBuIdList,String deleteflagBum){
		List<Object[]> buDataList = repoEntityBusinessunitMaster.returnListOfBuForSpecificUser(l_longBuIdList);
		List<EntityBusinessunitMaster> buEntityList = new ArrayList<>();
		for(Object[] customerData : buDataList) {
			EntityBusinessunitMaster buObject = new EntityBusinessunitMaster();
			buObject.setTypeidBum((Long) customerData[0]);
			buObject.setTypevalueBum((String) customerData[1]);
			buEntityList.add(buObject);
	    }
		return buEntityList;
	}

}
