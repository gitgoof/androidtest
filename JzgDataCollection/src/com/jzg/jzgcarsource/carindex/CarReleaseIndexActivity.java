package com.jzg.jzgcarsource.carindex;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.SlidingDrawer.OnDrawerScrollListener;
import android.widget.TextView;
import android.widget.Toast;

import com.jzg.jzgcarsource.activity.BaseActivity;
import com.jzg.jzgcarsource.activity.DialogManager;
import com.jzg.jzgcarsource.activity.R;
import com.jzg.jzgcarsource.adapter.CustomArrayAdapter;
import com.jzg.jzgcarsource.adapter.CustomArrayAdapter.OnImgClickListener;
import com.jzg.jzgcarsource.application.AppContext;
import com.jzg.jzgcarsource.carindex.adapter.GridAdapter;
import com.jzg.jzgcarsource.carindex.adapter.ModelCategoryAdapter;
import com.jzg.jzgcarsource.carindex.adapter.StyleCategoryAdapter;
import com.jzg.jzgcarsource.carindex.app.HttpService;
import com.jzg.jzgcarsource.carindex.constant.Constant;
import com.jzg.jzgcarsource.carindex.utils.ChineseUtil;
import com.jzg.jzgcarsource.carindex.view.MyLetterListView;
import com.jzg.jzgcarsource.carindex.view.MyLetterListView.OnTouchingLetterChangedListener;
import com.jzg.jzgcarsource.carindex.vo.CarDetail;
import com.jzg.jzgcarsource.carindex.vo.CarRelease;
import com.jzg.jzgcarsource.carindex.vo.CustomData;
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
import com.jzg.jzgcarsource.view.HorizontalListView;
import com.lidroid.xutils.util.LogUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * 发车、筛选车型索引共用界面
 * 
 * @author 作者 E-mail:
 * @version 创建时间：2015-1-5 下午5:30:02 类说明
 */
