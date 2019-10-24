package martin.dev.pricer.web.controller.store;

import martin.dev.pricer.data.model.store.Store;
import martin.dev.pricer.data.services.store.StoreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {

    private StoreRepository storeRepository;

    public StoreController(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @GetMapping
    public ResponseEntity<List<Store>> getItems() {
        List<Store> stores = storeRepository.findAll();
        return new ResponseEntity<>(stores, HttpStatus.OK);
    }
}
