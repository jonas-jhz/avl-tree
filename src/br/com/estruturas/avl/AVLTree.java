package br.com.estruturas.avl;

import br.com.estruturas.queue.Queue;

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
        if (r == null) { // para quando encontrar o lugar de inserção em alguma chamada recursiva
            r = new AVLNode<T>(value); // novo nó com o valor criado
            status = true; // arvore cresceu (sempre estou usando o true como indicador de talvez precisar de um balanceamento (entrar no switch case))
        } else if (r.getInfo().compareTo(value) > 0) { // Inserção à esquerda
            r.setLeft(insertNode(r.getLeft(), value)); // chamada recursiva até achar mais a esquerda caso seja necessario
            if (status) { // se o indicador acender, executamos as tratativas para saber como setar o fatbal ou rotacionar a arvore
                switch (r.getFatBal()) { // switch definidor baseado no valor do fatball
                    case 1:
                        r.setFatBal(0); // se positivo, ao inserir no lado esquerdo o resultado de fatbal é zero
                        status = false; // removo o indicador
                        break;
                    case 0:
                        r.setFatBal(-1); // se estava balanceado, pesa mais para a esquerda
                        break;
                    case -1:
                        r = rotateRight(r); // se estava pesado para a esquerda, o no novo pesa mais ainda, sendo necessario rotação
                        status = false; // a rotação garante que esta balanceado
                        break;
                }
            }
        } else { // Inserção à direita
            r.setRight(insertNode(r.getRight(), value)); // chamada recursiva ate achar o mais a direita caso necessario
            if (status) { // mesmo esquema
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
        } else { // Rotação dupla (LR)
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

    // Rotação à direita (simples ou dupla)
    private AVLNode<T> rotateRight(AVLNode<T> a) {
        AVLNode<T> b = a.getLeft(), c;
        if (b.getFatBal() == -1) { // rotação simples
            a.setLeft(b.getRight());
            b.setRight(a);
            a.setFatBal(0);
            a = b;
        } else { //rotação dupla
            c = b.getRight();
            b.setRight(c.getLeft());
            c.setLeft(b);
            a.setLeft(c.getRight());
            c.setRight(a);
            if (c.getFatBal() == -1) {
                a.setFatBal(1);
            } else {
                a.setFatBal(0);
            }
            if (c.getFatBal() == 1) {
                b.setFatBal(-1);
            } else {
                b.setFatBal(0);
            }
            a = c;
        }
        a.setFatBal(0);
        this.status = false;
        return a;

    }


    public void remove(T value) { // remove chama a remoção privada, assim como insert
        if (!isEmpty()) {
            this.root = removeNode(this.root, value); // chamada privada
            this.status = false; // define que está balanceada
        }
        else{
            System.out.println("Arvore vazia");
        }
    }

    private AVLNode<T> removeNode(AVLNode<T> r, T value) { // recebe um ponteiro para o root de uma Arvore ou Si
        if (r == null) return null; // valor não encontrado (no inicio por conta da chamada recursiva)

        if (value.compareTo(r.getInfo()) < 0) { // busca na esquerda se value for menor que o nó atual (compareTo = -1)
            r.setLeft(removeNode(r.getLeft(), value)); // chamada recursiva para continuar percorrendo a esquerda ainda ate encontrar o nó
            if (status) r = balanceLDel(r); // se estiver precisando de balanceamento (status = true), chamamos essa função interna enviando o "r" atual desta chamada
        } else if (value.compareTo(r.getInfo()) > 0) { // busca à direita (funciona da mesma forma que esquerda)
            r.setRight(removeNode(r.getRight(), value)); // chamada recursiva agora para a direita
            if (status) r = balanceRDel(r); // se precisar de balanceamento agora para a direita
        } else { // Encontrou o nó a ser removido, identificamos se tem dois filhos
            if (r.getLeft() == null || r.getRight() == null) { // Caso de 0 ou 1 filho
                r = (r.getLeft() != null) ? r.getLeft() : r.getRight(); // ternario para identificar qual lado tem nó para que o r da chamada o receba
                status = true; // POSSIBILIDADE de remover o nó. Na proxima chamada (percorrendo a arvore) iremos balancear
            } else { // no caso de 2 filhos a gente encontra sucessor em ordem
                AVLNode<T> next = findMin(r.getRight()); // para encontrar o sucessor em ordem, pegamos o menor pegamos o menor do lado direito para garantir que fique ordenada
                r.setInfo(next.getInfo()); // em vez de simplesmente substituir, copiamos o valor do sucessor tambem pra nao precisar fazer multiplas referencias e tambem mantem ordenado
                r.setRight(removeNode(r.getRight(), next.getInfo())); // chamada recursiva para remover o sucessor original (reconectar os nós)
                if (status) r = balanceLDel(r); // se houver indicador de balanceamento, o procedimento deve resolver
            }
        }
        return r;
    }

    // Metodo para encontrar o menor nó em uma subárvore (sucessor em ordem)
    private AVLNode<T> findMin(AVLNode<T> r) {
        while (r.getLeft() != null) { // ate que o proximo a esquerda seja o ultimo
            r = r.getLeft();
        }
        return r;
    }

    private AVLNode<T> balanceRDel(AVLNode<T> r) { // usado para balancear no caso de deleção na esquerda
        switch (r.getFatBal()) {
            case -1:
                r.setFatBal(0); // antes a esquerda estava mais pesada, agora equilibrou
                break;
            case 0:
                r.setFatBal(1); // direita ficou um pouco mais pesada, mas ainda balanceada
                status = false; // não precisa subir mais (encerramos o rebalanceamento)
                break;
            case 1: // se ja estava pesado antes, com a remoção piorou
                AVLNode<T> rightChild = r.getRight(); // filho a direita para avaliar o proximo
                if (rightChild.getFatBal() >= 0) { //se tambem esta mais pesado tambem para a direita, significa que faremos uma rotação simples
                    r = rotateLeft(r); // rotação simples a esquerdsa
                } else { // no caso de -2, a rotação agora precisa ser dupla pois os sinais sao diferentes
                    r.setRight(rotateRight(rightChild)); // setamos a direita com uma rotaçao simples para a direita
                    r = rotateLeft(r); // concluimos rotacionando para a esquerda
                }
                break;
        }
        return r;
    }

    private AVLNode<T> balanceLDel(AVLNode<T> r) {
        switch (r.getFatBal()) {
            case 1:
                r.setFatBal(0);
                break;
            case 0:
                r.setFatBal(-1);
                status = false;
                break;
            case -1:
                AVLNode<T> leftChild = r.getLeft();
                if (leftChild.getFatBal() <= 0) {
                    r = rotateRight(r);
                } else {
                    r.setLeft(rotateLeft(leftChild));
                    r = rotateRight(r);
                }
                break;
        }
        return r;
    }


    public void inOrder() {
        if (isEmpty()) return;
        this.inOrder(this.root);
    }

    private void inOrder(AVLNode<T> r){
        if (r != null) { // entquanto nao finalizar na chamada recursiva
            inOrder(r.getLeft()); // função recursiva para acessar os nos da esquerda
            System.out.println(r.getInfo()); // printamos o resultado
            inOrder(r.getRight()); // função recursiva para acessar os nos da esquerda
        }
    }
/*
    public void passeioNivel() {
        if (root == null) {
            System.out.println("Árvore vazia.");
            return;
        }

        Queue<AVLNode<T>> queue = new Queue<AVLNode<T>>();
        queue.add(root);

        int r = 1;      // começa com a raiz
        int next = 0;

        while (!queue.isEmpty()) {
            AVLNode<T> current = queue.remove();
            if (current.getInfo() == null) {
                System.out.println("null "); //mudar posicao
            } else {
                System.out.print(current.getInfo() + " ");
            }
            //System.out.print(current.getInfo() + " ");

            if (current.getLeft() != null) {
                queue.add(current.getLeft());
                next++;
            }

            if (current.getRight() != null) {
                queue.add(current.getRight());
                next++;
            }

            r--;

            if (r == 0) {
                System.out.println(); // quebra de linha ao final do nível (consertar)
                r = next;
                next = 0;
            }
        }
    }
*/

/*
    private void inOrder(AVLNode<T> r) {
        if (r.getLeft() != null) {
            inOrder(r.getLeft());
            System.out.println(r.getInfo());
            if(r.getRight() != null) {
                inOrder(r.getRight());
            }
        } else {
            System.out.println(r.getInfo());
            if(r.getRight() != null) {
                inOrder(r.getRight());
            }
        }
    }
*/
    @Override
    public String toString() {
        return this.root == null ? "[(null)]" : "r" + this.root.toString();
    }


}








