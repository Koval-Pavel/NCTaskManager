package ua.edu.sumdu.j2se.koval.tasks.view;

import ua.edu.sumdu.j2se.koval.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.koval.tasks.model.Task;
import ua.edu.sumdu.j2se.koval.tasks.model.Tasks;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import static ua.edu.sumdu.j2se.koval.tasks.controller.Controller.log;

public class CalendarView extends View {
    public static LocalDateTime startTime;
    public static LocalDateTime endTime;

    @Override
    public int printInfo(AbstractTaskList tasksList) {
        int variant = 11;
        System.out.println("-------------Check your Task List Calendar-------------");
        if (!fromEditToCalendar) {
                System.out.println("Enter time periods for Calendar: ");
                startTime = this.readTime(enterStartTime, timeFormat);
                endTime = this.readTime(enterEndTime, timeFormat);
        }
        try {
            tempCal = Tasks.calendar(tasksList, startTime, endTime);
            if (!tempCal.isEmpty()) {
                System.out.println("Your Calendar from " + startTime + " to " + endTime + " :");
                for (Map.Entry<LocalDateTime, Set<Task>> item : tempCal.entrySet()) {
                    System.out.print("Time: " + item.getKey() + " " + "Tasks Title: ");
                    item.getValue().forEach(s -> System.out.print(s.getTitle() + "; "));
                    System.out.println();
                }
            } else {
                System.out.println("Your Calendar is Empty");
                variant = 1;
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Mistake in time input. Calendar didn't create.");
            log.warn("Mistake in time input. Calendar didn't create. IllegalArgumentException");
        }
        fromEditToCalendar = false;
        pressEnter();
        return variant;
    }
}
