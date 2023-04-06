package ru.nsu.brusn.lab1.model.task;

import java.util.Optional;
import java.util.UUID;

public interface ITaskManager {
    UUID addNewTask(String hash, Integer maxLength);
    Optional<CrackHashTaskDescriptor> getTask(String guid);
}
