package com.lbs.nettyserver.utils.sysutils.FileUtil;

/**
 * Created by Administrator on 2017/7/31.
 */
public interface FileResolver {

    boolean typeCheck();

    String storagePath();

    String folderPath();

    String extension();

    String relativePath();
}
