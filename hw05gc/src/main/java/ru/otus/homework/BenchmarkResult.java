package ru.otus.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

public class BenchmarkResult {
    private static final Logger LOGGER =  LoggerFactory.getLogger(BenchmarkResult.class);
    private Map<String, Map.Entry<Integer, Double>> result = new HashMap<>();

    public BenchmarkResult(Map<String, Map.Entry<Integer, Double>> result){
        this.result = result;
    }

    public void print() {
        LOGGER.info("[GC Results]");

        result.forEach((k, v) -> {
            LOGGER.info(k + " — "
                    + "Runs: " + v.getKey() + ", "
                    + "Duration: " + v.getValue() + " min.");
        });
    }
}
