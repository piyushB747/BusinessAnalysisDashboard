package intelli.uno.dto.margin.realtime;

import java.util.List;

import lombok.Data;

@Data
public class JsonResponseRealTime {

	
	public Integer n_intSrNo;
	
	public String m_strPcName;
	
	public String m_strCustomerName;
	
	public String customerContractValue;
	public Integer n_IntCustomerContractValueTotal;
	
	public String  m_strTotalTicketsForCustomer;
	public Integer n_intTotalTicketsForCustomer;
	
	public String totalClaimApprovedAmountCustomer;
	public Integer n_intTotalClaimApprovedAmountCustomer;
	
	public String spareConsumedCostForCustomer;
	public Integer n_intSpareConsumedCostForCustomer;
	
	public String avgTicketCostOfEng;
	public Integer n_IntAvgTicketCostOfEng;
	
	public String CostOfOperation;
	public Integer n_IntCostOfOperation;
	
	public String  m_strYTDCost;
	public Integer n_IntYTDCost;
	
	public String avgPerMonth;
	public Integer n_IntAvgPerMonth;
	
	public String EoyMargin;
	public Integer n_EoyMargin;
	

	public String profitExpected;
	public Integer n_intProfitExpected;
	
	public String m_strPercentageOverall;
	public Double dbl_PercentageOverall;
	
	public List<DtoJsonEngRealTimeMargin> l_objEngList; 
	
	public String m_strIndexShown;
}
