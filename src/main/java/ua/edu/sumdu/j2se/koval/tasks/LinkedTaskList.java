package ua.edu.sumdu.j2se.koval.tasks;

/**
 *  Класс що описує спискок "Задач" (через дву-зв'язний список).
 */
public class LinkedTaskList extends AbstractTaskList{

    private Node last;
    private Node first;
    private Node nodeForRemove;

    public LinkedTaskList() {
        type = ListTypes.types.LINKED;
    }

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
}
