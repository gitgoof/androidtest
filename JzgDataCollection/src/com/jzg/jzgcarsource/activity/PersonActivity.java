package com.jzg.jzgcarsource.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jzg.jzgcarsource.activity.adjust.PersonMsgModel;
import com.jzg.jzgcarsource.application.AppContext;
import com.jzg.jzgcarsource.utils.HttpServiceHelper;
import com.jzg.jzgcarsource.utils.JsonObjectImpl;
import com.jzg.jzgcarsource.utils.MD5Utils;

/**
 * @Description: 个人界面
 * @Package com.jzg.jzgcarsource.activity PersonActivity.java
 * @author gf
 * @date 2016-11-1 上午8:58:28
 */
public class PersonActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person);
		
		initWidget();
	}
	private ImageView mImgIcon;
	private TextView mTvName;
	private TextView mTvAddress;
	private TextView mTvMail;
	
	private TextView mTvTodayOver;
	private TextView mTvAllOver;
	private TextView mTvAllAdjust;
	private TextView mTvTodayAdjust;
	private TextView mTvAllSubmit;
	
	private LinearLayout mLinearCheckBrand;
	private TextView mTvVersion;
	private Button mBtnExit;
	private void initWidget(){
//		ImageLoader.getInstance().displayImage(R.drawable.icon, mImgIcon);
		
		mImgIcon = (ImageView) findViewById(R.id.img_person_icon);
		mTvName = (TextView) findViewById(R.id.tv_person_name);
		mTvAddress = (TextView) findViewById(R.id.tv_person_address);
		mTvMail = (TextView) findViewById(R.id.tv_person_mail);
		
		mTvTodayOver = (TextView) findViewById(R.id.tv_person_content_today_over);
		mTvAllOver = (TextView) findViewById(R.id.tv_person_content_all_over);
		mTvAllAdjust = (TextView) findViewById(R.id.tv_person_content_adjust_all);
		mTvTodayAdjust = (TextView) findViewById(R.id.tv_person_content_adjust_today);
		mTvAllSubmit = (TextView) findViewById(R.id.tv_person_content_submit_all);
		
		mLinearCheckBrand = (LinearLayout) findViewById(R.id.linear_person_content_brankcheck);
		mTvVersion = (TextView) findViewById(R.id.tv_person_content_version);
		mBtnExit = (Button) findViewById(R.id.btn_person_content_exit);
		initListener();
	}
	private void initListener(){
		mLinearCheckBrand.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 设置擅长品牌
				if(null == mPersonMsgModel){
					showMsgToast("个人信息返回失败!");
					return;
				}
				Intent intent = new Intent(PersonActivity.this,CheckBrandActivity.class);
				intent.putExtra("checkedbrand", mPersonMsgModel.getAdeptMake());
				getParent().startActivityForResult(intent, REQUEST_CHECK_BRANK);
			}
		});
		mBtnExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				userInfo.edit().clear().commit();
				PersonActivity.this.finish();
				getParent().finish();
			}
		});
		initDataShow();
		try {
			final String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
			mTvVersion.setText("V" + versionName);
		} catch (NameNotFoundException e) {
			mTvVersion.setText("V1.0.1");
		}
		mInitPerson = true;
