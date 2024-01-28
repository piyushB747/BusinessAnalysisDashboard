package intelli.uno.service.entityengineermaster;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import intelli.uno.commons.CommonConstants;
import intelli.uno.entity.EntityEngineerMaster;
import intelli.uno.entity.EntityRoleMaster;
import intelli.uno.repository.RepositoryEntityEngineerMaster;


@Service
public class ServiceimplEntityEngineerMaster implements ServiceEntityEngineerMaster{

	@Autowired
	private RepositoryEntityEngineerMaster repositoryEntityEngineerMaster;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	

	@Autowired
	private HttpSession httpSession;
   
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        EntityEngineerMaster entityEngineerMaster=repositoryEntityEngineerMaster.findByLoginidEm(username);
        if(entityEngineerMaster==null) {
			throw new UsernameNotFoundException("Invalid Email And Password");	
        }else {
        	System.out.println(CommonConstants.purple + " GOING TO LOGIN BY USERNAME:= "+username+"  "   +CommonConstants.reset);
        	httpSession.setAttribute("userinfo", entityEngineerMaster);  // Create session and store session information	
        }
		return new org.springframework.security.core.userdetails.User(entityEngineerMaster.getLoginidEm(),
				entityEngineerMaster.getNewPasswordEm(),mapRolesToAuthorities(entityEngineerMaster.getRoles()));
	}

	public Set<GrantedAuthority> mapRolesToAuthorities(Set<EntityRoleMaster> roles) {
	    Set<GrantedAuthority> authorities = new HashSet<>();
	    for (EntityRoleMaster role : roles) {
	        authorities.add(new SimpleGrantedAuthority(role.getTypevalueRm()));
	    }
	    return authorities;
	}
	

	@Override
	@Transactional
	public void updateExistingEngineer(Long typeidIm) {
	  Optional<EntityEngineerMaster> e1=repositoryEntityEngineerMaster.findById(typeidIm);
	    if (e1.isPresent()) { 
		  EntityEngineerMaster entityEngineerMst=e1.get();
		  System.out.println("YOUR ENGINEER "+entityEngineerMst);
		  entityEngineerMst.setNewPasswordEm(passwordEncoder.encode("admin"));
		  try { 
			  
		  repositoryEntityEngineerMaster.save(entityEngineerMst);
		  }catch(Exception ex) { ex.printStackTrace(); }
	   }else{ throw new UsernameNotFoundException("Invalid UserName And Password"); }
	}

	@Override
	public void updateExistingEngineerWithNewPassword(Long typeidIm, String password) {
		 Optional<EntityEngineerMaster> e1=repositoryEntityEngineerMaster.findById(typeidIm);
		    if (e1.isPresent()) { 
			  EntityEngineerMaster entityEngineerMst=e1.get();
			  entityEngineerMst.setNewPasswordEm(passwordEncoder.encode(password));
			  try {
			    repositoryEntityEngineerMaster.save(entityEngineerMst);
			  }catch(Exception ex) { ex.printStackTrace(); }
		   }else{ throw new UsernameNotFoundException("Invalid UserName And Password"); }
	}

	@Override
	public EntityEngineerMaster loadUserByUsername2(String username, String password) {
		   EntityEngineerMaster entityEngineerMaster=repositoryEntityEngineerMaster.findByLoginidEm(username);
	        if(entityEngineerMaster==null) {
				throw new UsernameNotFoundException("Invalid Email And Password");	
	        }else {
	        	System.out.println(CommonConstants.purple + " GOING TO LOGIN BY USERNAME:= "+username+"  "   +CommonConstants.reset);
	        	httpSession.setAttribute("userinfo", entityEngineerMaster);  // Create session and store session information	
	        }
	        return entityEngineerMaster;
	}

	

	
}


