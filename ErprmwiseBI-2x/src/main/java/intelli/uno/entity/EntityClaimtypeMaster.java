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
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="claimtypemst_ctm")
public class EntityClaimtypeMaster {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="typeid_ctm")
	private Long typeidCtm;
	
	@Column(name="typevalue_ctm")
	private String typevalueCtm;
	
	@Column(name="deleteflag_ctm")
	private String deleteflagCtm;
}
