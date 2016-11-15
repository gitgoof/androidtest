/**
 * Project Name:JZGPingGuShi
 * File Name:JsonObject.java
 * Package Name:com.gc.jzgpinggushi.json
 * Date:2014-9-1上午10:51:32
 * Copyright (c) 2014, wangyd523@gmail.com All Rights Reserved.
 *
 */

package com.jzg.jzgcarsource.utils;

import java.io.Serializable;

import com.jzg.jzgcarsource.bean.CityList;
import com.jzg.jzgcarsource.bean.ProvinceList;

/**
 * ClassName:JsonObject <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014-9-1 上午10:51:32 <br/>
 */
public interface JsonObject {

	/**
	 * 生成json数据 generateJson: <br/>
	 */
	String generateJson(Serializable object);

	/**
	 * 解析省数据 parserProvinceList: <br/>
	 */
	ProvinceList parserProvinceList(String jsonData);

	/**
	 * 解析市数据 parserCityList: <br/>
	 */
	CityList parserCityList(String jsonData);

}
