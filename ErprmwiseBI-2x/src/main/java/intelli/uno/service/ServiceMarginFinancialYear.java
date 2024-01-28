package intelli.uno.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import intelli.uno.bean.BeanDirectMargin;
import intelli.uno.commons.CommonUtils;
import intelli.uno.dto.margin.DtoTableRow;
import intelli.uno.repository.RepositoryEntityContractDetailsnewMaster;
import intelli.uno.repository.RepositoryEntityCustomerMaster;
import intelli.uno.repository.RepositoryEntityPrincipalcustomerMaster;
import intelli.uno.repositorycustom.CustomRepostitory;

@Service
@Component
public class ServiceMarginFinancialYear {

	public static String[] HEADERS= {
			"Sr.No. ",
			"Principle Customer",
			"Customer Name",
			"Total Tickets",
			"Contract Value",
			"Estimated Cost Of Service",
			"YTD Margin",
			"Avg Of Remaining Months",
			"Eoy Margin",
			"Profit Expected",
			"Percentage",
			"Index"
	};
	
	public static String SHEET_NAME="category_date";
	
	@Autowired
	private CustomRepostitory customRepoEntityIncidentMaster;
	
	@Autowired
	private RepositoryEntityPrincipalcustomerMaster repositoryEntityPrincipalcustomerMaster;

	@Autowired
	private RepositoryEntityCustomerMaster repositoryEntityCustomerMaster;

	@Autowired
	private RepositoryEntityContractDetailsnewMaster repositoryEntityContractDetailsnewMaster;

	@Autowired
	private BeanDirectMargin beanApproximationMargin;
	

