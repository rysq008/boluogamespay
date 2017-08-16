package com.game.helper.sdk.model.comm;

import java.util.List;

import com.game.helper.sdk.model.base.BaseRequest;
import com.game.helper.sdk.model.returns.Images;
import android.content.Context;

/**
 * @Description
 * @Path com.game.helper.sdk.model.comm.insertDynamicBuild.java
 * @Author lbb
 * @Date 2016年9月16日 下午1:14:11
 * @Company 
 */
public class InsertDynamicBuild extends BaseRequest{

	public InsertDynamicBuild(Context context, String url,String userId,String guildId,String content,String address, List<Images> imagesList) {
		super(context, url);
		params=new Params();
		params.userId=userId;
		params.guildId=guildId;
		params.content=content;
		params.address=address;
		params.imagesList=imagesList;
	}
	public Params params;
	
	public class Params{
		/* 
         "guildId":"1",
        "userId":"10000",
        "content":"xxxxx",
        "imagesList":[{"srcFilePath":"1.jpg","thumbFilePath":"2.jpg"}],
        "address":"发布地点" */
		public String userId;
		public String guildId;
		public String content;
		public List<Images> imagesList;
		public String address;
	}
	
}
