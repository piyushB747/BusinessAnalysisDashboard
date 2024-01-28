package intelli.uno.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DtoAPIDirectMargin {
     
	private String pageNo;
	
	private String pageSize;
	
	private Integer n_intTotalPages;
	
	private String pageinationBoolean;
	
	List<DtoTableBodyData> tableData = new ArrayList<>();
	
	List<DtoTableHeader> tableRowHead=new ArrayList<>();
}
