package intelli.uno.apiweb.margin;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import intelli.uno.bean.BeanIndirectMargin;
import intelli.uno.commons.CommonConstants;
import intelli.uno.commons.CommonUtils;
import intelli.uno.dto.margin.DtoChartData;
import intelli.uno.dto.margin.DtoMarginJson;
import intelli.uno.dto.margin.DtoSeries;
import intelli.uno.dto.margin.DtoTableHeader;
import intelli.uno.dto.margin.DtoTableRow;
import intelli.uno.repositorycustom.CustomRepostitory;
import intelli.uno.service.ServiceMarginApproximationHelper;
import intelli.uno.service.ServiceMarginFinancialYear;
import intelli.uno.service.ServiceMarginYearTillDate;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "APIControlINDirectControl", description = "This API Generate INDIRECT Margin Approximation Calculation")
@RestController
@RequestMapping(value = "/apiindirectmargin")
@CrossOrigin(origins = "*")
public class APIControlINDirectControl {


	@Autowired
	private CustomRepostitory customRepoEntityIncidentMaster;
	
	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private BeanIndirectMargin beanApproximationMargin;
	
	@Autowired
	private ServiceMarginFinancialYear serviceMarginFinancialYear;
	
	@Autowired
	private ServiceMarginYearTillDate serMarginYearTillDate;
	
	@Autowired
	private ServiceMarginApproximationHelper serviceMarginHelper;


	public static int n_intTotalRecordsCounts = 0;
	public static int n_intTillMonthYTD=0;
	
	public static String m_strCurrencySymbolFormat = "";  public static String m_strStatPeriodicType="",m_strStatQarter="",m_strStatPFY="",m_strStatYTD="";
    
	public static int staticIntCurrentMonthNumber = 0;
	public static int staticIntCurrentDate;
	public static int staticIntCurrentYear = 0;
	
	public static String MAIN_QUERY_TO_DOWNLOAD="";
    public static String SHEET_NAME="category_date";
	
	
	public static String headerAVGMonth="",headerYTD="";
	
