package com.jzg.jzgcarsource.exception;

import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class CrashHandler implements UncaughtExceptionHandler {

	public static final String TAG = "CrashHandler";
	private static CrashHandler INSTANCE = new CrashHandler();
	private Context mContext;
	private Thread.UncaughtExceptionHandler mDefaultHandler;

	// Logger logger = Logger.getLogger(CrashHandler.class);

	private CrashHandler() {
	}

	public static CrashHandler getInstance() {
		return INSTANCE;
	}

	public void init(Context ctx) {
		mContext = ctx;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, final Throwable ex) {
		Log.i(TAG, "app UncaughtExceptionHandler");

		// 下面这部分代码必须和Activity绑定,否则AlertDialog将不会提示
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();

				Toast.makeText(mContext, ex.getMessage(), Toast.LENGTH_LONG)
						.show();

				System.exit(0);

				Looper.loop();
			}
		}.start();
		// 记录log信息
		// logger.debug(ex.getStackTrace());
	}

}