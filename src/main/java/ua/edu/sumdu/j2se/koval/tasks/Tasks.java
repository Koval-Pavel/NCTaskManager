package ua.edu.sumdu.j2se.koval.tasks;

import java.time.LocalDateTime;
import java.util.*;
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
        if (start.isBefore(LocalDateTime.MIN ) && end.isBefore(LocalDateTime.MAX)  && start.isBefore(end)) {
            throw new IllegalArgumentException();
        } else {
            AbstractTaskList tempList = TaskListFactory.createTaskList(AbstractTaskList.type);
            tasks.forEach(tempList::add);
            Stream<Task> str;
            str = tempList.getStream();
            AbstractTaskList incomTask = TaskListFactory.createTaskList(AbstractTaskList.type);
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
        if (start.isBefore(LocalDateTime.MIN) && end.isBefore(LocalDateTime.MAX) && start.isBefore(end)) {
            throw new IllegalArgumentException();
        } else {
            AbstractTaskList incomTasksList = (AbstractTaskList) incoming(tasks, start, end);
            SortedMap<LocalDateTime, Set<Task>> TableOfTasks = new TreeMap<>();
            LocalDateTime timeOfTasks;
            for (int i = 0; i < incomTasksList.size(); i++) {
                timeOfTasks = start;
                while (timeOfTasks != null && (timeOfTasks.isBefore(end) | timeOfTasks.isEqual(end))) {
//                --------------------------------------------------------- Реализация через Stream
                    Set<Task> tasksForDate;
                    timeOfTasks = incomTasksList.getTask(i).nextTimeAfter(timeOfTasks);
                    Stream<Task> str = incomTasksList.getStream();
                    LocalDateTime finalTimeOfTasks = timeOfTasks;
                    tasksForDate = str.filter(s -> finalTimeOfTasks != null && finalTimeOfTasks.equals(s.nextTimeAfter(finalTimeOfTasks.minusSeconds(1)))).collect(Collectors.toSet());
                    if (timeOfTasks != null && (timeOfTasks.isBefore(end) | timeOfTasks.isEqual(end))) {
                        TableOfTasks.put(timeOfTasks, tasksForDate);
                    }
//                --------------------------------------------------------- Реализация через for/if
//                Set<Task> tasksForDate = new LinkedHashSet<>();
//                timeOfTasks = incomTasksList.getTask(i).nextTimeAfter(timeOfTasks);
//                for (int j = 0; j < incomTasksList.size(); j++) {
//                    if (timeOfTasks != null && timeOfTasks.equals(incomTasksList.getTask(j).nextTimeAfter(timeOfTasks.minusSeconds(1)))) {
//                        tasksForDate.add(incomTasksList.getTask(j));
//                    }
//                }
//                if (timeOfTasks != null && (timeOfTasks.isBefore(end) | timeOfTasks.isEqual(end))) {
//                    TableOfTasks.put(timeOfTasks, tasksForDate);
//                }
//                ----------------------------------------------------------
                }
            }
            return TableOfTasks;
        }
    }
}
