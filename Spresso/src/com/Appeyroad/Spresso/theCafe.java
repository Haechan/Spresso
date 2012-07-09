package com.Appeyroad.Spresso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

public class theCafe {
	String name;
	String location;
	String phone;
	String note;
	String wifilist;
	String dclist;
	String etcnote;
	
	int index;//do i need this?
	int section;
	
	boolean consent;
	boolean wifi;
	boolean seat;
	
	double[] coordinate;
	HashMap<String,String[]> time;
	HashMap<String,ArrayList<String[]>> menu;
	
	boolean isOpen;//(contains result of method 'getIsOpen')
	
	//check whether cafe is open at time 'now'
	public Boolean getIsOpen(long now){
		String nowtime, opentime=null, closetime=null, nowdate=android.text.format.DateFormat.format("E", now).toString();
		
		if(nowdate.equals("Fri")){
			if(time.get("fri")!=null){
				opentime=time.get("fri")[0];
				closetime=time.get("fri")[1];
				Log.e(name, "fri");
			}
			else return false;
		}
		else if(nowdate.equals("Sat")){
			if(time.get("sat")!=null){
				opentime=time.get("sat")[0];
				closetime=time.get("sat")[1];
				Log.e(name, "sat");
			}
			else return false;
		}
		else if(nowdate.equals("Sun")){
			if(time.get("sun")!=null){
				opentime=time.get("sun")[0];
				closetime=time.get("sun")[1];
				Log.e(name, "sun");
			}
			else return false;
		}
		else if(time.get("monthu")!=null){
			opentime=time.get("monthu")[0];
			closetime=time.get("monthu")[1];
			Log.e(name, "monthu");
		}
		
		if(opentime==null || closetime==null) return false;
		else{
			nowtime=android.text.format.DateFormat.format("kk:mm:ss", now).toString();
			if(nowtime.compareTo(opentime)>0 && nowtime.compareTo(closetime)<0) return true;
			else return false;
		}
	}
	
	//section name constructor(case of header in cafe list of mainview)
	theCafe(String sectionName){
		index=-1;//-1 is index indicating header
		name=sectionName;
	}
	
