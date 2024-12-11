package model;

public class SimpleHabit extends Habit {
    public SimpleHabit(String name, int goal) {
        super(name, goal);
    }

    @Override
    public void performAction() {
        System.out.println("Performing action for habit: " + getName());
    }
}
