package intelli.uno.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="customerasset_cad")
public class EntityCustomerassetcadMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_cad")
	private Long idCad;

	@Column(name="CATEGORY")
	private String categroyCad;
	
	@Column(name="HCILSLA")
	private String hcilSlaCad;
	
	@Column(name="KSYSSLA")
	private String kysSlaCad;
	
	@Column(name="VSAT")
	private String vsatCad;
	
	@Column(name="POPLocation")
	private String popLocationCad;
	
	@Column(name="Engr")
	private String engrCad;
	
	@Column(name="Engineername")
	private String engineernameCad;

	@Column(name="Remarks")
	private String remarksCad;
	
	@Column(name="CustomerAddress")
	private String customerAddressCad;
	
	@Column(name="AssetID")
	private String assetIdCad;
	
	@Column(name="City")
	private String cityCad;
	
	@Column(name="State")
	private String stateCad;

	@Column(name="AverageTravelTime")
	private String averageTravelTimeCad;
	
}
