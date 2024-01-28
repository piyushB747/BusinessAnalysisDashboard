package intelli.uno.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="categories_new")
public class EntityResearchCategories {

	@Id 
	private Long id;
	
	private String title;
	
	private String description;
	
	private String coverImage;
}
