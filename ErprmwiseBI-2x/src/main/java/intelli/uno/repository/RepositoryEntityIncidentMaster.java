package intelli.uno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import intelli.uno.commons.CommonConstants;
import intelli.uno.entity.EntityIncidentMaster;


public interface RepositoryEntityIncidentMaster extends JpaRepository<EntityIncidentMaster, Long>{

	@Query(value="select count(*) from "+CommonConstants.db_Name+".incidentmaster where month(incdate_im)=?1 "
			+ " and year(incdate_im) =?2 and deleteflag_im='N' and businessunit_bum_im IN(?3) and status_sm_im IN (?4) ",nativeQuery=true)
	public abstract int listOfTotalOpenTicketForChairman(int month,int year,List<Integer> bum,List<String> statuses);
	
	
	@Query(value="select count(distinct(typeid_im)) from "+CommonConstants.db_Name+".incidentmaster,erprmwise.pausemst_pm"
			+ " where typeid_im=incidentid_pm and deleteflag_im='N' and status_sm_im=3 and month(pausestartdate_pm)=?1 and year(pausestartdate_pm)=?2"
			+ " and businessunit_bum_im IN(?3) and status_sm_im IN (?4) ",nativeQuery=true)
	public abstract int listOfTotalPausedTicketForChairman(int month,int year,List<Integer> bum,List<String> statuses);
	
	@Query(value="select count(*) from "+CommonConstants.db_Name+".incidentmaster, "
			+ "erprmwise.incidentlogtimemst where typeid_im=incidentid "
			+ "and deleteflag_im='N' "
			+ "and month(technicianclosedate)=?1 and year(technicianclosedate)=?2 "
			+ "and technicianclosedate is not null and businessunit_bum_im IN(?3) "
			+ "and status_sm_im IN (?4)", nativeQuery=true)
	public abstract int listOfTotalTechnicianClosedTicketForChairman(int month, int year, List<Integer> bum, List<String> statuses);

	@Query(value="select count(*) from "+CommonConstants.db_Name+".incidentmaster where deleteflag_im='N' "
			+ " and month(incdate_im)=?1 and year(incdate_im)=?2 and businessunit_bum_im IN(?3) and status_sm_im IN (?4) ",nativeQuery=true)
	public abstract int listOfTotalCancelledTicketForChairman(int month,int year,List<Integer> bum,List<String> statuses);

	
	/**JPQL**/
	@Query("SELECT count(im.typeidIm) FROM intelli.uno.entity.EntityIncidentMaster im "
			+ "WHERE im.deleteFlagIm='N' "
			+ "AND month(im.incdateIm) =?1 "
			+ "AND year(im.incdateIm) =?2 "
			+ "AND im.businessunitbumIm IN (?3)")
	public abstract int n_intTotalCalls(int month,int year,List<Integer> bum);
	
	@Query(value="SELECT COUNT(typeid_im) FROM "+CommonConstants.db_Name+".incidentmaster"
		    + " WHERE deleteflag_im = 'N' AND month(incdate_im) = '1' AND year(incdate_im) ='2023' "
		    + " AND businessunit_bum_im IN (:bum)", nativeQuery = true)
	public abstract int n_intTotalCallsNative(@Param("bum") List<Integer> bum);

	@Query(value="select count(*) from "+CommonConstants.db_Name+".incidentmaster where deleteflag_im='N' and status_sm_im=6 ",nativeQuery=true)
	public abstract int listOfTotalReopenedTicketForChairman();

      //------x-------//
	  //For Testing Purpose//
		@Query(value="SELECT COUNT(*) FROM " + CommonConstants.db_Name + ".incidentmaster " +
	            "WHERE MONTH(incdate_im) = 1 AND YEAR(incdate_im) = 2022 ", nativeQuery=true)
		public abstract int listOfTotalOpenTicketForChairmanTesting();
		
		
		

		
		/**19 JUNE 2023 DEVELOPMENT BY PIYUSHRAJ FOR COUNTS SHOWING ON EXECUTIVE HOME SCREEN MAIN DASHBOARD**/
		
