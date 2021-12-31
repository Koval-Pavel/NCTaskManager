package ua.edu.sumdu.j2se.koval.tasks.view;

import ua.edu.sumdu.j2se.koval.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.koval.tasks.model.Task;
import ua.edu.sumdu.j2se.koval.tasks.model.Tasks;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import static ua.edu.sumdu.j2se.koval.tasks.controller.Controller.*;

public class CalendarView extends View {
    public static LocalDateTime startTime;
    public static LocalDateTime endTime;

    @Override
    public int printInfo(AbstractTaskList tasksList) {
        int variant = SUB_MENU;
        System.out.println("-------------Check your Task List Calendar-------------");
        if (!fromEditToCalendar) {
                System.out.println("Enter time periods for Calendar: ");
                startTime = this.readTime(enterStartTime);
                endTime = this.readTime(enterEndTime);
        }
        try {
            tempCal = Tasks.calendar(tasksList, startTime, endTime);
            if (!tempCal.isEmpty()) {
                System.out.println("Your Calendar from " + startTime + " to " + endTime + " :");
                for (Map.Entry<LocalDateTime, Set<Task>> item : tempCal.entrySet()) {
                    System.out.print("Time: " + item.getKey().format(formatter) + " " + "Tasks Title: ");
                    item.getValue().forEach(s -> System.out.print(s.getTitle() + "; "));
                    System.out.println();
                }
            } else {
                System.out.println("Your Calendar is Empty");
                variant = MAIN_MENU;
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Mistake in time input. Calendar didn't create.");
        }
        fromEditToCalendar = false;
        pressEnter();
        return variant;
    }
}
