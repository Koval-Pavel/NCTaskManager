package ua.edu.sumdu.j2se.koval.tasks;


/**
 *  Абстрактний класс що описує характерні методи для списку задач.
 */
public abstract class AbstractTaskList implements Iterable<Task>{
    protected int taskCounter = 0;
    protected ListTypes.types type;

    /**
     * Абстрактний опис метода, що додає до списку вказану "Задачу".
     */
    public abstract void add(Task task);

    /**
     * Абстрактний опис метода, що видаляє зі списку вказану "Задачу".
     */
    public abstract boolean remove(Task task);

    /**
     * Абстрактний опис метода, що повертає "Задачу", яка знаходиться на вказаному місці у
     * списку, перша задача має індекс 0.
     */
    public abstract Task getTask(int index);

    /**
     * Абстрактний опис метода, що повертає кількість "Задач" у множині.
     */
    public int size() {
        return taskCounter;
    }

    /**
     * Спільна реалізація метод, що повертаэ true якщо вказана "Задача" знаходиться в множині за вказаним індексом.  .
     */
    public boolean ifEquals (int i, Task task) {
        if (getTask(i).getTitle() == null || task.getTitle() == null) {
            return false;
        } else
            return  getTask(i).getTitle().equals(task.getTitle()) && getTask(i).getTime() == task.getTime();
    }

    /**
     Спільна реалізація метод, що повертає підмножину "Задач", які заплановані на виконання
     хоча б раз після часу "from" і не пізніше ніж "to".
     */
    public  AbstractTaskList incoming(int from, int to) throws IllegalArgumentException {
        if (from < 0 && to < 0 && from > to) {
            throw new IllegalArgumentException();
        } else {
            AbstractTaskList incomTask = TaskListFactory.createTaskList(type);
            for (int i = 0; i < size(); i++) {
                int temp = getTask(i).nextTimeAfter(from);
                if (getTask(i) != null) {
                    if (temp != -1 && temp < to && getTask(i).isActive()) {
                        incomTask.add(getTask(i));
                    }
                } else break;
            }
            return incomTask;
        }
    }

    /**
     Перевизначення методу, для реалізації порівнянь "Задач" в списках.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractTaskList that = (AbstractTaskList) o;
        boolean check = false;
        if (hashCode() == that.hashCode()) {
            if (size() == that.size()) {
                check = true;
                for (int i = size() - 1; i >= 0; i--) {
                    if (!ifEquals(i, that.getTask(i))) {
                        check = false;
                        break;
                    }
                }
            }
        }
        return check;
    }

    /**
     Перевизначення методу, для рядкового відображення інформації про "Задачі".
     */
    @Override
    public String toString() {
        String text = null;
        for (int i = 0; i < taskCounter; i++) {
            text += "Task #" + i + ":" + getTask(i).toString() + "\n";
        }
        return text;
    }

}
