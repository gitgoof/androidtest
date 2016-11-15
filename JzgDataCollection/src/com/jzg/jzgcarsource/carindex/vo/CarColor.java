/**
 * Project Name:JingZhenGu
 * File Name:CarColor.java
 * Package Name:com.gc.jingzhengu.vo
 * Date:2014-6-3下午12:06:39
 * Copyright (c) 2014, wangyd523@gmail.com All Rights Reserved.
 *
 */

package com.jzg.jzgcarsource.carindex.vo;

import java.io.Serializable;

/**
 * ClassName:CarColor <br/>
 * Function: 汽车颜色实体. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014-6-3 下午12:06:39 <br/>
 * 
 * @author 汪渝栋
 * @version
 * @since JDK 1.6
 * @see
 */
public class CarColor implements Serializable
{
	/**
	 * 汽车颜色对应的图片资源id
	 */
	private int colorPic;

	/**
	 * 汽车颜色名字
	 */
	private String colorName;
	
	private String carColorId;
	
	
	public CarColor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CarColor(String colorName, String carColorId) {
		super();
		this.colorName = colorName;
		this.carColorId = carColorId;
	}

	public String getCarColorId() {
		return carColorId;
	}

	public void setCarColorId(String carColorId) {
		this.carColorId = carColorId;
	}

	public int getColorPic()
	{
		return colorPic;
	}

	public void setColorPic(int colorPic)
	{
		this.colorPic = colorPic;
	}

	public String getColorName()
	{
		return colorName;
	}

	public void setColorName(String colorName)
	{
		this.colorName = colorName;
	}

	@Override
	public String toString() {
		return "CarColor [colorPic=" + colorPic + ", colorName=" + colorName
				+ ", carColorId=" + carColorId + "]";
	}

}
