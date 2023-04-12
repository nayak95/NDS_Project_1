public class Entry {
    private int flowID;
    private int counter;
//      Getters Setter
    public int getFlowID() {
        return flowID;
    }
    public void setFlowID(int flowID) {
        this.flowID = flowID;
    }
    public int getCounter() {
        return counter;
    }
    public void setCounter(int counter) {
        this.counter = counter;
    }
//      Constructors
    public Entry(int flowID) {
        this.flowID = flowID;
        this.counter = 1;
    }
    public Entry(Entry e) {
        this.flowID = e.getFlowID();
        this.counter = e.getCounter();
    }

}
