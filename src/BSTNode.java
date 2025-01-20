public class BSTNode {
    private BSTNode left;
    private BSTNode right;
    private BSTNode parent;
    private Record item;

    /**
     * The constructor for BSTNodes
     * If item exists, node is not a dummy node and gets leaves
     * @param item
     */
    public BSTNode(Record item) {
        if (item == null) {
            this.left = null;
            this.right = null;
            this.parent = null;
            this.item = null;
        }
        else {
            this.left = null;
            this.right = null;
            this.parent = null;
            this.item = item;
            leaves();
        }
    }
    //private method for populating dummy leaves
    private void leaves() {
        //create dummy leaves
        BSTNode temp = new BSTNode(null);
        BSTNode temp2 = new BSTNode(null);
        this.left = temp;
        temp.parent = this;

        this.right = temp2;
        temp2.parent = this;
    }

    /**
     * Returns the Record object stored in this node.
     * @return item
     */
    public Record getRecord() {
        return item;
    }

    /**
     * Stores the given record in this node.
     * @param d
     */
    public void setRecord(Record d) {
        this.item = d;
    }

    /**
     * Returns the left child
     * @return left
     */
    public BSTNode getLeft() {
        return left;
    }

    /**
     * Returns the right child
     * @return right
     */
    public BSTNode getRight() {
        return right;
    }

    /**
     * Returns the parent
     * @return parent
     */
    public BSTNode getParent() {
        return parent;
    }

    /**
     * Sets the left child to the specified value
     * @param u
     */
    public void setLeft(BSTNode u) {
        this.left = u;
    }

    /**
     * Sets the right child to the specified value
     * @param u
     */
    public void setRight(BSTNode u) {
        this.right = u;
    }

    /**
     * Sets the parent to the specified value
     * @param u
     */
    public void setParent(BSTNode u) {
        this.parent = u;
    }

    /**
     * Returns if this node is a leaf
     * @return boolean
     */
    public boolean isLeaf() {
        return left == null && right == null;
    }

}
