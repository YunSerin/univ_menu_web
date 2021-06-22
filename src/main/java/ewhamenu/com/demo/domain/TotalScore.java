package ewhamenu.com.demo.domain;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.*;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TotalScore {
//    private String menu1;
//    private String menu2;
//    private String menu3;
//    private String menu4;
//    private String menu5;
    Map<String, String> rates = new LinkedHashMap<>();
//    @JsonAnySetter
//    public void setStar(String key, int value){
//        details.put(key, value);
//    }


}
