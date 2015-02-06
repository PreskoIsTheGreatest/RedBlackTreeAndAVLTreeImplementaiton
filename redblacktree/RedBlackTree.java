package redblacktree;


// Inclusions
public class RedBlackTree<T extends Comparable<T>> {

    // Root initialized to nil.
    private RedBlackNode<T> nil = new RedBlackNode<T>();
    private RedBlackNode<T> root = nil;

    public RedBlackTree() {
        root.left = nil;
        root.right = nil;
        root.parent = nil;
    }

    private void leftRotate(RedBlackNode<T> x) {
        RedBlackNode<T> y;
        y = x.right;
        //right child of x is now left child of y
        x.right = y.left;

        //if y has left child so its parent will be now x becouse of above
        if (!isNil(y.left)) {
            y.left.parent = x;
        }
        //setting y parent to be x parent
        y.parent = x.parent;

        // x's parent is nul
        if (isNil(x.parent)) {
            root = y;
        } // x is the left child of it's parent
        else if (x.parent.left == x) {
            x.parent.left = y;
        } // x is the right child of it's parent.
        else {
            x.parent.right = y;
        }
        // setting x to be left child of y
        y.left = x;
        //setting x parent to be y
        x.parent = y;
    }

    private void rightRotate(RedBlackNode<T> y) {
        //x is the left child of y
        RedBlackNode<T> x = y.left;
        //the left child of y is x right child
        y.left = x.right;
        // if x has right child it becomes y left child
        // so that x right can point to y
        if (!isNil(x.right)) {
            x.right.parent = y;
        }
        //settig x parent to be x parent
        x.parent = y.parent;
        // y.parent is nil
        if (isNil(y.parent)) {
            root = x;
        } // y is a right child of it's parent.
        else if (y.parent.right == y) {
            y.parent.right = x;
        } // y is a left child of it's parent.
        else {
            y.parent.left = x;
        }
        //x right child is y
        x.right = y;
        // y parent is now x
        y.parent = x;
    }

    public void insert(T key) {
        insert(new RedBlackNode<T>(key));
    }

    private void insert(RedBlackNode<T> z) {

        RedBlackNode<T> y = nil;
        RedBlackNode<T> x = root;
        //Searching for place of the new element iterative way
        while (!isNil(x)) {
            //setting y to the parent
            y = x;
            //Going left the tree
            if (z.key.compareTo(x.key) < 0) {
                x = x.left;
            } // Going right the tree
            else {
                x = x.right;
            }
        }
        //At the end y will be z parent
        z.parent = y;
        //Depending on z parent key (y) we put it left,right or on the place of the root
        if (isNil(y)) {
            root = z;
        } else if (z.key.compareTo(y.key) < 0) {
            y.left = z;
        } else {
            y.right = z;
        }

        //Setting left and right child of the new element to be nil a its color to be red
        z.left = nil;
        z.right = nil;
        z.color = RedBlackNode.RED;
        //Balancing the tree
        insertFixup(z);
    }

    //Balancing the tree
    private void insertFixup(RedBlackNode<T> z) {
        RedBlackNode<T> y = nil;
        // While there is a violation of the RedBlackTree properties
        while (z.parent.color == RedBlackNode.RED) {
            // If z's parent is the the left child of it's parent.
            if (z.parent == z.parent.parent.left) {
                // Initialize y to z 's cousin
                y = z.parent.parent.right;
                // when new element is left child of his parent
                // and his parent is red left child of his parent(grandfather of new el.)
                // and his uncle is red right child of his parent(grandfather of new el.)
                if (y.color == RedBlackNode.RED) {
                    //Doing recoloring
                    z.parent.color = RedBlackNode.BLACK;
                    y.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    z = z.parent.parent;
                } //when new element is right child of his parent
                //his parent is red left child of his parent(grandfather of new el.)
                // his uncle is black right child of his parent(grandfather of new el.)
                else if (z == z.parent.right) {
                    // left rotation around new el. parent
                    z = z.parent;
                    leftRotate(z);
                } // new el. is left child of his parent
                // his parent is red left child of his parent(grandfather of new el.)
                // his uncle is black right child of his parent(grandfather of new el.)
                else {
                    // recolor and rotate round new el. grandfather
                    z.parent.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    rightRotate(z.parent.parent);
                }
            } // If z's parent is the right child of it's parent.
            else {
                // Initialize y to z's cousin
                y = z.parent.parent.left;
                // new el.right child of his parent
                // his parent is right red child of his parent(new el.grandfather)
                // his uncle is left black child of his parent(new el.grandfather)
                if (y.color == RedBlackNode.RED) {
                    //Recolor
                    z.parent.color = RedBlackNode.BLACK;
                    y.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    z = z.parent.parent;
                } //new el. left child of his parent
                //his parent is right red child of his parent(new el.grandfather)
                //his uncle is left black child of his parent(new el.grandfather)
                else if (z == z.parent.left) {
                    //right rotate
                    z = z.parent;
                    rightRotate(z);
                } // new el. right child of his parent
                // his parent is right red child of his parent(new el. grandfather)
                //his uncle is left black child of his parent(new el. grandfather)
                else {
                    // recolor and rotate around new el. grandfather
                    z.parent.color = RedBlackNode.BLACK;
                    z.parent.parent.color = RedBlackNode.RED;
                    leftRotate(z.parent.parent);
                }
            }
        }
        // Color root black at all times
        root.color = RedBlackNode.BLACK;

    }

