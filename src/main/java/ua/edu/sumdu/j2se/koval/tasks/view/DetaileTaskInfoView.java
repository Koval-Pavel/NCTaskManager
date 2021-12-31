package ua.edu.sumdu.j2se.koval.tasks.view;

import ua.edu.sumdu.j2se.koval.tasks.model.AbstractTaskList;

public class DetaileTaskInfoView extends View {

    @Override
    public int printInfo(AbstractTaskList tasksList) {
        System.out.println("-------------Detailed information about Task-------------");
        String message = View.subMenuCalendar ? "calendar" : "all tasks";
        System.out.println("Choose task by number for DETAILS from your " + message + " list");
        int numberOfTask = subCalOutPut(tasksList);
        System.out.println("Detailed task INFO: ");
        AbstractTaskList temp = View.subMenuTaskList ? tasksList: View.tempTaskList;
        System.out.println(temp.getTask(numberOfTask));
        return 0;
    }
}
