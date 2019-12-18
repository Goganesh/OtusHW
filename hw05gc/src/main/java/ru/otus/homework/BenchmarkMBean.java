package ru.otus.homework;

public interface BenchmarkMBean {
    int getSize();

    void setSize(int size);

    void installGCMonitoring();
}
