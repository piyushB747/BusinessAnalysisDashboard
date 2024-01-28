package intelli.uno.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DtoTableBodyData {
	
	private String m_intSrNoIndex;
	
	private String principleCustomerName;
	
	private String customerName;
	
    private String totalTickets;
	private Integer n_intTotalTicketsDto;

	private String contractValue;
	private Long n_DoubleContractValue;
	
	private String ytdMargin;
	private Long n_DoubleYtdMargin;
    
	private String remainMonthAvg;
	private Long n_LongAvgPerMonth;
	
	private String eoyMargin;
	private Long n_LongEoyMargin;
	
	private String m_strProfitExpected;
	private Long n_LongProfitExpected;
	private Integer n_IntegerProfitExpected;
	
	private String m_strPercentage;
	private Double dbl_OverAllPercentage;
	
	private String index;
	
}
