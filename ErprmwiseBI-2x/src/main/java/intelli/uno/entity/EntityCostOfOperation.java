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
@Table(name="costofoperationmst_coo")
public class EntityCostOfOperation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_coo")
	private Long idCoo;
	
	private String totalContractValueCoo;
	
	private String operationCostCoo;
	
	private Integer n_IntTotalContractValueCoo;
	
	private Integer n_IntOperationContractValueCoo;
	
	private String deleteflag_coo;
}
