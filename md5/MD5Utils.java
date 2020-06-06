import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

public class MD5Utils {

  public static void main(String[] args) {
    Logger logger = Logger.getAnonymousLogger();
    String initialString = "abcdefg";
    logger.info("Initial string: " + initialString);
    logger.info("MD5 Checksum: " + MD5Utils.convert(initialString));
  }

  public static String convert(String s) {
    try {
      // Create MD5 Hash
      MessageDigest digest = MessageDigest.getInstance("MD5");
      digest.update(s.getBytes());
      byte messageDigest[] = digest.digest();

      // Create Hex String
      StringBuilder hexString = new StringBuilder();
      for (byte b : messageDigest) {
        hexString.append(Integer.toHexString(0xFF & b));
      }
      return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }
}
