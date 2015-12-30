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
        //数据库生成
        String path = "D:/leveldb";

        //init
        DBFactory factory = Iq80DBFactory.factory;
        File dir = new File(path);
        //如果数据不需要reload，则每次重启，尝试清理磁盘中path下的旧数据。
        if(cleanup) {
            try {
				factory.destroy(dir,null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//清除文件夹内的所有文件。
        }
       Options options = new Options().createIfMissing(true);
        //重新open新的db
        /**
         *服务器启动，打开数据库。 
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
