package com.game.helper.sdk.model.returns;

import java.util.List;

import com.game.helper.download.bean.AppContent;

/**
 * @Description
 * @Path com.game.helper.sdk.model.returns.GetHotGame.java
 * @Author lbb
 * @Date 2016年10月19日 下午3:05:13
 * @Company 
 */
public class GetHotGame {

	public HotGames data;
	public class HotGames{
		public List<AppContent>  list;
		public String refreshDate;
	}
}
