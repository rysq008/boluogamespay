package com.game.helper.sdk.model.returns;

import java.util.List;


/**
 * @Description
 * @Path com.game.helper.sdk.model.returns.GetContentTypeList.java
 * @Author lbb
 * @Date 2016年9月22日 下午6:01:10
 * @Company 
 */
public class GetContentTypeList {

	/*
	"data":
	[
	    {"typeName":"活动","typeId":1},
	    {"typeName":"资讯","typeId":2},
	    {"typeName":"搞笑","typeId":3},
	    {"typeName":"精彩","typeId":4}
	],*/
public List<ContentType> data;
public class ContentType{
	public String typeName;
	public String typeId;
}
}
