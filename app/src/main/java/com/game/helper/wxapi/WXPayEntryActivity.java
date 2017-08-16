package com.game.helper.wxapi;


import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.pay.wechat.Constants;
import com.game.helper.pay.wechat.PayWeixin;
import com.game.helper.pay.wechat.PayWeixinPtb;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler{
	public static int where;
	// 微信支付完成接口
	public interface OnWeiXinPayFinishListener {
		public void callbackWeiXin(boolean is);
		public void onWeiXinPayFinish(boolean isSuccess);
		public void onWeiXinPaying(String orderID, String price);
	}

	public static OnWeiXinPayFinishListener mOnWeiXinPayFinishListener = null;

	public static void setOnWeiXinPayFinishListener(OnWeiXinPayFinishListener l) {
		mOnWeiXinPayFinishListener = l;
	}
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private static IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	setContentView(R.layout.activity_pay_result);
        super.onCreate(savedInstanceState);
    	api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);

        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.e(TAG,"onPayFinish,errCode="+resp.errCode);

		if(resp.errCode==0){
			//支付完成
			if(mOnWeiXinPayFinishListener!=null)mOnWeiXinPayFinishListener.onWeiXinPayFinish(true);
			//订单确认
			if (where==0){
				PayWeixin.makeSure("1");
			}else if (where==1){
				PayWeixinPtb.makeSurePtb("1");
			}

		}else if(resp.errCode==-1){
			//支付失败
			if(mOnWeiXinPayFinishListener!=null)mOnWeiXinPayFinishListener.onWeiXinPayFinish(false);
			//订单确认
			if (where==0){
				PayWeixin.makeSure("-1");
			}else if (where==1){
				PayWeixinPtb.makeSurePtb("-1");
			}
		}else if(resp.errCode==-2){
			//用户取消 无需处理。发生场景：用户不支付了，点击取消，返回APP。 
			if(mOnWeiXinPayFinishListener!=null)mOnWeiXinPayFinishListener.callbackWeiXin(true);

			//订单确认
			if (where==0){
				PayWeixin.makeSure("-1");
			}else if (where==1){
				PayWeixinPtb.makeSurePtb("-1");
			}
		}
        finish();

	}
	public static boolean isWXAppInstalledAndSupported(Context context) {
		api = WXAPIFactory.createWXAPI(context, null);
		boolean sIsWXAppInstalledAndSupported = api.isWXAppInstalled()
				&& api.isWXAppSupportAPI();
		if (!sIsWXAppInstalledAndSupported) {
		}

		return sIsWXAppInstalledAndSupported;
	}


}