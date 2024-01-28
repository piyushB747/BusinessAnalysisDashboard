package intelli.uno.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table(name="statusmst_sm")
public class EntityStatusMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="typeid_sm")
	private Long typeidSm;
	
	@Column(name="typevalue_sm")
	private String typevalueSm;
	
	@Column(name="deleteflag_sm")
	private String deleteflagSm;
	
}
