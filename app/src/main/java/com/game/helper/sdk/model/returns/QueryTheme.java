package com.game.helper.sdk.model.returns;

import java.util.List;

/**
 * @Description
 * @Path com.game.helper.sdk.model.returns.QueryTheme.java
 * @Author lbb
 * @Date 2016年10月8日 上午10:24:15
 * @Company 
 */
public class QueryTheme {

	/*{"data":[
	  {"themeId":1,"createTimeString":"2016-10-01 12:12:12",
	  "fileAskPath":"http://ghelper.h5h5h5.cn/upload/helper/",
	  "remark":"真实的3D画面","themeName":"3D",
	  "pic":"image/20161021/20161021014339_530.jpg","picThumb":"test.jpg","gameNum":2},
	 
	  {"themeId":15,"createTimeString":"2016-10-19 22:47:55","fileAskPath":"http://ghelper.h5h5h5.cn/upload/helper/","remark":"主人，不要忘了伦家！","themeName":"养成","pic":"image/20161019/20161019224635_143.jpg","picThumb":null,"gameNum":1}],"msg":"请求成功","code":"100"}

	     *
	     *
	     *
	     */
	
	public List<ThemeGame> data;
	
	public class ThemeGame{
		public String themeId;
		public String createTimeString;
		public String remark;
		public String themeName;
		public String pic;
		public String picThumb;
		public String gameNum;
		public String fileAskPath;
    }
}
