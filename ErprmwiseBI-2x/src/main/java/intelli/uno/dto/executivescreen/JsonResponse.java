package intelli.uno.dto.executivescreen;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JsonResponse {
   
	private List<DtoJsonEngineer> Progress_Data;
    private List<DtoApprximateMarginJson> approximateMarginJson;
    
    
    public JsonResponse(List<DtoJsonEngineer> progressData) {
        this.Progress_Data = progressData;
    }


    
    
}