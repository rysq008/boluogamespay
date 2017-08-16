package com.game.helper.sdk.model.returns;

import java.util.List;

public class StarUser {

	 /*{
         "pwd": "123456",
         "fansTotal": 0,
         "icon": null,
         "status": "Y",
         "guildId": 0,
         "userId": 10000,
         "ptb": 997.8,
         "iconThumb": null,
         "account": "10000",
         "nickName": "昵称xx",
         "realName": null,
         "phone": "18060478013",
         "field1": "男",
         "field2": "",
         "field3": 0,
         "field4": "Y",
         "createTimeString": "2016-08-21 15:03:53",
         "fileAskPath": "http://ghelper.h5h5h5.cn/upload/helper/",
         "foucsTotal": 0
     },*/
	public StarUsers data;
	public class StarUsers{
		public String refreshDate;
		public List<Star> list;
	}
	
	public class Star{
		public String pwd;
		public String fansTotal;
		public String icon;
		public String status;
		public String guildId;
		public String userId;
		public String ptb;
		public String iconThumb;
		public String account;
		public String nickName;
		public String realName;
		
		public String phone;
		public String field1;
		public String field2;
		public String field3;
		public String field4;
		public String field9;
		public String field8;
		public String field7;
		public String createTimeString;
		public String fileAskPath;
		public String foucsTotal;
		
	}
}
