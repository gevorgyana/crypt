package lab_2;

import junit.framework.TestCase;

public class RC4Test extends TestCase {

    public void testCrypt() {
        final String key = "Hello world";
        final String text = "The quick brown fox jumps over the lazy dog.";

        RC4 rc4 = new RC4(key);

        final byte[] bytes = rc4.encryptMessage(text);

        final String actual = rc4.decryptMessage(bytes);

        assertEquals(text, actual);
    }

}