package com.game.helper.model.home;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("serial")
public class Gift implements Serializable, Parcelable{
	public String content;
	public String gameId;
	public String giftId;
	public String createTimeString;
	public String leftTimeString;
	public String rightTimeString;
	public String field1;
	public String giftName;
	public String amount;
	public String useWay;
	public String orderby;
	public String leftTime;
	public String rightTime;
	public String fileAskPath;
	public String field2;
	
	public String logo;
	public String logoThumb;
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(content);
		dest.writeString(gameId);
		dest.writeString(giftId);
		dest.writeString(createTimeString);
		dest.writeString(leftTimeString);
		dest.writeString(rightTimeString);
		dest.writeString(field1);
		dest.writeString(giftName);
		dest.writeString(amount);
		dest.writeString(useWay);
		dest.writeString(orderby);
		dest.writeString(leftTime);
		dest.writeString(rightTime);
		dest.writeString(fileAskPath);
		dest.writeString(field2);
		
		dest.writeString(logo);
		dest.writeString(logoThumb);
	}
	//该静态域是必须要有的，而且名字必须是CREATOR，否则会出错
	public static final Creator<Gift> CREATOR =
			new Creator<Gift>() {

		@Override
		public Gift createFromParcel(Parcel source) {
			//从Parcel读取通过writeToParcel方法写入的AppContent的相关成员信息
			Gift appContent = new Gift();
			appContent.content=source.readString();
			appContent.gameId=source.readString();
			appContent.giftId=source.readString();
			appContent.createTimeString=source.readString();
			appContent.leftTimeString=source.readString();
			appContent.rightTimeString=source.readString();
			appContent.field1=source.readString();
			appContent.giftName=source.readString();
			appContent.amount=source.readString();
			appContent.useWay=source.readString();
			appContent.orderby=source.readString();
			appContent.leftTime=source.readString();
			appContent.rightTime=source.readString();
			appContent.fileAskPath=source.readString();
			appContent.field2=source.readString();
			
			appContent.logo=source.readString();
			appContent.logoThumb=source.readString();
			
			
			//更加读取到的信息，创建返回Person对象
			return appContent;
		}

		@Override
		public Gift[] newArray(int size)
		{
			// TODO Auto-generated method stub
			//返回AppContent对象数组
			return new Gift[size];
		}
	};
}