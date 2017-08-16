package com.game.helper.sdk.model.returns;

import java.util.List;
/**
 * @Description
 * @Path com.game.helper.sdk.model.returns.getNoticeList.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:00:52
 * @Company 
 */
public class GetNoticeList {

	/*"data": [
	         {
	             "content": "小学生一放假，送人头，脑残时间频繁发生",
	             "createTimeString": "2016-08-23 16:49:40",
	             "guildId": 1,
	             "noticeId": 2,
	             "noticeName": "小学生开始放假了，请注意"
	         },
	         {
	             "content": "小学生一放假，送人头，脑残时间频繁发生",
	             "createTimeString": "2016-08-23 16:49:38",
	             "guildId": 1,
	             "noticeId": 1,
	             "noticeName": "小学生开始放假了，请注意"
	         },
	         {
	             "content": "小学生一放假，送人头，脑残时间频繁发生",
	             "createTimeString": "2016-08-23 16:48:43",
	             "guildId": 1,
	             "noticeId": 3,
	             "noticeName": "小学生开始放假了，请注意"
	         }
	     ]*/
	public List<GetNoticeListData> data;
	public class GetNoticeListData{
		public String content;
		public String createTimeString;
		public String guildId;
		public String noticeId;
		public String noticeName;
	}
}
