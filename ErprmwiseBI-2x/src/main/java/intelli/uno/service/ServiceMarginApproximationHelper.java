package intelli.uno.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import intelli.uno.dto.margin.DtoTableHeader;
import intelli.uno.repositorycustom.CustomRepostitory;

@Service
public class ServiceMarginApproximationHelper {

	@Autowired
	private CustomRepostitory customRepoEntityIncidentMaster;
	
	
	
	/**RETURNING TOTAL PAGES START**/
	   public int returnTotalCountsRecords(String m_strQuery,String m_strPageSize) {
		   int n_intPageSize=Integer.parseInt(m_strPageSize);
		   String m_strSubQueryCounts="SELECT COUNT(*) AS total_rows "
		   		+ "FROM (  "+m_strQuery+"  ) AS subquery"; 
		   int totalRecords=customRepoEntityIncidentMaster.getCountsParameterizedNativeQuery(m_strSubQueryCounts);
		   return (int) Math.ceil((double) totalRecords / n_intPageSize);
	  }
	/**RETURNING TOTAL PAGES ENDS**/
	   
	
	
	/**RETURN TABLE HEADER FROM HERE*/
	public List<DtoTableHeader> returnTableHeaders(String headerYTD,String headerAVGMonth){
		List<DtoTableHeader> tableRow=new ArrayList<>();
		tableRow.add(new DtoTableHeader("SrNo"));
		tableRow.add(new DtoTableHeader("PrinCustomer Name"));
		tableRow.add(new DtoTableHeader("Customer Name"));
		tableRow.add(new DtoTableHeader("Total Tickets"));
		tableRow.add(new DtoTableHeader("Contract Value (a)"));
		tableRow.add(new DtoTableHeader(headerYTD+" (b)"));
		tableRow.add(new DtoTableHeader(headerAVGMonth+" (c)"));
		tableRow.add(new DtoTableHeader("EOY (D=C*12)"));
		tableRow.add(new DtoTableHeader("Profit Expected"));
		tableRow.add(new DtoTableHeader("Percentage %"));
		tableRow.add(new DtoTableHeader("Index"));
		
		return tableRow;
	}
	
}
