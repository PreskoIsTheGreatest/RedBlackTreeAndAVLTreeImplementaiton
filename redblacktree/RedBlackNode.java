package redblacktree;

/**
 *
 * @author Presko
 */
public class RedBlackNode<T extends Comparable<T>> {

    public static final int BLACK = 0;
    public static final int RED = 1;
    public T key;
    RedBlackNode<T> parent;
    RedBlackNode<T> left;
    RedBlackNode<T> right;
    public int color;

    RedBlackNode() {
        color = BLACK;
        parent = null;
        left = null;
        right = null;
    }

    RedBlackNode(T key) {
        this();
        this.key = key;
    }
}
