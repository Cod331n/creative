package ru.codein.creative.util;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class MathUtils {
    public static double getAvgDouble(List<Double> list) {
        return list.stream().mapToDouble(num -> num).average().orElse(0);
    }

    public static int getAvgInt(List<Integer> list) {
        return (int) list.stream().mapToInt(num -> num).average().orElse(0);
    }

    public static double round(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}