    public RedBlackNode<T> treeMinimum(RedBlackNode<T> node) {
        while (!isNil(node.left)) {
            node = node.left;
        }
        return node;
    }

    //Binary removal
    public void remove(T searched) {
        RedBlackNode<T> found = search(searched);
        // Declare variables
        RedBlackNode<T> x = nil;
        RedBlackNode<T> y = nil;
        // if found has one child or has none
        if (isNil(found.left) || isNil(found.right)) {
            y = found;
        } //if found has two children
        else {
            y = treeMinimum(found.right);
        }
        // Let x be the left or right child of y (y can only have one child)
        if (!isNil(y.left)) {
            x = y.left;
        } else {
            x = y.right;
        }
        // link x's parent to y's parent
        x.parent = y.parent;
        // If y's parent is nil, then x is the root
        if (isNil(y.parent)) {
            root = x;
        } // else if y is a left child, set x to be y's left sibling
        else if (!isNil(y.parent.left) && y.parent.left == y) {
            y.parent.left = x;
        } // else if y is a right child, set x to be y's right sibling
        else if (!isNil(y.parent.right) && y.parent.right == y) {
            y.parent.right = x;
        }
        // if y != z, trasfer y's satellite data into z.
        if (y != found) {
            found.key = y.key;
        }
        // If y's color is black, it is a violation of the
        // RedBlackTree properties so call removeFixup()
        if (y.color == RedBlackNode.BLACK) {
            removeFixup(x);
        }
    }

    private void removeFixup(RedBlackNode<T> x) {
        RedBlackNode<T> w;
        while (x != root && x.color == RedBlackNode.BLACK) {
            // if x is it's parent's left child
            if (x == x.parent.left) {
                // set w = x's sibling
                w = x.parent.right;
                //x is black left
                //w is red right
                if (w.color == RedBlackNode.RED) {
                    //recolor and rotate around x parent
                    w.color = RedBlackNode.BLACK;
                    x.parent.color = RedBlackNode.RED;
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                // x is black left
                // w is right black
                //both w children are black
                if (w.left.color == RedBlackNode.BLACK &&
                        w.right.color == RedBlackNode.BLACK) {
                    w.color = RedBlackNode.RED;
                    x = x.parent;
                } else {
                    //x is left black
                    //w is right black
                    //w right is is black
                    //w left is red
                    if (w.right.color == RedBlackNode.BLACK) {
                        w.left.color = RedBlackNode.BLACK;
                        w.color = RedBlackNode.RED;
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    // w is right black
                    //w.right is red
                    //x is left black
                    //w.right.right is black
                    w.color = x.parent.color;
                    x.parent.color = RedBlackNode.BLACK;
                    w.right.color = RedBlackNode.BLACK;
                    leftRotate(x.parent);
                    x = root;
                }
            } // if x is it's parent's right child
            else {
                // set w to x's sibling
                w = x.parent.left;
                // w red
                // x black
                if (w.color == RedBlackNode.RED) {
                    //recolor
                    w.color = RedBlackNode.BLACK;
                    x.parent.color = RedBlackNode.RED;
                    //rotate
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                //w is black 
                //x is black
                //w children are black
                if (w.right.color == RedBlackNode.BLACK &&
                        w.left.color == RedBlackNode.BLACK) {
                    //recolor
                    w.color = RedBlackNode.RED;
                    x = x.parent;
                } else {
                    //w is black
                    //w left is black
                    //w right is red
                    if (w.left.color == RedBlackNode.BLACK) {
                        //recolor
                        w.right.color = RedBlackNode.BLACK;
                        w.color = RedBlackNode.RED;
                        //rotate
                        leftRotate(w);
                        w = x.parent.left;
                    }
                    // w is black
                    // w left is black
                    // x is black
                    w.color = x.parent.color;
                    //recolor
                    x.parent.color = RedBlackNode.BLACK;
                    w.left.color = RedBlackNode.BLACK;
                    //rotate
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }

        // set x to black to ensure there is no violation of
        // RedBlack tree Properties
        x.color = RedBlackNode.BLACK;
    }// end removeFixup(RedBlackNode x)

    //Simple iterative search in binary tree
    public RedBlackNode<T> search(T key) {
        RedBlackNode<T> current = root;
        // While we haven't reached the end of the tree
        while (!isNil(current)) {
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
    }// end search

    //Check is node nil (not null, prevent from null pointers)
    private boolean isNil(RedBlackNode node) {
        return node == nil;
    }

    //Simple binary size recursive finding
    public int size() {
        if (!isNil(root)) {
            return size(root);
        }
        return 0;
    }

    private int size(RedBlackNode node) {
        if (!isNil(node)) {
            return 1 + size(node.left) + size(node.right);
        }
        return 0;
    }

    public void print() {
        print(root, "root");
    }

    private void print(RedBlackNode node, String direction) {
        if (!isNil(node)) {
            System.out.println(node.key + " " + node.parent.key + " " + direction);
            print(node.left, "left");
            print(node.right, "right");
        }
    }
}

