package intelli.uno.apiweb.executivescreen;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import intelli.uno.commons.CommonConstants;
import intelli.uno.commons.CommonUtils;
import intelli.uno.dto.executivescreen.DtoJsonEngineer;
import intelli.uno.dto.executivescreen.JsonResponse;
import intelli.uno.repository.RepositoryEntityEngineerMaster;
import intelli.uno.repository.RepositoryEntityRegionMaster;
import intelli.uno.repository.RepositoryEntityStateMaster;
import intelli.uno.repositorycustom.CustomRepostitory;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "APIControlExecutiveDataTable", description = "This API Generate Date Table or Tree View")
@RestController
@RequestMapping(value="/getdataforexecutivetree")
@CrossOrigin(origins="*")
public class APIControlExecutiveDataTable {

   static int m_intOpenCount=0,m_intPausedCount=0,m_intTechClosedCount=0,m_intClosed=0,m_intSlaViolated=0;
   static String m_strRegion="",m_strState="";
   
	@Autowired
	private CustomRepostitory customRepoEntityIncidentMaster;

	@Autowired
	private RepositoryEntityEngineerMaster repositoryEntityEngineerMaster;
	
	@Autowired
	private RepositoryEntityRegionMaster repositoryEntityRegionMaster;
	
	@Autowired
	private RepositoryEntityStateMaster repositoryEntityStateMaster;
	
