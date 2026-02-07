package ru.kuzmich;

public interface Filter<T> {

    T apply(T o);
}
