package com.lbs.nettyserver.utils.sysutils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by will on 17/12/26.
 * 对象序列化方法
 *
 */
public class ByteUtil {

    private static final Log logger = LogFactory.getLog(ByteUtil.class);


    public static Object byte2object(byte[] data){
        Object object = null;

        try{
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            ObjectInputStream oi = new ObjectInputStream(in);
            object = oi.readObject();
            in.close();
            oi.close();

        }catch (Exception e){
            logger.error(e.getMessage());
        }

        return object;
    }


    public static byte[] object2bytes(Object object){
        byte[] data = null;

        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream oi = new ObjectOutputStream(out);
            oi.writeObject(object);
            data = out.toByteArray();
            oi.close();
            out.close();

        }catch (Exception e){
            logger.error(e.getMessage());

        }
        logger.info("data length :"+data.length);
        return data;
    }
}