	@GetMapping
	public ResponseEntity<?> returnListViewForTree(HttpServletRequest request,
			@RequestParam(value="m_strTreeType",required=false)String m_strTreeType,
			@RequestParam(value="action",required=false)String m_strAction){
		
		JsonResponse jsonResponse=new JsonResponse();
		List<DtoJsonEngineer> l_objListOfDtoEngineer = new ArrayList<>();
	  
			if(m_strTreeType!=null && m_strTreeType.equals("")==false && m_strTreeType.equals("rmwise")) {
				
				l_objListOfDtoEngineer=returnListOfDataForRmWise(request,m_strAction);
				
			}else if(m_strTreeType!=null && m_strTreeType.equals("")==false && m_strTreeType.equals("regionwise")) {
				
				l_objListOfDtoEngineer=returnListOfDataForRegionWise(request,m_strAction);
				
			}else if(m_strTreeType!=null && m_strTreeType.equals("")==false && m_strTreeType.equals("statewise")) {
			
				l_objListOfDtoEngineer=returnListOfDataForStateWise(request,m_strAction);
			}
			
			
			jsonResponse.setProgress_Data(l_objListOfDtoEngineer);
			return ResponseEntity.status(HttpStatus.OK).header("custom-header", "treeData")
					.contentType(MediaType.APPLICATION_JSON).body(jsonResponse);
	} 
	
	
	@SuppressWarnings("unused")
	public List<DtoJsonEngineer> returnListOfDataForStateWise(HttpServletRequest request,String m_strAction){
		List<DtoJsonEngineer> l1=new ArrayList<>();
		if(m_strAction!=null &&  !m_strAction.equals("")) {
			
			if(!m_strAction.equals("true")) {
				
				m_strState="ONLY-STATE";
				String l_strQuery="select typeid_sm, typevalue_sm from "+CommonConstants.db_Name+".statemst_sm where deleteflag_sm='N' ";
			   	List<Object[]> l_objList=customRepoEntityIncidentMaster.returnListOfEngineerAttendance(l_strQuery);
	        	   
			   	   for(Object[] m_strRmData : l_objList) {
	        		    DtoJsonEngineer m_strEngineer = new DtoJsonEngineer();     

            		    m_strEngineer.setId(String.valueOf(m_strRmData[0]));
            		    m_strEngineer.setName(String.valueOf(m_strRmData[1]));
            		     
            		    if(String.valueOf(m_strRmData[1])!=null && String.valueOf(m_strRmData[1]).equals("")==false) {
            		       getOpenCounts(request,String.valueOf(m_strRmData[1]));
	               		   getPausedCounts(request, String.valueOf(m_strRmData[1]));
	               		   getTechClosedCounts(request, String.valueOf(m_strRmData[1]));
	               		   getClosedCounts(request, String.valueOf(m_strRmData[1]));
	               		   getSlaCounts(request, String.valueOf(m_strRmData[1]));
            		    }
            		    
	               		   m_strEngineer.setOpen(""+m_intOpenCount);
							m_strEngineer.setTechnicianClosed(""+m_intTechClosedCount);
							m_strEngineer.setSlaViolated(""+m_intSlaViolated);
							m_strEngineer.setClosed(""+m_intClosed);
							m_strEngineer.setTreetype("rmwise");
							m_strEngineer.setSub("true");
							m_strEngineer.setPaused(""+m_intPausedCount);
							l1.add(m_strEngineer);
							m_strEngineer = null;
	        	   }
			}else {
				
				//If Action is Not true;
				String m_strFinalList = "";
				String l_strEID="";
                String l_strUserID = CommonUtils.nullToBlank(request.getParameter("UserId"), false);
                String eng = CommonUtils.nullToBlank(request.getParameter("UserId"), false);
                m_strFinalList = "" + eng;
				/* System.out.println("False Is Not there In statewise "+l_strUserID); */
			 	m_strState=repositoryEntityStateMaster.returnValueFromId(Long.valueOf(l_strUserID.replace(" ", "")), "N");
			 	String l_strQuery="Select typeid_lm, typevalue_lm from "+CommonConstants.db_Name+".locationmst_lm where deleteflag_lm='N' and state_sm_lm="+l_strUserID+" ";
				List<Object[]> l_objList=customRepoEntityIncidentMaster.returnListOfEngineerAttendance(l_strQuery);
	        	   for(Object[] m_strRmData : l_objList) {
	        	   
	        		    DtoJsonEngineer m_strEngineer = new DtoJsonEngineer();          
	        		    
	          		   
	         		    m_strEngineer.setId(String.valueOf(m_strRmData[0]));
	         		    m_strEngineer.setName(String.valueOf(m_strRmData[1]));
	         		    
	         		       getOpenCounts(request,String.valueOf(m_strRmData[1]));
	               		   getPausedCounts(request, String.valueOf(m_strRmData[1]));
	               		   getTechClosedCounts(request, String.valueOf(m_strRmData[1]));
	               		   getClosedCounts(request, String.valueOf(m_strRmData[1]));
	               		   getSlaCounts(request, String.valueOf(m_strRmData[1]));
	               		 
	               		   m_strEngineer.setOpen(""+m_intOpenCount);
							m_strEngineer.setTechnicianClosed(""+m_intTechClosedCount);
							m_strEngineer.setSlaViolated(""+m_intSlaViolated);
							m_strEngineer.setClosed(""+m_intClosed);
							m_strEngineer.setTreetype("rmwise");
							m_strEngineer.setSub("true");
							m_strEngineer.setPaused(""+m_intPausedCount);
							l1.add(m_strEngineer);
							m_strEngineer = null;
	        	   }
			}
		}
		return l1;
	}
	
	
	@SuppressWarnings("unused")
	public List<DtoJsonEngineer> returnListOfDataForRegionWise(HttpServletRequest request,String m_strAction){
		List<DtoJsonEngineer> l1=new ArrayList<>();
		if(m_strAction!=null &&  !m_strAction.equals("")) {
			
			if(!m_strAction.equals("true")) {
				
				m_strRegion="ONLY-REGION";
				
				String l_strQuery="Select typeid_rm,typevalue_rm from "+CommonConstants.db_Name+".regionmst_rm where deleteflag_rm='N' ";  
	        	List<Object[]> l_objList=customRepoEntityIncidentMaster.returnListOfEngineerAttendance(l_strQuery);
	        	   for(Object[] m_strRmData : l_objList) {
	        		    DtoJsonEngineer m_strEngineer = new DtoJsonEngineer();             		    
            		   
            		    m_strEngineer.setId(String.valueOf(m_strRmData[0]));
            		    m_strEngineer.setName(String.valueOf(m_strRmData[1]));
            		    
            		   
	            
		                   getOpenCounts(request,String.valueOf(m_strRmData[1]));
	               		   getPausedCounts(request, String.valueOf(m_strRmData[1]));
	               		   getTechClosedCounts(request, String.valueOf(m_strRmData[1]));
	               		   getClosedCounts(request, String.valueOf(m_strRmData[1]));
	               		   getSlaCounts(request, String.valueOf(m_strRmData[1]));
	               		 
	               		   m_strEngineer.setOpen(""+m_intOpenCount);
							m_strEngineer.setTechnicianClosed(""+m_intTechClosedCount);
							m_strEngineer.setSlaViolated(""+m_intSlaViolated);
							m_strEngineer.setClosed(""+m_intClosed);
							m_strEngineer.setTreetype("rmwise");
							m_strEngineer.setSub("true");
							m_strEngineer.setPaused(""+m_intPausedCount);
							l1.add(m_strEngineer);
							m_strEngineer = null;
	        	   }
	        	
			}else{
				//If Action is Not true;
				String m_strFinalList = "";
				String l_strEID="";
                String l_strUserID = CommonUtils.nullToBlank(request.getParameter("UserId"), false);
                String eng = CommonUtils.nullToBlank(request.getParameter("UserId"), false);
                m_strFinalList = "" + eng;
			 	//System.out.println("False Is Not there In regionWise "+l_strUserID);
			 	
			    m_strRegion=repositoryEntityRegionMaster.returnValueFromId("N",Long.valueOf(l_strUserID.replace(" ", "")));
			 //	System.out.println(m_strRegion+ "Please check m_strRegion In TRUE CONDITION");
			 	String l_strQuery="Select typeid_sm,typevalue_sm from "+CommonConstants.db_Name+".statemst_sm where deleteflag_sm='N' and region_rm_sm="+l_strUserID+" ";
			  	List<Object[]> l_objList=customRepoEntityIncidentMaster.returnListOfEngineerAttendance(l_strQuery);
	        	   for(Object[] m_strRmData : l_objList) {
	        		    DtoJsonEngineer m_strEngineer = new DtoJsonEngineer();             		    
         		   
         		    m_strEngineer.setId(String.valueOf(m_strRmData[0]));
         		    m_strEngineer.setName(String.valueOf(m_strRmData[1]));
         		    
         		    /**Development For Open Counts**/
	             	
		                   getOpenCounts(request,String.valueOf(m_strRmData[1]));
	               		   getPausedCounts(request, String.valueOf(m_strRmData[1]));
	               		   getTechClosedCounts(request, String.valueOf(m_strRmData[1]));
	               		   getClosedCounts(request, String.valueOf(m_strRmData[1]));
	               		   getSlaCounts(request, String.valueOf(m_strRmData[1]));
	               		 
	               		   m_strEngineer.setOpen(""+m_intOpenCount);
							m_strEngineer.setTechnicianClosed(""+m_intTechClosedCount);
							m_strEngineer.setSlaViolated(""+m_intSlaViolated);
							m_strEngineer.setClosed(""+m_intClosed);
							m_strEngineer.setTreetype("rmwise");
							m_strEngineer.setSub("true");
							m_strEngineer.setPaused(""+m_intPausedCount);
							l1.add(m_strEngineer);
							m_strEngineer = null;
	        	   }
			 	
			}
		}
		return l1;
	}
	
	
	public List<DtoJsonEngineer> returnListOfDataForRmWise(HttpServletRequest request,String m_strAction){
		List<DtoJsonEngineer> l1=new ArrayList<>();
		
		if(m_strAction!=null &&  !m_strAction.equals("")) {
		
			//System.out.println("m_strAction:= "+m_strAction);
		
			/**IF ACTION IS FALSE**/
			if(!m_strAction.equals("true")) {
				
				String l_strQuery="Select fname_em,typeid_em from "+CommonConstants.db_Name+".engineermst_em where deleteflag_em='N' and role_rm_em='15' ";
	   
	        	  
	        	  List<Object[]> l_objList=customRepoEntityIncidentMaster.returnListOfEngineerAttendance(l_strQuery);
             	   for(Object[] m_strRmData : l_objList) {
             		   
             		  DtoJsonEngineer m_strEngineer = new DtoJsonEngineer();             		    
             		    m_strEngineer.setName(String.valueOf(m_strRmData[0]));
             		    m_strEngineer.setId(String.valueOf(m_strRmData[1]));
             		    
             		   
             		    /**List Of Engineers Added In the List **/
             			List<String> m_strListOfEngineers=new ArrayList<String>();
             		    m_strListOfEngineers.add(String.valueOf(m_strRmData[1]));
             		   
             		    /**Development For Open Counts**/
	             		   String m_strFinalList = "";
		                   String eng =String.valueOf(m_strRmData[1]) ;
		                   m_strFinalList = "" + eng;
		                   int n_intLoopBreak=0;
		                   //System.out.println("engId Before Loop= "+eng+" || m_strFinalList= "+m_strFinalList);
		                   
		                	   
		                	   while(!eng.equals("")) {
				                	  
				                	   String arra = "";
				                	   String l_str = "select typeid_em from " + CommonConstants.db_Name + ".engineermst_em where deleteflag_em='N' AND resignedflag_em='N' AND  reportingmanager_em in(" + eng + ")";
				                	   
				                	   List<Integer> m_strEngineersId=customRepoEntityIncidentMaster.returnListOfTypeIdListForTree(l_str);
				                	   try {
				                		   arra = org.apache.commons.lang3.StringUtils.join(m_strEngineersId, ", ");  /**CONVERTING INTO LIST<INTEGER INTO STRING*/
				                	   }catch(Exception ex) { ex.printStackTrace(); }
				                	  
				                	   
				                	   if(!arra.equals("")) {
				                		   m_strFinalList = m_strFinalList + "," + arra;
				                	        eng = arra;
					                	   if (arra.contains(",")) { 
					                         
					                            
					                        }
				                	   }else {
				                		   eng="";
				                		   break;
				                	   }
				                	   
				                	   if(n_intLoopBreak==5) {  /**LOOOP IS BREAKING ON 8**/
				                		   break;
				                	   }
				                	   n_intLoopBreak++;
				                	   
		                        }//while loops ends here    
		                	   
		                	      
		                	   getOpenCounts(request,m_strFinalList);
		                	   getPausedCounts(request, m_strFinalList);
		                	   getClosedCounts(request, m_strFinalList);
		                	   getTechClosedCounts(request, m_strFinalList);
		                	   getSlaCounts(request, m_strFinalList);
		                	 
		                   
		               
		                m_strEngineer.setOpen(""+m_intOpenCount);
						m_strEngineer.setTechnicianClosed(""+m_intTechClosedCount);
						m_strEngineer.setSlaViolated(""+m_intSlaViolated);
						m_strEngineer.setClosed(""+m_intClosed);
						m_strEngineer.setTreetype("rmwise");
						m_strEngineer.setSub("true");
						m_strEngineer.setPaused(""+m_intPausedCount);
						l1.add(m_strEngineer);
						m_strEngineer = null;
 			            
	        	  }
			
			
			
			}else {   /**IF ACTION IS TRUE**/
			    
				String m_strFinalList = "";
				String l_strEID="";
                String l_strUserID = CommonUtils.nullToBlank(request.getParameter("UserId"), false);
                String eng = CommonUtils.nullToBlank(request.getParameter("UserId"), false);
                m_strFinalList = "" + eng;
				//System.out.println(request.getParameter("UserId")+ " USER_ID"+l_strUserID+ " "+eng+" "+m_strFinalList);
			      if (l_strUserID.equals(l_strEID)) {
			      
			      }else {
			    	  
			    	   String arra = "";
                       String l_str = "select typeid_em from " + CommonConstants.db_Name + ".engineermst_em where deleteflag_em='N' "
                      		+ " AND resignedflag_em='N' AND  reportingmanager_em in(" + eng + ") ";
                        List<Integer> m_strEngineersId=customRepoEntityIncidentMaster.returnListOfTypeIdListForTree(l_str);
                        try {
	                		   arra = org.apache.commons.lang3.StringUtils.join(m_strEngineersId, ", ");  /**CONVERTING INTO LIST<INTEGER INTO STRING*/
	                	   }catch(Exception ex) { ex.printStackTrace(); }
                         
					
						m_strFinalList = m_strFinalList + "," + arra;

						System.out.println("Finalist oF Engineer Under RM"+m_strFinalList);

						 String FinalListArr[] = m_strFinalList.split(",");
						 
						 for (int i = 0; i < FinalListArr.length; i++) {
					      System.out.println("id in for loop= "+FinalListArr[i]);
		    		            DtoJsonEngineer m_strEngineer = new DtoJsonEngineer();
		    		         
		               		    String m_strName=repositoryEntityEngineerMaster.returnValueForIdIncludedResigned(Long.valueOf(FinalListArr[i].replace(" ", "")), "N");
		               		    //System.out.println(m_strName+ " Name Please Check Here IN APICONTROLDATATABLE");
		    		            
		               		    m_strEngineer.setName(String.valueOf(m_strName));
		               		    m_strEngineer.setId(String.valueOf(FinalListArr[i])+" ");
		               		    
		               		    getOpenCounts(request,String.valueOf(FinalListArr[i]));
		               		    getPausedCounts(request, String.valueOf(FinalListArr[i]));
		               		    getTechClosedCounts(request, String.valueOf(FinalListArr[i]));
		               		    getClosedCounts(request, String.valueOf(FinalListArr[i]));
		               		    getSlaCounts(request, String.valueOf(FinalListArr[i]));
		               		    
		               		    m_strEngineer.setOpen(""+m_intOpenCount);
		               		    m_strEngineer.setPaused(""+m_intPausedCount);
		               		    m_strEngineer.setTechnicianClosed(""+m_intTechClosedCount);
		               		    m_strEngineer.setSlaViolated(""+m_intSlaViolated);
		               		    m_strEngineer.setClosed(""+m_intClosed);
		               		    m_strEngineer.setTreetype("rmwise");
		               		    m_strEngineer.setParent("0");
		               		 if (FinalListArr[i].equals(l_strUserID)) {
		               			m_strEngineer.setSub("false");
		               		 }else {
		               			m_strEngineer.setSub("true");
		               		 }
		   			            l1.add(m_strEngineer);
		   			            m_strEngineer=null;
		    			        
		                }
                      
						 
						 
			      }
			      
			}
			
		}
		return l1;
	}
	
	
	public void getOpenCounts(HttpServletRequest request,String m_strFinalList) {
		String m_strTreeType=request.getParameter("m_strTreeType");
		String ProductCategoryID = "";
		if(m_strTreeType!=null && m_strTreeType.equals("")==false && m_strTreeType.equals("rmwise")) {

			if (!m_strFinalList.equals("")) {

				/* OPEN COUNTS START */
				String l_strQueryOpen = "select count(*) from " + CommonConstants.db_Name
						+ ".incidentmaster where deleteflag_im='N' AND status_sm_im = 1 " + "AND engineer_engm_im in("
						+ m_strFinalList + ") ";

				if (ProductCategoryID != null && ProductCategoryID.equals("") == false) {
					l_strQueryOpen = l_strQueryOpen + " OR productcat_pcm_im in (" + ProductCategoryID + ") ";
				}
				l_strQueryOpen = l_strQueryOpen + "  ";

				//System.out.println("l_strQueryOpen " + l_strQueryOpen);
				try {
					m_intOpenCount = customRepoEntityIncidentMaster.getCountsParameterizedNativeQuery(l_strQueryOpen);
				} catch (Exception ex) {ex.printStackTrace(); m_intOpenCount = 0; }
				/* OPEN COUNTS ENDS */

			 }
			
		}else if(m_strTreeType!=null && m_strTreeType.equals("")==false && m_strTreeType.equals("regionwise")) {
			
			
			String l_strQueryOpen = "select count(*) from " + CommonConstants.db_Name
					+ ".incidentmaster where deleteflag_im='N' AND status_sm_im = 1 ";
			
			if(m_strRegion.equals("ONLY-REGION")) {
			    
				l_strQueryOpen=l_strQueryOpen+ " AND region_im in('"	+ m_strFinalList + "') ";
				
			}else{
				  l_strQueryOpen=l_strQueryOpen+ " AND region_im in('"	+ m_strRegion + "') and state_sm_im in('"+m_strFinalList+"')";
			}
			
			if (ProductCategoryID != null && ProductCategoryID.equals("") == false) {
				l_strQueryOpen = l_strQueryOpen + " OR productcat_pcm_im in (" + ProductCategoryID + ") ";
			}
			l_strQueryOpen = l_strQueryOpen + "  ";
		
			try {
				m_intOpenCount = customRepoEntityIncidentMaster.getCountsParameterizedNativeQuery(l_strQueryOpen);
			} catch (Exception ex) {ex.printStackTrace(); m_intOpenCount = 0; }
			
		}else if(m_strTreeType!=null && m_strTreeType.equals("")==false && m_strTreeType.equals("statewise")) {
			  
			String l_strQueryOpen = "select count(*) from " + CommonConstants.db_Name
					+ ".incidentmaster where deleteflag_im='N' AND status_sm_im = 1 ";
			
			if(m_strState.equals("ONLY-STATE")) {
				l_strQueryOpen=l_strQueryOpen+ " AND state_sm_im in('"	+ m_strFinalList + "') ";
			}else{
				l_strQueryOpen=l_strQueryOpen+ " AND state_sm_im in('"	+ m_strState + "') and location_lm_im in('"+m_strFinalList+"')";
			}
			
			if (ProductCategoryID != null && ProductCategoryID.equals("") == false) {
				l_strQueryOpen = l_strQueryOpen + " OR productcat_pcm_im in (" + ProductCategoryID + ") ";
			}
			l_strQueryOpen = l_strQueryOpen + "  ";
		 
			//System.out.println(l_strQueryOpen+ "Please check In STATEWISE");
			try {
				m_intOpenCount = customRepoEntityIncidentMaster.getCountsParameterizedNativeQuery(l_strQueryOpen);
			} catch (Exception ex) {ex.printStackTrace(); m_intOpenCount = 0; }
			
		}
	}
	