	/** WITH Pagination Implementation **/
	@GetMapping("/indirect")
	public ResponseEntity<?> getTableData(
			@RequestParam(name = "m_strAvgcostperticketid", required = false) String m_strAvgcostperticketid,
			@RequestParam(name = "m_strPeriodictype", required = false) String m_strPeriodictype,
			@RequestParam(name = "m_strCurrency", required = false, defaultValue = "INR") String m_strCurrency,
			@RequestParam(name = "m_strFromDate", required = false) String m_strFromDate,
			@RequestParam(name = "m_strToDate", required = false) String m_strToDate,
			@RequestParam(name = "m_strYear", required = false) String m_strYear,
			@RequestParam(name = "m_strPFY", required = false) String m_strPFY,
			@RequestParam(name = "m_strPageSize", required = false, defaultValue = "All") String m_strPageSize,
			@RequestParam(name = "m_strPageNo", required = false, defaultValue = "1") String m_strPageNo,
			@RequestParam(name = "m_strFilterBy", required = false) String m_strFilterBy,
			@RequestParam(name = "m_strQarter", required = false) String m_strQarter,
			@RequestParam(name = "m_strSortingType", required = false, defaultValue = "Descending") String m_strSortingType) {

		headerAVGMonth="Avg Per Month"; headerYTD="YTD";
		m_strStatPeriodicType=m_strPeriodictype;  m_strStatQarter=m_strQarter;
		
		DtoMarginJson jsonResponse=new DtoMarginJson();
		try {
			m_strYear = m_strYear.replaceAll("\\s", "");	
		}catch(Exception ex) { ex.printStackTrace();  m_strYear=""+CommonConstants.currentYear;  } 
		
		m_strAvgcostperticketid = m_strAvgcostperticketid.replaceAll("\\s", "");

		System.out.println(CommonConstants.bold + "  ");
		System.out.println(m_strPageSize + " PAGE SIZE & PAGE NO " + m_strPageNo);
		System.out.println("OVERALL-COST: " + m_strAvgcostperticketid);
		System.out.println("m_strPeriodictype: " + m_strPeriodictype);
		System.out.println("m_strCurrency: " + m_strCurrency);
		System.out.println("m_strFromDate: " + m_strFromDate);
		System.out.println("m_strToDate: " + m_strToDate);
		System.out.println("m_strYear: " + m_strYear);
		System.out.println("m_strPFY: " + m_strPFY);
		System.out.println("m_strPageSize: " + m_strPageSize);
		System.out.println("m_strPageNo: " + m_strPageNo);
		System.out.println("m_strFilterBy: " + m_strFilterBy);
		System.out.println("m_strQarter: " + m_strQarter);
		System.out.println("m_strSortingType: " + m_strSortingType);
		System.out.println(CommonConstants.reset + "  ");

		List<DtoTableRow> tableData = new ArrayList<>();

		LocalDate obj_CurrentDate = LocalDate.now();
		staticIntCurrentMonthNumber = obj_CurrentDate.getMonthValue(); // current Month 2
		staticIntCurrentDate = obj_CurrentDate.getDayOfMonth(); // Current Day Date 26
		staticIntCurrentYear = obj_CurrentDate.getYear(); // Current Year 2023

		/** AVERAGE COST PER TICKETS **/
		Integer n_intAverageCost = 0;
		n_intAverageCost = Integer.parseInt(m_strAvgcostperticketid);

		int m_strCurrentYear = 0;                        //int m_strPreviousYear = 0;
		m_strCurrentYear = Integer.parseInt(m_strYear);  //m_strPreviousYear = m_strCurrentYear - 1;

		
		
		System.out.println(CommonConstants.blue+" "+m_strCurrencySymbolFormat+" "+CommonConstants.reset);
		if(m_strPeriodictype!=null && m_strPeriodictype.equals("")==false) {
			String m_strQuery="SELECT "
	        		+ "    principal_cm_im, "
	        		+ "    customer_cm_im, "
	        		+ "    COUNT(*) AS count,"
	        		+ "    COUNT(*) * "+n_intAverageCost+" AS multiplied_count "
	        		+ "FROM  "
	        		+CommonConstants.db_Name+".incidentmaster "
	        		+ "WHERE "
	        		+ " engineer_engm_im IS NOT NULL "
	        		+ " AND principal_cm_im IS NOT NULL "
	        		+ " And customer_cm_im is not null \n";
			
			
			   try {
				   if(m_strPeriodictype.equals("")==false && m_strPeriodictype!=null  && m_strPeriodictype.equals("FY")) {	
				    	 
					   tableData=this.returnListByFinancialYear(m_strPeriodictype,m_strFromDate,m_strToDate,
				    			 n_intAverageCost,m_strCurrentYear,m_strQarter,m_strPageSize,m_strPageNo,m_strFilterBy,m_strPFY,m_strQuery);
				    	 
			        }else if(m_strPeriodictype.equals("")==false && m_strPeriodictype!=null  && m_strPeriodictype.equals("YTD")) {
			        	
			       	    tableData=this.returnListByYearTillDate(m_strPeriodictype,m_strFromDate,m_strToDate,
				    			 n_intAverageCost,m_strCurrentYear,m_strQarter,m_strPageSize,m_strPageNo,m_strFilterBy,m_strPFY,m_strQuery);
			       	    
			        }else if(m_strPeriodictype.equals("")==false && m_strPeriodictype!=null  && m_strPeriodictype.equals("custom")) {
			        	
			        	 tableData=this.returnListByCustomYearTillDate(m_strPeriodictype,m_strFromDate,m_strToDate,
				    			 n_intAverageCost,m_strCurrentYear,m_strQarter,m_strPageSize,m_strPageNo,m_strFilterBy,m_strPFY,m_strQuery);
			        	
			        }
			   }catch(Exception ex) { ex.printStackTrace(); }
	    }	
	   	
		this.beanApproximationMargin=applicationContext.getBean(BeanIndirectMargin.class);
		if(tableData!=null && tableData.size()!=0) {
			this.beanApproximationMargin.setL_strTable(tableData);	
		}else {    this.beanApproximationMargin.setL_strTable(new ArrayList<>()); }// or any other method to initialize an empty list
		
        
        this.beanApproximationMargin.setM_strQueryToDownload(MAIN_QUERY_TO_DOWNLOAD);
        this.beanApproximationMargin.setM_strCurrency(m_strCurrency);       
        MAIN_QUERY_TO_DOWNLOAD="";
	   
		if(m_strSortingType.equals("Ascending")) { }else if(m_strSortingType.equals("Descending")){ }
         
		List<DtoTableHeader> tableRow=serviceMarginHelper.returnTableHeaders(headerYTD,headerAVGMonth);
		
		jsonResponse.setTableRowHead(tableRow);
		
		jsonResponse.setTableData(tableData);
        
		if(m_strPageSize.equals("All")) {
	     	jsonResponse.setPageinationBoolean("False");
	     	jsonResponse.setM_strCurrencyType(m_strCurrency);
		}else {
			jsonResponse.setPageNo(m_strPageNo);
			jsonResponse.setN_intTotalPages(n_intTotalRecordsCounts);
			jsonResponse.setPageSize(""+m_strPageSize);
			jsonResponse.setPageinationBoolean("True");
			jsonResponse.setM_strCurrencyType(m_strCurrency);
			n_intTotalRecordsCounts=0;
		}
		
		return ResponseEntity.status(HttpStatus.OK).header("custom-header", "DirectApproximationMargin")
				.contentType(MediaType.APPLICATION_JSON).body(jsonResponse);

	}

