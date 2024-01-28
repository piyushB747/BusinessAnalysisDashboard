package intelli.uno.service.entitylocationmaster;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import intelli.uno.entity.EntityLocationMaster;
import intelli.uno.repository.RepositoryEntityLocationMaster;


@Service
public class ServiceimplEntityLocationMaster implements ServiceEntityLocationMaster{

	@Autowired
	private RepositoryEntityLocationMaster repoEntityLocationMaster;
	
	@Override
	public List<EntityLocationMaster> returnListOfPoplocationByStateWithKeyValue(String stateId, String deleteFlagLm) {
		List<EntityLocationMaster> l_objPopList = new ArrayList<>();
		try {
			List<Object[]> l_objListData = repoEntityLocationMaster.returnListOfPoplocationByStateWithKeyValue(stateId, "N");
			for(Object[] m_strStateData : l_objListData) {
				EntityLocationMaster smObject = new EntityLocationMaster();
				smObject.setTypeidLm((Long) m_strStateData[0]);
				smObject.setTypevalueLm((String) m_strStateData[1]);
				l_objPopList.add(smObject);
		    }
		}catch(Exception ex) { ex.printStackTrace(); }
		return l_objPopList;
	}

	
	@Override
	public List<EntityLocationMaster> returnListOfAllPoplocationWithKeyValue(String deleteFlagLm) {
		List<EntityLocationMaster> l_objPopList = new ArrayList<>();
		try {
			List<Object[]> l_objListData = repoEntityLocationMaster.returnListOfAllPoplocationWithKeyValue("N");
			for(Object[] m_strStateData : l_objListData) {
				EntityLocationMaster smObject = new EntityLocationMaster();
				smObject.setTypeidLm((Long) m_strStateData[0]);
				smObject.setTypevalueLm((String) m_strStateData[1]);
				l_objPopList.add(smObject);
		    }
		}catch(Exception ex) { ex.printStackTrace(); }
		return l_objPopList;
	}


	@Override
	public List<EntityLocationMaster> returnListOfAllPoplocationByRegionStateIdWithKeyValue(List<String> l_strStateId,
			String deleteFlagLm) {
		List<EntityLocationMaster> l_objPopList = new ArrayList<>();
		try {
			List<Object[]> l_objListData = repoEntityLocationMaster.returnListOfAllPoplocationByRegionStateIdWithKeyValue(l_strStateId,"N");
			for(Object[] m_strStateData : l_objListData) {
				EntityLocationMaster smObject = new EntityLocationMaster();
				smObject.setTypeidLm((Long) m_strStateData[0]);
				smObject.setTypevalueLm((String) m_strStateData[1]);
				l_objPopList.add(smObject);
		    }
		}catch(Exception ex) { ex.printStackTrace(); }
		return l_objPopList;
	}

}
