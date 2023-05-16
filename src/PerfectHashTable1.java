public class PerfectHashTable1 {
    int elementCount, currentSize;
    String[] elementArray;
    MatrixHash hash;
    public PerfectHashTable1(String[] initialList, int maxStrLen) {
        int index;
        boolean collisions = true;
        elementCount = initialList.length;
        currentSize = (int) Math.pow(elementCount,2);
        // Get table size as the next power of 2 after the initial table size.
        int b = (int) Math.ceil(Math.log(currentSize)/Math.log(2));
        // Set table size to 2^b
        elementArray = new String[(int)Math.pow(2,b)];
        currentSize = elementArray.length;
        // Keep finding random hash functions until no collisions are reached.
        while(collisions){
            hash = new MatrixHash(b, maxStrLen);
            collisions = false;
            for (String entry : initialList) {
                index = hash.getStringKey(entry);
                // Only insert
                if (elementArray[index] == null) elementArray[index] = entry;
                else{ // Collision found
                    collisions = true;
                     break;
                }
            }
        }
    }

    public PerfectHashTable1(int initialSize, int maxStrLen) {
        elementCount = 0;
        currentSize = (int) Math.pow(initialSize,2);
        // Get table size as the next power of 2 after the initial table size.
        int b = (int) Math.ceil(Math.log(currentSize)/Math.log(2));
        // Set table size to 2^b
        hash = new MatrixHash(b, maxStrLen);
        elementArray = new String[(int)Math.pow(2,b)];
        currentSize = elementArray.length;
    }

    public boolean insert(String string) {
        elementCount++;
        int index = hash.getStringKey(string);
        // Element already exists.
        if (elementArray[index].equals(string)) return false;

        if (elementArray[index] != null) {
            
        }else{
            elementArray[index] = string;
        }
        return true;
    }
}