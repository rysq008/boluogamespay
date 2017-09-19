package com.example.a.lib;

import java.io.Serializable;

public class jbean implements Serializable{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8683452581122892095l;
	/**
	 * 
	 */
	String	name;
	int		age;

	public jbean(String na, int ag) {
		// TODO Auto-generated constructor stub
		this.name = na;
		this.age = ag;
	}
}
