package ua.edu.sumdu.j2se.koval.tasks.controller;

import ua.edu.sumdu.j2se.koval.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.koval.tasks.model.Task;
import ua.edu.sumdu.j2se.koval.tasks.view.View;

public class AddTaskController extends Controller {

    public AddTaskController(View view, int actionToPerform) {
        super(view, actionToPerform);
    }

    @Override
    public int work(AbstractTaskList tasksList) {
        int variant = 1;
        boolean repetitive;
        while (variant == 1 | variant == 11) {
            repetitive = view.readBoolean("Task will be repetitive? Please insert (y/n)", "[y,n]");
            try {
                if (repetitive) {
                    tasksList.add(new Task(view.readString(view.enterTitle),
                            view.readTime(view.enterStartTime, view.timeFormat),
                            view.readTime(view.enterEndTime, view.timeFormat),
                            View.readInt(view.enterInterval, "[1-9][0-9]*")));
                } else {
                    tasksList.add(new Task(view.readString(view.enterTitle),
                        view.readTime(view.enterStartTime, view.timeFormat)));
                }
                log.info("New task was added");
                notifyObserver(tasksList);
            } catch (IllegalArgumentException ex) {
                System.out.println("Mistake in time input. Task didn't add.");
                log.warn("Mistake in task add (End before Star). IllegalArgumentException");
            }
            variant = view.printInfo(tasksList);
        }

        if (variant == 2) {variant = 1; }
        if (variant == 12 && View.subMenuTaskList) { variant = 2;}
        if (variant == 12 && View.subMenuCalendar) {
            variant = 3;
            View.fromEditToCalendar = true;
        }
        return variant;
    }



}
