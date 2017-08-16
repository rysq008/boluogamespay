package com.game.helper.model.home;

/**
 * @Description
 * @Path com.game.helper.model.home.RecommendBoutique.java
 * @Author lbb
 * @Date 2016年8月22日 下午1:46:14
 * @Company 
 */
public class RecommendBoutique {
	public int img;
	public  String url;
	public	String title;
	public	String type;
	public	String size;
	public int progress=0;
	
	public	String msg;
	public	String logo1;
	public	String logo2;
	
	public Flag flag = Flag.NO_STATUS;
	/**
	 * 下载的状态：无状态，暂停，正在下载
	 * @author zhy
	 *
	 */
	public enum Flag
	{
		NO_STATUS,PAUSE,DOWNLOADING,FINISH,AlreadyInstalled
	}
}
