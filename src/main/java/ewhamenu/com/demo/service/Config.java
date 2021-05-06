package ewhamenu.com.demo.service;

import ewhamenu.com.demo.repository.DietRepository;
import ewhamenu.com.demo.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    private final DietRepository dietRepository;
    private final MenuRepository menuRepository;

    @Autowired
    public Config(DietRepository dietRepository, MenuRepository menuRepository) {
        this.dietRepository = dietRepository;
        this.menuRepository = menuRepository;
    }




}
