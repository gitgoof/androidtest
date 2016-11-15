/**
 * Project Name:JZGPingGuShi
 * File Name:JsonObjectImpl.java
 * Package Name:com.gc.jzgpinggushi.json
 * Date:2014-9-1上午10:52:01
 * Copyright (c) 2014, wangyd523@gmail.com All Rights Reserved.
 *
 */

package com.jzg.jzgcarsource.utils;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jzg.jzgcarsource.bean.BaseObject;
import com.jzg.jzgcarsource.bean.City;
import com.jzg.jzgcarsource.bean.CityList;
import com.jzg.jzgcarsource.bean.Province;
import com.jzg.jzgcarsource.bean.ProvinceList;
import com.jzg.jzgcarsource.bean.User;
import com.jzg.jzgcarsource.carindex.vo.HotCar;
import com.jzg.jzgcarsource.carindex.vo.HotCarList;
import com.jzg.jzgcarsource.carindex.vo.Make;
import com.jzg.jzgcarsource.carindex.vo.MakeList;
import com.jzg.jzgcarsource.carindex.vo.Model;
import com.jzg.jzgcarsource.carindex.vo.ModelCategory;
import com.jzg.jzgcarsource.carindex.vo.ModelList;
import com.jzg.jzgcarsource.carindex.vo.Style;
import com.jzg.jzgcarsource.carindex.vo.StyleCategory;
import com.jzg.jzgcarsource.carindex.vo.StyleList;

/**
 * ClassName:JsonObjectImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014-9-1 上午10:52:01 <br/>
 */
@SuppressLint("ResourceAsColor")
public class JsonObjectImpl implements JsonObject {
	private JSONObject jsonObject;

	private int SUCCESS = 100;

	public HotCarList parserHotCarList(String result) {
		HotCarList hotCarList = new HotCarList();
		ArrayList<HotCar> hotList = new ArrayList<HotCar>();
		HotCar hotCar = null;
		try {
			jsonObject = new JSONObject(result);
			int status = getresult(jsonObject);
			String msg = jsonObject.getString("msg");
			// 成功
			if (SUCCESS == status) {
				JSONArray hotlists = jsonObject.getJSONArray("MakeList");
				// System.out
				// .println("makelists.length() is " + hotlists.length());
				for (int i = 0; i < hotlists.length(); i++) {
					hotCar = new HotCar();
					hotCar.setMakeId(hotlists.getJSONObject(i).getInt("MakeId"));
					hotCar.setMakeLogo(hotlists.getJSONObject(i).getString(
							"MakeLogo"));
					hotCar.setMakeName(hotlists.getJSONObject(i).getString(
							"MakeName"));
					hotList.add(hotCar);
				}
			}
			hotCarList.setStatus(status);
			hotCarList.setMsg(msg);
			hotCarList.setHotCars(hotList);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return hotCarList;
	}

	public MakeList parserMakeList(String jsonData) {
		MakeList makes = new MakeList();
		ArrayList<Make> makeList = new ArrayList<Make>();
		Make make = null;
		try {
			jsonObject = new JSONObject(jsonData);
			int status = getresult(jsonObject);
			String msg = jsonObject.getString("msg");
			// 成功
			if (SUCCESS == status) {
				JSONArray makelists = jsonObject.getJSONArray("MakeList");
				// System.out.println("makelists.length() is "
				// + makelists.length());
				for (int i = 0; i < makelists.length(); i++) {
					make = new Make();
					make.setMakeId(makelists.getJSONObject(i).getInt("MakeId"));
					make.setMakeLogo(makelists.getJSONObject(i).getString(
							"MakeLogo"));
					make.setGroupName(makelists.getJSONObject(i).getString(
							"GroupName"));
					make.setMakeName(makelists.getJSONObject(i).getString(
							"MakeName"));
					make.setFontColor(Color.rgb(51, 51, 51));
					make.setItemColor(Color.WHITE);
					makeList.add(make);
				}
			}
			makes.setStatus(status);
			makes.setMsg(msg);
			makes.setMakes(makeList);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return makes;
	}

	public ModelList parserModelList(String jsonData) {
		ModelList models = new ModelList();
		ArrayList<ModelCategory> modelCategoryList = new ArrayList<ModelCategory>();
		ModelCategory modelCategory = null;
		Model model = null;
		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			int status = getresult(jsonObject);
			String msg = jsonObject.getString("msg");
			if (SUCCESS == status) {
				JSONArray manufacturerList = jsonObject
						.getJSONArray("ManufacturerList");
				// System.out.println("manufacturerList.length() is "
				// + manufacturerList.length());
				for (int i = 0; i < manufacturerList.length(); i++) {
					JSONObject object = manufacturerList.getJSONObject(i);
					modelCategory = new ModelCategory(
							object.getString("ManufacturerName"));
					JSONArray modellists = object.getJSONArray("ModelList");
					for (int j = 0; j < modellists.length(); j++) {
						JSONObject modelObj = modellists.getJSONObject(j);
						model = new Model();
						model.setId(modelObj.getInt("Id"));
						model.setName(modelObj.getString("Name"));
						model.setFontColor(Color.rgb(51, 51, 51));
						model.setItemColor(Color.WHITE);
						modelCategory.addItem(model);
					}
					modelCategoryList.add(modelCategory);
				}

				models.setModels(modelCategoryList);
			}
			models.setStatus(status);
			models.setMsg(msg);
		} catch (JSONException e) {

			e.printStackTrace();

		}
		return models;
	}

	public StyleList parserStyleList(String jsonData) {
		StyleList styles = new StyleList();
		ArrayList<StyleCategory> styleCategoryList = new ArrayList<StyleCategory>();
		StyleCategory styleCategory = null;
		Style style = null;
		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			int status = getresult(jsonObject);
			String msg = jsonObject.getString("msg");
			if (SUCCESS == status) {
				JSONArray yearGroupList = jsonObject
						.getJSONArray("YearGroupList");
				// System.out.println("yearGroupList.length() is "
				// + yearGroupList.length());
				for (int i = 0; i < yearGroupList.length(); i++) {
					JSONObject object = yearGroupList.getJSONObject(i);
					styleCategory = new StyleCategory(object.getString("Year"));
					JSONArray modellists = object.getJSONArray("StyleList");
					for (int j = 0; j < modellists.length(); j++) {
						JSONObject modelObj = modellists.getJSONObject(j);
						style = new Style();
						style.setId(modelObj.getInt("Id"));
						style.setName(modelObj.getString("Name"));
						style.setYear(modelObj.getInt("Year"));
						style.setNowMsrp(modelObj.getString("NowMsrp"));
						style.setFullName(modelObj.getString("FullName"));
						style.setFontColor(Color.rgb(51, 51, 51));
						style.setItemColor(Color.WHITE);
						style.setMaxYEAR(modelObj.getString("MaxYEAR"));
						style.setMinYEAR(modelObj.getString("MinYEAR"));

						styleCategory.addItem(style);
					}
					styleCategoryList.add(styleCategory);
				}

				styles.setCarStyles(styleCategoryList);
			}
			styles.setStatus(status);
			styles.setMsg(msg);
		} catch (JSONException e) {

			e.printStackTrace();

		}
		return styles;
	}