		@Query(value="SELECT COUNT(*) FROM " + CommonConstants.db_Name+ ".incidentmaster " + 
				   "WHERE deleteFlag_im ='N' AND status_sm_im ='1' AND engineer_engm_im IS NOT NULL", nativeQuery=true)
		public abstract int retunCountOfExecuitveDashboardHearderAll_OPEN_Count();
		
		@Query(value="SELECT COUNT(*) FROM " + CommonConstants.db_Name+ ".incidentmaster "+ 
			      "WHERE deleteFlag_im ='N' AND status_sm_im ='3' AND engineer_engm_im IS NOT NULL" , nativeQuery=true)
		public abstract int retunCountOfExecutiveDashboardHeaderAll_PAUSED_Count();

		@Query(value="SELECT COUNT(*) FROM " + CommonConstants.db_Name+ ".incidentmaster "+
				"WHERE deleteFlag_im = 'N' AND status_sm_im = '5' AND engineer_engm_im IS NOT NULL" , nativeQuery=true)
		public abstract int returnCountOfExecutiveDashboardHeaderAll_TECHCLOSED_Count();

		@Query(value="SELECT COUNT(*) FROM " + CommonConstants.db_Name+ ".incidentmaster "+ 
				"WHERE deleteFlag_im = 'N' AND status_sm_im = '4' AND engineer_engm_im IS NOT NULL" , nativeQuery=true)
		public abstract int returnCountOfExecutiveDashboardHeaderAll_CLOSED_Count();
		
		@Query(value="SELECT COUNT(*) FROM " + CommonConstants.db_Name+ ".incidentmaster "+
				"WHERE deleteFlag_im = 'N' AND status_sm_im = '6' AND engineer_engm_im IS NOT NULL" , nativeQuery=true)
		public abstract int returnCountOfExecutiveDashboardHeaderAll_REOPEN_Count();
		
		@Query(value="SELECT COUNT(*) FROM " + CommonConstants.db_Name+ ".incidentmaster "+
				"WHERE deleteFlag_im = 'N' AND status_sm_im = '7' AND engineer_engm_im IS NOT NULL" , nativeQuery=true)
		public abstract int returnCountOfExecutveDashboardHeaderAll_CANCELLED_COUNT();
		
		@Query(value="SELECT COUNT(*) FROM " + CommonConstants.db_Name+ ".incidentmaster "+
				"WHERE deleteFlag_im = 'N'  AND engineer_engm_im IS NULL" , nativeQuery=true)
		public abstract int returnCountOfExecutveDashboardHeaderAll_UNASSIGNED_COUNT();
		
		@Query(value="SELECT COUNT(*) FROM " + CommonConstants.db_Name+ ".incidentmaster "+
				"WHERE deleteFlag_im = 'N' AND resolutionflag_im = 'V'  AND engineer_engm_im IS NULL" , nativeQuery=true)
		public abstract int returnCountOfExecutveDashboardHeaderAll_SLA_VIOLATED_COUNT();


		
		/**RETURN  TOTAL TICKET OF TICKET FOR PARTICULAR ENG**/ 
		@Query(value="SELECT COUNT(*) FROM " + CommonConstants.db_Name+ ".incidentmaster "+ 
				"WHERE deleteFlag_im =?2 AND engineer_engm_im=?1" , nativeQuery=true)
		public abstract int returnEngTotalTicketCount(Integer engID, String deleteflag);

		@Query(value="SELECT COUNT(*) FROM " + CommonConstants.db_Name+ ".incidentmaster "+ 
				"WHERE deleteFlag_im =?2 And status_sm_im=4 AND engineer_engm_im=?1" , nativeQuery=true)
		public abstract int returnEngTotalClosedTicketCount(Integer engID, String deleteflag);
		
