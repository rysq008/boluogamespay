package com.game.helper.activity.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.activity.community.SociatyBaseActivity;
import com.game.helper.activity.community.SociatyDetailsActivity;
import com.game.helper.adapter.home.TaskCenterAwardAdapter;
import com.game.helper.clander.CalendarAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.GetPlatformSignTask;
import com.game.helper.net.task.GetPtbRuleTask;
import com.game.helper.net.task.JuedeSignTask;
import com.game.helper.net.task.PlatformSignTask;
import com.game.helper.sdk.model.returns.GetPlatformSign;
import com.game.helper.sdk.model.returns.GetPlatformSign.PlatformSign;
import com.game.helper.sdk.model.returns.GetPtbRule;
import com.game.helper.sdk.model.returns.GetPtbRule.PtbRule;
import com.game.helper.sdk.model.returns.JuedeSign;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.widget.ListViewForScrollView;
import com.umeng.analytics.MobclickAgent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("SimpleDateFormat")
public class TaskCenterActivity extends BaseActivity {

    @BindView(R.id.tv_days)
    TextView tv_days;
    @BindView(R.id.tv_month)
    TextView tv_month;

    @BindView(R.id.btn_login)
    Button btn_login;
    TaskCenterAwardAdapter mTaskCenterAwardAdapter;
    @BindView(R.id.award_listView)
    ListViewForScrollView award_listView;
    @BindView(R.id.mScrollView)
    ScrollView mScrollView;
    List<PtbRule> mList = new ArrayList<PtbRule>();
    int num = 0;
    GridView gridView;

