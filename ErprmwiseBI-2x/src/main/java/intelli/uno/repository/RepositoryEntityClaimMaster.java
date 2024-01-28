package intelli.uno.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import intelli.uno.entity.EntityClaimMaster;

public interface RepositoryEntityClaimMaster extends JpaRepository<EntityClaimMaster, Long>{

	
	@Query(value = "SELECT COUNT(*) AS record_count, SUM(approvedamount_cm) AS total_approved_amount " +
            "FROM claimmst_cm " +
            "WHERE deleteflag_cm = 'N' AND claimedby_em_cm =?1 ",
    nativeQuery = true)
    Integer[][] getTotalClaimAndTotalClaimAmount(int n_intEngId);
}
