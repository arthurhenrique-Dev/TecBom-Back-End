package Domain.ValueObjects;

import java.time.LocalDate;
import java.util.List;

public class TimesInMonth{

        private List<LocalDate> moments;
        private Integer times;

    public void repeat(){
        this.moments.add(LocalDate.now());
        this.times = moments.size();
    }
    public TimesInMonth stats(){
        this.moments.removeIf(moment -> moment.isBefore(LocalDate.now().minusMonths(1)));
        this.times = moments.size();
        return this;
    }

    public List<LocalDate> getMoments() {
        return moments;
    }

    public Integer getTimes() {
        return times;
    }
}
