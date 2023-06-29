package ru.codein.creative.util;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class MathUtils {
    public static double getAvgDouble(List<Double> list) {
        return list.stream().mapToDouble(num -> num).average().orElse(0);
    }
}
