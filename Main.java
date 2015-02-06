
import avltree.AVLTree;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Presko
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<Integer>();
        tree.insert(2);
        tree.insert(4);
        tree.insert(3);
        tree.print();
    }
}
