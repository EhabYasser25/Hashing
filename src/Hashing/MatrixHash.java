package Hashing;
import java.nio.charset.StandardCharsets;
import java.util.Random;
public class MatrixHash{
    private final int b;
    public final int u;
    private final char[][] hashFunction;

    public MatrixHash(int b, int u){
        this.b = b;
        this.u = u;
        this.hashFunction = new char[this.u][(int)(Math.ceil(b / 8.0))];
        Random rand = new Random();

        for(int row = 0; row < this.u; row++){
            for(int col = 0; col < this.hashFunction[0].length; col++)
                this.hashFunction[row][col] = (char)rand.nextInt(256);
        }
    }

    public int getHashValue(String val){
        if(val.length() * 8 > this.u)
            return -1;
        char[] key = new char[this.hashFunction[0].length];
        for(int i = 0; i < key.length; i++)
            key[i] = 0;
        char[] valChars = val.toCharArray();
        for(int uCounter = 0; uCounter < valChars.length; uCounter++){
            for(int bitCounter = 0; bitCounter < 8; bitCounter++){
                if((valChars[uCounter]>>bitCounter & 1) == 1){
                    for(int bCounter = 0; bCounter < this.hashFunction[0].length; bCounter++)
                        key[bCounter] ^= this.hashFunction[8 * uCounter + bitCounter][bCounter];
                }

            }
        }
        int ret = 0;
        for(int i = 0; i < key.length; i++)
            ret += (((int)key[i]) << (8 * i));
        for(int i = 0; i < (key.length * 8 - this.b); i++)
            ret &= ~(1 << (key.length * 8 - i));
        return ret >> 1;
    }

    public String randomString(int maxStrLen) {
        byte[] array = new byte[maxStrLen];
        new Random().nextBytes(array);

        return new String(array, StandardCharsets.UTF_8);
    }
}