package intelli.uno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import intelli.uno.entity.EntityProductcategoryMaster;


public interface RepositoryEntityProductcategoryMaster extends JpaRepository<EntityProductcategoryMaster, Long>{

	
	@Query("SELECT em.typevaluePcm FROM EntityProductcategoryMaster em WHERE em.deleteflagPcm = ?2 AND em.typeidPcm = ?1")
	public String returnValueForId(Long id, String deleteFlag);

}
