package com.game.helper.sdk.model.returns;
/**
 * @Description
 * @Path com.game.helper.sdk.model.returns.judgeSign.java
 * @Author lbb
 * @Date 2016年9月16日 上午11:11:50
 * @Company 
 */
public class JudgeSign {

	/*"data": {
    "isSign": "Y",
    "sumCount": 2
}*/
	public JudgeSignData data;
	public class JudgeSignData{
		public String isSign;
		public String sumCount;
	}
}
