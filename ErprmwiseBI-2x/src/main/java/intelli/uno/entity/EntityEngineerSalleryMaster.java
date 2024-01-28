package intelli.uno.entity;

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
@Table(name="engineersalarymst_esm")
public class EntityEngineerSalleryMaster {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="engsallery_id")
	private Long engSalleryId;

	@Column(name="engid_esm")
	private String engIdEsm;
	
	@Column(name="engsallery_esm")
	private String engSalleryEsm;
	
	@Column(name="oldsalary_esm")
	private String oldSalaryEsm;
	
	@Column(name="date_esm")
	private String dateEsm;
	
	@Column(name="month_esm")
	private String monthEsm;
	
	@Column(name="year_esm")
	private String yearEsm;
	
	@Column(name="totaltickets_esm")
	private String totalTicketsEsm;
	
	@Column(name="totaltickets_clsoed")
	private String totalticketsClosedEsm;

	@Column(name="totalticketsyear_esm")
	private String totalTicketsOfYearEsm;
	
	@Column(name="totalticketsyear_clsoed")
	private String totalticketsOfYearClosedEsm;

	@Column(name="slaviolatedtotal_esm")
    private String slaViolatedTotalEsm;
	
	@Column(name="slaviolatedYeartotal_esm")
    private String slaViolatedYearTotalEsm;

	@Column(name="avgticketcost_esm")
	private String avgTicketCostEsm;
	
	@Column(name="avgticketyearcost_esm")
	private String avgTicketyearCostEsm;
	
	@Column(name="totalClaimTickets_esm")
	private String totaClaimTicketsEsm;
	
	@Column(name="totalClaimCost_esm")
	private String totalClaimCostEsm;
	
	@Column(name="deleteflag_esm")
	private String deleteflagEsm;
	
	@CreationTimestamp
	@Column(name="createdatetime_esm")
	private LocalDateTime createdatetimeEsm;
	
	@UpdateTimestamp
	@Column(name="changedatetime_esm")
	private LocalDateTime changedDatedEsm;
	
	@Column(name="totalticketnewsalary_esm")
	private String totalTicketsAfterNewSalaryEsm;
	
	@Column(name="totalticketsnewsalary_clsoed")
	private String totalticketsClosedNewSalary;
	
	@Column(name="avgticketcostnewsalary_esm")
	private String avgTicketCostNewSalaryEsm;
	
	@Column(name="salarychangedate_esm")
	private String salaryChangeDateEsm;
	
	@Column(name="salarychangeflage_esm")
	private String salaryChangeFlagEsm;
	
	@Column(name="avgticketcostbeforenewsalary_esm")
	private String avgTicketCostBeforeNewSalaryEsm;
	
	@Column(name="totalclosedticketbeforesalarychange_esm")
	private String totalClosedTicketBeforeSalaryChange;

	@Column(name="totalticketbeforesalarychange_esm")
	private String totalTicketBeforeSalaryChange;
	
}
