package intelli.uno.service.entityregionmaster;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import intelli.uno.entity.EntityRegionMaster;
import intelli.uno.repository.RepositoryEntityRegionMaster;

@Service
public class ServiceimplEntityRegionMaster implements ServiceEntityRegionMaster{

	@Autowired
	private RepositoryEntityRegionMaster repoEntityRegionMaster;
	
	@Override
	public List<EntityRegionMaster> returnListOfRegionWithKeyValue() {
		List<Object[]> buDataList = repoEntityRegionMaster.returnListOfRegionWithKeyValue("N");
		List<EntityRegionMaster> buEntityList = new ArrayList<>();
		for(Object[] customerData : buDataList) {
			EntityRegionMaster buObject = new EntityRegionMaster();
			buObject.setTypevalueRm((String) customerData[0]);
			buObject.setTypeidRm((Long) customerData[1]);
			buEntityList.add(buObject);
	    }
		return buEntityList;
	}
	

}