@SuppressWarnings("deprecation")
public class CarReleaseIndexActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener, OnDrawerOpenListener,
		OnDrawerCloseListener, OnScrollListener, OnImgClickListener {

	private static final int SUCCESS = 100;

	protected ImageLoader imageLoader = ImageLoader.getInstance();

	private MyLetterListView mIndexListView = null;
	private ListView mIndexCarListView = null;
	private ImageButton mReturnBtn = null;

	// 存放存在的汉语拼音首字母
	private String[] sections;

	// 存放存在的汉语拼音首字母和与之对应的列表位置
	private HashMap<String, Integer> index;

	/**
	 * 汽车系列drawer
	 */
	private SlidingDrawer mCarTypeDrawer;
	private ImageView mCarTypeHandle;
	private ListView mCarTypeContent;

	/**
	 * 汽车类型drawer
	 */
	private SlidingDrawer mCarYearstyleDrawer;
	private ImageView mCarYearstyleHandle;
	private ListView mCarYearstyleContent;

	private GridView mHotRecommended;

	/**
	 * 热门推荐
	 */
	private HotCarList mHotCarList;

	/**
	 * 品牌列表
	 */
	private MakeList mMakeList;

	/**
	 * 车系列表
	 */
	private ModelList mModelList;

	/**
	 * 类型列表
	 */
	private StyleList mStyleList;

	/**
	 * 品牌、车系、车型/热门车控制
	 */
	private Handler mHandler;

	/**
	 * 汽车品牌排序集合
	 */
	private ArrayList<Map<String, Object>> items;

	/**
	 * 当前选择的汽车品牌
	 */
	private String mCurMake;

	/**
	 * 当前选择的品牌id
	 */
	private int mCurMakeid;

	/**
	 * 当前选择的汽车车系
	 */
	private String mCurModel;

	/**
	 * 当前选择的车系id
	 */
	private int mCurModelid;

	/**
	 * 当前选择的汽车类型
	 */
	private String mCurStyle;

	/**
	 * 查车价详情
	 */
	private CarDetail mCarDetail;

	/**
	 * 当前选择的类型id
	 */
	private int mCurStyleid;

	/**
	 * 当前选择的车型年款
	 */
	private int mCurYear;

	/**
	 * 解析后车系json数据封装
	 */
	private List<ModelCategory> mModelCategorys;

	/**
	 * 热门推荐数据
	 */
	private ArrayList<HotCar> hotCars;

	/**
	 * 品牌列表所有数据
	 */
	private ArrayList<Make> makes;

	/**
	 * 车系列表所有数据包括标题
	 */
	private List<Model> mModels;

	/**
	 * 所有车系标题
	 */
	private List<String> mModelsGroupkey;

	/**
	 * 车型列表所有数据包括标题
	 */
	private List<Style> mStyles;

	/**
	 * 所有车型标题
	 */
	private List<String> mStylessGroupkey;

	/**
	 * 车型json解析数据封装
	 */
	private List<StyleCategory> carStyles;

	/**
	 * 品牌列表适配器
	 */
	private ListAdapter mIndexCarListAdapter;

	/**
	 * 车系列表适配器
	 */
	private ModelCategoryAdapter mModelCategoryAdapter;

	/**
	 * 车型列表适配器
	 */
	private StyleCategoryAdapter mStyleCategoryAdapter;

	/**
	 * 品牌列表的上一次点击位置
	 */
	private int mMakeListOldPosition = -1;

	/**
	 * 车系列表的上一次点击位置
	 */
	private int mModelListOldPosition = -1;

	/**
	 * 车型列表的上一次点击位置
	 */
	private int mStyleListOldPosition = -1;

	/**
	 * 热门品牌列表的上一次点击位置
	 */
	private int mHotcarsOldPosition = -1;

	/**
	 * 图片加载配置选项
	 */
	private DisplayImageOptions mOptions;

	/**
	 * 弹出窗口管理器
	 */
	private DialogManager mDialogManager;

	/**
	 * 加载中提示框
	 */
	private Dialog mDialog;

	private AppContext mAppContext;

	/**
	 * 判断是否为查价格
	 */
	// 估价
	private String isQueryCar = "";
	private static final int QUERYCAR_MSG = 1;// 车辆信息

	// 添加定制
	private String carFilterModleFlag = "";
	// 发车"from_carReleaseActivity"
	private String carReleaseFlag = "";
	// 发布求购信息"from_releaseBuyMsg"
	private String releaseBuyMsg = "";
	/**
	 * 判断是否有历史记录的标记
	 */
	private boolean queryEditIsNull;

	/**
	 * 筛选过程中选择的车型项
	 */
	private List<Model> models;

	private HorizontalListView mHlvCustomListWithDividerAndFadingEdge;
	private CustomArrayAdapter adapter = null;
	private static List<CustomData> mCustomData = new ArrayList<CustomData>();
	private LinearLayout chooseLayout;
	private Button mSureBtn;

	private boolean isFirst;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_car);
		mHandler = getHandler();
		mAppContext = (AppContext) getApplicationContext();
		mOptions = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.jingzhengu)
				.showImageForEmptyUri(R.drawable.jingzhengu)
				.showImageOnFail(R.drawable.jingzhengu).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(20)).build();
		carReleaseFlag = getIntent().getStringExtra("carReleaseActivity");
		isQueryCar = getIntent().getStringExtra("isQueryCar");
		releaseBuyMsg = getIntent().getStringExtra("from_releaseBuyMsg");
		carFilterModleFlag = getIntent().getStringExtra("carfilter_modle");
		queryEditIsNull = getIntent().getBooleanExtra("queryEditIsNull", true);
		init();
	}

	private void showError(String string) {
		showMsgToast(string);
	}

	private Handler getHandler() {
		return new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (mDialog != null && mDialog.isShowing())
					mDialog.dismiss();
				int id = msg.what;
				switch (id) {
				case R.id.hotcarlist:
					// 组装热门品牌数据
					assemblyHotCarGrid(msg);
					break;
				case R.id.makelist:
					// 组装汽车品牌数据
					assemblyMakeList(msg);
					break;
				case R.id.modellist:
					colseDrawer(mCarYearstyleDrawer);
					if (checkModelist(msg)) {
						// 组装汽车系列数据
						assemblyModelList(msg);
					} else {
						colseDrawer(mCarTypeDrawer);
					}
					break;
				case R.id.stylelist:
					// 组装汽车类型数据
					if (checkResult(msg)) {
						assemblyStyleList(msg);
					}
					break;

				case R.id.car_detail:
					completeCarDetail(msg);
					break;
				case R.id.net_error:
					showError(getResources().getString(R.string.net_error));
					break;
				case R.id.carDetailSuc:
					// =======不再调用获取车型全名接口========
					// CarDetail car = (CarDetail) msg.obj;
					// CarRelease carRelease = mAppContext.getCarRelease();
					// String name = car.getMakeName() + "-" +
					// car.getModelName()
					// + "-" + car.getStyleName();
					// // TODO 此处需要和后台接口进行沟通，无法获取fullName，需要添加相应参数
					// carRelease.setCarStyle(name);
					// // 设置结果回调
					// setResult(0);
					// finish();

					break;
				default:
					break;
				}

			}

			private void assemblyHotCarGrid(Message msg) {
				mHotCarList = (HotCarList) msg.obj;
				if (mHotCarList.getStatus() == SUCCESS) {
					hotCars = mHotCarList.getHotCars();

					// 历史热门数据列表
					AppContext.mHistoryHotCars.clear();
					AppContext.mHistoryHotCars.addAll(hotCars);

					showHotCarList(hotCars);
				}
			}

			/**
			 * 查车详情完成 completeCarDetail: <br/>
			 * 
			 * @author wang
			 * @param msg
			 * @since JDK 1.6
			 */
			private void completeCarDetail(Message msg) {
				CarDetail carDetail = (CarDetail) msg.obj;
				if (carDetail.getStatus() == SUCCESS) {
					// ActivityHelp.startActivity(getApplicationContext(),
					// QueryCarDetailActivity.class, "carDetail",
					// carDetail);
				} else {

					showError(getResources().getString(
							R.string.query_car_detail_error));
				}
			}

			private void assemblyStyleList(Message msg) {
				StyleList styleList = (StyleList) msg.obj;
				carStyles = styleList.getCarStyles();

				// 车型数据加载完毕后添加数据到车型历史记录
				AppContext.mHistoryCarStyles.clear();
				AppContext.mHistoryCarStyles.addAll(carStyles);

				showStyleList(queryEditIsNull ? carStyles
						: AppContext.mHistoryCarStyles);
			}

			private void assemblyModelList(Message msg) {
				ModelList modelList = (ModelList) msg.obj;
				mModelCategorys = modelList.getModels();

				// 车系数据加载完毕后添加数据到车系历史记录列表
				AppContext.mHistoryModelCategorys.clear();
				AppContext.mHistoryModelCategorys.addAll(mModelCategorys);

				// 组装数据的时候如果历史记录标记是null位true则用原有列表，否则使用历史记录列表
				showModelList(queryEditIsNull ? mModelCategorys
						: AppContext.mHistoryModelCategorys);
			}

			private void assemblyMakeList(Message msg) {
				mMakeList = (MakeList) msg.obj;

				if (mMakeList.getStatus() == SUCCESS) {
					makes = mMakeList.getMakes();

					// 加载品牌数据完成后添加数据到品牌历史记录
					AppContext.mHistoryMakes.clear();
					AppContext.mHistoryMakes.addAll(makes);

					showMakeList(makes);

				} else {
					showError("无品牌数据！！！");
				}

			}
		};
	}

	/**
	 * 显示品牌列表数据
	 * 
	 * @param makes
	 */
	protected void showMakeList(ArrayList<Make> makes) {
		setMakeColor();

		String contactSort = null;
		items = new ArrayList<Map<String, Object>>();
		if (makes != null) {
			Map<String, Object> map = null;
			for (int i = 0; i < makes.size(); i++) {
				map = new HashMap<String, Object>();
				map.put("name", makes.get(i).getMakeName());
				map.put("fontColor", makes.get(i).getFontColor());
				map.put("logo", makes.get(i).getMakeLogo());
				contactSort = ChineseUtil
						.getFullSpell(map.get("name").toString()).toUpperCase()
						.substring(0, 1);
				map.put("Sort", contactSort);
				map.put("itemColor", makes.get(i).getItemColor());
				items.add(map);
			}
			Comparator comp = new Mycomparator();
			Collections.sort(items, comp);
			mIndexCarListAdapter = new ListAdapter(getApplicationContext(),
					items);
			mIndexCarListView.setAdapter(mIndexCarListAdapter);
			if (mMakeListOldPosition != -1) {
				mIndexCarListView.setSelection(mMakeListOldPosition);
			}
		} else {
			showError("无品牌数据！！！");
		}
	}

	private void setMakeColor() {
		// 设置所有颜色为原色
		for (Make make : makes) {
			make.setFontColor(Color.BLACK);
			make.setItemColor(Color.WHITE);
		}

		// 设置点击item的点击颜色
		if (mMakeListOldPosition != -1) {
			// makes.get(mMakeListOldPosition).setFontColor(
			// getResources().getColor(R.color.list_click));
			makes.get(mMakeListOldPosition).setItemColor(
					getResources().getColor(R.color.grey2));
		}

	}

	/**
	 * 显示热门车系列表
	 * 
	 * @param hotCars
	 */
	protected void showHotCarList(List<HotCar> hotCars) {
		mHotRecommended.setAdapter(new GridAdapter(getApplicationContext(),
				hotCars));
	}

	/**
	 * 显示车系列表
	 * 
	 * @param mModelCategorys
	 */
	protected void showModelList(List<ModelCategory> mModelCategorys) {
		openDrawer(mCarTypeDrawer);
		mModels = new ArrayList<Model>();
		if ("carfilter_modle".equals(carFilterModleFlag)) {
			Model topAllModel = new Model();
			topAllModel.setFontColor(getResources().getColor(
					R.color.categroy_title));
			topAllModel.setId(0);
			topAllModel.setItemColor(getResources().getColor(R.color.white));
			topAllModel.setManufacturerName("全部车系");
			topAllModel.setName("全部车系");
			mModels.add(topAllModel);
		}
		mModelsGroupkey = new ArrayList<String>();
		for (ModelCategory category : mModelCategorys) {
			Model model = new Model();
			String groupName = category.getmCategoryName();
			mModelsGroupkey.add(groupName);
			model.setName(groupName);
			model.setManufacturerName(Constant.IS_TITLE);
			model.setFontColor(getResources().getColor(R.color.categroy_title));
			mModels.add(model);
			mModels.addAll(category.getmCategoryItem());
		}

		setModelColor();

		mModelCategoryAdapter = new ModelCategoryAdapter(
				getApplicationContext(), mModels, mModelsGroupkey);
		mCarTypeContent.setAdapter(mModelCategoryAdapter);

		// 只有为历史记录的时候才跳转历史选择的项
		if (!queryEditIsNull) {
			if (AppContext.mModelHistoryPosition != -1) {
				mCarTypeContent.setSelection(AppContext.mModelHistoryPosition);
			}
		}
	}

	private void setModelColor() {
		// 设置所有颜色为原色
		for (Model model : mModels)
			if (Constant.IS_TITLE.equals(model.getManufacturerName())) {
				model.setFontColor(getResources().getColor(
						R.color.categroy_title));
			} else {
				model.setFontColor(Color.BLACK);
				model.setItemColor(Color.WHITE);
			}
		// 设置点击item的点击颜色
		if (mModelListOldPosition != -1)
			mModels.get(mModelListOldPosition).setItemColor(
					getResources().getColor(R.color.grey2));
		// mModels.get(mModelListOldPosition).setFontColor(
		// getResources().getColor(R.color.list_click));
	}

	/**
	 * 显示车型列表
	 * 
	 * @param carStyles
	 */
	protected void showStyleList(List<StyleCategory> carStyles) {
		openDrawer(mCarYearstyleDrawer);
		mStyles = new ArrayList<Style>();
		mStylessGroupkey = new ArrayList<String>();
		if ("carfilter_modle".equals(carFilterModleFlag)
				|| "from_releaseBuyMsg".equals(releaseBuyMsg)) {
			Style style = new Style();
			style.setName("全部车型");
			style.setManufacturerName("全部车型");
			style.setId(0);
			style.setFontColor(getResources().getColor(R.color.categroy_title));
			style.setItemColor(Color.WHITE);
			// 添加标题到列表
			mStyles.add(style);
		}
		for (StyleCategory category : carStyles) {
			Style style = new Style();
			String groupName = category.getYearTitle();
			mStylessGroupkey.add(groupName);
			style.setName(groupName);
			style.setManufacturerName(Constant.IS_TITLE);
			style.setFontColor(getResources().getColor(R.color.categroy_title));
			style.setItemColor(Color.WHITE);
			// 添加标题到列表
			mStyles.add(style);
			// 添加所有选项到列表
			mStyles.addAll(category.getCategoryItem());
		}

		setStyleColor();

		mStyleCategoryAdapter = new StyleCategoryAdapter(
				getApplicationContext(), mStyles, mStylessGroupkey);
		mCarYearstyleContent.setAdapter(mStyleCategoryAdapter);

		// 只有为历史记录的时候才跳转历史选择的项
		if (!queryEditIsNull) {
			if (AppContext.mStyleHistoryPosition != -1) {
				mCarYearstyleContent
						.setSelection(AppContext.mStyleHistoryPosition);
			}
		}

	}

	private void setStyleColor() {
		// 设置所有颜色为原色
		for (Style style : mStyles)
			if (mStylessGroupkey.contains(style.getName())) {
				style.setFontColor(getResources().getColor(
						R.color.categroy_title));
			} else {
				style.setFontColor(Color.BLACK);
				style.setItemColor(Color.WHITE);
			}
		// 设置点击item的点击颜色
		if (mStyleListOldPosition != -1)
			mStyles.get(mStyleListOldPosition).setItemColor(
					getResources().getColor(R.color.grey2));
		// 原有修改点击item字体颜色代码
		// mStyles.get(mStyleListOldPosition).setFontColor(
		// getResources().getColor(R.color.list_click));
	}

	protected void openDrawer(SlidingDrawer mDrawer) {
		if (!mDrawer.isOpened()) {
			mDrawer.animateOpen();
		}
	}

	protected void colseDrawer(SlidingDrawer mDrawer) {
		if (mDrawer.isOpened()) {
			mDrawer.close();
		}
	}

	/**
	 * 校验Modellist返回结果 checkModelist: <br/>
	 * 
	 * @author wang
	 * @param msg
	 * @return
	 * @since JDK 1.6
	 */
	protected boolean checkModelist(Message msg) {
		ModelList modelList = (ModelList) msg.obj;
		if (modelList.getStatus() != SUCCESS)
			return false;
		return true;
	}

	protected boolean checkResult(Message msg) {
		StyleList styleList = (StyleList) msg.obj;
		int status = styleList.getStatus();
		if (status != SUCCESS) {
			showError(styleList.getMsg());
			return false;
		}
		return true;
	}

	// 按中文拼音排序
	public class Mycomparator implements Comparator {
		@Override
		public int compare(Object o1, Object o2) {
			Map<String, Object> c1 = (Map<String, Object>) o1;
			Map<String, Object> c2 = (Map<String, Object>) o2;
			Comparator cmp = Collator.getInstance(java.util.Locale.ENGLISH);
			return cmp.compare(c1.get("Sort"), c2.get("Sort"));
		}
	}

	private static class AnimateFirstDisplayListener extends
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

	@Override
	public void onPause() {
		super.onPause();
		// MobclickAgent.onPause(this);
		// 活动界面暂停的时候对开启的滑动界面进行关闭操作
		if (mCarTypeDrawer != null && mCarTypeDrawer.isOpened()) {
			mCarTypeDrawer.close();
		}
		if (mCarYearstyleDrawer != null && mCarYearstyleDrawer.isOpened()) {
			mCarYearstyleDrawer.close();
		}
	}

	// @Override
	// protected void onResume()
	// {
	// super.onResume();
	// // MobclickAgent.onResume(this);
	// }

	@Override
	public void init() {
		models = new ArrayList<Model>();
		models.clear();
		mCustomData.clear();
		mHlvCustomListWithDividerAndFadingEdge = (HorizontalListView) findViewById(R.id.hlvCustomListWithDividerAndFadingEdge);
		isFirst = true;
		TextView title = (TextView) findViewById(R.id.index_title);
		chooseLayout = (LinearLayout) findViewById(R.id.chooseLayout);
		mSureBtn = (Button) findViewById(R.id.sure_btn);
		mSureBtn.setOnClickListener(this);
		mReturnBtn = (ImageButton) findViewById(R.id.return_btn);
		mReturnBtn.setOnClickListener(this);
		if ("carfilter_modle".equals(carFilterModleFlag)) {
			// 筛选去掉
			// mSureBtn.setVisibility(View.VISIBLE);
			// mReturnBtn.setVisibility(View.VISIBLE);
			title.setText(getResources().getString(R.string.choose_model_make));
		} else {
			mSureBtn.setVisibility(View.GONE);
			// mReturnBtn.setVisibility(View.GONE);
		}

		mDialogManager = new DialogManager();
		mHotRecommended = (GridView) findViewById(R.id.hot_recommended);
		mHotRecommended.setOnItemClickListener(this);
		mHotRecommended.setSelector(new ColorDrawable(Color.TRANSPARENT));

		// 初始化汽车品牌
		mIndexListView = (MyLetterListView) findViewById(R.id.index_car_index_list);
		mIndexListView
				.setOnTouchingLetterChangedListener(new LetterListViewListener());
		mIndexCarListView = (ListView) findViewById(R.id.index_car_list);
		mIndexCarListView.setAlpha(200);
		mIndexCarListView.setOnItemClickListener(this);
		// 开启汽车品牌查询线程
		mDialog = mDialogManager.show(this, this, R.drawable.bg_loading);

		// 初始化汽车类型
		mCarTypeDrawer = (SlidingDrawer) findViewById(R.id.index_car_type_drawer);
		mCarTypeHandle = (ImageView) findViewById(R.id.index_car_type_handle);
		mCarTypeContent = (ListView) findViewById(R.id.index_car_type_list_content);
		mCarTypeContent.setOnItemClickListener(this);
		// 设置SlidingDrawer打开或者关闭时的监听器，设置失去
		mCarTypeDrawer.setOnDrawerOpenListener(this);
		mCarTypeDrawer.setOnDrawerCloseListener(this);

		// 设置mCarTypeDrawer滑动时监听，如果mCarYearstyleDrawer是打开状态则先关闭
		mCarTypeDrawer.setOnDrawerScrollListener(new OnDrawerScrollListener() {
			@Override
			public void onScrollStarted() {
				colseDrawer(mCarYearstyleDrawer);
			}

			@Override
			public void onScrollEnded() {

			}
		});

		// 初始化汽车年度款式
		mCarYearstyleDrawer = (SlidingDrawer) findViewById(R.id.index_car_yearstyle_drawer);
		mCarYearstyleHandle = (ImageView) findViewById(R.id.index_car_yearstyle_handle);

		mCarYearstyleContent = (ListView) findViewById(R.id.index_car_yearstyle_content);
		mCarYearstyleContent.setOnItemClickListener(this);

		// 设置SlidingDrawer打开或者关闭时的监听器
		mCarYearstyleDrawer.setOnDrawerOpenListener(this);
		mCarYearstyleDrawer.setOnDrawerCloseListener(this);

		mIndexCarListView.setOnScrollListener(this);

		if (queryEditIsNull || AppContext.mHistoryHotCars.size() == 0
				|| AppContext.mHistoryCarStyles.size() == 0
				|| AppContext.mHistoryMakes.size() == 0
				|| AppContext.mHistoryModelCategorys.size() == 0) {
			startMakeListThread();
			startHotListThread();
			LogUtils.i("没有历史记录，走的网络查询流程");
		} else {
			showAllHistoryList();
		}

	}

	@SuppressLint("ResourceAsColor")
	private void showAllHistoryList() {
		if (mDialog != null)
			mDialog.dismiss();

		mMakeListOldPosition = AppContext.mMakeHistoryPosition;
		mModelListOldPosition = AppContext.mModelHistoryPosition;
		mStyleListOldPosition = AppContext.mStyleHistoryPosition;

		if (hotCars == null) {
			hotCars = new ArrayList<HotCar>();
		} else {
			hotCars.clear();
		}
		hotCars.addAll(AppContext.mHistoryHotCars);
		showHotCarList(hotCars);

		if (makes == null) {
			makes = new ArrayList<Make>();
		} else {
			makes.clear();
		}

		makes.addAll(AppContext.mHistoryMakes);
		showMakeList(makes);

		if (mModelCategorys == null) {
			mModelCategorys = new ArrayList<ModelCategory>();
		} else {
			mModelCategorys.clear();
		}
		mModelCategorys.addAll(AppContext.mHistoryModelCategorys);
		showModelList(mModelCategorys);

		if (carStyles == null) {
			carStyles = new ArrayList<StyleCategory>();
		} else {
			carStyles.clear();
		}
		carStyles.addAll(AppContext.mHistoryCarStyles);
		showStyleList(carStyles);

		LogUtils.i("已存在历史记录，走的历史记录流程");
	}

	/**
	 * 热门品牌查询线程 startHotListThread: <br/>
	 * 
	 * @author wang
	 * @since JDK 1.6
	 */
	private void startHotListThread() {
		HttpService.getHotCarList(mHandler, AppContext.getRequestQueue(),
				R.id.hotcarlist);
	}

	/**
	 * 汽车品牌查询线程 runCarListThread: <br/>
	 * 
	 * @author wang
	 * @since JDK 1.6
	 */
	private void startMakeListThread() {
		HttpService.getMakeList(mHandler, AppContext.getRequestQueue(),
				R.id.makelist);
	}

	/**
	 * 返回定制界面
	 * 
	 * @param modelStr
	 * @param modelId
	 * @param makeStr
	 * @param makeId
	 * @param styleStr
	 * @param styleId
	 */
	private void returnToCustomActivity(String makeStr, String makeId,
			String modelStr, String modelId, String styleStr, String styleId) {
		Intent intent = new Intent();
		intent.putExtra("modelStr", modelStr);
		intent.putExtra("modelId", modelId);
		intent.putExtra("makeStr", makeStr);
		intent.putExtra("makeId", makeId);
		intent.putExtra("styleStr", styleStr);
		intent.putExtra("styleId", styleId);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		int viewid = parent.getId();
		switch (viewid) {
		// 品牌列表点击
		case R.id.index_car_list:
			makeItemClick(view, position);
			break;
		// 车系列表点击
		case R.id.index_car_type_list_content:
			modelItemClick(view, position);
			break;
		// 车型列表点击
		case R.id.index_car_yearstyle_content:
			styleItemClick(position);
			break;
		// 热门推荐
		case R.id.hot_recommended:
			hotRecommendedItemClick(position);
			break;
		default:
			showError("出错了请仔细检查！");
			break;
		}
	}

	private void hotRecommendedItemClick(int position) {
		// 热门品牌被点击的时候记录历史被点击的位置
		AppContext.mHotCarsHistoryPosition = mHotcarsOldPosition = position;

		// 判断是否为历史记录如果不是历史记录走之前的流程
		HotCar hotCar = null;
		if (queryEditIsNull) {
			hotCar = mHotCarList.getHotCars().get(position);
		}
		// 如果有历史记录则从历史记录中拿取数据
		else {
			hotCar = AppContext.mHistoryHotCars.get(position);
		}
		final int makeId = hotCar.getMakeId();
		mCurMakeid = makeId;

		final String makeid = String.valueOf(hotCar.getMakeId());
		startModelListThread(makeid);
		mDialog = mDialogManager.show(this, this, R.drawable.bg_loading);
		// 每次点击选热门品牌列表后，对车系上一次点击位置进行初始化
		AppContext.mModelHistoryPosition = mModelListOldPosition = -1;
	}

	private void styleItemClick(int position) {
		// 当车型列表被点击时，存储当前点击的位置
		AppContext.mStyleHistoryPosition = mStyleListOldPosition = position;

		Style style = mStyles.get(position);

		mCurStyleid = style.getId();
		style.setMakeId(mCurMakeid);
		style.setMakeName(mCurMake);
		style.setModelId(mCurModelid);
		style.setModelName(mCurModel);

		System.out.println("style---->" + style.toString());
		if ("carfilter_modle".equals(carFilterModleFlag)
				|| "from_releaseBuyMsg".equals(releaseBuyMsg)) {
			// 定制流程
			returnToCustomActivity(style.getMakeName(),
					String.valueOf(style.getMakeId()), style.getModelName(),
					String.valueOf(style.getModelId()), style.getFullName(),
					String.valueOf(style.getId()));
			return;

		} else if ("from_carReleaseActivity".equals(carReleaseFlag)) {
			// 发车流程

			AppContext.mCarReleaseYear = style.getYear();
			AppContext.mCarReleaseStyle = style;
			CarRelease carRelease = mAppContext.getCarRelease();
			// 保存样式id到应用内存中，发车的时候从内存中获取
			carRelease.setStyleId(mCurStyleid);
			carRelease.setStyle(style);
			carRelease.setCarStyle(style.getFullName());
			// 设置结果回调
			Intent in = new Intent();
			in.putExtra("mQueryCarStyle", carRelease);
			setResult(1, in);
			finish();
		} else if (!TextUtils.isEmpty(isQueryCar)) {
			// 估价流程
			AppContext.mQueryCarYear = style.getYear();
			AppContext.mQueryCarStyle = style;
			Intent in = new Intent();
			in.putExtra("mQueryCarStyle", style);
			setResult(QUERYCAR_MSG, in);
			finish();
			// // TODO 此处需要和后台接口进行沟通，无法获取fullName，需要添加相应参数
			// carRelease.setCarStyle(style.getFullName());
			// 设置结果回调
			// HttpService.getCarStyle(mHandler, style);
			// setResult(0);
			// finish();
		}
	}

	private void modelItemClick(View view, int position) {
		// 当车系列表被点击时，存储当前点击的位置
		AppContext.mModelHistoryPosition = mModelListOldPosition = position;

		modifyModelFontColor(view, R.id.addexam_list_item_text,
				R.color.list_click, position);
		int modelid = 0;
		// 走不是历史记录流程
		if (queryEditIsNull) {
			modelid = mModels.get(position).getId();
			mCurModel = mModels.get(position).getName();
		}
		// 走历史记录流程
		else {
			ArrayList<Model> models = new ArrayList<Model>();
			for (ModelCategory category : AppContext.mHistoryModelCategorys) {
				Model model = new Model();
				String groupName = category.getmCategoryName();
				model.setName(groupName);
				model.setManufacturerName(Constant.IS_TITLE);
				model.setFontColor(getResources().getColor(
						R.color.categroy_title));
				models.add(model);
				models.addAll(category.getmCategoryItem());
			}
			modelid = models.get(position).getId();
			mCurModel = models.get(position).getName();
		}

		mCurModelid = modelid;
		if (mStylessGroupkey != null) {
			mStylessGroupkey.clear();
			mStyleCategoryAdapter.notifyDataSetChanged();
		}
		// 走筛选流程
		if ("carfilter_modle".equals(carFilterModleFlag)
				|| "from_releaseBuyMsg".equals(releaseBuyMsg)) {
			String makeStr = "";
			String makeId = "";
			// 定制车源
			if (position == 0) {
				if (mHotcarsOldPosition != -1) {
					makeStr = hotCars.get(mHotcarsOldPosition).getMakeName();
					makeId = String.valueOf(hotCars.get(mHotcarsOldPosition)
							.getMakeId());
				} else if (mMakeListOldPosition != -1) {
					Make make = makes.get(mMakeListOldPosition);
					makeStr = make.getMakeName();
					makeId = String.valueOf(make.getMakeId());
				}
				returnToCustomActivity(makeStr, makeId, "", "0", "", "");
			} else {
				mDialog = mDialogManager
						.show(this, this, R.drawable.bg_loading);
				startStyleListThread(String.valueOf(modelid));
			}
		}
		// 估价、筛选流程
		else {
			mDialog = mDialogManager.show(this, this, R.drawable.bg_loading);
			startStyleListThread(String.valueOf(modelid));
		}

		// 每次点击车系列表后，对车型列表上次点击的位置进行初始化
		mStyleListOldPosition = AppContext.mStyleHistoryPosition = -1;
	}

	private void makeItemClick(View view, int position) {
		// 当品牌列表被点击的时候存储当前点击的位置
		AppContext.mMakeHistoryPosition = mMakeListOldPosition = position;

		Make make = null;
		// 如果当前点击是在历史记录状态下进行的则从历史列表中拿取相应的数据
		if (!queryEditIsNull) {
			make = AppContext.mHistoryMakes.get(position);
		}
		// 否则从查询出来的数据中拿取数据
		else {
			make = makes.get(position);
		}

		final String makeid = String.valueOf(make.getMakeId());
		// 设置当前选择的品牌、id
		mCurMake = make.getMakeName();
		mCurMakeid = make.getMakeId();
		// TODO 颜色变化稍后添加，目前先把数据的逻辑理清
		modifyMakeFontColor(view, R.id.car_name, R.color.list_click, position);
		mDialog = mDialogManager.show(this, this, R.drawable.bg_loading);
		startModelListThread(makeid);

		// 每次点击选品牌列表后，对车系上一次点击位置进行初始化
		mModelListOldPosition = -1;
		AppContext.mModelHistoryPosition = -1;
	}

	/**
	 * 修改品牌列表字体点击颜色 modifyMakeFontColor: <br/>
	 * 
	 * @author wang
	 * @param view
	 * @param carName
	 * @param listClick
	 * @param position
	 * @since JDK 1.6
	 */
	private void modifyMakeFontColor(View view, int resid, int listClickColor,
			int position) {

		// 设置所有颜色为原色
		for (int i = 0; i < items.size(); i++) {
			items.get(i).put("fontColor", Color.BLACK);
			items.get(i).put("itemColor", Color.WHITE);
		}

		TextView textView = (TextView) view.findViewById(resid);
		textView.setTextColor(getResources().getColor(listClickColor));

		// items.get(position).put("fontColor",
		// getResources().getColor(listClickColor));
		// 原有版本改变为黄色字体的代码，用下面代码替换，不改变字体颜色
		items.get(position).put("fontColor", Color.BLACK);
		items.get(position).put("itemColor",
				getResources().getColor(R.color.grey2));

		mIndexCarListAdapter.notifyDataSetChanged();
	}

	/**
	 * 修改车系列表字体点击颜色 modifyFontColor: <br/>
	 * 
	 * @author wang
	 * @param view
	 * @param resid
	 * @param listClickColor
	 * @param oldPosition
	 * @param position
	 * @since JDK 1.6
	 */

	private void modifyModelFontColor(View view, int resid, int listClickColor,
			int position) {
		for (Model model : mModels) {
			if (TextUtils.isEmpty(model.getManufacturerName())) {
				model.setFontColor(Color.BLACK);
			} else {
				model.setFontColor(getResources().getColor(
						R.color.categroy_title));
			}
			model.setItemColor(Color.WHITE);
		}

		TextView textView = (TextView) view.findViewById(resid);
		textView.setTextColor(getResources().getColor(listClickColor));
		// mModels.get(position).setFontColor(
		// getResources().getColor(listClickColor));
		// 原有版本改变为黄色字体的代码，用下面代码替换，不改变字体颜色
		mModels.get(position).setFontColor(Color.BLACK);
		mModels.get(position).setItemColor(
				getResources().getColor(R.color.grey2));
		mModelCategoryAdapter.notifyDataSetChanged();

	}

	/**
	 * 查询车辆价格详情 startQueryCarDetailThread: <br/>
	 * 
	 * @author wang
	 * @since JDK 1.6
	 */
	private void startQueryCarDetailThread() {
		// HttpService.getCarDetail(mHandler, AppContext.getRequestQueue(),
		// R.id.car_detail, mCurStyleid);
	}

	/**
	 * 车型查询线程 startStyleListThread: <br/>
	 * 
	 * @author wang
	 * @param modelid
	 * @since JDK 1.6
	 */
	private void startStyleListThread(final String modelid) {
		HttpService.getStyleList(mHandler, AppContext.getRequestQueue(),
				R.id.stylelist, modelid);
	}

	/**
	 * 车系查询线程 startModelListThread: <br/>
	 * 
	 * @author wang
	 * @param makeid
	 * @since JDK 1.6
	 */
	private void startModelListThread(final String makeid) {
		HttpService.getModelList(mHandler, AppContext.getRequestQueue(),
				R.id.modellist, makeid);
	}

	/**
	 * a-z索引监听 ClassName: LetterListViewListener <br/>
	 * Function: TODO ADD FUNCTION. <br/>
	 * Reason: TODO ADD REASON. <br/>
	 * date: 2014-6-2 下午3:44:10 <br/>
	 * 
	 * @author wang
	 * @version IndexCarActivity
	 * @since JDK 1.6
	 */
	class LetterListViewListener implements OnTouchingLetterChangedListener {

		@Override
		public void onTouchingLetterChanged(final String s) {
			if (index != null && index.get(s) != null) {
				int position = index.get(s);
				mIndexCarListView.setSelection(position);
				// overlay.setText(sections[position]);
				// overlay.setVisibility(View.VISIBLE);
				// handler.removeCallbacks(overlayThread);
				// 延迟一秒后执行，让overlay为不可见
				// handler.postDelayed(overlayThread, 1500);
			}
		}

	}

	/**
	 * 品牌列表适配器 ClassName: ListAdapter <br/>
	 * Function: TODO ADD FUNCTION. <br/>
	 * Reason: TODO ADD REASON. <br/>
	 * date: 2014-6-26 下午1:17:09 <br/>
	 * 
	 * @author wang
	 * @version IndexCarActivity
	 * @since JDK 1.6
	 */
	class ListAdapter extends BaseAdapter {
		private ImageLoadingListener mAnimateFirstListener = new AnimateFirstDisplayListener();
		private LayoutInflater inflater;
		private List<Map<String, Object>> list;

		public ListAdapter(Context context, List<Map<String, Object>> list) {
			this.inflater = LayoutInflater.from(context);
			this.list = list;
			Map<String, Integer> alphaIndexer = new HashMap<String, Integer>();
			sections = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				// 当前汉语拼音首字母
				String currentStr = list.get(i).get("Sort").toString();
				// 上一个汉语拼音首字母，如果不存在为“ ”
				String previewStr = (i - 1) >= 0 ? list.get(i - 1).get("Sort")
						.toString() : " ";
				if (!previewStr.equals(currentStr)) {
					String name = list.get(i).get("Sort").toString();
					alphaIndexer.put(name, i);
					System.out
							.println("name is " + name + ", position is " + i);
					sections[i] = name;
				}
			}
			index = (HashMap<String, Integer>) alphaIndexer;

		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.car_list_content, null);
				holder = new ViewHolder();
				holder.iamge = (ImageView) convertView
						.findViewById(R.id.car_image);
				holder.name = (TextView) convertView
						.findViewById(R.id.car_name);
				holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			convertView.setBackgroundColor((Integer) list.get(position).get(
					"itemColor"));
			holder.name.setText(list.get(position).get("name").toString());
			holder.name.setTextColor((Integer) list.get(position).get(
					"fontColor"));

			String imgUrl = (String) list.get(position).get("logo");
			// 品牌logo异步加载
			imageLoader.displayImage(imgUrl, holder.iamge, mOptions,
					mAnimateFirstListener);
			String currentStr = list.get(position).get("Sort").toString();
			String previewStr = (position - 1) >= 0 ? list.get(position - 1)
					.get("Sort").toString() : " ";
			if (!previewStr.equals(currentStr)) {
				holder.alpha.setVisibility(View.VISIBLE);
				holder.alpha.setText(currentStr);
			} else {
				holder.alpha.setVisibility(View.GONE);
			}
			return convertView;
		}

		private class ViewHolder {
			ImageView iamge;
			TextView name;
			TextView alpha;
		}

	}

	@Override
	public void onDrawerClosed() {
		// Toast.makeText(this, "onDrawerClosed", Toast.LENGTH_SHORT).show();

		// 如果mCarTypeDrawer关闭，但是mCarYearstyleDrawer还开启着，则mCarYearstyleDrawer也关联关闭
		if (!mCarTypeDrawer.isOpened() && mCarYearstyleDrawer.isOpened()) {
			mCarYearstyleDrawer.close();
		}
		// 如果CarYearstyleDrawer关闭，则调整handle的初始宽度为0
		RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(0,
				LayoutParams.MATCH_PARENT);
		if (!mCarYearstyleDrawer.isOpened()) {
			mCarYearstyleHandle.setLayoutParams(layout);
		}

		// 如果mCarTypeDrawer关闭，则调整两个Drawer的handle的初始宽度为0
		if (!mCarTypeDrawer.isOpened()) {
			mCarTypeHandle.setLayoutParams(layout);
		}
	}

	@Override
	public void onDrawerOpened() {
		if (mCarTypeDrawer.isOpened()) {
			mCarTypeHandle.setLayoutParams(new LayoutParams(40,
					LayoutParams.MATCH_PARENT));
		}
		if (mCarYearstyleDrawer.isOpened()) {
			mCarYearstyleHandle.setLayoutParams(new LayoutParams(40,
					LayoutParams.MATCH_PARENT));
		}
		// Toast.makeText(this, "onDrawerOpened", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// ListView开始滚动和结束滚动时候会调用
		switch (scrollState) {
		case OnScrollListener.SCROLL_STATE_IDLE:
			// 滚动结束调用
			System.out.println("1");
			break;
		case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
			// System.out.println("2");滚动时调用
			if (mCarTypeDrawer.isOpened())
				mCarTypeDrawer.animateClose();
			if (mCarYearstyleDrawer.isOpened())
				mCarYearstyleDrawer.animateClose();

			break;
		case OnScrollListener.SCROLL_STATE_FLING:
			System.out.println("3");
			break;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// ListView滚动过程中会一直调用
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sure_btn:
			if (models.size() > 0) {
				Intent intent = new Intent();
				StringBuffer modelStr = new StringBuffer();
				StringBuffer modelId = new StringBuffer();
				for (Model model : models) {
					modelStr.append(model.getName() + " ");
					modelId.append(model.getId() + ",");
				}
				intent.putExtra("modelStr",
						modelStr.substring(0, modelStr.length() - 1));
				intent.putExtra("modelId",
						modelId.substring(0, modelId.length() - 1));
				setResult(Activity.RESULT_OK, intent);
				finish();
			} else {
				Toast.makeText(this, "请选择品牌车系", 2000).show();
			}
			break;
		case R.id.return_btn:
			finish();
			break;
		default:
			break;
		}

	}

	@Override
	public void onImgClick(int position) {
		models.remove(position);
		mCustomData.remove(position);
		adapter.notifyDataSetChanged();
		if (mCustomData.size() == 0) {
			chooseLayout.setVisibility(View.GONE);
		}
		System.out.println("models result is " + models.toString());
	}

}
