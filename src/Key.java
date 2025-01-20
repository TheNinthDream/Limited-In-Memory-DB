public class Key {
    private String label;
    private int type;

    /**
     * A constructor which initializes a new Key object with the specified parameters.
     * Parameter theLabel must be converted to lowercase before being stored in instance variable label.
     * @param theLabel
     * @param theType
     */
    public Key(String theLabel, int theType) {
        label = theLabel.toLowerCase();
        type = theType;
    }

    /**
     * Accessor for instance variable label
     * @return label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Accessor for instance variable type
     * @return type
     */
    public int getType() {
        return type;
    }

    /**
     * Returns 0 if this Key object is equal to k, returns -1 if this Key object is smaller than k, and it returns 1 otherwise.
     * @param k
     * @return int
     */
    public int compareTo(Key k) {
        int stringCompare = this.label.compareTo(k.getLabel());
        if (this.type == k.getType() && stringCompare == 0) {return 0;}
        //else if (stringCompare == 0 && this.type < k.getType()) {return -1;}
        //else if (stringCompare < 0) {return -1;}
        //else {return 1;}
        else return (stringCompare == 0 && this.type < k.getType() || (stringCompare < 0) ? -1 : 1);
    }

}
