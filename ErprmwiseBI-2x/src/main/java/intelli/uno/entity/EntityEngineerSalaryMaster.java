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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="engineersalarymaster_esm")
public class EntityEngineerSalaryMaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_esm")
	private Long idEsm;
	
	@Column(name="engid_esm")
	private String engIdEsm;
	
	@Column(name="avgticketcost_esm")
	private String avgTicketCostEsm;
	
	@Column(name="engsalary_esm")
	private String engsalaryEsm;
	
	@Column(name="date_esm")
	private String dateEsm;
	
	@Column(name="month_esm")
	private String monthEsm;
	
	@Column(name="year_esm")
	private String yearEsm;
	
	@Column(name="deleteflag_esm")
	private String deleteflagEsm;
	
	@Column(name="totaltickets_esm")
	private String totalTicketsEsm;
	
	@Column(name="totaltickets_clsoed")
	private String totalticketsClosedEsm;
	
	@Column(name="slaviolatedtotal_esm")
    private String slaViolatedTotalEsm;
	
	@Column(name="totalClaimTickets_esm")
	private String totaClaimTicketsEsm;
	
	@Column(name="totalClaimCost_esm")
	private String totalClaimCostEsm;
	
	@Column(name="sparesconsumedtotal_esm")
	private String sparesConsumedTotalEsm;
	
	@Column(name="sparesconsumedtotalTickets_esm")
	private String sparesConsumedTotalTicketEsm;
	
	@CreationTimestamp
	@Column(name="createdatetime_esm")
	private LocalDateTime createdatetimeEsm;
	
	@UpdateTimestamp
	@Column(name="changedatetime_esm")
	private LocalDateTime changedDatedEsm;
	
}
