package com.game.helper.sdk.model.returns;

import java.util.List;

/**
 * @Description
 * @Path com.game.helper.sdk.model.returns.QueryGameGift.java
 * @Author lbb
 * @Date 2016年10月8日 上午10:50:46
 * @Company 
 */
public class QueryGameGift {

	/*{
        "fileAskPath": "http://ghelper.h5h5h5.cn/upload/helper/",
        "giftKindNum": 0,
        "gameName": "DNF",
        "logo": "dnf.jpg",
        "logoThumb": null,
        "lastGift": null,
        "gameId": 1
    }*/
	public List<GameGift> data;
	public class  GameGift{
		public String fileAskPath;
		public String giftKindNum;
		public String gameName;
		public String logo;
		public String logoThumb;
		public String lastGift;
		public String gameId;
	}
}
