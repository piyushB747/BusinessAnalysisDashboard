package intelli.uno.dto.margin.realtime;

import lombok.Data;

@Data
public class DtoJsonEngRealTimeMargin {

	
	public String m_strEngName;
	
	public String m_strEngSalary;
	
	public String m_strEngTotalTickets;
	public Integer n_intEngTotalTickets;
	
	public String m_strAvgCostPerTicketsForEng;
	
	public String m_strNoOfClaimForEng;
	
	public String m_strClaimApprovedTotalTicket;
	
	public String m_strClaimTotalApprovedAmount;
	public Integer n_IntClaimTotalApprovedAmount;
	
	public String sparesConsumedCostOfEng;
	public Integer n_intSparesConsumedCostOfEng;
	
	
}
