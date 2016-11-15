package com.jzg.jzgcarsource.activity;

import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jzg.jzgcarsource.application.AppContext;
import com.jzg.jzgcarsource.utils.HttpServiceHelper;
import com.jzg.jzgcarsource.utils.JsonObjectImpl;
import com.jzg.jzgcarsource.utils.MD5Utils;
import com.jzg.pricechange.phone.JzgCarChooseChineseUtil;
import com.jzg.pricechange.phone.JzgCarChooseConstant;
import com.jzg.pricechange.phone.JzgCarChooseHttpService;
import com.jzg.pricechange.phone.JzgCarChooseMake;
import com.jzg.pricechange.phone.JzgCarChooseMakeList;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * @Description: 设置擅长的品牌
 * @Package com.jzg.jzgcarsource.activity CheckBrankActivity.java
 * @author gf
 * @date 2016-11-2 下午2:07:19
 */
public class CheckBrandActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_brand);
		JzgCarChooseHttpService.setHttpurl(CarReleaseIndexActivity.JZG_CarChoose_URL);
		initWidget();
	}
	private ImageView mImgBack;
	private TextView mTvOk;
	private ListView mListBrands;
	private void initWidget(){
		mImgBack = (ImageView) findViewById(R.id.img_check_brand_title_back);
		mTvOk = (TextView) findViewById(R.id.tv_title_right_ok);
		
		mListBrands = (ListView) findViewById(R.id.list_check_brand_datas);
		initListener();
	}
	private void initListener(){
		mImgBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 返回
				CheckBrandActivity.this.finish();
			}
		});
		mTvOk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final int[] checked = getCheckd();
				/*
				Log.i("gf", "<>" + checked.length + "<>" + checked[0] + "<>" + checked[1]);
				Intent intent = getIntent();
				intent.putExtra("checkedbrand", checked);
				setResult(100, intent);
				CheckBrandActivity.this.finish();
				*/
				StringBuffer sb = new StringBuffer();
				for(int i:checked){
					if(i!=-1){
						if(sb.length() !=0 ){
							sb.append(",");
						}
						sb.append(String.valueOf(i));
					}
				}
				if(sb.length() == 0){
					Toast.makeText(CheckBrandActivity.this, "请选择品牌!", Toast.LENGTH_SHORT).show();
					return;
				}
				toChangeBrand(sb.toString());
			}
		});
		startMakeListThread("0");
		mListBrands.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				final boolean bl = (boolean) mBrandItems.get(position).get("checked");
				if(bl){
					mBrandItems.get(position).put("checked", false);
					mIndexCarListAdapter.notifyDataSetChanged();
					return;
				}
				
				mBrandItems.get(position).put("checked", true);
				mIndexCarListAdapter.notifyDataSetChanged();
			}
		});
		final String checkedBrand = getIntent().getStringExtra("checkedbrand");
		mCheckedStrs = checkedBrand.split(",");
	}
	private String[] mCheckedStrs;
	private int getChecked(){
		int checked = 0;
		for(Map<String, Object> item:mBrandItems){
			final boolean bl = (boolean) item.get("checked");
			if(bl)checked++;
		}
		return checked;
	}
	private int[] getCheckd(){
//		final int[] checkeds = new int[]{-1,-1};
//		int checked = 0;
		final List<Integer> check = new ArrayList<Integer>();
		for(Map<String, Object> item:mBrandItems){
			final boolean bl = (boolean) item.get("checked");
			if(bl){
//				checkeds[checked] = (int) item.get("makeid");
//				checked++;
				check.add((int) item.get("makeid"));
			}
		}
		final int[] checkeds = new int[check.size()];
		for(int i = 0;i < checkeds.length;i++){
			checkeds[i] = check.get(i);
		}
		return checkeds;
	}
	private void toChangeBrand(String brands){
		toShowLoadingDialog();
		HttpServiceHelper.newToRequest(CheckBrandActivity.this, mHandler, getBrandForPerson(brands),
				REQUEST_PERSON_BRAND_SUC, REQUEST_PERSON_BRAND_FAIL);
	}
	private Map<String, String> getBrandForPerson(String brands) {
		// 在这里设置需要post的参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("op", "UpdateAdeptMake");
		if(null != AppContext.user)
		map.put("userid", AppContext.user.getId());
		map.put("makes", brands);
		Map<String, Object> mapp = new HashMap<String, Object>();
		mapp.put("op", "UpdateAdeptMake");
		if(null != AppContext.user)
		mapp.put("userid", AppContext.user.getId());
		mapp.put("makes", brands);
		String sign = MD5Utils.getMD5Sign(mapp);
		map.put("sign", sign);
		return map;
	}
	private void startMakeListThread(final String InSale) {
		toShowLoadingDialog();
		JzgCarChooseHttpService.getMakeList(mHandler,
				AppContext.getRequestQueue(), JzgCarChooseConstant.makelist,InSale);
	}
	private ArrayList<JzgCarChooseMake> mBrandList;
	private final int REQUEST_PERSON_BRAND_SUC = 0x3003;
	private final int REQUEST_PERSON_BRAND_FAIL = 0x3004;
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			dismissLoadingDialog();
			int id = msg.what;
			switch (id) {
			case JzgCarChooseConstant.makelist:
				// 组装汽车品牌数据
				JzgCarChooseMakeList carMakeList = (JzgCarChooseMakeList)msg.obj;
				if(carMakeList.isSuccess()){
					mBrandList = carMakeList.getMakes();
					showMakeList(mBrandList);
				}
				break;
			case JzgCarChooseConstant.net_error_back:
				break;
			case JzgCarChooseConstant.carDetailSuc:
				break;
			case REQUEST_PERSON_BRAND_SUC:
				String brand = (String) msg.obj;
				if (JsonObjectImpl.isSuccess(CheckBrandActivity.this, brand)) {
					Toast.makeText(CheckBrandActivity.this, "设置成功!", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.putExtra("checkedbrand", getCheckd());
					setResult(100, intent);
					CheckBrandActivity.this.finish();
				} else {
//					Toast.makeText(CheckBrandActivity.this, "设置擅长品牌失败!", Toast.LENGTH_SHORT).show();
				}
				break;
			case REQUEST_PERSON_BRAND_FAIL:
				showMsgToast("登录失败，网络请求错误");
				break;
			default:
				break;
			}
		};
	};
	/**
	 * 汽车品牌排序集合
	 */
	private ArrayList<Map<String, Object>> mBrandItems;
	private boolean getIsChecked(String str){
		if(TextUtils.isEmpty(str))return false;
		if(null == mCheckedStrs)return false;
		if(mCheckedStrs.length == 0)return false;
		for(String s:mCheckedStrs){
			if(s.equals(str))return true;
		}
		return false;
	}
	protected void showMakeList(ArrayList<JzgCarChooseMake> makes) {
		setMakeColor();

		String contactSort = null;
		mBrandItems = new ArrayList<Map<String, Object>>();
		if (makes != null) {
			Map<String, Object> map = null;
			for (int i = 0; i < makes.size(); i++) {
				map = new HashMap<String, Object>();
				map.put("name", makes.get(i).getMakeName());
				map.put("fontColor", makes.get(i).getFontColor());
				map.put("logo", makes.get(i).getMakeLogo());
				contactSort = JzgCarChooseChineseUtil
						.getFullSpell(map.get("name").toString()).toUpperCase()
						.substring(0, 1);
				map.put("Sort", contactSort);
				map.put("makeid", makes.get(i).getMakeId());
				map.put("itemColor", makes.get(i).getItemColor());
				map.put("checked", getIsChecked(String.valueOf(makes.get(i).getMakeId()) ));
				mBrandItems.add(map);
			}
			Comparator comp = new Mycomparator();
			Collections.sort(mBrandItems, comp);
			
			mIndexCarListAdapter = new ListOneAdapter(getApplicationContext(), mBrandItems);
			
			mListBrands.setAdapter(mIndexCarListAdapter);
			
		}
	}
	public class Mycomparator implements Comparator {
		@Override
		public int compare(Object o1, Object o2) {
			Map<String, Object> c1 = (Map<String, Object>) o1;
			Map<String, Object> c2 = (Map<String, Object>) o2;
			Comparator cmp = Collator.getInstance(java.util.Locale.ENGLISH);
			return cmp.compare(c1.get("Sort"), c2.get("Sort"));
		}
	}
	private ListOneAdapter mIndexCarListAdapter;
	private void setMakeColor() {
		// 设置所有颜色为原色
		for (JzgCarChooseMake make : mBrandList) {
			make.setFontColor(Color.BLACK);
			make.setItemColor(Color.WHITE);
		}
	}
	class ListOneAdapter extends BaseAdapter {
		private ImageLoadingListener mAnimateFirstListener = new AnimateFirstDisplayListener();
		private LayoutInflater inflater;
		private List<Map<String, Object>> list;

		public ListOneAdapter(Context context, List<Map<String, Object>> list) {
			this.inflater = LayoutInflater.from(context);
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_checkbrand_ada, null);
				holder = new ViewHolder();
				holder.iamge = (ImageView) convertView
						.findViewById(R.id.car_image);
				holder.name = (TextView) convertView
						.findViewById(R.id.car_name);
				holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
				holder.imgChecked = (ImageView) convertView.findViewById(R.id.img_item_checkbrand_checked);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			convertView.setBackgroundColor((Integer) list.get(position).get(
					"itemColor"));
			holder.name.setText(list.get(position).get("name").toString());
			holder.name.setTextColor((Integer) list.get(position).get(
					"fontColor"));

			String imgUrl = (String) list.get(position).get("logo");
			// 品牌logo异步加载
			imageLoader.displayImage(imgUrl, holder.iamge, mOptions,
					mAnimateFirstListener);
			String currentStr = list.get(position).get("Sort").toString();
			String previewStr = (position - 1) >= 0 ? list.get(position - 1)
					.get("Sort").toString() : " ";
			if (!previewStr.equals(currentStr)) {
				holder.alpha.setVisibility(View.VISIBLE);
				holder.alpha.setText(currentStr);
			} else {
				holder.alpha.setVisibility(View.GONE);
			}
			final boolean checked = (boolean) list.get(position).get("checked");
			if(checked){
				holder.imgChecked.setVisibility(View.VISIBLE);
			} else {
				holder.imgChecked.setVisibility(View.GONE);
			}
			return convertView;
		}

		private class ViewHolder {
			ImageView iamge;
			TextView name;
			TextView alpha;
			ImageView imgChecked;
		}

	}
	private final MyListAdapter mMyListAdapter = new MyListAdapter();
	private class MyListAdapter extends BaseAdapter{
		private final List<ItemMode> list = new ArrayList<ItemMode>();
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return null;
		}
	}
	private class ItemMode implements Serializable{
		
	}
}
