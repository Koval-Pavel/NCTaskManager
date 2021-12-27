package ua.edu.sumdu.j2se.koval.tasks.controller;

import ua.edu.sumdu.j2se.koval.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.koval.tasks.view.View;

public class DetaileTaskInfoController extends Controller  {
    public DetaileTaskInfoController(View view, int actionToPerform) {
        super(view, actionToPerform);
    }

    @Override
    public int work(AbstractTaskList tasksList) {
        view.printInfo(tasksList);
        return menuChoose();
    }
}
