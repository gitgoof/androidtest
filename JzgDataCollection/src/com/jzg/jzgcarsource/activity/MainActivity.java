package com.jzg.jzgcarsource.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jzg.jzgcarsource.carindex.vo.CarRelease;
import com.jzg.jzgcarsource.utils.HttpServiceHelper;
import com.jzg.jzgcarsource.utils.JsonObjectImpl;
import com.jzg.jzgcarsource.utils.MD5Utils;

public class MainActivity extends BaseActivity implements OnClickListener {

	/**
	 * 车辆地区
	 */
	private final int ADDRESS = 1;
	/**
	 * 车辆信息
	 */
	private final int CAR_INFO = 2;
	/**
	 * 上牌时间
	 */
	private final int BUYCAR_TIME = 3;
	/**
	 * 收购时间
	 */
	private final int BUYOUT_TIME = 4;
	/**
	 * 销售时间
	 */
	private final int SELL_TIME = 5;
	private final int SCROLL_TOP = 6;
	/**
	 * 车况
	 */
	private final int CAR_CONDITION = 7;

	private RelativeLayout valuation_place_layout;
	private RelativeLayout valuation_shougoutime_item_layout;
	// private RelativeLayout valuation_place_layout;
	EditText valuation_place_edit;// 上牌地区
	EditText valuation_car_type_edit;// 车辆类型
	EditText valuation_card_time_edit;// 上牌时间
	EditText valuation_mileage_edit;// 行驶里程
	EditText buyout_price;// 收购价格
	EditText buyout_time;// 收购时间
	EditText sell_price;// 销售价格
	private EditText sell_time;// 销售时间
	EditText car_source;// 车辆来源
	EditText car_color;// 车辆颜色
	EditText car_condition;// 车况
	private TextView logout;
	private Button reset, post;
	private int mYear, mMonth;
	private String mCityId = "";
	private String mProvinceId = "";
	private String address = "";
	private String carInfo = "";
	private String buyCarDate = "";
	private String mileages = "";
	private String buyoutPrice = "";
	private String buyoutTime = "";
	private String sellPrice = "";
	private String sellTime = "";
	private String carSource = "";
	private String brandId = "";
	private String modelId = "";
	private String styleId = "";
	private String carColor = "";
	private String carCondition = "";
	private String carCjfs = "";

