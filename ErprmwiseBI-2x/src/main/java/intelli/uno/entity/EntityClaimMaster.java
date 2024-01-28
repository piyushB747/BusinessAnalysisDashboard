package intelli.uno.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="claimmst_cm")
public class EntityClaimMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="typeid_cm")
	private Long typeidClaimCm;
	
	@Column(name="approvedflag_cm")
	private String approvedflagCm;
	
	@Column(name="substatus_cm")
	private String substatusCm;
	
	@Column(name="approvedamount_cm")
	private String approvedamountCm;

	@Column(name="claimedby_em_cm")
	private String claimedbyEmCm;
	
	@Column(name="deleteflag_cm")
	private String deleteFlagCm;
}

