package intelli.uno.apiweb.executivescreen;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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

import intelli.uno.dto.executivescreen.DataPoint;
import intelli.uno.dto.executivescreen.DtoTreeMap;

import intelli.uno.entity.EntityRegionMaster;
import intelli.uno.repositorycustom.CustomRepostitory;
import intelli.uno.service.entityregionmaster.ServiceEntityRegionMaster;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "APIControlHeatMapChart",
        description = "For ExecutiveHomeScreen, the api will fill json data HEATMAP HIGHCHART"
)
@RestController
@RequestMapping(value="/apitoslaviolated")
@CrossOrigin(origins="*")
public class APIControlHeatMapChart {

	@Autowired
	private ServiceEntityRegionMaster serviceEntityRegionMaster;

	@Autowired
	private CustomRepostitory customRepoEntityIncidentMaster;

	
	@GetMapping("/regionwise")
	public ResponseEntity<?> getSlaViolatedForRegion(@RequestParam(value="m_strDate",required=false)String m_strDate,
			@RequestParam(value="m_strMapType",required=false)String m_strMapType,
			@RequestParam(value="m_strStatusType",required=false)String m_strStatusType,
			@RequestParam(value="m_strYear",required=false)String m_strYear,
			@RequestParam(value="m_strMonth",required=false)String m_strMonth,
	    	@RequestParam(value="m_strFilter",required=false)String m_strFilter){
		 
		   
		System.out.println(CommonConstants.cyan+"IN APIControlHeatMapChart Class"+CommonConstants.reset);
		
	    		   if(m_strFilter.equals("RegionWise")) {
	    			   
                            DtoTreeMap jsonResponseChartDat2=this.getHeatMapRegionWise(m_strDate,m_strMapType,m_strStatusType,m_strYear,m_strMonth);  
                         	return ResponseEntity.status(HttpStatus.OK).header("custom-header", "DirectApproximationMargin")
                			 		.contentType(MediaType.APPLICATION_JSON).body(jsonResponseChartDat2);
	    		   } else if(m_strStatusType.equals("")) {
	    			   
	    		   }
	    	 
	    	
	     
       return null;
	}

	
    public DtoTreeMap getHeatMapRegionWise(String m_strDate,String m_strMapType,String m_strStatusType,String m_strYear,String m_strMonth) {
      
    	
    	DtoTreeMap jsonResponse=new DtoTreeMap();
    	String m_strQuery="Select count(*) from "+CommonConstants.db_Name+".incidentmaster where deleteflag_im='N' ";
    	
        int n_IntYear=0,n_IntMonth=0;
        
        try {
        	if(m_strMonth!=null  && m_strMonth.equals("")==false) {
            	n_IntMonth=Integer.parseInt(m_strMonth.trim());
            }else {
            	n_IntMonth=CommonConstants.currentMonthNumeric;
            }
        }catch(Exception ex) { n_IntMonth=CommonConstants.currentMonthNumeric;   }
        
        try {
        	if(m_strYear!=null  && m_strYear.equals("")==false) {
            	n_IntYear=Integer.parseInt(m_strYear.trim());
            }else {
            	n_IntYear=Integer.parseInt(String.valueOf(CommonConstants.currentYear));
            }	
        }catch(Exception ex) { n_IntYear=Integer.parseInt(String.valueOf(CommonConstants.currentYear));   }
        
    	
      try {
    	if(m_strMapType.equals("weekwise")) {
    	     if(m_strStatusType.equals("SLA")) {
    	    	 m_strQuery=m_strQuery+" and resolutionflag_im = 'V' ";
    	    	 jsonResponse=this.getHeatMapRegionWiseWeekWise(m_strDate, m_strMapType, m_strStatusType,m_strQuery);
    	     }
    	}else if(m_strMapType.equals("monthwise")) {
    			m_strQuery=m_strQuery+" and resolutionflag_im = 'V' ";
    	    	jsonResponse=this.getHeatMapRegionWiseMonthWise(m_strDate, m_strMapType, m_strStatusType,m_strQuery,n_IntMonth,n_IntYear);
    	}
      }catch (Exception e) { e.printStackTrace(); }	
      return jsonResponse;
    }
    
    
    
