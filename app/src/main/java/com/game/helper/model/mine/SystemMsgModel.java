package com.game.helper.model.mine;

public class SystemMsgModel {
	public String title;
	public String time;
  
	public String people;
	public String content;
	public int actionType;//11评论动态，12评论攻略，21赞动态，22赞攻略，31收藏动态，32动态攻略，41关注，51送礼物
	public int type;//0代表系统消息，1代表动态消息，2代表充值消息
}
