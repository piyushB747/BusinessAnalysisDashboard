package intelli.uno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import intelli.uno.entity.EntityCustomerMaster;

public interface RepositoryEntityCustomerMaster extends JpaRepository<EntityCustomerMaster,Long>{

	//select typeid_cm, customername_cm,principalcustomer_pcm_cm, businessunit_bum_cm,deleteflag_cm from customermst_cm;
	
	@Query("SELECT em.customernameCm FROM EntityCustomerMaster em WHERE  em.typeidCm = ?1 AND em.deleteflagCm = ?2")
	public String returnValueForId(Long id, String deleteFlag);

	
	@Query("SELECT em.customernameCm,em.typeidCm  FROM EntityCustomerMaster em WHERE em.deleteflagCm =?1 "
			+ "  ")
	public List<Object[]> returnListOfAllCustomer(String deleteflagCm);
	
	@Query("SELECT em.customernameCm,em.typeidCm  FROM EntityCustomerMaster em WHERE principalcustomerpcmCm=?1 and em.deleteflagCm ='N' ")
	public List<Object[]> returnListOfAllCustomerByPc(String pcInt,String deleteflagCm);
	
	@Query("SELECT em.customernameCm,em.typeidCm  FROM EntityCustomerMaster em WHERE em.businessunitbumCm=?1 and em.deleteflagCm ='N' ")
	public List<Object[]> returnListOfAllCustomerByBu(String BuId,String deleteflagCm);
	
	
	@Query(value="select typeid_cm,customername_cm,principalcustomer_pcm_cm from customermst_cm where principalcustomer_pcm_cm is "
			+ "not null and customername_cm is not null\r\n"
			+ "and customername_cm!='' and principalcustomer_pcm_cm!=''\r\n"
			+ "and deleteflag_cm=?1 order by  principalcustomer_pcm_cm desc limit 100",nativeQuery=true)
	public List<Object[]> returnListOfAllCustomerForRealTime(String deleteflagCm);
	
	
}
