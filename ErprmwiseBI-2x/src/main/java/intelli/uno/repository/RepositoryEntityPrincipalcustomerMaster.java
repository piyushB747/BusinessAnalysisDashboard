package intelli.uno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import intelli.uno.entity.EntityPrincipalcustomerMaster;

public interface RepositoryEntityPrincipalcustomerMaster extends JpaRepository<EntityPrincipalcustomerMaster, Long>{

	//select typeid_pcm, customername_pcm,businessunitid_bum_pcm,deleteflag_pcm from principalcustomermst_pcm where deleteflag_pcm='N' and businessunitid_bum_pcm='2';
	
	@Query("SELECT em.customernamePcm FROM EntityPrincipalcustomerMaster em WHERE em.deleteflagPcm = ?2 AND em.typeidPcm = ?1")
	public String returnValueForId(Long id, String deleteFlag);

	/*
	  @Query("SELECT em.customernamePcm,em.typeidPcm  FROM EntityPrincipalcustomerMaster em WHERE em.deleteflagPcm ='N' ") 
	  public List<EntityPrincipalcustomerMaster> returnListOfPcustomerError();
	 */

	@Query("SELECT em.customernamePcm,em.typeidPcm  FROM EntityPrincipalcustomerMaster em WHERE em.deleteflagPcm ='N' ")
	public List<Object[]> returnListOfAllPcustomer();
	
	@Query("SELECT em.customernamePcm,em.typeidPcm  FROM EntityPrincipalcustomerMaster em WHERE em.businessunitidbumPcm=?1 and em.deleteflagPcm ='N' ")
	public List<Object[]> returnListOfAllPcustomerByBuIds(String businessunitidbumPcm,String deleteFlag);
	
	@Query("SELECT em.typeidPcm  FROM EntityPrincipalcustomerMaster em WHERE em.deleteflagPcm ='N' ")
	public List<Long> returnListOfAllPcustomerId();
	
}
