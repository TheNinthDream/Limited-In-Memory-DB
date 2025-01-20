public class BSTDictionary implements BSTDictionaryADT {
    private BinarySearchTree BST;

    public BSTDictionary() {
        BST = new BinarySearchTree();
    }

    /**
     * Returns the Record with key k, or null if the Record is not in the dictionary.
     * @param k Key data type
     * @return Record
     */
    @Override
    public Record get(Key k) {
        BSTNode get = BST.get(BST.getRoot(), k);
        if (get != null) {
            return get.getRecord();
        }
        return null;
    }

    /**
     * Inserts d into the ordered dictionary. It throws a DictionaryException if a Record with the same Key as d is already in the dictionary.
     * @param d Record data type containing a key, and a label
     * @throws DictionaryException
     */
    @Override
    public void put(Record d) throws DictionaryException {
        BST.insert(BST.getRoot(), d);
    }

    /**
     * Removes the Record with Key k from the dictionary. It throws a DictionaryException if the Record is not in the dictionary.
     * @param k Key data type
     * @throws DictionaryException
     */
    @Override
    public void remove(Key k) throws DictionaryException {
        BST.remove(BST.getRoot(), k);
    }

    /**
     * Returns the successor of k (the Record from the ordered dictionary with the smallest Key larger than k);
     * it returns null if the given Key has no successor. The given Key DOES NOT need to be in the dictionary. *
     * @param k Key data type
     * @return Record
     */
    @Override
    public Record successor(Key k) {
        return BST.successor(BST.getRoot(), k).getRecord();
    }

    /**
     * Returns the predecessor of k (the Record from the ordered dictionary with the largest key smaller than k;
     * it returns null if the given Key has no predecessor. The given Key DOES NOT need to be in the dictionary.
     * @param k Key data type
     * @return Record
     */
    @Override
    public Record predecessor(Key k) {
        return BST.predecessor(BST.getRoot(), k).getRecord();
    }

    /**
     * Returns the Record with the smallest key in the ordered dictionary. Returns null if the dictionary is empty.
     * @return Record
     */
    @Override
    public Record smallest() {
        return BST.smallest(BST.getRoot()).getRecord();
    }

    /**
     * Returns the Record with the largest key in the ordered dictionary. Returns null if the dictionary is empty.
     * @return Record
     */
    @Override
    public Record largest() {
        return BST.largest(BST.getRoot()).getRecord();
    }
}