	public void getPausedCounts(HttpServletRequest request,String m_strFinalList){
		String m_strTreeType=request.getParameter("m_strTreeType");
		String ProductCategoryID = "";
		if(m_strTreeType!=null && m_strTreeType.equals("")==false && m_strTreeType.equals("rmwise")) {
			if (!m_strFinalList.equals("")) {
				 /*PAUSED COUNTS START*/
			
                String l_strQueryPaused = "select count(*) from " + CommonConstants.db_Name + ".incidentmaster where deleteflag_im='N' AND status_sm_im = 3 "
                        + "AND engineer_engm_im in(" + m_strFinalList + ") ";
                
                if (ProductCategoryID != null && ProductCategoryID.equals("") == false) {
             	   l_strQueryPaused = l_strQueryPaused + " OR productcat_pcm_im in (" + ProductCategoryID + ") ";
                }
                l_strQueryPaused = l_strQueryPaused + " ";
               
                try {
                    m_intPausedCount = customRepoEntityIncidentMaster.getCountsParameterizedNativeQuery(l_strQueryPaused);
                }catch(Exception ex) { ex.printStackTrace(); m_intPausedCount=0; }
                /*PAUSED COUNTS ENDS*/
			}
		}else if(m_strTreeType!=null && m_strTreeType.equals("")==false && m_strTreeType.equals("regionwise")) {
			 /*PAUSED COUNTS START*/
			
            String l_strQueryPaused = "select count(*) from " + CommonConstants.db_Name + ".incidentmaster where deleteflag_im='N' AND status_sm_im = 3 ";
                  
	            
                if(m_strRegion.equals("ONLY-REGION")) {
                	l_strQueryPaused=l_strQueryPaused  + " AND region_im in('" + m_strFinalList + "') ";
	            }else {
	            	l_strQueryPaused=l_strQueryPaused  + " AND region_im in('" + m_strRegion + "') and state_sm_im in('"+m_strFinalList+"') ";
	            }
                
            
            if (ProductCategoryID != null && ProductCategoryID.equals("") == false) {
         	   l_strQueryPaused = l_strQueryPaused + " OR productcat_pcm_im in (" + ProductCategoryID + ") ";
            }
            l_strQueryPaused = l_strQueryPaused + " ";
           
            try {
                m_intPausedCount = customRepoEntityIncidentMaster.getCountsParameterizedNativeQuery(l_strQueryPaused);
            }catch(Exception ex) { ex.printStackTrace(); m_intPausedCount=0; }
            /*PAUSED COUNTS ENDS*/
			
		}else if(m_strTreeType!=null && m_strTreeType.equals("")==false && m_strTreeType.equals("statewise")) {
                 /*PAUSED COUNTS START*/
			
            String l_strQueryPaused = "select count(*) from " + CommonConstants.db_Name + ".incidentmaster where deleteflag_im='N' AND status_sm_im = 3 ";
                  
	            
                if(m_strState.equals("ONLY-STATE")) {
                	l_strQueryPaused=l_strQueryPaused  + " AND state_sm_im in('" + m_strFinalList + "') ";
	            }else {
	            	l_strQueryPaused=l_strQueryPaused  + " AND state_sm_im in('" + m_strState + "') and location_lm_im in('"+m_strFinalList+"') ";
	            }
                
            
            if (ProductCategoryID != null && ProductCategoryID.equals("") == false) {
         	   l_strQueryPaused = l_strQueryPaused + " OR productcat_pcm_im in (" + ProductCategoryID + ") ";
            }
            l_strQueryPaused = l_strQueryPaused + " ";
           
            try {
                m_intPausedCount = customRepoEntityIncidentMaster.getCountsParameterizedNativeQuery(l_strQueryPaused);
            }catch(Exception ex) { ex.printStackTrace(); m_intPausedCount=0; }
            /*PAUSED COUNTS ENDS*/
			
		}
	}
	
