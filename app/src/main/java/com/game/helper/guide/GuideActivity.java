package com.game.helper.guide;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.activity.LoginActivity;
import com.game.helper.activity.MainActivity;
import com.game.helper.db.manager.DBManager;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.util.IntentUtil;
import com.game.helper.util.LoginUtil;
import com.game.helper.util.SharedPreUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**

 *
 */
public class GuideActivity extends BaseActivity implements OnPageChangeListener {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.dots_top)
    LinearLayout dots_top;
    ViewPagerAdapter vpAdapter;
    private List<View> views;

    // 底部小点图片
    private ImageView[] dots;

    // 记录当前选中位置
    private int currentIndex;

    ImageHandler handler = new ImageHandler(new WeakReference<GuideActivity>(this));
    private int isLoginSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
          /*set it to be no title*/
        setNoTitle(this);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        isLoginSuccess = getIntent().getIntExtra(KEY_FIRSTUSER, 0); //1:登录成功，0：登录失败

        LayoutInflater inflater = LayoutInflater.from(this);
        LoginUtil.getHomeDate(mContext);

        views = new ArrayList<View>();
        // 初始化引导图片列表
        views.add(inflater.inflate(R.layout.guide_new_one, null));
        views.add(inflater.inflate(R.layout.guide_new_two, null));
        views.add(inflater.inflate(R.layout.guide_new_three, null));
        //views.add(inflater.inflate(R.layout.guide_new_four, null));
        views.add(inflater.inflate(R.layout.guide_new_five, null));

        // 初始化Adapter
        vpAdapter = new ViewPagerAdapter(views, this, isLoginSuccess);
        viewPager.setAdapter(vpAdapter);
        ViewPagerScroller scroller = new ViewPagerScroller(this);
        scroller.setScrollDuration(800);
        scroller.initViewPagerScroll(viewPager);//这个是设置切换过渡时间为0.8秒
        // 绑定回调
        viewPager.setOnPageChangeListener(this);
        setOnPlayTime(3000);
        setOnIsCirculate(false);
        // 初始化底部小点
        initDots(views.size());
        //开始轮播效果
        handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.time);
    }

    @Override
    protected void initData() {
        //OrderListManager.getInstance(GuideActivity.this).insert(1);
        SharedPreUtil.putIntValue(GuideActivity.this, "isFirstGuide", 1);
    }

    private void initDots(int count) {
        int chat_dot_margin_lr = (int) getResources().getDimension(
                R.dimen.chat_dot_margin_lr);
        int chat_dot_wh_big = (int) getResources().getDimension(
                R.dimen.chat_dot_wh_big2);
        for (int i = 0; i < count; i++) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    chat_dot_wh_big, chat_dot_wh_big);
            lp.leftMargin = chat_dot_margin_lr;
            lp.rightMargin = chat_dot_margin_lr;
            dots_top.addView(dotsItem(i), lp);
        }

        currentIndex = 0;
        dots_top.getChildAt(currentIndex).setSelected(true);
    }

    /**
     * ，底部小圆点
     *
     * @param position
     * @return
     */
    @SuppressWarnings("deprecation")
    private RelativeLayout dotsItem(int position) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dot_image, null);
        RelativeLayout iv = (RelativeLayout) layout.findViewById(R.id.face_dot);
        iv.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_circle_blue));
        iv.setId(position);
        return iv;
    }

    private void setCurrentDot(int position) {
        for (int i = 0; i < dots_top.getChildCount(); i++) {
            dots_top.getChildAt(i).setSelected(false);
        }
        dots_top.getChildAt(position).setSelected(true);

        currentIndex = position;
    }

    // 当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int arg0) {
        switch (arg0) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                handler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.time);
                break;
            default:
                break;
        }
    }

    // 当当前页面被滑动时调用
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    // 当新的页面被选中时调用
    @Override
    public void onPageSelected(int arg0) {
        // 设置底部小点选中状态
        setCurrentDot(arg0);
        handler.sendMessage(Message.obtain(handler, ImageHandler.MSG_PAGE_CHANGED, arg0, 0));
        if (arg0 == views.size() - 1) {
            //	mHandler.sendEmptyMessageDelayed(1, 6*1000);
        }
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (!ViewPagerAdapter.isClisck) {
                        // 设置已经引导
                        //Log.e("lbb", "-----------ViewPagerAdapter-------------");
                        setGuided();
                        goIntent();
                    }
                    break;
                default:
                    break;
            }
        }

    };

    private void goIntent() {

        if (isLoginSuccess == 1) {
            //jumpActivity(LoginTask.time_start, LoginTask.time_end,MainActivity.class);
            LoginData user = DBManager.getInstance(mContext).getUserMessage();
            jumpActivity(MainActivity.class, 0, null);
            LoginUtil.loginSuccess(mContext, user.userId);
        } else {
            jumpActivity(LoginActivity.class, 0, null);
        }

    }

    private void jumpActivity(Class<?> act, long time, Bundle bundle) {
        if (!isFinishing()) {
            IntentUtil.toActivity(mContext, act, time, bundle);
        }
    }

    /**
     * method desc：设置已经引导过了，下次启动不用再次引导
     */
    private void setGuided() {
        //存储数据
        //SharedPreEncryptUtil.putBooleanValue(activity, SharedPreEncryptUtil.isFirstIn, false);
        SharedPreUtil.putIntValue(mContext, "isFirstGuide", 1);
    }

    /**
     * 设置是否循环
     */
    public void setOnIsCirculate(boolean isCirculate) {
        handler.isCirculate = isCirculate;
    }

    /**
     * 设置是轮播时间
     */
    public void setOnPlayTime(long time) {
        handler.time = time;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPageEnd("GuideActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("GuideActivity");
        super.onResume();
    }
}
