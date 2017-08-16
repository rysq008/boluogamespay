package com.game.helper.sdk.model.returns;

import java.util.List;

/**
 * @Description
 * @Path com.game.helper.sdk.model.returns.getContenteOtherDetail.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:34:17
 * @Company 
 */
public class GetContenteOtherDetail {
	/* "data": {
        "tinfoContent": {
            "contentId": 204,
            "typeId": 1,
            "createTimeString": "2016-08-02 16:40:59",
            "fileAskPath": "http://ghelper.h5h5h5.cn/upload/helper/",
            "contentTitle": "天佑宝贝APP上线了！！！",
            "contentAbstract": null,
            "contentImage": "image/20160804/20160804164824_209.png",
            "contentText": "天佑宝贝APP上线了， 请大家做好测试工作！庆祝上线！啦啦啦",
            "dzNum": 0,
            "plNum": 0,
            "scNum": 0,
            "typeName": "活动"
        },
        "commentList": [
           {
                "contentId": 201,
                "otherId": 84,
                "fileAskPath": "http://ghelper.h5h5h5.cn/upload/helper/",
                "opType": "P",
                "opUserId": 10000,
                "opImage": "头像.jpg",
                "opName": "昵称xx",
                "plContent": "xxxxxx",
                "plTimeString": "2016-08-26 15:04:16"
            }
         ]
    }*/
	public GetContenteOtherDetailData data;
	public class GetContenteOtherDetailData{
		public Content tinfoContent;
		public List<Comment> commentList;
	}
	public class Content{
		public String contentId;
		public String typeId;
		public String createTimeString;
		public String fileAskPath;
		public String contentTitle;
		public String contentAbstract;
		public String contentImage;
		public String contentText;
		public String dzNum;
		public String plNum;
		public String scNum;
		public String typeName;
	}
	public class Comment{
		public String contentId;
		public String otherId;
		public String fileAskPath;
		public String opType;
		public String opUserId;
		public String opImage;
		public String opName;
		public String plContent;
		public String plTimeString;
	}
}
