package intelli.uno.dto.margin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoChartData {
	   private String[] categories;
	   private DtoSeries[] series;

}
