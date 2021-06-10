package ewhamenu.com.demo.domain;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class TotalScore {
    private int menu1;
    private int menu2;
    private int menu3;
//    Map<String, Integer> details = new LinkedHashMap<>();

//    @JsonAnySetter
//    public void setStar(String key, int value){
//        details.put(key, value);
//    }


}
