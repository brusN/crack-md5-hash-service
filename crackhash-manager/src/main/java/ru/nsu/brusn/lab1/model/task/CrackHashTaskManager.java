package ru.nsu.brusn.lab1.model.task;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CrackHashTaskManager implements ITaskManager {
    private final Map<UUID, CrackHashTask> tasks;

    public CrackHashTaskManager() {
        tasks = new HashMap<>();
    }
    @Override
    public String addNewTask(String hash, Integer maxLength) {
        var guid = java.util.UUID.randomUUID();
        tasks.put(guid, new CrackHashTask(hash, maxLength));
        return String.valueOf(guid);
    }

    @Override
    public CrackHashTask getTask(String guid) {
        return tasks.get(UUID.fromString(guid));
    }
}
