package ua.edu.sumdu.j2se.koval.tasks.controller;

import com.google.gson.JsonSyntaxException;
import ua.edu.sumdu.j2se.koval.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.koval.tasks.model.ArrayTaskList;
import ua.edu.sumdu.j2se.koval.tasks.model.TaskIO;
import ua.edu.sumdu.j2se.koval.tasks.view.*;

import java.io.File;
import java.util.ArrayList;

import static ua.edu.sumdu.j2se.koval.tasks.view.View.readInt;

public class MainMenuController extends Controller{

    private final AbstractTaskList taskList;
    private final ArrayList<Controller> controllers = new ArrayList<>();


    public MainMenuController(View mainView) {

        super(mainView, Controller.MAIN_MENU);
        log.info("Program start");
        this.taskList = new ArrayTaskList();

        controllers.add(this);
        controllers.add(new SubMenuController(new SubMenuView(), Controller.SUB_MENU));
        controllers.add(new TaskListController(new TaskListView(), Controller.TASK_LIST));
        controllers.add(new CalendarController(new CalendarView(), Controller.CALENDAR_LIST));
        controllers.add(new AddTaskController(new AddTaskView(), Controller.ADD_TASK));
        controllers.add(new EditTaskController(new EditTaskView(), Controller.EDIT_TASK));
        controllers.add(new RemoveTaskController(new RemoveTaskView(), Controller.REMOVE_TASK));
        controllers.add(new DetaileTaskInfoController(new DetaileTaskInfoView(), Controller.DETAILED_INFO));
        int fileCheck = 1;
        try {
            TaskIO.readText(taskList, new File("taskList.txt"));
        } catch (JsonSyntaxException | NullPointerException  ex) {
            log.warn("Some problem with taskList.txt file");
            fileCheck = readInt("Some problem with \"taskList.txt\" file. \n" +
                    "0. Exit and check file. \n" +
                    "1. Continue and REWRITE file.", "[0-1]");
        }
        if (fileCheck == 1) {
            this.work(taskList);
            log.info("Finish program");
        } else {
            log.info("Finish program");
            System.exit(0);}
        TaskIO.writeText(taskList, new File("taskList.txt"));
    }

    @Override
    public int work(AbstractTaskList tasksList) {
        int action = view.printInfo(tasksList);
        for ( ; ; ) {
            for (Controller tempController: controllers) {
                if (tempController.canWork(action)) {
                    action = tempController.work(this.taskList);
                }
            }
            if (action == Controller.EXIT) {
                ++threadStoper;
                break;
            }
        }
        return Controller.EXIT;
    }
}
