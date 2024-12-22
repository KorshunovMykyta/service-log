package servicelog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import servicelog.model.ShuffleRequest;
import servicelog.service.LogService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/log")
public class LogController {

    private final LogService logService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity logRequest(@RequestBody  ShuffleRequest request) throws InterruptedException {
        // todo: add request validation
        log.info("Received shuffled list: {}", request.shuffledArray());
        logService.logRequest(request);
        return ResponseEntity.ok(request);
    }

}

