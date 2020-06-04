package lab_2;

import java.util.Arrays;

public class RC4 {
    static private final int MIN_SIZE_OF_KEY = 5;
    static private final int SBLOCK_LENGTH = 256;
    private byte[] key = new byte[SBLOCK_LENGTH - 1];
    private int[] sBlock = new int[SBLOCK_LENGTH];

    public RC4() {
        reset();
    }

    public RC4(String key) {
        this();
        setKey(key);
    }

    public byte[] encryptMessage(String message, String key) {
        setKey(key);
        return encryptMessage(message);
    }

    public byte[] encryptMessage(String message) {
        reset();
        return crypt(message.getBytes());
    }

    public String decryptMessage(byte[] message, String key) {
        setKey(key);
        return decryptMessage(message);
    }

    public String decryptMessage(byte[] message) {
        reset();
        byte[] output = crypt(message);
        return new String(output);
    }


    private byte[] crypt(final byte[] msg) {
        initSBlock(key);

        byte[] code = new byte[msg.length];
        int i = 0;
        int j = 0;
        for (int n = 0; n < msg.length; ++n) {
            i = (i + 1) % SBLOCK_LENGTH;
            j = (j + sBlock[i]) % SBLOCK_LENGTH;
            swap(i, j, sBlock);
            int rand = sBlock[(sBlock[i] + sBlock[j]) % SBLOCK_LENGTH];

            code[n] = (byte) (rand ^ msg[n]);
        }
        return code;
    }

    private void initSBlock(byte[] key) {
        sBlock = new int[SBLOCK_LENGTH];
        int j = 0;

        for (int i = 0; i < SBLOCK_LENGTH; ++i) {
            sBlock[i] = i;
        }

        for (int i = 0; i < SBLOCK_LENGTH; ++i) {
            j = (j + sBlock[i] + (key[i % key.length]) & 0xFF) % SBLOCK_LENGTH;
            swap(i, j, sBlock);
        }
    }

    private void swap(int i, int j, int[] sbox) {
        int temp = sbox[i];
        sbox[i] = sbox[j];
        sbox[j] = temp;
    }

    public void setKey(String key) {
        if (!(key.length() >= MIN_SIZE_OF_KEY && key.length() < SBLOCK_LENGTH)) {
            System.out.println("Key size must be between "
                    + MIN_SIZE_OF_KEY + " and " + (SBLOCK_LENGTH - 1));
        }
        this.key = key.getBytes();
    }

    private void reset() {
        Arrays.fill(key, (byte) 0);
        Arrays.fill(sBlock, 0);
    }
}