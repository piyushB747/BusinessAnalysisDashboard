package intelli.uno.service.entityprincipalcustomermaster;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import intelli.uno.dto.DtoPrincipleCustomer;
import intelli.uno.entity.EntityPrincipalcustomerMaster;
import intelli.uno.repository.RepositoryEntityPrincipalcustomerMaster;


@Service
public class ServiceimplEntityPrincipalcustomerMaster implements ServiceEntityPrincipalcustomerMaster{

	@Autowired(required = true)
	private ModelMapper modelMapper;
	
	@Autowired
	private RepositoryEntityPrincipalcustomerMaster repoPrincipalcustomerMaster;
	
	@Override
	public List<DtoPrincipleCustomer> returnListOfAllPcustomer(String deleteflagPcm) {
		List<Object[]> customerDataList = repoPrincipalcustomerMaster.returnListOfAllPcustomer();
	    List<EntityPrincipalcustomerMaster> customerList = new ArrayList<>();
	    for(Object[] customerData : customerDataList) {
	        EntityPrincipalcustomerMaster customer = new EntityPrincipalcustomerMaster();
	        customer.setCustomernamePcm((String) customerData[0]);
	        customer.setTypeidPcm((Long) customerData[1]);
	        customerList.add(customer);
	    }

	    List<DtoPrincipleCustomer>  m_ObjListOfIncidentMaster= customerList
				.stream().map(this::convertEntityToDto)
				.collect(Collectors.toList());
	    
		return m_ObjListOfIncidentMaster;
	}

	
	@Override
	public List<DtoPrincipleCustomer> returnListOfAllPcustomerByBuIds(String businessunitidbumPcm, String deleteFlag) {
		List<Object[]> customerDataList = repoPrincipalcustomerMaster.returnListOfAllPcustomerByBuIds(businessunitidbumPcm,deleteFlag);
	    List<EntityPrincipalcustomerMaster> customerList = new ArrayList<>();
	    for(Object[] customerData : customerDataList) {
	        EntityPrincipalcustomerMaster customer = new EntityPrincipalcustomerMaster();
	        customer.setCustomernamePcm((String) customerData[0]);
	        customer.setTypeidPcm((Long) customerData[1]);
	        customerList.add(customer);
	    }

	    List<DtoPrincipleCustomer>  m_ObjListOfIncidentMaster= customerList
				.stream().map(this::convertEntityToDto)
				.collect(Collectors.toList());
	    
		return m_ObjListOfIncidentMaster;
	}
	
	
	/**Converting Entity Into DTO using Library**/
	public DtoPrincipleCustomer convertEntityToDto(EntityPrincipalcustomerMaster user) {		
		DtoPrincipleCustomer userLocationDto=new DtoPrincipleCustomer();
		
		userLocationDto=modelMapper.map(user, DtoPrincipleCustomer.class);
		return userLocationDto;		
	}



}
