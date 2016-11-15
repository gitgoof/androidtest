package com.jzg.jzgcarsource.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

/**
 * @Description: 主界面
 * @Package com.jzg.jzgcarsource.activity HomeActivity.java
 * @author gf
 * @date 2016-10-31 下午2:43:50
 */
public class HomeActivity extends Activity {
	private LocalActivityManager mLocalActivityManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		mTabHost = (TabHost) findViewById(R.id.tabhost_home_content);
		mLocalActivityManager = new LocalActivityManager(this, true);
		mLocalActivityManager.dispatchCreate(savedInstanceState);
		mLocalActivityManager.dispatchResume();
		mTabHost.setup(mLocalActivityManager);
		TabSpec tabOneSpec = mTabHost.newTabSpec("one").setContent(new Intent(this,AdjustPriceActivity.class));
		tabOneSpec.setIndicator(initTabSpeceView("调价"));
		mTabHost.addTab(tabOneSpec);
		
		TabSpec tabTwoSpec = mTabHost.newTabSpec("two").setContent(new Intent(this,MainActivity.class));
		tabTwoSpec.setIndicator(initTabSpeceView("收数"));
		mTabHost.addTab(tabTwoSpec);
		
		TabSpec tabThreeSpec = mTabHost.newTabSpec("three").setContent(new Intent(this,PersonActivity.class));
		tabThreeSpec.setIndicator(initTabSpeceView("个人中心"));
		mTabHost.addTab(tabThreeSpec);
		mTabHost.setCurrentTab(0);
		
		initTabHost();
	}
	@Override
	protected void onResume() {
		super.onResume();
	}
	public void toInitPersonCenter(){
		if(null != mLocalActivityManager){
			final Activity act = mLocalActivityManager.getActivity("three");
			if(null != act)
			((PersonActivity)act).initPerson();
		}
	}
	private TabHost mTabHost;
	
	@SuppressLint("NewApi")
	private View initTabSpeceView(String title){
		
		LinearLayout linearLayout = new LinearLayout(this);
		LinearLayout.LayoutParams lP = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
		
		lP.weight = 1f;
		linearLayout.setLayoutParams(lP);
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);
		linearLayout.setBackgroundResource(android.R.color.white);
		linearLayout.setGravity(Gravity.CENTER);
		linearLayout.setPadding(0, 5, 0, 5);
		TextView tvOne = new TextView(this);
		tvOne.setText(title);
		tvOne.setTextSize(14);
		if("调价".equals(title)){
			tvOne.setTextColor(getResources().getColor(R.color.blue));
			Drawable dra = getDrawableCheck(R.drawable.bottom_tj);
//			Drawable dra = getResources().getDrawable(R.drawable.bottom_tj, null);
			dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
			tvOne.setCompoundDrawables(null, dra, null, null);
		} else if ("收数".equals(title)){
			tvOne.setTextColor(getResources().getColor(R.color.grey3));
			Drawable dra = getDrawableCheck(R.drawable.bottom_sc);
//			Drawable dra = getResources().getDrawable(R.drawable.bottom_sc, null);
			dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
			tvOne.setCompoundDrawables(null, dra, null, null);
		} else if ("个人中心".equals(title)){
			tvOne.setTextColor(getResources().getColor(R.color.grey3));
			Drawable dra = getDrawableCheck(R.drawable.bottom_grzx);
//			Drawable dra = getResources().getDrawable(R.drawable.bottom_grzx, null);
			dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
			tvOne.setCompoundDrawables(null, dra, null, null);
		}
		tvOne.setCompoundDrawablePadding(5);
		
		linearLayout.addView(tvOne);
		return linearLayout;
	}
	
	private Drawable getDrawableCheck(final int id){
		final int version = Build.VERSION.SDK_INT;
		if(version<21){
			return getResources().getDrawable(id);
		}
		return getResources().getDrawable(id, null);
	}
	
	private void initTabHost(){
		final TabWidget tabWid = (TabWidget) findViewById(android.R.id.tabs);
		mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				int current = 0;
				if("one".equals(tabId)){
					current = 0;
				} else if ("two".equals(tabId)){
					current = 1;
				} else if ("three".equals(tabId)){
					current = 2;
				}
				for(int i = 0;i < tabWid.getChildCount();i++){
					View view = tabWid.getChildAt(i);
					if(current == i){
						view.setSelected(true);
						if(view instanceof LinearLayout){
							final LinearLayout ll = (LinearLayout) view;
							if(ll.getChildCount()>=1){
								ll.getChildAt(0).setSelected(true);
								View chi = ll.getChildAt(0);
								if(chi instanceof TextView){
									((TextView) chi).setTextColor(getResources().getColor(R.color.blue));
								}
							}
						}
					} else {
						view.setSelected(false);
						if(view instanceof LinearLayout){
							final LinearLayout ll = (LinearLayout) view;
							if(ll.getChildCount()>=1){
								ll.getChildAt(0).setSelected(false);
								View chi = ll.getChildAt(0);
								if(chi instanceof TextView){
									((TextView) chi).setTextColor(getResources().getColor(R.color.grey3));
								}
							}
						}
					}
				}
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(null != mLocalActivityManager){
			final Activity activity = mLocalActivityManager.getCurrentActivity();
			if(activity instanceof MainActivity){
				((MainActivity)activity).onActivityResult(requestCode, resultCode, data);
			} else if (activity instanceof MainActivity){
				((MainActivity)activity).onActivityResult(requestCode, resultCode, data);
			} else if (activity instanceof PersonActivity){
				((PersonActivity)activity).onActivityResult(requestCode, resultCode, data);
			}
		}
		
	}
}
