package intelli.uno.service.entityengineermaster;
import intelli.uno.entity.EntityEngineerMaster;
import org.springframework.security.core.userdetails.UserDetailsService;
public interface ServiceEntityEngineerMaster extends UserDetailsService{

	public abstract void updateExistingEngineer(Long typeidIm);
	
	public abstract void updateExistingEngineerWithNewPassword(Long typeidIm,String password);
	
	public abstract EntityEngineerMaster loadUserByUsername2(String username,String password);
	
}
