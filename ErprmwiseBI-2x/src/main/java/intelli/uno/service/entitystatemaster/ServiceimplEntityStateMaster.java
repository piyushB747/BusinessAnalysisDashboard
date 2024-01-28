package intelli.uno.service.entitystatemaster;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import intelli.uno.entity.EntityStateMaster;
import intelli.uno.repository.RepositoryEntityStateMaster;


@Service
public class ServiceimplEntityStateMaster implements ServiceEntityStateMaster{

	@Autowired
	private RepositoryEntityStateMaster repoEntityStateMaster;
	
	@Override
	public List<EntityStateMaster> returnListOfStateByRegionWithKeyValue(String regionId,String deleteFlagSm) {
		List<Object[]> ListCustomerData = repoEntityStateMaster.returnListOfStateByRegionWithKeyValue(regionId,deleteFlagSm);
		List<EntityStateMaster> buCustomerList = new ArrayList<>();
		for(Object[] customerData : ListCustomerData) {
			EntityStateMaster buObject = new EntityStateMaster();
			buObject.setTypeidSm((Long) customerData[0]);
			buObject.setTypevalueSm((String) customerData[1]);
			
			buCustomerList.add(buObject);
	    }
		return buCustomerList;
	}

	@Override
	public List<EntityStateMaster> returnListOfAllStateWithKeyValue(String deleteflagRm) {
		List<EntityStateMaster> l_objStateList = new ArrayList<>();
		try {
		List<Object[]> l_objListData = repoEntityStateMaster.returnListOfAllStateWithKeyValue("N");
		
		for(Object[] m_strStateData : l_objListData) {
			EntityStateMaster smObject = new EntityStateMaster();
			smObject.setTypeidSm((Long) m_strStateData[0]);
			smObject.setTypevalueSm((String) m_strStateData[1]);
			l_objStateList.add(smObject);
	    }
		}catch(Exception ex) { ex.printStackTrace(); }
		return l_objStateList;
	}

	@Override
	public List<String> returnListOfStateIdByRegionWithKeyValue(String regionId, String deleteflagRm) {
		List<String> l1=repoEntityStateMaster.returnListOfStateIdByRegionWithKeyValue(regionId, "N");
		return l1;
	}

}
