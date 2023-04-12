package ru.nsu.brusn.lab1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.brusn.lab1.model.dto.response.CrackHashManagerResponse;
import ru.nsu.brusn.lab1.model.worker.CrackHashManagerRequest;
import ru.nsu.brusn.lab1.service.WorkerService;

@RestController
@RequestMapping("/internal/api/worker/hash/crack")
public class WorkerController {
    private final WorkerService workerService;

    public WorkerController(WorkerService service) {
        this.workerService = service;
    }
    @PostMapping("/task")
    public ResponseEntity<CrackHashManagerResponse> runTask(@RequestBody CrackHashManagerRequest request) {
        return workerService.crackHash(request);
    }
}
