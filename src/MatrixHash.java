import static java.lang.Math.*;

import java.util.ArrayList;
import java.util.Random;
public class MatrixHash {

    private int[][] matrix;
    // b: log2(size) of the table we want to construct, we want the
    //    table size to be a power of 2, so it's always rounded up.
    // u: The maximum number of bits for an entry to be hashed.
    private int b;
    private int u;

    // Initialize h with a random b×u binary matrix.
    public MatrixHash(int powerOf2, int maxBits) {
        this.b = powerOf2;
        this.u = maxBits;
        matrix = new int[b][u];
        Random random = new Random();
        // Randomly fill the matrix with 0's & 1's
        for(int i=0 ; i<b ; i++)
            for(int j=0 ; j<u ; j++)
                matrix[i][j] = random.nextInt(2);
    }

    public int getStringKey(String s) {
        int[] binaryString = new int[u], binaryKey = new int[b];
        int i, j, sum, key = 0;
        // Get the binary representation of a string by parity of
        // the integer representation of each character.
        for(i=0 ; i<s.length() ; i++)
            binaryString[i] = s.charAt(i) % 2;
        // Multiply matrix by binary representation to get the binary key.
        for(i=0 ; i<b ; i++){
            sum = 0;
            for (j=0 ; j<u ; j++)
                sum += matrix[i][j] * binaryString[j];
            binaryKey[i] = sum % 2;
        }
        // Get the integer value of the binary key.
        for (i=0 ; i<b ; i++)
            key += binaryKey[i]*pow(2,b-i-1);
        return key;
    }

    // --------------- Testing stuff ---------------
    public void printMatrix(){
        for (int[] row : matrix){
            for (int elem : row) System.out.print(elem + " ");
            System.out.println();
        }
    }
    // --------------- Testing stuff ---------------
}
