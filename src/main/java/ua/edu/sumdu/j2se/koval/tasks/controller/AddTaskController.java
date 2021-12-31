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
        int variant = MAIN_MENU;
        boolean repetitive;
        while (variant == MAIN_MENU | variant == SUB_MENU) {
            repetitive = view.readBoolean("Task will be repetitive? Please insert (y/n)", "[y,n]");
            try {
                if (repetitive) {
                    tasksList.add(new Task(view.readString(view.enterTitle),
                            view.readTime(view.enterStartTime),
                            view.readTime(view.enterEndTime),
                            View.readInt(view.enterInterval, "[1-9][0-9]*")));
                } else {
                    tasksList.add(new Task(view.readString(view.enterTitle),
                        view.readTime(view.enterStartTime)));
                }
                log.info("New task was added");
                notifyObserver(tasksList);
            } catch (IllegalArgumentException ex) {
                System.out.println("Mistake in time input. Task didn't add.");
            }
            variant = view.printInfo(tasksList);
        }

        if (variant == MAIN_MENU + 1) {variant = MAIN_MENU; }
        if (variant == SUB_MENU + 1 && View.subMenuTaskList) { variant = TASK_LIST;}
        if (variant == SUB_MENU + 1 && View.subMenuCalendar) {
            variant = CALENDAR_LIST;
            View.fromEditToCalendar = true;
        }
        return variant;
    }
}
