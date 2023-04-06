package ru.nsu.brusn.lab1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.brusn.lab1.model.dto.request.CreateCrackHashTaskRequest;
import ru.nsu.brusn.lab1.model.dto.request.CrackHashWorkerRequest;
import ru.nsu.brusn.lab1.model.dto.response.CreateCrackHashTaskResponse;
import ru.nsu.brusn.lab1.model.dto.response.GetCrackHashTaskStatusResponse;
import ru.nsu.brusn.lab1.model.dto.response.MessageResponse;
import ru.nsu.brusn.lab1.service.CrackHashService;

@RestController
@RequestMapping("/api/hash")
public class CrackHashController {
    private final CrackHashService crackHashService;

    public CrackHashController(CrackHashService crackHashService) {
        this.crackHashService = crackHashService;
    }

    @PostMapping("/crack")
    public ResponseEntity<CreateCrackHashTaskResponse> crackHash(@RequestBody CreateCrackHashTaskRequest request) {
        return crackHashService.crackHash(request);
    }

    @PostMapping("/update")
    public ResponseEntity<MessageResponse> updateTaskStatus(@RequestBody CrackHashWorkerRequest request) {
        return crackHashService.updateTaskStatus(request);
    }

    @GetMapping("/status")
    public ResponseEntity<GetCrackHashTaskStatusResponse> getStatus(@RequestParam String requestId) {
        return crackHashService.getStatus(requestId);
    }
}
