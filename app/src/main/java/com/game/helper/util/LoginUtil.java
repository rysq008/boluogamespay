package com.game.helper.util;

import java.math.BigDecimal;

import com.game.helper.BaseApplication;
import com.game.helper.activity.MainActivity;
import com.game.helper.interfaces.comm.CommValues;
import com.game.helper.net.base.BaseBBXTask;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.CgamekindlistTask;
import com.game.helper.net.task.CgametypelistTask;
import com.game.helper.net.task.CrollListTask;
import com.game.helper.net.task.GetAdListTask;
import com.game.helper.net.task.GetAddressListTask;
import com.game.helper.net.task.GetBasicTask;
import com.game.helper.net.task.GetContactListTask;
import com.game.helper.net.task.GetContentTypeListTask;
import com.game.helper.net.task.GetFocusListTask;
import com.game.helper.net.task.GetGiftListTask;
import com.game.helper.net.task.GetGiveListTask;
import com.game.helper.net.task.GetGoodListTask;
import com.game.helper.net.task.GetInfoActTask;
import com.game.helper.net.task.GetPtbRuleTask;
import com.game.helper.net.task.GetRankTask;
import com.game.helper.net.task.QueryFirstOneTask;
import com.game.helper.net.task.QueryGameByModularTask;
import com.game.helper.net.task.QueryGameBykindAndTypeTask;
import com.game.helper.sdk.model.returns.Cgamekindlist;
import com.game.helper.sdk.model.returns.Cgametypelist;
import com.game.helper.sdk.model.returns.CrollList;
import com.game.helper.sdk.model.returns.GetAdList;
import com.game.helper.sdk.model.returns.GetAddressList;
import com.game.helper.sdk.model.returns.GetBasic;
import com.game.helper.sdk.model.returns.GetContactList;
import com.game.helper.sdk.model.returns.GetContentTypeList;
import com.game.helper.sdk.model.returns.GetFocusList;
import com.game.helper.sdk.model.returns.GetGiftList;
import com.game.helper.sdk.model.returns.GetGiveList;
import com.game.helper.sdk.model.returns.GetGoodList;
import com.game.helper.sdk.model.returns.GetInfoAct;
import com.game.helper.sdk.model.returns.GetPtbRule;
import com.game.helper.sdk.model.returns.GetPtbRule.PtbRule;
import com.game.helper.sdk.model.returns.GetRank;
import com.game.helper.sdk.model.returns.QueryFirstOne;
import com.game.helper.sdk.model.returns.QueryGameByModular;
import com.game.helper.sdk.model.returns.QueryGameBykindAndType;
import com.game.helper.sdk.net.base.JsonBuild;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
/**
 * @Description
 * @Path com.game.helper.util.LoginUtil.java
 * @Author lbb
 * @Date 2016年9月17日 下午4:24:41
 * @Company 
 */
public class LoginUtil implements CommValues{

