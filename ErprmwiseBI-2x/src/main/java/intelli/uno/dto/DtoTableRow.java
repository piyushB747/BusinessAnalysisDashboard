package intelli.uno.dto;

import lombok.Data;

@Data
public class DtoTableRow {
  
	private String m_intSrNoIndex;
	private String principleCustomerName;
    private String customerName;
    private String totalTickets;
    private String contractValue;
    private String estimatedCostOfService;
    private String ytdMargin;
    private String avgRemainsMonth;
    private String eoyMargin;
    private String profitExpected;
    private String percentage;
    private String index;

    private Integer n_intMarginDto;
    private Integer n_intContractValueDto;
    private Integer n_intMarginForSorting;
    private Integer n_intYtdMarginDto;
    private Integer n_intAvgRemainsMonthDto;
    private Integer n_intEoyMarginDto;
    private Integer n_intProfitExpected;
    private Integer n_intTotalTicketsDto;
}
