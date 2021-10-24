package ua.edu.sumdu.j2se.koval.tasks;

/**
 *  Класс що описує спискок задач "Задач" (через масив).
 */
public class ArrayTaskList {

    private int arrDimension = 10;
    private int taskCounter = 0;
    private Task[] tasksList;

    /**
     * Конструктор в якому ініціалізується масив для майбутніх "Задач".
     */
    public ArrayTaskList() {
        tasksList = new Task[arrDimension];
    }

    /**
     * Метод, що додає до списку вказану "Задачу", та розширює масив (на 10 єлементі) при досяганні ліміту.
     */
    public void add(Task task){
        tasksList[taskCounter] = task;
        if (taskCounter == (arrDimension - 1)) {
            Task[] TasksListCopy = new Task[arrDimension + 10];
            System.arraycopy(tasksList,0,TasksListCopy,0,arrDimension);
            tasksList = TasksListCopy;
            arrDimension += 10;
        }
        ++taskCounter;
    }

    /**
     * Метод, що видаляє зі списку вказану "Задачу", та впорядковує "Задачі" в масиві.
     */
    public boolean remove(Task task) {
        boolean check = false;
        for (int i = 0; i <= tasksList.length - 1; i++) {
            if (tasksList[i] != null) {
                if (tasksList[i].getTitle().equals(task.getTitle()) && tasksList[i].getTime() == task.getTime()) {
                    check = true;
                    --taskCounter;
                    if (tasksList.length - 1 - i >= 0)
                        System.arraycopy(tasksList, i + 1, tasksList, i, tasksList.length - 1 - i);
                    tasksList[tasksList.length - 1] = null;
                    break;
                }
            } else break;
        } return check;
    }

    /**
     * Метод, що повертає кількість "Задач" у списку.
     */
    public int size() {
        return taskCounter;
    }

    /**
     * Метод, що повертає "Задачу", яка знаходиться на вказаному місці у
     * списку, перша задача має індекс 0.
     */
    public Task getTask(int index) {
        return tasksList[index];
    }


    /**
     Метод, що повертає підмножину "Задач", які заплановані на виконання
     хоча б раз після часу "from" і не пізніше ніж "to".
     */
    public  ArrayTaskList incoming(int from, int to) {
        ArrayTaskList incomArrTask = new ArrayTaskList();
        for (int i = 0; i <= taskCounter - 1; i++) {
            int temp = tasksList[i].nextTimeAfter(from);
            if (tasksList[i] != null) {
                if (temp != -1 && temp < to && tasksList[i].isActive()) {
                    incomArrTask.add(tasksList[i]);
                }
            } else break;
        }
        return incomArrTask;
    }
}