/**
 * Project Name:JZGPingGuShi
 * File Name:ActivityHelp.java
 * Package Name:com.gc.jzgpinggushi.app
 * Date:2014-9-1上午10:17:48
 * Copyright (c) 2014, wangyd523@gmail.com All Rights Reserved.
 *
 */

package com.jzg.jzgcarsource.carindex.app;

import java.io.Serializable;

import android.content.Context;
import android.content.Intent;

/**
 * ClassName:ActivityHelp <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014-9-1 上午10:17:48 <br/>
 * 
 * @author 汪渝栋
 * @version
 * @since JDK 1.6
 * @see
 */
public class ActivityHelp
{

	/**
	 * 无参activity启动 startActivity: <br/>
	 * 
	 * @author wang
	 * @param context
	 * @param cls
	 * @since JDK 1.6
	 */
	@SuppressWarnings("rawtypes")
	public static void startActivity(Context context, Class cls)
	{
		Intent intent = new Intent(context, cls);
		context.startActivity(intent);
	}

	/**
	 * 有参activity启动， startActivity: <br/>
	 * 
	 * @author wang
	 * @param context
	 *            当前context
	 * @param cls
	 *            启动的activity
	 * @param nameFlag
	 *            名称标记，启动activity获取对应数据使用
	 * @param serializable
	 *            参数实体
	 * @since JDK 1.6
	 */
	public static void startActivity(Context context, Class cls,
			String nameFlag, Serializable serializable)
	{
		Intent intent = new Intent(context, cls);
		intent.putExtra(nameFlag, serializable);
		context.startActivity(intent);
	}

	/**
	 * 有参activity启动， startActivity: <br/>
	 * 
	 * @author wang
	 * @param context
	 *            当前context
	 * @param cls
	 *            启动的activity
	 * @param nameFlag
	 *            名称标记，启动activity获取对应数据使用
	 * @param serializable
	 *            参数实体 @param intentflag intent标记
	 * @since JDK 1.6
	 */
	public static void startActivity(Context context, Class cls,
			String nameFlag, Serializable serializable, int intentflag)
	{
		Intent intent = new Intent(context, cls);
		intent.putExtra(nameFlag, serializable);
		intent.addFlags(intentflag);
		context.startActivity(intent);
	}

	public static void startActivity(Context context, Class cls, int intentflag)
	{
		Intent intent = new Intent(context, cls);
		intent.addFlags(intentflag);
		context.startActivity(intent);
	}

}
