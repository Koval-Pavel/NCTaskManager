package ua.edu.sumdu.j2se.koval.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *  Класс що описує "Задачі".
 */
public class Task implements Cloneable {
    private String title;
    private LocalDateTime time;
    private LocalDateTime start;
    private LocalDateTime end;
    private int interval;
    private boolean active;
    private boolean repetitive;

    /**
     * Конструктор не активної задачі, яка виконується у заданий час без повторення із заданою назвою.
     */
    public Task(String title, LocalDateTime time) throws IllegalArgumentException{
        if (time == null) {
            throw new IllegalArgumentException();
        } else {
            this.title = title;
            this.time = time;
            active = false;
            repetitive = false;
        }
    }

    /**
     * Конструктор не активної задачі, яка виконується у заданому проміжку часу (і початок і кінець включно) із
     * заданим інтервалом.
     */
    public Task(String title, LocalDateTime start, LocalDateTime end, int interval) throws IllegalArgumentException{
        if (start == null  && end ==null  && interval < 0) {
            throw new IllegalArgumentException();
        } else {
            this.title = title;
            this.start = start;
            this.end = end;
            this.interval = interval;
            active = false;
            repetitive = true;
        }
    }

    /**
     * Метод що повертає назву активної "Задачі".
     */
    public String getTitle() {
        return title;
    }

    /**
     * Метод що встановлює нову назву "Задачі".
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Метод що повертає стан активності "Задачі".
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Метод що встановлює стан активності "Задачі".
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Метод що повертає час виконання "Задачі" (що не повторюється).
     */
    public LocalDateTime getTime() {
        return isRepeated()? start: time;
    }

    /**
     * Метод що встановлює час виконання "Задачі" (що не повторюється).
     */
    public void setTime(LocalDateTime time) throws IllegalArgumentException {
//        if (time.isBefore(null)) {
//            throw new IllegalArgumentException();
//        } else {
        if (isRepeated()) {
            start = time;
            end = time;
            interval = 0;
            this.time = time;
            repetitive = false;
        } else
            this.time = time;
//        }
    }

    /**
     * Метод що повертає час початку виконання "Задачі" (що повторюється).
     */
    public LocalDateTime getStartTime() {
        return isRepeated()? start: time;
    }

    /**
     * Метод що повертає час закінчення виконання "Задачі" (що повторюється).
     */
    public LocalDateTime getEndTime() {
        return isRepeated()? end: time;
    }

    /**
     * Метод що повертає час інтервалу виконання "Задачі" (що повторюється).
     */
    public int getRepeatInterval() {
        return isRepeated()? interval: 0;
    }

    /**
     * Метод що встановлює час початку, закінченяя та інтервалу виконання "Задачі" (що повторюється).
     */
    public void setTime(LocalDateTime start, LocalDateTime end, int interval) throws IllegalArgumentException {
        if (start.isBefore(LocalDateTime.MIN) && end.isBefore(LocalDateTime.MAX) && interval < 0) {
            throw new IllegalArgumentException();
        } else {
            if (!isRepeated()) {
                this.start = start;
                this.end = end;
                this.interval = interval;
                repetitive = true;
            }
        }
    }

    /**
     * Метод що перевіряє повторюваність активної "Задачі".
     */
    public boolean isRepeated() {
        return repetitive;
    }

    /**
     * Метод що повертає час наступного виконання задачі після вказаного часу current,
     * якщо задача не виконується - повертає значення -1
     */
    public LocalDateTime nextTimeAfter(LocalDateTime current) throws IllegalArgumentException {
        if (current.isBefore(LocalDateTime.MIN)) {
            throw new IllegalArgumentException();
        } else {
            if (isActive()) {
                if (!repetitive) {
                    return (current.isBefore(time)) ? time : null;
                } else {
                    if (current.isBefore(start)) {
                        return start;
                    } else {
                        if (current.isBefore(end.minusSeconds(Duration.between(start,end).toSeconds() % interval) )) {
                            return current.plusSeconds(interval - (Duration.between(start,current).toSeconds() % interval)) ;
                        } else
                            return null;
                    }
                }
            } else
                return null;
        }
    }

    /**
     * Перевизначення методу клонування "Задачі"
     */
    @Override
    public Task clone() throws CloneNotSupportedException {
        return (Task) super.clone();
    }

    /**
     * Перевизначення методу, для реалізації порівнянь "Задач"
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return time == task.time && start == task.start && end == task.end && interval == task.interval && active == task.active && repetitive == task.repetitive && Objects.equals(title, task.title);
    }

    /**
     * Перевизначення методу, для отримання Хеш-коду.
     */
    @Override
    public int hashCode() {
        return Objects.hash(title, time, start, end, interval, active, repetitive);
    }

    /**
     Перевизначення методу, для рядкового відображення інформації про "Задачі".
     */
    @Override
    public String toString() {
        String text;
        if (interval !=0) {
            text = "{" +
                    "title='" + title + '\'' +
                    ", start=" + start +
                    ", end=" + end +
                    ", interval=" + interval +
                    ", active=" + active +
                    ", repetitive=" + repetitive +
                    '}';
        } else {
            text = "{" +
                    "title='" + title + '\'' +
                    ", time=" + time +

                    ", active=" + active +
                    ", repetitive=" + repetitive +
                    '}';
        }

        return text;
    }

}