	public static void loginSuccess(final Context context,final String userId){
		new GetAdListTask(context, "2", new Back() {

			@Override
			public void success(Object object, String msg) {
				// TODO Auto-generated method stub
				if(object!=null && object instanceof GetAdList){
					GetAdList list=(GetAdList) object;
					Log.e("lbb", "---------3-------0--");
					if(list!=null &&list.data!=null &&list.data.size()>=0){
						BaseApplication.mInstance.imageIdList3=list.data;
						context.sendBroadcast(new Intent(ACTION_GetAdList3));
						SharedPreUtil.putStringValue(context, ACTION_GetAdList3, new JsonBuild().setModel(object).getJson1());
						Log.e("lbb", "---------3---------");
					}
				}
			}

			@Override
			public void fail(String status, String msg, Object object) {
				// TODO Auto-generated method stub

				context.sendBroadcast(new Intent(ACTION_GetAdList_Fail3));
			}
		}).start();
		new GetAdListTask(context, "4", new Back() {

			@Override
			public void success(Object object, String msg) {
				if(object!=null && object instanceof GetAdList){
					GetAdList list=(GetAdList) object;
					if(list!=null &&list.data!=null &&list.data.size()>=0){
						BaseApplication.mInstance.imageIdList1=list.data;
						context.sendBroadcast(new Intent(ACTION_GetAdList1));
						SharedPreUtil.putStringValue(context, ACTION_GetAdList1, new JsonBuild().setModel(object).getJson1());
						Log.e("lbb", "---------1---------");
					}
				}
			}

			@Override
			public void fail(String status, String msg, Object object) {
				// TODO Auto-generated method stub
				context.sendBroadcast(new Intent(ACTION_GetAdList_Fail1));
			}
		}).start();
		new GetAdListTask(context, "1", new Back() {

			@Override
			public void success(Object object, String msg) {
				// TODO Auto-generated method stub
				if(object!=null && object instanceof GetAdList){
					GetAdList list=(GetAdList) object;
					if(list!=null &&list.data!=null &&list.data.size()>=0){
						BaseApplication.mInstance.imageIdList2=list.data;
						context.sendBroadcast(new Intent(ACTION_GetAdList2));
						SharedPreUtil.putStringValue(context, ACTION_GetAdList2, new JsonBuild().setModel(object).getJson1());
						Log.e("lbb", "---------2---------");
					}
				}
			}

			@Override
			public void fail(String status, String msg, Object object) {
				// TODO Auto-generated method stub
				context.sendBroadcast(new Intent(ACTION_GetAdList_Fail2));
			}
		}).start();
		
		new GetAdListTask(context, "3", new Back() {
			@Override
			public void success(Object object, String msg) {
				// TODO Auto-generated method stub
				if(object!=null && object instanceof GetAdList){
					GetAdList list=(GetAdList) object;
					if(list!=null &&list.data!=null &&list.data.size()>=0){
						BaseApplication.mInstance.imageIdList4=list.data;
						context.sendBroadcast(new Intent(ACTION_GetAdList4));
						SharedPreUtil.putStringValue(context, ACTION_GetAdList4, new JsonBuild().setModel(object).getJson1());
					}
				}
			}

			@Override
			public void fail(String status, String msg, Object object) {
				
			}
		}).start();
		
		new GetGoodListTask(context, false,new Back() {

			@Override
			public void success(Object object, String msg) {
				if(object!=null && object instanceof GetGoodList){
					GetGoodList  mlist=(GetGoodList) object;
					if(mlist.data!=null){
						Log.e("lbb", "---------4--------");
						context.sendBroadcast(new Intent(ACTION_GetGoodList));
						SharedPreUtil.putStringValue(context, ACTION_GetGoodList, new JsonBuild().setModel(object).getJson1());
					}
				}
			}

			@Override
			public void fail(String status, String msg, Object object) {
				// TODO Auto-generated method stub
				context.sendBroadcast(new Intent(ACTION_GetGoodList_Fail));
			}
		}).start();
		new GetAddressListTask(context, false,userId, new Back() {

			@Override
			public void success(Object object, String msg) {
				if(object!=null && object instanceof GetAddressList){
					GetAddressList address=(GetAddressList) object;
					if(address.data!=null&& address.data.size()>=0){
						SharedPreUtil.putStringValue(context, ACTION_GetAddressList, new JsonBuild().setModel(object).getJson1());

					}
				}

			}

			@Override
			public void fail(String status, String msg, Object object) {
				// TODO Auto-generated method stub

			}
		}).start();

		new GetRankTask(context,false, userId, new Back() {

			@Override
			public void success(Object object, String msg) {
				if(object!=null && object instanceof GetRank){
					GetRank mGetRank=(GetRank) object;
					if(mGetRank.data!=null){
						if(mGetRank.data.size()>=0){
							SharedPreUtil.putStringValue(context, ACTION_GetRank+userId, new JsonBuild().setModel(object).getJson1());
						}
					}
				}
			}

			@Override
			public void fail(String status, String msg, Object object) {

			}
		}).start();

		new GetFocusListTask(context,false, userId, "0", new Back() {

			@Override
			public void success(Object object, String msg) {
				// TODO Auto-generated method stub
				if(object!=null&&object instanceof GetFocusList){
					GetFocusList m=(GetFocusList)object;
					if(m.data!=null&&m.data.size()>=0){
						SharedPreUtil.putStringValue(context, ACTION_GetFocusList1+userId, new JsonBuild().setModel(object).getJson1());
					}
				}
			}

			@Override
			public void fail(String status, String msg, Object object) {
				// TODO Auto-generated method stub

			}
		}).start();
		new GetFocusListTask(context,false, userId, "1", new Back() {

			@Override
			public void success(Object object, String msg) {
				// TODO Auto-generated method stub
				if(object!=null&&object instanceof GetFocusList){
					GetFocusList m=(GetFocusList)object;
					if(m.data!=null&&m.data.size()>=0){
						SharedPreUtil.putStringValue(context, ACTION_GetFocusList2+userId, new JsonBuild().setModel(object).getJson1());
					}
				}
			}

			@Override
			public void fail(String status, String msg, Object object) {
				// TODO Auto-generated method stub

			}
		}).start();

		new GetGiveListTask(context, false,userId, new Back() {

			@Override
			public void success(Object object, String msg) {
				// TODO Auto-generated method stub
				if(object!=null&& object instanceof GetGiveList){
					GetGiveList mGetGiveList=(GetGiveList) object;
					if(mGetGiveList.data!=null){

						SharedPreUtil.putStringValue(context, ACTION_GetGiveList+userId, new JsonBuild().setModel(object).getJson1());
					}
				}
			}

			@Override
			public void fail(String status, String msg, Object object) {

			}
		}).start();
		new GetGiftListTask(context,false, new Back() {

			@Override
			public void success(Object object, String msg) {

				if(object!=null&&object instanceof GetGiftList){
					GetGiftList m=(GetGiftList) object;
					if(m.data!=null&& m.data.size()>=0){
						SharedPreUtil.putStringValue(context, ACTION_GetGiftList, new JsonBuild().setModel(object).getJson1());
					}
				}
			}

			@Override
			public void fail(String status, String msg, Object object) {

			}
		}).start();
		new GetContentTypeListTask(context, new Back() {

			@Override
			public void success(Object object, String msg) {
				// 
				if(object!=null&& object instanceof GetContentTypeList){
					GetContentTypeList mGetContentTypeList=(GetContentTypeList) object;
					if(mGetContentTypeList.data!=null&&mGetContentTypeList.data.size()>=0){

						SharedPreUtil.putStringValue(context, ACTION_GetContentTypeList, new JsonBuild().setModel(object).getJson1());

					}
				}
			}

			@Override
			public void fail(String status, String msg, Object object) {
				// TODO Auto-generated method stub

			}
		}).start();
		new GetContactListTask(context, new Back() {

			@Override
			public void success(Object object, String msg) {
				// ACTION_GetContactList
				if(object!=null&& object instanceof GetContactList){
					GetContactList mGetContactList=(GetContactList) object;
					if(mGetContactList.data!=null&&mGetContactList.data.size()>=0){

						SharedPreUtil.putStringValue(context, ACTION_GetContactList, new JsonBuild().setModel(object).getJson1());

					}
				}
			}

			@Override
			public void fail(String status, String msg, Object object) {
				// TODO Auto-generated method stub

			}
		}).start();

		getHomeDate(context);

		new GetInfoActTask(context, new Back() {

			@Override
			public void success(Object object, String msg) {
				// 
				if(object!=null&& object instanceof GetInfoAct){
					GetInfoAct mGetInfoAct=(GetInfoAct) object;
					if(mGetInfoAct.data!=null&&mGetInfoAct.data.size()>=0){

						SharedPreUtil.putStringValue(context, ACTION_GetInfoAct, new JsonBuild().setModel(object).getJson1());

					}
				}
			}

			@Override
			public void fail(String status, String msg, Object object) {
				// TODO Auto-generated method stub

			}
		}).start();

		new QueryFirstOneTask(context, new Back() {

			@Override
			public void success(Object object, String msg) {
				// 
				if(object!=null&& object instanceof QueryFirstOne){
					QueryFirstOne mQueryFirstOne=(QueryFirstOne) object;
					if(mQueryFirstOne.data!=null&&mQueryFirstOne.data.size()>=0){

						SharedPreUtil.putStringValue(context, ACTION_QueryFirstOne, new JsonBuild().setModel(object).getJson1());

					}
				}
			}

			@Override
			public void fail(String status, String msg, Object object) {
				// TODO Auto-generated method stub

			}
		}).start();
		//游戏类型
		new CgametypelistTask(context, new Back() {

			@Override
			public void success(Object object, String msg) {
				// TODO Auto-generated method stub
				if(object!=null&& object instanceof Cgametypelist){
					Cgametypelist mCgametypelist=(Cgametypelist) object;
					if(mCgametypelist.data!=null&&mCgametypelist.data.size()>=0){

						SharedPreUtil.putStringValue(context, ACTION_Cgametypelist, new JsonBuild().setModel(object).getJson1());
					}
				}
			}

			@Override
			public void fail(String status, String msg, Object object) {
				// TODO Auto-generated method stub

			}
		}).start();
		new CgamekindlistTask(context, new Back() {

			@Override
			public void success(Object object, String msg) {
				// TODO Auto-generated method stub
				if(object!=null&& object instanceof Cgamekindlist){
					Cgamekindlist mCgamekindlist=(Cgamekindlist) object;
					if(mCgamekindlist.data!=null&&mCgamekindlist.data.size()>=0){

						SharedPreUtil.putStringValue(context, ACTION_Cgamekindlist, new JsonBuild().setModel(object).getJson1());
					}
				}
			}

			@Override
			public void fail(String status, String msg, Object object) {
				// TODO Auto-generated method stub

			}
		}).start();
		new GetPtbRuleTask(context,false, new Back() {

			@Override
			public void success(Object object, String msg) {
				if(object!=null && object instanceof GetPtbRule){
					GetPtbRule mGetPtbRule=(GetPtbRule) object;
					if(mGetPtbRule.data!=null){
						for(PtbRule mPtbRule:mGetPtbRule.data){
							BigDecimal bd = new BigDecimal(mPtbRule.ptb);
							bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
							mPtbRule.ptb=Double.valueOf(bd.toString());
							if(!TextUtils.isEmpty(mPtbRule.ruleCode)&&mPtbRule.ruleCode.equals("share")){
								BaseApplication.mInstance.shareNum=mPtbRule.ptb;
							}else if(!TextUtils.isEmpty(mPtbRule.ruleCode)&&mPtbRule.ruleCode.equals("publish")){
								BaseApplication.mInstance.publishNum=mPtbRule.ptb;
							}else if(!TextUtils.isEmpty(mPtbRule.ruleCode)&&mPtbRule.ruleCode.equals("award")){
								BaseApplication.mInstance.awardNum=mPtbRule.ptb;
							}
							
							
						}
					}
				}

			}

			@Override
			public void fail(String status, String msg, Object object) {
				// TODO Auto-generated method stub

			}
		}).start();
		//新账号首充，折扣号续充，游戏充值提示，金币充值提示，领折扣号提示

		new GetBasicTask(context,false, "txt1", new Back() {
			
			@Override
			public void success(Object object, String msg) {
				if(object!=null && object instanceof GetBasic){
					GetBasic mGetBasic=(GetBasic) object;
					if(mGetBasic.data!=null){
						BaseApplication.mInstance.put(KEY_MSGTEXT_SC, mGetBasic.data);
					}
					
				}
				
			}
			
			@Override
			public void fail(String status, String msg, Object object) {
				// TODO Auto-generated method stub
				
			}
		}).start();
        new GetBasicTask(context,false, "txt2", new Back() {
			
			@Override
			public void success(Object object, String msg) {
				if(object!=null && object instanceof GetBasic){
					GetBasic mGetBasic=(GetBasic) object;
					if(mGetBasic.data!=null){
						BaseApplication.mInstance.put(KEY_MSGTEXT_XC, mGetBasic.data);
					}
					
				}
				
			}
			
			@Override
			public void fail(String status, String msg, Object object) {
				// TODO Auto-generated method stub
				
			}
		}).start();
        new GetBasicTask(context,false, "txt3", new Back() {
			
			@Override
			public void success(Object object, String msg) {
				if(object!=null && object instanceof GetBasic){
					GetBasic mGetBasic=(GetBasic) object;
					if(mGetBasic.data!=null){
						BaseApplication.mInstance.put(KEY_MSGTEXT_GAME, mGetBasic.data);
					}
					
				}
				
			}
			
			@Override
			public void fail(String status, String msg, Object object) {
				// TODO Auto-generated method stub
				
			}
		}).start();
        new GetBasicTask(context,false, "txt4", new Back() {
			
			@Override
			public void success(Object object, String msg) {
				if(object!=null && object instanceof GetBasic){
					GetBasic mGetBasic=(GetBasic) object;
					if(mGetBasic.data!=null){
						BaseApplication.mInstance.put(KEY_MSGTEXT_PTB, mGetBasic.data);
					}
					
				}
				
			}
			
			@Override
			public void fail(String status, String msg, Object object) {
				// TODO Auto-generated method stub
				
			}
		}).start();
        new GetBasicTask(context,false, "txt5", new Back() {
			
			@Override
			public void success(Object object, String msg) {
				if(object!=null && object instanceof GetBasic){
					GetBasic mGetBasic=(GetBasic) object;
					if(mGetBasic.data!=null){
						BaseApplication.mInstance.put(KEY_MSGTEXT_LZKH, mGetBasic.data);
					}
					
				}
				
			}
			
			@Override
			public void fail(String status, String msg, Object object) {
				// TODO Auto-generated method stub
				
			}
		}).start();
        
      new CrollListTask(context, new Back() {
			
			@Override
			public void success(Object object, String msg) {
				if(object!=null && object instanceof  CrollList){
					 CrollList mCrollList=(CrollList) object;
					 if(mCrollList.data!=null&&mCrollList.data.size()>0){
						 SharedPreUtil.putStringValue(context, ACTION_QueryCS, new JsonBuild().setModel(object).getJson1());

					 }
				}
				
			}
			
			@Override
			public void fail(String status, String msg, Object object) {
				
			}
		}).start();


		new QueryGameBykindAndTypeTask(context, "3", "", "field1", "0", new BaseBBXTask.Back() {
			@Override
			public void success(Object object, String msg) {
				if (object != null && object instanceof QueryGameBykindAndType) {
					QueryGameBykindAndType mData = (QueryGameBykindAndType) object;
					if (mData.data != null && mData.data.size() >= 0) {
						SharedPreUtil.putStringValue(context, ACTION_QueryGameByModular_WY_N, new JsonBuild().setModel(object).getJson1());
					}
				}
			}

			@Override
			public void fail(String status, String msg, Object object) {
			}
		}).start();
	}

