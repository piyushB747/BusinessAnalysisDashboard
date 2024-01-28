package intelli.uno.entity;


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="engineermst_em")
public class EntityEngineerMaster {
  	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="typeid_em")
	private Long typeidEm;
	
	@Column(name="loginid_em")
	private String loginidEm;
	
	@Column(name="role_rm_em")
	private String roleRmEm;
	
	@Column(name="poplocation_em")
	private String poplocationEm;
	
	@Column(name="multipoplocation_pm_em")
	private String multipoplocationPmEm;
	
	@Column(name="region_em")
	private String regionEm;
	
	@Column(name="city_cm_em")
	private String cityCmEm;
	
	@Column(name="fname_em")
	private String fnameEm;
	
	@Column(name="lname_em")
	private String lnameEm;
    	
	@Column(name="state_sm_em")
	private String stateSmEm;
	
	@Column(name="multilocation_mm_em")
	private String multilocationMmEm;

	@Column(name="resignedflag_em")
	private String resignedFlagEm;
	
	@Column(name="deleteflag_em")
	private String deleteFlagEm;
	
	@Column(name="typeid_bum_em")
	private String typeidBumEm;
	
	@Column(name="salary_em")
	private String salaryEm;
	
	@Column(name="newpassword_em")
	private String newPasswordEm;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "engineer_roles_er",
         joinColumns = {
        	    	@JoinColumn(name = "user_id_er",columnDefinition = "int(10)",
                    referencedColumnName = "typeid_em")
                },
         inverseJoinColumns = {
        		 @JoinColumn(name = "role_id_er",columnDefinition = "int(11)",
        				 referencedColumnName ="typeid_rm" )
        		 })
    private Set<EntityRoleMaster> roles = new HashSet<>();
    

	
}
