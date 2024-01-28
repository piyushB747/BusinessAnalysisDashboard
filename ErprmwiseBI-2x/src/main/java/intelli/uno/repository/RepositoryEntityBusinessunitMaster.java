package intelli.uno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import intelli.uno.entity.EntityBusinessunitMaster;

public interface RepositoryEntityBusinessunitMaster extends JpaRepository<EntityBusinessunitMaster, Long>{

	@Query("SELECT em.typeidBum,em.typevalueBum  FROM EntityBusinessunitMaster em WHERE em.deleteflagBum ='N' order by"
			+ " typevalueBum ASC ")
	public abstract List<Object[]> returnListOfBu();
	
	@Query("SELECT em.typeidBum, em.typevalueBum  FROM EntityBusinessunitMaster em WHERE em.deleteflagBum ='N' and em.typeidBum IN (?1) order by"
			+ " em.typevalueBum ASC ")
	public abstract List<Object[]> returnListOfBuForSpecificUser(List<Long> l_longBuIdList);
	
}