	public static void getHomeDate(final Context context) {
		new QueryGameByModularTask(context, MODE_JP, "Y","0", new Back() {

			@Override
			public void success(Object object, String msg) {
				if(object!=null&& object instanceof QueryGameByModular){
					QueryGameByModular mQueryGameByModular=(QueryGameByModular) object;
					if(mQueryGameByModular.data!=null&&mQueryGameByModular.data.size()>=0){
						SharedPreUtil.putStringValue(context, ACTION_QueryGameByModular_JP_Y, new JsonBuild().setModel(object).getJson1());

					}
				}

			}

			@Override
			public void fail(String status, String msg, Object object) {
				// TODO Auto-generated method stub

			}
		}).start();
		new QueryGameByModularTask(context, MODE_TJ, "Y","0", new Back() {

			@Override
			public void success(Object object, String msg) {
				if(object!=null&& object instanceof QueryGameByModular){
					QueryGameByModular mQueryGameByModular=(QueryGameByModular) object;
					if(mQueryGameByModular.data!=null&&mQueryGameByModular.data.size()>=0){

						SharedPreUtil.putStringValue(context, ACTION_QueryGameByModular_TJ_Y, new JsonBuild().setModel(object).getJson1());

					}
				}

			}

			@Override
			public void fail(String status, String msg, Object object) {
				// TODO Auto-generated method stub

			}
		}).start();

		new QueryGameBykindAndTypeTask(context, "4", "1", "field1", "0", new BaseBBXTask.Back() {
			@Override
			public void success(Object object, String msg) {
				if (object != null && object instanceof QueryGameBykindAndType) {
					QueryGameBykindAndType mData = (QueryGameBykindAndType) object;
					if (mData.data != null && mData.data.size() >= 0) {

						SharedPreUtil.putStringValue(context, ACTION_QueryGameByModular_BT_N, new JsonBuild().setModel(object).getJson1());
					}
				}
			}

			@Override
			public void fail(String status, String msg, Object object) {

			}
		}).start();

		new QueryGameByModularTask(context, MODE_XY, "Y", "0",new Back() {

			@Override
			public void success(Object object, String msg) {
				if(object!=null&& object instanceof QueryGameByModular){
					QueryGameByModular mQueryGameByModular=(QueryGameByModular) object;
					if(mQueryGameByModular.data!=null&&mQueryGameByModular.data.size()>=0){

						SharedPreUtil.putStringValue(context, ACTION_QueryGameByModular_XY_Y, new JsonBuild().setModel(object).getJson1());

					}
				}

			}

			@Override
			public void fail(String status, String msg, Object object) {
				// TODO Auto-generated method stub
			}
		}).start();
	}
}
