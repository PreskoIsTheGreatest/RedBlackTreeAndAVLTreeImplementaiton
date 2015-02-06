/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package avltree;

public class AVLTree<T extends Comparable<T>> {

    AVLNode<T> root;
    //Insertion

    public void insert(int k) {
        AVLNode<T> node = new AVLNode(k, null, null, null);
        insert(node);
    }

    private void insert(AVLNode<T> inserted) {
        if (root == null) {
            root = inserted;
            return;
        }
        AVLNode<T> y = null;
        AVLNode<T> x = root;

        while (x != null) {
            y = x;
            if (inserted.key.compareTo(x.key) < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        inserted.parent = y;
        if (y == null) {
            root = inserted;
        } else if (inserted.key.compareTo(y.key) < 0) {
            y.left = inserted;
        } else {
            y.right = inserted;
        }
        //Balancing
        updateHeights(inserted);
        balance(inserted);
    }

    //Searching
    public AVLNode<T> search(T key) {
        AVLNode<T> current = root;
        // While we haven't reached the end of the tree
        while (current != null) {
            // If we have found a node with a key equal to key
            if (current.key.equals(key)) // return that node and exit search(int)
            {
                return current;
            } // go left or right based on value of current and key
            else if (current.key.compareTo(key) < 0) {
                current = current.right;
            } // go left or right based on value of current and key
            else {
                current = current.left;
            }
        }
        // we have not found a node whose key is "key"
        return null;
    }

    public AVLNode<T> treeMinimum(AVLNode<T> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public void remove(T searched) {
        AVLNode<T> found = search(searched);
        // Declare variables
        AVLNode<T> x = null;
        AVLNode<T> y = null;
        // if found has one child or has none
        if (found.left == null || found.right == null) {
            y = found;
        } //if found has two children
        else {
            y = treeMinimum(found.right);
        }
        // Let x be the left or right child of y (y can only have one child)
        if (y.left != null) {
            x = y.left;
        } else {
            x = y.right;
        }
        // link x's parent to y's parent
        if (x != null) {
            x.parent = y.parent;
        }
        // If y's parent is nil, then x is the root
        if (y.parent == null) {
            root = x;
        } // else if y is a left child, set x to be y's left sibling
        else if (y.parent.left != null && y.parent.left == y) {
            y.parent.left = x;
        } // else if y is a right child, set x to be y's right sibling
        else if (y.parent.right != null && y.parent.right == y) {
            y.parent.right = x;
        }
        // if y != z, trasfer y's satellite data into z.
        if (y != found) {
            found.key = y.key;
        }
        if (y != null && y.parent != null) {
            updateHeights(y.parent);
            balance(y.parent);
        }
    }

    //AVL Code
    private void updateHeights(AVLNode node) {
        if (node == null) {
            return;
        }
        int left = -1, right = -1;
        if (node.left != null) {
            left = node.left.height;
        }
        if (node.right != null) {
            right = node.right.height;
        }
        node.height = Math.max(left, right) + 1;
        updateHeights(node.parent);
    }

    private int checkBalance(AVLNode node) {
        int subleft = -1, subright = -1;
        if (node.right != null) {
            subright = node.right.height;
        }
        if (node.left != null) {
            subleft = node.left.height;
        }

        int balance = subleft - subright;
        return balance;
    }

    private void balance(AVLNode node) {
        int balance = checkBalance(node);
        if (balance < -1) {
            if (checkBalance(node.right) == 1) {
                rotateRight(node.right);
            }
            rotateLeft(node);
        } else if (balance > 1) {
            if (checkBalance(node.left) == -1) {
                rotateLeft(node.left);
            }
            rotateRight(node);
        }
        if (node.parent != null) {
            balance(node.parent);
        }
    }

    //Rotations
    void rotateLeft(AVLNode node) {
        //In case it's a Left-Right rotation
        AVLNode keepAlive = null;
        if (node.left != null) {
            keepAlive = node.left;
        }
        AVLNode newNode = new AVLNode(node.key, null, node.right.left, node);
        node.left = newNode;
        node.key = node.right.key;
        //Handling two special cases
        if (node.right.left != null) {
            node.right.left.parent = newNode;
        }
        if (node.right.right != null) {
            node.right.right.parent = node;
        }
        node.right = node.right.right;
        if (keepAlive != null) {
            node.left.left = keepAlive;
            keepAlive.parent = node.left;
        }
        updateHeights(newNode);
    }

    void rotateRight(AVLNode node) {
        //In case it's a Right-Left rotation
        AVLNode keepAlive = null;
        if (node.right != null) {
            keepAlive = node.right;
        }

        AVLNode newNode = new AVLNode(node.key, node.left.right, null, node);
        node.right = newNode;
        node.key = node.left.key;
        //Handle two special cases
        if (node.left.right != null) {
            node.left.right.parent = newNode;
        }
        if (node.left.left != null) {
            node.left.left.parent = node;
        }
        node.left = node.left.left;
        if (keepAlive != null) {
            node.right.right = keepAlive;
            keepAlive.parent = node.right;
        }
        updateHeights(newNode);
    }

    //SIZE
    public int size() {
        if (root != null) {
            return size(root);
        }
        return 0;
    }

    private int size(AVLNode node) {
        if (node != null) {
            return 1 + size(node.left) + size(node.right);
        }
        return 0;
    }

    public void print() {
        if (root != null) {
            print(root, "root");
        }
    }

    public void print(AVLNode node, String direction) {
        if (node != null) {
            System.out.println(node.key + (node.parent != null ? node.parent.key + "" : " ") + direction);
            print(node.left, "left");
            print(node.right, "right");
        }
    }
}