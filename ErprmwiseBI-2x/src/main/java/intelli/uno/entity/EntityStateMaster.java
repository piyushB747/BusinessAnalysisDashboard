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
@Table(name="statemst_sm")
public class EntityStateMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "typeid_sm") 
	private Long typeidSm;
	
	@Column(name="typevalue_sm")
	private String typevalueSm;
	
	@Column(name="deleteflag_sm") 
	private String deleteFlagSm;

    @Column(name="region_rm_sm")
	private String regionRmSm;
	

	
}
