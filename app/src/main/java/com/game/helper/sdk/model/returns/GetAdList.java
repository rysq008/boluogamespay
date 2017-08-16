package com.game.helper.sdk.model.returns;

import com.game.helper.download.bean.AppContent;

import java.util.List;

/**
 * @Description
 * @Path com.game.helper.sdk.model.returns.GetAdList.java
 * @Author lbb
 * @Date 2016年9月12日 下午7:41:54
 * @Company 
 */
public class GetAdList {
	/*"data": [
	         {
	             "content": null,
	             "field1": "http: //godbless.h5h5h5.cn/helper/tadvertisement/toBannerContentPage?adId=19",
	             "field2": "2",
	             "field3": "Y",
	             "adId": 19,
	             "baseAcessPath": "http: //godbless.h5h5h5.cn/upload/godbless/",
	             "adImg": null,
	             "imgRemark": null,
	             "isRedirct": null,
	             "outUrl": null,
	             "orderby": null,
	             "updateTimeString": null
	         }
"content": 广告内容,
"field1":跳转到详情页地址 ,
"field2": 位置 0-首页 1- 社区页 2-商城页 ,
"field3": 是否启用,
"adId": 广告id,
"baseAcessPath":图片访问地址,
"adImg": 图片地址,
"imgRemark": 广告名称,
"isRedirct": 是否跳转 0-否 1-是,
"outUrl": 外部链接地址,
"orderby": 排序顺序,
"updateTimeString": 更新时间
	     ]*/

	/*
	 {
      "content": null,
      "orderby": 23,
      "imgRemark": "折扣更低,更安全",
      "isRedirct": null,
      "outUrl": null,
      "updateTimeString": "2017-07-01 17:56:24",
      "baseAcessPath": "http://ghelper.h5h5h5.cn/upload/helper/",
      "adImg": "image/20170701/20170701175526_245.png",
      "adId": 43,
      "gameId": 16,
      "field1": "http://101.200.133.125:53010/helper/app/toBannerIndex",
      "field2": "4",
      "field3": "Y"
    },
	 */

	
	public List<AppContent> data;
	public class AdData{
		public String gameId;
		public String content;
		public String field1;
		public String field2;
		public String field3;
		public int adId;
		public String baseAcessPath;
		public String adImg;
		public String imgRemark;
		public String isRedirct;
		public String outUrl;
		public String orderby;
		public String updateTimeString;


		
	}
}
