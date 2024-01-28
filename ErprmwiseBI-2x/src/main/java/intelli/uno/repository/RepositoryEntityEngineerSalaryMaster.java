package intelli.uno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import intelli.uno.entity.EntityEngineerSalaryMaster;


public interface RepositoryEntityEngineerSalaryMaster extends JpaRepository<EntityEngineerSalaryMaster, Long>{

	@Query("SELECT em.monthEsm,em.engsalaryEsm FROM EntityEngineerSalaryMaster em WHERE em.engIdEsm = ?1 AND em.deleteflagEsm = ?2 AND em.yearEsm = ?3"
			+ " order by idEsm DESC  ")
	public String[][] returnEngMonthAndSalary(String engId, String deleteFlag, String yearEsm);
	
	@Query(value="SELECT * FROM engineersalarymaster_esm em WHERE em.engid_esm = ?1 AND em.deleteflag_esm = ?2 AND em.year_esm = ?3"
			+ " order by id_esm DESC LIMIT 1 ",nativeQuery=true)
	public EntityEngineerSalaryMaster returnEngSalaryIdForUpdation(String engId, String deleteFlag, String yearEsm);
	
}
