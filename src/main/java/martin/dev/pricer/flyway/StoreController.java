package martin.dev.pricer.flyway;

import martin.dev.pricer.flyway.model.Store;
import martin.dev.pricer.flyway.repository.StoreRepositoryFlyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StoreController {

    @Autowired
    private StoreRepositoryFlyway storeRepository;

    @RequestMapping(path = "/customers/", method = RequestMethod.GET)
    public List<Store> getCustomers() {
        return storeRepository.findAll();
    }
}
