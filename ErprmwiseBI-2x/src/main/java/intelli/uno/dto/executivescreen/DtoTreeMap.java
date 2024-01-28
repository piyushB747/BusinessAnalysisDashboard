package intelli.uno.dto.executivescreen;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoTreeMap {

    private List<String> xaxisCategories;
    private List<String> yaxisCategories;
    private List<DataPoint> data;
    private String m_strTitle;

}
