package intelli.uno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import intelli.uno.entity.EntityStatusMaster;
public interface RepositoryEntityStatusMaster extends JpaRepository<EntityStatusMaster, Long>{
    
	@Query("SELECT sm.typeidSm FROM EntityStatusMaster sm where sm.typevalueSm =?1 and deleteflagSm =?2 ")
	public Long returnIdForValue(String status,String deleteflagSm);
	
	@Query("SELECT sm.typevalueSm FROM EntityStatusMaster sm where sm.typeidSm =?1 and deleteflagSm =?2 ")
	public String returnValueForId(Long id,String deleteflagSm);

	
}
