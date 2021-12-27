package ua.edu.sumdu.j2se.koval.tasks.Observer;


import ua.edu.sumdu.j2se.koval.tasks.model.AbstractTaskList;

public interface Observable {
    void notifyObserver(AbstractTaskList tasksList);
}
