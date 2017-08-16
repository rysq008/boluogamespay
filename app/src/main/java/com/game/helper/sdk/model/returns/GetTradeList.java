package com.game.helper.sdk.model.returns;

import java.util.List;

public class GetTradeList {

	/*{"data":[{"tradeId":2,
		"userId":10000,"createTimeString":"2016-10-29 16:13:35",
		"ptb":67.0,"tradeType":"0","tradeName":"提现"}
	,{"tradeId":1,"userId":10000,"createTimeString":
		"2016-10-29 16:13:33","ptb":23.0,"tradeType":
			"0","tradeName":"送礼物"}],*/
	public List<Trade> data;
	public class Trade{
		public String tradeId;
		public String userId;
		public String createTimeString;
		public double ptb;
		public String tradeType;
		public String tradeName;
		
		public String field1;//充值返利 ,游戏id
		public String field2;;//充值返利 ,游戏名称
		public String field3;//充值返利 ,消费用户id
		public String field4;//充值返利 ,消费用户名称
		public String field5;//充值返利 ,消费金额
		public String field6;//充值返利
		public String fileAskPath;//充值返利
	}
}
