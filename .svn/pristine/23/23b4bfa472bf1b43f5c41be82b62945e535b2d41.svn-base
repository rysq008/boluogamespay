package com.game.helper.sdk.model.returns;

import java.util.List;

/**
 * @Description
 * @Path com.game.helper.sdk.model.returns.getGiveList.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:24:56
 * @Company 
 */
public class GetGiveList {

	/* "data": {
        "giftGiveList": [
             {
                "icon": "http://ghelper.h5h5h5.cn/upload/helper/20161113/115100517@18250840297.jpg",
                "giftId": 1,
                "userId": 10003,
                "giveId": 69,
                "field1": "1",
                "fileAskPath": "http://ghelper.h5h5h5.cn/upload/helper/",
                "createTimeString": "2016-11-30 18:18:01",
                "ptb": 1,
                "nickName": "10003李白冰回来了",
                "giftIcon": "image/20161130/20161130174838_469.gif",
                "operId": 10005,
                "benefit": 0.5
            }
        ],
        "sumBenefit": 1350
    }*/
	public GiveList data;
	public class GiveList{
		public String sumBenefit;
		public List<GetGiveListData> giftGiveList;
	}
	public class GetGiveListData{
		public String icon;//送到礼物人
		public String userId;//收到礼物人
		public String createTimeString;
		public String field1;
		public String giftId;
		public String giveId;
		public String operId;//送到礼物人
		public String nickName;//送到礼物人
		public String fileAskPath;
		public String giftIcon;
		public String ptb;
		public String benefit;
		public String field2;//收到礼物人的昵称
		public String field3;//收到礼物人的头像
	}
}
