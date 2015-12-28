package com.example.entity;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;
public class User {
	private String username;
	//private String password;
	private String time;
	public User(String username, String time) {
		super();
		this.username = username;
		this.time = time;
		//this.time=time;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	//public String getPassword() {
		//return password;
	//}
	//public void setPassword(String password) {
		//this.password = password;
	//}
	public String getTime() {
		 		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss yyyy-MM-dd");
		 		time=df.format(new Date());
		 		return time;
	}
}
