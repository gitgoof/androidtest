/**
 * Project Name:JZGPingGuShi
 * File Name:HttpService.java
 * Package Name:com.gc.jzgpinggushi.app
 * Date:2014-9-1上午10:36:02
 * Copyright (c) 2014, wangyd523@gmail.com All Rights Reserved.
 *
 */

package com.jzg.jzgcarsource.carindex.app;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.net.Uri;
import android.os.Handler;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache.Entry;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jzg.jzgcarsource.activity.R;
import com.jzg.jzgcarsource.application.AppContext;
import com.jzg.jzgcarsource.carindex.vo.HotCarList;
import com.jzg.jzgcarsource.carindex.vo.MakeList;
import com.jzg.jzgcarsource.carindex.vo.ModelList;
import com.jzg.jzgcarsource.carindex.vo.StyleList;
import com.jzg.jzgcarsource.utils.JsonObjectImpl;
import com.jzg.jzgcarsource.utils.MD5Utils;
import com.jzg.jzgcarsource.utils.MessageUtils;
import com.jzg.jzgcarsource.utils.SignUtils;

/**
 * ClassName:HttpService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014-9-1 上午10:36:02 <br/>
 * 
 * @author 汪渝栋
 * @version
 * @since JDK 1.6
 * @see
 */
public class HttpService
{
	
	private static final String jzgGuyeHttp = "http://api.jingzhengu.com";
	
	/**
	 * 检查网络并弹出Toast提示
	 * 
	 * @return
	 */

	private static boolean checkNetwork(final Handler hand)
	{
		if (!AppContext.isNetworkConnected())
		{
			if (hand != null)
				hand.obtainMessage(R.id.close_dialog).sendToTarget();
			return false;
		}
		return true;
	}
	
	/**
	 * 热门车
	 * 
	 * @param mHandler
	 * @param requestQueue
	 * @param successId
	 */
	public static void getHotCarList(final Handler mHandler,
			RequestQueue requestQueue, final int successId)
	{
		if (!checkNetwork(mHandler))
			return;
		JsonObjectRequest jsonObjRequest;
		String url = jzgGuyeHttp
				+ "/app/bbs/CarSource/GetHotMakeList.ashx?&sign="
				+ SignUtils.signForHotCarList();

		Entry entry = requestQueue.getCache().get(url);

		if (entry != null && !entry.isExpired())
		{
			String cache = new String(entry.data);
			JsonObjectImpl jsonObjectImpl = new JsonObjectImpl();
			HotCarList hotCarList = jsonObjectImpl.parserHotCarList(cache);
			MessageUtils.sendMessage(mHandler, successId, hotCarList);
		} else
		{
			jsonObjRequest = new JsonObjectRequest(Request.Method.POST, url,
					null, new Response.Listener<JSONObject>()
					{
						@Override
						public void onResponse(JSONObject response)
						{
							JsonObjectImpl jsonObjectImpl = new JsonObjectImpl();
							HotCarList hotCarList = jsonObjectImpl
									.parserHotCarList(response.toString());
							MessageUtils.sendMessage(mHandler, successId,
									hotCarList);
						}
					}, new Response.ErrorListener()
					{
						@Override
						public void onErrorResponse(VolleyError error)
						{
							if (error instanceof NetworkError)
							{
								System.out.println(error.toString());
							} else if (error instanceof ServerError)
							{
								System.out.println(error.toString());
							} else if (error instanceof AuthFailureError)
							{
								System.out.println(error.toString());
							} else if (error instanceof ParseError)
							{
								System.out.println(error.toString());
							} else if (error instanceof NoConnectionError)
							{
								System.out.println(error.toString());
							} else if (error instanceof TimeoutError)
							{
								System.out.println(error.toString());
							}
							MessageUtils.sendMessage(mHandler,
									R.id.net_error_back, error.getMessage());
						}
					});
			jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
					DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
					DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
			// jsonObjRequest.setTag("my_tag");
			requestQueue.add(jsonObjRequest);
		}
	}

	/**
	 * 获取汽车品牌列表
	 * 
	 * @param mHandler
	 * @param requestQueue
	 * @param successId
	 */
	public static void getMakeList(final Handler mHandler,
			RequestQueue requestQueue, final int successId)
	{
		if (!checkNetwork(mHandler))
			return;
		JsonObjectRequest jsonObjRequest;
		String url = jzgGuyeHttp + "/APP/Car/GetMakeByLetter.ashx?&sign="
				+ SignUtils.signForPost();
		// Uri.Builder builder = Uri.parse(url).buildUpon();
		// builder.appendQueryParameter("&sign", SignUtils.signForMakeList());
		Entry entry = requestQueue.getCache().get(url);
		if (entry != null && !entry.isExpired())
		{
			String cache = new String(entry.data);
			JsonObjectImpl jsonObjectImpl = new JsonObjectImpl();
			MakeList makeList = jsonObjectImpl.parserMakeList(cache);
			MessageUtils.sendMessage(mHandler, R.id.makelist, makeList);
		} else
		{
			jsonObjRequest = new JsonObjectRequest(Request.Method.POST, url,
					null, new Response.Listener<JSONObject>()
					{
						@Override
						public void onResponse(JSONObject response)
						{
							try
							{
								JsonObjectImpl jsonObjectImpl = new JsonObjectImpl();
								MakeList makeList = jsonObjectImpl
										.parserMakeList(response.toString());
								MessageUtils.sendMessage(mHandler,
										R.id.makelist, makeList);
							} catch (Exception e)
							{
								e.printStackTrace();
							}
						}
					}, new Response.ErrorListener()
					{
						@Override
						public void onErrorResponse(VolleyError error)
						{
							if (error instanceof NetworkError)
							{
								System.out.println(error.toString());
							} else if (error instanceof ServerError)
							{
								System.out.println(error.toString());
							} else if (error instanceof AuthFailureError)
							{
								System.out.println(error.toString());
							} else if (error instanceof ParseError)
							{
								System.out.println(error.toString());
							} else if (error instanceof NoConnectionError)
							{
								System.out.println(error.toString());
							} else if (error instanceof TimeoutError)
							{
								System.out.println(error.toString());
							}
							MessageUtils.sendMessage(mHandler,
									R.id.net_error_back, error.getMessage());
						}
					});
			jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
					DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
					DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
			// jsonObjRequest.setTag("my_tag");
			requestQueue.add(jsonObjRequest);
		}
	}

