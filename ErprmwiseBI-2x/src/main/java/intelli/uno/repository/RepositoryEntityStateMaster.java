package intelli.uno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import intelli.uno.entity.EntityStateMaster;

public interface RepositoryEntityStateMaster extends JpaRepository<EntityStateMaster, Long>{

	
	@Query("SELECT em.typeidSm, em.typevalueSm FROM EntityStateMaster em WHERE em.regionRmSm=?1 And em.deleteFlagSm =?2 ")
	public List<Object[]> returnListOfStateByRegionWithKeyValue(String regionId,String deleteflagRm);
	
	@Query("SELECT em.typeidSm, em.typevalueSm FROM EntityStateMaster em WHERE em.deleteFlagSm =?1 ")
	public List<Object[]> returnListOfAllStateWithKeyValue(String deleteflagRm);
	
	
	@Query("SELECT em.typeidSm FROM EntityStateMaster em WHERE em.regionRmSm=?1 And em.deleteFlagSm =?2 ")
	public List<String> returnListOfStateIdByRegionWithKeyValue(String regionId,String deleteflagRm);
	
	@Query("SELECT em.typevalueSm FROM EntityStateMaster em WHERE em.typeidSm=?1 And em.deleteFlagSm =?2 ")
	public String returnValueFromId(Long typeidSm,String deleteflagRm);
}
