package com.game.helper.sdk.model.returns;

import java.util.List;

public class QueryMyAccount {
	/* {
         "id": 1,
         "scNum": 0,
         "status": "Y",
         "createTimeString": "2016-10-19 00:00:00",
         "userName": "10000",
         "platId": 1,
         "userId": 10000,
         "gameAccount": "2233233",
         "getTimeString": "2016-10-20 13:53:56",
         "platName": "应用宝",
         "remark": null,
         "gamePwd": "123456",
         "getTime": 1476942836000
     }
 {"data":[
 {"id":7,"userName":"10005","status":"Y","platId":2,
 "userId":10005,"createTimeString":"2016-11-07 20:36:44",
 "platName":"乐游","gameAccount":"888888","gamePwd":"666666",
 "scNum":0,"remark":null,"getTimeString":"2016-11-07 20:38:14",
 "getTime":1478522294000},    {"id":8,"userName":"10005","status":"Y","platId":2,"userId":10005,"createTimeString":"2016-11-07 20:37:44","platName":"乐游","gameAccount":"5555","gamePwd":"1111","scNum":0,"remark":null,"getTimeString":"2016-11-07 20:38:51","getTime":1478522331000},{"id":9,"userName":"10005","status":"Y","platId":2,"userId":10005,"createTimeString":"2016-11-07 20:37:58","platName":"乐游","gameAccount":"99999","gamePwd":"2222","scNum":0,"remark":null,"getTimeString":"2016-11-07 20:38:57","getTime":1478522337000},{"id":10,"userName":"10005","status":"Y","platId":2,"userId":10005,"createTimeString":"2016-11-07 20:38:09","platName":"乐游","gameAccount":"666888","gamePwd":"8866","scNum":0,"remark":null,"getTimeString":"2016-11-07 20:39:00","getTime":1478522340000}],"msg":"请求成功","code":"100"}

     *
     *
     *
     */
	public List<Account>  data;
	public static class Account{
		public String id;
		public String userName;
		public String status;
		public String platId;
		public String userId;
		public String createTimeString;
		public String platName;
		public String gameAccount;
		public String gamePwd;
		public String scNum; 
		public String remark;
		public String getTimeString;
		public String getTime;
		public String field2;//field2表示gameId,field3代表gamgName
		public String field3;
		
	}

}
