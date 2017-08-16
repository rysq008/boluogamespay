package com.game.helper.sdk.model.returns;

import java.util.List;

public class GetMyGameOrder {

	/*"data":[
	{"status":"Y",
	"userName":null,
	"gameId":1,
	"orderId":1,
     "userId":10000,
     "field1":"image/20161023/20161023014645_585.jpg",
     "money":40.0,
     "createTimeString":"2016-10-23 15:12:55",
     "fileAskPath":"http://ghelper.h5h5h5.cn/upload/helper/",
     "gameName":"航海王强者之路",
     "platName":null,
     "orderNo":"s2123",
     "orderType":"首充",
     "gameAccount":"100",
     "realPay":25.0,
     "dealStatus":"受理中",
     "payWay":"支付宝",
     "payAccount":"12344",
     "payTimeString":"2016-10-23 15:13:00",
     "succTimeString":"2016-10-23 15:13:06"}]*/
	public List<GameOrder> data;
	public class GameOrder{
		public String status;//
		public String userName;
		public String gameId;
		public String orderId;
		public String userId;
		public String field1;//
		public String money;//
		public String createTimeString;//
		public String fileAskPath;//
		public String gameName;//
		public String platName;
		public String orderNo;//
		public String orderType;//
		public String gameAccount;
		public String realPay;//
		public String dealStatus;
		public String payWay;//
		public String payAccount;
		public String payTimeString;//
		public String succTimeString;//
		public String field7;
		
		public String field8;
	}
}
