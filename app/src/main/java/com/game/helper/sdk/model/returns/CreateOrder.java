package com.game.helper.sdk.model.returns;

public class CreateOrder {

	/* "data": {
    "accountPayMoney": "-5.99",
    "thirdPayMoney": "10",
    "wxOrderInfo": "",
    "orderInfo": "partner=\"2088421982372415\"&seller_id=\"123811689@qq.com\"&out_trade_no=\"ptb_20161027011346123d3K8t\"&subject=\"金币\"&body=\"金币\"&total_fee=\"10\"&notify_url=\"http://ghelper.h5h5h5.cn/helper/pay/alipayCallback\"&service=\"mobile.securitypay.pay\"&payment_type=\"1\"&_input_charset=\"utf-8\"&it_b_pay=\"30m\"&return_url=\"m.alipay.com\"&sign=\"xO6i0V2ePEcO3D0AbRY1JqilnOa310zl7tHEy91U5hrPllDaA32GurUuj574aiHD%2BIf6pDDWKSiHqDcGbv8xlKVcDH2bwDXHpYTHXlf3FGJeNhIJNi9Rr%2F%2FLYNPL8DOftdLDXUW5GKEjWnPmnNWLWgRmgoSbwNfdQcyH9Dh1UCk%3D\"&sign_type=\"RSA\"",
    "tradeNo": "ptb_20161027011346123d3K8t",
    "payKind": "ptb",
    "payChannel": "alipay"
},*/
	public PtbOrder data;
	public class PtbOrder{
		public String accountPayMoney;
		public String thirdPayMoney;
		public String wxOrderInfo;
		public String orderInfo;
		public String tradeNo;
		public String payKind;
		public String payChannel;
	}
}
