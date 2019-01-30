package com.gl.utils.sha;

import java.security.MessageDigest;
import java.util.Formatter;

public class SHA1Util {

    /**
     * 使用腾讯的jsSdk
     * 使用SHA-1 算法加密
     * @param decrepit
     * @return
     */
    public static String SHA1(String decrepit) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decrepit.getBytes("UTF-8"));
            byte messageDigest[] = digest.digest();
            Formatter formatter = new Formatter();
            for (byte b : messageDigest)
            {
                formatter.format("%02x", b);
            }
            String result = formatter.toString();
            formatter.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
