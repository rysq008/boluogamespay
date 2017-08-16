package com.game.helper.sdk.model.returns;

import java.util.List;

/**
 * @Description
 * @Path com.game.helper.sdk.model.returns.GetRank.java
 * @Author lbb
 * @Date 2016年9月16日 上午9:56:49
 * @Company 
 */
public class GetRank {


    /*"data": [
        {
            "icon": "1",
            "nickName": "1",
            "fileAskPath": "http://godbless.h5h5h5.cn/upload/godbless/",
            "totalSum": "2500"
        },
        {
            "icon": "头像1.jpg",
            "nickName": "xxx",
            "fileAskPath": "http://godbless.h5h5h5.cn/upload/godbless/",
            "totalSum": "200"
        }
    ]*/
	public List<GetRankData> data;
	public class GetRankData{
		public String userId;
		public String icon;
		public String nickName;
		public String fileAskPath;
		public String totalSum;
	}
}