	public void getTechClosedCounts(HttpServletRequest request,String m_strFinalList){
		String ProductCategoryID = "";
		String m_strTreeType=request.getParameter("m_strTreeType");
		if(m_strTreeType!=null && m_strTreeType.equals("")==false && m_strTreeType.equals("rmwise")) {
			if (!m_strFinalList.equals("")) {
				
				 /*TechClosed Count START*/
                String l_strQueryTechClosed = "select count(*) from " + CommonConstants.db_Name + ".incidentmaster where deleteflag_im='N' AND status_sm_im = 5 "
                        + "AND engineer_engm_im in(" + m_strFinalList + ") ";
                  
                if (ProductCategoryID != null && ProductCategoryID.equals("") == false) {
             	   l_strQueryTechClosed = l_strQueryTechClosed + " OR productcat_pcm_im in (" + ProductCategoryID + ") ";
                }
                l_strQueryTechClosed = l_strQueryTechClosed + " ";
                
                try {
                    m_intTechClosedCount = customRepoEntityIncidentMaster.getCountsParameterizedNativeQuery(l_strQueryTechClosed);
                }catch(Exception ex) { ex.printStackTrace(); m_intTechClosedCount=0; } 
                /*TechClosed Count ENDS*/
			}
		}else if(m_strTreeType!=null && m_strTreeType.equals("")==false && m_strTreeType.equals("regionwise")) {
			 /*TechClosed Count START*/
            String l_strQueryTechClosed = "select count(*) from " + CommonConstants.db_Name + ".incidentmaster where deleteflag_im='N' AND status_sm_im = 5 ";
              
	            if(m_strRegion.equals("ONLY-REGION")) {
	            	l_strQueryTechClosed=l_strQueryTechClosed  + " AND region_im in('" + m_strFinalList + "') ";
	            }else {
	            	l_strQueryTechClosed=l_strQueryTechClosed  + " AND region_im in('" + m_strRegion + "') and state_sm_im in('"+m_strFinalList+"') ";
	            }
            
            if (ProductCategoryID != null && ProductCategoryID.equals("") == false) {
         	   l_strQueryTechClosed = l_strQueryTechClosed + " OR productcat_pcm_im in (" + ProductCategoryID + ") ";
            }
            l_strQueryTechClosed = l_strQueryTechClosed + " ";
            
            try {
                m_intTechClosedCount = customRepoEntityIncidentMaster.getCountsParameterizedNativeQuery(l_strQueryTechClosed);
            }catch(Exception ex) { ex.printStackTrace(); m_intTechClosedCount=0; } 
            /*TechClosed Count ENDS*/
			
	    }else if(m_strTreeType!=null && m_strTreeType.equals("")==false && m_strTreeType.equals("statewise")) {
	    	 /*TechClosed Count START*/
            String l_strQueryTechClosed = "select count(*) from " + CommonConstants.db_Name + ".incidentmaster where deleteflag_im='N' AND status_sm_im = 5 ";
              
	            if(m_strState.equals("ONLY-STATE")) {
	            	l_strQueryTechClosed=l_strQueryTechClosed  + " AND state_sm_im in('" + m_strFinalList + "') ";
	            }else {
	            	l_strQueryTechClosed=l_strQueryTechClosed  + " AND state_sm_im in('" + m_strState + "') and location_lm_im in('"+m_strFinalList+"') ";
	            }
            
            if (ProductCategoryID != null && ProductCategoryID.equals("") == false) {
         	   l_strQueryTechClosed = l_strQueryTechClosed + " OR productcat_pcm_im in (" + ProductCategoryID + ") ";
            }
            l_strQueryTechClosed = l_strQueryTechClosed + " ";
            
            try {
                m_intTechClosedCount = customRepoEntityIncidentMaster.getCountsParameterizedNativeQuery(l_strQueryTechClosed);
            }catch(Exception ex) { ex.printStackTrace(); m_intTechClosedCount=0; } 
            /*TechClosed Count ENDS*/
			
		}
	}
	
