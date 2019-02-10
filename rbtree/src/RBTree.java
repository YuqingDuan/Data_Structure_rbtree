/**
 * This program demonstrates an implementation of Red-Black tree by Java.
 * The red-black tree is an improvement on the binary search tree, so its nodes are similar to the binary search tree,
 * except that a boolean variable is added to it to represent the color of the nodes.
 * Red-black tree is a self-balancing binary tree.
 * The underlying data structures of TreeSet and TreeMap are red-black trees.
 * Elements stored in TreeSet and TreeMap have uniqueness and sorting
 * --How to ensure the uniqueness of elements in TreeSet and TreeMap?
 * Determine whether to store newly inserted elements based on whether the return value of compareTo() method is 0
 * --How to ensure all the elements in TreeSet and TreeMap be sorted?
 * 1.Natural ordering: T must have implemented the compareTo () method in the Comparable interface.
 *
 * @param <T> the type of the data(key) stored in nodes of a Red-black tree
 * @author Yuqing Duan
 * @version 1.0
 */
public class RBTree<T extends Comparable<T>> {
    // the root
    private RBNode<T> root;
    // define the color flag
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    /**
     * Define the nodes in a Red-Black tree.
     * <T extends Comparable<T>> means the T type must have implemented the compareTo () method in the Comparable interface.
     * For example, the following Student class:
     * public class Student implements Comparable{
     *
     * @Override public int compareTo(Object o) {
     * return this.age - ((Student)o).age;
     * }
     * }
     */
    private static final class RBNode<T extends Comparable<T>> {
        // color (red or black)
        boolean color;
        // the key in a node, the type of key can be sorted, like Integer...
        T key;
        // left child
        RBNode<T> left;
        // right child
        RBNode<T> right;
        // parent node
        RBNode<T> parent;

        /**
         * Constructor with parameters.
         *
         * @param color  node color (red or black)
         * @param key    the key in a node
         * @param left   left child
         * @param right  right child
         * @param parent parent node
         */
        public RBNode(boolean color, T key, RBNode<T> left, RBNode<T> right, RBNode<T> parent) {
            this.color = color;
            this.key = key;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        public T getKey() {
            return key;
        }

        @Override
        public String toString() {
            return " " + key + (color == RED ? "R" : "B");
        }
    }

    /**
     * Default constructor.
     */
    public RBTree() {
        root = null;
    }

    /**
     * getters and setters
     */
    public void setParent(RBNode<T> node, RBNode<T> parent) {
        if (node != null) {
            node.parent = parent;
        }
    }

    public RBNode<T> parentOf(RBNode<T> node) {
        return node != null ? node.parent : null;
    }

    public void setRed(RBNode<T> node) {
        if (node != null) {
            node.color = RED;
        }
    }

    public void setBlack(RBNode<T> node) {
        if (node != null) {
            node.color = BLACK;
        }
    }

    public void setColor(RBNode<T> node, boolean color) {
        if (node != null) {
            node.color = color;
        }
    }

    public boolean colorOf(RBNode<T> node) {
        return node != null ? node.color : BLACK;
    }

    /**
     * Judges node color.
     */
    public boolean isRed(RBNode<T> node) {
        return (node != null) && (node.color == RED) ? true : false;
    }

    public boolean isBlack(RBNode<T> node) {
        // return (node != null) && (node.color == BLACK) ? true : false;
        return !isRed(node);
    }

    /**
     * Preorder traversing.
     */
    public void preOrderTraversing(RBNode<T> current) {
        if (current != null) {
            System.out.print(current.key + " ");
            preOrderTraversing(current.left);
            preOrderTraversing(current.right);
        }
    }

    /**
     * Postorder traversing.
     */
    public void postOrderTraversing(RBNode<T> current) {
        if (current != null) {
            postOrderTraversing(current.left);
            postOrderTraversing(current.right);
            System.out.print(current.key + " ");
        }
    }

    /**
     * Inorder traversing.
     */
    public void inOrderTraversing(RBNode<T> current) {
        if (current != null) {
            inOrderTraversing(current.left);
            System.out.print(current.key + " ");
            inOrderTraversing(current.right);
        }
    }

    /**
     * Finds a node in the tree.
     *
     * @param key the data stored in the target node
     * @return target node
     */
    public RBNode<T> search(T key) {
        return search(root, key);
    }

    private RBNode<T> search(RBNode<T> current, T key) {
        if (current == null) {
            return null;
        }
        int cmp = key.compareTo(current.key);
        if (cmp < 0) {
            return search(current.left, key);
        } else if (cmp > 0) {
            return search(current.right, key);
        } else {
            return current;
        }
    }

