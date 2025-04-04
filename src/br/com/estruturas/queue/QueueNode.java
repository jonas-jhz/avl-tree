package br.com.estruturas.queue;

class QueueNode<T> {

    private T info;
    private QueueNode<T> next;

    public QueueNode(T info){
        this.info = info;
        this.next = null;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public QueueNode<T> getNext() {
        return next;
    }

    public void setNext(QueueNode<T> next) {
        this.next = next;
    }
}