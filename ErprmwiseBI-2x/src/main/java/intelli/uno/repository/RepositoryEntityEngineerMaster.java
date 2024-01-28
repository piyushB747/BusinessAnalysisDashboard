package intelli.uno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import intelli.uno.entity.EntityEngineerMaster;
import java.util.List;
public interface RepositoryEntityEngineerMaster extends JpaRepository<EntityEngineerMaster, Long>{
	
	@Query(
			"SELECT em FROM EntityEngineerMaster em where em.deleteFlagEm =?2 AND"
			+ " em.resignedFlagEm='N' and loginidEm =?1  "
			)
	public abstract EntityEngineerMaster findAllDetailsOfUserLogin(String loginId,String deleteflag,String resignedflag,String role);
	
	@Query(value=
			"select * from engineermst_em where typeid_em =?1",nativeQuery=true
			)
	public abstract EntityEngineerMaster findByEngineerDetails(int typeid);
	
	/**JPQL**/
	@Query("SELECT em.fnameEm,em.lnameEm FROM EntityEngineerMaster em where em.deleteFlagEm =?2 AND"
			+ " em.resignedFlagEm='N' and  em.typeidEm =?1 ")
	public EntityEngineerMaster returnFnamForId();

	/*Return Engineer Where It is not resigned*/
	@Query("SELECT CONCAT(em.fnameEm, ' ', em.lnameEm) FROM EntityEngineerMaster em WHERE em.deleteFlagEm = ?2 AND em.resignedFlagEm = 'N' AND em.typeidEm = ?1")
	public String returnValueForId(Long id, String deleteFlag);

	/*Return Engineer Where It is not resigned*/
	@Query("SELECT CONCAT(em.fnameEm, ' ', em.lnameEm) FROM EntityEngineerMaster em WHERE em.deleteFlagEm = ?2 AND em.typeidEm = ?1")
	public String returnValueForIdIncludedResigned(Long id, String deleteFlag);

	@Query("SELECT CONCAT(em.fnameEm, ' ', em.lnameEm) FROM EntityEngineerMaster em WHERE  em.typeidEm = ?1")
	public String returnValueForIdIncludedResignedIgnoreDeleteflag(Long id, String deleteFlag);
	
	
	@Query("SELECT em.fnameEm, em.lnameEm FROM EntityEngineerMaster em WHERE em.deleteFlagEm = 'N' AND em.resignedFlagEm = 'N' " +
	           "  ")
	public EntityEngineerMaster returnFnamForIdMenTest(@Param("moreCondition") String moreCondition);

	public abstract EntityEngineerMaster findByLoginidEm(String loginidEm);
	public abstract EntityEngineerMaster findFirstByLoginidEm(String loginidEm);

	

	
	/***RETURNING SALLERY OF CUSTOMER**/
	@Query("SELECT salaryEm FROM EntityEngineerMaster em WHERE em.deleteFlagEm = ?2 AND em.typeidEm = ?1")
	public String returnSalaryForIdIncludedResigned(Long id, String deleteFlag);
	
	@Query("SELECT em FROM EntityEngineerMaster em WHERE em.deleteFlagEm =?1 ")
	public List<EntityEngineerMaster> l_objListOfEng(String deleteFlag);

	
	@Query("SELECT em FROM EntityEngineerMaster em WHERE em.deleteFlagEm = 'N' AND em.resignedFlagEm = 'N'"
			+ "  AND em.typeidEm = :moreCondition")
	public abstract EntityEngineerMaster returnUser(@Param("moreCondition") Long moreCondition);

}
