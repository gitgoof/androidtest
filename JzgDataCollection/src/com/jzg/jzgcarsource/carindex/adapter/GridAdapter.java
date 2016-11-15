/**
 * Project Name:JZGPingGuShi
 * File Name:GridAdapter.java
 * Package Name:com.gc.jzgpinggushi.adapter
 * Date:2014-9-4上午9:55:40
 * Copyright (c) 2014, wangyd523@gmail.com All Rights Reserved.
 *
 */

package com.jzg.jzgcarsource.carindex.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jzg.jzgcarsource.activity.R;
import com.jzg.jzgcarsource.carindex.vo.HotCar;

/**
 * ClassName:GridAdapter <br/>
 * Function: 热门推荐GridView适配器. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014-9-4 上午9:55:40 <br/>
 * 
 * @author 汪渝栋
 * @version
 * @since JDK 1.6
 * @see
 */
public class GridAdapter extends MBaseAdapter
{
	private Context mContext;

	private List<HotCar> mList;

	public GridAdapter(Context context, List<HotCar> hotCars)
	{
		super();
		this.mContext = context;
		this.mList = hotCars;
	}

	@Override
	public int getCount()
	{
		return mList.size();
	}

	@Override
	public Object getItem(int position)
	{
		return position;
	}

	@Override
	public long getItemId(int position)
	{
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.grid_item, null);
			final ImageView img = (ImageView) convertView
					.findViewById(R.id.ItemImage);
			img.setTag(mList.get(position));

			imageLoader.displayImage(mList.get(position).getMakeLogo(), img,
					mOptions, mAnimateFirstListener);
			// img.setImageResource(mList.get(position));
			TextView text = (TextView) convertView.findViewById(R.id.ItemText);
			text.setText(mList.get(position).getMakeName());
			return convertView;
		} else
		{
			return convertView;
		}
	}

}
