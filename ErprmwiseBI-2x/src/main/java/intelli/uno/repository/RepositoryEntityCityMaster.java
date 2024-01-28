package intelli.uno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import intelli.uno.entity.EntityCityMaster;

public interface RepositoryEntityCityMaster extends JpaRepository<EntityCityMaster, Long>{
	
	@Query("SELECT em.typeidCm, em.typevalueCm FROM EntityCityMaster em WHERE em.poplocationLmCm=?1 And em.deleteFlagCm =?2 ")
	public List<Object[]> returnListOfCityByPoplocationWithKeyValue(String poplocationId,String deleteFlagCm);
	
	@Query("SELECT em.typeidCm, em.typevalueCm FROM EntityCityMaster em WHERE em.deleteFlagCm =?1 ")
	public List<Object[]> returnListOfAllCityWithKeyValue(String deleteFlagCm);
	
	@Query("SELECT em.typeidCm, em.typevalueCm FROM EntityCityMaster em WHERE  em.stateidSmCm IN (?1) and em.deleteFlagCm =?2")
	public List<Object[]> returnListOfAllCityByRegionViaStateIdWithKeyValue(List<String> l_strStateId,String deleteFlagLm);
	
}
