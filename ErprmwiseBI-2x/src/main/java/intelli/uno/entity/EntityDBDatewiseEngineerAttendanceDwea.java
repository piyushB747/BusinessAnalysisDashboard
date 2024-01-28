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
@Table(name="db_datewiseengineerattendance_dwea")
public class EntityDBDatewiseEngineerAttendanceDwea {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="typeid_dwea")
	private Long typeidDwea;
	
	@Column(name="present_dwea")
	private String presentDwea;
	
	@Column(name="absent_dwea")
	private String absentDwea;
	
	@Column(name="onleave_dwea")
	private String onleaveDwea;
	
	@Column(name="holiday_dwea")
	private String holidayDwea;
	
	@Column(name="weekoff_dwea")
	private String weekoffDwea;
	
	@Column(name="year_dwea")
	private String yearDwea;
	
	@Column(name="month_dwea")
	private String monthDwea;
	
	@Column(name="bu_dwea")
	private String buDwea;

	@Column(name="role_dwea")
	private String roleDwea;
	
	@Column(name="cast")
	private Double castDwea;
	
	@Column(name="sum")
	private Double sumDwea;
	
	@Column(name="present")
	private Double presentDoDwea;

	public EntityDBDatewiseEngineerAttendanceDwea(String presentDwea, String absentDwea, String onleaveDwea,
			String holidayDwea, String weekoffDwea, String yearDwea, String monthDwea, String buDwea, String roleDwea,
			Double castDwea, Double sumDwea, Double presentDoDwea) {
		super();
		this.presentDwea = presentDwea;
		this.absentDwea = absentDwea;
		this.onleaveDwea = onleaveDwea;
		this.holidayDwea = holidayDwea;
		this.weekoffDwea = weekoffDwea;
		this.yearDwea = yearDwea;
		this.monthDwea = monthDwea;
		this.buDwea = buDwea;
		this.roleDwea = roleDwea;
		this.castDwea = castDwea;
		this.sumDwea = sumDwea;
		this.presentDoDwea = presentDoDwea;
	}
	
}
