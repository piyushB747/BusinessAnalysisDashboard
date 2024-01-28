package intelli.uno.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class DtoUserVariable {
	private String m_strBusinessUnit;
	private String m_strState;
	private String m_strPoplocation;
	private String m_strUserId;
	private String m_strUserName;
	private String m_strMultiPoplocation;
	private String m_strMultiStates;
	private String m_strRoleId;
	private String m_strRoleName;
	private String m_strRegion;
	private String m_strMultiProductCategory;
	private List<Integer> l_intBusinessUnit;
}
