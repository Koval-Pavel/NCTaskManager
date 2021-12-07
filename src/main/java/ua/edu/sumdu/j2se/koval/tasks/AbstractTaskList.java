package ua.edu.sumdu.j2se.koval.tasks;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

/**
 *  Абстрактний класс що описує характерні методи для списку задач.
 */
public abstract class AbstractTaskList implements Iterable<Task>{
    protected int taskCounter = 0;
    protected int tasksQuantity = 10;
    protected  ListTypes.types type ;

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
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < taskCounter; i++) {
            text.append("Task #").append(i).append(":").append(getTask(i).toString()).append("\n");
        }
        return text.toString();
    }

    /**
     * Перевизначення методу, для отримання Хеш-коду.
     */
    @Override
    public int hashCode() {
        int result = 0;
        for (int i = 0; i < taskCounter; i++) {
            result += Objects.hashCode(getTask(i));
        }
        return result * 31;
    }

    /**
     Метод, що дозволяє працювати нам з коллекціями як з потоками.
     */
    public Stream<Task> getStream() {
        Task[] collToArr = new Task[size() ];
        for (int i = 0; i < collToArr.length; i++) {
            collToArr[i] = getTask(i);
        }
        return Arrays.stream(collToArr);
    }


}
