/**
 * Project Name:JZGPingGuShiWangLuo
 * File Name:Hot.java
 * Package Name:com.gc.jzgpgswl.vo
 * Date:2014-11-27下午6:03:24
 * Copyright (c) 2014, wangyd523@gmail.com All Rights Reserved.
 *
 */

package com.jzg.jzgcarsource.carindex.vo;

import java.io.Serializable;

/**
 * ClassName:Hot <br/>
 * Function: 热门车实体. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014-11-27 下午6:03:24 <br/>
 * 
 * @author 汪渝栋
 * @version
 * @since JDK 1.6
 * @see
 */
public class HotCar extends BaseObject implements Serializable
{
	private int makeId;

	private String makeName;

	private String makeLogo;

	public int getMakeId()
	{
		return makeId;
	}

	public void setMakeId(int makeId)
	{
		this.makeId = makeId;
	}

	public String getMakeName()
	{
		return makeName;
	}

	public void setMakeName(String makeName)
	{
		this.makeName = makeName;
	}

	public String getMakeLogo()
	{
		return makeLogo;
	}

	public void setMakeLogo(String makeLogo)
	{
		this.makeLogo = makeLogo;
	}

}
