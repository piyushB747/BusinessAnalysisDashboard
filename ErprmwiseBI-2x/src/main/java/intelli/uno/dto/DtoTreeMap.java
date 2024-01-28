package intelli.uno.dto;
import java.util.List;

import lombok.Data;
@Data
public class DtoTreeMap {
	
	    private List<String> xaxisCategories;
	    private List<String> yaxisCategories;
	    private List<DataPoint> data;
	    private String m_strTitle;

}
