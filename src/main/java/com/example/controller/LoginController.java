package com.example.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.iq80.leveldb.*;
import org.iq80.leveldb.impl.Iq80DBFactory;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.example.entity.*;
 
 
import javax.servlet.ServletContextListener;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBFactory;
import org.iq80.leveldb.Options;
  

public class LoginController extends AbstractController 
{
  
	//成功与失败字段
	  private String successView;
	  private String failView;
	  public String getSuccessView() 
	  {
	    return successView;
	  }

	  public void setSuccessView(String successView) 
	  {
	    this.successView = successView;
	  }
		public String getFailView() 
		{
			return failView;
		}

		public void setFailView(String failView) 
		{
			this.failView = failView;
		}
	   
	@Override
  protected ModelAndView handleRequestInternal(HttpServletRequest request,
      HttpServletResponse response) throws Exception 
	{
    //获取登录信息和登录时间
    String username = request.getParameter("username");
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	String time=df.format(new Date());
	int count=0;

	DB db=ServerListener.getDB();
    Charset charset = Charset.forName("utf-8");
    //boolean cleanup = false;
	    //保存相应的参数，通过ModelAndView返回
	Map<String ,String> map=new HashMap<String,String>();
//	DBFactory factory = Iq80DBFactory.factory;
	//String path = "D:/leveldb";
   // File dir = new File(path); 
    //如果数据不需要reload，则每次重启，尝试清理磁盘中path下的旧数据。
   // if(cleanup) {
    //    factory.destroy(dir,null);//清除文件夹内的所有文件。
  //  }
  // options = new Options().createIfMissing(true);	
	

  
	
    //write
    if(username !=null && !username.equals("")){
    	db.put(username.getBytes(charset),time.getBytes(charset));
    }
    else{
    	map.put("error", "输入错误,请重新输入");
		return new ModelAndView(getFailView(),map);
    }

    User user=new User(username,time);
    //读取当前snapshot，快照，读取期间数据的变更，不会反应出来
    Snapshot snapshot = db.getSnapshot();
    //读选项
    ReadOptions readOptions = new ReadOptions();
    readOptions.fillCache(false);//遍历中swap出来的数据，不应该保存在memtable中。
    readOptions.snapshot(snapshot);//默认snapshot为当前。
    DBIterator iterator = db.iterator(readOptions);
    while (iterator.hasNext()) {
        Map.Entry<byte[],byte[]> item = iterator.next();
        String key = new String(item.getKey(),charset);
        String value = new String(item.getValue(),charset);//null,check.
      	map.put(key, value);
        count++;
    }
    iterator.close();//must be
    
    List <Map.Entry<String,String>> infoIds=new ArrayList<Map.Entry<String,String>>(map.entrySet());
    Collections.sort(infoIds,new Comparator<Map.Entry<String,String>>(){
    	public int compare(Map.Entry<String, String> o1,Map.Entry<String,String> o2){
    		 return (o1.getValue()).toString().compareTo(o2.getValue());

    	 }
    });
 
//  按登陆时间进行排序
    Map<String ,String> map2=new LinkedHashMap<String,String>();
    for (int i = 0; i < infoIds.size(); i++) {
    	map2. put( infoIds.get(i).getKey(),infoIds.get(i).getValue());
        System.out.println(infoIds.get(i).getKey()+":"+infoIds.get(i).getValue());
    }
    
	ModelAndView mav=new ModelAndView();
	mav.addObject("map2",map2);
	mav.addObject("user",user);
	mav.addObject("count",count);
	mav.setViewName("showUser");

    return mav;
	
  
	}
		
  }	

