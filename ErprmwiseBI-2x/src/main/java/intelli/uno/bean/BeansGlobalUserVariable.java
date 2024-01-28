package intelli.uno.bean;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class BeansGlobalUserVariable {
	private String variable;

	private String m_strUserId;

	private String m_strLoginId;

	private String m_strStateSmIm;

	private String m_strMultilocation;

	private String m_strBusinessUnit;

	private String m_strMultiPoplocation;

	private String m_strFname;

	private String m_strLname;

	private String m_strFullName;
    
	private List<Integer> l_intBusinessUnit;
	
	private String m_strRoleId;
	private String m_strRoleName;
	private String m_strRegion;
	private String m_strMultiProductCategory;

}
