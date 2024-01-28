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
@Table(name="rolemst_rm")
public class EntityRoleMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="typeid_rm", columnDefinition = "int(11)")
	private Long typeidRm;

	@Column(name="typevalue_rm")
	private String typevalueRm;
	
	@Column(name="deleteflag_rm")
	private String deleteflagRm;

	public EntityRoleMaster(String typevalueRm, String deleteflagRm) {
		super();
		this.typevalueRm = typevalueRm;
		this.deleteflagRm = deleteflagRm;
	}
	
	
	
}
