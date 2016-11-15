/**
 * Project Name:JingZhenGu
 * File Name:MakeList.java
 * Package Name:com.gc.jingzhengu.vo
 * Date:2014-6-12下午12:20:59
 * Copyright (c) 2014, wangyd523@gmail.com All Rights Reserved.
 *
 */

package com.jzg.jzgcarsource.carindex.vo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * ClassName:MakeList <br/>
 * Function: 热门推荐实体列表对象. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014-6-12 下午12:20:59 <br/>
 * 
 * @author 汪渝栋
 * @version
 * @since JDK 1.6
 * @see
 */
public class HotCarList extends BaseObject implements Serializable
{
	private ArrayList<HotCar> hotCars;

	public ArrayList<HotCar> getHotCars()
	{
		return hotCars;
	}

	public void setHotCars(ArrayList<HotCar> hotCars)
	{
		this.hotCars = hotCars;
	}

}
