package ua.edu.sumdu.j2se.koval.tasks.view.observer;


import ua.edu.sumdu.j2se.koval.tasks.model.AbstractTaskList;

public interface Observable {
    void notifyObserver(AbstractTaskList tasksList);
}
