package intelli.uno.repositorycustom;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class CustomImplRepostitory implements CustomRepostitory{

	@PersistenceContext
	EntityManager entityManager;
	
	/**NATIVE QUERY TO RETURN COUNTS**/
	@Override
	public int getCountsParameterizedNativeQuery(String paramQuery) {
		Query query = entityManager.createNativeQuery(paramQuery);
	    return ((Number) query.getSingleResult()).intValue();
	}

	/**ADDED FOR PASSWORD UPDATE ON 7AUG BY PIYUSHRAJ**/
	@Override
	@Transactional
	public void insertEngineerRoleNativeQuery(String userId, String roleId) {
		 String nativeQuery = "INSERT INTO engineer_roles_er (user_id_er, role_id_er) VALUES (?, ?)";
	        entityManager.createNativeQuery(nativeQuery)
	            .setParameter(1, userId) // user_id_er
	            .setParameter(2, roleId) // role_id_er
	            .executeUpdate();
	}
	
	/**Main For Engineer Attendance Dash board**/
	@Override
	public List<Object[]>  returnListOfEngineerAttendance(String m_strQuery) {
        
        @SuppressWarnings("unchecked")
        List<Object[]> resultList = entityManager.createNativeQuery(m_strQuery)
                .getResultList();
        return resultList;
    }
	
	/**Main For Engineer Attendance Dash board**/
	@Override
	public List<Integer> returnListOfTypeIdListForTree(String m_strQuery) {
        
		Query query = entityManager.createNativeQuery(m_strQuery);
		
	    @SuppressWarnings("unchecked")
	    List<Integer> resultArray =query.getResultList();
	
	    return resultArray;
    }

	
	/**PAGEINATION IMPLEMENTATION 2 AUGUST 2023**/
	@Override
	public List<Object[]> returnListOfPageinationConcept2(String m_strPageSize,String m_strPageNo,String sqlQuery) {
	      
          
	      int pageNumber=0;
	      try {
	          pageNumber=Integer.parseInt(m_strPageNo);
	      }catch(Exception ex) { ex.printStackTrace(); pageNumber=1;  }
	      
	      int pageSize=20;
	      try {
	    	  pageSize=Integer.parseInt(m_strPageSize);
	      }catch(Exception ex) { ex.printStackTrace(); pageSize=20; }
	     
	      int firstResult =0;
	      try {
	    	   firstResult = (pageNumber - 1) * pageSize;
	      }catch(Exception ex) { ex.printStackTrace(); firstResult=0; }
	       
	        // Create the native SQL query using the EntityManager
	        @SuppressWarnings("unchecked")
	        List<Object[]> resultList = entityManager.createNativeQuery(sqlQuery)
            .setFirstResult(firstResult)
            .setMaxResults(pageSize)
            .getResultList();

	        return resultList;		
	}

	/**YOU CAN USE THIS FOR SELECT * FROM**/
	@Override
	public List<Object[]> returnListOfSelectStarFromConcept(String m_strQuery) {
		@SuppressWarnings("unchecked")
        List<Object[]> resultList = entityManager.createNativeQuery(m_strQuery)
                .getResultList();
        return resultList;
	}

	@Override
	public String returnAvgTicketCostForEngineerString(String m_strQuery) {
	    Query query = entityManager.createNativeQuery(m_strQuery);
	    
	    @SuppressWarnings("unchecked")
		List<String> resultList = query.getResultList();
	    return resultList.isEmpty() ? null : resultList.get(0);
	}
	
}
