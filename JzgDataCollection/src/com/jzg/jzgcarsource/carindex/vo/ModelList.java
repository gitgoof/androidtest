/**
 * Project Name:JingZhenGu
 * File Name:ModelList.java
 * Package Name:com.gc.jingzhengu.vo
 * Date:2014-6-13上午11:33:32
 * Copyright (c) 2014, wangyd523@gmail.com All Rights Reserved.
 *
 */

package com.jzg.jzgcarsource.carindex.vo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * ClassName:ModelList <br/>
 * Function: 车系实体对象列表. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014-6-13 上午11:33:32 <br/>
 * 
 * @author 汪渝栋
 * @version
 * @since JDK 1.6
 * @see
 */
public class ModelList extends BaseObject implements Serializable
{
	private ArrayList<ModelCategory> modeCategroy;

	public ArrayList<ModelCategory> getModels()
	{
		return modeCategroy;
	}

	public void setModels(ArrayList<ModelCategory> modeCategroy)
	{
		this.modeCategroy = modeCategroy;
	}
}