	@SuppressWarnings("unused")
	public List<DtoTableRow> returnListByCustomYearTillDate(String m_strPeriodictype,String m_strFromDate,String m_strToDate,
			Integer n_intAverageCost,int m_strCurrentYear,String m_strQarter,String m_strPageSize,String m_strPageNo,String m_strFilterBy,String m_strPFY,
			String m_strQuery1){
		
		System.out.println("m_strFromDate= "+m_strFromDate+" m_strToDate= "+m_strToDate);
		List<DtoTableRow> tableData = new ArrayList<>();
		 
		DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
		LocalDate m_strOriginalStartDate = LocalDate.parse(m_strFromDate, originalFormatter);
		LocalDate m_strOriginalEndDate = LocalDate.parse(m_strToDate, originalFormatter);
      
		//Period period = Period.between(m_strOriginalStartDate, m_strOriginalEndDate);   //DAYS DIFFERENCE
		//System.out.println(period+" Period ");

		long yearsDiff = ChronoUnit.YEARS.between(m_strOriginalStartDate, m_strOriginalEndDate);
		long monthsDiff = ChronoUnit.MONTHS.between(m_strOriginalStartDate.plusYears(yearsDiff), m_strOriginalEndDate);
		
		if (m_strOriginalStartDate.getDayOfMonth() > m_strOriginalEndDate.getDayOfMonth()) {
		    monthsDiff--;
		}
		
		System.out.println(monthsDiff);
	
		
		String m_strStartDateNew =String.valueOf(m_strOriginalStartDate);
		String m_strEndDateNew=String.valueOf(m_strOriginalEndDate);
		
		
        int currentMonthNumberStart = m_strOriginalStartDate.getMonthValue();         int currentYearStart = m_strOriginalStartDate.getYear();
        int currentMonthNumberEnd = m_strOriginalEndDate.getMonthValue();           int currentYearEnd = m_strOriginalEndDate.getYear();

        
        //LocalDate startDate = LocalDate.of();

        
		String m_strQuery="Select principal_cm_im,customer_cm_im,Count(*),COUNT(*) *"+n_intAverageCost+" AS multiplied_count from "+CommonConstants.db_Name+".incidentmaster "
				 + " where engineer_engm_im is not null and principal_cm_im is not null  "
				 + "AND incdate_im BETWEEN '" + m_strStartDateNew + "' AND '" + m_strEndDateNew + "' "
				 + "and customer_cm_im is not null  group"
				 + " by principal_cm_im,customer_cm_im";
		
		System.out.println("m_strQuery= "+m_strQuery);
		List<Object[]> l1;
		if(m_strPageSize.equals("All")) {
			 System.out.println("In Custom Wise Date Range All Query WITHOUT PAGEINATION "+m_strQuery);
			 MAIN_QUERY_TO_DOWNLOAD=m_strQuery;
		     l1=customRepoEntityIncidentMaster.returnListOfEngineerAttendance(m_strQuery);	 
		}else {
			 System.out.println("In Custom Wise Date Range WITHOUT PAGEINATION "+m_strQuery);
			 int n_intTotalRecords=0;
			 try {
			   n_intTotalRecords=serviceMarginHelper.returnTotalCountsRecords(m_strQuery,m_strPageSize);
			 }catch(Exception ex) {  System.out.println("Error In Pageination "); }
			 n_intTotalRecordsCounts=n_intTotalRecords;
		     l1=customRepoEntityIncidentMaster.returnListOfPageinationConcept2(m_strPageSize,m_strPageNo,m_strQuery);
		}
		
		n_intTillMonthYTD=(int) monthsDiff;;
		if(l1!=null && l1.isEmpty()==false){
	      //tableData=serMarginYearTillDate.perFormIteration(l1,m_strCurrencySymbolFormat,staticIntCurrentDate,n_intTillMonthYTD,m_strPeriodictype,m_strStatQarter,m_strStatYTD);
			tableData=serMarginYearTillDate.perFormIteration(l1,m_strCurrencySymbolFormat,staticIntCurrentDate,n_intTillMonthYTD,m_strPeriodictype,m_strStatQarter,m_strStatYTD);
		}		
		
		return tableData;
	}


