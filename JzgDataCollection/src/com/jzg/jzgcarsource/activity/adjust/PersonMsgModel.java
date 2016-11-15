package com.jzg.jzgcarsource.activity.adjust;

import java.io.Serializable;
/**
 * @Description: 个人中心数据
 * @Package com.jzg.jzgcarsource.activity.adjust PersonMsgModel.java
 * @author gf
 * @date 2016-11-3 下午3:08:47
 */
public class PersonMsgModel implements Serializable {
	/**
	 * UID
	 */
	private static final long serialVersionUID = 1L;
	/** 本日已完成调价 **/
	private String CompleteCountByDay;
	/** 本期完成调价 **/
	private String CompleteCountByUserIdAndTimes;
	/** 本期需调价总数 **/
	private String CountByUserIdAndTimes;
	/** 本期号 **/
	private String DataTimes;
	/** 分配调价记录总数 **/
	private String Counts;
	/** 本日提交数据 **/
	private String SubmitCountByDay;
	/** 总提交数据 **/
	private String SubmitDataByUser;
	/**
	 * 擅长品牌
	 */
	private String AdeptMake;
	
	public String getDataTimes() {
		return DataTimes;
	}
	public void setDataTimes(String dataTimes) {
		DataTimes = dataTimes;
	}
	public String getCompleteCountByDay() {
		return CompleteCountByDay;
	}
	public void setCompleteCountByDay(String completeCountByDay) {
		CompleteCountByDay = completeCountByDay;
	}
	public String getCompleteCountByUserIdAndTimes() {
		return CompleteCountByUserIdAndTimes;
	}
	public void setCompleteCountByUserIdAndTimes(
			String completeCountByUserIdAndTimes) {
		CompleteCountByUserIdAndTimes = completeCountByUserIdAndTimes;
	}
	public String getCountByUserIdAndTimes() {
		return CountByUserIdAndTimes;
	}
	public void setCountByUserIdAndTimes(String countByUserIdAndTimes) {
		CountByUserIdAndTimes = countByUserIdAndTimes;
	}
	public String getCounts() {
		return Counts;
	}
	public void setCounts(String counts) {
		Counts = counts;
	}
	public String getSubmitCountByDay() {
		return SubmitCountByDay;
	}
	public void setSubmitCountByDay(String submitCountByDay) {
		SubmitCountByDay = submitCountByDay;
	}
	public String getSubmitDataByUser() {
		return SubmitDataByUser;
	}
	public void setSubmitDataByUser(String submitDataByUser) {
		SubmitDataByUser = submitDataByUser;
	}
	public String getAdeptMake() {
		return AdeptMake;
	}
	public void setAdeptMake(String adeptMake) {
		AdeptMake = adeptMake;
	}
}
