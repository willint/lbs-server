package com.lbs.nettyserver.utils.sysutils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;


public class RSAUtil {

    public static final String RSA = "RSA";
    /** 指定公钥存放文件 */  
    public static String PUBLIC_KEY_FILE = PropUtils.get("sysfile.path.publicKey");  
    //private static String PUBLIC_KEY_FILE = "PublicKey";  
    /** 指定私钥存放文件 */  
    public static String PRIVATE_KEY_FILE = PropUtils.get("sysfile.path.privateKey"); 
    //private static String PRIVATE_KEY_FILE = "PrivateKey"; 
    
    /**
     * 生成RSA密钥
     * 
     * @return
     * @throws Exception
     */
    public static RSADTO generateRSA() throws Exception {
        RSADTO rsa = null;
        // 将公钥私钥输出文件
        ObjectOutputStream oos1 = null;  
        ObjectOutputStream oos2 = null;  

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
            keyPairGenerator.initialize(1024);

            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            Key publicKey =  keyPair.getPublic();
//            PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            Key privateKey = keyPair.getPrivate();
//            PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            rsa = new RSADTO();
            String publicString = keyToString(publicKey);
            String privateString = keyToString(privateKey);
            rsa.setPublicKey(publicString);
            rsa.setPrivateKey(privateString);
            
          
            /** 用对象流将生成的密钥写入文件 */  
            oos1 = new ObjectOutputStream(new FileOutputStream(PUBLIC_KEY_FILE));  
            oos2 = new ObjectOutputStream(new FileOutputStream(PRIVATE_KEY_FILE));  
            oos1.writeObject(publicKey);  
            oos2.writeObject(privateKey);  

        } catch (NoSuchAlgorithmException e) {
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        }catch (Exception e) {
        	e.printStackTrace();
        	throw new Exception(e.getMessage());
		}finally{
        	 /** 清空缓存，关闭文件输出流 */  
        	 oos1.close();  
             oos2.close(); 
        }

