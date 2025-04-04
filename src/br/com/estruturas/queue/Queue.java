package br.com.estruturas.queue;

public class Queue<T> {
    private QueueNode<T> head;
    private QueueNode<T> tail;

    public boolean isEmpty() {
        return this.head == null;
    }

    public void add(T info) {
        QueueNode<T> newNode = new QueueNode<>(info);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
    }

    public T remove() {
        if (isEmpty()) {
            return null;
        } else {
            T info = head.getInfo();
            head = head.getNext();
            if (head == null) {
                tail = null;
            }
            return info;
        }
    }
}
