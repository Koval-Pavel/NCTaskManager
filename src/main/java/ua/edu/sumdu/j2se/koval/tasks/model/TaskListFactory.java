package ua.edu.sumdu.j2se.koval.tasks.model;

/**
 *  Класс що реалізує вибір конкретного механізму реалізації списку "Задач".
 */
public class TaskListFactory {

    /**
     *  Метод що викунує вибір реазіцазії списку задач відповідно до параметру type.
     */
    public static AbstractTaskList createTaskList(ListTypes.types type) {

        AbstractTaskList get = null;

        switch (type) {
            case ARRAY:
                get = new ArrayTaskList();
                break;
            case LINKED:
                get = new LinkedTaskList();
                break;
        }
        return get;
    }
}