	/**
	 * 获取汽车车系列表 getModelList: <br/>
	 * 
	 * @author wang
	 * @param makeid
	 *            品牌id
	 * @return
	 * @throws Exception
	 * @since JDK 1.6
	 */
	public static void getModelList(final Handler mHandler,
			RequestQueue requestQueue, final int successId, String makeid)
	{
		if (!checkNetwork(mHandler))
			return;
		JsonObjectRequest jsonObjRequest;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("MakeId", makeid);
		String sign = MD5Utils.getMD5Sign(map);
		String url = jzgGuyeHttp
				+ "/APP/Car/GetGroupModelByManufacturerId.ashx";
		Uri.Builder builder = Uri.parse(url).buildUpon();
		builder.appendQueryParameter("MakeId", makeid);
		builder.appendQueryParameter("sign", sign);
		jsonObjRequest = new JsonObjectRequest(Request.Method.POST,
				builder.toString(), null, new Response.Listener<JSONObject>()
				{
					@Override
					public void onResponse(JSONObject response)
					{
						try
						{
							JsonObjectImpl jsonObjectImpl = new JsonObjectImpl();
							ModelList modelList = jsonObjectImpl
									.parserModelList(response.toString());
							// System.out.println("queryCarList response is "
							// + queryCarList.toString());
							MessageUtils.sendMessage(mHandler, successId,
									modelList);
						} catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
						if (error instanceof NetworkError)
						{
							System.out.println(error.toString());
						} else if (error instanceof ServerError)
						{
							System.out.println(error.toString());
						} else if (error instanceof AuthFailureError)
						{
							System.out.println(error.toString());
						} else if (error instanceof ParseError)
						{
							System.out.println(error.toString());
						} else if (error instanceof NoConnectionError)
						{
							System.out.println(error.toString());
						} else if (error instanceof TimeoutError)
						{
							System.out.println(error.toString());
						}
						MessageUtils.sendMessage(mHandler, R.id.net_error_back,
								error.getMessage());
					}
				});
		jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		jsonObjRequest.setTag("my_tag");
		requestQueue.add(jsonObjRequest);
	}

	/**
	 * 获取汽车类型数据 getStyleList: <br/>
	 * 
	 * @author wang
	 * @param modelid
	 * @return
	 * @throws Exception
	 * @since JDK 1.6
	 */
	public static void getStyleList(final Handler mHandler,
			RequestQueue requestQueue, final int successId, String modelid)
	{
		if (!checkNetwork(mHandler))
			return;
		JsonObjectRequest jsonObjRequest;
		String url = jzgGuyeHttp
				+ "/app/car/GetGroupStyleByModelId.ashx?&sign="
				+ SignUtils.signForPost();
		Uri.Builder builder = Uri.parse(url).buildUpon();
		builder.appendQueryParameter("ModelId", modelid);
		// builder.appendQueryParameter("sign",
		// SignUtils.signForStylelList(modelid));
		jsonObjRequest = new JsonObjectRequest(Request.Method.POST,
				builder.toString(), null, new Response.Listener<JSONObject>()
				{
					@Override
					public void onResponse(JSONObject response)
					{
						try
						{

							JsonObjectImpl jsonObjectImpl = new JsonObjectImpl();
							StyleList styleList = jsonObjectImpl
									.parserStyleList(response.toString());
							// System.out.println("queryCarList response is "
							// + queryCarList.toString());
							MessageUtils.sendMessage(mHandler, successId,
									styleList);
						} catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
						if (error instanceof NetworkError)
						{
							System.out.println(error.toString());
						} else if (error instanceof ServerError)
						{
							System.out.println(error.toString());
						} else if (error instanceof AuthFailureError)
						{
							System.out.println(error.toString());
						} else if (error instanceof ParseError)
						{
							System.out.println(error.toString());
						} else if (error instanceof NoConnectionError)
						{
							System.out.println(error.toString());
						} else if (error instanceof TimeoutError)
						{
							System.out.println(error.toString());
						}

					}
				});
		jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		jsonObjRequest.setTag("my_tag");
		requestQueue.add(jsonObjRequest);

	}}
