package visual.hashtable;

/**
 * Visual Hashtable
 * Created by Connor Crawford on 11/9/15.
 */
public class MyHashtable {

    public static int SIZE = 100;
    private int free = SIZE;
    private Double[] hashtable = new Double[SIZE];
    private int[] collisions = new int[SIZE];

    public MyHashtable() {
        for (int i = 0; i < SIZE; i++)
            collisions[i] = -1;
    }

    /**
     * Calculates the index for an element using the hash function round(v)
     * @param v the value for which the index is being found
     * @return the index for the value
     */
    private int getIndex(double v) {
        return (int)Math.round(v) % SIZE;
    }

    /**
     * Inserts a value into hashtable
     * @param v the value to be inserted
     * @param index the index where the value shoul be inserted
     */
    private void insert(double v, int index) {
        int collisions = 0;
        while (hashtable[index] != null) {
            collisions++;
            index++;
            if (index == SIZE)
                index = 0;
        }
        this.collisions[index] = collisions;
        hashtable[index] = v;
    }

    /**
     * Inserts 10 values into the hashtable
     */
    void insert10() {
        if (free >= 10) {
            for (int i = 0; i < 10; i++) {
                double v = Math.round(Math.random() * 1000);
                int index = getIndex(v);
                insert(v, index);
            }
            free -= 10;
        }
    }

    /**
     * Gets the number of collisions that occurred at a given index
     * @param index the index in question
     * @return the number of collisions at that index
     */
    public int getCollisionsAtIndex(int index) {
        return collisions[index];
    }

    /**
     * Gets the average number of collisions in the entire hashtable
     * @return the average number of collisions
     */
    public float getAvgCollisions() {
        int sum = 0;
        for (int v : collisions) {
            if (v != -1)
                sum += v;
        }
        float avg = (float)sum/(SIZE - free);
        return (float) (Math.round(avg * 100) / 100.0);
    }

}
