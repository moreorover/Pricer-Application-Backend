package martin.dev.pricer.data.request;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ParseDto {
    private long days;
    private long hours;
    private long minutes;
}
