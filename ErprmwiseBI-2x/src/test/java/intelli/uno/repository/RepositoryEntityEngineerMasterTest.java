package intelli.uno.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import intelli.uno.entity.EntityEngineerMaster;

@SpringBootTest
class RepositoryEntityEngineerMasterTest {

	@Autowired
	private RepositoryEntityEngineerMaster repo;
	
	@Test
	public void testToSaveEngineer() {
		EntityEngineerMaster e1 = new  EntityEngineerMaster();
		e1.setFnameEm("Piyush");
		e1.setLnameEm("Singh");
		e1.setDeleteFlagEm("N");
		e1.setLoginidEm("piyush747");
		e1.setRegionEm("1");
		
		repo.save(e1);
		fail("Not yet implemented");
	}

}
