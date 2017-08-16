package com.game.helper.model.mine;

import android.graphics.Bitmap;

/**
 * @Description
 * @Path com.game.helper.model.mine.PersonaImg.java
 * @Author lbb
 * @Date 2016年9月1日 下午1:33:09
 * @Company 
 */
public class PersonaImg implements Comparable<PersonaImg>{
	public Bitmap smallBitmap;
	public int isSel;
	//public Integer index;
	
	public int id;
	public String fileAskPath;
	public String icon;
	public int orderby;
	public String userId;
	public String iconThumb;
	@Override
	public int compareTo(PersonaImg another) {
		// TODO Auto-generated method stub
		return (Integer.valueOf(orderby)).compareTo(Integer.valueOf(another.orderby));
	}
}
