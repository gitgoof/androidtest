package com.jzg.jzgcarsource.application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.jzg.jzgcarsource.bean.User;
import com.jzg.jzgcarsource.carindex.vo.CarRelease;
import com.jzg.jzgcarsource.carindex.vo.HotCar;
import com.jzg.jzgcarsource.carindex.vo.Make;
import com.jzg.jzgcarsource.carindex.vo.ModelCategory;
import com.jzg.jzgcarsource.carindex.vo.Style;
import com.jzg.jzgcarsource.carindex.vo.StyleCategory;
import com.jzg.jzgcarsource.exception.CrashHandler;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * ClassName:AppContext <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014-9-1 上午10:18:25 <br/>
 * @see
 */
@TargetApi(Build.VERSION_CODES.FROYO)
public class AppContext extends Application {

	private static RequestQueue mVolleyQueue;

	// Default maximum disk usage in bytes
	private static final int DEFAULT_DISK_USAGE_BYTES = 25 * 1024 * 1024;

	// Default cache folder name
	private static final String DEFAULT_CACHE_DIR = "cache_directory";
	public static Context mContext;

	// 是否刷新车辆查询列表
	public static boolean cars_is_refresh = false;
	public static User user;

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		AppContext.user = user;
	}

	/**
	 * App最新版本号
	 */
	public static String appVersion = "";

	/**
	 * App 是否有更新
	 */
	public static boolean appHasUpdate = false;

	public static String mMileage = "";

	public static String getmMileage() {
		return mMileage;
	}

	public static void setmMileage(String mMileage) {
		AppContext.mMileage = mMileage;
	}

	/**
	 * 估价模块车辆信息索引后，最终车型
	 */
	// public static JzgCarChooseStyle mQueryCarStyle = null;

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
		// 初始化imageload库
		initImageLoader(getApplicationContext());
		// 初始化volley请求队列
		mVolleyQueue = newParallelRequestQueue(this);

		initCrashHandler();

	}

	/**
	 * 初始化日志
	 */
	private void initCrashHandler() {
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(this);
	}

	public static RequestQueue newParallelRequestQueue(Context context) {
		// define cache folder
		File rootCache = context.getExternalCacheDir();
		if (rootCache == null) {
			Log.i("AppContext", "Can't find External Cache Dir, "

			+ "switching to application specific cache directory");
			rootCache = context.getCacheDir();
		}

		File cacheDir = new File(rootCache, DEFAULT_CACHE_DIR);
		cacheDir.mkdirs();

		HttpStack stack = new HurlStack();
		Network network = new BasicNetwork(stack);
		DiskBasedCache diskBasedCache = new DiskBasedCache(cacheDir,
				DEFAULT_DISK_USAGE_BYTES);
		RequestQueue queue = new RequestQueue(diskBasedCache, network);
		queue.start();

		return queue;
	}

	public static RequestQueue getRequestQueue() {
		return mVolleyQueue;
	}

	/**
	 * 判断sd卡是否存在 isSdcardExisting: <br/>
	 * 
	 * @author wang
	 * @return
	 * @since JDK 1.6
	 */
	public static boolean isSdcardExisting() {
		final String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 初始化ImageLoader initImageLoader: 使用imageload库必须加<br/>
	 * 
	 * @author wang
	 * @param context
	 * @since JDK 1.6
	 */
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

	/**
	 * 检测网络是否可用
	 * 
	 * @return
	 */
	public static boolean isNetworkConnected() {
		if (mContext == null) {
			return false;
		}
		ConnectivityManager cm = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	public static String getAppVersion() {
		return appVersion;
	}

	public static void setAppVersion(String appVersion) {
		AppContext.appVersion = appVersion;
	}

	public static boolean isAppHasUpdate() {
		return appHasUpdate;
	}

	public static void setAppHasUpdate(boolean appHasUpdate) {
		AppContext.appHasUpdate = appHasUpdate;
	}

	/**
	 * 历史热门推荐数据
	 */
	public static List<HotCar> mHistoryHotCars = new ArrayList<HotCar>();
	/**
	 * 热门品牌历史列表被点击的位置（用于品牌列表字体颜色改变）
	 */
	public static int mHotCarsHistoryPosition = -1;

	/**
	 * 历史车型json解析数据封装
	 */
	public static ArrayList<StyleCategory> mHistoryCarStyles = new ArrayList<StyleCategory>();

	/**
	 * 历史品牌列表所有数据
	 */
	public static ArrayList<Make> mHistoryMakes = new ArrayList<Make>();

	/**
	 * 历史解析后车系json数据封装
	 */
	public static ArrayList<ModelCategory> mHistoryModelCategorys = new ArrayList<ModelCategory>();

	/**
	 * 车系历史列表被点击的位置（用于车系列表字体颜色改变）
	 */
	public static int mModelHistoryPosition = -1;

	/**
	 * 车型历史列表被点击的位置（用于车系列表字体颜色改变）
	 */
	public static int mStyleHistoryPosition = -1;

	/**
	 * 品牌历史列表被点击的位置（用于品牌列表字体颜色改变）
	 */
	public static int mMakeHistoryPosition = -1;
	/**
	 * 估价模块车辆信息索引后，最终车的年款
	 */
	public static int mQueryCarYear = -1;

	/**
	 * 发车模块车型信息索引后，最终车的年款
	 */
	public static int mCarReleaseYear = -1;
	/**
	 * 发车模块车型信息索引后，最终车的年款
	 */
	public static Style mCarReleaseStyle = null;

	/**
	 * 估价模块车辆信息索引后，最终车型
	 */
	public static Style mQueryCarStyle = null;

	/**
	 * 发车实体
	 */
	private CarRelease carRelease;

	public CarRelease getCarRelease() {
		if (carRelease == null) {
			carRelease = new CarRelease();
		}
		return carRelease;
	}

}
