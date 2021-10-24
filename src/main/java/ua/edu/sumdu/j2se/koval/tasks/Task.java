package ua.edu.sumdu.j2se.koval.tasks;

/**
 *  Класс що описує "Задачі".
 */
public class Task {
    private String title;
    private int time;
    private int start;
    private int end;
    private int interval;
    private boolean active;
    private boolean repetitive;

    /**
     * Конструктор не активної задачі, яка виконується у заданий час без повторення із заданою назвою.
     */
    public Task(String title, int time) {
        this.title = title;
        this.time = time;
        active = false;
        repetitive = false ;
    }

    /**
     * Конструктор не активної задачі, яка виконується у заданому проміжку часу (і початок і кінець включно) із
     * заданим інтервалом.
     */
    public Task(String title, int start, int end, int interval) {
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        active = false;
        repetitive = true;
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
    public int getTime() {
        return isRepeated()? start: time;
    }

    /**
     * Метод що встановлює час виконання "Задачі" (що не повторюється).
     */
    public void setTime(int time) {
        if (isRepeated()) {
            start = time;
            end = time;
            interval = 0;
            this.time = time;
            repetitive = false;
        } else
            this.time = time;
    }

    /**
     * Метод що повертає час початку виконання "Задачі" (що повторюється).
     */
    public int getStartTime() {
        return isRepeated()? start: time;
    }

    /**
     * Метод що повертає час закінчення виконання "Задачі" (що повторюється).
     */
    public int getEndTime() {
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
    public void setTime(int start, int end, int interval) {
        if (!isRepeated()) {
            this.start = start;
            this.end = end;
            this.interval = interval;
            repetitive = true;
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
    public int nextTimeAfter(int current) {
        if (isActive()) {
            if (!repetitive) {
                return (current < time)? time: -1;
            } else {
                if (current < start) {
                    return start;
                } else {
                    if ( current <= (end - ((end - start) % interval))) {
                        return current + (interval - (current - start) % interval);
                    } else
                        return -1;
                }
            }
        } else
            return -1;
    }
}
