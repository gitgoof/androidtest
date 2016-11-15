/**
 * Project Name:JingZhenGu
 * File Name:ClassNameConstant.java
 * Package Name:com.gc.jingzhengu.constant
 * Date:2014-5-23下午1:38:49
 * Copyright (c) 2014, wangyd523@gmail.com All Rights Reserved.
 *
 */

package com.jzg.jzgcarsource.carindex.constant;

import java.util.ArrayList;
import java.util.List;

import com.jzg.jzgcarsource.carindex.vo.CarSourceMarkFlag;


/**
 * ClassName:ClassNameConstant <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014-5-23 下午1:38:49 <br/>
 * 
 * @author 汪渝栋
 * @version
 * @since JDK 1.6
 * @see
 */
public class Constant
{
	/**
	 * 确定列表标题和内容是否有重复标记
	 */
	public static final String IS_TITLE = "istitle";

	/**
	 * 上牌起始时间
	 */
	public static final int REGISTRATION_DATE = 1992;

	/**
	 * 默认
	 */
	public static final String LIST_DEFAULT = "0";

	/**
	 * 刷新
	 */
	public static final String LIST_REFRESH = "1";

	/**
	 * 加载
	 */
	public static final String LIST_LOAD_MORE = "2";

	/**
	 * 升序
	 */
	public static final String ASC = "asc";

	/**
	 * 降序
	 */
	public static final String DESC = "desc";

	/**
	 * 排序字段1:按价格
	 */
	public static final String ORDERITEM1 = "1";

	/**
	 * 排序字段2:按车龄
	 */
	public static final String ORDERITEM2 = "2";

	/**
	 * 排序字段3:按行驶公里
	 */
	public static final String ORDERITEM3 = "3";

	/**
	 * 请求服务器成功标示
	 */
	public static final int SUCCESS = 100;
	
	/**
	 * 获取车源标签列表
	 * @return 车源标签集合
	 */
	public static List<CarSourceMarkFlag> getMarkList(){
		
		List<CarSourceMarkFlag> markList = new ArrayList<CarSourceMarkFlag>();
		markList.add(new CarSourceMarkFlag("里程少(1万公里内)",false));
		markList.add(new CarSourceMarkFlag("车龄短(1年以内)",false));
		markList.add(new CarSourceMarkFlag("准新车(车况A级)",false));
		markList.add(new CarSourceMarkFlag("保值率高(年折旧低于10%)",false));
		markList.add(new CarSourceMarkFlag("无事故",false));
		markList.add(new CarSourceMarkFlag("超省油(百公里油耗低于6L)",false));
		markList.add(new CarSourceMarkFlag("有亮点配置",false));
		markList.add(new CarSourceMarkFlag("定期保养",false));
		markList.add(new CarSourceMarkFlag("一手交易(第一次交易过户)",false));
		markList.add(new CarSourceMarkFlag("多次过户",false));
		
		return markList;
		
	}
	
}
