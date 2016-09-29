package com.example.andtest.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
/**
 * @Description: 测试
 * @Package com.example.andtest.provider MyContentProvider.java
 * @author gf
 * @date 2015-11-19 下午2:46:29
 */
public class MyContentProvider extends ContentProvider {

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO 返回指定uri中的数据的MIME类型
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO 添加指定uri
		return null;
	}

	@Override
	public boolean onCreate() {
		// 创建ContentProvider时调用
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO 查询指定uri的数据
		
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
