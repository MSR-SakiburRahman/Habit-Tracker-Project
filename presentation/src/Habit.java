public abstract class Habit {
    private String name;
    private int daysDone;

    public Habit(String name) {
        this.name = name;
        this.daysDone = 0;
    }

    public String getName() {
        return name;
    }

    public int getDaysDone() {
        return daysDone;
    }

    public void incrementDaysDone() {
        daysDone++;
    }

    public void decrementDaysDone() {
        if (daysDone > 0) {
            daysDone--;
        }
    }

    public abstract void markDay(boolean done);
}
