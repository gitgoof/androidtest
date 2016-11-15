/**
 * Project Name:JZGPingGuShiWangLuo
 * File Name:CarDetail.java
 * Package Name:com.gc.jzgpgswl.vo
 * Date:2014-11-27下午8:10:46
 * Copyright (c) 2014, wangyd523@gmail.com All Rights Reserved.
 *
 */

package com.jzg.jzgcarsource.carindex.vo;

import java.io.Serializable;

/**
 * ClassName:CarDetail <br/>
 * Function: 查价车辆详情实体. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014-11-27 下午8:10:46 <br/>
 * 
 * @author 汪渝栋
 * @version
 * @since JDK 1.6
 * @see
 */
public class CarDetail extends BaseObject implements Serializable
{
	// 建议收车价
	private String appraise;

	private String makeName;

	private String modelName;

	private String styleName;

	private String year;

	// 厂商指导价
	private String price;

	private String pic;
	
	//排放标准
	private String Pfbz;
	
	//建议收车价
	private String C2BPrice;
	
	//建议售车价
	private String B2CPrice;
	
	private String B2BPrice;
	
	private String Mileage;
	
	private String RegDate;
	
	private String RecordId;
	

	public String getAppraise()
	{
		return appraise;
	}

	public void setAppraise(String appraise)
	{
		this.appraise = appraise;
	}

	public String getMakeName()
	{
		return makeName;
	}

	public void setMakeName(String makeName)
	{
		this.makeName = makeName;
	}

	public String getModelName()
	{
		return modelName;
	}

	public void setModelName(String modelName)
	{
		this.modelName = modelName;
	}

	public String getStyleName()
	{
		return styleName;
	}

	public void setStyleName(String styleName)
	{
		this.styleName = styleName;
	}

	public String getYear()
	{
		return year;
	}

	public void setYear(String year)
	{
		this.year = year;
	}

	public String getPrice()
	{
		return price;
	}

	public void setPrice(String price)
	{
		this.price = price;
	}

	public String getPic()
	{
		return pic;
	}

	public void setPic(String pic)
	{
		this.pic = pic;
	}

	
	public String getPfbz() {
		return Pfbz;
	}

	public void setPfbz(String pfbz) {
		Pfbz = pfbz;
	}

	public String getC2BPrice() {
		return C2BPrice;
	}

	public void setC2BPrice(String c2bPrice) {
		C2BPrice = c2bPrice;
	}

	public String getB2CPrice() {
		return B2CPrice;
	}

	public void setB2CPrice(String b2cPrice) {
		B2CPrice = b2cPrice;
	}

	
	public String getB2BPrice() {
		return B2BPrice;
	}

	public void setB2BPrice(String b2bPrice) {
		B2BPrice = b2bPrice;
	}

	public String getMileage() {
		return Mileage;
	}

	public void setMileage(String mileage) {
		Mileage = mileage;
	}

	public String getRegDate() {
		return RegDate;
	}

	public void setRegDate(String regDate) {
		RegDate = regDate;
	}

	public String getRecordId() {
		return RecordId;
	}

	public void setRecordId(String recordId) {
		RecordId = recordId;
	}

	@Override
	public String toString() {
		return "CarDetail [appraise=" + appraise + ", makeName=" + makeName
				+ ", modelName=" + modelName + ", styleName=" + styleName
				+ ", year=" + year + ", price=" + price + ", pic=" + pic
				+ ", Pfbz=" + Pfbz + ", C2BPrice=" + C2BPrice + ", B2CPrice="
				+ B2CPrice + ", Mileage=" + Mileage + ", RegDate=" + RegDate
				+ ", RecordId=" + RecordId + "]";
	}

}
