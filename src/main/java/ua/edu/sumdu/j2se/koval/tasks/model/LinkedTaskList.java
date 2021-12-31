package ua.edu.sumdu.j2se.koval.tasks.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/**
 *  Класс що описує спискок "Задач" (через дву-зв'язний список).
 */
public class LinkedTaskList extends AbstractTaskList implements Cloneable{

    private Node last = null;
    private Node first = null;
    private Node nodeForRemove;
    private static int prevCloneCounter;

    public LinkedTaskList() {
        type = ListTypes.types.LINKED;
    }

    /**
     * Вкладений статичний клас що надає реалізацію "Вузла" для побудови дву-зв'язного списку.
     */
    private static class Node implements Cloneable {
        private Task item;
        private Node prev;
        private Node next;

        Node(Node prev, Task element, Node next) {
            this.item = element;
            this.prev = prev;
            this.next = next;
        }
        @Override
        public Node clone() throws CloneNotSupportedException {
            Node res = (Node) super.clone();
            res.item = item.clone();
            if (prevCloneCounter > 0) {
                res.prev = (res.prev == null) ? null : prev.clone();
                prevCloneCounter--;
            }
            else {
                res.next = (res.next == null) ? null : next.clone();
            }
            return res;
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
            ++taskCounter;
        }
    }

    /**
     * Метод, що видаляє зі списку вказану "Задачу", та впорядковує  зв'язки  між  "Вузлами" в дву-зв'язному списку.
     */
    public boolean remove(Task task) {
        boolean result = false;
        for (int i = 0; i < size(); i++) {
            if (ifEquals(i, task)) {
                if (size() == 1) {
                    first = null;
                    last = null;
                    break;
                }
                if (i == 0) {
                    first = first.next;
                    result = true;
                    --taskCounter;
                } else {
                    if (i == (size() - 1)) {
                        last = last.prev;
                        result = true;
                        --taskCounter;
                    } else {
                        nodeForRemove.prev.next = nodeForRemove.next;
                        nodeForRemove.next.prev = nodeForRemove.prev;
                        --taskCounter;
                        result = true;
                    }
                }
                break;
            }
        }
        return result;
    }

    /**
     * Метод, що повертає "Задачу", яка знаходиться на вказаному місці у
     * списку, перша задача має індекс  0.
     */
    public Task getTask(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size() || size() == 0) {
            throw new IndexOutOfBoundsException();
        } else {
            Node nodeFinder;
            int counter;
            if (index > taskCounter / 2) {
                counter = taskCounter - 1;
                nodeFinder = last; //
                while (index != counter) {
                    nodeFinder = nodeFinder.prev;
                    --counter;
                }
            } else {
                counter = 0;
                nodeFinder = first;
                while (index != counter) {
                    nodeFinder = nodeFinder.next;
                    ++counter;
                }
            }
            nodeForRemove = nodeFinder;
            return nodeFinder.item;
        }
    }

    /**
     * Перевизначення методу, реалізації ітератора.
     */
    @Override
    public Iterator<Task> iterator() {

        return new Iterator<>() {
            int nextCallCounter = 0;
            Node currentNodeIterator = first;

            @Override
            public boolean hasNext() {
                return (nextCallCounter < size() && currentNodeIterator.item != null );
            }

            @Override
            public Task next() {
                if (hasNext()) {
                    Task res = currentNodeIterator.item;
                    currentNodeIterator = currentNodeIterator.next;
                    ++nextCallCounter;
                    return res;
                } else throw new NoSuchElementException();
            }

            public void remove() throws IllegalStateException{
                if (nextCallCounter == 0) {
                    throw  new IllegalStateException();
                } else {
                    if (nextCallCounter == 1) {
                        first = first.next;
                        first.prev = null;
                    } else {
                        if (nextCallCounter == size()) {
                            last = last.prev;
                            last.next = null;
                        } else {
                            nodeForRemove = currentNodeIterator.prev;
                            nodeForRemove.prev.next = nodeForRemove.next;
                            nodeForRemove.next.prev = nodeForRemove.prev;
                        }
                    }
                }
                --taskCounter;
                --nextCallCounter;
            }
        };
    }

    /**
     * Перевизначення методу клонування "Задачі"
     */
    @Override
    public LinkedTaskList clone() throws CloneNotSupportedException {
        LinkedTaskList result = (LinkedTaskList) super.clone();
        prevCloneCounter = taskCounter;
        result.last = last.clone();
        result.first = first.clone();
        return result;
    }

    /**
     * Перевизначення методу, для отримання потоку з коллекції.
     */
    @Override
    public Stream<Task> getStream() {
        Task[] collToArr = new Task[size()];
        for (int i = 0; i < collToArr.length; i++) {
            collToArr[i] = getTask(i);
        }
        return Arrays.stream(collToArr);
    }
}
