/**
 * Project Name:JZGPingGuShiWangLuo
 * File Name:RegionActivity.java
 * Package Name:com.gc.jzgpgswl.ui
 * Date:2014-11-21下午8:29:11
 * Copyright (c) 2014, wangyd523@gmail.com All Rights Reserved.
 *
 */

package com.jzg.jzgcarsource.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;
import android.widget.Toast;

import com.jzg.jzgcarsource.adapter.CustomArrayAdapter;
import com.jzg.jzgcarsource.application.AppContext;
import com.jzg.jzgcarsource.bean.City;
import com.jzg.jzgcarsource.bean.CityList;
import com.jzg.jzgcarsource.bean.CustomData;
import com.jzg.jzgcarsource.bean.Province;
import com.jzg.jzgcarsource.bean.ProvinceList;
import com.jzg.jzgcarsource.bean.Simple2Adapter;
import com.jzg.jzgcarsource.bean.SimpleList;
import com.jzg.jzgcarsource.utils.HttpServiceHelper;
import com.jzg.jzgcarsource.view.HorizontalListView;
import com.jzg.pricechange.phone.JzgCarChooseCustomArrayAdapter.OnImgClickListener;

/**
 * ClassName:RegionActivity <br/>
 * Function: TODO 地区选择. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014-11-21 下午8:29:11 <br/>
 * 
 * @author 汪渝栋
 * @version
 * @since JDK 1.6
 * @see
 */
