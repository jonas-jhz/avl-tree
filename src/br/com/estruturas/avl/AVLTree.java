package br.com.estruturas.avl;

public class AVLTree<T extends Comparable<T>> { // Classe Arvore AVL genérica que traz o compareTo
    private AVLNode<T> root; // Raiz
    private boolean status; // Indica se a árvore precisa de balanceamento

    public boolean isEmpty() { // Verifica se a árvore está vazia
        return this.root == null; // Se a raiz é nula, não há nós
    }


    public void insert(T value) {// Inserção pública mas chama métdo privado
        if (isEmpty()) { //  se for vazia, a raiz recebe um novo nó com o valor em parâmetro
            this.root = new AVLNode<T>(value);
        } else { // se não for vazia, raiz recebe metodo privado insertNode
            this.root = insertNode(root, value); // raiz da arvore recebe novo nó inserido ordenado
            this.status = false; // setado como false pois a inserção garante o balanceamento
        }
    }

    private AVLNode<T> insertNode(AVLNode<T> r, T value) { // inserção recursiva com balanceamento
        if (r == null) {
            r = new AVLNode<T>(value);
            status = true; // Árvore cresceu
        }
        else if (r.getInfo().compareTo(value) > 0) { // Inserção à esquerda
            r.setLeft(insertNode(r.getLeft(), value));
            if (status) {
                switch (r.getFatBal()) {
                    case 1:
                        r.setFatBal(0);
                        status = false;
                        break;
                    case 0:
                        r.setFatBal(-1);
                        break;
                    case -1:
                        r = rotateRight(r);
                        status = false;
                        break;
                }
            }
        }
        else { // Inserção à direita
            r.setRight(insertNode(r.getRight(), value));
            if (status) {
                switch (r.getFatBal()) {
                    case -1:
                        r.setFatBal(0);
                        status = false;
                        break;
                    case 0:
                        r.setFatBal(1);
                        break;
                    case 1:
                        r = rotateLeft(r);
                        status = false;
                        break;
                }
            }
        }
        return r;
    }

    // Rotação à esquerda (simples ou dupla)
    private AVLNode<T> rotateLeft(AVLNode<T> a) {
        AVLNode<T> b = a.getRight();
        if (b.getFatBal() == 1) { // Rotação simples (LL)
            a.setRight(b.getLeft());
            b.setLeft(a);
            a.setFatBal(0);
            a = b;
        }
        else { // Rotação dupla (LR)
            AVLNode<T> c = b.getLeft();
            b.setLeft(c.getRight());
            c.setRight(b);
            a.setRight(c.getLeft());
            c.setLeft(a);

            // Atualiza fatores de balanceamento
            if (c.getFatBal() == 1) {
                a.setFatBal(-1);
            } else {
                a.setFatBal(0);
            }
            if (c.getFatBal() == -1) {
                b.setFatBal(1);
            } else {
                b.setFatBal(0);
            }
            a = c;
        }
        a.setFatBal(0);
        status = false;
        return a;
    }

    // Rotação à direita (simples ou dupla) - Implementação similar à rotateLeft
    private AVLNode<T> rotateRight(AVLNode<T> a) {
        AVLNode<T> b = a.getLeft(), c;
        if(b.getFatBal() == -1){ // rotação simples
            a.setLeft(b.getRight());
            b.setRight(a);
            a.setFatBal(0);
            a = b;
        }
        else{ //rotação dupla
            c = b.getRight();
            b.setRight(c.getLeft());
            c.setLeft(b);
            a.setLeft(c.getRight());
            c.setRight(a);
            if(c.getFatBal() == -1){
                a.setFatBal(1);
            }
            else{
                a.setFatBal(0);
            }
            if(c.getFatBal() == 1){
                b.setFatBal(-1);
            }
            else{
                b.setFatBal(0);
            }
            a = c;
        }
        a.setFatBal(0);
        this.status = false;
        return a;

    }











    public void remove(T value) { // remove chama a remoção privada, assim como insert
        if (!isEmpty()) { // como dito pela senhora, é dever do manipulador da arvore não remover uma arvore vazia
            this.root = removeNode(this.root, value); // chamada privada
            this.status = false; // Define que está balanceada
        }
        //se estiver vazia, o procedimento não faz nada
    }

    private AVLNode<T> removeNode(AVLNode<T> r, T value) { // recebe um ponteiro para o root de uma Arvore ou Si
        if (r == null) return null; // Valor não encontrado (no inicio por conta da chamada recursiva)

        if (value.compareTo(r.getInfo()) < 0) { // Busca na esquerda se value for menor que o nó atual (compareTo = -1)
            r.setLeft(removeNode(r.getLeft(), value)); // chamada recursiva para continuar percorrendo a esquerda ainda ate encontrar o nó
            if (status) r = balanceAfterDeletion(r); // se estiver precisando de balanceamento (status = true), chamamos essa função interna enviando o "r" atual desta chamada
        }
        else if (value.compareTo(r.getInfo()) > 0) { // busca à direita (funciona da mesma forma que esquerda)
            r.setRight(removeNode(r.getRight(), value));
            if (status) r = balanceAfterDeletion(r);
        }
        else { // Encontrou o nó a ser removido, identificamos se tem dois filhos
            if (r.getLeft() == null || r.getRight() == null) { // Caso de 0 ou 1 filho
                r = (r.getLeft() != null) ? r.getLeft() : r.getRight(); // ternario para identificar qual lado tem nó para que o r da chamada o receba
                status = true; // POSSIBILIDADE de remover o nó. Na proxima chamada (percorrendo a arvore) iremos balancear
            }
            else { // no caso de 2 filhos a gente encontra sucessor em ordem
                AVLNode<T> successor = findMin(r.getRight());
                r.setInfo(successor.getInfo());
                r.setRight(removeNode(r.getRight(), successor.getInfo()));
                if (status) r = balanceAfterDeletion(r);
            }
        }
        return r;
    }

    // Metodo para encontrar o menor nó em uma subárvore (sucessor in-order)
    private AVLNode<T> findMin(AVLNode<T> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    // Balanceamento após remoção
    private AVLNode<T> balanceAfterDeletion(AVLNode<T> r) {
        switch (r.getFatBal()) {
            case -1:
                r.setFatBal(0);
                break;
            case 0:
                r.setFatBal(1);
                status = false;
                break;
            case 1:
                AVLNode<T> rightChild = r.getRight();
                if (rightChild.getFatBal() >= 0) {
                    r = rotateLeft(r);
                } else {
                    r.setRight(rotateRight(rightChild));
                    r = rotateLeft(r);
                }
                break;
        }
        return r;
    }









}