package com.jzg.jzgcarsource.activity;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.jzg.jzgcarsource.application.AppContext;
import com.jzg.jzgcarsource.application.AppManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * ClassName:BaseActivity <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014-9-3 下午12:09:28 <br/>
 * 
 * @author 汪渝栋
 * @version
 * @since JDK 1.6
 * @see
 */
public class BaseActivity extends FragmentActivity {

	public AppContext appContext;

	protected SharedPreferences configPres;

	public static final int SUCCESS = 100;// 成功

	public static final int AUDIT = 101;// 审核

	public static final int COMPLETE = 106;// 资料是否完整

	protected ImageLoader imageLoader = ImageLoader.getInstance();

	protected ImageLoadingListener mAnimateFirstListener = new AnimateFirstDisplayListener();

	protected DisplayImageOptions mOptions;

	/**
	 * 弹出窗口管理器
	 */
	protected DialogManager mDialogManager;

	/**
	 * 加载中提示框
	 */
	protected Dialog mDialog;

	protected SharedPreferences userInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		appContext = (AppContext) getApplicationContext();

		configPres = getPreferences(MODE_PRIVATE);

		userInfo = getSharedPreferences("user_info", 0);

		mOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.no_car)
				.showImageForEmptyUri(R.drawable.no_car)
				.showImageOnFail(R.drawable.no_car2).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(0))
				.displayer(new SimpleBitmapDisplayer()).build();

		mDialogManager = new DialogManager();

		// 添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 结束Activity&从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
	}

	/**
	 * 初始化界面元素 init: <br/>
	 * 
	 * @author wang
	 * @since JDK 1.6
	 */
	public void init() {
	}

	/**
	 * 验证界面的文本元素是否为空 checkText: <br/>
	 * 
	 * @author wang
	 * @return
	 * @since JDK 1.6
	 */
	public boolean checkText() {
		return true;
	}

	public static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}

	}

	/**
	 * 弹出Toast提示。
	 * 
	 * @param msg
	 *            需要显示的文字
	 */
	public void showMsgToast(String msg) {
		if (msg == null)
			msg = "";
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 通过id获取资源文件中的字符串信息
	 * 
	 * @param id
	 * @return
	 */
	public String getStringById(int id) {
		if (id <= 0)
			return "";
		return this.getResources().getString(id);
	}

	/**
	 * 显示异常对话框
	 * 
	 * @param message
	 */
	public void showExceptionDialog(Activity activity, final Dialog d,
			String message) {
		new AlertDialog.Builder(activity).setTitle("提示").setCancelable(false)
				.setMessage(message)
				.setNeutralButton("我知道了", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Log.i(TAG, "exit");
						// System.exit(0);
						if (d != null && d.isShowing())
							d.dismiss();
					}
				}).create().show();
	}

	public interface RequestExceptionListener {
		void showMessageDialog(String message);
	}

	public void toShowLoadingDialog() {
		if (mDialog != null && mDialog.isShowing())
			return;
		mDialog = mDialogManager.show(this, this, R.drawable.bg_loading);
	}

	public void dismissLoadingDialog() {
		if (mDialog != null && mDialog.isShowing())
			mDialog.dismiss();
	}

}
