package com.game.helper.activity.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.adapter.home.GameGiftsAdapter;
import com.game.helper.db.manager.DBManager;
import com.game.helper.model.home.Gift;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.ListGiftTask;
import com.game.helper.sdk.model.returns.ListGift;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.view.dialog.ShareDialog;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * @Description 某个游戏的礼包
 * @Path com.game.helper.activity.home.GiftsActivity.java
 * @Author lbb
 * @Date 2016年8月24日 下午5:45:31
 * @Company
 */
public class GiftsActivity extends BaseActivity implements PlatformActionListener {
    @BindView(R.id.gamegifts_listView)
    ListView gamegifts_listView;
    @BindView(R.id.topLeftBack)
    ImageView topLeftBack;
    @BindView(R.id.top_title)
    TextView topTitle;
    private GameGiftsAdapter mGameGiftsAdapter;
    private List<Gift> mList = new ArrayList<Gift>();
    LoginData user;
    String gameId, gameName;
    public String fileAskPath;
    public String logo;
    public String logoThumb;

    private ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_gifts);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        // 初始化ShareSDK
        ShareSDK.initSDK(this);
    }

    @Override
    protected void initView() {
        gameName = getIntent().getStringExtra("gameName");
        topTitle.setText(gameName + "礼包");
        topLeftBack.setVisibility(View.VISIBLE);

		/*topRight.setText("分享");
		topRight.setVisibility(View.VISIBLE);*/
		/*top_liner_right.setVisibility(View.VISIBLE);
		top_iv_right.setImageDrawable(getResources().getDrawable(R.drawable.shouye_09));*/

        mGameGiftsAdapter = new GameGiftsAdapter(mContext, mList);
        gamegifts_listView.setAdapter(mGameGiftsAdapter);

        gamegifts_listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Bundle bundle = new Bundle();
                bundle.putString("giftId", mGameGiftsAdapter.getmList().get(arg2).giftId);
                startActivity(GiftsDetailActivity.class, bundle);
            }
        });
    }

    @Override
    protected void initData() {

        gameId = getIntent().getStringExtra("gameId");
        fileAskPath = getIntent().getStringExtra("fileAskPath");
        logo = getIntent().getStringExtra("logo");
        logoThumb = getIntent().getStringExtra("logoThumb");
        user = DBManager.getInstance(mContext).getUserMessage();
        new ListGiftTask(mContext, true, user.userId, gameId, new Back() {

            @Override
            public void success(Object object, String msg) {
                if (object != null && object instanceof ListGift) {
                    ListGift mListGift = (ListGift) object;
                    if (mListGift.data != null && mListGift.data.size() >= 0) {
                        mList.clear();
                        mList.addAll(mListGift.data);
                        for (Gift mGift : mList) {
                            mGift.fileAskPath = fileAskPath;
                            mGift.logo = logo;
                            mGift.logoThumb = logoThumb;
                        }
                        mGameGiftsAdapter.setmList(mList);
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
    @OnClick({R.id.top_left_layout, R.id.top_right})//,R.id.top_liner_right
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left_layout:
                finish1();
                break;
            case R.id.top_right:
                shareDialog = new ShareDialog(this);
                shareDialog.setCancelButtonOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        shareDialog.dismiss();

                    }
                });
                shareDialog.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int arg2, long arg3) {
                        HashMap<String, Object> item = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
                        if (item.get("ItemText").equals("QQ好友")) {
                            ShareParams sp = new ShareParams();
                            sp.setTitle("快来领取:" + gameName + "的游戏礼包吧！");
                            sp.setTitleUrl("http://g9yx.com"); // 标题的超链接
                            sp.setText("立即下载G9游戏领取吧 ，还有更多福利等你拿。");
                            sp.setImageUrl("" + fileAskPath + logo);//分享网络图片

                            Platform qq = ShareSDK.getPlatform(QQ.NAME);
                            qq.setPlatformActionListener(GiftsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("QQ空间")) {
                            ShareParams sp = new ShareParams();
                            sp.setTitle("快来领取:" + gameName + "的游戏礼包吧！");
                            sp.setTitleUrl("http://g9yx.com"); // 标题的超链接
                            sp.setText("立即下载G9游戏领取吧 ，还有更多福利等你拿。");
                            sp.setImageUrl("" + fileAskPath + logo);//分享网络图片
                            sp.setSite("G9游戏");
                            sp.setSiteUrl("http://g9yx.com");

                            Platform qq = ShareSDK.getPlatform(QZone.NAME);
                            qq.setPlatformActionListener(GiftsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("新浪微博")) {
                            ShareParams sp = new ShareParams();
                            //sp.setTitle("测试分享的标题");
                            //sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                            sp.setText("快来领取:" + gameName + "的游戏礼包吧！" + "立即下载G9游戏领取吧 ，还有更多福利等你拿。" + "http://g9yx.com");
                            sp.setImageUrl("" + fileAskPath + logo);//分享网络图片

                            Platform qq = ShareSDK.getPlatform(SinaWeibo.NAME);
                            qq.setPlatformActionListener(GiftsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("微信好友")) {
                            ShareParams sp = new ShareParams();
                            sp.setTitle("快来领取:" + gameName + "的游戏礼包吧！");
                            //sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                            sp.setText("立即下载G9游戏领取吧 ，还有更多福利等你拿。");
                            sp.setImageUrl("" + fileAskPath + logo);//分享网络图片
                            sp.setUrl("http://g9yx.com");
                            sp.setShareType(Platform.SHARE_WEBPAGE);

                            Platform qq = ShareSDK.getPlatform(Wechat.NAME);
                            qq.setPlatformActionListener(GiftsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("朋友圈")) {
                            ShareParams sp = new ShareParams();
                            sp.setTitle("快来领取:" + gameName + "的游戏礼包吧！" + "立即下载G9游戏搜索游戏Id：" + gameId + "即可领取 ");
                            //sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                            sp.setText("立即下载G9游戏领取吧 ，还有更多福利等你拿。");//设置了不会显示,可选参数
                            sp.setImageUrl("" + fileAskPath + logo);//分享网络图片
                            sp.setUrl("http://g9yx.com");
                            sp.setShareType(Platform.SHARE_WEBPAGE);

                            Platform qq = ShareSDK.getPlatform(WechatMoments.NAME);
                            qq.setPlatformActionListener(GiftsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else {
                            Toast.makeText(GiftsActivity.this, "您点中了" + item.get("ItemText"), Toast.LENGTH_LONG).show();
                        }
                        shareDialog.dismiss();

                    }
                });
                break;
            default:
                super.onClick(v);
                break;
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

    @Override
    protected void onPause() {
        MobclickAgent.onPageEnd("GiftsActivity");
        super.onPause();
    }

    int i = 0;

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("GiftsActivity");
        super.onResume();
        if (i == 0) {
            i = 1;
        } else {
            new ListGiftTask(mContext, true, user.userId, gameId, new Back() {

                @Override
                public void success(Object object, String msg) {
                    if (object != null && object instanceof ListGift) {
                        ListGift mListGift = (ListGift) object;
                        if (mListGift.data != null && mListGift.data.size() >= 0) {
                            mList.clear();
                            mList.addAll(mListGift.data);
                            for (Gift mGift : mList) {
                                mGift.fileAskPath = fileAskPath;
                                mGift.logo = logo;
                                mGift.logoThumb = logoThumb;
                            }
                            mGameGiftsAdapter.setmList(mList);
                        }
                    }

                }

                @Override
                public void fail(String status, String msg, Object object) {
                    // TODO Auto-generated method stub

                }
            }).start();
        }
    }

    @Override
    public void onCancel(Platform arg0, int arg1) {//回调的地方是子线程，进行UI操作要用handle处理
        handler.sendEmptyMessage(2);

    }

    @Override
    public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {//回调的地方是子线程，进行UI操作要用handle处理
        if (arg0.getName().equals(QQ.NAME)) {// 判断成功的平台是不是QQ
            handler.sendEmptyMessage(1);
        } else if (arg0.getName().equals(QZone.NAME)) {// 判断成功的平台是不是QQ空间
            handler.sendEmptyMessage(4);
        } else if (arg0.getName().equals(SinaWeibo.NAME)) {// 判断成功的平台是不是新浪
            handler.sendEmptyMessage(5);
        } else if (arg0.getName().equals(Wechat.NAME)) {// 判断成功的平台是不是微信
            handler.sendEmptyMessage(6);
        } else if (arg0.getName().equals(WechatMoments.NAME)) {// 判断成功的平台是不是微信朋友圈
            handler.sendEmptyMessage(7);
        }


    }

    @Override
    public void onError(Platform arg0, int arg1, Throwable arg2) {//回调的地方是子线程，进行UI操作要用handle处理
        arg2.printStackTrace();
        Message msg = new Message();
        msg.what = 3;
        msg.obj = arg2.getMessage();
        handler.sendMessage(msg);
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(getApplicationContext(), "QQ分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 4:
                    Toast.makeText(getApplicationContext(), "QQ空间分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 5:
                    Toast.makeText(getApplicationContext(), "新浪微博分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 6:
                    Toast.makeText(getApplicationContext(), "微信好友分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 7:
                    Toast.makeText(getApplicationContext(), "微信朋友圈分享成功", Toast.LENGTH_LONG).show();
                    break;

                case 2:
                    Toast.makeText(getApplicationContext(), "取消分享", Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Toast.makeText(getApplicationContext(), "分享失败", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }

    };
}
