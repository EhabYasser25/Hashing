package Hashing;
import java.util.Random;

public class ModularHash {
    private final int a, b, m;
    private final static int p = Integer.MAX_VALUE;

    public ModularHash(int tableSize) {
        m = tableSize;
        Random rand = new Random();
        a = rand.nextInt(1,Integer.MAX_VALUE);
        b = rand.nextInt(1,Integer.MAX_VALUE);
    }

    public int getHashValue(int data) {
        // Mask the negative bit in case of negative value.
        data &= 0x7fffffff;
        long temp = (long) a*data + b;
        return (int) ((temp % p) % m);
    }

    public int getHashValue(String stringData) {
        // Mask the negative bit in case of negative value.
        int data = stringData.hashCode() & 0x7fffffff;
        long temp = (long) a *data + b;
        return (int) ((temp % p) % m);
    }
}
