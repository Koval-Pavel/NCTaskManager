package ua.edu.sumdu.j2se.koval.tasks.view;

import ua.edu.sumdu.j2se.koval.tasks.model.AbstractTaskList;

import static ua.edu.sumdu.j2se.koval.tasks.controller.Controller.MAIN_MENU;
import static ua.edu.sumdu.j2se.koval.tasks.controller.Controller.SUB_MENU;

public class TaskListView extends View {

    @Override
    public int printInfo(AbstractTaskList tasksList) {
        subMenuCalendar = false;
        subMenuTaskList = true;
        int variant = SUB_MENU;
        System.out.println("-------------Check your Task List-------------");
        if (tasksList.size() != 0) {
            System.out.println("Your tasks list:");
            System.out.println(tasksList);
        } else {
            System.out.println("Your Task List empty");
            variant = MAIN_MENU;
        }
        pressEnter();
        return variant;
    }
}
