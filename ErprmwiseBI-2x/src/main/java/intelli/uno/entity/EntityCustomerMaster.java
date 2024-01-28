package intelli.uno.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="customermst_cm")
public class EntityCustomerMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="typeid_cm")
	private Long typeidCm;
	
	@Column(name="customername_cm")
	private String customernameCm;
	
	@Column(name="createdby_cm")
	private String createdbyCm;
	
	@Column(name="createddate_cm")
	@Type(type="date")
	private String createddateCm;
	
	@Column(name="createdtime_cm")
	private String createdtimeCm;
	
	@Column(name="deleteflag_cm")
	private String deleteflagCm;
	
	@Column(name="businessunit_bum_cm")
	private String businessunitbumCm;
	
	@Column(name="principalcustomer_pcm_cm")
	private String principalcustomerpcmCm;
}
