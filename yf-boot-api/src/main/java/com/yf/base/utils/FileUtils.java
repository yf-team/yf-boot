package com.yf.base.utils;

import java.io.File;

/**
 * @author bool
 */
public class FileUtils {

    public static boolean checkDelete(String path){

        if(path.contains("/.git/") || path.contains("/target/") || path.contains("/.idea/")){
            return true;
        }

        return false;
    }


    public static void deleteDir(File file){

        // 文件夹不存在
        if(!file.exists() || !file.isDirectory()){
            return;
        }


        File [] files = file.listFiles();
        if(files == null || files.length==0){
            return;
        }

        for(File item: files){
            if(file.isDirectory()){
                deleteDir(item);
                continue;
            }


            if(checkDelete(item.getAbsolutePath())){
                System.out.println("+++++++++删除文件："+item.getAbsolutePath());
                item.delete();
            }
        }

        // 删除大文件夹
        if(checkDelete(file.getAbsolutePath())){
            System.out.println("+++++++++删除文件："+file.getAbsolutePath());
            file.delete();
        }
    }

}