    private static int jumpMonth = 0;      //每次滑动，增加或减去一个月,默认为0（即显示当前月）
    private static int jumpYear = 0;       //滑动跨越一年，则增加或者减去一年,默认为0(即当前年)
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    private List<PlatformSign> data = new ArrayList<PlatformSign>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_task_center);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        topTitle.setText("任务中心");
        user = DBManager.getInstance(mContext).getUserMessage();

        topLeftBack.setVisibility(View.VISIBLE);
        mTaskCenterAwardAdapter = new TaskCenterAwardAdapter(mContext, mList);
        award_listView.setAdapter(mTaskCenterAwardAdapter);
        award_listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PtbRule mPtbRule = mTaskCenterAwardAdapter.getmList().get(position);
                if (mPtbRule != null && !TextUtils.isEmpty(mPtbRule.ruleCode)) {
                    if (mPtbRule.ruleCode.equals("invite")
                            || mPtbRule.ruleCode.equals("friendCircle")
                            || mPtbRule.ruleCode.equals("qq")
                            || mPtbRule.ruleCode.equals("weibo")
                            || mPtbRule.ruleCode.equals("qqFriend")
                            || mPtbRule.ruleCode.equals("weixinFriend")) {//邀请好友
                        startActivity(InviteFriendsActivity.class);
                    } else if (mPtbRule.ruleCode.equals("pSign")) {//平台签到

                    } else if (mPtbRule.ruleCode.equals("gSign")) {//工会签到
                        if (!TextUtils.isEmpty(user.guildId) && !user.guildId.equals("0")) {
                            Bundle bundle = new Bundle();
                            bundle.putString("guildId", user.guildId);
                            startActivity(SociatyDetailsActivity.class, bundle);
                        } else {
                            startActivity(SociatyBaseActivity.class);
                        }
                    } else if (mPtbRule.ruleCode.equals("publish")) {//发布攻略
                        /*Bundle bundle=new Bundle();
						bundle.putString("gameId", gameId);
						startActivity(ReleaseStrategyActivity.class,bundle);*/
                    } else if (mPtbRule.ruleCode.equals("pl")) {//评论攻略/资讯

                    }
                }
            }
        });
        CalendarActivity();
        topText = (TextView) findViewById(R.id.tv_month);
        calV = new CalendarAdapter(this, getResources(), jumpMonth, jumpYear, year_c, month_c, day_c, data);
        addGridView();
        gridView.setAdapter(calV);
    }

    LoginData user;

    @Override
    protected void initData() {
        new JuedeSignTask(mContext, user.userId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof JuedeSign) {
                    JuedeSign mJuedeSign = (JuedeSign) object;
                    if (!TextUtils.isEmpty(mJuedeSign.data)
                            && mJuedeSign.data.equals("N")) {
                        btn_login.setEnabled(true);
                        btn_login.setText("签到");
                    } else {
                        btn_login.setText("已签到");
                        btn_login.setEnabled(false);
                    }

                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                btn_login.setText("已签到");
                btn_login.setEnabled(false);
            }
        }).start();
        new GetPtbRuleTask(mContext, true, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetPtbRule) {
                    GetPtbRule mGetPtbRule = (GetPtbRule) object;
                    if (mGetPtbRule.data != null) {
                        mList.clear();
                        mList.addAll(mGetPtbRule.data);
                        mTaskCenterAwardAdapter.setmList(mList);
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                // TODO Auto-generated method stub

            }
        }).start();

        setDatas();


    }

    public void setDatas() {
        //2016-10
        Date dates = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String cDate = sdf.format(dates);  //当期日期

        new GetPlatformSignTask(mContext, user.userId, cDate, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof GetPlatformSign) {
                    GetPlatformSign mGetPlatformSign = (GetPlatformSign) object;
                    if (mGetPlatformSign.data != null) {
                        data.clear();
                        data.addAll(mGetPlatformSign.data);
                        CalendarActivity();
                        jumpMonth = 0;
                        jumpYear = 0;
                        addGridView();   //添加一个gridView
                        year_c = Integer.parseInt(currentDate.split("-")[0]);
                        month_c = Integer.parseInt(currentDate.split("-")[1]);
                        day_c = Integer.parseInt(currentDate.split("-")[2]);
                        calV = new CalendarAdapter(TaskCenterActivity.this, getResources(),
                                jumpMonth, jumpYear, year_c, month_c, day_c, data);
                        gridView.setAdapter(calV);
                        if (mGetPlatformSign.data.size() > 0) {
                            PlatformSign mPlatformSign = mGetPlatformSign.data.get(mGetPlatformSign.data.size() - 1);
                            tv_days.setText("已连续签到" + mPlatformSign.days + "天");
                            ;//已连续签到0天
                        }
                    }
                }

            }

            @Override
            public void fail(String status, String msg, Object object) {
                // TODO Auto-generated method stub

            }
        }).start();
    }

    @Override
    @OnClick({R.id.top_left_layout, R.id.btn_login})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.btn_login:
                if (btn_login.isEnabled()) {
                    btn_login.setEnabled(false);
                    new PlatformSignTask(mContext, user.userId, new Back() {

                        @Override
                        public void success(Object object, String msg) {
                            // TODO Auto-generated method stub
                            ToastUtil.showToast(mContext, "签到成功");
                            btn_login.setText("已签到");
                            btn_login.setEnabled(false);
                            setDatas();
                        }

                        @Override
                        public void fail(String status, String msg, Object object) {
                            // TODO Auto-generated method stub
                            ToastUtil.showToast(mContext, msg);
                            btn_login.setEnabled(true);
                        }
                    }).start();
                }

                break;
            default:
                super.onClick(v);
                break;
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        MobclickAgent.onPageStart("TaskCenterActivity");
        super.onResume();
        if (num == 0) {
            num = 1;
            mScrollView.post(new Runnable() {
                public void run() {
                    mScrollView.fullScroll(ScrollView.FOCUS_UP);
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            finish1();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private CalendarAdapter calV = null;
    private TextView topText = null;
    private int year_c = 0;
    private int month_c = 0;
    private int day_c = 0;
    private String currentDate = "";

    private String ruzhuTime;
    private String lidianTime;
    private String state = "";

    private Bundle bd = null;//发送参数
    private Bundle bun = null;//接收参数

    public void CalendarActivity() {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        currentDate = sdf.format(date);  //当期日期
        year_c = Integer.parseInt(currentDate.split("-")[0]);
        month_c = Integer.parseInt(currentDate.split("-")[1]);
        day_c = Integer.parseInt(currentDate.split("-")[2]);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        tv_month.setText("" + sdf1.format(date) + " (" + format.format(date) + ")");

    }

    //添加gridview
    private void addGridView() {

        gridView = (GridView) findViewById(R.id.gridview);


        gridView.setOnItemClickListener(new OnItemClickListener() {
            //gridView中的每一个item的点击事件

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {/*
				//点击任何一个item，得到这个item的日期(排除点击的是周日到周六(点击不响应))
				int startPosition = calV.getStartPositon();
				int endPosition = calV.getEndPosition();
				if(startPosition <= position+7  && position <= endPosition-7){
					String scheduleDay = calV.getDateByClickItem(position).split("\\.")[0];  //这一天的阳历
					//String scheduleLunarDay = calV.getDateByClickItem(position).split("\\.")[1];  //这一天的阴历
					String scheduleYear = calV.getShowYear();
					String scheduleMonth = calV.getShowMonth();
					//		                  Toast.makeText(CalendarActivity.this, scheduleYear+"-"+scheduleMonth+"-"+scheduleDay, 2000).show();
					ruzhuTime=scheduleMonth+"月"+scheduleDay+"日";	                  
					lidianTime=scheduleMonth+"月"+scheduleDay+"日";       
					Intent intent=new Intent();
					if(state.equals("ruzhu"))
					{

						bd.putString("ruzhu", ruzhuTime);
						System.out.println("ruzhuuuuuu"+bd.getString("ruzhu"));
					}else if(state.equals("lidian")){

						bd.putString("lidian", lidianTime);
					}
					//		                intent.setClass(CalendarActivity.this, HotelActivity.class);	             
					//		                intent.putExtras(bd);
					//		                startActivity(intent);
					//		                finish();
				}
			*/
            }

        });
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPageEnd("TaskCenterActivity");
        super.onPause();
    }

}