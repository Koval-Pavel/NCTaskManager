package ua.edu.sumdu.j2se.koval.tasks.view;

import ua.edu.sumdu.j2se.koval.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.koval.tasks.model.ArrayTaskList;
import ua.edu.sumdu.j2se.koval.tasks.model.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class View  {

    public static boolean subMenuTaskList;
    public static boolean subMenuCalendar;
    public static boolean fromEditToCalendar = false;
    public static SortedMap<LocalDateTime, Set<Task>> tempCal;
    public static AbstractTaskList tempTaskList;

    public final String enterTitle = "Enter task title: ";
    public final String enterStartTime = "Enter START time in format (yyy-mm-ddThh:mm:ss) exmpl: " + LocalDateTime.now().withNano(0);
    public final String enterEndTime = "Enter END Time in format (yyy-mm-ddThh:mm:ss) exmpl: " + LocalDateTime.now().withNano(0);
    public final String enterInterval = "Enter Task time INTERVAL in seconds:";
    public final String timeFormat = "^(\\d{4,})-(\\d{2})-(\\d{2})[T ](\\d{2}):(\\d{2})(?::(\\d{2}(?:\\.\\d+)?))?$";

    public abstract int printInfo(AbstractTaskList tasksList);

    public void pressEnter() {
        System.out.println("Press Enter for Menu");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }

    public static int readInt(String message, String regEx) {
        Scanner sc = new Scanner(System.in);
        System.out.println(message);
        String enter = sc.nextLine();
        while (!enter.matches(regEx)) {
            System.out.println("Input don't match specifications. Try again.");
            enter = sc.nextLine();
        }
        return Integer.parseInt(enter);
    }

    public String readString (String message) {
        Scanner sc = new Scanner(System.in);
        System.out.println(message);
        String enter = sc.nextLine();
        while (enter.equals("")) {
            System.out.println("Input don't match specifications. Try again.");
            enter = sc.nextLine();
        }
        return (enter);
    }

    public LocalDateTime readTime(String message, String regEx) {
        boolean inputCheck = false;
        LocalDateTime time = null;
            Scanner sc = new Scanner(System.in);
            System.out.println(message);
            String enter = sc.nextLine();
        while (!inputCheck) {
//            while (!enter.matches(regEx)) {
//                System.out.println("Input don't match specifications. Try again.");
//                enter = sc.nextLine();
//            }
            try {
                time = LocalDateTime.parse(enter);
                inputCheck = true;
            } catch (DateTimeParseException ex) {
                System.out.println("Invalid date format, please try again:");
                enter = sc.nextLine();
            }
        }
        return time;
    }

    public boolean readBoolean(String message, String regEx) {
        Scanner sc = new Scanner(System.in);
        System.out.println(message);
        String enter = sc.nextLine().toLowerCase();
        while (!enter.matches(regEx)) {
            System.out.println("Input don't match specifications. Try again.");
            enter = sc.nextLine();
        }
        return enter.matches("y");
    }

    public int subCalOutPut (AbstractTaskList tasksList) {
        int numberOfTask;
        int taskQuantity;
        if (subMenuCalendar) {
            tempTaskList = new ArrayTaskList(); //
            AtomicInteger i = new AtomicInteger();
            for (Map.Entry<LocalDateTime, Set<Task>>  item: tempCal.entrySet()) {
                LocalDateTime temporary = item.getKey();
                item.getValue().forEach(s-> System.out.println("# " + i.getAndIncrement() + ") Time: "
                        + temporary + "; Task Title: " + s.getTitle() + "; ") );
                item.getValue().forEach(tempTaskList::add);
            }
            taskQuantity = tempTaskList.size() - 1;
        } else {
            taskQuantity = tasksList.size() - 1;
        }
            numberOfTask = readInt("Task#: ", "[0-" + taskQuantity + "]");

        return numberOfTask;
    }

}
