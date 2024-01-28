package intelli.uno.bean;


import java.util.List;

import org.springframework.stereotype.Component;

import intelli.uno.dto.margin.DtoTableBodyData;
import intelli.uno.dto.margin.DtoTableRow;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class BeanDirectMargin {

    private List<DtoTableRow> l_strTable;
	
	private List<DtoTableBodyData> l_strTableBody;
	 
	private String m_strQueryToDownload;
	
	private String m_strCurrency;
	
	private String m_strCurrencyType;
	
	private String m_strTillMonthDivisionNumber;
	
}
