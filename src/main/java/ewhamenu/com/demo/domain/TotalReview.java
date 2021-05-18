package ewhamenu.com.demo.domain;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.LinkedHashMap;
import java.util.Map;

public class TotalReview {
    Map<String, Integer> details = new LinkedHashMap<>();

    @JsonAnySetter
    void setMenu(String key, int value){
        details.put(key, value);
    }
}
