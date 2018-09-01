package com.club.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;





/**
 * 对象与数组转换；对象集合与数组转换方�?
 *
 * @author SharlaCheung
 * @version 1.0.0 2017�?02�?15�?
 */

public class SerializeUtil {
    /**
     * 获取byte[]类型Key
     *
     * @param object
     * @return
     */
    public static byte[] getBytesKey(Object object) {
        if (object instanceof String) {
            return StringUtils.getBytes((String) object);
        } else {
            return SerializeUtil.serialize(object);
        }
    }
    /**
     * Object转换byte[]类型
     *
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // 序列�?
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * byte[]型转换Object
     *
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {

        }
        return null;
    }

    public static byte[] serializeList(List value) {
        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }
        byte[] rv=null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            for(Object object : value){
                os.writeObject(object);
            }
            os.writeObject(null);
            os.close();
            bos.close();
            rv = bos.toByteArray();
        } catch (IOException e) {
          
        } finally {
            close(os);
            close(bos);
        }
        return rv;
    }

    public static List deserializeList(byte[] in) {
        List list = new ArrayList<>();
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if(in != null) {
                bis=new ByteArrayInputStream(in);
                is=new ObjectInputStream(bis);
                while (true) {
                    Object object = (Object) is.readObject();
                    if(object == null){
                        break;
                    }else{
                        list.add(object);
                    }
                }
                is.close();
                bis.close();
            }
        } catch (IOException e) {
        
        } catch (ClassNotFoundException e) {
          
        } finally {
            close(is);
            close(bis);
        }
        return list;
    }


    private static void close(Closeable closeable){
        try{
            if(null != closeable){
                closeable.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
