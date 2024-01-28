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

///**Getting Business Unit from NamedNativeQuery**/
//@NamedNativeQuery(
//	     name = "EntityBusinessunitmst_bum.getAllBusinessUnit",
//	     query = "select bs.typevalue_bum as first from businessunitmst_bum bs where deleteflag_bum='N' "
//	     		+ "and typevalue_bum is not null and typevalue_bum!='' order by typevalue_bum asc;",
//	      resultSetMapping = "Mapping.Businessunitmst_bumDto"
//	      )
//	@SqlResultSetMapping(name = "Mapping.Businessunitmst_bumDto",
//	classes = @ConstructorResult(targetClass = Dto_Businessunitmst_bum.class,
//	                             columns = {@ColumnResult(name = "first",type = String.class)
//	                             })
//	)
///**End Of Getting Business Unit from NamedNativeQuery**/

@Entity
@Table(name="businessunitmst_bum")
public class EntityBusinessunitMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="typeid_bum")
	private Long typeidBum;
	
	@Column(name="typevalue_bum")
	private String typevalueBum;
	
	@Column(name="deleteflag_bum")
	private char deleteflagBum;

	public EntityBusinessunitMaster(String typevalueBum, char deleteflagBum) {
		super();
		this.typevalueBum = typevalueBum;
		this.deleteflagBum = deleteflagBum;
	}


	
	
}