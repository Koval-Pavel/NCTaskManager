package ua.edu.sumdu.j2se.koval.tasks.view;

import ua.edu.sumdu.j2se.koval.tasks.model.AbstractTaskList;


public class SubMenuView extends View {

    @Override
    public int printInfo(AbstractTaskList tasksList) {
        System.out.println("-------------Sub menu-------------");
        System.out.println("Make your choose, enter number from 0 to 7:");
        System.out.println("0. Exit");
        System.out.println("1. Main menu");
        System.out.println("2. Check your Task List");
        System.out.println("3. Check your Task List Calendar");
        System.out.println("4. Add Task to your Task List");
        System.out.println("5. Edit your Task");
        System.out.println("6. Remove your Task");
        System.out.println("7. Detailed information about Task");
        return readInt("Menu:", "[0-7]");
    }
}
