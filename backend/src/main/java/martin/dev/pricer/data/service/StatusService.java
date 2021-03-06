package martin.dev.pricer.data.service;

import martin.dev.pricer.data.model.Status;
import martin.dev.pricer.data.repository.StatusRepository;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public Status findStatusByStatus(String status) {
        return this.statusRepository.findStatusByStatus(status);
    }
}
