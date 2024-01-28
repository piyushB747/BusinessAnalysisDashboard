package intelli.uno.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name="contractdetailsnewmst_cdnm")
public class EntityContractDetailsnewMaster {

	/*
	 
	Select typeid_cdnm,start_date,end_date,
	creation_time_stamp,customerid_cm_cdnm,rate_cdnm, total_amount,contractrefrenceno_cdnm,
	 startdate_cdnm, enddate_cdnm, contractenddate_cdnm,contractvalue_cdnm,contractcost_cdnm,monthlyamount,
	 amountpaid,remains_amount,yearly_monthly,terminationdate_cdnm,terminationreason_cdnm,contract_active_cdnm
	from contractdetailsnewmst_cdnm where deleteflag_cdnm='N' order by typeid_cdnm desc;

	 
	 */
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="typeid_cdnm")
	private Long idCdnm;
	
	@Column(name="contractrefrenceno_cdnm")
    private String contractrefrencenoCdnm;	
	
	@Column(name="customerid_cm_cdnm")
	private String customeridCdnm;
	
	@Column(name="rate_cdnm")
	private String rateCdnm;
	
	@Column(name="deleteflag_cdnm")
	private String deleteflagCdnm;
	
	@Column(name="contractstartdate_cdnm")
	private String contractstartdateCdnm;
	
	@Column(name="contractenddate_cdnm")
	private String contractenddateCdnm;
	
    @Column(name="contractdate_cdnm")
	private String contractdate_cdnm;
	
	@Column(name="billingfrequency_cdnm")
	private String billingfrequencyCdnm;
	
	@Column(name="billingstarted")
	private String billingStarted;
	
	@Column(name="startdate_cdnm")
	private String startdate_cdnm;
	
	@Column(name="enddate_cdnm")
	private String enddate_cdnm;
	
    private String yearlyMonthly;
    
    private String monthlyamount;
    
    private String amountpaid;
    
    private String remainsAmount;
    
    private String totalAmount;
    
    @CreationTimestamp
    private LocalDateTime creationTimeStamp;
    
    @UpdateTimestamp
    private LocalDateTime updationTimeStamp;
    
    @Column(name="start_date")
    private LocalDate startDate;
    
    @Column(name="end_date")
    private LocalDate endDate;
    
    @Column(name="terminationdate_cdnm")
    private String terminationdateCdnm;
    
    @Column(name="terminationreason_cdnm")
    private String terminationreasonCdnm;
    
    @Column(name="contract_active_cdnm")
    private String contractActiveCdnm; 
    
    
   
}
