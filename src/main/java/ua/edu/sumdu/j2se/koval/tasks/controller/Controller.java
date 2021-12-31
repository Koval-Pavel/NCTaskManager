package ua.edu.sumdu.j2se.koval.tasks.controller;

import org.apache.log4j.Logger;
import ua.edu.sumdu.j2se.koval.tasks.view.observer.Notify;
import ua.edu.sumdu.j2se.koval.tasks.view.observer.Observable;
import ua.edu.sumdu.j2se.koval.tasks.model.AbstractTaskList;
import ua.edu.sumdu.j2se.koval.tasks.view.View;

public abstract class Controller implements Observable {

    protected View view;
    protected int actionToPerform;

    public static final int MAIN_MENU = 1732;
    public static final int SUB_MENU = 11;
    public static final int TASK_LIST = 1;
    public static final int CALENDAR_LIST = 2;
    public static final int ADD_TASK = 3;
    public static final int EDIT_TASK = 4;
    public static final int REMOVE_TASK = 5;
    public static final int DETAILED_INFO = 6;
    public static final int EXIT = 0;

    public static int threadStoper = 0;

    public static final org.apache.log4j.Logger log = Logger.getLogger(Controller.class);

    public Controller(View view, int actionToPerform){
        this.view = view;
        this.actionToPerform = actionToPerform;
    }

    public boolean canWork(int action) {
        return action == actionToPerform;
    }

    public int work (AbstractTaskList tasksList) {
        return view.printInfo(tasksList);
    }

    @Override
    public void notifyObserver(AbstractTaskList tasksList) {
        Notify secondThread = new Notify(tasksList, "Thread for notify");
        secondThread.start();
        ++threadStoper;
    }

    public int menuChoose () {
        View.tempTaskList = null;
        View.tempCal = null;
        int variant = SUB_MENU;
        if (View.subMenuTaskList) { variant = TASK_LIST;}
        if (View.subMenuCalendar) {
            variant = CALENDAR_LIST;
            View.fromEditToCalendar = true;
        }
        return variant;
    }
}
