package ewhamenu.com.demo.service;

import ewhamenu.com.demo.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    @Autowired
    private MenuRepository menuRepository;

}
