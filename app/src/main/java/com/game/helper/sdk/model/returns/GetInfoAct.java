package com.game.helper.sdk.model.returns;

import java.util.List;

/**
 * @Description
 * @Path com.game.helper.sdk.model.returns.GetInfoAct.java
 * @Author lbb
 * @Date 2016年10月8日 上午11:02:32
 * @Company 
 */
public class GetInfoAct {

	/*
	{"data":[
	{"typeName":"活动","contentId":204,"typeId":1,"dzNum":0,
	"createTimeString":"2016-08-02 16:40:59","fileAskPath":"http://ghelper.h5h5h5.cn/upload/helper/",
	"scNum":0,"contentTitle":"天佑宝贝APP上线了！！！","contentAbstract":null,
	"contentImage":"image/20160804/20160804164824_209.png",
	"contentText":"天佑宝贝APP上线了， 请大家做好测试工作！庆祝上线！啦啦啦","plNum":0},
	
	{"typeName":"活动","contentId":201,"typeId":1,"dzNum":0,"createTimeString":"2016-05-24 17:47:01","fileAskPath":"http://ghelper.h5h5h5.cn/upload/helper/","scNum":0,"contentTitle":"看IXIN ","contentAbstract":"SDFSD","contentImage":"image/20160717/20160717222300_13.jpg","contentText":null,"plNum":0},{"typeName":"活动","contentId":202,"typeId":1,"dzNum":0,"createTimeString":"2016-05-18 10:42:41","fileAskPath":"http://ghelper.h5h5h5.cn/upload/helper/","scNum":0,"contentTitle":"xxxx","contentAbstract":"ddddd","contentImage":"image/20160717/20160717222339_217.jpg","contentText":"爱上大声地&nbsp;","plNum":0}],"msg":"请求成功","code":"100"}

	 * 
	 * 
	 * 
	 * 
	 * "data":[
	 { "typeName":"活动",
	   "typeId":1,
	   "contentId":204,
	   "fileAskPath":"http://ghelper.h5h5h5.cn/upload/helper/",
	   "createTimeString":"2016-08-02 16:40:59",
	   "dzNum":0,
	   "plNum":0,
	   "contentTitle":"天佑宝贝APP上线了！！！",
	   "contentAbstract":null,
	   "contentImage":"image/20160804/20160804164824_209.png",
	   "contentText":"天佑宝贝APP上线了， 请大家做好测试工作！庆祝上线！啦啦啦","scNum":0
	   },
	 *   {"typeName":"活动",
	 *   "typeId":1,
	 *   "contentId":201,
	 *   "fileAskPath":"http://ghelper.h5h5h5.cn/upload/helper/",
	 *   "createTimeString":"2016-05-24 17:47:01",
	 *   "dzNum":0,
	 *   "plNum":0,
	 *   "contentTitle":"看IXIN ",
	 *   "contentAbstract":"SDFSD",
	 *   "contentImage":"image/20160717/20160717222300_13.jpg",
	 *   "contentText":null,
	 *   "scNum":0
	 *   },{"typeName":"活动","typeId":1,"contentId":202,"fileAskPath":"http://ghelper.h5h5h5.cn/upload/helper/","createTimeString":"2016-05-18 10:42:41","dzNum":0,"plNum":0,"contentTitle":"xxxx","contentAbstract":"ddddd","contentImage":"image/20160717/20160717222339_217.jpg","contentText":"爱上大声地&nbsp;","scNum":0}]*/
	public List<InfoAct> data;
	public class InfoAct{
		
		public String typeName;
		public String typeId;
		public String contentId;
		public String fileAskPath;
		public String createTimeString;
		public String dzNum;
		public String plNum;
		public String scNum;
		public String contentTitle;
		public String contentAbstract;
		public String contentImage;
		public String contentText;
	}
}
