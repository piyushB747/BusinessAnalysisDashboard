package intelli.uno.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="productcategorymst_pcm")
public class EntityProductcategoryMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="typeid_pcm")
	private Long typeidPcm;
	
	@Column(name="deleteflag_pcm")
	private String deleteflagPcm;

	@Column(name="typevalue_pcm")
	private String typevaluePcm;
	
	@Column(name="businessunit_pcm")
	private String businessunitPcm;
	
}
