package com.cloudant.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;


public class HappyCloudantTest {
	
	public static void main(String[] args){
		String targetURL = "https://lina.cloudant.com";
		String username = "lina";
		String password = "123456789";
		String dbname = "testdb001";
		String docname ="document001";
		
		CreateDB(targetURL,username,password,dbname);
		CreateDOC(targetURL,username,password,dbname,docname);
		CetDOC(targetURL,username,password,dbname,docname);
	
	}
	
	public static void CreateDB(String targetURL, String username, String password, String dbname) {
		try {
			
			URL url = new URL(targetURL+"/"+ dbname);
			HttpURLConnection  con = (HttpURLConnection )url.openConnection();
			
			con.setRequestMethod("PUT");
			con.setDoOutput(true);
			con.setDoInput(true);
			
			String auth = username + ":" + password;
			String auth_str = new sun.misc.BASE64Encoder().encode(auth.getBytes());
			con.setRequestProperty("Authorization", "Basic " + auth_str);
			
			java.io.InputStream is = con.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);  
			BufferedReader br = new BufferedReader(isr);  
			String res = br.readLine();
			System.out.println(res); 
			if(res.contains("true")){
				System.out.println("Create db done.");
			}
			else{
				System.out.println(res);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void CreateDOC(String targetURL, String username, String password,String dbname, String docname) {
		try {
			//URL url = new URL("https://lina.cloudant.com/lndb/lndoc");
			URL url = new URL(targetURL+"/" + dbname + "/" + docname);
			HttpURLConnection  con = (HttpURLConnection )url.openConnection();
			
			con.setRequestMethod("PUT");
			con.setDoOutput(true);
			con.setDoInput(true);
		
			String auth = username + ":" + password;
			String auth_str = new sun.misc.BASE64Encoder().encode(auth.getBytes());
			con.setRequestProperty("Authorization", "Basic " + auth_str);
			con.setRequestProperty("content-type", "application/json");
			
			JSONObject json_obj = new JSONObject();
			try {
				json_obj.put("foo", "bar");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			OutputStream os = con.getOutputStream();
			PrintWriter out = new PrintWriter(os);  
	        out.print(json_obj.toString());  
	        out.flush();  
	        os.flush();  
	        out.close();  
	        os.close();
	        
			java.io.InputStream is = con.getInputStream();
	        InputStreamReader isr = new InputStreamReader(is);  
	        BufferedReader br = new BufferedReader(isr);  
	        String res = br.readLine();
	        System.out.println(res); 
	        if(res.contains("true")){
				System.out.println("Create document done.");
			}
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void CetDOC(String targetURL, String username, String password,String dbname, String docname) {
		try {
			URL url = new URL(targetURL+"/" + dbname + "/" + docname);
			HttpURLConnection  con = (HttpURLConnection )url.openConnection();
			
			con.setRequestMethod("GET");
			con.setDoInput(true);
			String auth = username + ":" + password;
			String auth_str = new sun.misc.BASE64Encoder().encode(auth.getBytes());
			con.setRequestProperty("Authorization", "Basic " + auth_str);
			java.io.InputStream is = con.getInputStream();
	        InputStreamReader isr = new InputStreamReader(is);  
	        BufferedReader br = new BufferedReader(isr);  
	        String res = br.readLine();
	        System.out.println(res);         
	        if(res.contains("_rev")){
				System.out.println("Get doc success.");
				JSONObject json = JSONObject.parseObject(res); //error
				System.out.println("Document id is : " + json.get("_id"));
			}   	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
