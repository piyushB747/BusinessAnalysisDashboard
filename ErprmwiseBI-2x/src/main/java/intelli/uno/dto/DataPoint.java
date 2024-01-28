package intelli.uno.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DataPoint {
	private int xIndex;
    private int yIndex;
    private int value;
}
