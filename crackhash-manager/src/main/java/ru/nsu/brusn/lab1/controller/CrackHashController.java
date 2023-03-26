package ru.nsu.brusn.lab1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.brusn.lab1.model.dto.request.CreateCrackHashTaskRequest;
import ru.nsu.brusn.lab1.service.CrackHashService;

@RestController
@RequestMapping("/api/hash")
public class CrackHashController {
    private final CrackHashService crackHashService;

    public CrackHashController(CrackHashService crackHashService) {
        this.crackHashService = crackHashService;
    }

    @PostMapping("/crack")
    public ResponseEntity<Object> crackHash(@RequestBody CreateCrackHashTaskRequest request) {
        var response = crackHashService.crackHash(request);
        return response;
    }
}
