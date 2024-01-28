package intelli.uno.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import intelli.uno.bean.BeansGlobalUserVariable;
import intelli.uno.entity.EntityEngineerMaster;
import intelli.uno.commons.CommonConstants;
import intelli.uno.commons.CommonUtils;
import intelli.uno.dto.DtoUserVariable;

@Service
public class ServicesUserCommonSessionContainer {
 
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private BeansGlobalUserVariable globalVariable;
	

	public void getUserDetailsForService(ApplicationContext applicationContext,EntityEngineerMaster userDetails) {
	    this.globalVariable = applicationContext.getBean(BeansGlobalUserVariable.class);
	   
	    List<DtoUserVariable> b1=this.getUserDetailsForService(userDetails);
	    b1.forEach((p)->{
	    	this.globalVariable.setM_strFullName(p.getM_strUserName());
	    	this.globalVariable.setM_strMultiPoplocation(p.getM_strMultiPoplocation());
	    	this.globalVariable.setM_strLoginId(p.getM_strUserId());
	    	this.globalVariable.setM_strMultiProductCategory(p.getM_strMultiProductCategory());
	    	this.globalVariable.setM_strStateSmIm(p.getM_strMultiStates());
	    	this.globalVariable.setM_strRoleId(p.getM_strRoleId());
	    	this.globalVariable.setM_strBusinessUnit(p.getM_strBusinessUnit());
	    	this.globalVariable.setL_intBusinessUnit(p.getL_intBusinessUnit());
	    });
	    
	  }
	
	public List<DtoUserVariable> getUserDetailsForService(EntityEngineerMaster userDetails) {
	    String m_strSelect="typeid_em, "
	    		+ "  loginid_em, "
	    		+ "  concat(fname_em, \" \", lname_em) as Name, "
	    		+ "  role_rm_em,"
	    		+ "  poplocation_em, "
	    		+ "  multipoplocation_pm_em, "
	    		+ "  region_em,reportingmanager_em, "
	    		+ "  multilocation_mm_em, "
	    		+ "  city_cm_em, "
	    		+ "  state_sm_em, multiproductcategory_pcm_em,ecode_em, "
	    		+ "  typeid_bum_em";
	    
	    String sqlSelect = "SELECT "+m_strSelect+" FROM "+CommonConstants.db_Name+".engineermst_em where loginid_em='"+userDetails.getLoginidEm()+"' "
	    		+ "and deleteflag_em='N' ";
	    //System.out.println("In Common Session Container:= "+sqlSelect+ "");
	    List<DtoUserVariable> m_strUserDetails = jdbcTemplate.query(sqlSelect, new RowMapper<DtoUserVariable>() {
	        public DtoUserVariable mapRow(ResultSet result, int rowNum) throws SQLException {
	            
	        	boolean l_blnUserExists = false;
	        	
	        	DtoUserVariable user = new DtoUserVariable();
	            
	        	String bum=CommonUtils.nullToBlank(result.getString("typeid_bum_em"), l_blnUserExists);
	        	String[] arr_Bum2=bum.split(",");
	        	List<Integer> integerList = Arrays.stream(arr_Bum2)
                        .map(Integer::valueOf)
                        .collect(Collectors.toList());
	        	
                user.setM_strBusinessUnit(bum);       
                user.setL_intBusinessUnit(integerList);
	           
		            
	            user.setM_strMultiStates(CommonUtils.nullToBlank(result.getString("state_sm_em")+","+result.getString("multilocation_mm_em"), false));
	          
	            if (user.getM_strMultiStates() != null && user.getM_strMultiStates().endsWith(",")) {
                     String m_strMultiStatesTemp = user.getM_strMultiStates().substring(0, user.getM_strMultiStates().length() - 1);
                     user.setM_strMultiStates(CommonUtils.nullToBlank(m_strMultiStatesTemp, l_blnUserExists));
                }
	            
	            String m_strMultiPoplocationTemp=String.valueOf(result.getString("poplocation_em")+","+result.getString("multipoplocation_pm_em"));
	            user.setM_strMultiPoplocation(m_strMultiPoplocationTemp);
	            if (m_strMultiPoplocationTemp != null && m_strMultiPoplocationTemp.endsWith(",")) {
	            	m_strMultiPoplocationTemp = m_strMultiPoplocationTemp.substring(0, m_strMultiPoplocationTemp.length() - 1);
	            	user.setM_strMultiPoplocation(m_strMultiPoplocationTemp);
                }
	            
	            user.setM_strRoleId(String.valueOf(result.getString("role_rm_em")));
	          
	            user.setM_strRegion(String.valueOf(result.getString("region_em")));
	            
	            user.setM_strMultiProductCategory(String.valueOf(result.getString("multiproductcategory_pcm_em")));
	            
	            user.setM_strUserName(CommonUtils.nullToBlank(result.getString("Name"), l_blnUserExists));
	            
	            user.setM_strUserId(CommonUtils.nullToBlank(result.getString("typeid_em"), l_blnUserExists));
	            
	            return user;
	        }
	    });
	    
	    return m_strUserDetails;
	}


	
}