	public List<DtoTableRow> returnListByYearTillDate(String m_strPeriodictype,String m_strFromDate,String m_strToDate,Integer n_intAverageCost,int m_strCurrentYear,String m_strQarter,String m_strPageSize,String m_strPageNo,String m_strFilterBy,String m_strPFY,
			String m_strQuery){
		
		 m_strStatYTD=String.valueOf(m_strCurrentYear);
		 List<DtoTableRow> tableData = new ArrayList<>();
		 
		 if(m_strQarter.equals("")==false && m_strQarter!=null && m_strQarter.equals("All")) {
			 n_intTillMonthYTD=staticIntCurrentMonthNumber-1;
			  if(staticIntCurrentYear==m_strCurrentYear) {
	                int n_intStartMonth=1;  if(n_intTillMonthYTD==0) {  n_intStartMonth=1;   n_intTillMonthYTD=2;  }
	                
	                m_strQuery=m_strQuery + "    AND YEAR(incdate_im) = "+m_strCurrentYear+" "
	                		+ "    AND MONTH(incdate_im) BETWEEN "+n_intStartMonth+" AND "+n_intTillMonthYTD+" \r\n"
	                		+ "    AND customer_cm_im IS NOT NULL  "
	                		+ "GROUP BY "
	                		+ "    principal_cm_im,"
	                		+ "    customer_cm_im ";
	                headerAVGMonth=headerAVGMonth+" From Jan To "+CommonUtils.getMonthName(n_intTillMonthYTD)+", "+m_strCurrentYear;      		
			 	}else {
				     m_strQuery=m_strQuery+" and year(incdate_im)="+m_strCurrentYear+"  "
								 +  "and customer_cm_im is not null  group"
								 + " by principal_cm_im,customer_cm_im";
				     n_intTillMonthYTD=12;
				     headerAVGMonth=headerAVGMonth+" From Jan To Dec "+m_strCurrentYear;
			 	}
		      
			  
			  					
					List<Object[]> l1;
					if(m_strPageSize.equals("All")) {
						 System.out.println("In YTD Without Pageination \n "+m_strQuery);
						 MAIN_QUERY_TO_DOWNLOAD=m_strQuery;
					     l1=customRepoEntityIncidentMaster.returnListOfEngineerAttendance(m_strQuery);	 
					}else {	
						
						 int n_intTotalRecords=0;
						 try {
						   n_intTotalRecords=serviceMarginHelper.returnTotalCountsRecords(m_strQuery,m_strPageSize);
						 }catch(Exception ex) {  System.out.println("Error In Pageination 260 "); }
						 n_intTotalRecordsCounts=n_intTotalRecords;
						 
						 System.out.println("In YTD All Query WITH PAGEINATION "+m_strQuery);
						 MAIN_QUERY_TO_DOWNLOAD=m_strQuery;
					     l1=customRepoEntityIncidentMaster.returnListOfPageinationConcept2(m_strPageSize,m_strPageNo,m_strQuery);
					}
				
					if(l1!=null && l1.isEmpty()==false){
						tableData=serMarginYearTillDate.perFormIteration(l1,m_strCurrencySymbolFormat,staticIntCurrentDate,n_intTillMonthYTD,m_strPeriodictype,m_strStatQarter,m_strStatYTD);
					}		
		 }else{
		      
		       	String xM_strQ="",m_strFromMonth="",m_strToMonth="";
		       	xM_strQ=CommonUtils.getQarterByYear(m_strQarter);// Accessing the individual parts
		       	List<String> m_lstQarterList=CommonUtils.getQarterByYearList(m_strQarter);
		        try {
		        	String[] parts = xM_strQ.split("-");  m_strFromMonth =  parts[0];   	m_strToMonth = parts[1];	
		        }catch(Exception ex) {  System.err.println(""+ex); m_strFromMonth = "1";  m_strToMonth = "3"; }
		        
		        n_intTillMonthYTD=3;
		        if(staticIntCurrentYear==m_strCurrentYear) {
		        	if (m_lstQarterList.contains(String.valueOf(staticIntCurrentMonthNumber))) {
		        		int monthNumber=0;  monthNumber =staticIntCurrentMonthNumber-1; if(monthNumber==0) { monthNumber=1; n_intTillMonthYTD=1; }
		        		m_strToMonth=""+monthNumber;
		        		n_intTillMonthYTD=2;
		        	}
		        }
		        
		        headerAVGMonth=headerAVGMonth+" From "+CommonUtils.getMonthName(Integer.parseInt(m_strFromMonth))+" To "+CommonUtils.getMonthName(Integer.parseInt(m_strToMonth))+", "+m_strCurrentYear;
		        m_strQuery=m_strQuery+" AND YEAR(incdate_im)="+m_strCurrentYear+" "
    					 +  "AND MONTH(incdate_im) >= "+m_strFromMonth+" AND MONTH(incdate_im) <= "+m_strToMonth+" "
   					 + " and customer_cm_im is not null  group"
   					 + " by principal_cm_im,customer_cm_im";
			
		        System.out.println("QUERY IN YTD IN QARTER  := "+m_strQuery);
				List<Object[]> l1;
				if(m_strPageSize.equals("All")) {
				      MAIN_QUERY_TO_DOWNLOAD=m_strQuery;
				     l1=customRepoEntityIncidentMaster.returnListOfEngineerAttendance(m_strQuery);	 
				}else {
					 int n_intTotalRecords=0;
					 try {
					   n_intTotalRecords=serviceMarginHelper.returnTotalCountsRecords(m_strQuery,m_strPageSize);
					 }catch(Exception ex) {  System.out.println("ERROR IN PAGEINATION IN YTD"); }
					   n_intTotalRecordsCounts=n_intTotalRecords;
					 MAIN_QUERY_TO_DOWNLOAD=m_strQuery;
				     l1=customRepoEntityIncidentMaster.returnListOfPageinationConcept2(m_strPageSize,m_strPageNo,m_strQuery);
				}
				
				if(l1!=null && l1.isEmpty()==false){
					tableData=serMarginYearTillDate.perFormIteration(l1,m_strCurrencySymbolFormat,staticIntCurrentDate,n_intTillMonthYTD,m_strPeriodictype,m_strStatQarter,m_strStatYTD);
				}		
			
				
		 }
		 return tableData;
	}
 	
