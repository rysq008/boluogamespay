package com.game.helper.sdk.model.returns;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Path com.game.helper.sdk.model.returns.GetGoodList.java
 * @Author lbb
 * @Date 2016年9月14日 下午2:22:36
 * @Company 
 */
public class GetGoodList {

	public  List<TypeList> data;

	public class  TypeList {
		public ArrayList<Good> typeOne;
		public ArrayList<Good> typeTwo;
		public ArrayList<Good> typeThree;
		public ArrayList<Good> typeFour;


	}
	public class  Good implements Serializable{
		public String content;
		public String status;
		public String goodId;
		public String fileAskPath;
		public String goodName;
		public String goodType;
		public String ptb;
		public String amount;
		public String price;
		public String img;
		public String thumb;
		public String chance;
		public String createTimeString;
	}
}
