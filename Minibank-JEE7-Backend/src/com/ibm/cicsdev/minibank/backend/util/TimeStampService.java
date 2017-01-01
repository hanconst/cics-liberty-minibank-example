package com.ibm.cicsdev.minibank.backend.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStampService {

	private static TimeStampService instance = null;

	public static TimeStampService getTimeStampInstance() {
		if (instance == null) {
			instance = new TimeStampService();
		}
		return instance;
	}

	public String getCurrentTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = formatter.format(new Date());
		return currentTime;
	}

}
