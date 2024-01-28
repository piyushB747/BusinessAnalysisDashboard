package intelli.uno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import intelli.uno.entity.EntityContractDetailsnewMaster;


public interface RepositoryEntityContractDetailsnewMaster extends JpaRepository<EntityContractDetailsnewMaster, Long> {
	/*
	 
	 Select typeid_cdnm,start_date,end_date,
	creation_time_stamp,customerid_cm_cdnm,rate_cdnm, total_amount,contractrefrenceno_cdnm,
	 startdate_cdnm, enddate_cdnm, contractenddate_cdnm,contractvalue_cdnm,contractcost_cdnm,monthlyamount,
	 amountpaid,remains_amount,yearly_monthly,terminationdate_cdnm,terminationreason_cdnm,contract_active_cdnm
	from contractdetailsnewmst_cdnm where deleteflag_cdnm='N'
	 AND MONTH(start_date) BETWEEN 1 AND 12
	 AND YEAR(start_date) = 2022
	order by typeid_cdnm desc limit 1
	 
	 */
	
	
	/*
	 
    @Query("SELECT e FROM YourEntity e WHERE MONTH(e.startDate) = :month")
List<YourEntity> findByStartMonth(@Param("month") int month);

*/
	
	@Query(value="SELECT rate_cdnm from contractdetailsnewmst_cdnm where customerid_cm_cdnm=?1 "
			+ " AND YEAR(start_date) = ?2 AND MONTH(start_date) = ?3 AND YEAR(end_date) = ?4 AND MONTH(end_date) = ?5 order by typeid_cdnm desc limit 1 "
			,nativeQuery=true)
	public abstract String returnContractValueFromCustomerIdAndYear(String custId,String startyear,String startmonth,String engYear,String endMonth);
	
	
	@Query(value="SELECT rate_cdnm from contractdetailsnewmst_cdnm where customerid_cm_cdnm=?1 "
			+ " AND YEAR(start_date) = ?2  AND YEAR(end_date) = ?3  order by typeid_cdnm desc limit 1 "
			,nativeQuery=true)
	public abstract String returnContractValueFromCustomerIdAndYearOnly(String custId,String startyear,String endYear);
	
	@Query(value="SELECT rate_cdnm from contractdetailsnewmst_cdnm where customerid_cm_cdnm=?1 "
			+ " AND YEAR(start_date) = ?2   order by typeid_cdnm desc limit 1 "
			,nativeQuery=true)
	public abstract String returnContractValueFromCustomerIdAndOneStartYearParam(String custId,String startyear);
	
	@Query(value="SELECT rate_cdnm from contractdetailsnewmst_cdnm where customerid_cm_cdnm=?1 "
			+ " AND YEAR(end_date) = ?2   order by typeid_cdnm desc limit 1 "
			,nativeQuery=true)
	public abstract String returnContractValueFromCustomerIdAndOneEndYearParam(String custId,String endyear);
	
	
	@Query("SELECT em.rateCdnm FROM EntityContractDetailsnewMaster em where em.customeridCdnm=?1  ")
	public abstract String returnContractValueFromCustomerId(String custId);
	
	
	
	
}
