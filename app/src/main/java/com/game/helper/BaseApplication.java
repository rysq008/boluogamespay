package com.game.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.game.helper.download.bean.AppContent;
import com.game.helper.download.downloador.Downloador;
import com.game.helper.fragment.HomeFragment;
import com.game.helper.interfaces.comm.CommValues;
import com.game.helper.interfaces.comm.HttpComm;
import com.game.helper.sdk.model.returns.GetAdList.AdData;
import com.game.helper.util.SharedPreUtil;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.widget.BarrageView;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.MemoryCacheAware;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.location.Location;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Vibrator;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;

public class BaseApplication extends MultiDexApplication implements CommValues, HttpComm {

    public final String TAG = "BaseApplication";
    public static BaseApplication mInstance;
    public Context context;
    public Vibrator mVibrator;
    private Map<String, Object> datas = new HashMap<String, Object>();

    private LocationClient client = null;
    private LocationClientOption mOption;
    private Object objLock = new Object();

    public boolean isonAppExit = false;
    private boolean flag;

    public List<AppContent> imageIdList1 = new ArrayList<AppContent>();
    public List<AppContent> imageIdList2 = new ArrayList<AppContent>();
    public List<AppContent> imageIdList3 = new ArrayList<AppContent>();
    public List<AppContent> imageIdList4 = new ArrayList<AppContent>();
    public static DisplayImageOptions mNormalImageOptions;
    public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().toString();
    public static final String IMAGES_FOLDER = SDCARD_PATH + File.separator + "demo" + File.separator + "images" + File.separator;

    public Map<String, Downloador> downloadorMap = new HashMap<String, Downloador>();
    /**
     * 主线程Looper
     */
    private static Looper mMainLooper;

    /**
     * 获取主线程的looper
     */
    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    public int isRecommendBoutiqueAdapter = 0;
    public static Bitmap bimap;
    public static int num = 9;
    public static double shareNum = 0.1;
    public static double publishNum = 0.1;
    public static double awardNum = 0.1;

    //public static Tencent mTencent;
    public static String mAppid = "1105689325";
    public boolean isCancelable = false;

    @Override
    public void onCreate() {
        super.onCreate();
//        com.game.helper.sdk.Config.getInstance().setHost(this,"");

        mMainLooper = getMainLooper();
        mInstance = this;
        context = this;
        /*if (mTencent == null) {
                mTencent = Tencent.createInstance(mAppid, this);
		}*/
        initImageLoader(this);
        initLocation();

        bimap = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.pic_08);
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        // JPushInterface.init(this);     		// 初始化 JPush

        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

        MobclickAgent.setDebugMode(true);
        MobclickAgent.openActivityDurationTrack(false);
        //MobclickAgent.updateOnlineConfig(this);
        SharedPreUtil.init(this);

        // 设置输出运行时日志
        // UMGameAgent.setDebugMode(true);
        // UMGameAgent.init(this);
        // Deprecated UMGameAgent.setPlayerLevel("LV.01");
        //  UMGameAgent.setPlayerLevel(1);
        // UMGameAgent.setSessionContinueMillis(1000);
        // UMGameAgent.startWithConfigure(
        // new UMAnalyticsConfig(mContext, "4f83c5d852701564c0000011", "Umeng",
        // EScenarioType.E_UM_GAME));
        //  MobclickAgent.setScenarioType(this, EScenarioType.E_UM_GAME);

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                Log.e("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub

            }
        };
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                Log.d("app", "onDownloadFinish");
            }

            @Override
            public void onInstallFinish(int i) {
                Log.d("app", "onInstallFinish");
            }

            @Override
            public void onDownloadProgress(int i) {
                Log.d("app", "onDownloadProgress:" + i);
            }
        });

        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    private void initImageLoader(Context context) {
        int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 5);
        MemoryCacheAware<String, Bitmap> memoryCache;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            memoryCache = new LruMemoryCache(memoryCacheSize);
        } else {
            memoryCache = new LRULimitedMemoryCache(memoryCacheSize);
        }

        mNormalImageOptions = new DisplayImageOptions.Builder().bitmapConfig(Config.RGB_565).cacheInMemory(true).cacheOnDisc(true)
                .resetViewBeforeLoading(true).build();

        // This
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(mNormalImageOptions)
                .denyCacheImageMultipleSizesInMemory().discCache(new UnlimitedDiscCache(new File(IMAGES_FOLDER)))
                // .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .memoryCache(memoryCache)
                // .memoryCacheSize(memoryCacheSize)
                .tasksProcessingOrder(QueueProcessingType.LIFO).threadPriority(Thread.NORM_PRIORITY - 2).threadPoolSize(3).build();

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    @Override
    public void onTerminate() {
        // TODO Auto-generated method stub
        super.onTerminate();
    }

    private void initLocation() {
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        synchronized (objLock) {
            if (client == null) {
                client = new LocationClient(context);
                client.setLocOption(getDefaultLocationClientOption());
                client.registerLocationListener(mListener);
                start();
            }
        }

    }

    /***
     *
     * @return DefaultLocationClientOption
     */
    public LocationClientOption getDefaultLocationClientOption() {
        if (mOption == null) {
            mOption = new LocationClientOption();
            mOption.setLocationMode(LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
            mOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
            mOption.setScanSpan(3000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
            mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
            mOption.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
            mOption.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
            mOption.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
            mOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
            mOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
            mOption.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
            mOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        }
        return mOption;
    }

    public void start() {
        synchronized (objLock) {
            if (client != null && !client.isStarted()) {
                client.start();
            }
        }
    }

    public void stop() {
        synchronized (objLock) {
            if (client != null && client.isStarted()) {
                client.stop();
            }
        }
    }

    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                //WriteLog.getInstance().writeLog(location.getLongitude()+","+location.getLatitude());
				/*Log.e("lbb", "getCountryCode"+location.getCountryCode());
				Log.e("lbb", "getCountry"+location.getCountry());
				Log.e("lbb", "getCityCode"+location.getCityCode());
				Log.e("lbb", "getCity"+location.getCity());
				Log.e("lbb", "getDistrict"+location.getDistrict());
				Log.e("lbb", "getStreet"+location.getStreet());
				Log.e("lbb", "getAddrStr"+location.getAddrStr());
				Log.e("lbb", "getLocationDescribe"+location.getLocationDescribe());
				Log.e("lbb", "getDirection"+location.getDirection());*/
                if (location.getAddrStr() != null) {
                    SharedPreUtil.putStringValue(context, SHA_LAST_ADDR, location.getAddrStr());
                }
				/*if(location.getCity()!=null){
					SharedPreUtil.putStringValue(context, SHA_LAST_CITY, location.getCity());
				}*/
            }
        }

    };

    public void put(String key, Object data) {
        datas.put(key, data);
    }

    public void clear() {
        datas.clear();
    }

    public Object get(String key) {
        return datas.get(key);
    }

    public static void clearActivity() {
        for (int i = 0; i < BaseActivity.activityList.size(); i++) {
            Activity activity = BaseActivity.activityList.get(i);
            if (activity != null)
                activity.finish();
        }
    }


    public void onAppExit() {

        SharedPreUtil.commit(context);

        ToastUtil.cancelToast();
        HomeFragment.isStop = true;
        BarrageView.isStop = true;

        datas.clear();
        MobclickAgent.onKillProcess(context);
        clearActivity();
    }

    public void onError() {
        // TODO Auto-generated method stub
        onAppExit();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