	public List<DtoTableRow> returnListByFinancialYear(String m_strPeriodictype,String m_strFromDate,String m_strToDate,Integer n_intAverageCost,int m_strCurrentYear,String m_strQarter,String m_strPageSize,String m_strPageNo,String m_strFilterBy,String m_strPFY,
			String m_strQuery){
	     
		 List<DtoTableRow> tableData = new ArrayList<>();
		 m_strStatPFY=m_strPFY;

		 	int hyphenIndex = m_strPFY.indexOf("-");
			String part1PreviousYear = m_strPFY.substring(0, hyphenIndex); //   2022
			String part2CurrentYear = m_strPFY.substring(hyphenIndex + 1); //  2023
		 
		 if(m_strQarter.equals("")==false && m_strQarter!=null && m_strQarter.equals("All")) {	    
			 
				   headerAVGMonth=headerAVGMonth+" From April 1,"+part1PreviousYear+" To March 31,"+part2CurrentYear;
					
					m_strQuery=m_strQuery+" "
							+ "  AND ("
							+ "    (YEAR(incdate_im) = "+part1PreviousYear+" AND MONTH(incdate_im) >= 4) "
							+ "    OR"
							+ "    (YEAR(incdate_im) = "+part2CurrentYear+" AND MONTH(incdate_im) <= 3) "
							+ "  )"
							+ "GROUP BY principal_cm_im, customer_cm_im ";
					
					 MAIN_QUERY_TO_DOWNLOAD=m_strQuery;
					 List<Object[]> l1;
					 
					 if(m_strPageSize.equals("All")) {
						 System.out.println("IN PFY ALL PAGE SIZE WITHOUT PAGEINATION \n "+m_strQuery);
					     l1=customRepoEntityIncidentMaster.returnListOfEngineerAttendance(m_strQuery);	 
					 }else {	
						 int n_intTotalRecords=0;
						 
						 try {
						   n_intTotalRecords=serviceMarginHelper.returnTotalCountsRecords(m_strQuery,m_strPageSize);
						 }catch(Exception ex) {  System.out.println("Error In Pageination "); }
						 n_intTotalRecordsCounts=n_intTotalRecords;
						 
						 System.out.println("In PFY Qarter All Query WITH PAGEINATION "+m_strQuery);
					     l1=customRepoEntityIncidentMaster.returnListOfPageinationConcept2(m_strPageSize,m_strPageNo,m_strQuery);
					  }
					  
					 if(l1!=null &&l1.isEmpty()==false) {
						  tableData=serviceMarginFinancialYear.perFormIteration(l1,m_strCurrencySymbolFormat,staticIntCurrentDate,staticIntCurrentMonthNumber,m_strPeriodictype,m_strStatQarter,m_strPFY);	 
					 }
			
		}else {

			
			String m_strYearForPfy=""; String m_strFromMonth=""; String m_strToMonth="";String xM_strQ="";
			
			xM_strQ=CommonUtils.getQarterByPFY(m_strQarter,part1PreviousYear,part2CurrentYear);// Accessing the individual parts
			
	        try {
	        	String[] parts = xM_strQ.split("-"); 
	        	m_strFromMonth =  parts[0];   	m_strToMonth = parts[1]; 	m_strYearForPfy =  parts[2];
	        }catch(Exception ex) {  System.err.println(""+ex); m_strFromMonth = "1";  m_strToMonth = "3"; }
	        
	        //String monthName = Month.of(Integer.parseInt(m_strFromMonth)).name();
			//String monthName = ;
			headerAVGMonth=headerAVGMonth+" From 1 "+Month.of(Integer.parseInt(m_strFromMonth)).name()+","+m_strYearForPfy+
					" To  30 "+ Month.of(Integer.parseInt(m_strToMonth)).name()+""+m_strYearForPfy;

			m_strQuery=m_strQuery+" "
					 + "  AND MONTH(incdate_im) >= "+m_strFromMonth+" "
					 		+ " AND MONTH(incdate_im) <= "+m_strToMonth+"  "
					 + " and YEAR(incdate_im)="+m_strYearForPfy+"  group"
					 + " by principal_cm_im,customer_cm_im";
		    
			MAIN_QUERY_TO_DOWNLOAD=m_strQuery;
			List<Object[]> l1;
			 if(m_strPageSize.equals("All")) {
				 System.out.println("In PFY Qarter All Query WITHOUT PAGEINATION \n "+m_strQuery);
			     l1=customRepoEntityIncidentMaster.returnListOfEngineerAttendance(m_strQuery);	 
			 }else {	
				 int n_intTotalRecords=0;
				 try {
				   n_intTotalRecords=serviceMarginHelper.returnTotalCountsRecords(m_strQuery,m_strPageSize);
				 }catch(Exception ex) {  System.out.println("Error In Pageination "); }
				 n_intTotalRecordsCounts=n_intTotalRecords;
				 System.out.println("In PFY Qarter All Query WITH PAGEINATION \n "+m_strQuery);
			     l1=customRepoEntityIncidentMaster.returnListOfPageinationConcept2(m_strPageSize,m_strPageNo,m_strQuery);
			     
			 }
			
			 if(l1!=null && l1.isEmpty()==false){
				 tableData=serviceMarginFinancialYear.perFormIteration(l1,m_strCurrencySymbolFormat,staticIntCurrentDate,staticIntCurrentMonthNumber,m_strPeriodictype,m_strStatQarter,m_strPFY);	 	 
			 }  
			 
		}
		return tableData;
	}


