package com.example.entity;  
  
import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContextEvent;  
import javax.servlet.ServletContextListener;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBFactory;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;
import org.omg.CORBA.PUBLIC_MEMBER;  
  
 
  
public class ServerListener implements ServletContextListener {   

	private static DB db;
    public static DB getDB(){
    	return db;
    }
    public void contextInitialized(ServletContextEvent event)   
    {   
        System.out.println("Server started!");  
    	boolean cleanup = false;
        //���ݿ�����
        String path = "D:/leveldb";

        //init
        DBFactory factory = Iq80DBFactory.factory;
        File dir = new File(path);
        //������ݲ���Ҫreload����ÿ���������������������path�µľ����ݡ�
        if(cleanup) {
            try {
				factory.destroy(dir,null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//����ļ����ڵ������ļ���
        }
       Options options = new Options().createIfMissing(true);
        //����open�µ�db
        /**
         *�����������������ݿ⡣ 
         *
         */
        try {
		 db = factory.open(dir,options);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }   
    public void contextDestroyed(ServletContextEvent event)   
    {   
        try {
			db.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Server closed!");  
    }
    
} 
