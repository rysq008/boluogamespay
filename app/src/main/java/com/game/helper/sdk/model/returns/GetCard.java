package com.game.helper.sdk.model.returns;

public class GetCard {

	/*"data": {
    "cardId": 1,
    "userId": 10000,
    "cardName": "首充卡",
    "cardType": "sc",
    "isUsed": null,
    "money": 5.99,
    "useTime": null,
    "createTimeString": "2016-10-20 14:05:45",
    "useTimeString": null
},*/
	public Card data;
	public class Card{
		public String cardId;
		public String userId;
		public String cardName;
		public String cardType;
		public String isUsed;
		public String money;
		public String useTime;
		public String createTimeString;
		public String useTimeString;
	}
}
