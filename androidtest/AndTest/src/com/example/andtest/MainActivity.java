package com.example.andtest;

import java.net.HttpURLConnection;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andtest.customview.bussiness.MyScrollView;
import com.example.andtest.model.business.TestModel;
import com.google.gson.Gson;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		HttpURLConnection http;
		
//		FileProvider.getUriForFile(this, "", new File(""));
//		getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
		Cursor cusor = null;
//		cusor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
//		cusor.getColumnIndex(OpenableColumns.SIZE);
		/*
		int persission = checkCallingOrSelfPermission("");
		// 判断是否有某个权限，如果有返回这个。
		if(persission == PackageManager.PERMISSION_GRANTED ){
			// 不具有这个权限
		} else if(persission == PackageManager.PERMISSION_DENIED ){
			
		}
		*/
		
		mScrollView = (MyScrollView) findViewById(R.id.scroll_self_test);
		mLinearScroll = (LinearLayout) findViewById(R.id.linear_scroll_child);
		
		mScrollView.setOnScrollListener(new MyScrollView.OnScrollListener() {
			@SuppressLint("NewApi")
			@Override
			public void onScrollChanged(int l, int t, int oldl, int oldt) {
				Log.i("gf", "<l>" + l + "<t>" + t + "<oldl>" + oldl + "<oldt>" + oldt);
				if(mTv != null){
					Log.i("gf", "------<top>" + mTv.getScrollX() + "<bottom>" + mTv.getScrollY() + "<>");
				}
			}
		});
		
		for(int i = 0;i<40;i++){
			TextView tv = new TextView(this);
			if(i == 2){
				mTv = tv;
			}
			tv.setText("-----" + i);
			tv.setTextSize(19);
			mLinearScroll.addView(tv);
		}
		String versionName = null;
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(),0).versionName;
            Log.i("gf", "v" + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        
        
        Uri uri;
        Uri.parse("");
        
        String callString = Intent.ACTION_CALL;
        
//        getIntent().getData();
        /*
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        .detectAll().penaltyLog().build());
        
        
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
        .detectAll().penaltyLog().build());
        */
//        ContextCompat.checkSelfPermission(this, permission);// 检查权限
//        ActivityCompat.requestPermissions(arg0, arg1, arg2);// 请求权限
        /*
        AnimationSet set = new AnimationSet(true);
        Animation ani = new TranslateAnimation(0, 0, 200, 200);
        set.addAnimation(ani);
        mTv.startAnimation(set);
        */
        
        mTvTopShow = (TextView) findViewById(R.id.tv_top_show);
        
	}
	private TextView mTvTopShow;
	private TextView mTv;
	private MyScrollView mScrollView;
	private LinearLayout mLinearScroll;
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event){
		return super.dispatchKeyEvent(event);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	class MScroll extends ScrollView{

		public MScroll(Context context, AttributeSet attrs, int defStyleAttr) {
			super(context, attrs, defStyleAttr);
		}

		public MScroll(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public MScroll(Context context) {
			super(context);
		}
		@Override
		protected void onScrollChanged(int l, int t, int oldl, int oldt) {
			super.onScrollChanged(l, t, oldl, oldt);
			
			
		}
	}
	public void toJson(View view){
//		initModel();
		toShowAnimaiton(view);
	}
	private void toShowAnimaiton(final View view){
        
		AnimationSet set = new AnimationSet(true);
        Animation ani = new TranslateAnimation(0, 400, 0, 700);
        ani.setDuration(1000);
        set.addAnimation(ani);
        view.startAnimation(set);
        
        set.setAnimationListener(new Animation.AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				Animation ani2 = new TranslateAnimation(400, 0, 700, 200);
		        ani2.setDuration(2000);
		        view.setAnimation(ani2);
			}
		});
	}
	private void initModel(){
		final String str = getResources().getString(R.string.json);
		Gson gson = new Gson();
		TestModel testModel = gson.fromJson(str, TestModel.class);
		
		Log.i("gf", "<success>>" + testModel.isSuccess() + "<status>" + testModel.getStatus());
	}
	
	public void register_detail_layout(View view){
		switch (view.getId()) {
		case R.id.tv_register_detail_sell_qudao_choose:
			Toast.makeText(this, "-----choose 选择点击", Toast.LENGTH_SHORT).show();
			break;
		case R.id.register_detail_sell_qudao_text_show:
			Toast.makeText(this, "-----展示点击", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
}
