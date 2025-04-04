package br.com.estruturas.avl;

class AVLNode<T extends Comparable<T>> {
    private AVLNode<T> left;
    private AVLNode<T> right;
    private T info;
    private int fatBal; // Fator de balanceamento (altura direita - altura esquerda)

    public AVLNode(T info) {
        this.info = info;
        this.fatBal = 0;
    }
/*

    public String toString() {
        return (this.left != null ? "[(" +this.left.toString() + "]" + this.left.getFatBal() +")" : " [(null)] ")
                + "[(" + this.info.toString() + ")"+ this.getFatBal() +"]"
                + (this.right != null ? "[(" +this.left.toString() + "]"+ this.right.getFatBal() +")" : " [(null)] ");
    }
*/
    @Override
    public String toString(){
        return (this.left == null ? "[(null)]" : "[(" +this.left.toString()+ ")"+ this.left.getFatBal() +"]") +
                "[(" + this.info.toString() + ")]" +
                (this.right == null ? "[(null)]" : "[(" + this.right.toString() + ")" +this.right.getFatBal()+ "]");
    }
    // Getters e Setters
    public void setInfo(T info) {
        this.info = info;
    }

    public T getInfo() {
        return info;
    }



    public void setLeft(AVLNode<T> left) {
        this.left = left;
    }

    public AVLNode<T> getLeft() {
        return left;
    }

    public void setRight(AVLNode<T> right) {
        this.right = right;
    }

    public AVLNode<T> getRight() {
        return right;
    }

    public void setFatBal(int fatBal) {
        this.fatBal = fatBal;
    }

    public int getFatBal() {
        return fatBal;
    }
}