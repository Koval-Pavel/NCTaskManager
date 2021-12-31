package ua.edu.sumdu.j2se.koval.tasks.view;

import ua.edu.sumdu.j2se.koval.tasks.model.AbstractTaskList;

public class EditTaskView extends View {
    @Override
    public int printInfo(AbstractTaskList tasksList)  {
        int result = -1;
        System.out.println("-------------Edit your Task-------------");
        if (readBoolean(" Are you sure (y/n)?", "[y,n]")) {
            String message = View.subMenuCalendar ? "calendar" : "all tasks";
            System.out.println("Choose task by number for EDIT from your " + message + " list");
            result = subCalOutPut(tasksList);
        }
		return result;
    }
}