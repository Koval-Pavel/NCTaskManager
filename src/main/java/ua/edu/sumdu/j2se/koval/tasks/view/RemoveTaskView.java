package ua.edu.sumdu.j2se.koval.tasks.view;

import ua.edu.sumdu.j2se.koval.tasks.model.AbstractTaskList;


public class RemoveTaskView extends View  {
    @Override
    public int printInfo(AbstractTaskList tasksList) {
        System.out.println("-------------Remove your Task-------------");
        String message = View.subMenuCalendar ? "calendar" : "all tasks";
        System.out.println("Choose task by number for REMOVE from your " + message + " list");
        return subCalOutPut(tasksList);
    }
}
