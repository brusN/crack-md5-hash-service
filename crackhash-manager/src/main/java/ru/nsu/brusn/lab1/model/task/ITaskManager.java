package ru.nsu.brusn.lab1.model.task;

public interface ITaskManager {
    String addNewTask(String hash, Integer maxLength);
    CrackHashTask getTask(String guid);
}
