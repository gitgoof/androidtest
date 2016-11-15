/**
 * Project Name:JZGPingGuShiWangLuo
 * File Name:BaseAdapter.java
 * Package Name:com.gc.jzgpgswl.adapter
 * Date:2014-11-29下午5:03:57
 * Copyright (c) 2014, wangyd523@gmail.com All Rights Reserved.
 *
 */

package com.jzg.jzgcarsource.carindex.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.jzg.jzgcarsource.activity.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * ClassName:BaseAd<br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014-11-29 下午5:03:57 <br/>
 * 
 * @author 汪渝栋
 * @version
 * @since JDK 1.6
 * @see
 */
public class MBaseAdapter extends BaseAdapter
{
	protected ImageLoader imageLoader = ImageLoader.getInstance();

	protected ImageLoadingListener mAnimateFirstListener = new AnimateFirstDisplayListener();

	protected DisplayImageOptions mOptions;

	public MBaseAdapter()
	{
		super();
		mOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.jingzhengu)
				.showImageForEmptyUri(R.drawable.jingzhengu)
				.showImageOnFail(R.drawable.jingzhengu).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(0)).build();
	}

	@Override
	public int getCount()
	{
		return 0;
	}

	@Override
	public Object getItem(int position)
	{
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		return null;
	}

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener
	{

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage)
		{
			if (loadedImage != null)
			{
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay)
				{
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

}
