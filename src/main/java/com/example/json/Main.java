package com.example.json;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.json.bean.BeanInfo;

/**
 * 
 * @author hexian
 *
 */
public class Main {
	
	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	/**
	 * fastjson支持单引号的json转对象
	 * 
	 * FiledConfig config = JSON.parseObject("{'ignore': false, 'overrideType': 'string'}", FiledConfig.class);
	 * 
	 */
	public static void main(String[] args) {
		 long start = System.currentTimeMillis();
		 String basePath = System.getProperty("user.dir") + File.separator + "json" + File.separator;
		 String filePath = basePath + "wechat-message.json";
		 // 第1步:  预处理json
		 String json = JsonProcess.combiJson(filePath);
		 
		 
		 // 第2步:  获取BeanDefinition
		 String baseDir = System.getProperty("user.dir") + File.separator + 
				          "code" + File.separator + new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒SSS毫秒 E").format(new Date()) + File.separator +  
				          "";
		 List<BeanInfo> beanList = new LinkedList<>();
		 JsonProcess.jsonToBeanDefinition(json, null, beanList);
		 
		 
		 // 第3步:  根据 BeanDefinition 生成bean文件
		 BeanWriter.writerBeanList(beanList, baseDir);
		 long end = System.currentTimeMillis();
		 LOG.info("耗时: {} 毫秒.", (end - start));
	}
	
}
