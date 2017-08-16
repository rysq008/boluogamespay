package com.game.helper.sdk.model.returns;

import java.util.List;

public class GetMyCollection {

/*	"data":[
	   {"collectionType":"zx",
	   "collectionId":2,
	        	"typeId":201,
	        	"userId":10000,
	        	"field1":"看IXIN ",
	        	"createTimeString":"2016-10-26 17:33:51",
	     "fileAskPath":"http://ghelper.h5h5h5.cn/upload/helper/",
	     "img":"image/20160717/20160717222300_13.jpg",
	     "introduce":null},
	  {"collectionType":"gl","collectionId":1,"typeId":1,
	    	 "userId":10000,"field1":"这是游戏1第一个攻略",
	    	 "createTimeString":"2016-10-26 17:33:25",
	    	 "fileAskPath":"http://ghelper.h5h5h5.cn/upload/helper/",
	    	 "img":"1","introduce":null}],*/
	public List<MyCollection>  data;
	public class MyCollection{
		public String collectionType;
		public String collectionId;
		public String typeId;
		public String userId;
		public String field1;
		public String createTimeString;
		public String fileAskPath;
		public String img;
		public String introduce;
	}
}
