package com.lbs.nettyserver.utils.sysutils;

import java.io.FileOutputStream;
import java.io.OutputStream;
import org.apache.commons.codec.binary.Base64;

public class Base64FileUtil {

	/**
	 * 
	 * @param fileStr base64编码的文件流字符串
	 * @param fileType 文件的后缀名，如    .jpg
	 * @param savePath 保存到服务器的路径 如  d://file//
	 * @return 文件保存后的完整路径，如 d://file//xx.jpg
	 * @throws Exception
	 */
    public static String saveFileFromBase64 (String fileStr,String fileType,String savePath)
    {   
    	String resultPath="";
    	
        if (fileStr == null || "".equals(fileStr) || fileType==null || "".equals(fileType) || savePath==null || "".equals(savePath)) //数据为空  
            return null;  
        
        try   
        {   
        	byte[] b = Base64.decodeBase64(fileStr);  
            for(int i=0;i<b.length;++i)  
            {  
                if(b[i]<0)  
                {//调整异常数据  
                    b[i]+=256;  
                }  
            }  

            resultPath =savePath+UUIDUtil.getUUID()+fileType; //"d://222.jpg";//保存的完整路径
            OutputStream out = new FileOutputStream(resultPath);      
            out.write(b);  
            out.flush();  
            out.close();  
            return resultPath;  
        }   
        catch (Exception e)   
        {  
        	e.printStackTrace();
            return null;  
        }  
    }  
}
