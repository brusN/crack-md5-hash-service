package ru.nsu.brusn.lab1.mapper;

import ru.nsu.brusn.lab1.exception.mapper.ObjectMapException;

public interface IMapper<T, V> {
    V map(T obj) throws ObjectMapException;
}
