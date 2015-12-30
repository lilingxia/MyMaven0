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
  
	//�ɹ���ʧ���ֶ�
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
    //��ȡ��¼��Ϣ�͵�¼ʱ��
    String username = request.getParameter("username");
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
	String time=df.format(new Date());
	int count=0;

	DB db=ServerListener.getDB();
    Charset charset = Charset.forName("utf-8");
    //boolean cleanup = false;
	    //������Ӧ�Ĳ�����ͨ��ModelAndView����
	Map<String ,String> map=new HashMap<String,String>();
//	DBFactory factory = Iq80DBFactory.factory;
	//String path = "D:/leveldb";
   // File dir = new File(path); 
    //������ݲ���Ҫreload����ÿ���������������������path�µľ����ݡ�
   // if(cleanup) {
    //    factory.destroy(dir,null);//����ļ����ڵ������ļ���
  //  }
  // options = new Options().createIfMissing(true);	
	

  
	
    //write
    if(username !=null && !username.equals("")){
    	db.put(username.getBytes(charset),time.getBytes(charset));
    }
    else{
    	map.put("error", "�������,����������");
		return new ModelAndView(getFailView(),map);
    }

    User user=new User(username,time);
    //��ȡ��ǰsnapshot�����գ���ȡ�ڼ����ݵı�������ᷴӦ����
    Snapshot snapshot = db.getSnapshot();
    //��ѡ��
    ReadOptions readOptions = new ReadOptions();
    readOptions.fillCache(false);//������swap���������ݣ���Ӧ�ñ�����memtable�С�
    readOptions.snapshot(snapshot);//Ĭ��snapshotΪ��ǰ��
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
 
//  ����½ʱ���������
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

