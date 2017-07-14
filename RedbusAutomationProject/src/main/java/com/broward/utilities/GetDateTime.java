package com.broward.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.broward.base.Base;


public class GetDateTime {

	public static long suiteExecutionTime(){
		try {
			return (System.currentTimeMillis()-Base.suiteStartTime)/1000;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static long testCaseExecutionTime(){
		try {
			return (System.currentTimeMillis()-Base.testCaseStartTime)/1000;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static long stepExecutionTime(){
		try {
			return (System.currentTimeMillis()-Base.stepStartTime)/1000;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static String getCurrentDate(String sDateFormate){
		try {
			DateFormat dateFormat = new SimpleDateFormat(sDateFormate);
			Date date = new Date();
			return dateFormat.format(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String convertTime(long lTime){
		try {
			String sValue = "00:00:00";
			if(lTime<60)
				sValue = "00:00:"+lTime;
			else if(lTime<3600)
				sValue = "00:"+lTime/60+":"+lTime%60;
			else
				sValue = lTime/3600+":"+(lTime%3600)/60+":"+(lTime%3600)%60;
			return sValue;
		} catch (Exception e) {
			return null;
		}
	}
}