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
@Table(name="principalcustomermst_pcm")
public class EntityPrincipalcustomerMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="typeid_pcm")
	private Long typeidPcm;

	@Column(name="deleteflag_pcm")
	private String deleteflagPcm;
	
	@Column(name="customername_pcm")
	private String customernamePcm;
	
	@Column(name="businessunitid_bum_pcm")
	private String businessunitidbumPcm;
	
}
