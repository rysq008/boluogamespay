package com.game.helper.sdk.model.returns;
/**
 * @Description
 * @Path com.game.helper.sdk.model.returns.CptbordergetOrderByNo.java
 * @Author lbb
 * @Date 2016年11月30日 下午2:43:02
 * @Company 
 */
public class CptbordergetOrderByNo {
	
	public CptbordergetOrder data;
	
	public class CptbordergetOrder{
		public String orderId;
		public String orderNo;
		public String userId;
		public String userName;
		public String money;
		public String ptb;
		public String payWay;
		public String payAccount;
		public String createTimeString;
		public String payTimeString;
		public String status;
		public String field1;//首充卡金额
		public String field2;//是否首充
		public String field3;
		
		public String fileAskPath;
	}
   /* "userName": null,
    "status": "Y",
    "orderId": 66,
    "userId": 10061,
    "createTimeString": "2016-11-30 18:10:47",
    "orderNo": "ptb_20161130181047532r049F",
    "money": 0.01,
    "ptb": 0.01,
    "payWay": "alipay",
    "payAccount": null,
    "isDel": "N",
    "payTimeString": "2016-11-30 18:11:02",
    "payTime": 1480500662000*/
}
