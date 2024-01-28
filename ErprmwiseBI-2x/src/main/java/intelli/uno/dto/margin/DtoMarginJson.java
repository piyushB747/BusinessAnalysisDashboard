package intelli.uno.dto.margin;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DtoMarginJson {

	private String dtoPrincipalCustomerName;
	
	private String dtoContractValueTotal;
	
	private String dtoMarginTotal;
	
	private String dtoTotalTickets;
	
	private String pageNo;
	
	private String pageSize;
	
	private Integer n_intTotalPages;
	
	private String pageinationBoolean;
	
	private String filterBy;
	
	private String m_strCurrencyType;
	
	List<DtoTableRow> tableData = new ArrayList<>();
	
	List<DtoTableHeader> tableRowHead=new ArrayList<>();
	
}
