package intelli.uno.dto.margin.realtime;

import java.util.List;

import lombok.Data;


@Data
public class DtoRealTimeJsonResponse {

public Integer totalContractValueAvg;
	
	private String pageNo;
	
	private String pageSize;
	
	private Integer n_intTotalPages;
	
	private String pageinationBoolean;
	
	public List<JsonResponseRealTime> jsonResponse;

	List<DtoTableHeader> dtoTableHeaderList;
}
