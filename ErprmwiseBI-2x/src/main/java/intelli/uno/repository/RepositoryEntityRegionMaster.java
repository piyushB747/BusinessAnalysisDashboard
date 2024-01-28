package intelli.uno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import intelli.uno.entity.EntityRegionMaster;


public interface RepositoryEntityRegionMaster extends JpaRepository<EntityRegionMaster, Long>{
	/**
	@Query("SELECT em.typeidRm,em.typevalueRm  FROM EntityRegionMaster em WHERE em.deleteflagRm ='N' order by ")
	public List<Object[]> returnListOfPcustomer();
    **/
	
	@Query("SELECT em.typevalueRm FROM EntityRegionMaster em WHERE em.deleteflagRm =?1 and em.typeidRm=?2 ")
	public String returnValueFromId(String deleteflagRm,Long n_longId);
	
	@Query("SELECT em.typevalueRm, em.typeidRm FROM EntityRegionMaster em WHERE em.deleteflagRm =?1 order by em.typevalueRm ")
	public List<Object[]> returnListOfRegionWithKeyValue(String deleteflagRm);
	
}
