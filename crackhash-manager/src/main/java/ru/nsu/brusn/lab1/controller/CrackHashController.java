package ru.nsu.brusn.lab1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.brusn.lab1.exception.ManagerApiException;
import ru.nsu.brusn.lab1.model.dto.request.task.CreateCrackHashTaskRequest;
import ru.nsu.brusn.lab1.model.dto.response.common.ResponseWrapper;
import ru.nsu.brusn.lab1.model.dto.response.task.CreateCrackHashTaskResponse;
import ru.nsu.brusn.lab1.model.dto.response.task.GetCrackHashTaskStatusResponse;
import ru.nsu.brusn.lab1.service.CrackHashService;

@RestController
@RequestMapping("/api/hash")
public class CrackHashController {
    private final CrackHashService crackHashService;

    public CrackHashController(CrackHashService crackHashService) {
        this.crackHashService = crackHashService;
    }

    @PostMapping("/crack")
    public ResponseWrapper<ResponseEntity<CreateCrackHashTaskResponse>> crackHash(@RequestBody CreateCrackHashTaskRequest request) throws ManagerApiException {
        return new ResponseWrapper<>(crackHashService.crackHash(request));
    }

    @GetMapping("/status")
    public ResponseWrapper<ResponseEntity<GetCrackHashTaskStatusResponse>> getStatus(@RequestParam String requestId) {
        return new ResponseWrapper<>(crackHashService.getStatus(requestId));
    }
}
