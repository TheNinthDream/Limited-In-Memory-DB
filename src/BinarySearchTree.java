public class BinarySearchTree {
    private BSTNode root;

    public BinarySearchTree() {
        root = new BSTNode(null);
    }

    public BSTNode getRoot() {
        return root;
    }

    public BSTNode get(BSTNode r, Key k) {
        if (r != root && (r == null) || r.isLeaf()) return null; //base case, since leaf nodes hold no records - aside from root
        else if (root.getRecord().getKey().compareTo(k) == 0) {return root;}

        int keyComparison = k.compareTo(r.getRecord().getKey());

        //traversing through the tree
        if (keyComparison < 0) return get(r.getLeft(), k);
        else if (keyComparison > 0) return get(r.getRight(), k);
        else return r;
    }

    public void insert(BSTNode r, Record d) throws DictionaryException {
        BSTNode newNode = new BSTNode(d);
        if (r == root && r.getRecord() == null) {
            root = newNode;
            return;
        }
        insertionHelper(r, d, newNode);
    }

    private void insertionHelper(BSTNode r, Record d, BSTNode n) throws DictionaryException {
        int keyComparison;
        if (!(r == root) && r.getRecord() == null) {keyComparison = 0;} //the record does not exist, but ignore for root -- so a leaf node
        //this can only happen as a base case, when r's parent is the root
        // any other case, and it would be caught by the traversal later on
        else {keyComparison = d.getKey().compareTo(r.getRecord().getKey());}

        if (keyComparison == 0) {
            //base case: only if root is the parent of r
            // so compare the key to the root
            if (r.getParent() == root) {
                keyComparison = d.getKey().compareTo(r.getParent().getRecord().getKey());
            }
            // the rest will sort itself
        }

        //traversing through the tree
        if (keyComparison < 0) {
            if (r.getLeft().isLeaf()) {
                r.setLeft(n); //parent to child link
                n.setParent(r); //child to parent link

                //Null record leaves
                BSTNode newNodeR = new BSTNode(null);
                BSTNode newNodeL = new BSTNode(null);

                n.setRight(newNodeR);
                n.setLeft(newNodeL);
            }
            else {insertionHelper(r.getLeft(), d, n);}
        }
        else if (keyComparison > 0) {
            if (r.getRight().isLeaf()) {
                r.setRight(n); //parent to child link
                n.setParent(r); //child to parent link

                BSTNode newNodeR = new BSTNode(null);
                BSTNode newNodeL = new BSTNode(null);

                n.setRight(newNodeR);
                n.setLeft(newNodeL);
            }
            else {insertionHelper(r.getRight(), d, n);}
        }
        else {
            throw new DictionaryException("Tree already stores a record with same key as d");
        }
    }

    public void remove(BSTNode r, Key k) throws DictionaryException {
        if (r == null || (r.isLeaf() && r != root)) {throw new DictionaryException("Tree Does not store a record with key k");}

        int keyComparison;
        keyComparison = k.compareTo(r.getRecord().getKey());

        if (keyComparison < 0) remove(r.getLeft(), k);
        else if (keyComparison > 0) remove(r.getRight(), k);
        else {
            //case 1: no child, to check see if both left and right are dummy nodes
            if (r.getLeft().isLeaf() && r.getRight().isLeaf()) {
                BSTNode newNode = new BSTNode(null);
                if (!(r == root)) { //if r is not the root
                    //if the r is the left child, set the parent's left link to dummy node; otherwise the parent's right link to dummy node
                    if (r == r.getParent().getLeft()) {r.getParent().setLeft(newNode);}
                    else {r.getParent().setRight(newNode);}
                    newNode.setParent(r.getParent());
                }
                //if r is the root, given it's a leaf, there is no longer a tree
                else {root = newNode;}
            }
            //case 2: One sub-child
            else if (r.getLeft().isLeaf()) { //if left subtree of r has only a dummy node, there is no left subtree
                // r as root case
                if (r.getParent() == null) { //if r is the root node
                    root = r.getRight(); //set right child of r, as root
                    root.setParent(null); //set parent of root as null
                }
                //normal case
                //set right child's parent, as r's parent
                //set r's parents left or right as r's right child
                else {
                    r.getRight().setParent(r.getParent());
                    //if r is the left child, set the parent left link to r's right child
                    if (r == r.getParent().getLeft()) {r.getParent().setLeft(r.getRight());}
                    //otherwise set the parent's right child as r's right child
                    else {r.getParent().setRight(r.getRight());}
                }
            }
            else if (r.getRight().isLeaf()) {
                //r as root case
                if (r.getParent() == null) {
                    root = r.getLeft(); //set left child of r, as root
                    root.setParent(null); //set parent of root as null
                }
                //set left child's parent, as r's parent
                //set r's parents left or right as r's left child
                else {
                    r.getLeft().setParent(r.getParent());
                    //if r is the left child, set the parent left link to r's left child
                    if (r == r.getParent().getLeft()) {r.getParent().setLeft(r.getLeft());}
                    //otherwise set the parent's right child as r's left child
                    else {r.getParent().setRight(r.getLeft());}
                }
            }
            //case 3: Internal node with both children
            else {
                BSTNode sub = successor(r.getRight(), r.getRight().getRecord().getKey()); //create a successor
                r.setRecord(sub.getRecord());
                //remove the now duplicate successor value in right subtree
                remove(sub, sub.getRecord().getKey());
            }
        }

    }

    //may need to rename to account for misspelling in test case
    // min value in right subtree
    public BSTNode successor(BSTNode r, Key k) {
        if (r == null) return null;

        // if record is null, the r is a leaf node
        // to accommodate for k not in BST, set keyComparison to 0 to bypass further traversal I
        int keyComparison;
        if (r.getRecord() == null) {keyComparison = 0;}
        else {keyComparison = k.compareTo(r.getRecord().getKey());}

        //traversing through the tree
        if (keyComparison < 0) {return successor(r.getLeft(), k);}
        else if (keyComparison > 0) {return successor(r.getRight(), k);}

        //Account for the dummy nodes, and to handle key that doesn't exist in the BST II
        if (r.isLeaf()) {r = r.getParent();}

        //found node with key
        if (r.getRight().isLeaf()) { //if right child is a leaf (that holds no records), then there is no right subtree
            BSTNode p = r.getParent(); //so get the parent of r as the first candidate for successor
            //while p is lesser, keep going up
            while (p != null && k.compareTo(p.getRecord().getKey()) >= 0) {
                p = p.getParent();
            }
            //return the first ancestor larger
            return p;
        }
        else { //if right child is not a leaf, then there is a right subtree
            //find the smallest
            return smallest(r.getRight());
        }
    }

    //max value in left subtree
    public BSTNode predecessor(BSTNode r, Key k) {
        if (r == null) return null;

        // if record is null, the r is a leaf node
        // to accommodate for k not in BST, set keyComparison to 0 to bypass further traversal I
        int keyComparison;
        if (r.getRecord() == null) {keyComparison = 0;}
        else {keyComparison = k.compareTo(r.getRecord().getKey());}

        //traversing through the tree
        if (keyComparison < 0) {return predecessor(r.getLeft(), k);}
        else if (keyComparison > 0) {return predecessor(r.getRight(), k);}

        //Account for the dummy nodes, and to handle key that doesn't exist in the BST II
        if (r.isLeaf()) {r = r.getParent();}

        //found node with key
        if (r.getLeft().isLeaf()) { //if left child is a leaf (that holds no records), then there is no left subtree
            BSTNode p = r.getParent(); //so get the parent of r as the first candidate for predecessor
            //while parent is not null, and k is greater keep going to next ancestor
            while (p != null && k.compareTo(p.getRecord().getKey()) <= 0) {
                p = p.getParent();
            }
            //return the first ancestor that is lesser than k
            return p;
        }
        else { //if left child is not a leaf, then there is a left subtree
            //find the largest lesser node
            return largest(r.getLeft());
        }
    }

    public BSTNode smallest(BSTNode r) {
        if (r == null) return null;
        else if (r.isLeaf()) {return r;}
        else {
            BSTNode p = r;
            while (!p.getLeft().isLeaf()) {p = p.getLeft();}
            return p;
        }
    }

    public BSTNode largest(BSTNode r) {
        if (r == null) return null;
        else if (r.isLeaf()) {return r;}
        else {
            BSTNode p = r;
            while (!p.getRight().isLeaf()) {p = p.getRight();}
            return p;
        }
    }

}
