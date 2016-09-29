package com.example.andtest.bindtest;

import butterknife.ButterKnife;

import com.example.andtest.R;

import android.app.Activity;
import android.os.Bundle;
// 测试
public class MyBindActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bind_test_layout);
		ButterKnife.bind(this);
		
		
		
	}
}
