package com.game.helper.sdk.model.returns;

import java.util.List;

/**
 * @Description
 * @Path com.game.helper.sdk.model.returns.GetExchangelist.java
 * @Author lbb
 * @Date 2016年9月22日 下午3:54:03
 * @Company 
 */
public class GetExchangelist {

	/* "data": {
    "exchangList": [
        {
            "address": "群裕小区31栋",
            "status": "N",
            "goodId": 2,
            "exchangeId": 3,
            "userId": 10001,
            "realName": "YYYY",
            "phone": "13806944856",
            "field1": "福建",
            "field2": "漳州",
            "field3": "很优秀",
            "account": null,
            "createTimeString": "2016-08-26 18:15:33",
            "ptb": 7,
            "goodType": null,
            "goodName": null
        }
    ],
    "sumPtb": 7
}*/
	public ExchangeData data;
	public class ExchangeData{
		public String sumPtb;
		public List<Exchange> exchangList;
	}
	public class Exchange{
	   public String address;
	   public String status;
	   public String goodId;
	   public String exchangeId;
	   public String userId;
	   public String realName;
	   public String phone;
	   public String field1;
	   public String field2;
	   public String field3;
	   public String account;
	   public String createTimeString;
	   public String ptb;
	   public String goodType;
	   public String goodName;
   }
}
