package intelli.uno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import intelli.uno.entity.EntityClaimtypeMaster;


public interface RepositoryEntityClaimtypeMaster extends JpaRepository<EntityClaimtypeMaster, Long>{

	
	/***RETURNING SALLERY OF CUSTOMER**/
	@Query("SELECT em.typevalueCtm FROM EntityClaimtypeMaster em WHERE em.deleteflagCtm = ?2 AND em.typeidCtm = ?1")
	public String returnClaimTypeForId(Long id, String deleteFlag);
}
