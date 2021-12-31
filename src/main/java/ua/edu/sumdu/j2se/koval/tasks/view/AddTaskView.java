package ua.edu.sumdu.j2se.koval.tasks.view;

import ua.edu.sumdu.j2se.koval.tasks.model.AbstractTaskList;

import static ua.edu.sumdu.j2se.koval.tasks.controller.Controller.MAIN_MENU;
import static ua.edu.sumdu.j2se.koval.tasks.controller.Controller.SUB_MENU;

public class AddTaskView extends View {

    @Override
    public int printInfo(AbstractTaskList tasksList) {
        int variant = MAIN_MENU - 1;
        if (subMenuTaskList | subMenuCalendar) {
            variant = SUB_MENU - 1;
        }
        System.out.println("-------------Add Task to your List-------------");
        return variant + readInt("Choose variant (1 or 2): \n" +
                "1. Add one more Task \n" +
                "2. Come back to previous Menu", "[1-2]");
    }
}