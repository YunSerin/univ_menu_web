package ewhamenu.com.demo.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ewhamenu.com.demo.domain.TotalReview;

import javax.persistence.AttributeConverter;
import java.io.IOException;


public class TotalreviewAttributeConverter implements AttributeConverter<TotalReview, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );

    @Override
    public String convertToDatabaseColumn(TotalReview totalreview) {
        try{
            return objectMapper.writeValueAsString(totalreview);
        }catch(JsonProcessingException e){
            throw new IllegalArgumentException("error log ...");
        }
    }

    @Override
    public TotalReview convertToEntityAttribute(String dbData) {
        try{
            return objectMapper.readValue(dbData, TotalReview.class);
        }catch (IOException e){
            throw new IllegalArgumentException("error log ...");
        }
    }

}
