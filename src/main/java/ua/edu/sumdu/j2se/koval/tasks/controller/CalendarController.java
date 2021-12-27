package ua.edu.sumdu.j2se.koval.tasks.controller;

import ua.edu.sumdu.j2se.koval.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.koval.tasks.view.View;

public class CalendarController extends Controller {

    public CalendarController(View view, int actionToPerform) {
        super(view, actionToPerform);
    }

    @Override
    public int work(AbstractTaskList tasksList) {
        View.subMenuTaskList = false;
        View.subMenuCalendar = true;
        return view.printInfo(tasksList);
    }
}
