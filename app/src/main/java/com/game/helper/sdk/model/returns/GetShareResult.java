package com.game.helper.sdk.model.returns;

import java.util.List;

public class GetShareResult {

	/*"data":[{"icon":"头像1.jpg","userId":10001,
		"nickName":"昵称sss","totalSum":80.0,
		"baseAcessPath":"http://ghelper.h5h5h5.cn/upload/helper/"},
		{"icon":"头像2.jpg","userId":10002,
			"nickName":"昵称111","totalSum":0.0,
			"baseAcessPath":"http://ghelper.h5h5h5.cn/upload/helper/"}]*/
	public List<ShareResult> data;
	public class ShareResult{
		public String icon;
		public String userId;
		public String nickName;
		public String totalSum;
		public String baseAcessPath;
	}

}
