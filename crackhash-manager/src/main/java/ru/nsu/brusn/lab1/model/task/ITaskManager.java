package ru.nsu.brusn.lab1.model.task;

import ru.nsu.brusn.lab1.exception.ManagerApiException;

import java.util.Optional;
import java.util.UUID;

public interface ITaskManager {
    UUID addNewTask(String hash, Integer maxLength) throws ManagerApiException;
    Optional<CrackHashTaskDescriptor> getTask(String guid);
}
