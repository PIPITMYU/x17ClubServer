package com.club.redis;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 对象与数组转换；对象集合与数组转换方�?
 *
 * @author SharlaCheung
 * @version 1.0.0 2017�?02�?15�?
 */
public class ObjectsTranscoder {


    /**
     * 获取对象属�?�，返回�?个字符串数组
     *
     * @param o 对象
     * @return String[] 字符串数�?
     */
    private String[] getFiledName(Object o) {
        try {
            Field[] fields = o.getClass().getDeclaredFields();
            String[] fieldNames = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                fieldNames[i] = fields[i].getName();
            }
            return fieldNames;
        } catch (SecurityException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
        return null;
    }


    /**
     * 使用反射根据属�?�名称获取t属�?�的get方法
     *
     * @param fieldNames 属�?�名�?
     * @param o          操作对象
     * @return List<Method> get方法
     */

    private List<Method> getGetField(String[] fieldNames, Object o) {

        List<Method> methods = new ArrayList<Method>();
        for (String fieldName : fieldNames) {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = null;
            try {
                method = o.getClass().getMethod(getter, new Class[]{});
            } catch (NoSuchMethodException e) {
                System.out.println("属�?�不存在");
                continue;
            }
            //Object value = method.invoke(o, new Object[] {});
            methods.add(method);
        }
        return methods;

    }

    /**
     * 将list集合转换为二维string数组
     *
     * @param list 要转换的集合
     * @return String[][] 返回的sting数组
     */

    public String[][] listToArrayWay(List list) {
        Object o = list.get(0);

        String[] filedNames = getFiledName(o);
        int filedNum = filedNames.length;
        int listSize = list.size();
        List<Method> methods = getGetField(filedNames, o);

        String[][] arrs = new String[listSize][filedNum];
        int i = 0;
        for (Object object : list) {
            int j = 0;
            for (Method method : methods) {
                Object value = null;
                try {
                    value = method.invoke(object, new Object[]{});
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("属�?�不存在" + e);
                }
                arrs[i][j] = (String) value;
                j++;
            }
            i++;
        }

        return arrs;
    }

    /**
     * 将list集合转换为二维Byte数组
     *
     * @param list 要转换的集合
     * @return Byte[][] Byte
     */

    public Byte[][] listToArrayByte(List list) {
        Object o = list.get(0);

        String[] filedNames = getFiledName(o);
        int filedNum = filedNames.length;
        int listSize = list.size();
        List<Method> methods = getGetField(filedNames, o);

        Byte[][] arrs = new Byte[listSize][filedNum];
        int i = 0;
        for (Object object : list) {
            int j = 0;
            for (Method method : methods) {
                Object value = null;
                try {
                    value = method.invoke(object, new Object[]{});
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("属�?�不存在" + e);
                }
                arrs[i][j] = (Byte) value;
                j++;
            }
            i++;
        }
        return arrs;
    }


}
