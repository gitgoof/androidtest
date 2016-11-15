package com.jzg.jzgcarsource.activity;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jzg.jzgcarsource.application.AppContext;
import com.jzg.jzgcarsource.bean.User;
import com.jzg.jzgcarsource.utils.HttpServiceHelper;
import com.jzg.jzgcarsource.utils.JsonObjectImpl;
import com.jzg.jzgcarsource.utils.MD5Utils;
import com.mato.sdk.proxy.Proxy;

public class LoginActivity extends BaseActivity implements OnClickListener {
	private EditText username_edit;
	private EditText password_edit;
	private Button login;
	private String requestUsername = "";
	private String requestPass = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Proxy.supportWebview(this.getApplication());
		Proxy.start(this.getApplication());
		setContentView(R.layout.activity_login);
		initView();
		checkLogin();
	}

	private void initView() {
		login = (Button) findViewById(R.id.login_button);
		username_edit = (EditText) findViewById(R.id.username_edit);
		password_edit = (EditText) findViewById(R.id.password_edit);
		login.setOnClickListener(this);

	}

	/**
	 * 校验用户是否是自动登录，还是只是记住密码，还是什么操作都没有
	 */
	private void checkLogin() {

		String name = userInfo.getString("name", "");
		requestUsername = userInfo.getString("loginname", "");
		requestPass = userInfo.getString("password", "");
		if (isNull()) {
			toAutoLogin();
		}
		
		if (!TextUtils.isEmpty(name)) {
			// username_edit.setText(name);
			// 跳转到首页
//			Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//			startActivity(intent);
//			finish();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_button:
			login();
			break;

		default:
			break;
		}
	}
	private void toAutoLogin(){
		toShowLoadingDialog();
		HttpServiceHelper.loginNew(LoginActivity.this, mHandler, getParamsNew(),
				R.id.login_suc, R.id.login_fail);
	}
	private void login() {
		requestUsername = username_edit.getText().toString();
		requestPass = password_edit.getText().toString();

		if (isNull()) {
			toShowLoadingDialog();
			HttpServiceHelper.loginNew(LoginActivity.this, mHandler, getParamsNew(),
					R.id.login_suc, R.id.login_fail);
//			HttpServiceHelper.login(LoginActivity.this, mHandler, getParams(),
//					R.id.login_suc, R.id.login_fail);

		}
		// ActivityHelp.startActivity(getApplicationContext(),
		// HomeActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK);
	}

	private boolean isNull() {
		if (TextUtils.isEmpty(requestUsername)) {
			Toast.makeText(LoginActivity.this, "用户名不能为空", 1000).show();
			return false;
		} else if (TextUtils.isEmpty(requestPass)) {
			Toast.makeText(LoginActivity.this, "密码不能为空", 1000).show();
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 提交的参数
	 */
	private Map<String, String> getParams() {

		// 在这里设置需要post的参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("op", "GetLogin");
		map.put("name", requestUsername);
		map.put("pwd", requestPass);
		Map<String, Object> mapp = new HashMap<String, Object>();
		mapp.put("op", "GetLogin");
		mapp.put("name", requestUsername);
		mapp.put("pwd", requestPass);
		String sign = MD5Utils.getMD5Sign(mapp);
		map.put("sign", sign);
		return map;
	}
	
	private Map<String, String> getParamsNew() {

		// 在这里设置需要post的参数
		Map<String, String> map = new HashMap<String, String>();
		map.put("op", "GetUsrInfo");
		map.put("mobile", requestUsername);
		map.put("password", requestPass);
		
		Map<String, Object> mapp = new HashMap<String, Object>();
		mapp.put("op", "GetUsrInfo");
		mapp.put("mobile", requestUsername);
		mapp.put("password", requestPass);
		String sign = MD5Utils.getMD5Sign(mapp);
//		map.put("sign", sign);
		return map;
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			dismissLoadingDialog();
			switch (msg.what) {
			case R.id.login_suc:
				String sucMsg = (String) msg.obj;
				if (JsonObjectImpl.isSuccess(LoginActivity.this, sucMsg)) {

					User user = JsonObjectImpl.parserUser(sucMsg);
					AppContext.user = user;
					// saveUsername(requestUsername);
					Editor edit = userInfo.edit();
					edit.putString("mProvinceId", user.getProvinceId());
					edit.putString("mCityId", user.getCityId() + "");
					edit.putString("name", user.getUserName());
					edit.putString("id", user.getId());
					edit.putString("explain", user.getExplain());
					edit.putString("email", user.getEmail());
					edit.putString("mobile", user.getMobile());
					edit.putString("mRegion",
							user.getProvinceName() + "  " + user.getCityName());
					edit.putString("password", requestPass);
					edit.putString("loginname", requestUsername);
					edit.commit();
					edit.clear();
					// 跳转到首页
					Intent intent = new Intent(LoginActivity.this,
							HomeActivity.class);
//					MainActivity.class);
					startActivity(intent);
					finish();
				}

				break;
			case R.id.login_fail:
				showMsgToast("登录失败，网络请求错误");
				break;
			/*
			 * case R.id.close_dialog: showError("无网络"); break;
			 */
			default:
				break;
			}
		}

	};

	/**
	 * 保存用户名、密码信息
	 */
	// private void saveUsername(String user) {
	// Editor edit = userInfo.edit();
	// edit.putString("mProvinceId", mProvinceId);
	// edit.putString("mCityId", mCityId);
	// edit.putString("mRegion", mRegion);
	// edit.commit();
	// edit.clear();
	// // userInfo.edit().putString("name", user).commit();
	// }
}