    /**THIS WILL RETURN MONTH WISE SLA VIOLATED FOR MONTH FOR REGION WISE 18 OCT, 2023**/
    public DtoTreeMap getHeatMapRegionWiseMonthWise(String m_strDate,String m_strMapType,String m_strStatusType,String m_strQuery,
    		int m_strMonth,int m_strYear) {
    	DtoTreeMap jsonResponseDtoTreeMap=new DtoTreeMap();
    	
    	String m_strTitle="";
        m_strTitle="SLA Violated Regionwise From ";
    	
        	System.out.println(CommonConstants.yellowBackground+" "+CommonConstants.currentMonthNumeric+"  "+CommonConstants.reset);
        	//m_strMonth=1;m_strYear=2023;     FOR TESTING PURPOSE
            try {
                String[] listOfMonths=new String[7];
   		        String[] listOfMonthsName=new String[7];
   		        String[] listOfYears=new String[7];
   		        
   		        listOfMonthsName[0]=String.valueOf(Month.of(m_strMonth).getDisplayName(TextStyle.SHORT, Locale.ENGLISH)+" "+m_strYear);   //FOR USE IN YAXIS
   		        listOfMonths[0]=String.valueOf(m_strMonth);   //FOR USE IN YAXIS
   		        listOfYears[0]=String.valueOf(CommonConstants.currentYear);   
   		        
   		        m_strTitle=m_strTitle+String.valueOf(Month.of(m_strMonth).getDisplayName(TextStyle.SHORT, Locale.ENGLISH)+" "+m_strYear)+" to ";
   		        
   		        for(int i=0;i<listOfMonths.length;i++) {
		              try {
		            	  if(i!=0) {
		   		        	  
		  		           	  int n_intMonthNumber=Integer.parseInt(listOfMonths[i-1]);   
		  		        	  int n_intYear=Integer.parseInt(listOfYears[i-1]);
		  		              
		  		        	  YearMonth targetDate = YearMonth.of(n_intYear, n_intMonthNumber);    //2023-10
		  		              
		  		              YearMonth lastMonth = targetDate.minusMonths(1);                       //2023-09
		  		              //Month lastMonthName = lastMonth.getMonth();    // SEPTEMBER
		  		                   
		  		              String[] twoPartsFormat = String.valueOf(lastMonth).split("-");
		  		              if (twoPartsFormat.length == 2) {
		  		                  int n_Year = Integer.parseInt(twoPartsFormat[0]); // "2023"
		  		                  int n_Month = Integer.parseInt(twoPartsFormat[1]); // "09"
		  		                  //month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
		  		                  listOfMonthsName[i]=String.valueOf(Month.of(n_Month).getDisplayName(TextStyle.SHORT, Locale.ENGLISH)+" "+n_Year);   //FOR USE IN YAXIS
		  		     		      listOfMonths[i]=String.valueOf(n_Month);   //FOR USE IN YAXIS
		  		     		      listOfYears[i]=String.valueOf(n_Year);
		  		              } 

		   		        	}  
		              }catch(Exception ex) {  ex.printStackTrace(); }		        	  		              
		              //DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM MMM yyyy");
		              //String formattedLastMonth = lastMonth.format(monthFormatter);  
		              //System.out.println("Last Month: " + formattedLastMonth);      //09 Sep 2023
		        }

   		      String m_strlastValueDate = listOfMonthsName[listOfMonthsName.length - 1];
  		      m_strTitle=m_strTitle+m_strlastValueDate+"";
  		      jsonResponseDtoTreeMap.setM_strTitle(m_strTitle);          
  		        
  		      
   		     jsonResponseDtoTreeMap.setYaxisCategories(Arrays.asList(listOfMonthsName));
   		     System.out.println("Years Names "+Arrays.toString(listOfYears));   
   		     System.out.println("Month Names "+Arrays.toString(listOfMonthsName));   		        
   		     System.out.println( " LOOP ENDS HERE   "+Arrays.toString(listOfMonths));
   		     List<EntityRegionMaster> l1_RegionList= serviceEntityRegionMaster.returnListOfRegionWithKeyValue();
	   		 
   		     if(l1_RegionList!=null && l1_RegionList.isEmpty()==false) {
		    	  String[] m_strRegionArray=new String[l1_RegionList.size()];
			      for(int i=0;i<l1_RegionList.size();i++) {
			    	    m_strRegionArray[i] = l1_RegionList.get(i).getTypevalueRm();
			      }
			      jsonResponseDtoTreeMap.setXaxisCategories(Arrays.asList(m_strRegionArray));
			      List<DataPoint> dataPoints = new ArrayList<>();
			      
			       try {
			    	   for(int i=0;i<m_strRegionArray.length;i++) {
				        	for(int j=0;j<listOfYears.length;j++) {
				        		String m_strSubQuery=m_strQuery;
				        		m_strSubQuery= m_strSubQuery + " and region_im='"+m_strRegionArray[i]+"'" ;
				        		m_strSubQuery=m_strSubQuery+" and month(incdate_im)="+listOfMonths[j]+" and year(incdate_im)="+listOfYears[j];
				        		//System.out.println("QUERY IN HEARTMAP "+m_strSubQuery);
				        		int n_IntvalueCount = customRepoEntityIncidentMaster.getCountsParameterizedNativeQuery(m_strSubQuery);
				        		dataPoints.add(new DataPoint(i,j,n_IntvalueCount));
				        		m_strSubQuery="";
					        }
					    }
			       }catch(Exception ex) { ex.printStackTrace(); }
			       jsonResponseDtoTreeMap.setData(dataPoints);
			     
	   		 }
   		     
            }catch(Exception ex) { ex.printStackTrace(); }    	
        
        
        return jsonResponseDtoTreeMap;     
    }
    
    
    public DtoTreeMap getHeatMapRegionWiseWeekWise(String m_strDate,String m_strMapType,String m_strStatusType,String m_strQuery) {
        DtoTreeMap jsonResponse=new DtoTreeMap();
        try {
        	 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
 	         LocalDate currentDate = LocalDate.parse(m_strDate, formatter);      //  OUTPUT WILL BE-----> 2023-10-18
 	         
             String m_strTitle="";
	         m_strTitle="SLA Violated Regionwise From ";
	         String[] listOfDays=new String[7];
		     String[] listOfDate=new String[7];
      	
		     listOfDays[0]=String.valueOf(""+currentDate.getDayOfWeek().getDisplayName(TextStyle.SHORT_STANDALONE, Locale.ENGLISH));   //FOR USE IN YAXIS
		     listOfDate[0]=String.valueOf(currentDate);          //FOR USE IN QUERY
		     
		     m_strTitle=m_strTitle+String.valueOf(currentDate)+" to ";
		     
		     
		     /**ADDIND DAYS IN ARRAYLIST**/
		     for(int i=0;i<listOfDays.length;i++) {
		        	if(i!=0) {
		        		LocalDate sixDaysBefore = currentDate.minusDays(i);
				        listOfDays[i]=String.valueOf(""+sixDaysBefore.getDayOfWeek().getDisplayName(TextStyle.SHORT_STANDALONE, Locale.ENGLISH));
				        listOfDate[i]=String.valueOf(sixDaysBefore);
		        	}
		      }
		     
		     
		     jsonResponse.setYaxisCategories(Arrays.asList(listOfDays));
		     
		      String m_strlastValueDate = listOfDate[listOfDate.length - 1];
		      m_strTitle=m_strTitle+m_strlastValueDate+"(Per Week)";
		      jsonResponse.setM_strTitle(m_strTitle);          

		      List<EntityRegionMaster> l1_RegionList= serviceEntityRegionMaster.returnListOfRegionWithKeyValue();
		      try {
		    	  
		    	  if(l1_RegionList.isEmpty()==false) {
		    		  
			    	  String[] m_strRegionArray=new String[l1_RegionList.size()];
				      for(int i=0;i<l1_RegionList.size();i++) {
				    	    m_strRegionArray[i] = l1_RegionList.get(i).getTypevalueRm();
				      }

				      jsonResponse.setXaxisCategories(Arrays.asList(m_strRegionArray));	
				     
				      List<DataPoint> dataPoints = new ArrayList<>();
				      
				       try {
				    	   for(int i=0;i<m_strRegionArray.length;i++) {
					        	
					        	for(int j=0;j<listOfDate.length;j++) {
					        		String m_strSubQuery=m_strQuery;
					        		m_strSubQuery= m_strSubQuery + " and region_im='"+m_strRegionArray[i]+"'" ;
					        		m_strSubQuery=m_strSubQuery+" and incdate_im='"+listOfDate[j]+"'";
					        		//System.out.println("QUERY IN HEARTMAP "+m_strSubQuery);
					        		int n_IntvalueCount = customRepoEntityIncidentMaster.getCountsParameterizedNativeQuery(m_strSubQuery);
					        		dataPoints.add(new DataPoint(i,j,n_IntvalueCount));
					        		m_strSubQuery="";
						        }
						        	
					        }
				       }catch(Exception ex) { ex.printStackTrace(); }
				    
				       jsonResponse.setData(dataPoints);
				        	
			      }  
		      }catch(Exception ex) { ex.printStackTrace(); }
		      
		      
		  
		      
		     
        }catch (Exception e) { e.printStackTrace(); }	
        return jsonResponse;
      }
}