	private ScrollView scroll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// carRelease = appContext.getCarRelease();
		initView();
		initData();
	}

	private void initData() {
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH) + 1;
		address = userInfo.getString("mRegion", "");
		mProvinceId = userInfo.getString("mProvinceId", "");
		mCityId = userInfo.getString("mCityId", "");
		if (!TextUtils.isEmpty(address) && !TextUtils.isEmpty(mProvinceId)
				&& !TextUtils.isEmpty(mCityId)) {
			valuation_place_edit.setText(address);
		}
		carInfo = userInfo.getString("carname", "");
		modelId = userInfo.getString("modelId", "");
		styleId = userInfo.getString("styleId", "");
		brandId = userInfo.getString("brandId", "");
		if (!TextUtils.isEmpty(carInfo) && !TextUtils.isEmpty(modelId)
				&& !TextUtils.isEmpty(styleId) && !TextUtils.isEmpty(brandId)) {
			valuation_car_type_edit.setText(carInfo);
		}

	}

	public void initView() {
		scroll = (ScrollView) findViewById(R.id.scroll);
		logout = (TextView) findViewById(R.id.btn_title_right_more);
		logout.setOnClickListener(this);
		valuation_car_type_edit = (EditText) findViewById(R.id.valuation_car_type_edit);
		valuation_car_type_edit.setOnClickListener(this);
		valuation_place_layout = (RelativeLayout) findViewById(R.id.valuation_place_layout);
		valuation_shougoutime_item_layout = (RelativeLayout) findViewById(R.id.valuation_shougoutime_item_layout);
		valuation_place_layout.setOnClickListener(this);
		valuation_shougoutime_item_layout.setOnClickListener(this);
		valuation_place_edit = (EditText) findViewById(R.id.valuation_place_edit);
		valuation_place_edit.clearFocus();
		valuation_place_edit.setOnClickListener(this);
		valuation_card_time_edit = (EditText) findViewById(R.id.valuation_card_time_edit);
		valuation_card_time_edit.clearFocus();
		valuation_card_time_edit.setOnClickListener(this);
		valuation_mileage_edit = (EditText) findViewById(R.id.valuation_mileage_edit);
		valuation_mileage_edit.clearFocus();
		buyout_price = (EditText) findViewById(R.id.valuation_jiage_edit);
		buyout_price.clearFocus();
		buyout_time = (EditText) findViewById(R.id.valuation_card_shougoutime_edit);
		buyout_time.clearFocus();
		sell_time = (EditText) findViewById(R.id.valuation_card_xiaoshoutime_edit);
		sell_time.clearFocus();
		buyout_time.setOnClickListener(this);
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(buyout_time.getWindowToken(), 0);
		buyout_time.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(buyout_time.getWindowToken(), 0);
				return false;
			}
		});
		imm.hideSoftInputFromWindow(sell_time.getWindowToken(), 0);
		sell_time.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(sell_time.getWindowToken(), 0);
				return false;
			}
		});
		sell_time.setOnClickListener(this);
		sell_price = (EditText) findViewById(R.id.valuation_xiaoshoujiage_edit);
		car_source = (EditText) findViewById(R.id.valuation_lycar_type_edit);
		sell_price.clearFocus();
		car_source.clearFocus();
		car_color = (EditText) findViewById(R.id.valuation_colorcar_type_edit);
		car_color.clearFocus();

		car_condition = (EditText) findViewById(R.id.valuation_conditioncar_type_edit);
		car_condition.clearFocus();
		car_condition.setOnClickListener(this);
		car_condition.setInputType(InputType.TYPE_NULL);

		valuation_car_type_edit.setInputType(InputType.TYPE_NULL);
		valuation_card_time_edit.setInputType(InputType.TYPE_NULL);
		valuation_place_edit.setInputType(InputType.TYPE_NULL);
		buyout_time.setInputType(InputType.TYPE_NULL);
		sell_time.setInputType(InputType.TYPE_NULL);
		reset = (Button) findViewById(R.id.chongzhi_btn);
		reset.setOnClickListener(this);
		post = (Button) findViewById(R.id.tijiao_btn);
		post.setOnClickListener(this);
		imm.hideSoftInputFromWindow(valuation_mileage_edit.getWindowToken(), 0);
		
		valuation_mileage_edit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (TextUtils.isEmpty(s))
					return;
				if (!s.toString().contains(".")
						&& Integer.valueOf(String.valueOf(s)) >= 50) {
					valuation_mileage_edit.setText("49");
					valuation_mileage_edit.setSelection("49".length());
				} else if (s.length() > 2 && !s.toString().contains(".")) {
					valuation_mileage_edit.setText(s.toString().subSequence(0,
							s.length() - 1));
					valuation_mileage_edit.setSelection(s.length() - 1);
					Toast.makeText(MainActivity.this, "请输入小数点", 2000).show();

				} else if (s.toString().contains(".")
						&& s.toString()
								.substring(s.toString().lastIndexOf("."))
								.length() > 3) {
					valuation_mileage_edit.setText(s.toString().substring(0,
							s.toString().lastIndexOf("."))
							+ s.toString()
									.substring(s.toString().lastIndexOf("."))
									.subSequence(0, 3));
					valuation_mileage_edit.setSelection(s.length() - 1);
					
					Toast.makeText(MainActivity.this, "只能输入小数点后两位", 2000)
							.show();
					
				} else if ("0".equals(String.valueOf(s.charAt(0)))
						&& !s.toString().contains(".")) {
					if (s.length() == 2
							&& "0".equals(String.valueOf(s.charAt(0)))) {
						valuation_mileage_edit.setText("0");
						valuation_mileage_edit.setSelection(1);
					}
					Toast.makeText(MainActivity.this, "请输入小数点", 2000).show();
				} else if (".".equals(String.valueOf(s.charAt(0)))) {
					valuation_mileage_edit.setText("");
					Toast.makeText(MainActivity.this, "第一位不能为小数点", 2000).show();
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		sell_price.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (TextUtils.isEmpty(s))
					return;
				if (s.toString().contains(".")
						&& s.toString()
								.substring(s.toString().lastIndexOf("."))
								.length() > 3) {
					sell_price.setText(s.toString().substring(0,
							s.toString().lastIndexOf("."))
							+ s.toString()
									.substring(s.toString().lastIndexOf("."))
									.subSequence(0, 3));
					sell_price.setSelection(s.length() - 1);
					Toast.makeText(MainActivity.this, "只能输入小数点后两位", 2000)
							.show();
				} else if ("0".equals(String.valueOf(s.charAt(0)))
						&& !s.toString().contains(".")) {
					if (s.length() == 2
							&& "0".equals(String.valueOf(s.charAt(0)))) {
						sell_price.setText("0");
						sell_price.setSelection(1);
					}
					Toast.makeText(MainActivity.this, "请输入小数点", 2000).show();
				} else if (".".equals(String.valueOf(s.charAt(0)))) {
					sell_price.setText("");
					Toast.makeText(MainActivity.this, "第一位不能为小数点", 2000).show();
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		buyout_price.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (TextUtils.isEmpty(s))
					return;
				if (s.toString().contains(".")
						&& s.toString()
								.substring(s.toString().lastIndexOf("."))
								.length() > 3) {
					buyout_price.setText(s.toString().substring(0,
							s.toString().lastIndexOf("."))
							+ s.toString()
									.substring(s.toString().lastIndexOf("."))
									.subSequence(0, 3));
					buyout_price.setSelection(s.length() - 1);
					Toast.makeText(MainActivity.this, "只能输入小数点后两位", 2000)
							.show();
				} else if ("0".equals(String.valueOf(s.charAt(0)))
						&& !s.toString().contains(".")) {
					if (s.length() == 2
							&& "0".equals(String.valueOf(s.charAt(0)))) {
						buyout_price.setText("0");
						buyout_price.setSelection(1);
					}
					Toast.makeText(MainActivity.this, "请输入小数点", 2000).show();
				} else if (".".equals(String.valueOf(s.charAt(0)))) {
					buyout_price.setText("");
					Toast.makeText(MainActivity.this, "第一位不能为小数点", 2000).show();
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_right_more:
			showWarnDialog(MainActivity.this);
			break;
		case R.id.valuation_car_type_edit_layout:
		case R.id.valuation_car_type_edit:
		case R.id.valuation_car_icon:
			selectCarType();
			break;

		case R.id.valuation_card_time_edit_layout:
		case R.id.valuation_card_time_edit:
		case R.id.valuation_time_icon:
		case R.id.valuation_time_item_bj:
		case R.id.valuation_time_item_layout:
			selectTime("选择上牌时间", BUYCAR_TIME, true);
			break;

		case R.id.valuation_place_layout:
		case R.id.valuation_place_edit:
			startSelectAddress();
			break;
		case R.id.valuation_card_shougoutime_edit:
			selectTime("选择收购时间", BUYOUT_TIME, false);
			break;
		case R.id.valuation_shougoutime_item_layout:
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
			break;
		case R.id.valuation_card_xiaoshoutime_edit:
			selectTime("选择销售时间", SELL_TIME, false);
			break;
		case R.id.chongzhi_btn:
			reset();
			break;
		case R.id.tijiao_btn:
			post();
			break;
		case R.id.valuation_conditioncar_type_edit:
			selectCarCondition();
			break;
		}
	}

	/**
	 * 显示异常对话框
	 * 
	 * @param message
	 */
	public void showWarnDialog(Activity activity) {
		new AlertDialog.Builder(activity).setTitle("提示").setCancelable(false)
				.setNeutralButton("取消", new AlertDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).setMessage("点击确定将会退出该账户！")
				.setPositiveButton("确定", new AlertDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Editor edit = userInfo.edit();
						edit.putString("mProvinceId", "");
						edit.putString("mCityId", "");
						edit.putString("mRegion", "");
						edit.putString("name", "");
						edit.putString("styleId", "");
						edit.putString("brandId", "");
						edit.putString("carname", "");
						edit.putString("modelId", "");
						edit.commit();
						edit.clear();
						Intent in = new Intent(MainActivity.this,
								LoginActivity.class);
						startActivity(in);
						finish();
					}
				}).create().show();
	}

	private void startSelectAddress() {
		Intent intent = new Intent(MainActivity.this, RegionActivity.class);
		intent.putExtra("hideAllRegion", "hideAllRegion");
		getParent().startActivityForResult(intent, ADDRESS);

	}

	public void selectTime(String title, int requestCode, boolean isClear) {
		// 选择时间
		if (TextUtils.isEmpty(valuation_car_type_edit.getText())) {
			Toast.makeText(MainActivity.this, "请选择车辆信息！", Toast.LENGTH_LONG)
					.show();
		} else {
			showDatePicker(title, requestCode, mMonth, isClear);
		}
	}

	int pos = -1;

	public void selectCarCondition() {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("请选择车况");
		final String[] conditions = { "A", "B", "C", "D" };

		builder.setSingleChoiceItems(conditions, 0,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						pos = which;
					}
				});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (pos == -1) {
					Message msg = Message.obtain();
					msg.obj = conditions[0];
					carConditionHandler.sendMessage(msg);
				} else {
					Message msg = Message.obtain();
					msg.obj = conditions[pos];
					carConditionHandler.sendMessage(msg);
					pos = -1;
				}
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

	/**
	 * 显示时间弹出
	 */
	@SuppressLint("NewApi")
	private void showDatePicker(String title, int requestCode, int mMonth,
			boolean isClear) {
		Intent in = new Intent(MainActivity.this, SheetTimeAty.class);
		in.putExtra("Maxyear", 2016);
		in.putExtra("Minyear", userInfo.getInt("year", 2016) - 1);
		in.putExtra("MaxMonth", 12);
		in.putExtra("MinMonth", 1);
		in.putExtra("title", title);
		in.putExtra("isClear", isClear);
		// wyd修改，这段代码不知道什么意思先注释掉了
		if (requestCode != BUYCAR_TIME) {
			mMonth = mMonth + 1;
		}
		in.putExtra("CurMonth", mMonth);
		getParent().startActivityForResult(in, requestCode);

	}

	// private CarRelease carRelease;
	public void selectCarType() {
		// 弹出选择车型界面
		// Intent in = new Intent(MainActivity.this,
		// CarReleaseIndexActivity.class);
		// // in.putExtra("isQueryCar", "isTrue");
		// // 是否已经进行过查价的标记，如果文本框不为空则跳转到原有已经查过的位置
		// in.putExtra("queryEditIsNull", TextUtils
		// .isEmpty(valuation_car_type_edit.getText()) ? true : false);
		// startActivityForResult(in, CAR_INFO);
		// carRelease.setCarStyle("");
		Intent in = new Intent(this,
				com.jzg.jzgcarsource.carindex.CarReleaseIndexActivity.class);
		in.putExtra("carReleaseActivity", "from_carReleaseActivity");
		in.putExtra("queryEditIsNull", TextUtils
				.isEmpty(valuation_car_type_edit.getText()) ? true : false);
		getParent().startActivityForResult(in, CAR_INFO);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		// super.onActivityResult(arg0, arg1, arg2);
		// switch (requestCode) {
		// // 车型跳转返回结果
		// case CAR_STYLE_REQUEST:
		// carStyleContent.setText(carRelease.getCarStyle());
		// break;
		switch (requestCode) {
		case CAR_INFO:
			if (data != null) {
				// JzgCarChooseStyle style = (JzgCarChooseStyle) data
				// .getSerializableExtra("mQueryCarStyle");
				// if (style != null) {
				// // carStyleId = style.getId();
				// // mCarMsg = style.getFullName();
				//
				// MessageUtils
				// .sendMessage(mHandler, R.id.carDetailSuc, style);
				// // HttpService.getCarStyle(mHandler, style);
				// }
				// 获取车型详情
				CarRelease carRelease = (CarRelease) data
						.getSerializableExtra("mQueryCarStyle");
				styleId = carRelease.getStyleId() + "";
				brandId = String.valueOf(carRelease.getStyle().getMakeId());
				modelId = String.valueOf(carRelease.getStyle().getModelId());
				String nowMsrp = carRelease.getStyle().getNowMsrp();
				String name = carRelease.getCarStyle();
				Editor edit = userInfo.edit();

				edit.putString("styleId", styleId);
				edit.putString("brandId", brandId);
				edit.putString("carname", name);
				edit.putString("modelId", modelId);
				edit.putString("nowmsrp", nowMsrp);
				
				edit.putInt("year", carRelease.getStyle().getYear());
				edit.commit();
				edit.clear();
				valuation_car_type_edit.setText(name);
			}
			break;
		case BUYCAR_TIME:
			if (data != null) {
				int year = data.getIntExtra("year", 2016);
				int monthOfYear = data.getIntExtra("month", 1);

				// int mMonths = (mYear - year) * 12 + (mMonth - monthOfYear);

				valuation_card_time_edit.setText(year + "-" + monthOfYear);
			}
			break;
		case ADDRESS:
			if (data != null) {
				String mRegion = data.getStringExtra("province") + "  "
						+ data.getStringExtra("city");
				valuation_place_edit.setText(mRegion);
				mProvinceId = String
						.valueOf(data.getIntExtra("mProvinceId", 0));
				mCityId = String.valueOf(data.getIntExtra("mCityId", 0));
				Editor edit = userInfo.edit();
				edit.putString("mProvinceId", mProvinceId);
				edit.putString("mCityId", mCityId);
				edit.putString("mRegion", mRegion);
				edit.commit();
				edit.clear();
			}
			break;
		case BUYOUT_TIME:
			if (data != null) {
				int year = data.getIntExtra("year", 2015);
				if (year == 0) {
					buyout_time.setText("");
				} else {
					int monthOfYear = data.getIntExtra("month", 1);
					buyout_time.setText(year + "-" + monthOfYear);
				}

			}
			break;
		case SELL_TIME:
			if (data != null) {

				int year = data.getIntExtra("year", 2015);
				if (year == 0) {
					sell_time.setText("");
				} else {
					int monthOfYear = data.getIntExtra("month", 1);
					sell_time.setText(year + "-" + monthOfYear);
				}

			}
			break;

		default:
			break;
		}
	}

	private Handler carConditionHandler = new Handler() {
		public void handleMessage(Message msg) {
			car_condition.setText(msg.obj + "");
		};
	};

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case R.id.post_suc:
				dismissLoadingDialog();
				String sucMsg = (String) msg.obj;
				if (JsonObjectImpl.isSuccess(MainActivity.this, sucMsg)) {
					((HomeActivity)getParent()).toInitPersonCenter();
					showMsgToast("提交成功！！！");
					car_source.setText("");
					sell_price.setText("");
					buyout_time.setText("");
					sell_time.setText("");
					buyout_price.setText("");
					valuation_mileage_edit.setText("");
					valuation_card_time_edit.setText("");
					car_color.setText("");
					car_condition.setText("");
					mHandler.sendEmptyMessage(SCROLL_TOP);
					
					styleId = "";
					brandId = "";
					modelId = "";
					Editor edit = userInfo.edit();

					edit.putString("styleId", styleId);
					edit.putString("brandId", brandId);
					edit.putString("carname", "");
					edit.putString("modelId", modelId);
					edit.putString("nowmsrp", "");
					
					edit.putInt("year", -1);
					edit.commit();
					edit.clear();
					valuation_car_type_edit.setText("");
				} else {
					JsonObjectImpl.isShowMsg(MainActivity.this, sucMsg);
//					showMsgToast("提交失败");
				}
				break;
			case R.id.post_fail:
				dismissLoadingDialog();
				showMsgToast("提交失败，网络请求错误");
				break;
			case SCROLL_TOP:
				scroll.fullScroll(ScrollView.FOCUS_UP);
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

	/**
	 * 重置
	 */
	private void reset() {

		car_source.setText("");
		sell_price.setText("");
		buyout_time.setText("");
		sell_time.setText("");
		buyout_price.setText("");
		valuation_mileage_edit.setText("");
		valuation_card_time_edit.setText("");
		valuation_car_type_edit.setText("");
		valuation_place_edit.setText("");
		car_color.setText("");
		car_condition.setText("");
		mHandler.sendEmptyMessage(SCROLL_TOP);

	}

	/**
	 * 提交
	 */
	private void post() {
		if (isComplete()) {
			toShowLoadingDialog();
			HttpServiceHelper.post(MainActivity.this, mHandler, getParams(),
					R.id.post_suc, R.id.post_fail);
		}

	}

	/**
	 * 提交的参数
	 */
	public Map<String, String> getParams() {

		// 在这里设置需要post的参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("op", "SavePgsPrice");
		map.put("userid", userInfo.getString("id", ""));
		map.put("proId", mProvinceId);
		map.put("cityId", mCityId);
		map.put("brandId", brandId);
		map.put("modelId", modelId);
		map.put("styleId", styleId);
		map.put("color", carColor);
		map.put("condition", carCondition);
		if (TextUtils.isEmpty(buyoutPrice)) {
			map.put("buyoutPrice", "0");
		} else {
			map.put("buyoutPrice", buyoutPrice);
		}

		if (TextUtils.isEmpty(buyoutTime)) {
			map.put("buyoutTime", "");
		} else {
			map.put("buyoutTime", buyoutTime);
		}
		if (TextUtils.isEmpty(sellPrice)) {
			map.put("sellPrice", "0");
		} else {
			map.put("sellPrice", sellPrice);
		}
		if (TextUtils.isEmpty(sellTime)) {
			map.put("sellTime", "");
		} else {
			map.put("sellTime", sellTime);
		}
		if (TextUtils.isEmpty(carSource)) {
			map.put("carSource", "");
		} else {
			map.put("carSource", carSource);
		}
		map.put("buyCarDate", buyCarDate);
		map.put("mileages", mileages);
		Map<String, Object> mapp = new HashMap<String, Object>();
		mapp.putAll(map);
		String sign = MD5Utils.getMD5Sign(mapp);
		map.put("sign", sign);
		return map;
	}

	private boolean isComplete() {

		address = valuation_place_edit.getText().toString();
		if (TextUtils.isEmpty(address)) {
			showMsgToast("请填写上牌地区");
			return false;
		}
		carInfo = valuation_car_type_edit.getText().toString();
		if (TextUtils.isEmpty(carInfo)) {
			showMsgToast("请填写车辆信息");
			return false;
		}
		buyCarDate = valuation_card_time_edit.getText().toString();
		if (TextUtils.isEmpty(buyCarDate)) {
			showMsgToast("请填写上牌时间");
			return false;
		}
		mileages = valuation_mileage_edit.getText().toString();
		if (TextUtils.isEmpty(mileages)) {
			showMsgToast("请填写行驶里程。");
			return false;
		}
		buyoutPrice = buyout_price.getText().toString();
		buyoutTime = buyout_time.getText().toString();
		if (TextUtils.isEmpty(buyoutPrice) && !TextUtils.isEmpty(buyoutTime)) {
			showMsgToast("请填写收购价格");
			return false;
		}
		if (!TextUtils.isEmpty(buyoutPrice) && TextUtils.isEmpty(buyoutTime)) {
			showMsgToast("请填写收购时间");
			return false;
		}
		sellPrice = sell_price.getText().toString();
		// if (TextUtils.isEmpty(sellPrice)) {
		// showMsgToast("销售价格不能为空!");
		// return false;
		// }
		sellTime = sell_time.getText().toString();
		// if (TextUtils.isEmpty(sellTime)) {
		// showMsgToast("销售时间不能为空!");
		// return false;
		// }
		if (TextUtils.isEmpty(buyoutPrice) && TextUtils.isEmpty(buyoutTime)
				&& TextUtils.isEmpty(sellTime) && TextUtils.isEmpty(sellPrice)) {
			showMsgToast("请填写收购价格、时间或销售价格、时间。");
			return false;
		}
		if (TextUtils.isEmpty(sellPrice)) {
			showMsgToast("销售价格不能为空!");
			return false;
		}
		
		if (TextUtils.isEmpty(sellTime)) {
			showMsgToast("销售时间不能为空!");
			return false;
		}
		
		if (TextUtils.isEmpty(sellTime) && !TextUtils.isEmpty(sellPrice)) {
			showMsgToast("请填写销售时间");
			return false;
		}
		if (!TextUtils.isEmpty(sellTime) && TextUtils.isEmpty(sellPrice)) {
			showMsgToast("请填写销售价格");
			return false;
		}
		
		final String msrpStr = userInfo.getString("nowmsrp", "");
		if(TextUtils.isEmpty(msrpStr)){
			Toast.makeText(MainActivity.this	, "厂商指导价为空!", Toast.LENGTH_SHORT).show();
			return false;
		} else {
			float msrpFl = Float.valueOf(msrpStr);
			final float c2bF = Float.valueOf(buyoutPrice);
			final float b2cF = Float.valueOf(sellPrice);
			if(c2bF > msrpFl){
				Toast.makeText(MainActivity.this	, "收购价格不能高于厂商指导价!", Toast.LENGTH_SHORT).show();
				return false;
			}
			if(b2cF > msrpFl){
				Toast.makeText(MainActivity.this	, "销售价格不能高于厂商指导价!", Toast.LENGTH_SHORT).show();
				return false;
			}
		}

		carSource = car_source.getText().toString();
		/*if (TextUtils.isEmpty(carSource)) {
			showMsgToast("请填写车辆来源");
			return false;
		}*/
		carColor = car_color.getText().toString();
		if (TextUtils.isEmpty(carColor)) {
			showMsgToast("请填写颜色");
			return false;
		}

		carCondition = car_condition.getText().toString();
		if (TextUtils.isEmpty(carCondition)) {
			showMsgToast("请选择车况");
			return false;
		}
		
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		try {
			final Date shangpai = sdf.parse(buyCarDate);
			final Date shougou = sdf.parse(buyoutTime);
			final Date xiaoshou = sdf.parse(sellTime);
			if(shangpai.after(shougou)){
				showMsgToast("收购时间不能早于上牌时间！");
				return false;
			}
			if(shougou.after(xiaoshou)){
				showMsgToast("销售时间不能早于收购时间！");
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			showMsgToast("时间格式错误！");
			return false;
		}
		
		return true;
	}

}
