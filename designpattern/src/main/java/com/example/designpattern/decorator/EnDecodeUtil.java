package com.example.designpattern.decorator;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/28 11:10
 * @Description : 加 解密工具
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class EnDecodeUtil {

    private static final char password='a';
    public static String encodeDecode(String str){
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (chars[i] ^ password);
        }
        return new String(chars);
    }

}
