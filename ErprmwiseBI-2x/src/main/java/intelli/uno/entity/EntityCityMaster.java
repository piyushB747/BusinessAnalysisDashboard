package intelli.uno.entity;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedNativeQuery(
	     name = "EntityCitymst_cm.getAllCity",
	   query = "select distinct(typevalue_cm) as first from citymst_cm where deleteflag_cm='N' and "
	     		+ "typevalue_cm is not null "
	     		+ "and typevalue_cm!='' and stateid_sm_cm =:stateid_sm_cm "
	     		+ "and typevalue_cm NOT IN (:typevalue_cm) "
	     		+ "order by if(typevalue_cm rlike '^[a-z]',1,2), typevalue_cm",
	      resultSetMapping = "Mapping.EntityCitymst_cm")			

@SqlResultSetMapping(name = "Mapping.EntityCitymst_cm",
classes = @ConstructorResult(targetClass = EntityCityMaster.class,
              columns = {@ColumnResult(name = "first",type = String.class)
              })
)

@Entity
@Table(name="citymst_cm")
public class EntityCityMaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="typeid_cm")
	private Long typeidCm;
	
	@Column(name="typevalue_cm")
	private String typevalueCm;
	
	@Column(name="stateid_sm_cm")
	private String stateidSmCm;

	@Column(name="deleteflag_cm")
	private String deleteFlagCm;

	@Column(name="poplocation_lm_cm")
	private String poplocationLmCm;

	public EntityCityMaster(String typevalueCm, String stateidSmCm, String deleteFlagCm, String poplocationLmCm) {
		super();
		this.typevalueCm = typevalueCm;
		this.stateidSmCm = stateidSmCm;
		this.deleteFlagCm = deleteFlagCm;
		this.poplocationLmCm = poplocationLmCm;
	}


	
	
	
}