    /**
     * Finds the minimum value.
     *
     * @param current current searching node
     * @return the minimum node
     */
    private RBNode<T> minNode(RBNode<T> current) {
        if (current == null) {
            return null;
        }

        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public T minValue() {
        RBNode<T> node = minNode(root);
        if (node != null) {
            return node.key;
        }
        return null;
    }

    /**
     * Finds the maximum value.
     *
     * @param current current searching node
     * @return the maximum node
     */
    private RBNode<T> maxNode(RBNode<T> current) {
        if (current == null) {
            return null;
        }

        while (current.right != null) {
            current = current.right;
        }
        return current;
    }

    public T maxValue() {
        RBNode<T> node = maxNode(root);
        if (node != null) {
            return node.key;
        }
        return null;
    }

    /**
     * Finds the successor node of the current node.
     *
     * @param current current node
     * @return the successor node
     * @reference https://www.jianshu.com/p/4cb355a6cad0
     */
    public RBNode<T> successor(RBNode<T> current) {
        // if "current" has a right child node, then the successor node is "the smallest node of the subtree with the right child node as the root"
        if (current.right != null) {
            return minNode(current.right);
        }

        // if "current" does not have a right child node, the following two situations will occur:
        // 1. "current" is the left child of its parent node, then the successor node of "current" is its parent node
        RBNode<T> p = current.parent;
        // 2. "current" is the right child of its parent node, "children switch to parents and parents switch to grandparents"
        // find "the lowest father node of "current", and the father node must have a left child (which is "current" node itself)
        // the lowest father node found is "the successor node of 'current'"
        while ((p != null) && (current != p.left)) {
            current = p;
            p = current.parent;
        }
        return p;
    }

    /**
     * Finds the successor node of the current node.
     *
     * @param current current node
     * @return the successor node
     * @reference https://www.jianshu.com/p/4cb355a6cad0
     */
    public RBNode<T> precursor(RBNode<T> current) {
        // if "current" has a left child node, then the precursor node is "the largest node of the subtree with the left child node as the root"
        if (current.left != null) {
            return maxNode(current.left);
        }

        // if "current" does not have a left child node, the following two situations will occur:
        // 1. "current" is the right child of its parent node, then the precursor node of "current" is its parent node
        RBNode<T> p = current.parent;
        // 2. "current" is the left child of its parent node, "children switch to parents and parents switch to grandparents"
        // find "the lowest father node of "current", and the father node must have a right child (which is "current" node itself)
        // the lowest father node found is "the precursor node of 'current'"
        while ((p != null) && (current != p.right)) {
            current = p;
            p = current.parent;
        }
        return p;
    }

    /**
     * Left rotate operation based on the current node.
     * Three steps in left rotate operation:
     * 1. Assign the left child node of y to the right child node of X and X to the parent node of the left child node of Y (the left child node of Y is not space-time).
     * 2. Assign the parent of X P (non-space-time) to the parent of y, and update the child of P to y (left or right).
     * 3. Set the left child node of y to X and the parent node of X to y.
     *
     * @param current the current node
     * @reference https://www.cnblogs.com/shianliang/p/9233117.html
     */

    /* Sketch Map:(current = x)
     *       p                       p
     *      /                       /
     *     x                       y
     *    / \                     / \
     *   lx  y       ----->      x  ry
     *      / \                 / \
     *     ly ry               lx ly
     */
    private void leftRotate(RBNode<T> current) {
        // assign the left child node of y to the right child node of X and X to the parent node of the left child node of Y
        RBNode<T> y = current.right;
        current.right = y.left;
        if (y.left != null) {
            y.left.parent = current;
        }

        // assign the parent of X P (non-space-time) to the parent of y, and update the child of P to y (left or right)
        y.parent = current.parent;
        // if the parent of X is empty, set Y to the parent
        if (current.parent == null) {
            root = y;
        } else {
            // if x is a left child node, then y is also set to a left child
            if (current == current.parent.left) {
                current.parent.left = y;
            } else {
                // otherwise, set Y to the right child node
                current.parent.right = y;
            }
        }

        // set the left child node of y to X and the parent node of X to y
        y.left = current;
        current.parent = y;
    }

    /**
     * Right rotate operation based on the current node.
     * Three steps in right rotate operation:
     * 1. Assign the right child node of x to the left child node of y and y to the parent node of the right child node of x (the right child node of x is not space-time).
     * 2. Assign the parent of y P (non-space-time) to the parent of x, and update the child of P to x (left or right).
     * 3. Set the right child node of x to y and the parent node of y to x.
     *
     * @param current the current node
     * @reference https://www.cnblogs.com/shianliang/p/9233117.html
     */

    /* Sketch Map:(current = y)
     *        p                   p
     *       /                   /
     *      y                   x
     *     / \                 / \
     *    x  ry   ----->      lx  y
     *   / \                     / \
     * lx  rx                   rx ry
     */
    private void rightRotate(RBNode<T> current) {
        // assign the right child node of x to the left child node of y and y to the parent node of the right child node of x
        RBNode<T> x = current.left;
        current.left = x.right;
        if (x.right != null) {
            x.right.parent = current;
        }
        // assign the parent of y P (non-space-time) to the parent of x, and update the child of P to x (left or right)
        x.parent = current.parent;
        // if the parent of y is empty, set x to the parent
        if (current.parent == null) {
            this.root = x;
        } else {
            // if y is a right child node, then x is also set to a right child
            if (current == current.parent.right) {
                current.parent.right = x;
            } else {
                // otherwise, set x to the left child node
                current.parent.left = x;
            }
        }

        // set the right child node of x to y and the parent node of y to x
        x.right = current;
        current.parent = x;
    }

    /**
     * Inserts a node into the red-black tree.
     * The process of inserting nodes into a red-black tree is the same as that of a binary search tree.
     *
     * @param key the data stored in the node
     * @return insert operation is successful or not
     */
    public boolean insert(T key) {
        RBNode<T> node = new RBNode<>(RED, key, null, null, null);
        if (node != null) {
            return insert(node);
        }
        return false;
    }

    private boolean insert(RBNode<T> node) {
        if (root == null) {
            root = node;
            return true;
        } else {
            RBNode<T> current = root;
            RBNode<T> parentNode = null;
            while (current != null) {
                parentNode = current;
                int cmp = node.key.compareTo(current.key);
                if (cmp < 0) {
                    current = current.left;
                    if (current == null) {
                        parentNode.left = node;
                        return true;
                    }
                } else {
                    current = current.right;
                    if (current == null) {
                        parentNode.right = node;
                        return true;
                    }
                }
            }
            // re-trim it into a balanced tree
            insertFixUp(node);
        }
        return false;
    }

    /**
     * Re-trims a unbalanced tree into a balanced tree by color tags and rotate operation.
     * In the following three cases, we are about to start discoloring and rotating:
     * 1. The parent node of the inserting node and its uncle node (another child of the grandfather node) are red.
     * 2. The parent node of the inserting node is red, the uncle node is black, and the inserting node is the right child of its parent node.
     * 3. The parent node of the insertion node is red, the uncle node is black, and the insertion node is the left child node of its parent node.
     *
     * @param node inserted node
     * @reference https://www.cnblogs.com/ysocean/p/8004211.html
     */
    private void insertFixUp(RBNode<T> node) {
        // define parent and grandparent
        RBNode<T> parent;
        RBNode<T> grandparent;

        // the parent node exists and the color of the parent node is red
        while (((parent = parentOf(node)) != null) && isRed(parent)) {
            // get the grandparent
            grandparent = parentOf(parent);

            // if the parent node is the left child of the grandparent node, the following "else" condition is the opposite
            if (parent == grandparent.left) {
                // get the uncle
                RBNode<T> uncle = grandparent.right;

                // case1: the uncle node is also red
                if (uncle != null && isRed(uncle)) {
                    // blacken parent and uncle nodes
                    setBlack(parent);
                    setBlack(uncle);
                    // red the grandparent node
                    setRed(grandparent);
                    // point the current node to its grandfather node
                    node = grandparent;
                    // start the algorithm again from the new current node
                    continue;
                }

                // case2: the uncle node is black, and the inserting node is the right child of its parent node
                if (isBlack(uncle) && parent.right == node) {
                    // do left rotate operation based on the parent node
                    leftRotate(parent);
                    // replace the parent node with inserted node to prepare for the following right rotate operation
                    RBNode<T> tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // case3: the uncle node is black, and the inserting node is the left child of its parent node
                if (isBlack(uncle) && parent.left == node) {
                    // blacken the parent node
                    setBlack(parent);
                    // red the grandparent node
                    setRed(grandparent);
                    // do right rotate operation based on the grandparent node
                    rightRotate(grandparent);
                }
            } else {// if the parent node is the right child of the grandfather node, it is the exact opposite and essentially the same
                RBNode<T> uncle = grandparent.left;

                // case1: the uncle node is also red
                if (uncle != null & isRed(uncle)) {
                    setBlack(parent);
                    setBlack(uncle);
                    setRed(grandparent);
                    node = grandparent;
                    continue;
                }

                // case2: the uncle node is black, and the inserting node is the left child of its parent node
                if (isBlack(uncle) && parent.left == node) {
                    rightRotate(parent);
                    RBNode<T> tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // case3: the uncle node is black, and the inserting node is the right child of its parent node
                if (isBlack(uncle) && parent.right == node) {
                    setBlack(parent);
                    setRed(grandparent);
                    leftRotate(grandparent);
                }
            }
        }
        // blacken the root
        setBlack(root);
    }

    /**
     * Removing nodes is the most complex operation.
     * Re-balance the tree after delete operation when necessary.
     * There are three cases of deleting nodes. The first two are relatively simple, but the third one is very complex.
     * 1. The node is a leaf node (no child node)
     * 2. The node has a child node
     * 3. The node has two children
     *
     * @param key the data stored in the node
     * @return if the remove operation execute successfully, return true; otherwise, return false
     */
    public boolean remove(T key) {
        RBNode<T> node = search(key);
        if (node != null) {
            return remove(node);
        }
        return false;
    }

    private boolean remove(RBNode<T> node) {
        RBNode<T> current = root;
        RBNode<T> parent = root;
        boolean isLeftChild = false;

        while (current.getKey() != null) {
            parent = current;
            if (current == null) {
                return false;
            }

            int cmp = current.getKey().compareTo(node.getKey());
            if (cmp < 0) {
                isLeftChild = true;
                current = current.left;
            } else {
                isLeftChild = false;
                current = current.right;
            }
        }

        if (current.left == null && current.right == null) {// delete nodes without any children
            if (current == root) {
                root = null;
            } else if (isLeftChild) {
                parent.left = null;
            } else {
                parent.right = null;
            }
            return true;
        } else if (current.left == null && current.right != null) {// delete a node with a child node(has right child)
            if (current == root) {
                root = current.right;
            } else if (isLeftChild) {
                parent.left = current.right;
            } else {
                parent.right = current.right;
            }
            return true;
        } else if (current.left != null && current.right == null) {// delete a node with a child node(has left child)
            if (current == root) {
                root = current.left;
            } else if (isLeftChild) {
                parent.left = current.left;
            } else {
                parent.right = current.left;
            }
            return true;
        } else if (current.left != null && current.right != null) {// delete a node with two children
            // get the successor of the current node
            RBNode<T> successor = successor(current);
            // exchange the color between current and successor
            boolean temp = successor.color;
            successor.color = current.color;
            current.color = temp;

            if (current == root) {
                successor = root;
            } else if (isLeftChild) {
                parent.left = successor;
            } else {
                parent.right = successor;
            }
            successor.left = current.left;

            if (successor.color == BLACK) {
                removeFixUp(successor.right, successor.parent);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Re-trims a unbalanced tree into a balanced tree by color tags and rotate operation.
     *
     * @param node   the node to be corrected is the child of the successor node, because the successor node is moved to the delete position
     * @param parent parent node of the successor
     */
    private void removeFixUp(RBNode<T> node, RBNode<T> parent) {
        RBNode<T> other;
        while ((node == null) || isBlack(node) && (node != root)) {
            // node becomes the left child
            if (parent.left == node) {
                // the sibling of node
                other = parent.right;

                // case1: the color of other is red
                if (isRed(other)) {
                    setBlack(other);
                    setRed(parent);
                    leftRotate(parent);
                    other = parent.right;
                }

                // case2: the color of other is black and the color of other' left and right children is black
                if (((other.left == null) || (isBlack(other.left))) && ((other.right == null) || isBlack(other.right))) {
                    setRed(other);
                    node = parent;
                    parent = parentOf(node);
                } else {
                    // case2: the color of other is black and the color of other' left child is red, right child is black
                    if (other.right == null || isBlack(other.right)) {
                        setBlack(other.left);
                        setRed(other);
                        rightRotate(other);
                        other = parent.right;
                    }

                    // case4: the color of other is black and the color of other' right child is red, left child is black
                    setColor(other, colorOf(parent));
                    setBlack(parent);
                    setBlack(other.right);
                    leftRotate(parent);
                    node = this.root;
                    break;
                }
            } else {
                // symmetry with the above
                other = parent.left;
                if (isRed(other)) {
                    setBlack(other);
                    setRed(parent);
                    rightRotate(parent);
                    other = parent.left;
                }
                if ((other.left == null || isBlack(other.left)) && (other.right == null || isBlack(other.right))) {
                    setRed(other);
                    node = parent;
                    parent = parentOf(node);
                } else {
                    if (other.left == null || isBlack(other.left)) {
                        setBlack(other.right);
                        setRed(other);
                        leftRotate(other);
                        other = parent.left;
                    }
                    setColor(other, colorOf(parent));
                    setBlack(parent);
                    setBlack(other.left);
                    rightRotate(parent);
                    node = this.root;
                    break;
                }
            }
        }
        if (node != null) {
            setBlack(node);
        }
    }
}

