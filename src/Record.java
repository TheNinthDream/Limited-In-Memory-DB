public class Record {
    private Key theKey;
    private String data;

    /**
     * A constructor which initializes a new Record object with the specified parameters.
     * @param k
     * @param theData
     */

    public Record(Key k, String theData) {
        theKey = k;
        data = theData;
    }
    /**
     * Accessor for instance variable key
     * @return theKey
     */
    public Key getKey() { return theKey;}

    /**
     * Accessor for instance variable data
     * @return data
     */
    public String getDataItem() { return data;}
}
