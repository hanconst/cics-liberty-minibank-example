/*
 Copyright IBM Corporation 2014

 LICENSE: Apache License
          Version 2.0, January 2004
          http://www.apache.org/licenses/

 The following code is sample code created by IBM Corporation.
 This sample code is not part of any standard IBM product and
 is provided to you solely for the purpose of assisting you in
 the development of your applications.  The code is provided
 'as is', without warranty or condition of any kind.  IBM shall
 not be liable for any damages arising out of your use of the
 sample code, even if IBM has been advised of the possibility
 of such damages.
*/

package com.ibm.cics.minibank.entity;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Enum type for customer gender
 */
public enum UserGender {

	MALE {
		public String getDesc() {
			return "male";
		}
	},
	FEMALE {
		public String getDesc() {
			return "female";
		}
	};

	public static Map<String, String> toStringMap() {
		UserGender[] type = values();
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (type != null) {
			for (UserGender e : type) {
				map.put(e.toString(), e.getDesc());
			}
		}
		return map;
	}

	public abstract String getDesc();
}
