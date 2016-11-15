package com.jzg.jzgcarsource.carindex.vo;

import java.io.Serializable;
/**
 * 记录车源标签位置,选中状态
 * @author jzg
 *
 */
public class CarSourceMarkFlag implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String markStr;
	private boolean flag;
	
	
	public CarSourceMarkFlag(String markStr, boolean flag) {
		super();
		this.markStr = markStr;
		this.flag = flag;
	}

	public String getMarkStr() {
		return markStr;
	}

	public void setMarkStr(String markStr) {
		this.markStr = markStr;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
}
