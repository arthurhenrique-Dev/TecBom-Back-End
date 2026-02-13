package com.tecbom.e_commerce.Domain.ValueObjects;

import java.time.LocalDate;
import java.util.List;

public class TimesInMonth {

    private List<LocalDate> moments;
    private Integer times;

    public void repeat() {
        this.moments.add(LocalDate.now());
        this.times = moments.size();
    }

    public Integer stats() {
        this.moments.removeIf(moment -> moment.isBefore(LocalDate.now().minusMonths(1)));
        return this.times;
    }
    public TimesInMonth() {
        this.moments = new java.util.ArrayList<>();
        this.times = 0;
    }
}
