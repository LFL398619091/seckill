package main.java.com.fang.web.controller;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import main.java.com.fang.service.UserInfoService;

@Controller
@RequestMapping("/hello")
public class HelloController {
	private static Logger logger = LoggerFactory.getLogger(HelloController.class);
	@Autowired
	private UserInfoService userinfoService;
	@RequestMapping(value="/sayHello/{msg}",method=RequestMethod.GET)
	public String sayHello(@PathVariable("msg") String msg1) {
		userinfoService.printHello();
		logger.info(msg1);
		return "hello";
	}
	
	@RequestMapping("/uploadFile")
	public String showUploadFile(){
		return "uploadFile";
	}
	
	@RequestMapping(value="/saveFile",method=RequestMethod.POST)
	public String uploadFile(@RequestParam(value="file")MultipartFile file) throws IOException{
		if (!file.isEmpty()) {
			logger.debug("正在上传文件:｛｝",file.getOriginalFilename());
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File("/Library/apache-tomcat-8.0.32/wtpwebapps/seckill/WEB-INF/classes/main/resources/img",System.currentTimeMillis()+file.getOriginalFilename()));
		}return "success";
	}
	@RequestMapping(value="/json")
	public @ResponseBody Map<String, Object> getJson(){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("name", "张三");
		map.put("age", 201);
		map.put("addr", "江西赣州");
		return map;
	}

}
