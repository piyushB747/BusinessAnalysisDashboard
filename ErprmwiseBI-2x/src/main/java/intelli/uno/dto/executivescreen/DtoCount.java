package intelli.uno.dto.executivescreen;

import lombok.Data;

@Data
public class DtoCount {

	public String year;

	public int month;

	public int open;

	public int techclosed;

	public int paused;

	public int cancelled;
	
	public int sla;
	
	public int allCalledLogs;
	
	public String viewRequestParam;
	
	public String uniqueId;
	
    public String l_int;
    
    public float present;
    public float absent;
    public float onleave;
    public float holiday;
}
