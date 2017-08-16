package com.game.helper.activity.home;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.mine.MineInvitedFriendsActivity;
import com.game.helper.activity.mine.MineProceedsActivity;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.InsertShareTask;
import com.game.helper.sdk.Config;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.util.QRCodeUtil;
import com.game.helper.util.SystemUtil;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.dialog.ShareDialog1;
import com.umeng.analytics.MobclickAgent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

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
 * @Description 邀请好友
 * @Path com.game.helper.activity.home.InviteFriendsActivity.java
 * @Author lbb
 * @Date 2016年8月24日 下午7:08:40
 * @Company
 */
public class InviteFriendsActivity extends BaseActivity implements PlatformActionListener {
    @BindView(R.id.topRight1)
    TextView topRight1;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv_save)
    TextView tv_save;


    @BindView(R.id.top_title2)
    RelativeLayout top_title2;

    LoginData user;
    ShareDialog1 shareDialog;
    Bitmap bitmap1;
    String fp;
    @BindView(R.id.share_list)
    TextView shareList;
    @BindView(R.id.share_get)
    TextView shareGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_invite_friends);
        ButterKnife.bind(this);
        // 初始化ShareSDK
        ShareSDK.initSDK(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        user = DBManager.getInstance(mContext).getUserMessage();
        /*topTitle.setText("邀请好友");
		topLeftBack.setVisibility(View.VISIBLE);
		topRight.setText("邀请攻略");//?tv_code
		topRight.setVisibility(View.VISIBLE);*/
        fp = Environment.getExternalStorageDirectory() + File.separator + "G9Games2016_00001.png";
        bitmap1 = QRCodeUtil.createQRImage(Config.getInstance().IP + "/helper/app/toBannerIndex?inviteCode=" + user.userId, 600, 600, null, fp);//Config.getInstance().IP+"/helper/cversion/loadapk"
        iv.setImageBitmap(QRCodeUtil.createQRImage(Config.getInstance().IP + "/helper/app/toBannerIndex?inviteCode=" + user.userId, 320, 320, null, ""));
    }

    @Override
    protected void initData() {

        if (!TextUtils.isEmpty(user.field5)) {
            //tv_code.setText("邀请码：" + user.field5);
            //tv_code.setVisibility(View.VISIBLE);
        }
    }

    @Override
    @OnClick({R.id.top_left_layout1, R.id.topRight1, R.id.tv_save, R.id.top_title2,R.id.share_list, R.id.share_get})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_list:
                //邀请的好友
                startActivity(MineInvitedFriendsActivity.class);

                break;
            case R.id.share_get:
                //我的收益
                startActivity(MineProceedsActivity.class);
                break;
            case R.id.top_left_layout1:
                finish1();
                break;
            case R.id.top_title2:
                shareDialog = new ShareDialog1(this);
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
                        if (item.get("ItemText").equals("复制链接")) {
                            if (SystemUtil.getSDKVersionNumber() >= 11) {
                                ClipboardManager clipboardManager = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                                clipboardManager.setText(Config.getInstance().IP + "/helper/app/toBannerIndex?inviteCode=" + user.userId);
                            } else {
                                // 得到剪贴板管理器
                                android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, Config.getInstance().IP + "/helper/app/toBannerIndex?inviteCode=" + user.userId));
                            }
                            ToastUtil.showToast(mContext, "已复制");
						/*ShareParams sp = new ShareParams();
						sp.setTitle("您的好友"+user.nickName);
						sp.setTitleUrl(Config.getInstance().IP+"/helper/app/toRegister?inviteCode="+user.userId); // 标题的超链接
						sp.setText("邀请您成为手游推广代理。 ");
						sp.setImageUrl(""+user.fileAskPath+user.icon);//分享网络图片
						
						Platform qq = ShareSDK.getPlatform(QQ.NAME);
						qq.setPlatformActionListener(InviteFriendsActivity.this); // 设置分享事件回调
						// 执行分享
						qq.share(sp);*/
                        } else if (item.get("ItemText").equals("QQ好友")) {
                            ShareParams sp = new ShareParams();
                            sp.setTitle("您的好友" + user.nickName);
                            sp.setTitleUrl(Config.getInstance().IP + "/helper/app/toBannerIndex?inviteCode=" + user.userId); // 标题的超链接
                            sp.setText("邀请您成为手游推广代理，千款游戏2.5折起，邀请好友充值返利10%，注册每天送现金。");
                            sp.setImageUrl(Config.getInstance().IP + "/upload/logo/logo.png");//分享网络图片

                            Platform qq = ShareSDK.getPlatform(QQ.NAME);
                            qq.setPlatformActionListener(InviteFriendsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("QQ空间")) {
                            ShareParams sp = new ShareParams();
                            sp.setTitle("您的好友" + user.nickName);
                            sp.setTitleUrl(Config.getInstance().IP + "/helper/app/toBannerIndex?inviteCode=" + user.userId); // 标题的超链接
                            sp.setText("邀请您成为手游推广代理，千款游戏2.5折起，邀请好友充值返利10%，注册每天送现金。");
                            sp.setImageUrl(Config.getInstance().IP + "/upload/logo/logo.png");//分享网络图片
                            sp.setSite("G9游戏");
                            sp.setSiteUrl(Config.getInstance().IP + "/helper/app/toBannerIndex?inviteCode=" + user.userId);

                            Platform qq = ShareSDK.getPlatform(QZone.NAME);
                            qq.setPlatformActionListener(InviteFriendsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("新浪微博")) {
                            ShareParams sp = new ShareParams();
                            //sp.setTitle("测试分享的标题");
                            //sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                            sp.setText("您的好友" + user.nickName + "邀请您成为手游推广代理，千款游戏2.5折起，邀请好友充值返利10%，注册每天送现金。" + Config.getInstance().IP + "/helper/app/toBannerIndex?inviteCode=" + user.userId);

                            sp.setImageUrl(Config.getInstance().IP + "/upload/logo/logo.png");//分享网络图片

                            Platform qq = ShareSDK.getPlatform(SinaWeibo.NAME);
                            qq.setPlatformActionListener(InviteFriendsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("微信好友")) {
                            ShareParams sp = new ShareParams();
                            sp.setTitle("您的好友" + user.nickName);
                            //sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                            sp.setText("邀请您成为手游推广代理，千款游戏2.5折起，邀请好友充值返利10%，注册每天送现金。");
                            sp.setImageUrl(Config.getInstance().IP + "/upload/logo/logo.png");//分享网络图片
                            sp.setUrl(Config.getInstance().IP + "/helper/app/toBannerIndex?inviteCode=" + user.userId);
                            sp.setShareType(Platform.SHARE_WEBPAGE);

                            Platform qq = ShareSDK.getPlatform(Wechat.NAME);
                            qq.setPlatformActionListener(InviteFriendsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else if (item.get("ItemText").equals("朋友圈")) {
                            ShareParams sp = new ShareParams();

                            //sp.setTitleUrl("http://sharesdk.cn"); // 标题的超链接
                            sp.setText("邀请您成为手游推广代理，千款游戏2.5折起，邀请好友充值返利10%，注册每天送现金。");//设置了不会显示,可选参数
                            sp.setTitle("您的好友" + user.nickName + "邀请您成为手游推广代理，千款游戏2.5折起，邀请好友充值返利10%，注册每天送现金。");
                            sp.setImageUrl(Config.getInstance().IP + "/upload/logo/logo.png");//分享网络图片  //+user.fileAskPath+user.icon
                            sp.setUrl(Config.getInstance().IP + "/helper/app/toBannerIndex?inviteCode=" + user.userId);
                            sp.setShareType(Platform.SHARE_WEBPAGE);

                            Platform qq = ShareSDK.getPlatform(WechatMoments.NAME);
                            qq.setPlatformActionListener(InviteFriendsActivity.this); // 设置分享事件回调
                            // 执行分享
                            qq.share(sp);
                        } else {
                            Toast.makeText(InviteFriendsActivity.this, "您点中了" + item.get("ItemText"), Toast.LENGTH_LONG).show();
                        }
                        shareDialog.dismiss();

                    }
                });
                break;
            case R.id.topRight1:
                //邀请攻略
                startActivity(InviteStrategyActivity.class);
                break;
            case R.id.tv_save:
                //Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.gamehelper);
                saveBitmap(bitmap1);
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

    /** 保存方法 *//*
	public void saveBitmap(Bitmap bitmap ) {

		try {
			File f = new File("/sdcard/games/G9Games_001.png");
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
			ToastUtil.showToast(mContext, "已保存在SD");
			Log.e("lbb", "已经保存");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}*/

    /**
     * 保存生成的二維碼圖片
     */
    private void saveBitmap(Bitmap bitmap) {

        final File qrImage = new File(fp);
        if (qrImage.exists()) {
            qrImage.delete();
        }
        try {
            qrImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(qrImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
            fOut.close();
            ToastUtil.showToast(mContext, "已保存");
            saveImgToGallery(fp);
            MediaScannerConnection.scanFile(InviteFriendsActivity.this, new String[]
                    {Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath()
                            + "/" + qrImage.getParentFile().getAbsolutePath()}, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean saveImgToGallery(String filePath) {

        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (!sdCardExist)
            return false;
        try {
            ContentValues values = new ContentValues();
            values.put("datetaken", new Date().toString());
            values.put("mime_type", "image/jpg");
            values.put("_data", filePath);
            ContentResolver cr = BaseApplication.mInstance.getContentResolver();
            cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;

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
                    new InsertShareTask(InviteFriendsActivity.this, "qqFriend", user.userId, new Back() {

                        @Override
                        public void success(Object object, String msg) {


                        }

                        @Override
                        public void fail(String status, String msg, Object object) {


                        }
                    }).start();
                    Toast.makeText(getApplicationContext(), "QQ分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 4:
                    new InsertShareTask(InviteFriendsActivity.this, "qq", user.userId, new Back() {

                        @Override
                        public void success(Object object, String msg) {


                        }

                        @Override
                        public void fail(String status, String msg, Object object) {


                        }
                    }).start();
                    Toast.makeText(getApplicationContext(), "QQ空间分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 5:
                    new InsertShareTask(InviteFriendsActivity.this, "weibo", user.userId, new Back() {

                        @Override
                        public void success(Object object, String msg) {


                        }

                        @Override
                        public void fail(String status, String msg, Object object) {


                        }
                    }).start();
                    Toast.makeText(getApplicationContext(), "新浪微博分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 6:
                    new InsertShareTask(InviteFriendsActivity.this, "weixinFriend", user.userId, new Back() {

                        @Override
                        public void success(Object object, String msg) {


                        }

                        @Override
                        public void fail(String status, String msg, Object object) {


                        }
                    }).start();
                    Toast.makeText(getApplicationContext(), "微信好友分享成功", Toast.LENGTH_LONG).show();
                    break;
                case 7:
                    new InsertShareTask(InviteFriendsActivity.this, "friendCircle", user.userId, new Back() {

                        @Override
                        public void success(Object object, String msg) {


                        }

                        @Override
                        public void fail(String status, String msg, Object object) {


                        }
                    }).start();
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

    @Override
    protected void onPause() {
        MobclickAgent.onPageEnd("InviteFriendsActivity");
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onPageStart("InviteFriendsActivity");
        super.onResume();
    }

}