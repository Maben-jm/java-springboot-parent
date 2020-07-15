
package com.maben.util;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * Utility class to peform common String manipulation algorithms.
 */
public class DesUtil {
    /**
     * 
     */
    private static final String PASSWORD_CRYPT_KEY = "CSSDJYPT";
    /**
     * 
     */
    private static final String DES = "DES";

    /**
     * 
     * 加密
     * 
     * @param src
     *            数据源
     * 
     * @param key
     *            密钥，长度必须是8的倍数
     * 
     * @return 返回加密后的数据
     * 
     * @throws Exception
     * 
     */
    public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
        // DES算法要求有一个可信任的随机数源
        final SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建DESKeySpec对象
        final DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成
        // 一个SecretKey对象
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        final SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        final Cipher cipher = Cipher.getInstance(DES);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        // 现在，获取数据并加密
        // 正式执行加密操作
        return cipher.doFinal(src);
    }

    /**
     * 
     * 解密
     * 
     * @param src
     *            数据源
     * 
     * @param key
     *            密钥，长度必须是8的倍数
     * 
     * @return 返回解密后的原始数据
     * 
     * @throws Exception
     * 
     */
    public static byte[] decrypt(byte[] src, byte[] key) throws Exception {
        // DES算法要求有一个可信任的随机数源
        final SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建一个DESKeySpec对象
        final DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
        // 一个SecretKey对象
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        final SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成解密操作
        final Cipher cipher = Cipher.getInstance(DES);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        // 现在，获取数据并解密
        // 正式执行解密操作
        return cipher.doFinal(src);
    }

    /**
     * 
     * 密码解密
     * 
     * @param data data
     * 
     * @return data
     * 
     * @throws Exception
     * 
     */
    public static final String decrypt(String data) {
        try {
            return new String(decrypt(hex2byte(data.getBytes()), PASSWORD_CRYPT_KEY.getBytes()));
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 
     * 密码加密
     * 
     * @param password password
     * 
     * @return password
     * 
     * @throws Exception
     * 
     */
    public static final String encrypt(String password) {
        try {
            return byte2hex(encrypt(password.getBytes(), PASSWORD_CRYPT_KEY.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 
     * 二行制转字符串
     * 
     * @param b b
     * 
     * @return b
     * 
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    /**
     * 
     *@name    中文名称
     *@description 相关说明
     *@time    创建时间:2019年3月25日下午3:26:10
     *@param b  b
     *@return b
     *@author   史雪涛
     *@history 修订历史（历次修订内容、修订人、修订时间等）
     */
    public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0) {
            throw new IllegalArgumentException("长度不是偶数");
        }
        final byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            final String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    /**
     * 
     *@name    中文名称
     *@description 相关说明
     *@time    创建时间:2019年3月25日下午3:26:43
     *@param args args
     *@author   史雪涛
     *@history 修订历史（历次修订内容、修订人、修订时间等）
     */
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
        String str = null; 
        System.out.println("输入要加密或者解密的字符:"); 
        try {
            str = br.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        System.out.println("输入的信息为 :"+str); 
        System.out.println("加密后:"+encrypt(str));
        System.out.println("解密后:"+decrypt(str));
    }

    /**
     * DES解密
     * @param secretData 密码字符串
     * @param secretKey 解密密钥
     * @return 原始字符串
     * @throws Exception
     */
    public static String decryption(String secretData, String secretKey) throws Exception {

        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(DES);
            cipher.init(Cipher.DECRYPT_MODE, generateKey(secretKey));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new Exception("NoSuchAlgorithmException", e);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw new Exception("NoSuchPaddingException", e);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new Exception("InvalidKeyException", e);

        }

        try {

            final byte[] buf = cipher.doFinal(Base64Utils.decode(secretData.toCharArray()));

            return new String(buf);

        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new Exception("IllegalBlockSizeException", e);
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new Exception("BadPaddingException", e);
        }
    }

    /**
     * 获得秘密密钥
     * 
     * @param secretKey SecretKey
     * @return SecretKey
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     */
    private static SecretKey generateKey(String secretKey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        final DESKeySpec keySpec = new DESKeySpec(secretKey.getBytes());
        keyFactory.generateSecret(keySpec);
        return keyFactory.generateSecret(keySpec);
    }

    private static class Base64Utils {

        /**
         * 
         */
        private static char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
        /**
         * 
         */
        private static byte[] codes = new byte[256];

        static {
            for (int i = 0; i < 256; i++) {
                codes[i] = -1;
            }
            for (int i = 'A'; i <= 'Z'; i++) {
                codes[i] = (byte) (i - 'A');
            }
            for (int i = 'a'; i <= 'z'; i++) {
                codes[i] = (byte) (26 + i - 'a');
            }
            for (int i = '0'; i <= '9'; i++) {
                codes[i] = (byte) (52 + i - '0');
            }
            codes['+'] = 62;
            codes['/'] = 63;
        }

        private static String encode(byte[] data) {
            final char[] out = new char[((data.length + 2) / 3) * 4];
            for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
                boolean quad = false;
                boolean trip = false;
                int val = (0xFF & (int) data[i]);
                val <<= 8;
                if ((i + 1) < data.length) {
                    val |= (0xFF & (int) data[i + 1]);
                    trip = true;
                }
                val <<= 8;
                if ((i + 2) < data.length) {
                    val |= (0xFF & (int) data[i + 2]);
                    quad = true;
                }
                out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
                val >>= 6;
                out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
                val >>= 6;
                out[index + 1] = alphabet[val & 0x3F];
                val >>= 6;
                out[index + 0] = alphabet[val & 0x3F];
            }

            return new String(out);
        }

        /**
         * 将base64编码的数据解码成原始数据
         */
        private static byte[] decode(char[] data) {
            int len = ((data.length + 3) / 4) * 3;
            if (data.length > 0 && data[data.length - 1] == '=') {
                --len;
            }
            if (data.length > 1 && data[data.length - 2] == '=') {
                --len;
            }
            final byte[] out = new byte[len];
            int shift = 0;
            int accum = 0;
            int index = 0;
            for (int ix = 0; ix < data.length; ix++) {
                final int value = codes[data[ix] & 0xFF];
                if (value >= 0) {
                    accum <<= 6;
                    shift += 6;
                    accum |= value;
                    if (shift >= 8) {
                        shift -= 8;
                        out[index++] = (byte) ((accum >> shift) & 0xff);
                    }
                }
            }
            if (index != out.length) {
                throw new Error("miscalculated data length!");
            }
            return out;
        }
    }
}