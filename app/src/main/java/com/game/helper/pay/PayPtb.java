package com.game.helper.pay;

import com.game.helper.R;
import com.game.helper.net.task.CreateGameOrderTask;
import com.game.helper.sdk.model.returns.CreateGameOrder;
import com.game.helper.util.ToastUtil;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

/**
 * @Description
 * @Path com.game.helper.pay.PayPtb.java
 * @Author lbb
 * @Date 2016年11月9日 下午9:14:32
 * @Company 
 */
public class PayPtb {

	private Context context;
	public String userId;
	public String gameId;
	public String gameAccount;
	public String payWay;
	public String orderType;
	public String ptb;
	public String money;
	public String card;
	public String realPay;
	public String saveMoney;
	public interface setEnabledCallbackPayPtb{
		public void callbackPayPtb(boolean is);
	}
	private setEnabledCallbackPayPtb msetEnabledCallback=null;

	public void setMsetEnabledCallbackPayPtb(setEnabledCallbackPayPtb msetEnabledCallback) {
		this.msetEnabledCallback = msetEnabledCallback;
	}
	public PayPtb(Activity context,String userId,String gameId,String gameAccount
			,String orderType,String ptb,String money/*,String card*/,String realPay
			,String saveMoney) {
		this.userId=userId;
		this.gameId=gameId;
		this.gameAccount=gameAccount;
		this.payWay="ptb";
		this.orderType=orderType;
		this.ptb=ptb;
		this.money=money;
		this.card=card;
		this.realPay=realPay;
		this.saveMoney=saveMoney;
		this.context = context;
	}
	public void pay() {
		//获取支付订单id
		new CreateGameOrderTask(context, userId, gameId,
				gameAccount, payWay, orderType, 
				ptb, money, /*card,*/ realPay, saveMoney
				,"0","", new com.game.helper.net.base.BaseBBXTask.Back() {

			@Override
			public void success(Object object, String msg) {
				if (object != null && object instanceof CreateGameOrder) {
					CreateGameOrder mOrder=(CreateGameOrder) object;
					if(mOrder.data!=null){
						
						if (!TextUtils.isEmpty(mOrder.data.payChannel)
								&& mOrder.data.payChannel.equals("ptb")) {
							//支付完成
							if(msetEnabledCallback!=null){
								msetEnabledCallback.callbackPayPtb(true);
							}
						}else{
							ToastUtil.showToast(context, "服务器未返回订单信息");
							if(msetEnabledCallback!=null){
								msetEnabledCallback.callbackPayPtb(false);
							}
						}
					}else{
						ToastUtil.showToast(context, "服务器未返回订单信息");
						if(msetEnabledCallback!=null){
							msetEnabledCallback.callbackPayPtb(false);
						}
					}

				}else{
					ToastUtil.showToast(context, R.string.no_network);
					if(msetEnabledCallback!=null){
						msetEnabledCallback.callbackPayPtb(false);
					}
				}
				

			}

			@Override
			public void fail(String status, String msg, Object object) {
				ToastUtil.showToast(context, "提交订单失败:"+msg);
				if(msetEnabledCallback!=null){
					msetEnabledCallback.callbackPayPtb(false);
				}
			}
		}).start();
	}
}
