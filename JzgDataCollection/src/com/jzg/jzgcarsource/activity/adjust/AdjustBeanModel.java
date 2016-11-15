package com.jzg.jzgcarsource.activity.adjust;

import java.io.Serializable;
/**
 * @Description: 返回数据
 * @Package com.jzg.jzgcarsource.activity.adjust AdjustBeanModel.java
 * @author gf
 * @date 2016-11-3 下午2:14:06
 */
public class AdjustBeanModel implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 1L;
	/** 数据总量 **/
	private String Counts;
	private String CompleteDate;
	/** 记录主键ID **/
	private String Id;
	/**  **/
	private String ProvinceName;
	/**  **/
	private String ProvinceId;
	/**  **/
	private String CityName;
	/**  **/
	private String CityId;
	/**  **/
	private String CarFullName;
	/** 新车厂商指导价 **/
	private String Msrp;
	/** 上牌时间 **/
	private String Launchdate;
	/** 行驶里程 **/
	private String TypicalMileage;
	/**  **/
	private String status;
	/**  **/
	private String msg;
	
	public String getCompleteDate() {
		return CompleteDate;
	}
	public void setCompleteDate(String completeDate) {
		CompleteDate = completeDate;
	}
	public String getCounts() {
		return Counts;
	}
	public void setCounts(String counts) {
		Counts = counts;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getProvinceName() {
		return ProvinceName;
	}
	public void setProvinceName(String provinceName) {
		ProvinceName = provinceName;
	}
	public String getProvinceId() {
		return ProvinceId;
	}
	public void setProvinceId(String provinceId) {
		ProvinceId = provinceId;
	}
	public String getCityName() {
		return CityName;
	}
	public void setCityName(String cityName) {
		CityName = cityName;
	}
	public String getCityId() {
		return CityId;
	}
	public void setCityId(String cityId) {
		CityId = cityId;
	}
	public String getCarFullName() {
		return CarFullName;
	}
	public void setCarFullName(String carFullName) {
		CarFullName = carFullName;
	}
	public String getMsrp() {
		return Msrp;
	}
	public void setMsrp(String msrp) {
		Msrp = msrp;
	}
	public String getLaunchdate() {
		return Launchdate;
	}
	public void setLaunchdate(String launchdate) {
		Launchdate = launchdate;
	}
	public String getTypicalMileage() {
		return TypicalMileage;
	}
	public void setTypicalMileage(String typicalMileage) {
		TypicalMileage = typicalMileage;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