        return rsa;
    }

    /**
     * BASE64 String 转换为 PublicKey
     * @param publicKeyString
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String publicKeyString) throws Exception {
        PublicKey publicKey = null;
        try {
            byte[] keyByteArray = Base64.decodeBase64(publicKeyString);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyByteArray);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            publicKey = keyFactory.generatePublic(x509KeySpec);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

        return publicKey;
    }

    /**
     * BASE64 String 转换为 PrivateKey
     * @param privateKeyString
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String privateKeyString) throws Exception {
        PrivateKey privateKey = null;

        try {
            byte[] keyByteArray = Base64.decodeBase64(privateKeyString);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyByteArray);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } 

        return privateKey;

    }

    /**
     * RSA 私钥签名返回byte[]
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] encodeBytePrivate(byte[] content, PrivateKey privateKey) throws Exception {
        byte[] encodeContent = null;
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            encodeContent = cipher.doFinal(content);
        return encodeContent;
    }

    /**
     * RSA 私钥签名返回 
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    private static String encodeStringPrivateSign(byte[] content, PrivateKey privateKey) throws Exception {
        byte[] encodeContent = encodeBytePrivate(content, privateKey);
        String stringContent = Hex.encodeHexString(encodeContent);
        return stringContent;
    }
    
    
    /**
     * 公钥验证返回byte[]
     * 
     * @param content
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] decodeBytePublic(byte[] content, PublicKey publicKey) throws Exception {
        byte[] decodeContent = null;
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        decodeContent = cipher.doFinal(content);
        return decodeContent;
    }

    /**
     * 公钥解密方法
     * @param content
     * @param publicKey
     * @return
     * @throws Exception
     */
    private static byte[] decodeStringPublicSign(String content, PublicKey publicKey) throws Exception {
        byte[] decodeContent = null;
        byte[] sourceByteArray = Hex.decodeHex(content.toCharArray());
        decodeContent = decodeBytePublic(sourceByteArray, publicKey);
        return decodeContent;
    }

    /**
     * 将文件流转化成Key对象
     * @param fileName
     * @return
     * @throws Exception
     * @throws IOException
     */
    private static Key getKey(String fileName) throws Exception, IOException {  
        Key key;  
        ObjectInputStream ois = null;  
        try {  
            /** 将文件中的私钥对象读出 */  
            ois = new ObjectInputStream(new FileInputStream(fileName));  
            key = (Key) ois.readObject();  
        } catch (Exception e) {  
            throw e;  
        } finally {  
            ois.close();  
        }  
        return key;  
    }
    
    /**
     * 将Key转为String
     * 
     * @param key
     * @return
     */
    private static String keyToString(Key key) {
        byte[] keyByteArray = key.getEncoded();
        String keyString = Base64.encodeBase64String(keyByteArray);
        return keyString;
    }
    
    public static class RSADTO {
        /**
         * 公钥
         */
        private String publicKey;

        /**
         * 私钥
         */
        private String privateKey;

		public String getPublicKey() {
			return publicKey;
		}

		public void setPublicKey(String publicKey) {
			this.publicKey = publicKey;
		}
		public String getPrivateKey() {
			return privateKey;
		}

		public void setPrivateKey(String privateKey) {
			this.privateKey = privateKey;
		}
    }
    
    public static class MD5Util {
        /**
         * MD5加密
         *
         * @param s
         * @return
         */
        public static String MD5(String s) {
            char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
            try {
                byte[] btInput = s.getBytes("utf8");
                // 获得MD5摘要算法的 MessageDigest 对象
                MessageDigest mdInst = MessageDigest.getInstance("MD5");
                // 使用指定的字节更新摘要
                mdInst.update(btInput);
                // 获得密文
                byte[] md = mdInst.digest();
                // 把密文转换成十六进制的字符串形式
                int j = md.length;
                char str[] = new char[j * 2];
                int k = 0;
                for (int i = 0; i < j; i++) {
                    byte byte0 = md[i];
                    str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                    str[k++] = hexDigits[byte0 & 0xf];
                }
                return new String(str);
            } catch (Exception e) {
            	e.printStackTrace();
                return null;
            }
        }
    }

    /**
     * 服务器通过私钥生成签名给客户端
     * @param source
     * @return
     * @throws Exception
     */
    public static String serverGenerateSign(String source) {
    	String sign = "" ;
    	try {
    		sign = encodeStringPrivateSign(source.getBytes(),(PrivateKey)getKey(PRIVATE_KEY_FILE));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return sign;
    }

    /**
     * 客户端通过公钥解密来自服务端的签名
     * @param encryptSign
     * @return
     * @throws Exception
     */
    public static String clientAnalyseSign(String encryptSign) {
    	byte[] source = null ;
    	try {
    		source =  decodeStringPublicSign(encryptSign, (PublicKey)getKey(PUBLIC_KEY_FILE));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return String.valueOf(source);
    }
    
    
    /** 
     * 客户端通过公钥加密签名 
     * @param source 源数据 
     * @return 
     * @throws Exception 
     */  
    public static String clientEncryptSign(String source) {  
    	String encryptSign = "";
        try {
        	Key publicKey = getKey(PUBLIC_KEY_FILE);  
        	/** 得到Cipher对象来实现对源数据的RSA加密 */  
        	Cipher cipher = Cipher.getInstance(RSA);  
        	cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
        	byte[] b = source.getBytes();  
        	/** 执行加密操作 */  
        	encryptSign  = Base64.encodeBase64String(cipher.doFinal(b));  
		} catch (Exception e) {
			e.printStackTrace();
		}

        
        return encryptSign;  
    } 
    
    /** 
     * 服务端通过私钥解密签名
     * @param cryptograph    密文 
     * @return 
     * @throws Exception 
     */  
    public static String serverDecryptSign(String cryptograph)  {  
    	String decryptSign = "";
    	try {
    		Key privateKey =  getKey(PRIVATE_KEY_FILE) ;  
    		/** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */  
    		Cipher cipher = Cipher.getInstance(RSA);  
    		cipher.init(Cipher.DECRYPT_MODE, privateKey);  
    		byte[] b1 = Base64.decodeBase64(cryptograph);  
    		/** 执行解密操作 */  
    		decryptSign = new String(cipher.doFinal(b1));  
		} catch (Exception e) {
			e.printStackTrace();
		}
        return decryptSign;  
    } 
    
     
    public static void main(String[] args) throws Exception {
//		 生成公私钥方法
//    	RSADTO dto = generateRSA();
//		String privateKey = dto.getPrivateKey();
//		String publicKey = dto.getPublicKey();
//		System.out.println("私钥 :" + privateKey);
//		System.out.println("公钥 :" + publicKey);
		
		System.out.println("----------------公私钥只生成一次---------------------");
		
		// 按key字母升序排序后的源串
		String sources = "20170101";
//		私钥签名	
//		String sign = encodeStringPrivateSign(sources.getBytes(), (PrivateKey)getKey(PRIVATE_KEY_FILE));
//		System.out.println("加密后的数据:"+sign);
		String bb = clientEncryptSign(sources);
		System.out.println("加密后的数据:"+bb);
		// 公钥验证签名
//		byte[] aa = decodeStringPublicSign(sign, (PublicKey)getKey(PUBLIC_KEY_FILE));
//		 String oldsource = new String(aa);
//		System.out.println("解密后的数据:"+oldsource);
		 String cc = serverDecryptSign(bb);
		 System.out.println("解密后的数据:"+cc);
	}

}
