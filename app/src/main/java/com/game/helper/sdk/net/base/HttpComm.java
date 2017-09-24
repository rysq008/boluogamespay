package com.game.helper.sdk.net.base;

import com.game.helper.sdk.Config;
/**
 * @Description
 * @Path
 * @Author
 * @Date 2016年8月23日 上午11:49:59
 * @Company
 */
public interface HttpComm {

	String BASE_URL=Config.getInstance().BASE_URL;

	//-----------------------------------------------------------------------------------------------------------------------------------//

	/** 获取验证码接口 */
	String API_V_CODE_Url = "/tcheckcode/getCheckCode";
	/** 获取广告轮播图 */
	String API_getAdList_Url =  "/tadvertisement/getAdList";
	/** 获取广告轮播图 */
	String API_getBunnerList_Url =  "/tadvertisement/getBannerList";
	/** 用户注册接口 */
	String API_register_Url =  "/tuser/register";
	/**用户登录接口 */
	String API_login_Url =  "/tuser/login";
	/**忘记密码/重置密码 */
	String API_resetPwd_Url =  "/tuser/resetPwd";
	/**修改密码接口 */
	String API_updPwd_Url =  "/tuser/updPwd";
	/**修改昵称接口 */
	String API_updNick_Url =  "/tuser/updNick";
	/**修改个性签名接口 */
	String API_updSign_Url =  "/tuser/updSign";


	/**修改性别 */
	String API_updSex_Url =  "/tuser/updSex";
	/**修改绑定的手机号 */
	String API_updPhone_Url =  "/tuser/updPhone";
	/**获取商品列表 */
	String API_getGoodList_Url =  "/cgood/getGoodList";
	/**获取收货地址 */
	String API_getAddressList_Url =  "/caddress/getAddressList";
	/**新增/修改收货地址 */
	String API_saveOrUpdateAddress_Url =  "/caddress/saveOrUpdateAddress";
	/**兑换商品 */
	String API_saveExchange_Url =  "/cgoodexchange/saveExchange";
	/**获取兑换记录 */
	String API_getExchangelist_Url =  "/cgoodexchange/getExchangelist";

	/**抽奖摇一摇 */
	String API_getLottery_Url =  "/cgood/getLottery";
	/**社区摇一摇 */
	String API_getAward_Url =  "/caward/getAward";
	/**  获取工会公告详情*/
	String API_getNoticeById_Url =  "/cguildnotice/getNoticeById";
	/** 获取收到的礼物榜单*/
	String API_getRank_Url =  "/cgiftgive/getRank";
	/** 取消用户关注*/
	String API_deleteFocus_Url =  "/cfocus/deleteFocus";
	/** 赠送礼物*/
	String API_saveGive_Url =  "/cgiftgive/saveGive";

	/**加入工会/退出工会*/
	String API_joinOrExitGuild_Url =  "/cguild/joinOrExitGuild";
	/** 关注用户*/
	String API_saveFocus_Url =  "/cfocus/saveFocus";
	/** 获取关注/粉丝列表*/
	String API_getFocusList_Url =  "/cfocus/getFocusList";
	/** 新增工会/修改工会*/
	String API_saveOrUpdateGuild_Url =  "/cguild/saveOrUpdateGuild";
	/** 获取工会列表*/
	String API_getGuildList_Url =  "/cguild/getGuildList";
	/** 获取工会成员列表*/
	String API_getGuildUser_Url =  "/cguild/getGuildUser";
	/** 新增工会公告*/
	String API_saveNotice_Url =  "/cguildnotice/saveNotice";
	/** 获取工会公告*/
	String API_getNoticeList_Url =  "/cguildnotice/getNoticeList";



