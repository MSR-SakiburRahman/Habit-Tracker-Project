public class DailyHabit extends Habit implements Trackable {
    public DailyHabit(String name) {
        super(name);
    }

    @Override
    public void markDay(boolean done) {
        if (done) {
            incrementDaysDone();
        } else {
            decrementDaysDone();
        }
    }
}