//		toRequestPriceMsg();
	}
	public void initPerson(){
		mInitPerson = true;
	}
	private boolean mInitPerson = false;
	private void toRequestPriceMsg(){
		toShowLoadingDialog();
		HttpServiceHelper.newToRequest(PersonActivity.this, mHandler, getParamsForPerson(),
				REQUEST_PERSON_MSG_SUC, REQUEST_PERSON_MSG_FAIL);
	}
	private void toChangeBrand(String brands){
		toShowLoadingDialog();
		HttpServiceHelper.newToRequest(PersonActivity.this, mHandler, getBrandForPerson(brands),
				REQUEST_PERSON_BRAND_SUC, REQUEST_PERSON_BRAND_FAIL);
	}
	private Map<String, String> getParamsForPerson() {

		// 在这里设置需要post的参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("op", "Statistics");
		if(null != AppContext.user)
		map.put("Userid", AppContext.user.getId());
		
		Map<String, Object> mapp = new HashMap<String, Object>();
		mapp.put("op", "Statistics");
		if(null != AppContext.user)
		mapp.put("Userid", AppContext.user.getId());
		String sign = MD5Utils.getMD5Sign(mapp);
		map.put("sign", sign);
		return map;
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
	private final int REQUEST_PERSON_MSG_SUC = 0x3001;
	private final int REQUEST_PERSON_MSG_FAIL = 0x3002;
	private final int REQUEST_PERSON_BRAND_SUC = 0x3003;
	private final int REQUEST_PERSON_BRAND_FAIL = 0x3004;
	private PersonMsgModel mPersonMsgModel;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			dismissLoadingDialog();
			switch (msg.what) {
			case REQUEST_PERSON_MSG_SUC:
				mInitPerson = false;
				String sucMsg = (String) msg.obj;
				if (JsonObjectImpl.isSuccess(PersonActivity.this, sucMsg)) {
					mPersonMsgModel = getPerMsg(sucMsg);
					initDataShow();
				}
				break;
			case REQUEST_PERSON_BRAND_FAIL:
			case REQUEST_PERSON_MSG_FAIL:
				showMsgToast("登录失败，网络请求错误");
				break;
			case REQUEST_PERSON_BRAND_SUC:
				String brand = (String) msg.obj;
				if (JsonObjectImpl.isSuccess(PersonActivity.this, brand)) {
					
				} else {
					
				}
				break;
			default:
				break;
			}
		}

	};
	private PersonMsgModel getPerMsg(String msg){
		if(TextUtils.isEmpty(msg))return null;
		Gson gson = new Gson();
		return gson.fromJson(msg, PersonMsgModel.class);
	}
	private void initDataShow(){
		if(null != AppContext.user){
			mTvName.setText(AppContext.user.getUserName());
			mTvMail.setText(AppContext.user.getEmail());
			mTvAddress.setText(AppContext.user.getProvinceName() + "-" + AppContext.user.getCityName());
		} else {
			mTvName.setText("--");
			mTvMail.setText("--");
			mTvAddress.setText("--");
		}
		if(null == mPersonMsgModel){
			mTvAddress.setText("--");
			mTvTodayOver.setText("--条");
			mTvAllOver.setText("-/-条  第一期");
			mTvAllAdjust.setText("--条");
			mTvTodayAdjust.setText("--条");
			mTvAllSubmit.setText("--条");
			return;
		}
		mTvTodayOver.setText(mPersonMsgModel.getCompleteCountByDay() + "条");
		mTvAllOver.setText(mPersonMsgModel.getCompleteCountByUserIdAndTimes()+
				"/" + mPersonMsgModel.getCountByUserIdAndTimes() + "  第" + mPersonMsgModel.getDataTimes() + "期");
		mTvAllAdjust.setText(mPersonMsgModel.getCounts() + "条");
		mTvTodayAdjust.setText(mPersonMsgModel.getSubmitCountByDay() + "条");
		mTvAllSubmit.setText(mPersonMsgModel.getSubmitDataByUser() + "条");
	}
	private final int REQUEST_CHECK_BRANK = 0x1000;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case REQUEST_CHECK_BRANK:
			if(resultCode == 100){
				final int[] checkeds = data.getIntArrayExtra("checkedbrand");
				StringBuffer sb = new StringBuffer();
				for(int i:checkeds){
					if(i!=-1){
						if(sb.length() !=0 ){
							sb.append(",");
						}
						sb.append(String.valueOf(i));
					}
				}
				mPersonMsgModel.setAdeptMake(sb.toString());
				if(sb.length() == 0)break;
//				toChangeBrand(sb.toString());
			}
			break;
		default:
			break;
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		if(mInitPerson){
			toRequestPriceMsg();
		}
	}
}
