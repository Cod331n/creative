package ru.codein.creative.criteria;

import lombok.Value;

import java.util.UUID;

@Value
public class PlotCriteria {
    UUID uuid;
    double creativity;
    double composition;
    double realization;
}
