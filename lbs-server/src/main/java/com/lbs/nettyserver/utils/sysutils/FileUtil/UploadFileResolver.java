package com.lbs.nettyserver.utils.sysutils.FileUtil;

import com.lbs.nettyserver.utils.sysutils.UUIDUtil;
import com.sun.org.apache.xerces.internal.util.DatatypeMessageFormatter;

import java.io.File;
import java.io.SyncFailedException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/7/31.
 */
public class UploadFileResolver implements FileResolver{

    private String orgName;

    private String type;

    private String baseDir;

    private String extension;

    private String folderPath;

    private String storagePath;

    private String relativePath;

    //private static DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private static DateFormat df = new SimpleDateFormat("yyyyMMddHHmm");

//    private static String generateRandomPrefix(){
////        byte b = Integer.
//    }

    private static Map<String, Set<String>> types = new HashMap<>();
    static {
        Set<String> IMGS = new HashSet<>();
        String[] imgs = {"jpeg", "jpg", ""};
        Collections.addAll(IMGS, imgs);
        types.put("IMAGE", IMGS);

        Set<String> VOICES = new HashSet<>();
        String[] voices = {"", ""};
        Collections.addAll(VOICES, voices);
        types.put("AUDIO", VOICES);

        Set<String> VIDEOS = new HashSet<>();
        String[] videos = {"", ""};
        Collections.addAll(VIDEOS, videos);
        types.put("VIDEO", VIDEOS);
    }

    public static String extension(String fileName){
        String[] names = fileName.split("\\.");
        if (names.length > 1){
            return names[names.length - 1];
        }else {
            return null;
        }
    }

    public UploadFileResolver(String orgName, String type, String baseDir){
        this.orgName = orgName;
        this.type = type;
        this.baseDir = baseDir;
        this.extension = extension(orgName);
        this.folderPath = baseDir + type;
        //this.relativePath = type + "/" + df.format(new Date()) + "." + extension;
        this.relativePath = type + "/" + df.format(new Date()) +UUIDUtil.getUUID() + "." + extension;
        this.storagePath = baseDir + this.relativePath;
    }

    @Override
    public boolean typeCheck() {
        return types.containsKey(type) && types.get(type).contains(extension);
    }

    @Override
    public String storagePath() {
        return storagePath;
    }

    @Override
    public String folderPath() {
        return folderPath;
    }

    @Override
    public String extension() {
        return extension;
    }

    @Override
    public String relativePath() {
        return relativePath;
    }
}

