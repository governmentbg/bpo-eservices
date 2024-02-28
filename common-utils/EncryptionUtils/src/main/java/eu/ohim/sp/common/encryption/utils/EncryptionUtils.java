/*******************************************************************************
 * * $Id:: EncryptionUtils.java 51067 2012-11-16 16:35:00Z soriama $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.encryption.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import eu.ohim.sp.common.SPException;

public class EncryptionUtils {

    /** SHA-256 Algorith name **/
    private static final String SHA_256 = "SHA-256";

    /** SHA-256 Algorith name **/
    private static final String SHA_1 = "SHA-1";

    /** DES Algorith name **/
    private static final String DES = "DES";

    /** UTF-8 encoding **/
    private static final String UTF_8 = "UTF-8";

    /** Logger for this class. */
    private static final Logger logger = Logger.getLogger(EncryptionUtils.class);

    /** error code for exceptions in rest calls. */
    private static final String ERROR_SHA_256 = "error.common.sha_256";

    /** error code for exceptions in rest calls. */
    private static final String ERROR_SHA_1 = "error.common.sha_1";


    public String getSha256Hash(String message) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(SHA_256);
            md.update(message.getBytes(UTF_8));

            byte byteData[] = md.digest();

            // convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error creating HASH code for the message: " + message);
            logger.error(e.getMessage());
            throw new SPException("Error creating HASH code for the message:  " + message, e, ERROR_SHA_256);
        } catch (UnsupportedEncodingException e) {
            logger.error("Error creating HASH code for the message: " + message);
            logger.error(e.getMessage());
            throw new SPException("Error creating HASH code for the message:  " + message, e, ERROR_SHA_256);
        }
    }

    /**
     * Get the hash of the data passed as byte array, using the algorith SHA 256
     *
     * @param data
     * @return
     */
    public static byte[] getSha256Hash(byte[] data) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(SHA_256);
            md.update(data);

            byte hashData[] = md.digest();

            return hashData;

        } catch (NoSuchAlgorithmException e) {
            logger.error("Error creating HASH code for the message: ");
            logger.error(e.getMessage());
            throw new SPException("Error creating HASH code for the message.", e, ERROR_SHA_256);
        }
    }

    /**
     * Get the hash of the data passed as byte array, using the algorith SHA 256
     *
     * @param data
     * @return
     */
    public static byte[] getSha1Hash(byte[] data) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(SHA_1);
            md.update(data);

            byte hashData[] = md.digest();

            return hashData;

        } catch (NoSuchAlgorithmException e) {
            logger.error("Error creating HASH code for the message: ");
            logger.error(e.getMessage());
            throw new SPException("Error creating HASH code for the message.", e, ERROR_SHA_1);
        }
    }

    /**
     * Encrypt a message using the secret password passed as parameter
     *
     * @param strMessage
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     */
    public static String encryptMessage(String strMessage, String password) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException,
            BadPaddingException, UnsupportedEncodingException {
        DESKeySpec keySpec = new DESKeySpec(password.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey key = keyFactory.generateSecret(keySpec);

        Cipher encryptCipher = Cipher.getInstance(DES);
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encryptedBytes = encryptCipher.doFinal(strMessage.getBytes(UTF_8));

        return Base64.encodeBase64String(encryptedBytes);

    }

    /**
     * Decrypt a message using the secret password passed as parameter
     *
     * @param strMessage
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     */
    public static String decryptMessage(String strMessage, String password) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException,
            BadPaddingException, UnsupportedEncodingException {
        DESKeySpec keySpec = new DESKeySpec(password.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey key = keyFactory.generateSecret(keySpec);

        Cipher encryptCipher = Cipher.getInstance(DES);
        encryptCipher.init(Cipher.DECRYPT_MODE, key);

        byte[] encryptedBytes = Base64.decodeBase64(strMessage);
        byte[] plainBytes = encryptCipher.doFinal(encryptedBytes);

        return new String(plainBytes, UTF_8);

    }

}