	public void getClosedCounts(HttpServletRequest request,String m_strFinalList) {
		String ProductCategoryID = "";
		String m_strTreeType=request.getParameter("m_strTreeType");
		if(m_strTreeType!=null && m_strTreeType.equals("")==false && m_strTreeType.equals("rmwise")) {
			if (!m_strFinalList.equals("")) {
			
				  /*Closed Counts Start*/
                String l_strQueryClosed = "select count(*) from " + CommonConstants.db_Name + ".incidentmaster where deleteflag_im='N' AND status_sm_im = 4 "
                        + "AND engineer_engm_im in(" + m_strFinalList + ") ";
                
            
                
                
                if (ProductCategoryID != null && ProductCategoryID.equals("") == false) {
                	l_strQueryClosed = l_strQueryClosed + " OR productcat_pcm_im in (" + ProductCategoryID + ") ";
                 }
                l_strQueryClosed = l_strQueryClosed + " ";

                try {
             	   m_intClosed = customRepoEntityIncidentMaster.getCountsParameterizedNativeQuery(l_strQueryClosed);
                }catch(Exception ex) { ex.printStackTrace(); m_intClosed=0; } 
                
                /*Closed Counts Ends*/
			}
		}else if(m_strTreeType!=null && m_strTreeType.equals("")==false && m_strTreeType.equals("regionwise")) {
	
			
          String l_strQueryClosed = "select count(*) from " + CommonConstants.db_Name + ".incidentmaster where deleteflag_im='N' AND status_sm_im = 4 ";
          
	          if(m_strRegion.equals("ONLY-REGION")) {
	          	l_strQueryClosed=l_strQueryClosed  + " AND region_im in('" + m_strFinalList + "') ";
	          }else {
	          	l_strQueryClosed=l_strQueryClosed  + " AND region_im in('" + m_strRegion + "') and state_sm_im in('"+m_strFinalList+"') ";
	          }
          
          if (ProductCategoryID != null && ProductCategoryID.equals("") == false) {
          	l_strQueryClosed = l_strQueryClosed + " OR productcat_pcm_im in (" + ProductCategoryID + ") ";
           }
          l_strQueryClosed = l_strQueryClosed + " ";

          try {
       	   m_intClosed = customRepoEntityIncidentMaster.getCountsParameterizedNativeQuery(l_strQueryClosed);
          }catch(Exception ex) { ex.printStackTrace(); m_intClosed=0; } 
			
	     }else if(m_strTreeType!=null && m_strTreeType.equals("")==false && m_strTreeType.equals("statewise")) {

	          String l_strQueryClosed = "select count(*) from " + CommonConstants.db_Name + ".incidentmaster where deleteflag_im='N' AND status_sm_im = 4 ";
	          
		          if(m_strState.equals("ONLY-STATE")) {
		          	l_strQueryClosed=l_strQueryClosed  + " AND state_sm_im in('" + m_strFinalList + "') ";
		          }else {
		          	l_strQueryClosed=l_strQueryClosed  + " AND state_sm_im in('" + m_strState + "') and location_lm_im in('"+m_strFinalList+"') ";
		          }
	          
	          if (ProductCategoryID != null && ProductCategoryID.equals("") == false) {
	          	l_strQueryClosed = l_strQueryClosed + " OR productcat_pcm_im in (" + ProductCategoryID + ") ";
	           }
	          l_strQueryClosed = l_strQueryClosed + " ";

	          try {
	       	   m_intClosed = customRepoEntityIncidentMaster.getCountsParameterizedNativeQuery(l_strQueryClosed);
	          }catch(Exception ex) { ex.printStackTrace(); m_intClosed=0; } 
			
		 }
	}
	
