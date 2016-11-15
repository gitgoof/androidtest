/**
 * @Title: CarDealerList.java
 * @Package com.gc.jzgpgswl.vo
 * @Description: TODO
 * Copyright: Copyright (c) 2011 
 * Company:北京精真估信息技术有限公司
 * 
 * @author Comsys-wangyd
 * @date 2015-10-9 上午11:04:38
 * @version V1.0
 */

package com.jzg.jzgcarsource.carindex.vo;

import java.io.Serializable;

/**
 * @ClassName: CarDealerList
 * @Description: 经销商实体
 * @author Comsys-wangyd
 * @date 2015-10-9 上午11:04:38
 * 
 */

public class CarDealer extends BaseObject implements Serializable
{

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 4558558214621867705L;

	// {"CarDealerName":"估爷","Id":1}
	private String CarDealerName;

	private int Id;

	public String getCarDealerName()
	{
		return CarDealerName;
	}

	public void setCarDealerName(String carDealerName)
	{
		CarDealerName = carDealerName;
	}

	public int getId()
	{
		return Id;
	}

	public void setId(int id)
	{
		Id = id;
	}

	@Override
	public String toString()
	{
		return "CarDealerList [CarDealerName=" + CarDealerName + ", Id=" + Id
				+ "]";
	}
}
