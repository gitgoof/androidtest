/**
 * Project Name:JingZhenGu
 * File Name:StyleList.java
 * Package Name:com.gc.jingzhengu.vo
 * Date:2014-6-16上午10:19:54
 * Copyright (c) 2014, wangyd523@gmail.com All Rights Reserved.
 *
 */

package com.jzg.jzgcarsource.carindex.vo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * ClassName:StyleList <br/>
 * Function: 车型列表实体对象. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014-6-16 上午10:19:54 <br/>
 * 
 * @author 汪渝栋
 * @version
 * @since JDK 1.6
 * @see
 */
public class StyleList extends BaseObject implements Serializable
{
	private ArrayList<StyleCategory> carStyles;

	public ArrayList<StyleCategory> getCarStyles()
	{
		return carStyles;
	}

	public void setCarStyles(ArrayList<StyleCategory> carStyles)
	{
		this.carStyles = carStyles;
	}
}
