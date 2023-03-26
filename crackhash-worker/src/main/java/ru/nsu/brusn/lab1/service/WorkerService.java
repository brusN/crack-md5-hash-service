package ru.nsu.brusn.lab1.service;

import org.springframework.stereotype.Service;
import ru.nsu.ccfit.schema.crack_hash_request.CrackHashManagerRequest;

import java.util.Optional;

@Service
public class WorkerService {
    private int getPartSize(int alphabetSize, int partCount, int partNumber) {
        int overflowWorkersCount = alphabetSize % partCount;
        int countLiteralsForWorker = alphabetSize / partCount;
        if (partNumber < overflowWorkersCount)
            ++countLiteralsForWorker;
        return countLiteralsForWorker;
    }

    public Optional<String> crackHashByPartNumber(CrackHashManagerRequest request) {
        var partCount = request.getPartCount();
        var partNumber = request.getPartNumber();
        var alphabet = request.getAlphabet();



        for (int i = 0)
        return null;
    }
}
