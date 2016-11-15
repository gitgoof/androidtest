package com.jzg.jzgcarsource.utils;

import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.AuthFailureError;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jzg.jzgcarsource.activity.R;
import com.jzg.jzgcarsource.application.AppContext;
import com.jzg.jzgcarsource.bean.CityList;
import com.jzg.jzgcarsource.bean.ProvinceList;

public class HttpServiceHelper {

	private static String TAG = "HttpServiceHelper";

	private static final String jzgGuyeHttp = "http://api.jingzhengu.com";
	// ceshi55 kizo6DcvkrQ9 ceshi56 ZGz1qx1jjfch
	public static final String BASE_URL ="http://xiaoshou.guchewang.com/appxsgcwapi";
//	public static final String BASE_URL ="http://192.168.6.107:8080/appxsgcwapi";
//	public static final String BASE_URL = "http://192.168.0.15:8088/appxsgcwapi";

//	public static final String BASE_URL_NEW ="http://192.168.6.107/AppraiserService.ashx";
	public static final String BASE_URL_NEW ="http://guzhiadmin.jingzhengu.com/AppraiserService.ashx";
	/**
	 * 登录
	 */
	public static void login(Context context, final Handler handler,
			final Map<String, String> requestMap, final int sucId,
			final int failId) {
		String httpurl = BASE_URL + "/Login.ashx";
		request(context, handler, requestMap, sucId, failId, httpurl);
	}
	
	public static void loginNew(Context context, final Handler handler,
			final Map<String, String> requestMap, final int sucId,
			final int failId) {
		String httpurl = BASE_URL_NEW ;
		request(context, handler, requestMap, sucId, failId, httpurl);
	}
	/**
	 * 新接口请求 op不同，其他相同
	 * @param context
	 * @param handler
	 * @param requestMap
	 * @param sucId
	 * @param failId
	 */
	public static void newToRequest(Context context, final Handler handler,
			final Map<String, String> requestMap, final int sucId,
			final int failId) {
		String httpurl = BASE_URL_NEW ;
		request(context, handler, requestMap, sucId, failId, httpurl);
	}

	/**
	 * 登录
	 */
	public static void post(Context context, final Handler handler,
			final Map<String, String> requestMap, final int sucId,
			final int failId) {
		String httpurl = BASE_URL + "/PgsPrice.ashx";
		request(context, handler, requestMap, sucId, failId, httpurl);
	}

	public static void requestGet(Context context, final Handler handler,
			final Map<String, String> requestMap, final int sucId,
			final int failId, String httpurl) {

		RequestQueue requestQueue = Volley.newRequestQueue(context);
		StringRequest stringRequest = new StringRequest(Request.Method.GET,
				httpurl, new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.d(TAG, "网络返回数据 -> " + response);
						Message msg = new Message();
						msg.obj = response;
						msg.what = sucId;
						handler.sendMessage(msg);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e(TAG, error.getMessage(), error);
						Message msg = new Message();
						msg.what = failId;
						handler.sendMessage(msg);
					}
				}) {
			@Override
			protected Map<String, String> getParams() {
				return requestMap;
			}
		};
		stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		requestQueue.add(stringRequest);
	}
	
	public static void request(Context context, final Handler handler,
			final Map<String, String> requestMap, final int sucId,
			final int failId, String httpurl) {

		RequestQueue requestQueue = Volley.newRequestQueue(context);
		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				httpurl, new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.d(TAG, "网络返回数据 -> " + response);
						Message msg = new Message();
						msg.obj = response;
						msg.what = sucId;
						handler.sendMessage(msg);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("gf", error.getMessage(), error);
						Message msg = new Message();
						msg.what = failId;
						handler.sendMessage(msg);
					}
				}) {
			@Override
			protected Map<String, String> getParams() {
				return requestMap;
			}
		};
		stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		requestQueue.add(stringRequest);
	}

	/**
	 * 检查网络并弹出Toast提示
	 * 
	 * @return
	 */

	private static boolean checkNetwork(final Handler hand) {
		if (!AppContext.isNetworkConnected()) {
			if (hand != null)
				hand.obtainMessage(R.id.close_dialog).sendToTarget();
			return false;
		}
		return true;
	}

	/**
	 * 获取省数据 getProvinceList: <br/>
	 * 
	 * @return
	 * @throws Exception
	 * @since JDK 1.6
	 */
	public static void getProvinceList(final Handler mHandler,
			RequestQueue requestQueue) {
		if (!checkNetwork(mHandler))
			return;
		JsonObjectRequest jsonObjRequest;
		String url = jzgGuyeHttp + "/app/area/GetProv.ashx?&sign="
				+ SignUtils.signForPost();
		jsonObjRequest = new JsonObjectRequest(Request.Method.POST, url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							JsonObjectImpl jsonObjectImpl = new JsonObjectImpl();
							ProvinceList provinceList = jsonObjectImpl
									.parserProvinceList(response.toString());
							MessageUtils.sendMessage(mHandler,
									R.id.provinceList, provinceList);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if (error instanceof NetworkError) {
							System.out.println(error.toString());
						} else if (error instanceof ServerError) {
							System.out.println(error.toString());
						} else if (error instanceof AuthFailureError) {
							System.out.println(error.toString());
						} else if (error instanceof ParseError) {
							System.out.println(error.toString());
						} else if (error instanceof NoConnectionError) {
							System.out.println(error.toString());
						} else if (error instanceof TimeoutError) {
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
	 * 获取市数据 getCityList: <br/>
	 * 
	 * @author wang
	 * @param provinceid
	 * @return
	 * @throws Exception
	 * @since JDK 1.6
	 */
	public static void getCityList(final Handler mHandler,
			RequestQueue requestQueue, int provinceid, boolean isHideAll) {
		if (!checkNetwork(mHandler))
			return;
		JsonObjectRequest jsonObjRequest;
		String url = "";
		if (isHideAll) {
			// 直辖市
			url = jzgGuyeHttp + "/app/area/GetCityByProvId.ashx";
		} else {
			url = jzgGuyeHttp + "/app/area/GetAreaList.ashx";
		}
		Uri.Builder builder = Uri.parse(url).buildUpon();
		builder.appendQueryParameter("ProvId", String.valueOf(provinceid));
		builder.appendQueryParameter("sign",
				SignUtils.signForCityList(String.valueOf(provinceid)));
		jsonObjRequest = new JsonObjectRequest(Request.Method.POST,
				builder.toString(), null, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							JsonObjectImpl jsonObjectImpl = new JsonObjectImpl();
							CityList cityList = jsonObjectImpl
									.parserCityList(response.toString());
							MessageUtils.sendMessage(mHandler, R.id.cityList,
									cityList);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if (error instanceof NetworkError) {
							System.out.println(error.toString());
						} else if (error instanceof ServerError) {
							System.out.println(error.toString());
						} else if (error instanceof AuthFailureError) {
							System.out.println(error.toString());
						} else if (error instanceof ParseError) {
							System.out.println(error.toString());
						} else if (error instanceof NoConnectionError) {
							System.out.println(error.toString());
						} else if (error instanceof TimeoutError) {
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
}
