package intelli.uno.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="incidentlogtimemst")
public class EntityincidentlogtimeMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="incidentid")	
	private Long incidentId;
	
	@Column(name="technicianclosedate")
	@Type(type="date")
	private Date techniciancloseDate;
	
	@Column(name="technicianclosetime")
	private String techniciancloseTime;

	public EntityincidentlogtimeMaster(Date techniciancloseDate, String techniciancloseTime) {
		super();
		this.techniciancloseDate = techniciancloseDate;
		this.techniciancloseTime = techniciancloseTime;
	}

	
	
}
