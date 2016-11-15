package com.jzg.jzgcarsource.activity;

import net.simonvt.numberpicker.NumberPicker;
import net.simonvt.numberpicker.NumberPicker.OnValueChangeListener;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * 
 * 
 * @author 作者 E-mail:
 * @version 创建时间：
 */
@SuppressLint("NewApi")
public class SheetTimeAty extends BaseActivity implements OnClickListener
{

	int win_width;
	int win_height;
	NumberPicker yearNumberPicker;
	NumberPicker monthNumberPicker;
	private TextView clear;
	private TextView tv;
	private int maxYear;
	private int minYear;

	private int maxMonth = 12;
	private int minMonth = 1;
	private int maxYearMonth = 12;
	private int minYearMonth = 1;

	private int currentYear;
	private int currentMonth;
	private int currentMAXMonth;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 隐藏标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setFinishOnTouchOutside(true);

		setContentView(R.layout.aty_sheettime);

		yearNumberPicker = (NumberPicker) findViewById(R.id.yearNumberPicker);
		monthNumberPicker = (NumberPicker) findViewById(R.id.monthNumberPicker);

		tv = (TextView) findViewById(R.id.title);
		clear = (TextView) findViewById(R.id.time_clear);
		clear.setOnClickListener(this);
		TextView done = (TextView) findViewById(R.id.time_done);
		done.setOnClickListener(this);

		WindowManager m = getWindowManager();
		Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
		WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
		Point size = new Point();
		d.getSize(size);
		win_width = size.x;
		win_height = size.y;
		p.gravity = Gravity.BOTTOM;
		p.height = (int) (win_height * 0.5); // 高度设置为屏幕的0.6
		p.width = (int) (win_width * 1); // 宽度设置为屏幕的1
		p.alpha = 1.0f;// 设置透明度

		maxYear = getIntent().getIntExtra("Maxyear", 2016);
		minYear = getIntent().getIntExtra("Minyear", 2016);
		maxYearMonth = getIntent().getIntExtra("MaxMonth", 12);
		minYearMonth = getIntent().getIntExtra("MinMonth", 1);
		currentMAXMonth = getIntent().getIntExtra("CurMonth", 1);
		tv.setText(getIntent().getStringExtra("title"));
		if (getIntent().getBooleanExtra("isClear", false))
		{
			clear.setVisibility(View.GONE);
		}
		this.getWindow().setAttributes(p);

		currentYear = maxYear; // 默认显示最大年限
		currentMonth = maxYearMonth;
		yearNumberPicker.setMaxValue(maxYear);
		yearNumberPicker.setMinValue(minYear);
		yearNumberPicker.setValue(currentYear);

		monthNumberPicker.setMaxValue(maxYearMonth);
		monthNumberPicker.setMinValue(minMonth);
		monthNumberPicker.setValue(currentMonth);

		yearNumberPicker
				.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		monthNumberPicker
				.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

		yearNumberPicker.setOnValueChangedListener(new OnValueChangeListener()
		{

			@Override
			public void onValueChange(NumberPicker picker, int oldVal,
					int newVal)
			{
				int year = picker.getValue();
				currentYear = year;

				updateMonth();
			}
		});
		monthNumberPicker.setOnValueChangedListener(new OnValueChangeListener()
		{

			@Override
			public void onValueChange(NumberPicker picker, int oldVal,
					int newVal)
			{
				int month = picker.getValue();
				currentMonth = month;
			}
		});

		updateMonth();

	}

	public void updateMonth()
	{
		if (yearNumberPicker.getValue() == maxYear)
		{
			/*
			 * if(StringUtil.getMonth() !=1){
			 * monthNumberPicker.setMaxValue(StringUtil.getMonth()-1); }else{
			 * yearNumberPicker.setMaxValue(maxYear-1);
			 * monthNumberPicker.setMaxValue(12); }
			 */
			monthNumberPicker.setMaxValue(currentMAXMonth - 1 == 0 ? 1
					: currentMAXMonth - 1);
			monthNumberPicker.setMinValue(minMonth);

		} else if (yearNumberPicker.getValue() == minYear)
		{
			monthNumberPicker.setMaxValue(maxMonth);
			monthNumberPicker.setMinValue(minYearMonth);
		} else
		{
			monthNumberPicker.setMaxValue(maxMonth);
			monthNumberPicker.setMinValue(minMonth);
		}
	}

	// public void time_done(View v){
	// back_data();
	// this.finish();
	// }
	// public void time_clear(View v){
	// clear_data();
	// this.finish();
	// }

	private void clear_data()
	{
		Intent in = new Intent();
		in.putExtra("year", 0);
		in.putExtra("month", 0);
		setResult(1234, in);

	}

	public void back_data()
	{
		Intent in = new Intent();
		in.putExtra("year", yearNumberPicker.getValue());
		in.putExtra("month", monthNumberPicker.getValue());
		setResult(1234, in);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId()) {
		case R.id.time_clear:
			clear_data();
			this.finish();
			break;
		case R.id.time_done:
			back_data();
			this.finish();
			break;

		default:
			break;
		}

	}

}
