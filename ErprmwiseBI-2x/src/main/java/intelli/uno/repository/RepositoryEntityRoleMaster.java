package intelli.uno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import intelli.uno.commons.CommonConstants;
import intelli.uno.entity.EntityRoleMaster;

public interface RepositoryEntityRoleMaster extends JpaRepository<EntityRoleMaster, Long>{

	@Query(value="Select typevalue_rm from "+CommonConstants.db_Name+".rolemst_rm where typeid_rm=?1 and deleteflag_rm='N' ",nativeQuery = true)
	public abstract String returnValueOfId(String id);
	
	public abstract EntityRoleMaster findByTypevalueRmAndDeleteflagRm(String typevalueRm,String deleteflagRm);
	
	@Query("SELECT em.typeidRm FROM EntityRoleMaster em where em.deleteflagRm =?1 AND em.typevalueRm =?2 ")
	public abstract Long returnValueForId(String deleteFlagEm,String role);
	
	@Query("SELECT em.typevalueRm FROM EntityRoleMaster em where em.deleteflagRm =?1 AND em.typeidRm =?2 ")
	public abstract String returnValueForIdString(String deleteFlagEm,Long role);
	
}
