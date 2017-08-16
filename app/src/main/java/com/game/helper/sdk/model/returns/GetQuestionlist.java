package com.game.helper.sdk.model.returns;

import java.util.List;

public class GetQuestionlist {

/*	"data":[{"context":"你是孙楠的还是女的",
 *           "questionId":1,"orderBy":2,
 *           "questionName":"问题1"},
	        {"context":"唉，生活不易",
	        "questionId":2,"orderBy":1,"questionName":"问题2"}]*/
	public List<Question> data;
	public class Question{
		public String context;
		public String questionId;
		public int orderBy;
		public String questionName;
		
	}

}
