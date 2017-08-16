package com.game.helper.pay.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.json.JSONException;
import org.json.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.game.helper.R;
import com.game.helper.net.task.CreateGameOrderTask;
import com.game.helper.sdk.model.returns.CreateGameOrder;
import com.game.helper.util.ToastUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
/**
 * 游戏充值接口
 * */
@SuppressLint("HandlerLeak")
public class PayAlipay {
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_CHECK_FLAG = 2;
	private Activity mContext;
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
	public String orderID=null;
	public String price=null;

	public interface setEnabledCallback{
		public void callback(boolean is);
	}
	private setEnabledCallback msetEnabledCallback=null;

	public void setMsetEnabledCallback(setEnabledCallback msetEnabledCallback) {
		this.msetEnabledCallback = msetEnabledCallback;
	}

	// 支付宝支付完成接口
	public interface OnAliPayFinishListener {
		public void onAliPayFinish(boolean isSuccess, String orderID, String price);
	}

	private OnAliPayFinishListener mOnAliPayFinishListener = null;

	public void setOnAliPayFinishListener(OnAliPayFinishListener l) {
		mOnAliPayFinishListener = l;
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				/**
				 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
				 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
				 * docType=1) 建议商户依赖异步通知
				 * 
				 * 9000  订单支付成功
				 * 8000 正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
				 * 4000 订单支付失败
				 * 5000 重复请求
				 * 6001 用户中途取消
				 * 6002 网络连接出错
				 * 6004 支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
				 * 其它  其它支付错误
				 */
				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息
				String resultStatus = payResult.getResultStatus();
				Log.e("lbb", "--l-------resultStatus-------:" + resultStatus);
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					//Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
					if (mOnAliPayFinishListener != null) {
						mOnAliPayFinishListener.onAliPayFinish(true, orderID, price);
					}
					resultSure("1",orderID);
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")||TextUtils.equals(resultStatus, "6004")) {
						Toast.makeText(mContext, "支付结果确认中", Toast.LENGTH_SHORT).show();
						if (mOnAliPayFinishListener != null)
							mOnAliPayFinishListener.onAliPayFinish(false,orderID,price);
					}else if (TextUtils.equals(resultStatus, "6001")) {
						//用户中途取消
						if(msetEnabledCallback!=null){
							msetEnabledCallback.callback(true);
						}
					} 
					else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						//Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
						if (mOnAliPayFinishListener != null)
							mOnAliPayFinishListener.onAliPayFinish(false,orderID,price);

					}
					resultSure("-1",orderID);
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(mContext, "检查结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
		};
	};

	private void resultSure(String retStatus,String orderID) {
		// 获取支付订单
		new CreateGameOrderTask(mContext, userId, gameId,
				gameAccount, payWay, orderType,
				ptb, money, realPay, saveMoney
				,retStatus,orderID, new com.game.helper.net.base.BaseBBXTask.Back() {

			@Override
			public void success(Object object, String msg) {
				//ToastUtil.showToast(mContext, "订单确认完成");
			}

			@Override
			public void fail(String status, String msg, Object object) {
				//ToastUtil.showToast(mContext, "订单确认失败");
			}
		}).start();
	}

	public PayAlipay(Activity context,String userId,String gameId,String gameAccount
			,String orderType,String ptb,String money,/*String card,*/String realPay
			,String saveMoney) {
		this.mContext = context;
		this.userId=userId;
		this.gameId=gameId;
		this.gameAccount=gameAccount;
		this.payWay="alipay";
		this.orderType=orderType;
		this.ptb=ptb;
		this.money=money;
		//this.card=card;
		this.realPay=realPay;
		this.saveMoney=saveMoney;
	}

	/**
	 * 传入的价钱请以10.00元格式传入。。。 价钱,微信是以分为单位，支付是以元为单位，所以微信为整形，支付为double，注意检查
	 */
	public void pay() {
		// 获取支付订单
		new CreateGameOrderTask(mContext, userId, gameId,
				gameAccount, payWay, orderType, 
				ptb, money, realPay, saveMoney
				,"0","", new com.game.helper.net.base.BaseBBXTask.Back() {

			@Override
			public void success(Object object, String msg) {
				if (object != null && object instanceof CreateGameOrder) {
					CreateGameOrder mOrder=(CreateGameOrder) object;
					if(mOrder.data!=null){
						orderID=mOrder.data.tradeNo;
						price=mOrder.data.thirdPayMoney;//单位是元
						if(!TextUtils.isEmpty(mOrder.data.orderInfo)){
							try {
								// 完整的符合支付宝参数规范的订单信息
								final String payInfo =mOrder.data.orderInfo;
								Log.e("lbb", "payInfo:" + payInfo);
								Runnable payRunnable = new Runnable() {

									@Override
									public void run() {
										// 构造PayTask 对象
										PayTask alipay = new PayTask(mContext);
										// 调用支付接口，获取支付结果
										String result = alipay.pay(payInfo, true);

										Message msg = new Message();
										msg.what = SDK_PAY_FLAG;
										msg.obj = result;
										mHandler.sendMessage(msg);
									}
								};
								// 必须异步调用
								Thread payThread = new Thread(payRunnable);
								payThread.start();

							}catch (Exception e) {
								e.printStackTrace();
							}
						}else{
							ToastUtil.showToast(mContext, "服务器未返回订单信息");
						}

					}
				}else{
					ToastUtil.showToast(mContext, R.string.no_network);
				}
				if(msetEnabledCallback!=null){
					msetEnabledCallback.callback(true);
				}

			}

			@Override
			public void fail(String status, String msg, Object object) {
				ToastUtil.showToast(mContext, "提交订单失败:"+msg);
				if(msetEnabledCallback!=null){
					msetEnabledCallback.callback(true);
				}
			}
		}).start();
	}

}
