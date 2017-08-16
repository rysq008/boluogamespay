package com.game.helper.download.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.game.helper.sdk.model.returns.GetAdList;

import java.io.Serializable;
import java.util.List;

/**
 * @Class: AppContent
 * @Description: 
 * @author: 
 * @Date:
 */
@SuppressWarnings("serial")
public class AppContent extends GetAdList implements Serializable, Parcelable {
	

	public String typeName;
	public String packageName;
	public String gameId;
	public String kindId;
	public String platId;
	public String typeId;
	public String field1;
	public String field2;
	public String field3;
	public String field4;
	public String field5;//游戏标签
	public String field6;
	public String platName;
	public String gameName;
	public String logo;
	public String logoThumb;
	public String kindName;
	public String fileSize;
	public String sczk;
	public String xczk;
	public String intro;
	public String detail;	  
	public String createTimeString;	   
	public String downloadPath;
	public String fileAskPath;
	public String tagName;//游戏标签
	public String jsonData;
	public String adImg;
	public String baseAcessPath;
	public int downloadPercent = 0;
	public Status status = Status.PENDING;
    public String content;
    public int adId;
    public String imgRemark;
    public String isRedirct;
    public String outUrl;
    public String orderby;
    public String updateTimeString;
	public AppContent detailInfo;


	@Override
	public int describeContents() {
		return 0;
	}

	//实现Parcel接口必须覆盖实现的方法
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		/*将AppContent的成员写入Parcel，
		 * 注：Parcel中的数据是按顺序写入和读取的，即先被写入的就会先被读取出来
		 */
		dest.writeString(typeName);
		dest.writeString(packageName);
		dest.writeString(gameId);
		dest.writeString(kindId);
		dest.writeString(platId);
		dest.writeString(typeId);
		dest.writeString(field1);
		dest.writeString(field2);
		dest.writeString(field3);
		dest.writeString(field4);
		dest.writeString(field5);
		dest.writeString(field6);
		dest.writeString(platName);
		dest.writeString(gameName);
		dest.writeString(logo);
		dest.writeString(logoThumb);
		dest.writeString(kindName);
		dest.writeString(fileSize);
		dest.writeString(sczk);
		dest.writeString(xczk);
		dest.writeString(intro);
		dest.writeString(detail);
		dest.writeString(createTimeString);
		dest.writeString(downloadPath);
		dest.writeString(fileAskPath);
		dest.writeString(tagName);
		dest.writeString(jsonData);
		
		dest.writeInt(downloadPercent);
		dest.writeValue(status);
	}

	//该静态域是必须要有的，而且名字必须是CREATOR，否则会出错
	public static final Creator<AppContent> CREATOR =
			new Creator<AppContent>() {

		@Override
		public AppContent createFromParcel(Parcel source) {
			//从Parcel读取通过writeToParcel方法写入的AppContent的相关成员信息
			AppContent appContent = new AppContent();
			appContent.typeName=source.readString();
			appContent.packageName=source.readString();
			appContent.gameId=source.readString();
			appContent.kindId=source.readString();
			appContent.platId=source.readString();
			appContent.typeId=source.readString();
			appContent.field1=source.readString();
			appContent.field2=source.readString();
			appContent.field3=source.readString();
			appContent.field4=source.readString();
			appContent.field5=source.readString();
			appContent.field6=source.readString();
			appContent.platName=source.readString();
			appContent.gameName=source.readString();
			appContent.logo=source.readString();
			appContent.logoThumb=source.readString();
			appContent.kindName=source.readString();
			appContent.fileSize=source.readString();
			appContent.sczk=source.readString();
			appContent.xczk=source.readString();
			appContent.intro=source.readString();
			appContent.detail=source.readString();
			appContent.createTimeString=source.readString();
			appContent.downloadPath=source.readString();
			appContent.fileAskPath=source.readString();
			appContent.tagName=source.readString();
			appContent.jsonData=source.readString();
			
			
			appContent.downloadPercent=source.readInt();
			Status status = (Status)source.readValue(Status.class.getClassLoader());
			appContent.status=status;
			
			//更加读取到的信息，创建返回Person对象
			return appContent;
		}

		@Override
		public AppContent[] newArray(int size)
		{
			// TODO Auto-generated method stub
			//返回AppContent对象数组
			return new AppContent[size];
		}
	};

	public enum Status {
		/**
		 * Indicates that the task has not been executed yet.
		 */
		PENDING(1),

		/**
		 * Indicates that the task is downloading.
		 */
		DOWNLOADING(2),

		/**
		 * Indicates that the task was paused.
		 */
		PAUSED(3),

		/**
		 * Indicates that the task has finished.
		 */
		FINISHED(4),

		/**
		 * 已安装
		 */
		AlreadyInstalled(5);

		private int value;

		Status(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public static Status getByValue(int value) {
			switch (value) {
			case 1:
				return Status.PENDING;
			case 2:
				return Status.DOWNLOADING;
			case 3:
				return Status.PAUSED;
			case 4:
				return Status.FINISHED;
			case 5:
				return Status.AlreadyInstalled;
			}
			return Status.PENDING;
		}
	}
}
