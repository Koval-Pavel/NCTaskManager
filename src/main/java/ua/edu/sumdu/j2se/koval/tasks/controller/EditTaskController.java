package ua.edu.sumdu.j2se.koval.tasks.controller;

import ua.edu.sumdu.j2se.koval.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.koval.tasks.view.View;


public class EditTaskController extends Controller{

    public EditTaskController(View view, int actionToPerform) {
        super(view, actionToPerform);
    }

    @Override
    public int work(AbstractTaskList tasksList) {
            int numberOfTask = view.printInfo(tasksList);
            try {
                AbstractTaskList temp = View.subMenuTaskList ? tasksList: View.tempTaskList;
                if (temp.getTask(numberOfTask).isRepeated()) {
                    temp.getTask(numberOfTask).setTitle(view.readString(view.enterTitle));
                    temp.getTask(numberOfTask).setTime(view.readTime(view.enterStartTime, view.timeFormat),
                            view.readTime(view.enterEndTime, view.timeFormat),
                            View.readInt(view.enterInterval, "[1-9][0-9]*"));
                    temp.getTask(numberOfTask).setActive(view.readBoolean(" Task is active (y/n)?", "[y,n]"));
                } else {
                    temp.getTask(numberOfTask).setTitle(view.readString(view.enterTitle));
                    temp.getTask(numberOfTask).setTime(view.readTime(view.enterStartTime, view.timeFormat));
                    temp.getTask(numberOfTask).setActive(view.readBoolean(" Task is active (y/n)?", "[y,n]"));
                }
                log.info("Task was Edited");
                notifyObserver(tasksList);
            } catch (IllegalArgumentException ex) {
                System.out.println("Mistake in time input. Task didn't edite.");
                log.warn("Mistake in task edit . IllegalArgumentException");
            }
        return menuChoose();
    }
}
