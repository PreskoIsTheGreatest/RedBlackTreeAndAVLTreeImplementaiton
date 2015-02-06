/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package avltree;

public class AVLNode<T extends Comparable<T>> {

    AVLNode<T> parent;
    AVLNode<T> right;
    AVLNode<T> left;
    int height = 0;
    T key;

    public AVLNode(T key, AVLNode left, AVLNode right, AVLNode parent) {
        this.key = key;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    public AVLNode() {
        parent = null;
        left = null;
        right = null;
    }

    public AVLNode(T key) {
        this();
        this.key = key;
    }
}
