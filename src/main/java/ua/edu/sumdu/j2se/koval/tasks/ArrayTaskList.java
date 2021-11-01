package ua.edu.sumdu.j2se.koval.tasks;

/**
 *  Класс що описує спискок задач "Задач" (через масив).
 */
public class ArrayTaskList extends AbstractTaskList{

    private int arrDimension = 10;
    private Task[] tasksList;

    /**
     * Конструктор в якому ініціалізується масив для майбутніх "Задач".
     */
    public ArrayTaskList() {
        tasksList = new Task[arrDimension];
        type = ListTypes.types.ARRAY;
    }

    /**
     * Метод, що додає до списку вказану "Задачу", та розширює масив (на 10 єлементі) при досяганні ліміту.
     */
    public void add(Task task) throws NullPointerException {
        if (task == null) {
            throw new NullPointerException();
        } else {
            tasksList[taskCounter] = task;
            if (taskCounter == (arrDimension - 1)) {
                Task[] TasksListCopy = new Task[arrDimension + 10];
                System.arraycopy(tasksList, 0, TasksListCopy, 0, arrDimension);
                tasksList = TasksListCopy;
                arrDimension += 10;
            }
            ++taskCounter;
        }
    }

    /**
     * Метод, що видаляє зі списку вказану "Задачу", та впорядковує "Задачі" в масиві.
     */
    public boolean remove(Task task) {
        boolean check = false;
        for (int i = 0; i <= tasksList.length - 1; i++) {
            if (getTask(i) != null) {
                if (ifEquals(i, task)) {
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
     * Метод, що повертає "Задачу", яка знаходиться на вказаному місці у
     * списку, перша задача має індекс 0.
     */
    public Task getTask(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size() || size() == 0) {
            throw new IndexOutOfBoundsException();
        } else {
            return tasksList[index];
        }
    }

}