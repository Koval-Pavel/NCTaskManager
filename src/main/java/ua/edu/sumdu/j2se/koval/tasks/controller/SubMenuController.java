package ua.edu.sumdu.j2se.koval.tasks.controller;

import ua.edu.sumdu.j2se.koval.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.koval.tasks.view.View;

public class SubMenuController extends Controller{
    public SubMenuController(View view, int actionToPerform) {
        super(view, actionToPerform);
    }

    @Override
    public int work(AbstractTaskList tasksList) {
        return view.printInfo(tasksList);
    }
}
