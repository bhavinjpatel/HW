package javaapplication;

public class BinaryTree<E> {

    int size;
    Node<E> root;
    
    public BinaryTree () {
     size =0;
     root=null;
    }
    
    public BinaryTree(E val) {
        root = new Node(val);
        size=1;
    }
    
    public boolean isEmpty() {
        return size==0;
    }
    
    public Node<E> addLeft(Node<E> node, E val) {
        Node<E> n = new Node(val);
        node.addLeft(n);
        size++;
        return node;
    }
    
    public Node<E> addRight(Node<E> node, E val) {
        
        Node<E> n = new Node(val);
        node.addRight(n);
        size++;
        return node ;
    } 
    
    public void preOrder(Node<E> n) {
        
        if (n==null) return;
        System.out.println(n.getInfo());
        preOrder(n.getLeft());
        preOrder(n.getRight());
        
    }
    
    public void inOrder(Node<E> n) {
        
        if (n==null) return;
        inOrder(n.getLeft());
        System.out.println(n.getInfo());
        inOrder(n.getRight());
        
    }
    
    public void postOrder(Node<E> n) {
        
        if (n==null) return;
        
        postOrder(n.getLeft());
        postOrder(n.getRight());
        System.out.println(n.getInfo());
        
    }
}