	@Override
	public String generateJson(Serializable object) {
		return null;
	}

	private int getresult(JSONObject jsonObject) throws JSONException {
		return jsonObject.getInt("status");
	}

	public static User parserUser(String result) {
		User user = null;
		Gson gson = new Gson();
		user = (User) gson.fromJson(result, User.class);
		return user;
	}

	@Override
	public ProvinceList parserProvinceList(String jsonData) {

		ProvinceList provinces = new ProvinceList();
		ArrayList<Province> provinceList = new ArrayList<Province>();
		Province province = null;
		try {
			jsonObject = new JSONObject(jsonData);
			int status = getresult(jsonObject);
			String msg = jsonObject.getString("msg");
			// 成功
			if (SUCCESS == status) {
				JSONArray provincelists = jsonObject.getJSONArray("content");
				// System.out.println("provincelists.length() is "
				// + provincelists.length());
				for (int i = 0; i < provincelists.length(); i++) {
					JSONObject object = provincelists.getJSONObject(i);
					province = new Province();
					province.setId(object.getInt("Id"));
					province.setName(object.getString("Name"));
					provinceList.add(province);
				}
			}
			provinces.setStatus(status);
			provinces.setMsg(msg);
			provinces.setProvinces(provinceList);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return provinces;
	}

	@Override
	public CityList parserCityList(String jsonData) {

		CityList citys = new CityList();
		ArrayList<City> cityList = new ArrayList<City>();
		City city = null;
		try {
			jsonObject = new JSONObject(jsonData);
			int status = getresult(jsonObject);
			String msg = jsonObject.getString("msg");
			// 成功
			if (SUCCESS == status) {
				JSONArray citylists = jsonObject.getJSONArray("content");
				// System.out.println("provincelists.length() is "
				// + citylists.length());
				for (int i = 0; i < citylists.length(); i++) {
					JSONObject object = citylists.getJSONObject(i);
					city = new City();
					city.setId(object.getInt("Id"));
					city.setName(object.getString("Name"));
					cityList.add(city);
				}
			}
			citys.setStatus(status);
			citys.setMsg(msg);
			citys.setCitys(cityList);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return citys;
	}

	public static boolean isSuccess(Context context, String resu) {
		boolean isSuccess = false;
		try {
			JSONObject jsonObject = new JSONObject(resu);
			int status = jsonObject.getInt("status");
			if (status == 100) {
				isSuccess = true;
			} else {
//				Toast.makeText(context, jsonObject.getString("msg"),
//						Toast.LENGTH_SHORT).show();
				isSuccess = false;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isSuccess = false;
		}

		return isSuccess;
	}
	public static void isShowMsg(Context context, String resu){
		try {
			JSONObject jsonObject = new JSONObject(resu);
			int status = jsonObject.getInt("status");
			if (status == 100) {
			} else {
				Toast.makeText(context, jsonObject.getString("msg"),
						Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取状态码
	 * 
	 * @param result
	 * @return
	 */
	public BaseObject getMsgStatus(String jsonData) {

		BaseObject result = null;

		if (!TextUtils.isEmpty(jsonData)) {
			Gson gson = new Gson();
			result = gson.fromJson(jsonData, BaseObject.class);
		}
		return result;
	}

	//
	// public JzgCarChooseModelList parserModelList(String jsonData) {
	// JzgCarChooseModelList models = new JzgCarChooseModelList();
	// ArrayList<JzgCarChooseModelCategory> modelCategoryList = new
	// ArrayList<JzgCarChooseModelCategory>();
	// JzgCarChooseModelCategory modelCategory = null;
	// JzgCarChooseModel model = null;
	// try {
	// JSONObject jsonObject = new JSONObject(jsonData);
	// boolean status = getResult(jsonObject);
	// String msg = jsonObject.getString("msg");
	// if (status) {
	// JSONArray manufacturerList = jsonObject
	// .getJSONArray("ManufacturerList");
	// for (int i = 0; i < manufacturerList.length(); i++) {
	// JSONObject object = manufacturerList.getJSONObject(i);
	// modelCategory = new JzgCarChooseModelCategory(
	// object.getString("ManufacturerName"));
	//
	// JSONArray modellists = object.getJSONArray("ModelList");
	// for (int j = 0; j < modellists.length(); j++) {
	// JSONObject modelObj = modellists.getJSONObject(j);
	// model = new JzgCarChooseModel();
	// model.setId(modelObj.getInt("Id"));
	// model.setName(modelObj.getString("Name"));
	// model.setModelimgpath(modelObj.getString("modelimgpath"));
	// model.setFontColor(Color.rgb(51, 51, 51));
	// model.setItemColor(Color.WHITE);
	// modelCategory.addItem(model);
	// }
	// modelCategoryList.add(modelCategory);
	// }
	//
	// models.setModels(modelCategoryList);
	// }
	// models.setSuccess(status);
	// models.setMsg(msg);
	// } catch (JSONException e) {
	//
	// e.printStackTrace();
	// }
	// return models;
	// }
	//
	// public JzgCarChooseStyleList parserStyleList(String jsonData) {
	// JzgCarChooseStyleList styles = new JzgCarChooseStyleList();
	// ArrayList<JzgCarChooseStyleCategory> styleCategoryList = new
	// ArrayList<JzgCarChooseStyleCategory>();
	// JzgCarChooseStyleCategory styleCategory = null;
	// JzgCarChooseStyle style = null;
	// try {
	// JSONObject jsonObject = new JSONObject(jsonData);
	// boolean status = getResult(jsonObject);
	// String msg = jsonObject.getString("msg");
	// styles.setSuccess(status);
	// styles.setMsg(msg);
	// if (status) {
	// JSONArray yearGroupList = jsonObject.optJSONArray("YearGroupList");
	// // .getJSONArray("YearGroupList");
	// if(yearGroupList==null){
	// return styles;
	// }
	// for (int i = 0; i < yearGroupList.length(); i++) {
	// JSONObject object = yearGroupList.getJSONObject(i);
	// styleCategory = new JzgCarChooseStyleCategory(object.getString("Year"));
	// JSONArray modellists = object.getJSONArray("StyleList");
	// for (int j = 0; j < modellists.length(); j++) {
	// JSONObject modelObj = modellists.getJSONObject(j);
	// style = new JzgCarChooseStyle();
	// style.setId(modelObj.getInt("Id"));
	// style.setName(modelObj.getString("Name"));
	// style.setYear(modelObj.getInt("Year"));
	// style.setNowMsrp(modelObj.getString("NowMsrp"));
	// style.setFullName(modelObj.getString("FullName"));
	// style.setFontColor(Color.rgb(51, 51, 51));
	// style.setItemColor(Color.WHITE);
	// styleCategory.addItem(style);
	// }
	// styleCategoryList.add(styleCategory);
	// }
	//
	// styles.setCarStyles(styleCategoryList);
	// }
	// styles.setSuccess(status);
	// styles.setMsg(msg);
	// } catch (JSONException e) {
	//
	// e.printStackTrace();
	//
	// }
	// return styles;
	// }
	private boolean getResult(JSONObject jsonObject) throws JSONException {
		return jsonObject.getBoolean("success");
	}
}
