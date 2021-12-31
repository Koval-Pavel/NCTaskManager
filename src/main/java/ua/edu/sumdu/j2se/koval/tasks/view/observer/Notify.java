package ua.edu.sumdu.j2se.koval.tasks.view.observer;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.koval.tasks.controller.Controller;
import ua.edu.sumdu.j2se.koval.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.koval.tasks.model.Task;
import ua.edu.sumdu.j2se.koval.tasks.model.Tasks;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.SortedMap;

public class Notify extends Thread  {
    private final AbstractTaskList tasksList;
    private final int isActive;
    static final org.apache.log4j.Logger log = Logger.getLogger(Controller.class);

    public Notify(AbstractTaskList taskList, String name) {
        super(name);
        this.tasksList = taskList;
        isActive = Controller.threadStoper;
    }

    public void run() {
        log.info("Thread started #" + isActive );
        SortedMap<LocalDateTime, Set<Task>> activeTaskList = Tasks.calendar(tasksList, LocalDateTime.now().withNano(0), LocalDateTime.MAX.withNano(0).minusSeconds(10));
        while (isActive + 1  == Controller.threadStoper) {
            try {
                Thread.sleep(999);
                if (!activeTaskList.isEmpty()) {
                    if (activeTaskList.firstKey().isEqual(LocalDateTime.now().withNano(0))) {
                        System.out.println("---------NOTIFICATION-----------");
                        System.out.println("At that moment you have some tasks " + LocalDateTime.now().withNano(0));
                        activeTaskList.get(activeTaskList.firstKey()).forEach(s -> System.out.print(s.getTitle() + "; "));
                        System.out.println("\n-------------------------------");
                        activeTaskList.remove(activeTaskList.firstKey());
                    }
                } else {
                    interrupt();
                }
            } catch (InterruptedException e) {
                break;
            }
        }
        interrupt();
        log.info("Thread interrupted #" + isActive );
    }
}
