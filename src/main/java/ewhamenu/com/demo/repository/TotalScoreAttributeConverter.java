package ewhamenu.com.demo.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ewhamenu.com.demo.domain.TotalScore;

import javax.persistence.AttributeConverter;
import java.io.IOException;


public class TotalScoreAttributeConverter implements AttributeConverter<TotalScore, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );

    @Override
    public String convertToDatabaseColumn(TotalScore totalScore) {
        try{
            return objectMapper.writeValueAsString(totalScore);
        }catch(JsonProcessingException e){
            throw new IllegalArgumentException("fail to serialize as object into Json");
        }
    }

    @Override
    public TotalScore convertToEntityAttribute(String dbData) {
        try{
            return objectMapper.readValue(dbData, TotalScore.class);
        }catch (IOException e){
            throw new IllegalArgumentException("fail to serialize as Json into Object");
        }
    }

}
