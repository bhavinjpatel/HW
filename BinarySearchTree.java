package javaapplication;
public class BinarySearchTree<E extends Comparable<E>> extends BinaryTree<E> {

    public BinarySearchTree() {
    }
    public BinarySearchTree(E val) {
        
    }
    public boolean search(Node<E> root, E key) {
        if (this.isEmpty())
            return false;
        if (root.getInfo().compareTo(key) == 0)
            return true;
        if (root.getInfo().compareTo(key) < 0)
           return search(root.getRight(), key);
        return search(root.getLeft(), key);
    }
    // returns true if BST has val else false.
    public boolean contains (E val) {
        return search(this.root, val);
    }
    Node<E> insertInto(Node<E> root, E key) {
        if (root == null){
            this.size++;
            return new Node<>(key);
        }
        if (root.getInfo().compareTo(key) > 0)
            root.addLeft(insertInto(root.getLeft(), key));
        else if (root.getInfo().compareTo(key) < 0)
            root.addRight(insertInto(root.getRight(), key));
        return root;
    }
    // inserts val at the right place satisfying search tree properties, should handle if the tree is empty
    // if value is already there then don’t insert it again
    public void insert(E val) {
        this.root = insertInto(this.root, val);
    }
    // returns the minimum value stored in the tree
    public E findMin() {
        Node<E> curr = this.root;
        while (curr.getLeft() != null)
            curr = curr.getLeft();
        return curr.getInfo();
    }

// returns the maximum value stored in the tree
    public E findMax() {
        Node<E> curr = this.root;
        while (curr.getRight() != null)
            curr = curr.getRight();
        return curr.getInfo();
    }
    
    public static void main(String[] args) {
        BinarySearchTree<Integer> bt= new BinarySearchTree<>();
        bt.insert(5);
        bt.insert(10);
        bt.insert(3);
        bt.insert(20);
        bt.insert(8);
        bt.insert(4);
        bt.postOrder(bt.root);
    }      
}