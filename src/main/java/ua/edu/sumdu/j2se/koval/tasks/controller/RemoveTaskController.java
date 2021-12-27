package ua.edu.sumdu.j2se.koval.tasks.controller;

import ua.edu.sumdu.j2se.koval.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.koval.tasks.view.View;

public class RemoveTaskController extends Controller{
    public RemoveTaskController(View view, int actionToPerform) {
        super(view, actionToPerform);
    }

    @Override
    public int work(AbstractTaskList tasksList) {
        int numberOfTask;
        numberOfTask = view.printInfo(tasksList);
        AbstractTaskList temp = View.subMenuTaskList ? tasksList: View.tempTaskList;
        tasksList.remove(temp.getTask(numberOfTask));
        log.info("Task removed");
        notifyObserver(tasksList);
        return menuChoose();
    }
}
