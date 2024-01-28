package intelli.uno.dto;

import lombok.Data;
@Data
public class DtoJsonRealTimeMarginApproximation {

	private String m_strEngName;
	
	private String m_strEngTotalTickets;
	
	private String m_strEngSallery;
	
	private String m_strAvgCostPerTicket;
	
	private String m_strClaimsTotalTicketByEng;
	
	private String m_strClaimApprovedTotalTicket;
	
	private String m_strClaimAmmountTotal;
	
	private String m_strClaimPendingTickets;
	
	private String m_strClaimPendingAmmounts;
	
	private String m_strClaimType;
	
	private String m_strSpareConsumedTotalTickets;
	
	private String m_strSpareConsumedCost;
	
	private String m_strSpareRequestPendingTickets;
	
	private String SpareRequestPendingAmounts;
	
	//private List<DtoClaim> dtoClaimAmount;
	
}
