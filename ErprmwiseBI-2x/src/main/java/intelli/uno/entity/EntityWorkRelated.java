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
@Table(name = "workrelated_wr")
public class EntityWorkRelated {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_wr")
	private Long idWr;

	@Column(name = "taskdesc_wr", columnDefinition = "LONGTEXT")
	private String taskDescWr;

	@Column(name = "taskname_wr", length = 255) // Specify an appropriate length
	private String taskNameWr;

}
