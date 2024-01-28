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
@Table(name="incidentmaster")
public class EntityIncidentMaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="typeid_im")
	private Long typeidIm;
	
	@Column(name="inctype_itm_im")
	private Integer inctypeItmIm;
	
	@Column(name="incdate_im")
	private String incdateIm;
	
	@Column(name="inctime_im")
	private String inctimeIm;
	
	@Column(name="engineer_engm_im")
	private Integer engineerEngmIm;
	
	@Column(name="slatype_stm_im")
	private Integer slatypeStmIm;
	
	@Column(name="status_sm_im")
	private Integer statusSmIm;
	
	@Column(name="deleteflag_im")
	private String deleteFlagIm;
	
	@Column(name="businessunit_bum_im")
	private Integer businessunitbumIm;
	
	@Column(name="principal_cm_im")
	private Integer principalCmIm;
	
	@Column(name="customer_cm_im")
	private String customerCmIm;
	
	@Column(name="sparesconsumed_im")
	private String sparesconsumedIm;
	
	@Column(name="resolutionflag_im")
	private String resolutionflagIm;
	
	@Column(name="region_im")
	private String regionRmIm;
	
	@Column(name="state_sm_im")
	private String stateSmIm;
	
	@Column(name="location_lm_im")
	private String popLocationIm;
	
	@Column(name="city_cm_im")
	private String cityIm;
	
}
