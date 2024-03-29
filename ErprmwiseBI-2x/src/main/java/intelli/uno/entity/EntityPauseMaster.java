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
@Table(name="pausemst_pm")
public class EntityPauseMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="typeid_pm")
	private Long typeidPm;
	
	@Column(name="incidentid_pm")
	private String incidentidPm;
	
	@Column(name="pausestartdate_pm")
	private String pausestartdatePm;
	
		
}

