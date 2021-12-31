package ua.edu.sumdu.j2se.koval.tasks.view;

import ua.edu.sumdu.j2se.koval.tasks.model.AbstractTaskList;

public class MainMenuView extends View {

    @Override
    public int printInfo(AbstractTaskList tasksList) {
        subMenuTaskList = false;
        System.out.println("-------------Main menu-------------");
        System.out.println("Make your choose, enter number from 0 to 3:");
        System.out.println("0. Exit");
        System.out.println("1. Check your Task List");
        System.out.println("2. Check your Task List Calendar");
        System.out.println("3. Add Task to your Task List");
        return readInt("Menu:", "[0-3]");
    }
}
