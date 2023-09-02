package com.yf.base.utils.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩文件工具类
 * @author Dav
 */
public class ZipUtils {

    /**
     * 缓冲流长度
     */
    private static final int BUFFER_SIZE = 1024;


    /**
     * 压缩文件
     * @param srcFilePath 输入文件或文件夹
     * @param destFilePath 目标文件
     */
    public static void compress(String srcFilePath, String destFilePath) {
        File src = new File(srcFilePath);
        if (!src.exists()) {
            throw new RuntimeException(srcFilePath + "不存在");
        }
        File zipFile = new File(destFilePath);
        try {
            FileOutputStream fos = new FileOutputStream(zipFile);
            CheckedOutputStream cos = new CheckedOutputStream(fos, new CRC32());
            ZipOutputStream zos = new ZipOutputStream(cos);
            String baseDir = "";
            compressByType(src, zos, baseDir);
            zos.close();
            fos.close();
            cos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据类型压缩文件
     * @param src
     * @param zos
     * @param baseDir
     */
    private static void compressByType(File src, ZipOutputStream zos, String baseDir) {
        if (!src.exists()) {
            return;
        }
        System.out.println("压缩" + baseDir + src.getName());
        if (src.isFile()) {
            compressFile(src, zos, baseDir);
        } else if (src.isDirectory()) {
            compressDir(src, zos, baseDir);
        }
    }


    /**
     * 压缩文件
     * @param file
     * @param zos
     * @param baseDir
     */
    private static void compressFile(File file, ZipOutputStream zos, String baseDir) {
        if (!file.exists()) {
            return;
        }
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            ZipEntry entry = new ZipEntry(baseDir + file.getName());
            zos.putNextEntry(entry);
            int count;
            byte[] buf = new byte[BUFFER_SIZE];
            while ((count = bis.read(buf)) != -1) {
                zos.write(buf, 0, count);
            }
            bis.close();
        } catch (Exception e) {

        }
    }

    /**
     * 压缩文件夹
     * @param dir
     * @param zos
     * @param baseDir
     */
    private static void compressDir(File dir, ZipOutputStream zos, String baseDir) {
        if (!dir.exists()) {
            return;
        }
        File[] files = dir.listFiles();
        if (files.length == 0) {
            try {
                zos.putNextEntry(new ZipEntry(baseDir + dir.getName()
                        + File.separator));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (File file : files) {
            compressByType(file, zos, baseDir + dir.getName() + File.separator);
        }
    }

}
