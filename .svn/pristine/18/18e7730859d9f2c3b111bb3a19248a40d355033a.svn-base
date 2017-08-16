package com.game.helper.sdk.model.comm;

import com.game.helper.sdk.model.base.BaseRequest;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.SaveGameCutBuild.java
 * @Author lbb
 * @Date 2016年10月12日 上午9:16:48
 * @Company 
 */
public class SaveGameCutBuild extends BaseRequest{

	public SaveGameCutBuild(Context context, String url,String userId,String gameId
			,String title,String content,String img1,String img2,String img3,String img4,String img5) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.gameId=gameId;
		params.title=title;
		params.content=content;
		
		params.img1=img1;
		params.img2=img2;
		params.img3=img3;
		params.img4=img4;
		params.img5=img5;
	}
	public Params params;
	
	class Params{
		/*  "userId":"10000",
        "gameId":"1",
        "title":"这是游戏1第3个攻略",
        "content":"这是游戏1第3个攻略攻略内容"
        img1	否	string	攻略图片1
img2	否	string	攻略图片2
img3	否	string	攻略图片3
img4	否	string	攻略图片4
img5	否	string	攻略图片5
*/
		public String userId;
		public String gameId;
		public String title;
		public String content;
		public String img1;
		public String img2;
		public String img3;
		public String img4;
		public String img5;
	}
}