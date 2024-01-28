package intelli.uno.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DtoApproxColumnNegativePrincipalCustomer {

	private String dtoPrincipalCustomerName;
	
	private String dtoContractValueTotal;
	
	private String dtoMarginTotal;
	
	private String dtoTotalTickets;
	
	private String pageNo;
	
	private String pageSize;
	
	private Integer n_intTotalPages;
	
	private String pageinationBoolean;
	
	private String filterBy;
	
	List<DtoTableRow> tableData = new ArrayList<>();
	
	List<DtoTableHeader> tableRowHead=new ArrayList<>();
	
}