	/** 批量退出工会*/
	String API_batchExit_Url =  "/cguild/batchExit";
	/** 工会签到*/
	String API_saveSign_Url =  "/cguildsign/saveSign";
	/** 获取是否签到和签到总人数*/
	String API_judgeSign_Url =  "/cguildsign/judgeSign";
	/**获取工会签到头像列表（默认展示6个）*/
	String API_getSignList_Url =  "/cguildsign/getSignList";
	/**获取工会详情*/
	String API_getGuildById_Url =  "/cguild/getGuildById";
	/**获取礼物列表*/
	String API_getGiftList_Url =  "/cgift/getGiftList";
	/**获取收到的礼物明细列表*/
	String API_getGiveList_Url =  "/cgiftgive/getGiveList";
	/**获取资讯列表*/
	String API_getInfoList_Url =  "/tinfocontent/getInfoList";
	/**评论资讯*/
	String API_addPL_Url =  "/tinfocontentother/addPL";
	/**获取资讯详情*/
	String API_getContenteOtherDetail_Url =  "/tinfocontentother/getContenteOtherDetail";

	/**收藏资讯*/
	String API_addSC_Url =  "/tinfocontentother/addSC";
	/**点赞资讯*/
	String API_addDZ_Url =  "/tinfocontentother/addDZ";
	/**获取关注的人动态列表*/
	String API_getDynamicInfo_1_Url =  "/tdynamicinfo/getDynamicInfo_1";
	/**获取摇一摇剩余次数*/
	String API_getRestTime_Url =  "/caward/getRestTime";
	/**获取广场/工会圈动态列表*/
	String API_getDynamicInfo_Url =  "/tdynamicinfo/getDynamicInfo";
	/**获取动态详情*/
	String API_getDynamicById_Url =  "/tdynamicinfo/getDynamicById";
	/**点赞动态*/
	String API_dzDynamic_Url =  "/tdynamicinfo/dzDynamic";
	/**删除动态*/
	String API_deleteDynamic_Url =  "/tdynamicinfo/deleteDynamic";
	/**评论动态*/
	String API_commentDynamic_Url =  "/tdynamicinfo/commentDynamic";
	/**发布动态*/
	String API_insertDynamic_Url =  "/tdynamicinfo/insertDynamic";
	/**获取工会成员的所有游戏*/
	String API_getGuildGameList_Url =  "/cgamemine/getGuildGameList";
	/**获取资讯类别*/
	String API_getContentTypeList_Url =  "/tinfotype/getContentTypeList";
	/**获取热门工会推荐*/
	String API_getHotGuild_Url =  "/cguild/getHotGuild";
	/**修改备注名称*/
	String API_updateRemarkName_Url =  "/cfocus/updateRemarkName";
	/**获取个人动态列表*/
	String API_getSelfDynamic_Url =  "/tdynamicinfo/getSelfDynamic";
	/**删除收货地址接口*/
	String API_deleteAddress_Url =  "/caddress/deleteAddress";
	/**是否关注*/
	String API_judgeFocus_Url =  "/cfocus/judgeFocus";

	/**获取客服列表*/
	String API_getContactList_Url =  "/ccontact/getContactList";
	/**获取游戏礼包列表*/
	String API_listGift_Url =  "/cgamegift/listGift";
	/**获取我的游戏*/
	String API_mygame_Url =  "/cgame/mygame";
	/**获取网游，单机游戏*/
	String API_queryGameBykindAndType_Url =  "/cgame/queryGameBykindAndType";
	/**获取主题游戏列表*/
	String API_queryTheme_Url =  "/cgame/queryTheme";
	/**获取首个主题游戏*/
	String API_queryFirstOne_Url =  "/cgame/queryFirstOne";
	/**获取主题下的游戏*/
	String API_queryThemeGame_Url =  "/cgame/queryThemeGame";
	/**精品，特价，新游*/
	String API_queryGameByModular_Url =  "/cgame/queryGameByModular";
	/**获取游戏类型*/
	String API_cgametypelist_Url =  "/cgametype/list";
	/** 礼包接口*/
	String API_queryGameGift_Url =  "/cgamegift/queryGameGift";
	/** 获取游戏分类*/
	String API_cgamekindlist_Url =  "/cgamekind/list";
	/** 搜索礼包接口*/
	String API_searchGift_Url =  "/cgamegift/searchGift";
	/** 领取礼包接口*/
	String API_getGift_Url =  "/cgamegift/getGift";
	/** 首页获取活动公告*/
	String API_getInfoAct_Url =  "/tinfocontent/getInfoAct";

