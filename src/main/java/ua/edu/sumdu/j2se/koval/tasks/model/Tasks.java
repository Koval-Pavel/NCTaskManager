
package ua.edu.sumdu.j2se.koval.tasks.model;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 Клас для роботи з задачами колекцій.
 */
public class Tasks  {

    /**
     Спільна реалізація метод, що повертає підмножину "Задач", які заплановані на виконання
     хоча б раз після часу "from" і не пізніше ніж "to".
     */
    public static  Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) throws IllegalArgumentException {
        if (start.isBefore(LocalDateTime.MIN ) | end.isAfter(LocalDateTime.MAX)  | start.isAfter(end)) {
            throw new IllegalArgumentException();
        } else {
            AbstractTaskList tempList = new ArrayTaskList();
            tasks.forEach(tempList::add);
            Stream<Task> str = tempList.getStream();
            Set<Task> incomTask = new LinkedHashSet<Task>();//
//            AbstractTaskList incomTask = new ArrayTaskList();

            str.filter(s -> s != null && s.nextTimeAfter(start) != null && s.nextTimeAfter(start).isAfter(start.minusSeconds(1)) &&
                    s.nextTimeAfter(start).isBefore(end.plusSeconds(1)) && s.isActive()).forEach(incomTask::add);
            return incomTask;
        }
    }

    /**
     Реалізація метод, що будує календар задач з множини "Задач" task за заданий період, де кожній даті
     відповідає множина задач, що мають бути виконані в цей час після часу "start" і не пізніше ніж "end".
     */
    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) throws IllegalArgumentException {
        if (start.isBefore(LocalDateTime.MIN) | end.isAfter(LocalDateTime.MAX) | end.isBefore(start)) {
            throw new IllegalArgumentException();
        } else {
            LinkedHashSet<Task> incomTasksList = (LinkedHashSet<Task>) incoming(tasks, start, end);//
//            AbstractTaskList incomTasksList = (ArrayTaskList) incoming(tasks, start, end);

            SortedMap<LocalDateTime, Set<Task>> TableOfTasks = new TreeMap<>();
            LocalDateTime timeOfTasks;
            for (Task temp: incomTasksList) {
                timeOfTasks = start;
                while (timeOfTasks != null && (timeOfTasks.isBefore(end) | timeOfTasks.isEqual(end))) {
                    Set<Task> tasksForDate;
                    timeOfTasks = temp.nextTimeAfter(timeOfTasks);
                    Stream<Task> str = incomTasksList.stream();//
//                    Stream<Task> str = incomTasksList.getStream();
                    LocalDateTime finalTimeOfTasks = timeOfTasks;
                    tasksForDate = str.filter(s -> finalTimeOfTasks != null && finalTimeOfTasks.equals(s.nextTimeAfter(finalTimeOfTasks.minusSeconds(1)))).collect(Collectors.toSet());
                    if (timeOfTasks != null && (timeOfTasks.isBefore(end) | timeOfTasks.isEqual(end))) {
                        TableOfTasks.put(timeOfTasks, tasksForDate);
                    }
                }
            }
            return TableOfTasks;
        }
    }
}