package com.game.helper.sdk.model.returns;

import java.util.List;

import com.game.helper.download.bean.AppContent;

/**
 * @Description
 * @Path com.game.helper.sdk.model.returns.GetNoNetGame.java
 * @Author lbb
 * @Date 2016年10月19日 下午3:01:16
 * @Company 
 */
public class GetNoNetGame {
	
	public NoNetGames data;
	
	public class NoNetGames {
		public List<AppContent> list;
		public String refreshDate;
	}
}
