package intelli.uno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import intelli.uno.entity.EntityLocationMaster;

public interface RepositoryEntityLocationMaster extends JpaRepository<EntityLocationMaster,Long>{

	
	@Query("SELECT em.typeidLm, em.typevalueLm FROM EntityLocationMaster em WHERE em.stateSmLm=?1 And em.deleteFlagLm =?2 ")
	public List<Object[]> returnListOfPoplocationByStateWithKeyValue(String stateId,String deleteFlagLm);
	
	@Query("SELECT em.typeidLm, em.typevalueLm FROM EntityLocationMaster em WHERE em.deleteFlagLm =?1 ")
	public List<Object[]> returnListOfAllPoplocationWithKeyValue(String deleteFlagLm);
	
	@Query("SELECT em.typeidLm, em.typevalueLm FROM EntityLocationMaster em WHERE  em.stateSmLm IN (?1) and em.deleteFlagLm =?2")
	public List<Object[]> returnListOfAllPoplocationByRegionStateIdWithKeyValue(List<String> stateId,String deleteFlagLm);
	

	@Query("SELECT em.typevalueLm FROM EntityLocationMaster em WHERE em.typeidLm=?1 and em.deleteFlagLm =?2 ")
	public String returnValueForID(Long m_strPopId,String deleteFlagLm);
}
