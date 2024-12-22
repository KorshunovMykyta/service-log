package servicelog.service;

import org.springframework.stereotype.Service;
import servicelog.model.ShuffleRequest;

@Service
public class LogServiceImpl implements LogService {

    public void logRequest(ShuffleRequest shuffleRequest) {
        System.out.println(shuffleRequest);
    }
}
