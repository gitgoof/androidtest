/**
 * Project Name:JingZhenGu
 * File Name:Style.java
 * Package Name:com.gc.jingzhengu.vo
 * Date:2014-6-16上午10:21:03
 * Copyright (c) 2014, wangyd523@gmail.com All Rights Reserved.
 *
 */

package com.jzg.jzgcarsource.carindex.vo;

import java.io.Serializable;

/**
 * ClassName:Style <br/>
 * Function: 车型实体. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014-6-16 上午10:21:03 <br/>
 * 
 * @see
 */
public class Style implements Serializable {
	private int id;
	private String name;
	private int year;
	private String nowMsrp;
	private int itemColor;
	private String FullName;
	private String MaxYEAR;
	private String MinYEAR;

	/**
	 * 年限名称
	 */
	private String manufacturerName;

	/**
	 * 字体颜色
	 */
	private int fontColor;

	private int MakeId;
	private String MakeName;

	private int ModelId;
	private String ModelName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFontColor() {
		return fontColor;
	}

	public void setFontColor(int fontColor) {
		this.fontColor = fontColor;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getNowMsrp() {
		return nowMsrp;
	}

	public void setNowMsrp(String nowMsrp) {
		this.nowMsrp = nowMsrp;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public int getItemColor() {
		return itemColor;
	}

	public void setItemColor(int itemColor) {
		this.itemColor = itemColor;
	}

	public String getFullName() {
		return FullName;
	}

	public void setFullName(String fullName) {
		FullName = fullName;
	}

	public String getMaxYEAR() {
		return MaxYEAR;
	}

	public void setMaxYEAR(String maxYEAR) {
		MaxYEAR = maxYEAR;
	}

	public String getMinYEAR() {
		return MinYEAR;
	}

	public void setMinYEAR(String minYEAR) {
		MinYEAR = minYEAR;
	}

	public int getMakeId() {
		return MakeId;
	}

	public void setMakeId(int makeId) {
		MakeId = makeId;
	}

	public String getMakeName() {
		return MakeName;
	}

	public void setMakeName(String makeName) {
		MakeName = makeName;
	}

	public int getModelId() {
		return ModelId;
	}

	public void setModelId(int modelId) {
		ModelId = modelId;
	}

	public String getModelName() {
		return ModelName;
	}

	public void setModelName(String modelName) {
		ModelName = modelName;
	}

	@Override
	public String toString() {
		return "Style [id=" + id + ", name=" + name + ", year=" + year
				+ ", nowMsrp=" + nowMsrp + ", itemColor=" + itemColor
				+ ", FullName=" + FullName + ", MaxYEAR=" + MaxYEAR
				+ ", MinYEAR=" + MinYEAR + ", manufacturerName="
				+ manufacturerName + ", fontColor=" + fontColor + ", MakeId="
				+ MakeId + ", MakeName=" + MakeName + ", ModelId=" + ModelId
				+ ", ModelName=" + ModelName + "]";
	}

}
