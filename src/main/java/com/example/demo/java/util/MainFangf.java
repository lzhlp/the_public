package com.example.demo.java.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MainFangf {

	
	 public static String txt2String(File file){
		    Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -1);    //得到前一天
			String  yestedayDate= new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
	        StringBuilder result = new StringBuilder();
	        try{
	        	InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "gbk");
	        	BufferedReader br = new BufferedReader(isr);
	            String s = null;
	            int i=0;
	            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
	            String[] arr=s.trim().split("\\|"); 
	            if(arr!=null) {
	            	if(i==0) {
	            		if(arr.length==2) {
	            			if(!yestedayDate.equals(arr[0])) {
	    	            		System.out.println("头部不符合符合"+arr.length);
	    	            		}else {
	    	            		System.out.println("符合"+arr.length);
	    	            		}
	            		}
	            	}else {
	            		if(arr.length==7) {
	            			System.out.println("数组长度符合条件"+arr.length);
	            		}
	            	}
	            	i++;
	            	arr=null;
	                result.append(s);
	            }
	            }
	            br.close();    
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	        return result.toString();
	    }

	public static void main(String[] args) {

		 File file = new File("D:/0101011.20190110.txt");
	        System.out.println(txt2String(file));
	}
	
}