	//cafe cell constructor(case of item(cafe) in cafe list of mainview)
	theCafe(XmlPullParser parser, int i, long now){
		index=i;
		int counter=-1;		
		String nametag=null, nametag_time;
		try {
			while ( parser.getEventType() != XmlPullParser.END_DOCUMENT ){ 
				if ( parser.getEventType() == XmlPullParser.START_TAG ){   
					//dont read any data until counter get into i
					if (counter!=i){
						if(parser.getName().equals("index") ){    
							nametag="index";
							counter=Integer.parseInt(parser.getAttributeValue(0));
						}
					}
					else{
						nametag=parser.getName();
						//string tag has so many names so they have to be distinguished by their own name
						if(nametag.equals("string")){
							nametag=parser.getAttributeValue(null, "name");
						}
						//time data parsing
						else if(nametag.equals("time")){
							time=new HashMap<String,String[]>();
							String[] temp=new String[2];
							int j=0;
							while ( parser.getEventType() != XmlPullParser.END_TAG || !parser.getName().equals("time") ){
								if(parser.getEventType()==XmlPullParser.START_TAG){
									nametag_time=parser.getName();
									if(nametag_time.equals("array")){
										nametag=parser.getAttributeValue(null, "name");
									}
								}
								else if(parser.getEventType()==XmlPullParser.TEXT && parser.getText().charAt(0)!='\n') {
									temp[j]=parser.getText();
									j++;
								}
								else if(parser.getEventType()==XmlPullParser.END_TAG)
									if(parser.getName().equals("array")){
										time.put(nametag, temp);
										temp=new String[2];
										j=0;
									}
								parser.next();
							}
						}
					}
				}
				else if (counter==i){
					if(parser.getEventType()== XmlPullParser.END_TAG){
						if(parser.getName().equals("cafe")) break;//it ends only when parser meets cafes[i]'s </cafe> 
					}
					else if(parser.getEventType()== XmlPullParser.TEXT && parser.getText().charAt(0)!='\n'){
						//data parsing
						if(nametag.equals("name")) name=parser.getText();
						else if(nametag.equals("section")) section=Integer.parseInt(parser.getText());
						else if(nametag.equals("consent")) consent=parser.getText().contains("true");
						else if(nametag.equals("wifi")) wifi=parser.getText().contains("true");
						else if(nametag.equals("seat")) seat=parser.getText().contains("true");
					}
				}
				parser.next();
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		isOpen=getIsOpen(now);
	}
		
	//detail cafe data constructor(case of data(cafe) in detailview)
	//gets more additional data than upper one(cafe cell constructor).
	theCafe(XmlPullParser parser, int i){
		index=i;
		int counter=-1;
		String nametag=null,nametag_menu, nametag_time;
		try {
			while ( parser.getEventType() != XmlPullParser.END_DOCUMENT ){  
				if ( parser.getEventType() == XmlPullParser.START_TAG ){   
					if (counter!=i){
						if(parser.getName().equals("index") ){ 
							nametag="index";
							counter=Integer.parseInt(parser.getAttributeValue(0));
						}
					}
					else{
						nametag=parser.getName();
						if(nametag.equals("string")){
							nametag=parser.getAttributeValue(null, "name");
						}
						else if(nametag.equals("coordinate")){
							coordinate=new double[2];
							int j=0;
							while ( parser.getEventType() != XmlPullParser.END_TAG || !parser.getName().equals("coordinate") ){
								if(parser.getEventType()==XmlPullParser.TEXT && parser.getText().charAt(0)!='\n') {
									coordinate[j]=Double.parseDouble(parser.getText());
									j++;
								}
								/*else if(parser.getEventType()==XmlPullParser.END_TAG)
									if(parser.getName().equals("coordinate")){
										time.put(nametag, temp);
								}*/
								parser.next();
							}
						}
						else if(nametag.equals("time")){
							time=new HashMap<String,String[]>();
							String[] temp=new String[2];
							int j=0;
							while ( parser.getEventType() != XmlPullParser.END_TAG || !parser.getName().equals("time") ){
								if(parser.getEventType()==XmlPullParser.START_TAG){
									nametag_time=parser.getName();
									if(nametag_time.equals("array")){
										nametag=parser.getAttributeValue(null, "name");
									}
								}
								else if(parser.getEventType()==XmlPullParser.TEXT && parser.getText().charAt(0)!='\n') {
									temp[j]=parser.getText();
									j++;
								}
								else if(parser.getEventType()==XmlPullParser.END_TAG)
									if(parser.getName().equals("array")){
										time.put(nametag, temp);
										temp=new String[2];
										j=0;
									}
								parser.next();
							}
						}
						//menu data parsing
						else if(nametag.equals("menu")){
							menu=new HashMap<String,ArrayList<String[]>>();
							ArrayList<String[]> templist=new ArrayList<String[]>();
							String[] temp=new String[2];
							int j=0;
							while ( parser.getEventType() != XmlPullParser.END_TAG || !parser.getName().equals("menu") ){
								if(parser.getEventType()==XmlPullParser.START_TAG){
									nametag_menu=parser.getName();
									if(nametag_menu.equals("array")){
										String menu_name=parser.getAttributeValue(null, "name");
										if(menu_name==null);
										else if(menu_name.equals("coffee")) nametag="coffee";
										else if(menu_name.equals("non-coffee")){
											menu.put(nametag, templist);
											templist=new ArrayList<String[]>();
											nametag="non-coffee";
										}
										else if(menu_name.equals("side")){
											menu.put(nametag, templist);
											templist=new ArrayList<String[]>();
											nametag="side";
										}
									}
								}
								else if(parser.getEventType()==XmlPullParser.TEXT && parser.getText().charAt(0)!='\n') {
									temp[j]=parser.getText();
									j++;
								}
								else if(parser.getEventType()==XmlPullParser.END_TAG)
									if(parser.getName().equals("array") && j==2){
										templist.add(temp);
										temp=new String[2];
										j=0;
									}
								parser.next();
							}
							menu.put(nametag, templist);
						}
					}
				}
				else if (counter==i){
					if(parser.getEventType()== XmlPullParser.END_TAG){
						if(parser.getName().equals("cafe")) break;
					}
					else if(parser.getEventType()== XmlPullParser.TEXT && parser.getText().charAt(0)!='\n'){
						if(nametag.equals("name")) name=parser.getText();
						else if(nametag.equals("location")) location=parser.getText();
						else if(nametag.equals("phone")) phone=parser.getText();
						else if(nametag.equals("note")) note=parser.getText();
						else if(nametag.equals("wifilist")) wifilist=parser.getText();
						else if(nametag.equals("dclist")) dclist=parser.getText();
						else if(nametag.equals("etcnote")) etcnote=parser.getText();
						else if(nametag.equals("section")) section=Integer.parseInt(parser.getText());
						else if(nametag.equals("consent")) consent=parser.getText().contains("true");
						else if(nametag.equals("wifi")) wifi=parser.getText().contains("true");
						else if(nametag.equals("seat")) seat=parser.getText().contains("true");
					}
				}
				parser.next();
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}