package intelli.uno.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import intelli.uno.repository.RepositoryEntityContractDetailsnewMaster;

@SpringBootTest
class EntityContractDetailsnewMasterTest {
	
	@Autowired
	private RepositoryEntityContractDetailsnewMaster entityContractDetailsnewMaster;

	@Test
	public void testToFetchRate() {
	    String x=entityContractDetailsnewMaster.returnContractValueFromCustomerIdAndYear("6","2022","1","2022","12");	
	    System.out.println(x+" PIYUSH");
	}
	
	
	public void testToSaveContractDetails() {
		
		EntityContractDetailsnewMaster yourObject = new EntityContractDetailsnewMaster();
        yourObject.setCustomeridCdnm("6");
        
        
        yourObject.setRateCdnm("50000");
        yourObject.setAmountpaid("30000");
        yourObject.setRemainsAmount("20000");
        yourObject.setTotalAmount("50000");
        
        yourObject.setDeleteflagCdnm("N");
        yourObject.setContractrefrencenoCdnm("wsfsd");
        
        yourObject.setStartdate_cdnm("2022-01-01");
        yourObject.setEnddate_cdnm("2022-12-31");
        yourObject.setContractstartdateCdnm("2022-01-01");
        yourObject.setContractenddateCdnm("2022-12-31");
        yourObject.setContractenddateCdnm("2022-01-01");
        
        String dateString = "2022-01-01";
        LocalDate parsedDate = LocalDate.parse(dateString);
        yourObject.setStartDate(parsedDate);
        
        String EndString = "2022-12-31";
        LocalDate parsedDateEndString = LocalDate.parse(EndString);
        yourObject.setEndDate(parsedDateEndString);
        
        yourObject.setBillingfrequencyCdnm("Yearly");
        yourObject.setBillingStarted("2022-01-15");
        yourObject.setYearlyMonthly("Yearly");
        
        yourObject.setDeleteflagCdnm("N");
        yourObject.setContractActiveCdnm("Y");
  
        
        ///-------/
        
		EntityContractDetailsnewMaster yourObject2 = new EntityContractDetailsnewMaster();
        yourObject2.setCustomeridCdnm("8");
        
        
        yourObject2.setRateCdnm("50000");
        yourObject2.setAmountpaid("30000");
        yourObject2.setRemainsAmount("20000");
        yourObject2.setTotalAmount("50000");
        
        yourObject2.setDeleteflagCdnm("N");
        yourObject2.setContractrefrencenoCdnm("wsfsd");
        
        yourObject2.setStartdate_cdnm("2022-01-01");
        yourObject2.setEnddate_cdnm("2022-12-31");
        yourObject2.setContractstartdateCdnm("2022-01-01");
        yourObject2.setContractenddateCdnm("2022-12-31");
        yourObject2.setContractenddateCdnm("2022-01-01");
        
        String dateString2 = "2022-01-01";
        LocalDate parsedDate2 = LocalDate.parse(dateString2);
        yourObject2.setStartDate(parsedDate2);
        
        String EndString2 = "2022-12-31";
        LocalDate parsedDateEndString2 = LocalDate.parse(EndString2);
        yourObject.setEndDate(parsedDateEndString2);
        
        yourObject2.setBillingfrequencyCdnm("Yearly");
        yourObject2.setBillingStarted("2022-01-15");
        yourObject2.setYearlyMonthly("Yearly");
        
        yourObject2.setDeleteflagCdnm("N");
        yourObject2.setContractActiveCdnm("Y");
        
        

        
      List<EntityContractDetailsnewMaster> l1=new ArrayList<EntityContractDetailsnewMaster>();        
        l1.add(yourObject2);
        l1.add(yourObject);
        entityContractDetailsnewMaster.saveAll(l1);
		fail("Not yet implemented");
	}

}
