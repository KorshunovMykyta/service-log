package servicelog.service;

import servicelog.model.ShuffleRequest;

public interface LogService {
    void logRequest(ShuffleRequest shuffleRequest);
}
