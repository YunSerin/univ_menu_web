package ewhamenu.com.demo.service;

import ewhamenu.com.demo.repository.DietRepository;
import ewhamenu.com.demo.repository.MenuRepository;
import ewhamenu.com.demo.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    private final DietRepository dietRepository;
    private final MenuRepository menuRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public Config(DietRepository dietRepository, MenuRepository menuRepository, ReviewRepository reviewRepository) {
        this.dietRepository = dietRepository;
        this.menuRepository = menuRepository;
        this.reviewRepository = reviewRepository;
    }





}
