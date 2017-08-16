package com.game.helper.model.home;
/**
 * @Description
 * @Path com.game.helper.model.home.SortingEntity.java
 * @Author lbb
 * @Date 2016年8月23日 下午5:03:35
 * @Company
 */
public class SortingEntity {
	public String orderby;
	public String orderbyName;
	public SortingEntity(String orderby,String orderbyName) {
		super();
		this.orderby=orderby;
		this.orderbyName=orderbyName;
	}
	
}
