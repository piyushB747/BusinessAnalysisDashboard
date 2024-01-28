package intelli.uno.repositorycustom;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface CustomRepostitory {

	/**RETURNS COUNTS  FROM NATIVE QUERY**/
	public abstract int getCountsParameterizedNativeQuery(String paramQuery);
	
	/**ADDED FOR PASSWORD UPDATE**/
    public abstract void insertEngineerRoleNativeQuery(String userId,String roleId);
    
    /**Main For Engineer Attendance Dash board You can use [Select * from]  **/
    public abstract List<Object[]>  returnListOfEngineerAttendance(String m_strQuery);
    
    
    /**USED FOR PAGEINATION CONCEPT HERE ***/
    public abstract List<Object[]> returnListOfPageinationConcept2(String m_strPageSize,String m_strPageNo,String sqlQuery);
    
    
    /**RETURN LIST OF TYPEID**/
    public abstract List<Integer> returnListOfTypeIdListForTree(String m_strQuery);
    
    /**Main For List Of Select * from  Dash board You can use [Select * from]  **/
    public abstract List<Object[]>  returnListOfSelectStarFromConcept(String m_strQuery);
	
    
    public abstract String returnAvgTicketCostForEngineerString(String m_strQuery);
}
