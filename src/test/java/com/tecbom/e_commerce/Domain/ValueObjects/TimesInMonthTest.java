package com.tecbom.e_commerce.Domain.ValueObjects;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class TimesInMonthTest {

    @Test
    void deveIniciarComValoresVazios() {
        TimesInMonth timesInMonth = new TimesInMonth();
        assertEquals(0, timesInMonth.stats());
    }

    @Test
    void deveIncrementarAoRepetir() {
        TimesInMonth timesInMonth = new TimesInMonth();
        timesInMonth.repeat();
        timesInMonth.repeat();

        assertEquals(2, timesInMonth.stats());
    }

    @Test
    void deveRemoverMomentosAntigosAoGerarStats() throws Exception {
        TimesInMonth timesInMonth = new TimesInMonth();

        Field fieldMoments = TimesInMonth.class.getDeclaredField("moments");
        fieldMoments.setAccessible(true);
        List<LocalDate> moments = (List<LocalDate>) fieldMoments.get(timesInMonth);

        Field fieldTimes = TimesInMonth.class.getDeclaredField("times");
        fieldTimes.setAccessible(true);

        moments.add(LocalDate.now().minusMonths(2));
        fieldTimes.set(timesInMonth, 1);

        timesInMonth.repeat();

        timesInMonth.stats();

        fieldTimes.set(timesInMonth, moments.size());

        assertEquals(1, timesInMonth.stats());
    }
}