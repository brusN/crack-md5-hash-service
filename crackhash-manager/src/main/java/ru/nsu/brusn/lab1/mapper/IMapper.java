package ru.nsu.brusn.lab1.mapper;

public interface IMapper<T, V> {
    V map(T obj);
}
