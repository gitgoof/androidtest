package com.example.andtest.customview.bussiness;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;
/**
 * @Description: 自定义
 * @Package com.example.andtest.customview.bussiness MyScrollView.java
 * @author gf
 * @date 2016-5-20 下午3:00:13
 */
public class MyScrollView extends ScrollView {

	public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyScrollView(Context context) {
		super(context);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if(mOnScrollListener != null){
			mOnScrollListener.onScrollChanged(l, t, oldl, oldt);
		}
	}
	public interface OnScrollListener{
		void onScrollChanged(int l, int t, int oldl, int oldt);
	}
	private OnScrollListener mOnScrollListener;
	public void setOnScrollListener( OnScrollListener s){
		mOnScrollListener = s;
	}
	
	
}
