package intelli.uno.dto;

import java.util.List;

import lombok.Data;

@Data
public class JsonResponseRealTimeApproximation {
	
	private String m_strCustomerName;
	
	private String m_strTotalTicketsForCustomer;
	
	private String m_strContractValueOfCustomer;
	
	private String m_strDblcostPerTicketTotal;
	
	private String m_strMargin;
	
	private String m_strPercentage;
	
	private String m_strTotalClaimTicketsForCustomer;	
	
	private String m_strTotalClaimApprovedTicketForCustomer;
	
	private String m_strTotalClaimApprovedAmountForCustomer;
	
	private String m_strTotalClaimPendingTicketsForCustomer;
	
	private String m_strTotalClaimPendingAmountForCustomer;
	
	private String m_strTotalAvgCostOfEngForCustomer;
	
	private String m_strClaimTypeMost;
	
	private String m_strSparesConsumedCostForCustomer;

	private String m_strIndexNo;
	
	private String m_strIndexProfitLossBoth;
	
	private String n_intTotalSparedTicketForCustomer;
	
	private List<DtoJsonRealTimeMarginApproximation> approximateMarginJson;
	
	
}
