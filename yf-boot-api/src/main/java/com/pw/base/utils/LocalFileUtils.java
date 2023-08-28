/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.pw.base.utils;

import com.pw.base.api.exception.ServiceException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 文件工具类
 * @author bool
 */
public class LocalFileUtils {


	/**
	 * 创建文件夹
	 * @param path
	 */
	public static void makeDirs(String path){
		String dirPath = path.substring(0, path.lastIndexOf("/"));
		File dir = new File(dirPath);
		if(!dir.exists()){
			dir.mkdirs();
		}
	}


	/**
	 * 下载网络文件到本地
	 * @param httpUrl
	 * @param path
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static String downloadFile(String httpUrl, String path, String name) throws Exception {
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}

		URL url = new URL(httpUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(3 * 1000);
		//防止屏蔽程序抓取而放回403错误
		conn.setRequestProperty("User-Agent", "Mozilla/4.0(compatible;MSIE 5.0;Windows NT;DigExt)");
		Long totalSize = Long.parseLong(conn.getHeaderField("Content-Length"));
		if (totalSize > 0) {
			FileUtils.copyURLToFile(url, new File(path + name));
			return path + name;
		} else {
			throw new ServiceException("网络文件下载失败！");
		}
	}

}