	public  List<DtoTableRow> perFormIteration(List<Object[]> l1,String m_strCurrencySymbolFormat,
			int staticIntCurrentDate,int staticIntCurrentMonthNumber,String m_strPeriodictype,String m_strStatQarter,String m_strPFY){
		List<DtoTableRow> tableData = new ArrayList<>();
		
		int n_Intmonths=12;
		if(m_strPeriodictype.equals("FY")) {
 	       if(m_strStatQarter.equals("All")==false) {   n_Intmonths=3;  }
		}
		System.out.println("Contract Year= "+m_strPFY+" Year  For Division="+n_Intmonths);
		
		/*Development For Contract Value Find*/
		int hyphenIndex = m_strPFY.indexOf("-");
		String part1StartYear = m_strPFY.substring(0, hyphenIndex); //   2022
		String part2EndYear = m_strPFY.substring(hyphenIndex + 1); //  2023
		
		 int n_intSrNoIndex=1;
		 for(Object[] m_strRmData : l1) {
			   DtoTableRow row1 = new DtoTableRow();
			   
			    String m_strPc="";
			    String m_strCust="";
			    try {
			    	
			    	System.out.println("Cust-ID= "+String.valueOf(m_strRmData[0])+ "PrincipalId= "+String.valueOf(m_strRmData[1]));
			    	/**PRINCIPAL CUSTOMER NAME START**/
			    	try {
				         //m_strPc=repositoryEntityPrincipalcustomerMaster.returnValueForId(Long.valueOf(String.valueOf(m_strRmData[0])),"N");
				         m_strPc=CommonUtils.nullToBlank(repositoryEntityPrincipalcustomerMaster.returnValueForId(Long.valueOf(String.valueOf(m_strRmData[0])),"N"), false);
				        
			    	}catch(Exception ex) { m_strPc="NULL"; }
			    	row1.setPrincipleCustomerName(m_strPc+"");
			    	/**PRINCIPAL CUSTOMER NAME ENDS**/
			    	
			    	try {
			            //m_strCust=repositoryEntityCustomerMaster.returnValueForId(Long.valueOf(String.valueOf(m_strRmData[1])), "N");
			            m_strCust=CommonUtils.nullToBlank(repositoryEntityCustomerMaster.returnValueForId(Long.valueOf(String.valueOf(m_strRmData[1])), "N"), false);
			    	}catch(Exception ex) { m_strCust="-|-";  }
			    	row1.setCustomerName(m_strCust+"");
			    	
			    	try {
			    	    row1.setN_intTotalTicketsDto(Integer.parseInt(String.valueOf(m_strRmData[2])));
			    	}catch(Exception ex) { ex.printStackTrace(); };
				    row1.setTotalTickets( String.valueOf(m_strRmData[2]));
			    	
				    /**CONTRACT VALUE START**/
			        String m_intContractValue="";
			        try {
			          //  m_intContractValue=repositoryEntityContractDetailsnewMaster.returnContractValueFromCustomerId(String.valueOf(m_strRmData[1]));
			        	m_intContractValue=repositoryEntityContractDetailsnewMaster.returnContractValueFromCustomerIdAndYearOnly(String.valueOf(m_strRmData[1]),part1StartYear,part2EndYear);
			        	if(m_intContractValue==null || m_intContractValue.equals("") || m_intContractValue.equals("0")) {
			        		m_intContractValue=repositoryEntityContractDetailsnewMaster.returnContractValueFromCustomerIdAndOneStartYearParam(String.valueOf(m_strRmData[1]),part1StartYear);
			        		if(m_intContractValue==null || m_intContractValue.equals("") || m_intContractValue.equals("0")) {
				        		m_intContractValue=repositoryEntityContractDetailsnewMaster.returnContractValueFromCustomerIdAndOneEndYearParam(String.valueOf(m_strRmData[1]),part2EndYear);
				        	}
			        	}
			        }catch(Exception ex) { m_intContractValue="0"; }
			        
			        if(m_intContractValue!=null && m_intContractValue.equals("")==false) {    
			        	m_intContractValue=m_intContractValue.replaceAll("\\s", "");
			        }else {
			        	m_intContractValue="0";
			        }
			        row1.setContractValue(""+m_intContractValue);
			        /**CONTRACT VALUE ENDS**/
			        
			        /**ESTIMATED COST OF SERVICE**/
			        int n_IntEstimateCostOfService=0;
			        try {
			            n_IntEstimateCostOfService=Integer.parseInt(String.valueOf(m_strRmData[3]));
			        }catch(Exception ex) {ex.printStackTrace(); }
			        row1.setEstimatedCostOfService(""+n_IntEstimateCostOfService);
			        /**ESTIMATED COST OF SERVICE**/
			        
			        
			        /**YTD  Margin  **/
			        double n_intContractValue=0;
			        try {
			        	 n_intContractValue=Double.parseDouble(m_intContractValue.replaceAll(",", ""));
			        	 row1.setN_intYtdMarginDto(n_IntEstimateCostOfService);
			        	 row1.setN_intContractValueDto((int) Math.round(n_intContractValue));
			        }catch(Exception ex) { ex.printStackTrace();  }
			          row1.setYtdMargin(""+n_IntEstimateCostOfService);
			        /**YTD Margin **/
			          
			          	/**AVG OF REMAINS MONTH **/
			           
			          // double dblRemainMonthAvg=0;
			           double dblAveragePerMonth=0;
			 
			           dblAveragePerMonth=(double)n_IntEstimateCostOfService/n_Intmonths;
			           row1.setAvgRemainsMonth(""+dblAveragePerMonth);
			           
			           /*Stopped on 4 Novemeber 2023 By Piyush Because of the average Mistake;
				        try {
				        	if(staticIntCurrentDate>15) {
				        		int remainMonth=12-staticIntCurrentMonthNumber;
				        		dblRemainMonthAvg=(double)n_IntEstimateCostOfService/remainMonth;
				        	}else {
				        		int month=staticIntCurrentMonthNumber-1;
				        		int remainMonth=12-month;
				        		dblRemainMonthAvg=(double)n_IntEstimateCostOfService/remainMonth;
				        	}
				        	
				        	row1.setAvgRemainsMonth(""+dblRemainMonthAvg);
				        }catch(Exception ex) { ex.printStackTrace();  }
				        */
				        /**AVG OF REMAINS MONTH **/
			          
					        /**EOY  Margin  **/
					        double dbl_EoyMargin=0;
					        try {
					        	dbl_EoyMargin=dblAveragePerMonth*12;
					        }catch(Exception ex) { ex.printStackTrace();  }
					          row1.setEoyMargin(""+dbl_EoyMargin);
					          row1.setN_intEoyMarginDto((int)dbl_EoyMargin);
					        /**EOY Margin **/
					          
				          /**Profit Expected  Margin  **/
					        double dbl_ProfitExpected=0;
					        try {
					        	if(dbl_EoyMargin<0){
					        		dbl_ProfitExpected=n_intContractValue+dbl_EoyMargin;	
					        	}else {
					        		dbl_ProfitExpected=n_intContractValue-dbl_EoyMargin;	
					        	}
					        }catch(Exception ex) { ex.printStackTrace();  }
					          row1.setProfitExpected(""+dbl_ProfitExpected);
					          row1.setN_intProfitExpected((int)dbl_ProfitExpected);
					       /**Profit Expected Margin **/    
				        
			         /** CALCULATE THE PERCENTAGE START**/ 
			         try {
					         double percentage = (double) dbl_ProfitExpected/n_intContractValue; // Calculate the percentage
					         double d_blPercentage=(double)percentage*100;
					         if (Double.isInfinite(d_blPercentage)) {
					        	 row1.setPercentage("0.0%");
					         }else {
					        	 row1.setPercentage(d_blPercentage+"%");	 
					         }
						     percentage=0;
				      }catch(Exception ex) { ex.printStackTrace();
						   double percentage=0; row1.setPercentage(percentage+"%"); percentage=0;
				      }  
			         /** CALCULATE THE PERCENTAGE ENDS**/
			         
			        /**SET INDEX START**/ 
                     if (dbl_ProfitExpected < 0) {
                     	row1.setIndex("IN_LOSS");
                     } else if (dbl_ProfitExpected == 0) {
                     	row1.setIndex("IN_BOTH");
                     } else if(dbl_ProfitExpected >0) {
                     	row1.setIndex("IN_PROFIT");
                     }
                     /**SET INDEX ENDS**/
                     
                     /** SET SERIAL NO START**/
			         row1.setM_intSrNoIndex(""+n_intSrNoIndex);
                     n_intSrNoIndex++;
                     /** SET SERIAL NO ENDS**/
                     
                     tableData.add(row1);
				     row1=null;	
				     
			    }catch(Exception ex) { ex.printStackTrace();   }
			  
		 }
		return tableData;
	}  
	
	
	public List<DtoTableRow> returnListByFinancialYear(String m_strPeriodictype, String m_strFromDate,
			String m_strToDate, Integer n_intAverageCost, int m_strCurrentYear, Currency m_strCurrencySymbol,
			String m_strQarter, String m_strPageSize, String m_strPageNo, String m_strFilterBy, String m_strPFY) {
		
		return null;
	}


