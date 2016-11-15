package com.jzg.jzgcarsource.carindex.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 发车实体类
 * 
 * @author jzg
 * 
 */
public class CarRelease extends BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private Style style;
	// 车型
	private String carStyle;
	// 车型标示
	private int styleId;
	// 城市
	private String city;
	// 城市标示
	private int cityId;
	// 省
	private String province;
	// 省id
	private int provinceId;
	// 里程
	private String mileage;
	// 颜色
	private CarColor carColor;
	// 面向商家价格
	private String rCarPriceB2B;
	// 面向客户价格
	private String rCarPriceB2C;
	// 姓名
	private String name;
	// 手机号
	private String phoneNum;
	// 车辆描述
	private String carDes;
	// 车辆图片id集合
	private List<String> carPics;
	// 上牌时间
	private String date;
	// 过户周期
	private String transferCycle;
	// 车源标签
	private List carMark;

	// 发车时 为0, 编辑车源时,为车源Id
	private String oldCarSourceId;
	/**
	 * vin码
	 */
	private String vin;
	/**
	 * 销售编号
	 */
	private String saleCode;
	/**
	 * 过户次数
	 */
	private String transferCount;
	/**
	 * 照片的ID
	 */
	private String dcpId;
	private String dcpIdTwo;

	/**
	 * 经销商
	 */
	private CarDealer dealer;

	/**
	 * 评估单图片1
	 */
	private String AssessImgUrl1;

	/**
	 * 评估单图片2
	 */
	private String AssessImgUrl2;

	/**
	 * 是否是经销商
	 */
	private boolean isDealer;

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	@Override
	public String toString() {
		return "CarRelease [carStyle=" + carStyle + ", styleId=" + styleId
				+ ", city=" + city + ", cityId=" + cityId + ", province="
				+ province + ", provinceId=" + provinceId + ", mileage="
				+ mileage + ", carColor=" + carColor + ", rCarPriceB2B="
				+ rCarPriceB2B + ", rCarPriceB2C=" + rCarPriceB2C + ", name="
				+ name + ", phoneNum=" + phoneNum + ", carDes=" + carDes
				+ ", carPics=" + carPics + ", date=" + date
				+ ", transferCycle=" + transferCycle + ", carMark=" + carMark
				+ ", oldCarSourceId=" + oldCarSourceId + ", vin=" + vin
				+ ", saleCode=" + saleCode + ", transferCount=" + transferCount
				+ ", dcpId=" + dcpId + ", dcpIdTwo=" + dcpIdTwo + ", dealer="
				+ dealer + ", AssessImgUrl1=" + AssessImgUrl1
				+ ", AssessImgUrl2=" + AssessImgUrl2 + ", isDealer=" + isDealer
				+ "]";
	}

	public String getDcpIdTwo() {
		return dcpIdTwo;
	}

	public void setDcpIdTwo(String dcpIdTwo) {
		this.dcpIdTwo = dcpIdTwo;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getSaleCode() {
		return saleCode;
	}

	public void setSaleCode(String saleCode) {
		this.saleCode = saleCode;
	}

	public String getTransferCount() {
		return transferCount;
	}

	public void setTransferCount(String transferCount) {
		this.transferCount = transferCount;
	}

	public String getDcpId() {
		return dcpId;
	}

	public void setDcpId(String dcpId) {
		this.dcpId = dcpId;
	}

	public String getOldCarSourceId() {
		return oldCarSourceId;
	}

	public void setOldCarSourceId(String oldCarSourceId) {
		this.oldCarSourceId = oldCarSourceId;
	}

	public String getTransferCycle() {
		return transferCycle;
	}

	public void setTransferCycle(String transferCycle) {
		this.transferCycle = transferCycle;
	}

	public List getCarMark() {
		return carMark;
	}

	public void setCarMark(List carMark) {
		this.carMark = carMark;
	}

	public String getCarStyle() {
		return carStyle;
	}

	public void setCarStyle(String carStyle) {
		this.carStyle = carStyle;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMileage() {
		return mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	public CarColor getCarColor() {
		return carColor;
	}

	public void setCarColor(CarColor carColor) {
		this.carColor = carColor;
	}

	public String getrCarPriceB2B() {
		return rCarPriceB2B;
	}

	public void setrCarPriceB2B(String rCarPriceB2B) {
		this.rCarPriceB2B = rCarPriceB2B;
	}

	public String getrCarPriceB2C() {
		return rCarPriceB2C;
	}

	public void setrCarPriceB2C(String rCarPriceB2C) {
		this.rCarPriceB2C = rCarPriceB2C;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getCarDes() {
		return carDes;
	}

	public void setCarDes(String carDes) {
		this.carDes = carDes;
	}

	public List<String> getCarPics() {
		return carPics;
	}

	public void setCarPics(List<String> carPics) {
		this.carPics = carPics;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// 拍摄6张标准姿势，让买家更了解您的爱车。我们的数据表明，拍摄过这6张照片的车，成交率会高50%哦！

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getStyleId() {
		return styleId;
	}

	public void setStyleId(int styleId) {
		this.styleId = styleId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public CarDealer getDealer() {
		return dealer;
	}

	public void setDealer(CarDealer dealer) {
		this.dealer = dealer;
	}

	public boolean isDealer() {
		return isDealer;
	}

	public void setDealer(boolean isDealer) {
		this.isDealer = isDealer;
	}

	public String getAssessImgUrl1() {
		return AssessImgUrl1;
	}

	public void setAssessImgUrl1(String assessImgUrl1) {
		AssessImgUrl1 = assessImgUrl1;
	}

	public String getAssessImgUrl2() {
		return AssessImgUrl2;
	}

	public void setAssessImgUrl2(String assessImgUrl2) {
		AssessImgUrl2 = assessImgUrl2;
	}

}
