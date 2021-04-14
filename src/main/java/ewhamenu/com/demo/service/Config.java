package ewhamenu.com.demo.service;

import ewhamenu.com.demo.repository.DietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    private final DietRepository dietRepository;

    @Autowired
    public Config(DietRepository dietRepository) {
        this.dietRepository = dietRepository;
    }



}