	/** 游戏攻略-保存攻略*/
	String API_saveGameCut_Url =  "/cgamecut/saveGameCut";//
	/** 游戏攻略-点赞，差劲，收藏，分享，评论接口*/
	String API_saveGameCutOptype_Url =  "/cgamecut/saveGameCutOptype";//
	/** 游戏攻略-查询攻略*/
	String API_queryGameCut_Url =  "/cgamecut/queryGameCut";//
	/** 游戏攻略-获取攻略评论*/
	String API_queryGameCutPl_Url =  "/cgamecut/queryGameCutPl";//
	/** 游戏评论-添加:游戏评论*/
	String API_saveGamePl_Url =  "/cgamepl/saveGamePl";//
	/**游戏评论-查询:游戏评论*/
	String API_queryGamePl_Url =  "/cgamepl/queryGamePl";//
	/**游戏评论-点赞:游戏评论*/
	String API_dzGamePl_Url =  "/cgamepl/dzGamePl";//



	/**获取用户最新的金币*/
	String API_queryPtb_Url =  "/tuser/queryPtb";//
	/**根据Id获取游戏*/
	String API_getGameById_Url =  "/cgame/getGameById";
	/**根据Id获取游戏攻略*/
	String API_getGameCutById_Url =  "/cgamecut/getGameCutById";
	/**单机榜*/
	String API_getNoNetGame_Url =  "/cgame/getNoNetGame";
	/**热门榜*/
	String API_getHotGame_Url =  "/cgame/getHotGame";
	/**网游榜*/
	String API_getNetGame_Url =  "/cgame/getNetGame";


	/**获取文章*/
	String API_getBasic_Url =  "/cbasic/getBasic";
	/**获取问题列表*/
	String API_getQuestionlist_Url =  "/cquestion/getQuestionlist";
	/**获取问题详情*/
	String API_getQuestion_Url =  "/uestion/getQuestion";//
	/**获取邀请朋友消费汇总*/
	String API_getShareResult_Url =  "/tuser/getShareResult";
	/**获取我的折扣号*/
	String API_queryMyAccount_Url =  "/cplataccount/queryMyAccount";
	/**获取我的折扣号*/
	String API_getAccount_Url =  "/cplataccount/getAccount";
	/**获取平台列表*/
	String API_cgameplatlist_Url =  "/cgameplat/list";
	/**领取首充卡*/
	String API_getCard_Url =  "/ccard/getCard";
	/**获取我的首充卡*/
	String API_queryMyCard_Url =  "/ccard/queryMyCard";

	/**移动头像接口*/
	String API_changeIconOrderby_Url =  "/tuser/changeIconOrderby";//
	/**获取用户头像接口*/
	String API_queryUserIcon_Url =  "/tuser/queryUserIcon";//
	/**删除头像接口*/
	String API_removeUserIcon_Url =  "/tuser/removeUserIcon";
	/**上传个人头像接口*/
	String API_addUserIcon_Url =  "/tuser/addUserIcon";
	/**获取金币规则*/
	String API_getPtbRule_Url =  "/cptbrule/getPtbRule";
	/**获取我的游戏订单*/
	String API_getMyGameOrder_Url =  "/cgameorder/getMyGameOrder";
	/**反馈问题*/
	String API_savefeedBack_Url =  "/cfeedback/savefeedBack";
	/**删除我的礼包接口*/
	String API_removeMyGift_Url =  "/cgamegift/removeMyGift";
	/**记录浏览游戏接口*/
	String API_browse_Url =  "/cgame/browse";
	/**记录下载游戏接口*/
	String API_downloadFinish_Url =  "/cgame/downloadFinish";
	/**获取游戏详情介绍的图片*/
	String API_queryGameImg_Url =  "/cgame/queryGameImg";
	/**获取礼包详情*/
	String API_getGiftDetail_Url =  "/cgamegift/getGiftDetail";
	/**获取热搜词*/
	String API_queryHotWord_Url =  "/chotword/queryHotWord";
	/**搜索游戏接口*/
	String API_queryGameByHotWord_Url =  "/cgame/queryGameByHotWord";
	/**我的礼包接口*/
	String API_queryMyGift_Url =  "/cgamegift/queryMyGift";