    /**HIGHCHART DATA IMPLEMENTATION  ADDED BY PIYUSHRAJ 2 AUG 2023**/
    @GetMapping("/negativecolumndata")
    public DtoChartData getDtoChartDataForLoss() {
	   
    System.out.println(CommonConstants.blue+ " GETTING NEGATIVE CUSTOMER  "+CommonConstants.reset);
	//System.out.println("PIYUSH PLEASE CHECK HERE  "+this.beanApproximationMargin.getL_strTable()+ " " );   
	  List<DtoTableRow> l_objLowest=new ArrayList<DtoTableRow>();
	  for(int i=0;i<this.beanApproximationMargin.getL_strTable().size();i++) {
		  DtoTableRow b1=this.beanApproximationMargin.getL_strTable().get(i);
		  l_objLowest.add(b1);
	  }
	  
	  
	  Collections.sort(l_objLowest, new MarginComparatorAscending());   //ASCENDING ORDER
	  
	  int iterations = 5;
	  String[] m_strLowestArrayCustomerName=new String[iterations];
	  int[] m_strEoyMargin=new int[iterations];
	  int[] m_strYtdMargin=new int[iterations];
	  int[] m_strProfit=new int[iterations];
	  int[] m_strContractValues=new int[iterations];
	  
	  
      for (int i = 0; i < Math.min(iterations, l_objLowest.size()); i++) {
            DtoTableRow row = l_objLowest.get(i);
            m_strLowestArrayCustomerName[i]=row.getCustomerName();
            
            try {
            	m_strEoyMargin[i]=row.getN_intEoyMarginDto();
            }catch(Exception ex) { ex.printStackTrace(); }
            
            try {
            	m_strYtdMargin[i]=row.getN_intYtdMarginDto();
            }catch(Exception ex) { ex.printStackTrace(); }
            
            try {
            	m_strProfit[i]=row.getN_intProfitExpected();
            }catch(Exception ex) { ex.printStackTrace(); }
            
            try {
              m_strContractValues[i]=row.getN_intContractValueDto();
            }catch(Exception ex) { ex.printStackTrace(); }
        }
	  
	  
	  DtoChartData chartData = new DtoChartData();
      chartData.setCategories(m_strLowestArrayCustomerName);
      
      DtoSeries[] series = new DtoSeries[4];
      series[0] = new DtoSeries("Contract Values", m_strContractValues);
      series[1] = new DtoSeries("YTD Margin",m_strYtdMargin);
      series[2] = new DtoSeries("EOY Margin",m_strEoyMargin);
      series[3] = new DtoSeries("Profit ", m_strProfit);
      chartData.setSeries(series);
      l_objLowest.clear();
      return chartData; 
    }

    
    /**HIGHCHART DATA IMPLEMENTATION  ADDED BY PIYUSHRAJ 6 AUG 2023**/
    @GetMapping("/getDtochartdataForProfit")
    public DtoChartData getDtoChartDataForProfit() {
	   
    	System.out.println(CommonConstants.blue+ " GETTING POSITIVE CUSTOMER  "+CommonConstants.reset);
    	
	  List<DtoTableRow> l_objLowest=new ArrayList<DtoTableRow>();
	  for(int i=0;i<this.beanApproximationMargin.getL_strTable().size();i++) {
		  DtoTableRow b1=this.beanApproximationMargin.getL_strTable().get(i);
		  l_objLowest.add(b1);
	  }
	  
	  
	  Collections.sort(l_objLowest, new MarginComparatorDescending());   //ASCENDING ORDER
	  
	  int iterations = 5;
	  String[] m_strLowestArrayCustomerName=new String[iterations];
	  int[] m_strEoyMargin=new int[iterations];
	  int[] m_strYtdMargin=new int[iterations];
	  int[] m_strProfit=new int[iterations];
	  int[] m_strContractValues=new int[iterations];
	  
	  
      for (int i = 0; i < Math.min(iterations, l_objLowest.size()); i++) {
            DtoTableRow row = l_objLowest.get(i);
            m_strLowestArrayCustomerName[i]=row.getCustomerName();
            
            try {
            	m_strEoyMargin[i]=row.getN_intEoyMarginDto();
            }catch(Exception ex) { ex.printStackTrace(); }
            
            try {
            	m_strYtdMargin[i]=row.getN_intYtdMarginDto();
            }catch(Exception ex) { ex.printStackTrace(); }
            
            try {
            	m_strProfit[i]=row.getN_intProfitExpected();
            }catch(Exception ex) { ex.printStackTrace(); }
            
            try {
              m_strContractValues[i]=row.getN_intContractValueDto();
            }catch(Exception ex) { ex.printStackTrace(); }
        }
	  
	  DtoChartData chartData = new DtoChartData();
      chartData.setCategories(m_strLowestArrayCustomerName);
      
      DtoSeries[] series = new DtoSeries[4];
      series[0] = new DtoSeries("Contract Values", m_strContractValues);
      series[1] = new DtoSeries("YTD Margin",m_strYtdMargin);
      series[2] = new DtoSeries("EOY Margin",m_strEoyMargin);
      series[3] = new DtoSeries("Profit ", m_strProfit);
      
      chartData.setSeries(series);
      l_objLowest.clear();
      return chartData; 
    }

