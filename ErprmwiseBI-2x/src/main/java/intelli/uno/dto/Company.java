package intelli.uno.dto;

import lombok.Data;

@Data
public class Company {
	 private String company;
	 private String m_strTotalTicketsForCustomer;
	 private String m_strContractValueOfCustomer;
	    private Double stockPrice;
	    private Double[] quarterlySales;
	    private String priceSuffix;

	    
	    
	    public Company(String company, Double stockPrice, Double[] quarterlySales, String priceSuffix) {
	        this.company = company;
	        this.stockPrice = stockPrice;
	        this.quarterlySales = quarterlySales;
	        this.priceSuffix = priceSuffix;
	    }



		public Company(String company, String m_strTotalTicketsForCustomer, String m_strContractValueOfCustomer,
				Double stockPrice, Double[] quarterlySales, String priceSuffix) {
			super();
			this.company = company;
			this.m_strTotalTicketsForCustomer = m_strTotalTicketsForCustomer;
			this.m_strContractValueOfCustomer = m_strContractValueOfCustomer;
			this.stockPrice = stockPrice;
			this.quarterlySales = quarterlySales;
			this.priceSuffix = priceSuffix;
		}

}