		@Query(value="SELECT COUNT(*) FROM " + CommonConstants.db_Name+ ".incidentmaster "+ 
				"WHERE deleteFlag_im =?2 AND engineer_engm_im=?1 and year(incdate_im)=?3"  , nativeQuery=true)
		public abstract int returnEngTotalTicketYearCount(Integer engID, String deleteflag,int year);
		
		@Query(value="SELECT COUNT(*) FROM " + CommonConstants.db_Name+ ".incidentmaster "+ 
				"WHERE deleteFlag_im =?2 And status_sm_im=4 AND engineer_engm_im=?1 and year(incdate_im)=?3" , nativeQuery=true)
		public abstract int returnEngTotalClosedTicketYearCount(Integer engID, String deleteflag,int year);
		
		@Query(value="SELECT COUNT(*) FROM " + CommonConstants.db_Name+ ".incidentmaster "+ 
				"WHERE deleteFlag_im =?2  and resolutionflag_im = 'V' AND engineer_engm_im=?1"
				+ " and year(incdate_im)=?3  " , nativeQuery=true)
		public abstract int returnEngTotalSlaViolatedTicketYearCount(Integer engID, String deleteflag,int year);
		
		@Query(value="SELECT COUNT(*) FROM " + CommonConstants.db_Name+ ".incidentmaster "+ 
				"WHERE deleteFlag_im =?2  and resolutionflag_im = 'V' AND engineer_engm_im=?1"
				+ "   " , nativeQuery=true)
		public abstract int returnEngTotalSlaViolatedTicketCount(Integer engID, String deleteflag);
		
		/**RETURN  TOTAL TICKET FOR NEW SALARY ENG 22 JULE 2023**/ 
		@Query(value="SELECT COUNT(*) FROM " + CommonConstants.db_Name+ ".incidentmaster "+ 
				"WHERE deleteFlag_im =?2 AND engineer_engm_im=?1  AND incdate_im >=?3 " , nativeQuery=true)
		public abstract int n_intTotalTicketAfterNewSalary(Integer engID, String deleteflag,String incdateIm);
		
		@Query(value="SELECT COUNT(*) FROM " + CommonConstants.db_Name+ ".incidentmaster "+ 
				"WHERE deleteFlag_im =?2 And status_sm_im=4   AND engineer_engm_im=?1  AND incdate_im >=?3 " , nativeQuery=true)
		public abstract int n_intTotalClosedTicketAfterNewSalary(Integer engID, String deleteflag,String incdateIm);
		
		
		@Query(value="SELECT COUNT(*) FROM " + CommonConstants.db_Name+ ".incidentmaster "+ 
				"WHERE deleteFlag_im =?2 And status_sm_im=4   AND engineer_engm_im=?1  AND incdate_im <?3 " , nativeQuery=true)
		public abstract int n_intTotalClosedTicketBeforeSalaryChange(Integer engID, String deleteflag,String incdateIm);
		
		@Query(value="SELECT COUNT(*) FROM " + CommonConstants.db_Name+ ".incidentmaster "+ 
				"WHERE deleteFlag_im =?2  AND engineer_engm_im=?1  AND incdate_im <?3 " , nativeQuery=true)
		public abstract int n_intTotalTicketBeforeSalaryChange(Integer engID, String deleteflag,String incdateIm);
		
		
		
		/**DEVELOPMENT FOR SEARCH NOT WORKING 15 AUGUST**/
	    //@Query(value = "SELECT some_column FROM some_table WHERE some_condition", nativeQuery = true)
	    //String getStringValue();
		@Query(value = "SELECT ticketid_im FROM " + CommonConstants.db_Name+ ".incidentmaster "
				+ " WHERE deleteflag_im='N' and ticketid_im LIKE ?1 or typeid_im LIKE ?1 ", nativeQuery = true)
		public abstract List<String> findBySearchTerm(String searchString);
		
}