@SuppressWarnings("deprecation")
public class RegionActivity extends BaseActivity implements
		OnItemClickListener, OnDrawerOpenListener, OnDrawerCloseListener,
		OnClickListener, OnImgClickListener
{

	private static final int UPDATE_COMPANY_RESULT = 4;// 公司地址修改

	private static final String IS_PROVINCE_FLAG = "all";

	/**
	 * 从发车选择地区界面跳转到本界面的标示符(根据这个标示符做数据操作)
	 */
	private String carreleaseRegionFlag;

	/**
	 * 从筛选设置界面选择地区跳转到本界面的标示符(根据这个标示符做数据操作)
	 */
	private String carFilterRegionFlag;

	/**
	 * 界面Handler控制
	 */
	private Handler mHandler;

	/**
	 * 省列表是否被点击
	 */
	private boolean isProvinceClick = false;

	/**
	 * 省份
	 */
	private String mProvince;

	/**
	 * 市
	 */
	private String mCity;

	/**
	 * 颜色
	 */
	private String mColor;

	/**
	 * 省列表被点击的位置
	 */
	private int mProvinceContentListClickPostion = -1;

	/**
	 * 上次选择位置
	 */
	private int oldPoc;

	/**
	 * 省结果集
	 */
	private ArrayList<Province> provinces;

	/**
	 * 市结果集
	 */
	private ArrayList<City> cities;

	/**
	 * 省结果集带字体颜色、字体内容及位置的适配器缓存列表
	 */
	private List<SimpleList> list;

	/**
	 * 市结果集带字体颜色、字体内容及位置的适配器缓存列表
	 */
	private List<SimpleList> cityslist;

	/**
	 * 筛选过程中选择的市选项
	 */
	private List<City> filterCitys;

	/**
	 * 筛选过程中选择的省选项
	 */
	private List<Province> filterProvinces;

	private HorizontalListView mHlvCustomListWithDividerAndFadingEdge;
	private CustomArrayAdapter adapter = null;
	private static List<CustomData> mCustomData = new ArrayList<CustomData>();

	private boolean isFirstAdapter;

	private LinearLayout chooseLayout;

	private Simple2Adapter simple2Adpter;
	private ListView mProvinceContent;
	private SlidingDrawer mCarDetailInfoCityDrawer;
	private ImageView mCarDetailInfoCityHandle;
	private ListView mCarDetailInfoCityContent;

	/**
	 * 市适配器
	 */
	private Simple2Adapter cityAdapter;

	/**
	 * 市列表显示类容
	 */
	private List<String> cityList;

	private ImageButton mReturnBtn;

	private Button mSureBtn;

	private int mCityId;

	private int mProvinceId;

	/**
	 * 影藏全部地区标记(不为空表示隐藏全部地区)
	 */
	private String hideAllRegionFlag;
	
	//限迁标准查询
	private boolean isFromTools = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.region_layout);
		mHandler = getHandler();
		Intent in = getIntent();
		carreleaseRegionFlag = in.getStringExtra("carrelease_region");

		hideAllRegionFlag = in.getStringExtra("hideAllRegion");

		carFilterRegionFlag = in.getStringExtra("carfilter_region");
		isFromTools = in.getBooleanExtra("isFromTools", false);
		if (in.getBooleanExtra("flag", false))
		{
//			((AppContext) getApplicationContext()).getUser().setCityName(mCity);
		}

		init();

		startProvinceThead();

	}

	@Override
	public void init()
	{
		super.init();
		filterCitys = new ArrayList<City>();
		filterCitys.clear();
		filterProvinces = new ArrayList<Province>();
		filterProvinces.clear();
		mCustomData.clear();
		mHlvCustomListWithDividerAndFadingEdge = (HorizontalListView) findViewById(R.id.hlvCustomListWithDividerAndFadingEdge);
		isFirstAdapter = true;

		mProvinceContent = (ListView) findViewById(R.id.province_content);
		mProvinceContent.setOnItemClickListener(this);

		// 初始化汽车市
		mCarDetailInfoCityDrawer = (SlidingDrawer) this
				.findViewById(R.id.car_detail_info_city_drawer);
		mCarDetailInfoCityHandle = (ImageView) this
				.findViewById(R.id.car_detail_info_city_handle);
		mCarDetailInfoCityContent = (ListView) this
				.findViewById(R.id.car_detail_info_city_content);
		mCarDetailInfoCityContent.setOnItemClickListener(this);
		mCarDetailInfoCityDrawer.setOnDrawerOpenListener(this);
		mCarDetailInfoCityDrawer.setOnDrawerCloseListener(this);

		mReturnBtn = (ImageButton) findViewById(R.id.return_btn);
		mReturnBtn.setOnClickListener(this);

		mSureBtn = (Button) findViewById(R.id.sure_btn);
		mSureBtn.setOnClickListener(this);
//		if ("carfilter_region".equals(carFilterRegionFlag))
//		{
//			mSureBtn.setVisibility(View.VISIBLE);
//		} else
//		{
//			mSureBtn.setVisibility(View.GONE);
//		}

		chooseLayout = (LinearLayout) findViewById(R.id.chooseLayout);
	}

	private Handler getHandler()
	{
		return new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				int id = msg.what;
				switch (id) {
				case R.id.provinceList:
					assemblyProvinceList(msg);
					break;
				case R.id.cityList:
					assemblyCityList(msg);
					break;
				case R.id.net_error:
					showMsgToast(getResources().getString(R.string.net_error));
				case R.id.modifyCity:

					Intent it = new Intent();
					it.putExtra("province", mProvince);
					it.putExtra("city", mCity);
					it.putExtra("mCityId", mCityId);
					it.putExtra("mProvinceId", mProvinceId);
//					((AppContext) getApplicationContext()).getUser().setCityName(mCity);
					// if ("南宁".equals(mCity)
					// & NanNingCompanyListActivity.getmCacheCompanyList()
					// .size() != 0)
					// {
					// startActivity(new Intent(RegionActivity.this,
					// NanNingCompanyListActivity.class));
					// }
					setResult(Activity.RESULT_OK, it);
					finish();
					break;
				case R.id.close_dialog:
					if(mDialog != null && mDialog.isShowing())mDialog.dismiss();
					showMsgToast(getStringById(R.string.common_no_network_msg));
					break;
				default:
					break;
				}
			}

			private void assemblyCityList(Message msg)
			{

				// provinces = provinceList.getProvinces();
				// list = new ArrayList<SimpleList>();
				// for (int i = 1; i <= provinces.size(); i++)
				// {
				// int color = getResources().getColor(
				// android.R.color.black);
				// String name = provinces.get(i - 1).getName();
				// SimpleList simpleList = new SimpleList(i, color, name);
				// list.add(simpleList);
				// }
				// simple2Adpter = new Simple2Adapter(getApplicationContext(),
				// list);
				// mProvinceContent.setAdapter(simple2Adpter);

				CityList citylist = (CityList) msg.obj;
				if (citylist.getStatus() == SUCCESS)
				{
					if (!TextUtils.isEmpty(hideAllRegionFlag)&!isFromTools)
					{
							cities = citylist.getCitys();
							cities.remove(0);
					} else
					{
							cities = citylist.getCitys();
					}

					cityslist = new ArrayList<SimpleList>();
					for (int i = 1; i <= cities.size(); i++)
					{
						int color = getResources().getColor(
								android.R.color.black);
						String name = cities.get(i - 1).getName();
						SimpleList simpleList = new SimpleList(i, color, name);
						cityslist.add(simpleList);
					}
					cityAdapter = new Simple2Adapter(getApplicationContext(),
							cityslist);
					mCarDetailInfoCityContent.setAdapter(cityAdapter);
					// cityList = new ArrayList<String>();
					// for (City city : cities)
					// {
					// cityList.add(city.getName());
					// }
				} else
				{
					showMsgToast("亲，请检查网络是否连接正常，没有查询到城市数据！！");
				}
			}

			private void assemblyProvinceList(Message msg)
			{
				ProvinceList provinceList = (ProvinceList) msg.obj;
				if (checkProvinceList(provinceList))
				{
					provinces = provinceList.getProvinces();
					list = new ArrayList<SimpleList>();
					for (int i = 1; i <= provinces.size(); i++)
					{
						int color = getResources().getColor(
								android.R.color.black);
						String name = provinces.get(i - 1).getName();
						SimpleList simpleList = new SimpleList(i, color, name);
						list.add(simpleList);
					}
					simple2Adpter = new Simple2Adapter(getApplicationContext(),
							list);
					mProvinceContent.setAdapter(simple2Adpter);
				} else
				{
					showMsgToast("省列表无返回数据，请检查网络是否链接！！！");
				}
			}
		};
	}

	/**
	 * 获取省列表信息 startProvinceThead: <br/>
	 * 
	 * @author wang
	 * @since JDK 1.6
	 */
	private void startProvinceThead()
	{
		HttpServiceHelper.getProvinceList(mHandler, AppContext.getRequestQueue());
	}

	private void startCityThread()
	{
		Province province = provinces.get(mProvinceContentListClickPostion);
//		// 当标记比较相同的时候走发车的业务逻辑
//		if ("carrelease_region".equals(carreleaseRegionFlag))
//		{
//			CarRelease carRelease = appContext.getCarRelease();
//			carRelease.setProvince(province.getName());
//			carRelease.setProvinceId(province.getId());
//			HttpService.getCityList(mHandler, AppContext.getRequestQueue(),
//					province.getId(), isFromTools);
//		}
//		// 注册、个人中心的流程
//		else
//		{
			HttpServiceHelper.getCityList(mHandler, AppContext.getRequestQueue(),
					province.getId(), isFromTools);
//		}
	}

	protected boolean checkProvinceList(ProvinceList provinceList)
	{
		if (provinceList.getStatus() != SUCCESS)
		{
			return false;
		}
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id)
	{
		int viewid = parent.getId();
		switch (viewid) {
		case R.id.province_content:
			modifyTextColor(view, position);
			prepareCityList();
			startCityThread();
			break;
		case R.id.car_detail_info_city_content:
			returnRegion(position);
			break;
		default:
			break;
		}
	}

	private void returnRegion(int position)
	{

		// 当标记比较相同的时候走发车的业务逻辑
		if ("carrelease_region".equals(carreleaseRegionFlag))
		{
//			City city = cities.get(position);
//			CarRelease carRelease = appContext.getCarRelease();
//			carRelease.setCity(city.getName());
//			carRelease.setCityId(city.getId());
//			setResult(1);
//			finish();
		}
		// 当标记比较相同的时候走筛选设置模块的选择城市业务
		else if ("carfilter_region".equals(carFilterRegionFlag))
		{
			//车源定制选择城市
			City city = cities.get(position);
			Province province = provinces.get(mProvinceContentListClickPostion);
			Intent intent = new Intent();
			String regionStr = city.getName();
			String regionId = String.valueOf(city.getId());
			intent.putExtra("regionStr", regionStr);
			intent.putExtra("regionId",regionId);
			intent.putExtra("regionProvince", province.getName());
			intent.putExtra("regionProvinceId", String.valueOf(province.getId()));
			setResult(Activity.RESULT_OK, intent);
			finish();
			//    carFilterRegion(position);   筛选
		}
		// 注册和个人中心的逻辑
		else
		{
			mCity = cityslist.get(position).getName();
			returnCityInfo(position);
			
//			if(isFromTools){
//				returnCityInfo(position);
//			}else{
//				startSubmitCityDataThread(position);
//			}
		}

	}
	
	/**
	 * 限迁标准查询
	 * @param position
	 */
	private void returnCityInfo(final int position)
	{
		//城市Id
			mCityId = cities.get(position).getId();
			//省Id
			mProvinceId = provinces.get(
					mProvinceContentListClickPostion).getId();
			//城市名字
			mCity = cities.get(position).getName();
			mProvince = provinces.get(mProvinceContentListClickPostion).getName();
			Intent it = new Intent();
			it.putExtra("province", mProvince);
			it.putExtra("city", mCity);
			it.putExtra("mCityId", mCityId);
			it.putExtra("mProvinceId", mProvinceId);
			setResult(Activity.RESULT_OK, it);
			finish();
					
	}

	
	
	

	/**
	 * 筛选界面地区选择
	 * 
	 * @param position
	 */
	private void carFilterRegion(int position)
	{

		// 判断当前结果列表的个数是否等于3，如果等于直接返回
		if (mCustomData.size() == 3)
		{
			Toast.makeText(this, "地区选项已达上限", 2000).show();
		}
		// 判断点击位置是否为0，为0则表示是选择的所有地区
		else if (position == 0)
		{
			Province province = provinces.get(mProvinceContentListClickPostion);
			System.out.println("province is " + province);
			// 1、判断当前选择项的省选项是否已经被选择了，如果被选择则给予提示
			if (!hasProvince(province, "已经存在当前省份"))
			{
				// 2、选择所有地区后需要对列表当中当前点击省的已选城市进行清空
				clearCity(province);

				filterProvinces.add(province);

				mCustomData.add(new CustomData(Color.WHITE, province.getName(),
						String.valueOf(province.getId()), IS_PROVINCE_FLAG));

				// 3、显示省名字在列表中
				showData();
			}

		}
		// 选择城市列表的其他位置
		else
		{
			Province province = provinces.get(mProvinceContentListClickPostion);
			System.out.println("province is " + province);
			// 1、判断当前选择项的省选项是否已经被选择了，如果被选择则给予提示
			if (!hasProvince(province, "当前选择的全部地区已被选择，请不要重复选择！"))
			{
				City city = cities.get(position);
				// 2、判断当前选择项的市选项是否已经被选择了，如果被选择则给予提示
				if (!hasCity(city))
				{
					// 3、显示内容
					System.out.println("包含");
					city.setProvinceId(String.valueOf(province.getId()));
					filterCitys.add(city);

					mCustomData.add(new CustomData(Color.WHITE, city.getName(),
							String.valueOf(province.getId()), String
									.valueOf(city.getId())));
					showData();
				}
			}
		}

	}

	/**
	 * 
	 * @param city
	 * @return
	 */
	private boolean hasCity(City city)
	{
		if (filterCitys.size() > 0)
			for (City c : filterCitys)
			{
				if (c.getId() == city.getId())
				{
					Toast.makeText(getApplicationContext(), "当前市已经选择，请不要重复选择！",
							2000).show();
					return true;
				}
			}
		return false;
	}

	/**
	 * 显示数据在列表中
	 */
	private void showData()
	{
		chooseLayout.setVisibility(View.VISIBLE);
		if (isFirstAdapter)
		{
			// 继续组装list的数据
			isFirstAdapter = false;
//			adapter = new CustomArrayAdapter(this, mCustomData, this);
			mHlvCustomListWithDividerAndFadingEdge.setAdapter(adapter);
		} else
		{
			if (adapter != null)
				adapter.notifyDataSetChanged();
		}
	}

	/**
	 * 选择所有地区后需要对列表当中当前点击省的已选城市进行清空
	 * 
	 * @param province
	 */
	private void clearCity(Province province)
	{
		List<City> delCity = new ArrayList<City>();
		if (filterCitys.size() > 0)
		{
			for (City city : filterCitys)
			{
				if (!TextUtils.isEmpty(city.getProvinceId())
						&& city.getProvinceId().equals(
								String.valueOf(province.getId())))
					delCity.add(city);
			}
			filterCitys.removeAll(delCity);
		}

		if (mCustomData.size() > 0)
		{
			List<CustomData> delData = new ArrayList<CustomData>();
			for (CustomData data : mCustomData)
			{
				if (!TextUtils.isEmpty(data.getmProvinceId())
						&& data.getmProvinceId().equals(
								String.valueOf(province.getId())))
				{
					delData.add(data);
				}
			}
			mCustomData.removeAll(delData);
			delData.clear();
		}
	}

	/**
	 * 判断当前选择项的省选项是否已经被选择了，如果被选择则给予提示
	 * 
	 * @param province
	 * @return
	 */
	private boolean hasProvince(Province province, String alertText)
	{
		if (filterProvinces.size() > 0)
		{
			for (Province p : filterProvinces)
			{
				if (String.valueOf(province.getId()).equals(
						String.valueOf(p.getId())))
				{
					Toast.makeText(getApplicationContext(), alertText, 2000)
							.show();
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 在列表顶部显示当前选中的地区项
	 * 
	 * @param regions2
	 */
	// private void showRegions(List<City> regions)
	// {
	// if (regions.size() > 0)
	// regionLayout.setVisibility(View.VISIBLE);
	//
	// if (regions.size() == 1)
	// {
	// regionOne.setText(regions.get(0).getName());
	// regionOne.setTag(regions.get(0));
	// regionOneLayout.setVisibility(View.VISIBLE);
	// } else if (regions.size() == 2)
	// {
	// regionTwo.setText(regions.get(1).getName());
	// regionTwo.setTag(regions.get(1));
	// regionTwoLayout.setVisibility(View.VISIBLE);
	// } else if (regions.size() == 3)
	// {
	// regionThree.setText(regions.get(2).getName());
	// regionThree.setTag(regions.get(2));
	// regionThreeLayout.setVisibility(View.VISIBLE);
	// } else
	// {
	// Toast.makeText(this, "regions.size() is error!!!", 2000).show();
	// }
	// }

	private void startSubmitCityDataThread(final int position)
	{
		new Thread(new Runnable()
		{

			@Override
			public void run()
			{
//				// 调用修改城市接口
//				User user = appContext.getUser();
//				try
//				{
//					SharedPreferences userInfo = getSharedPreferences(
//							"user_info", 0);
//
//					mCityId = cities.get(position).getId();
//					// TODO 和个人中心走分支流程，不调用线程使用标记进行界面结果返回 修改
//					// user.setCityId(mCityId);
//					user.setCityId(mCityId);
//					mProvinceId = provinces.get(
//							mProvinceContentListClickPostion).getId();
//					User resultUser = HttpService.updateCity(userInfo
//							.getString("name", ""), cities.get(position)
//							.getId(),
//							provinces.get(mProvinceContentListClickPostion)
//									.getId());
//					if (resultUser.getStatus() == SUCCESS)
//					{
//						MessageUtils.sendMessage(mHandler, R.id.modifyCity,
//								resultUser);
//					} else
//					{
//						MessageUtils
//								.sendMessage(mHandler, R.id.net_error, null);
//					}
//				} catch (Exception e)
//				{
//					e.printStackTrace();
//				}
			}
		}).start();
	}

	private void prepareCityList()
	{
		// 如果城市选择列表没有开启则打开列表
		if (!mCarDetailInfoCityDrawer.isOpened())
			mCarDetailInfoCityDrawer.animateOpen();
		// 清空市列表数据原始数据
		if (cityList != null)
		{
			cityList.clear();
			cityAdapter.notifyDataSetChanged();
		}
	}

	private void modifyTextColor(View view, int position)
	{
		isProvinceClick = true;
		mProvinceContentListClickPostion = position;

		TextView name = (TextView) view
				.findViewById(R.id.car_list_content_name);
		// 赋值当前选择省份
		mProvince = name.getText().toString();
		// 修改当前选中项字体颜色
		list.get(mProvinceContentListClickPostion).setColor(
				getResources().getColor(R.color.list_click));
		if (oldPoc != -1 && oldPoc != position)
		{
			list.get(oldPoc).setColor(
					getResources().getColor(R.color.black_font));
		}
		simple2Adpter.notifyDataSetChanged();
		oldPoc = mProvinceContentListClickPostion;
	}

	@Override
	public void onDrawerClosed()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void onDrawerOpened()
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void onClick(View v)
	{
		int id = v.getId();
		switch (id) {
		case R.id.return_btn:
			finish();
			break;
		case R.id.sure_btn:
			if (mCustomData.size() > 0)
			{
				Intent intent = new Intent();
				StringBuffer regionStr = new StringBuffer();
				StringBuffer regionId = new StringBuffer();

				for (CustomData customData : mCustomData)
				{
					regionStr.append(customData.getText() + " ");
					regionId.append(customData.getmProvinceId() + "|"
							+ customData.getmCityId() + ",");
				}

				intent.putExtra("regionStr", regionStr.toString());
				intent.putExtra("regionId",
						regionId.substring(0, regionId.length() - 1));
				// System.out.println("regionStr is " + regionStr
				// + ",regionId is " + regionId);
				setResult(Activity.RESULT_OK, intent);
				finish();
			} else
			{
				Toast.makeText(this, "请选择地区项", 2000).show();
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		// if("南宁".equals(mCity)){
		// startActivity(new Intent(this,
		// NanNingCompanyListActivity.class));
		// }

	}

	@Override
	public void onImgClick(int position)
	{
		CustomData customData = mCustomData.get(position);
		ArrayList<City> removeCity = new ArrayList<City>();
		ArrayList<Province> removeProvince = new ArrayList<Province>();

		// 1、如果删除的位置是省则在省列表中删除对应的数据
		if (IS_PROVINCE_FLAG.equals(customData.getmCityId()))
		{
			for (Province province : filterProvinces)
			{
				if (customData.getmProvinceId().equals(
						String.valueOf(province.getId())))
				{
					removeProvince.add(province);
					// filterProvinces.remove(province);
				}
			}
			filterProvinces.removeAll(removeProvince);
		} else
		{
			// 2、如果删除的位置是市则在市列表中删除对应的数据
			for (City city : filterCitys)
			{
				if (customData.getmCityId()
						.equals(String.valueOf(city.getId())))
				{
					removeCity.add(city);
					// filterCitys.remove(city);
				}
			}
			filterCitys.removeAll(removeCity);
		}
		// 3、删除滑动列表点击位置显示的数据
		mCustomData.remove(position);

		adapter.notifyDataSetChanged();
		if (mCustomData.size() == 0)
		{
			chooseLayout.setVisibility(View.GONE);
		}
		// System.out.println("regions result is " + regions.toString());
	}
}