    /**DOWNLOAD FUNCTIONALITY IMPLEMENTATION**/
    @GetMapping("/downloadindirectmargin")
	public ResponseEntity<Resource> returnExcelFileFOrPiyush() throws IOException{
		
		LocalDate obj_CurrentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();                                                   //Current Time
        
        String m_strTime=String.valueOf(obj_CurrentDate)+"_"+String.valueOf(currentTime);
	    
        String fileName="DirectMarginApproximation"+m_strTime+".xlsx";
	    
        ByteArrayInputStream b1 = null;
	    if(m_strStatPeriodicType.equals("")==false && m_strStatPeriodicType!=null  && m_strStatPeriodicType.equals("FY")) {
	    	
	    	b1=serviceMarginFinancialYear.getActualData(m_strCurrencySymbolFormat,staticIntCurrentDate,staticIntCurrentMonthNumber,m_strStatPeriodicType,m_strStatQarter,m_strStatPFY);
	    	
	    }else if(m_strStatPeriodicType.equals("")==false && m_strStatPeriodicType!=null  && m_strStatPeriodicType.equals("YTD")) {
	    	
	    	b1=serMarginYearTillDate.getActualData(m_strCurrencySymbolFormat,staticIntCurrentDate,n_intTillMonthYTD,m_strStatPeriodicType,m_strStatQarter,m_strStatYTD);
	    	
	    }else if(m_strStatPeriodicType.equals("")==false && m_strStatPeriodicType!=null  && m_strStatPeriodicType.equals("custom")) {
	    	
	    	b1=serMarginYearTillDate.getActualData(m_strCurrencySymbolFormat,staticIntCurrentDate,n_intTillMonthYTD,m_strStatPeriodicType,m_strStatQarter,m_strStatYTD);
	    }	
		
	    InputStreamResource file=new InputStreamResource(b1);
		
		  ResponseEntity<Resource> body=ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName+"")
	                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
	                .body(file);
		  
		  return body;
	}

    


}
