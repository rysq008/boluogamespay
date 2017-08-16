package com.game.helper.sdk.model.returns;

import java.util.List;

/**
 * @Description
 * @Path com.game.helper.sdk.model.returns.QueryGameCutPl.java
 * @Author lbb
 * @Date 2016年10月12日 上午10:00:47
 * @Company 
 */
public class QueryGameCutPl {

	/*{
        "id": 7,
        "content": "评论1",
        "userName": "昵称xx",
        "cutId": 1,
        "userId": 10000,
        "fileAskPath": "http://ghelper.h5h5h5.cn/upload/helper/",
        "createTimeString": "2016-10-11 00:31:20",
        "userIcon": "头像.jpg",
        "opType": "pl"
    }*/
	public List<GameCutPl> data;
	public class GameCutPl{
		public String id;
		public String content;
		public String userName;
		public String cutId;
		public String userId;
		public String fileAskPath;
		public String createTimeString;
		public String userIcon;
		public String opType;
	}
}
