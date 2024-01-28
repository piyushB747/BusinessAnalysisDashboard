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
@Table(name="locationmst_lm")
public class EntityLocationMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="typeid_lm")
	private Long typeidLm;
	
	@Column(name="typevalue_lm")
	private String typevalueLm;

	@Column(name="lat_lm")
	private String latLm;
	
	@Column(name="lon_lm")
	private String lonLm;
	
	@Column(name="address_lm")
	private String addressLm;
	
	@Column(name="state_sm_lm")
	private String stateSmLm;

	@Column(name="businessunit_bum_lm")
	private int businessunitBumLm;
	
	@Column(name="sequenceno_lm")
	private int sequencenoLm;
	
	@Column(name="deleteflag_lm")
	private String deleteFlagLm;

	public EntityLocationMaster(String typevalueLm, String latLm, String lonLm, String addressLm, String stateSmLm,
			int businessunitBumLm, int sequencenoLm, String deleteFlagLm) {
		super();
		this.typevalueLm = typevalueLm;
		this.latLm = latLm;
		this.lonLm = lonLm;
		this.addressLm = addressLm;
		this.stateSmLm = stateSmLm;
		this.businessunitBumLm = businessunitBumLm;
		this.sequencenoLm = sequencenoLm;
		this.deleteFlagLm = deleteFlagLm;
	}


	
}
