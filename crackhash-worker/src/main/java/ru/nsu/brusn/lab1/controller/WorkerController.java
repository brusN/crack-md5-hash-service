package ru.nsu.brusn.lab1.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.brusn.lab1.service.WorkerService;
import ru.nsu.ccfit.schema.crack_hash_request.CrackHashManagerRequest;

@RestController
@RequestMapping("/internal/api/worker/hash/crack")
public class WorkerController {
    private final WorkerService workerService;

    public WorkerController(WorkerService service) {
        this.workerService = service;
    }
    @PostMapping("/task")
    public ResponseEntity<String> runTask(@RequestBody CrackHashManagerRequest request) {
        return workerService.crackHash(request);
    }
}
