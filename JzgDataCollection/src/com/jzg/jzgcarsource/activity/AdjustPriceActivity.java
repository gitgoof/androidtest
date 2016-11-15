package com.jzg.jzgcarsource.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jzg.jzgcarsource.activity.adjust.AdjustBeanModel;
import com.jzg.jzgcarsource.application.AppContext;
import com.jzg.jzgcarsource.utils.HttpServiceHelper;
import com.jzg.jzgcarsource.utils.JsonObjectImpl;
import com.jzg.jzgcarsource.utils.MD5Utils;

/**
 * @Description: 价格调整
 * @Package com.jzg.jzgcarsource.activity AdjustPriceActivity.java
 * @author gf
 * @date 2016-11-1 上午8:58:39
 */
public class AdjustPriceActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_adjust_price);
		
		initWidget();
	}
	private EditText mEditCarColor;
	private EditText mEditCarLevel;
	private RelativeLayout mRelativeCarColor;
	private RelativeLayout mRelativeCarLevel;
	private TextView mTvTitleMore;
	
	private TextView mTvTitlePerLeft;
	private TextView mTvTitlePerRight;
	private TextView mTvTitleTime;
	private EditText mTvAddress;
	private EditText mTvFullName;
	private EditText mTvTime;
	private EditText mTvMile;
	
	private EditText mEditShougou;
	private EditText mEditXiaoshou;
	
	private Button mBtnReset;
	private Button mBtnSubmit;
	
	private LinearLayout mLinearTopNum;
	private RelativeLayout mRelNullShow;
	private ScrollView mScrollMain;
	
	private TextView mTvRefreshAdjust;
	
	private void toShowIfNull(boolean bl){
		if(bl){
			mLinearTopNum.setVisibility(View.GONE);
			mRelNullShow.setVisibility(View.VISIBLE);
			mScrollMain.setVisibility(View.GONE);
			mTvTitleMore.setVisibility(View.GONE);
		} else {
			mLinearTopNum.setVisibility(View.VISIBLE);
			mRelNullShow.setVisibility(View.GONE);
			mScrollMain.setVisibility(View.VISIBLE);
			mTvTitleMore.setVisibility(View.VISIBLE);
		}
	}
	
	private void initWidget(){
		
		mLinearTopNum = (LinearLayout) findViewById(R.id.linear_adjust_price_top_show);
		mRelNullShow = (RelativeLayout) findViewById(R.id.rel_adjust_price_null_show);
		mScrollMain = (ScrollView) findViewById(R.id.scroll_adjust_price_main);
		
		mEditCarColor = (EditText) findViewById(R.id.valuation_colorcar_type_edit);
		mEditCarLevel = (EditText) findViewById(R.id.valuation_levelcar_type_edit);
		mRelativeCarColor = (RelativeLayout) findViewById(R.id.relative_adjust_price_carclor);
		mRelativeCarLevel = (RelativeLayout) findViewById(R.id.relative_adjust_price_carlevel);
		mTvTitleMore = (TextView) findViewById(R.id.btn_title_right_more);
		
		mTvTitlePerLeft = (TextView) findViewById(R.id.tv_adjust_price_title_perleft);
		mTvTitlePerRight = (TextView) findViewById(R.id.tv_adjust_price_title_perright);
		mTvTitleTime = (TextView) findViewById(R.id.tv_adjust_price_title_time);
		mTvAddress = (EditText) findViewById(R.id.tv_adjust_price_address);
		mTvFullName = (EditText) findViewById(R.id.tv_adjust_price_fullname);
		mTvTime = (EditText) findViewById(R.id.tv_adjust_price_time);
		mTvMile = (EditText) findViewById(R.id.tv_adjust_price_mile);
		mTvRefreshAdjust = (TextView) findViewById(R.id.tv_adjust_price_null_click);
		
		mEditShougou = (EditText) findViewById(R.id.valuation_jiage_edit);
		mEditXiaoshou = (EditText) findViewById(R.id.valuation_xiaoshoujiage_edit);
		
		mBtnReset = (Button) findViewById(R.id.btn_adjust_price_bottom_reset);
		mBtnSubmit = (Button) findViewById(R.id.btn_adjust_price_bottom_submit);
		initListener();
	}
	private void initListener(){
		mTvTitleMore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(tooFastClick())return;
				// TODO 跳过，刷新界面
				if(null == mAdjustBeanModel){
//					Toast.makeText(AdjustPriceActivity.this	, "无数据!", Toast.LENGTH_SHORT).show();
					mPageIndex = 1;
					toRequestPriceMsg();
					return;
				}
				initDataTwo();
				final String count = mAdjustBeanModel.getCounts();
				if(TextUtils.isEmpty(count))return;
				final int countInt = Integer.valueOf(count);
				if(mPageIndex >= countInt){
					mPageIndex = 1;
				} else {
					mPageIndex++;
				}
				toRequestPriceMsg();
			}
		});
		mEditCarColor.clearFocus();
		mEditCarColor.setFocusable(false);
		mEditCarColor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(tooFastClick())return;
				selectCarColor();
			}
		});
		mRelativeCarColor.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(tooFastClick())return;
				selectCarColor();
			}
		});
		mEditCarLevel.clearFocus();
		mEditCarLevel.setFocusable(false);
		mEditCarLevel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(tooFastClick())return;
				selectCarCondition();
			}
		});
		mRelativeCarLevel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(tooFastClick())return;
				selectCarCondition();
			}
		});
		
		mBtnReset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 重置
				initDataTwo();
			}
		});
		mBtnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 提交
				if(null == mAdjustBeanModel)return;
				final String id = mAdjustBeanModel.getId();
				if(TextUtils.isEmpty(id)){
					Toast.makeText(AdjustPriceActivity.this	, "数据ID不能为空!", Toast.LENGTH_SHORT).show();
					return;
				}
				final String color = mEditCarColor.getText().toString();
				if(TextUtils.isEmpty(color)){
					Toast.makeText(AdjustPriceActivity.this	, "请选择颜色!", Toast.LENGTH_SHORT).show();
					return;
				}
				final String carlevel = mEditCarLevel.getText().toString();
				if(TextUtils.isEmpty(carlevel)){
					Toast.makeText(AdjustPriceActivity.this	, "请选择车况级别!", Toast.LENGTH_SHORT).show();
					return;
				}
				final String c2bprice = mEditShougou.getText().toString();
				if(TextUtils.isEmpty(c2bprice)){
					Toast.makeText(AdjustPriceActivity.this	, "请输入收购价格!", Toast.LENGTH_SHORT).show();
					return;
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
				final String time = sdf.format(new Date());
				final String c2bupdatetime = time;
				
				String b2cprice = mEditXiaoshou.getText().toString();
				if(TextUtils.isEmpty(b2cprice)){
					Toast.makeText(AdjustPriceActivity.this	, "请输入销售价格!", Toast.LENGTH_SHORT).show();
					return;
				}
				final String b2cupdatetime = time;
				
				final String msrpStr = mAdjustBeanModel.getMsrp();
				if(TextUtils.isEmpty(msrpStr)){
					Toast.makeText(AdjustPriceActivity.this	, "厂商指导价为空!", Toast.LENGTH_SHORT).show();
					return;
				} else {
					float msrpFl = Float.valueOf(msrpStr);
					final float c2bF = Float.valueOf(c2bprice);
					final float b2cF = Float.valueOf(b2cprice);
					if(c2bF > msrpFl){
						Toast.makeText(AdjustPriceActivity.this	, "收购价格不能高于厂商指导价!", Toast.LENGTH_SHORT).show();
						return;
					}
					if(b2cF > msrpFl){
						Toast.makeText(AdjustPriceActivity.this	, "销售价格不能高于厂商指导价!", Toast.LENGTH_SHORT).show();
						return;
					}
					if(c2bF > b2cF){
						Toast.makeText(AdjustPriceActivity.this	, "销售价格不能低于收购价格!", Toast.LENGTH_SHORT).show();
						return;
					}
				}
				
				final Map<String, String> map = getSubmitParams(id,
						color,b2cprice,b2cupdatetime,c2bprice,
						c2bupdatetime,carlevel);
				toSubmitPriceMsg(map);
			}
		});
		mTvRefreshAdjust.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPageIndex = 1;
				toRequestPriceMsg();
			}
		});
		initDataOne();
		mPageIndex = 1;
		toRequestPriceMsg();
	}
	private int mPageIndex = 1;
	private void initDataOne(){
		if(null == mAdjustBeanModel){
			mTvTitlePerLeft.setText("0");
			mTvTitlePerRight.setText("0");
			mTvTitleTime.setText("--");
			mTvAddress.setText("--");
			mTvFullName.setText("--");
			mTvTime.setText("--");
			mTvMile.setText("--");
			return;
		}
		mTvTitlePerLeft.setText(String.valueOf(mPageIndex));
		mTvTitlePerRight.setText(mAdjustBeanModel.getCounts());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		mTvTitleTime.setText(sdf.format(new Date()) + "-" + mAdjustBeanModel.getCompleteDate());
		mTvAddress.setText(mAdjustBeanModel.getProvinceName() + " " + mAdjustBeanModel.getCityName());
		mTvFullName.setText(mAdjustBeanModel.getCarFullName() + "  " + mAdjustBeanModel.getMsrp() + "万");
		mTvTime.setText(mAdjustBeanModel.getLaunchdate());
		mTvMile.setText(mAdjustBeanModel.getTypicalMileage());
	}
	private void initDataTwo(){
		mEditCarColor.setText("");
		mEditCarColor.setTag(false);
		mEditCarLevel.setText("");
		mEditCarLevel.setTag(false);
		mEditShougou.setText("");
		mEditXiaoshou.setText("");
	}
	private void toRequestPriceMsg(){
		toShowLoadingDialog();
		HttpServiceHelper.newToRequest(AdjustPriceActivity.this, mHandler, getParamsForList(),
				REQUEST_ADJUST_LIST_SUC, REQUEST_ADJUST_LIST_FAIL);
	}
	private void toSubmitPriceMsg(Map<String, String> params){
		toShowLoadingDialog();
		HttpServiceHelper.newToRequest(AdjustPriceActivity.this, mHandler, params,
				REQUEST_ADJUST_SUBMIT_SUC, REQUEST_ADJUST_SUBMIT_FAIL);
	}
	private Map<String, String> getParamsForList() {

		// 在这里设置需要post的参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("op", "GetRecordByUserId");
		map.put("Pageindex", String.valueOf(mPageIndex));
		map.put("pagesize", "1");
		if(null != AppContext.user)
		map.put("userId", AppContext.user.getId());
		
		Map<String, Object> mapp = new HashMap<String, Object>();
		mapp.put("op", "GetRecordByUserId");
		mapp.put("Pageindex", String.valueOf(mPageIndex));
		mapp.put("pagesize", "1");
		if(null != AppContext.user)
		mapp.put("userId", AppContext.user.getId());
		String sign = MD5Utils.getMD5Sign(mapp);
		map.put("sign", sign);
		return map;
	}
	private Map<String, String> getSubmitParams(String id,String color,String b2cprice,
			String b2cupdatetime,String c2bprice,String c2bupdatetime,String carlevel) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("op", "UpdateRecord");
		map.put("Id", id);
		map.put("color", color);
		map.put("b2cprice", b2cprice);
		map.put("b2cupdatetime", b2cupdatetime);
		map.put("c2bprice", c2bprice);
		map.put("c2bupdatetime", c2bupdatetime);
		map.put("carlevel", carlevel);
		
		Map<String, Object> mapp = new HashMap<String, Object>();
		mapp.put("op", "UpdateRecord");
		mapp.put("Id", id);
		mapp.put("color", color);
		mapp.put("b2cprice", b2cprice);
		mapp.put("b2cupdatetime", b2cupdatetime);
		mapp.put("c2bprice", c2bprice);
		mapp.put("c2bupdatetime", c2bupdatetime);
		mapp.put("carlevel", carlevel);
		String sign = MD5Utils.getMD5Sign(mapp);
		map.put("sign", sign);
		return map;
	}
	private final int REQUEST_ADJUST_LIST_SUC = 0x2001;
	private final int REQUEST_ADJUST_LIST_FAIL = 0x2002;
	private final int REQUEST_ADJUST_SUBMIT_SUC = 0x2003;
	private final int REQUEST_ADJUST_SUBMIT_FAIL = 0x2004;
	private AdjustBeanModel mAdjustBeanModel;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			dismissLoadingDialog();
			switch (msg.what) {
			case REQUEST_ADJUST_LIST_SUC:
				final String sucMsg = (String) msg.obj;
				if (JsonObjectImpl.isSuccess(AdjustPriceActivity.this, sucMsg)) {
					mAdjustBeanModel = getModelFromJson(sucMsg);
					toShowIfNull(false);
					initDataOne();
					initDataTwo();
				} else {
					toShowIfNull(true);
				}
				break;
			case REQUEST_ADJUST_LIST_FAIL:
			case REQUEST_ADJUST_SUBMIT_FAIL:
				showMsgToast("登录失败，网络请求错误");
				break;
			case REQUEST_ADJUST_SUBMIT_SUC:
				final String subM = (String) msg.obj;
				if (JsonObjectImpl.isSuccess(AdjustPriceActivity.this, subM)) {
					showMsgToast("提交成功！");
					((HomeActivity)getParent()).toInitPersonCenter();
					initDataTwo();
					final String count = mAdjustBeanModel.getCounts();
					if(TextUtils.isEmpty(count))break;
					final int countInt = Integer.valueOf(count);
					if(mPageIndex > countInt){
						mPageIndex = countInt;
					}
					toRequestPriceMsg();
				} else {
					showMsgToast("提交失败！");
				}
				break;
			default:
				break;
			}
		}

	};
	private AdjustBeanModel getModelFromJson(String str){
		if(TextUtils.isEmpty(str))return null;
		Gson gson = new Gson();
		return gson.fromJson(str, AdjustBeanModel.class);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private int mIntCheckCarLevelId = 0;
	private int mIntCurrentCarLevelId = 0;
	private void selectCarCondition() {
		AlertDialog.Builder builder = new AlertDialog.Builder(AdjustPriceActivity.this);
		builder.setIcon(R.drawable.laiyuan);
		builder.setTitle("请选择车况");
		final String[] conditions = { "A级车况", "B级车况", "C级车况", "D级车况" };
		mIntCurrentCarLevelId = mIntCheckCarLevelId;
		builder.setSingleChoiceItems(conditions, mIntCurrentCarLevelId,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mIntCurrentCarLevelId = which;
					}
				});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mIntCheckCarLevelId = mIntCurrentCarLevelId;
				mEditCarLevel.setText(conditions[mIntCurrentCarLevelId]);
				mEditCarLevel.setTag(true);
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}
	private int mIntCheckCarColorId = 0;
	private int mIntCurrentCarColorId = 0;
	private void selectCarColor() {
		AlertDialog.Builder builder = new AlertDialog.Builder(AdjustPriceActivity.this);
		builder.setIcon(R.drawable.laiyuan);
		builder.setTitle("请选择颜色");
		final String[] conditions = { "黑色", "白色", "灰色", "橙色",
				"褐色", "红色", "黄色", "金色",
				"咖啡色", "蓝色", "栗色", "绿色",
				"米色", "青色", "紫色", "深灰色",
				"香槟色", "银灰色", "银色", "棕色",
				"其他"};
		mIntCurrentCarColorId = mIntCheckCarColorId;
		builder.setSingleChoiceItems(conditions, mIntCurrentCarColorId,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mIntCurrentCarColorId = which;
					}
				});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mIntCheckCarColorId = mIntCurrentCarColorId;
				mEditCarColor.setText(conditions[mIntCurrentCarColorId]);
				mEditCarColor.setTag(true);
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}
	private long mOldTime = 0l;
	private boolean tooFastClick(){
		final long current = System.currentTimeMillis();
		if(current-mOldTime < 500){
			return true;
		}
		mOldTime = current;
		return false;
	}
}