	public void getSlaCounts(HttpServletRequest request,String m_strFinalList){
		String ProductCategoryID = "";
		String m_strTreeType=request.getParameter("m_strTreeType");
		if(m_strTreeType!=null && m_strTreeType.equals("")==false && m_strTreeType.equals("rmwise")) {
			if (!m_strFinalList.equals("")) {
			
				/*SLA Counts Start*/
                String l_strQuerySLA = "select count(*) from " + CommonConstants.db_Name + ".incidentmaster where deleteflag_im='N' AND resolutionflag_im = 'V' "
                        + "AND engineer_engm_im in(" + m_strFinalList + ") ";
                if (ProductCategoryID != null && ProductCategoryID.equals("") == false) {
                	l_strQuerySLA = l_strQuerySLA + " OR productcat_pcm_im in (" + ProductCategoryID + ") ";
                 }
                l_strQuerySLA = l_strQuerySLA + " ";
               
                try {
             	   m_intSlaViolated = customRepoEntityIncidentMaster.getCountsParameterizedNativeQuery(l_strQuerySLA);
                }catch(Exception ex) { ex.printStackTrace(); m_intClosed=0; }
                /*SLA Counts Ends*/
			}
		} else if(m_strTreeType!=null && m_strTreeType.equals("")==false && m_strTreeType.equals("regionwise")) {
			  String l_strQuerySLA = "select count(*) from " + CommonConstants.db_Name + ".incidentmaster where deleteflag_im='N' AND resolutionflag_im = 'V' ";
              
			  if(m_strRegion.equals("ONLY-REGION")) {
				  l_strQuerySLA=l_strQuerySLA  + " AND region_im in('" + m_strFinalList + "') ";
		          }else {
		        	  l_strQuerySLA=l_strQuerySLA  + " AND region_im in('" + m_strRegion + "') and state_sm_im in('"+m_strFinalList+"') ";
		          }
	          
              if (ProductCategoryID != null && ProductCategoryID.equals("") == false) {
              	l_strQuerySLA = l_strQuerySLA + " OR productcat_pcm_im in (" + ProductCategoryID + ") ";
               }
              
              l_strQuerySLA = l_strQuerySLA + " ";
            // .println(l_strQuerySLA+ " QUERY SLA ");
              try {
           	   m_intSlaViolated = customRepoEntityIncidentMaster.getCountsParameterizedNativeQuery(l_strQuerySLA);
              }catch(Exception ex) { ex.printStackTrace(); m_intClosed=0; }
              /*SLA Counts Ends*/
			
	    }else if(m_strTreeType!=null && m_strTreeType.equals("")==false && m_strTreeType.equals("statewise")) {
              String l_strQuerySLA = "select count(*) from " + CommonConstants.db_Name + ".incidentmaster where deleteflag_im='N' AND resolutionflag_im = 'V' ";
              
			  if(m_strState.equals("ONLY-STATE")) {
				  l_strQuerySLA=l_strQuerySLA  + " AND state_sm_im in('" + m_strFinalList + "') ";
		          }else {
		        	  l_strQuerySLA=l_strQuerySLA  + " AND state_sm_im in('" + m_strState + "') and location_lm_im in('"+m_strFinalList+"') ";
		          }
	          
              if (ProductCategoryID != null && ProductCategoryID.equals("") == false) {
              	l_strQuerySLA = l_strQuerySLA + " OR productcat_pcm_im in (" + ProductCategoryID + ") ";
               }
              
              l_strQuerySLA = l_strQuerySLA + " ";
            // System.out.println(l_strQuerySLA+ " QUERY SLA ");
              try {
           	   m_intSlaViolated = customRepoEntityIncidentMaster.getCountsParameterizedNativeQuery(l_strQuerySLA);
              }catch(Exception ex) { ex.printStackTrace(); m_intClosed=0; }
			
		}
	}
	
	
	
	
}
