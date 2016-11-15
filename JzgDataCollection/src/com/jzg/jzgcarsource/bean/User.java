package com.jzg.jzgcarsource.bean;

import java.io.Serializable;

public class User implements Serializable {

	/**
	 * status : 100 UserInfoModel :
	 * {"UserType":"经销商","CityId":201,"CityName":"北京"
	 * ,"Password":"666666","PhoneNumber"
	 * :"13522642757","UserName":"yqdz","ProvinceId"
	 * :2,"State":1,"Liaisons":"邱中阳"
	 * ,"CreateTime":"/Date(1433088000000+0800)/","Id"
	 * :1,"NearbyCity":2601,"ProvinceName":"北京","DistributorName":"一汽大众北京经销商"}
	 * msg : 登录成功
	 */
	private int status;
	private String Id;
	private String Explain;
	private String Email;
	private String Mobile;
	// private UserInfoModelEntity UserInfoModel;
	private String CityName;
	private String UserName;
	private String ProvinceId;
	private String ProvinceName;
	private String CityId;
	private String msg;
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getExplain() {
		return Explain;
	}
	public void setExplain(String explain) {
		Explain = explain;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getMobile() {
		return Mobile;
	}
	public void setMobile(String mobile) {
		Mobile = mobile;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getProvinceId() {
		return ProvinceId;
	}
	public void setProvinceId(String provinceId) {
		ProvinceId = provinceId;
	}
	public String getCityId() {
		return CityId;
	}
	public void setCityId(String cityId) {
		CityId = cityId;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCityName() {
		return CityName;
	}
	public void setCityName(String cityName) {
		CityName = cityName;
	}
	public String getProvinceName() {
		return ProvinceName;
	}
	public void setProvinceName(String provinceName) {
		ProvinceName = provinceName;
	}

}