	/**游戏充值接口*/
	String API_createGameOrder_Url =  "/cgameorder/createGameOrder";
	/**充值金币下订单接口*/
	String API_createOrder_Url =  "/cptborder/createOrder";
	/**我的收藏*/
	String API_getMyCollection_Url =  "/ccollection/getMyCollection";
	/**人脉榜*/
	String API_connectUser_Url =  "/tuser/connectUser";
	/**收益榜*/
	String API_incomeUser_Url =  "/tuser/incomeUser";
	/**已节省人民币接口*/
	String API_countSaveMoney_Url =  "/cgameorder/countSaveMoney";
	/**明星榜*/
	String API_starUser_Url =  "/tuser/starUser";
	/**我的收入或者我的支出*/
	String API_getTradeList_Url =  "/ctrade/getTradeList";

	/**获取订单详情*/
	String API_getOrderByNo_Url =  "/cgameorder/getOrderByNo";
	/**获取个人主页信息*/
	String API_getMainPageInfo_Url =  "/tuser/getMainPageInfo";


	/**获取我的平台签到记录*/
	String API_getPlatformSign_Url =  "/cplatformsign/getPlatformSign";
	/**判断是否平台签到*/
	String API_juedeSign_Url =  "/cplatformsign/juedeSign";
	/**平台签到*/
	String API_platformSign_Url =  "/cplatformsign/platformSign";
	/**提现申请接口*/
	String API_saveWithdraw_Url =  "/cwithdraw/saveWithdraw";
	/**允许提现的金额*/
	String API_allowTxMoney_Url =  "/cwithdraw/allowTxMoney";

	/**判断领折扣号按钮是否置灰*/
	String API_isGetMoreThanThree_Url =  "/cplataccount/isGetMoreThanThree";
	/**游戏账号是否允许首充*/
	String API_isAccountAllowSc_Url =  "/cplataccount/isAccountAllowSc";

	/**绑定折扣号*/
	String API_addMyZkh_Url =  "/cplataccount/addMyZkh";
	/**获取我的折扣号*/
	String API_queryMyZkh_Url =  "/cplataccount/queryMyZkh";


	/**获取我的反馈问题*/
	String API_getfeedBack_Url =  "/cfeedback/getFeedBack";
	/**获取我的提现记录*/
	String API_myWithdraw_Url =  "/cwithdraw/myWithdraw";
	/**获取金币充值订单详情*/
	String API_cptbordergetOrderByNo_Url =  "/cptborder/getOrderByNo";
	/**检查版本更新*/
	String API_version_check_Url =  "/cversion/version_check";
	/**分享资讯新增金币*/
	String API_insertShare_Url =  "/cshare/insertShare";

	/**获取滚动广告*/
	String API_crollList_Url =  "/croll/list";

	/**客户端列表*/
	String API_fromlist="/cgameplat/list";

	/**根据客户端查询游戏*/
	String API_queryGameByPlatId="/cgame/queryGameByPlatId";

	/**删除游戏订单*/
	String API_delGameOrderByNo="/cgameorder/delGameOrderByNo";

	/**查询游戏的首充续充*/
	String API_findGameDisCount="/cgame/findGameDisCount";

	/**
	 * 验证码登录
	 */
	String API_checkCodeLogin="/tuser/checkCodeLogin";


	//-----------------------------------------------------------------------------------------------------------------------------------//



	String SESSION_ID = "sessionid";
	String ACCEPT_ENCODING = "Accept-Encoding";
	String GZIP = "gzip";
	String AUTH_SECRET = "";
	String METHOD_GET = "GET";
	String AUTH_KEY = "Authorization";
	int HTTP_TIMEOUT = 20 * 1000;
	String ACCEPT_CHARSET = "Accept-Charset";
	String RESULT = "result";

	int API_CODE_SUCCESS = 100;
	int CONNECTION_TIMEOUT = 20000;
	int SO_TIMEOUT = 20000;
	int TIMEOUT_CODE=408;//http状态码 408 （请求超时） 服务器等候请求时发生超时。

}