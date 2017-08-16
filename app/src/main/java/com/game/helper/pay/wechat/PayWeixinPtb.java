package com.game.helper.pay.wechat;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.game.helper.R;
import com.game.helper.net.task.CreateGameOrderTask;
import com.game.helper.net.task.CreateOrderTask;
import com.game.helper.pay.wechat.PayWeixin.setEnabledCallbackWX;
import com.game.helper.sdk.model.returns.CreateOrder;
import com.game.helper.util.ToastUtil;
import com.game.helper.wxapi.WXPayEntryActivity;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class PayWeixinPtb {
	private PayReq req;
	private IWXAPI msgApi;
	private static Context context;
	public static String userId;
	public static String payWay;
	public static String ptb;
	public static String money;
	public String card;

	
	private setEnabledCallbackWX msetEnabledCallback=null;
	private static String tradeNo;

	public void setMsetEnabledCallbackWX(setEnabledCallbackWX msetEnabledCallback) {
		this.msetEnabledCallback = msetEnabledCallback;
	}
	public PayWeixinPtb(Activity context,String userId,String ptb,String money) {
		this.userId=userId;
		this.payWay="weixin";
		this.ptb=ptb;
		this.money=money;
		//this.card=card;
		this.context = context;
	}

	public void pay() {
		//获取支付订单id
		new CreateOrderTask(context, userId, money,
				ptb, payWay, "0","", new com.game.helper.net.base.BaseBBXTask.Back() {
			
			@Override
			public void success(Object object, String msg) {
				if (object != null && object instanceof CreateOrder) {
					CreateOrder mOrder=(CreateOrder) object;
					if(mOrder.data!=null){
						tradeNo = mOrder.data.tradeNo;
						if(WXPayEntryActivity.mOnWeiXinPayFinishListener!=null){
							WXPayEntryActivity.mOnWeiXinPayFinishListener.onWeiXinPaying(
									mOrder.data.tradeNo,""+(
									TextUtils.isEmpty(mOrder.data.thirdPayMoney)?
											0:Double.parseDouble(mOrder.data.thirdPayMoney))/100);//微信返回的支付单位是分
						}
						genPayReq(mOrder.data.wxOrderInfo);
						/*if(!TextUtils.isEmpty(mOrder.data.wxOrderInfo)){
							
						}else if (!TextUtils.isEmpty(mOrder.data.payChannel)
								&& mOrder.data.payChannel.equals("weixin")) {
							//支付完成
							if(WXPayEntryActivity.mOnWeiXinPayFinishListener!=null)WXPayEntryActivity.mOnWeiXinPayFinishListener.onWeiXinPayFinish(true);
						}else{
							ToastUtil.showToast(context, "服务器未返回订单信息");
						}*/
					
					}
				}else{
					ToastUtil.showToast(context, R.string.no_network);
				}
				if(msetEnabledCallback!=null){
					msetEnabledCallback.callbackWX(true);
				}
			}
			
			@Override
			public void fail(String status, String msg, Object object) {
				ToastUtil.showToast(context, "提交订单失败:"+msg);
				if(msetEnabledCallback!=null){
					msetEnabledCallback.callbackWX(true);
				}
			}
		}).start();
		
	}

	/**
	 * 生成签名参数
	 */
	private void genPayReq(String data) {
		req = new PayReq();
		try {
			JSONObject json_data = new JSONObject(data);
			if(json_data!=null){
				req.appId = json_data.getString("appid");
				req.partnerId = json_data.getString("partnerid");
				req.prepayId = json_data.getString("prepayid");
				req.packageValue = json_data.getString("package");
				req.nonceStr = json_data.getString("noncestr");
				req.timeStamp = json_data.getString("timestamp");
				req.sign = json_data.getString("sign");
				Log.e("lbb", "---genPayReq-----");
			}
			msgApi = WXAPIFactory.createWXAPI(context, null);
			msgApi.registerApp(req.appId);
			msgApi.sendReq(req);
		}catch (JSONException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		WXPayEntryActivity.where=1;
	}


	public static void makeSurePtb(String makeSurePtb){
		new CreateOrderTask(context, userId, money,
				ptb, payWay, makeSurePtb, tradeNo,new com.game.helper.net.base.BaseBBXTask.Back() {

			@Override
			public void success(Object object, String msg) {
				//ToastUtil.showToast(context, "订单确认完成");
			}

			@Override
			public void fail(String status, String msg, Object object) {
				//ToastUtil.showToast(context, "订单确认失败");
			}
		}).start();
	}
}

