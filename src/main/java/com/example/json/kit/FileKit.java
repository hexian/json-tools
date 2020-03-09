package com.example.json.kit;

import java.io.File;
import java.io.IOException;
/**
 * 
 * @author hexian
 *
 */
public class FileKit {
	
	private FileKit() {
		
	}

    /**
     * 递归创建文件夹或文件
     * @param file
     */
	public static void markDir(File file){
		 if(file.getParentFile().exists()){
			 if(!file.exists()) {
				file.mkdir();
			 } else {
				 /**  预留    */
			 }
		 }else{
			 markDir(file.getParentFile());
			 if(!file.exists()){
				file.mkdir();
			 } else {
				 /**  预留    */
			 }
		 }
	}
	
	/**
     * 递归创建文件夹或文件
     * @param file
     */
	public static void markFile(File file){
		 markDir(file.getParentFile());
		 if(!file.exists()){
			 try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		 }
	}
	
}
