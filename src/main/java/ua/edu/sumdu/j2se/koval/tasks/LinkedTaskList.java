package ua.edu.sumdu.j2se.koval.tasks;

/**
 *  Класс що описує спискок "Задач" (через дву-зв'язний список).
 */
public class LinkedTaskList {

    private Node last;
    private Node first;
    private Node nodeForRemove;
    private int size;

    /**
     * Вкладений статичний клас що надає реалізацію "Вузла" для побудови дву-зв'язного списку.
     */
    private static class Node {
        final private Task item;
        private Node prev;
        private Node next;

        Node(Node prev, Task element, Node next) {
            this.item = element;
            this.prev = prev;
            this.next = next;
        }
    }

    /**
     * Метод, що додає до списку вказану "Задачу".
     */
    public void add(Task task) throws NullPointerException {
        if (task == null) {
            throw new NullPointerException();
        } else {
            Node l = last;
            Node newNode = new Node(l, task, null);
            last = newNode;
            if (l == null) {
                first = newNode;
            } else {
                l.next = newNode;
            }
            size++;
        }
    }

    /**
     * Метод, що видаляє зі списку вказану "Задачу", та впорядковує  зв'язки  між  "Вузлами" в дву-зв'язному списку.
     */
    public boolean remove(Task task) {
        boolean result = false;
        for (int i = 0; i < size(); i++) {
            if (getTask(i).getTitle().equals(task.getTitle()) && getTask(i).getTime() == task.getTime()) {
                if (i == 0) {
                    first = first.next;
                    result = true;
                    --size;
                } else {
                    if (i == (size() - 1)) {
                        last = last.prev;
                        result = true;
                        --size;
                    } else {
                        nodeForRemove.prev.next = nodeForRemove.next;
                        nodeForRemove.next.prev = nodeForRemove.prev;
                        --size;
                        result = true;
                    }
                }
                break;
            }
        }
        return result;
    }

    /**
     * Метод, що повертає кількість "Задач" у списку.
     */
    public int size() {
        return size;
    }

    /**
     * Метод, що повертає "Задачу", яка знаходиться на вказаному місці у
     * списку, перша задача має індекс 0.
     */
    public Task getTask(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size() || size() == 0) {
            throw new IndexOutOfBoundsException();
        } else {
            int counter = size;
            Node nodeFinder = last;
            while (index != counter - 1) {
                nodeFinder = nodeFinder.prev;
                --counter;
            }
            nodeForRemove = nodeFinder;
            return nodeFinder.item;
        }
    }

    /**
     Метод, що повертає підмножину "Задач", які заплановані на виконання
     хоча б раз після часу "from" і не пізніше ніж "to".
     */
    public LinkedTaskList incoming(int from, int to) throws IllegalArgumentException {
        if (from < 0 && to < 0 && from > to) {
            throw new IllegalArgumentException();
        } else {
            LinkedTaskList incomLinkTask = new LinkedTaskList();
            for (int i = 0; i < size(); i++) {
                int temp = getTask(i).nextTimeAfter(from);
                if (getTask(i) != null) {
                    if (temp != -1 && temp < to && getTask(i).isActive()) {
                        incomLinkTask.add(getTask(i));
                    }
                } else break;
            }
            return incomLinkTask;
        }
    }
}
