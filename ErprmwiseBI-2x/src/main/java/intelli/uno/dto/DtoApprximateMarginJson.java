package intelli.uno.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DtoApprximateMarginJson {
    private String id;
    private String name;
    private String position;
    private String salary;
    private String start_date;
    private String office;
    private String extn;
}
