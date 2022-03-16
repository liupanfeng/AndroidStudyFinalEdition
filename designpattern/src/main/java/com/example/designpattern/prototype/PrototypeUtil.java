package com.example.designpattern.prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/27 14:20
 * @Description :
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class PrototypeUtil {

    /**
     * 如果对象比较大的时候，这种情况效率高，因为直接操作的二进制数据
     * @param prototype
     * @return
     */
    public static IPrototype getSerializeInstance(IPrototype prototype) {

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(prototype);

            ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream=new ObjectInputStream(byteArrayInputStream);
            IPrototype copy = (IPrototype) objectInputStream.readObject();

            byteArrayOutputStream.flush();
            byteArrayInputStream.close();
            byteArrayInputStream.close();

            return copy;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
