package Hashing;
import static java.lang.Math.*;
import java.util.Random;
public class MatrixHash {
    private final boolean[][] matrix;
    // b: log2(size) of the table we want to construct, we want the
    //    table size to be a power of 2, so it's always rounded up.
    // u: The maximum number of bits for an entry to be hashed.
    private final int b;
    private static final int u = 32;

    // Initialize h with a random b×u binary matrix.
    public MatrixHash(int mapTo) {
        this.b = mapTo;
        matrix = new boolean[b][u];
        Random random = new Random();
        // Randomly fill the matrix with 0's & 1's
        for(int i=0 ; i<b ; i++)
            for(int j=0 ; j<u ; j++)
                matrix[i][j] = random.nextBoolean();
    }

    public int getHashValue(int data) {
        int i, j, key = 0;
        String binaryData = Integer.toBinaryString(data);
        boolean[] binaryHashValue = new boolean[b];
        // Multiply matrix by binary representation to get the binary key.
        for(i=0 ; i<b ; i++)
            for (j=0 ; j<binaryData.length() ; j++)
                if (matrix[i][j] && binaryData.charAt(j) == '1')
                    binaryHashValue[i] = !binaryHashValue[i];
        // Get the integer value of the binary key.
        for (i=0 ; i<b ; i++)
            key += (binaryHashValue[i])? pow(2,b-i-1) : 0;
        return key;
    }

    // Overloaded Hash value to accept Strings
    public int getHashValue(String stringData) {
        int data = stringData.hashCode();
        return getHashValue(data);
    }

    // --------------- Testing stuff ---------------
    public void printMatrix(){
        for (boolean[] row : matrix){
            for (boolean elem : row)
                System.out.print(elem + " ");
            System.out.println();
        }
    }
    // --------------- Testing stuff ---------------
}
