package martin.dev.pricer.web.controller.store;

import martin.dev.pricer.data.model.store.Store;
import martin.dev.pricer.data.model.store.StoreUrl;
import martin.dev.pricer.data.services.store.StoreRepository;
import martin.dev.pricer.data.services.store.StoreUrlRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/store_url")
public class StoreUrlController {

    private StoreUrlRepository storeUrlRepository;
    private StoreRepository storeRepository;

    public StoreUrlController(StoreUrlRepository storeUrlRepository, StoreRepository storeRepository) {
        this.storeUrlRepository = storeUrlRepository;
        this.storeRepository = storeRepository;
    }

    @GetMapping
    public ResponseEntity<List<StoreUrl>> getItems() {
        List<StoreUrl> storeUrls = storeUrlRepository.findAll();
        return new ResponseEntity<>(storeUrls, HttpStatus.OK);
    }

    @GetMapping("/findByStore")
    public ResponseEntity<List<StoreUrl>> findByStore(@RequestParam long id) {
        Store store = storeRepository.getOne(id);
        List<StoreUrl> storeUrls = storeUrlRepository.findAllByStore(store);
        return new ResponseEntity<>(storeUrls, HttpStatus.OK);
    }
}
