package com.game.helper.sdk.model.returns;

public class CreateGameOrder {
/*
	"data": {
    "orderInfo": 
             "partner=\"2088421982372415\"
             &seller_id=\"123811689@qq.com
              \"&out_trade_no=\"ptb_20161027013555779Z1R6O\"
              &subject=\"充值游戏\"
              &body=\"充值游戏\"
              &total_fee=\"50\"
              &notify_url=\"http://ghelper.h5h5h5.cn/helper/pay/alipayCallback
              \"&service=\"mobile.securitypay.pay
              \"&payment_type=\"1\"&_input_charset=\"utf-8
              \"&it_b_pay=\"30m\
              "&return_url=\"m.alipay.com
              \"&sign=\"Q1iQWqtLFztz6GZhnfezut2AjD7nDyXQLx4qvy9mKOQ3
                        lqzy8asFHJoPrvacwiBgqDUcDBHGsg0VCHxLMhnjQLBjnjtPvm3K
                         UAduHvCYqxbNQU%2FHlgmfiTIGYEPikMfvA2pIdo8FH%2F3VId
                         UA%2FOXYN3mkqO9qGHGytcvP%2FJYtlUo%3D\"
              &sign_type=\"RSA\"",
    "payKind": "game",
    "tradeNo": "ptb_20161027013555779Z1R6O",
    "accountPayMoney": "-5.99",
    "thirdPayMoney": "20",
    "wxOrderInfo": "",
    "payChannel": "alipay"
},*/
	public GameOrder data;
	public class  GameOrder{
		public String orderInfo;
		public String payKind;
		public String tradeNo;
		public String accountPayMoney;
		public String thirdPayMoney;
		public String wxOrderInfo;
		public String payChannel;
	}
}
