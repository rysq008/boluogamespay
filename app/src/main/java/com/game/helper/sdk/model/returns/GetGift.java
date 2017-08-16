package com.game.helper.sdk.model.returns;
/**
 * @Description
 * @Path com.game.helper.sdk.model.returns.GetGift.java
 * @Author lbb
 * @Date 2016年10月8日 上午10:53:05
 * @Company 
 */
public class GetGift {

/*
    "data": {
        "amount": 198,
        "content": "恭喜您成功领取礼包，礼包码已保存至我的礼包中，请尽快使用哦",
        "title": "恭喜，成功领取礼包",
        "getCode": "1475820825253"
    },*/
	public Gif data;
	public class Gif{
		public String amount;
		public String content;
		public String title;
		public String getCode;
	} 
}
