package intelli.uno.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import intelli.uno.repository.RepositoryEntityRoleMaster;
@SpringBootTest
class EntityRoleMasterTest {

	@Autowired
	private RepositoryEntityRoleMaster repoEntityRoleMaster;
	
	@Test
	public void testToSaveRole() {
		EntityRoleMaster r1 = new EntityRoleMaster();
		r1.setTypevalueRm("SD");
		r1.setDeleteflagRm("N");
		
		repoEntityRoleMaster.save(r1);
		fail("Not yet implemented");
	}

}
