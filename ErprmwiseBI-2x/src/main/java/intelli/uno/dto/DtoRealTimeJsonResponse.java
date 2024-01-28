package intelli.uno.dto;


//import java.util.List;

import lombok.Data;
//import net.unopoint.dtorealtime.JsonResponseRealTime;
@Data
public class DtoRealTimeJsonResponse {

	
	public Integer totalContractValueAvg;
	
	private String pageNo;
	
	private String pageSize;
	
	private Integer n_intTotalPages;
	
	private String pageinationBoolean;
	
	//public List<JsonResponseRealTime> jsonResponse;

	//List<DtoTableHeader> dtoTableHeaderList;
}