    public ByteArrayInputStream getActualData(String m_strCurrencySymbolFormat, int staticIntCurrentDate, int staticIntCurrentMonthNumber,
    		String m_strStatPeriodicType,String m_strStatQarter,String m_strStatPFY) throws IOException {
		String m_strQuery="",m_strPFY=m_strStatPFY;
		
		m_strQuery=this.beanApproximationMargin.getM_strQueryToDownload();
		
		//String m_strCurrencySymbol=this.beanApproximationMargin.getM_strCurrency();
		List<DtoTableRow> tableData = new ArrayList<>();	
		List<Object[]> l1=customRepoEntityIncidentMaster.returnListOfEngineerAttendance(m_strQuery);
		
		if(l1.isEmpty()==false) {
	    	tableData=this.perFormIteration(l1,m_strCurrencySymbolFormat,staticIntCurrentDate,staticIntCurrentMonthNumber,m_strStatPeriodicType,m_strStatQarter,m_strPFY);
	    }
		
		ByteArrayInputStream b1=dataToExcel(tableData);
		return b1;
	}
    
    public static ByteArrayInputStream dataToExcel(List<DtoTableRow> l_objListCategory) throws IOException {
		Workbook workBook=new XSSFWorkbook();
		ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
		try {
         
			Sheet sheet=workBook.createSheet(SHEET_NAME);
         // Create row
         Row row = sheet.createRow(0);
         CellStyle style = workBook.createCellStyle();
         Font font = workBook.createFont();
         font.setBold(true); // Set the font to bold
         style.setFont(font);

         for (int i = 0; i < HEADERS.length; i++) {
             Cell cell = row.createCell(i);
             cell.setCellValue(HEADERS[i]);
             cell.setCellStyle(style); // Apply the bold style to the cell
         }

         // Auto-size the columns
         for (int i = 0; i < HEADERS.length; i++) {
             sheet.autoSizeColumn(i);
         }
            
            //VALUES ROW
            int rowIndex=0;
            for(DtoTableRow c:l_objListCategory) {
            	rowIndex++;
            	Row dataRow=sheet.createRow(rowIndex);
            	dataRow.createCell(0).setCellValue(c.getM_intSrNoIndex());
            	dataRow.createCell(1).setCellValue(c.getPrincipleCustomerName());
            	dataRow.createCell(2).setCellValue(c.getCustomerName());
            	dataRow.createCell(3).setCellValue(c.getTotalTickets());
            	dataRow.createCell(4).setCellValue(c.getContractValue());
            	dataRow.createCell(5).setCellValue(c.getEstimatedCostOfService());
            	dataRow.createCell(6).setCellValue(c.getYtdMargin());
            	dataRow.createCell(7).setCellValue(c.getAvgRemainsMonth());
            	dataRow.createCell(8).setCellValue(c.getEoyMargin());
            	dataRow.createCell(9).setCellValue(c.getProfitExpected());
            	dataRow.createCell(10).setCellValue(c.getPercentage());
            	dataRow.createCell(11).setCellValue(c.getIndex());
            	
            }
			
            workBook.write(byteArrayOutputStream);
			
           
		}catch(IOException ex) {ex.printStackTrace(); 
		   System.out.println("Fail To Import Data");
		}finally {
            workBook.close();
            byteArrayOutputStream.close();
		}
		 return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
	}

}
