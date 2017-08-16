package com.game.helper.view.dialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.game.helper.BaseActivity;
import com.game.helper.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 */
public class MyAlertDailogEd extends Dialog implements
View.OnClickListener {
	public interface onClickLeftListener {
		public void onClickLeft();
	}

	public interface onClickRightListener {
		public void onClickRight();
	}

	public interface onClickSingleListener {
		public void onClickSingle();
	}

	public onClickLeftListener monClickLeftListener;
	public onClickRightListener monClickRightListener;
	public onClickSingleListener mOnClickSingleListener;
	public LinearLayout layout_dailog;
	private LinearLayout layout_double;

	private Button btn_single;
	private TextView tv_title;
	private EditText tv_content;
	private TextView tv_contentT;
	private Button btn_cencel, btn_confirm;
	private boolean cancelable = true;
	private Context context;
	private boolean isFinish=false;
	private View msView;
	public MyAlertDailogEd(Context context) {
		super(context,R.style.Dialog_bocop);//:R.style.Dialog_bocop2
		this.context=context;
		init();
	}

	private void init() {
		View contentView = View.inflate(getContext(), R.layout.dailog_alert_ed,
				null);
		setContentView(contentView);
		msView= (View) findViewById(R.id.msView);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_content = (EditText) findViewById(R.id.tv_content);
		tv_contentT = (TextView) findViewById(R.id.tv_contentT);
		btn_cencel = (Button) findViewById(R.id.btn_cencel);
		btn_confirm = (Button) findViewById(R.id.btn_confirm);
		layout_double = (LinearLayout) findViewById(R.id.layout_double);
		layout_dailog = (LinearLayout) findViewById(R.id.layout_dailog);
		btn_single = (Button) findViewById(R.id.btn_single);
		btn_cencel.setOnClickListener(this);
		btn_confirm.setOnClickListener(this);
		btn_single.setOnClickListener(this);
		layout_dailog.setVisibility(View.GONE);
		this.setCancelable(false);
		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (cancelable) {
					dismiss();
				}
			}
		});
	}

	@Override
	public void show() {
		super.show();
		Animation a = AnimationUtils.loadAnimation(context, R.anim.dialog_in);
		layout_dailog.startAnimation(a);
		layout_dailog.setVisibility(View.VISIBLE);
		isFinish=false;
		//MessageDialog.list_dialog.add(MessageDialog.DIALOG_SHOW);
	}

	@SuppressLint("NewApi")
	@Override
	public void dismiss() {
		if(isFinish)
			super.dismiss();
		/*int size=MessageDialog.list_dialog.size();
		if(size>0){
			MessageDialog.list_dialog.remove(size-1);
		}*/
		Animation a = AnimationUtils.loadAnimation(context, R.anim.dialog_out);
		layout_dailog.startAnimation(a);
		a.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation anim) {}

			@Override
			public void onAnimationRepeat(Animation anim) {}	
			@Override
			public void onAnimationEnd(Animation anim) {
				isFinish=true;
				dismiss();
			}
		});

	}

	/**
	 * 设置单按钮没收
	 */
	public void setSingle(String text) {
		layout_double.setVisibility(View.GONE);
		btn_single.setVisibility(View.VISIBLE);
		btn_single.setText(text);
	}
	/**
	 * 设置没收所有按钮
	 */
	public void setNoBtn() {
		layout_double.setVisibility(View.GONE);
	    msView.setVisibility(View.INVISIBLE);
	}
	@Override
	public void setCancelable(boolean flag) {
		cancelable = flag;
		super.setCancelable(flag);
	}
	public String getEdText(){
		 return tv_content.getText().toString();	
	}
	public void setTitle(String text) {
		if(text==null){
			tv_title.setVisibility(View.GONE);
		}
		else{
			tv_title.setVisibility(View.VISIBLE);
			tv_title.setText(text);
		}

	}
	public void setTitle(String text,int mGravity) {
		if(text==null){
			tv_title.setVisibility(View.GONE);
		}
		else{
			tv_title.setVisibility(View.VISIBLE);
			tv_title.setText(text);
			tv_title.setGravity(mGravity);
		}

	}
	public void setContent1(String text) {
		//		if(text==null){
		//			tv_content.setVisibility(View.GONE);
		//		}
		//		else
		//		tv_content.setText(text);
		//		tv_content.setVisibility(View.GONE);
		if(text==null){
			tv_content.setVisibility(View.GONE);
		}
		else{
			tv_content.setVisibility(View.VISIBLE);
			tv_content.setText(text);
		}

	}
	public void setContent1(String text,int mGravity) {
		//		if(text==null){
		//			tv_content.setVisibility(View.GONE);
		//		}
		//		else
		//		tv_content.setText(text);
		//		tv_content.setVisibility(View.GONE);
		if(text==null){
			tv_content.setVisibility(View.GONE);
		}
		else{
			tv_content.setVisibility(View.VISIBLE);
			tv_content.setText(text);
			tv_content.setGravity(mGravity);
		}

	}
	public void setContent1(Spanned text) {
		//		if(text==null){
		//			tv_content.setVisibility(View.GONE);
		//		}
		//		else
		//		tv_content.setText(text);
		//		tv_content.setVisibility(View.GONE);
		if(text==null){
			tv_content.setVisibility(View.GONE);
		}
		else{
			tv_content.setVisibility(View.VISIBLE);
			tv_content.setText(text);
		}

	}
	public void setContentT(String text) {
		//		if(text==null){
		//			tv_content.setVisibility(View.GONE);
		//		}
		//		else
		//		tv_content.setText(text);
		//		tv_content.setVisibility(View.GONE);
		if(text==null){
			tv_contentT.setText("");
			//tv_contentT.setVisibility(View.GONE);
		}
		else{
			//tv_contentT.setVisibility(View.VISIBLE);
			tv_contentT.setText(text);
		}

	}
	/*public void setContent(String text) {
		//		if(text==null){
		//			tv_content.setVisibility(View.GONE);
		//		}
		//		else
		//		tv_content.setText(text);
		tv_content.setVisibility(View.GONE);
		if(text==null){
			tv_title.setVisibility(View.GONE);
		}
		else{
			tv_title.setVisibility(View.VISIBLE);
			tv_title.setText(text);
		}

	}*/
	/*********************/
	/*public void setContentForMsg(String text) {
		//		if(text==null){
		//			tv_content.setVisibility(View.GONE);
		//		}
		//		else
		//		tv_content.setText(text);
		tv_content.setVisibility(View.GONE);
		if(text==null){
			tv_title.setVisibility(View.GONE);
		}
		else{
			tv_title.setVisibility(View.VISIBLE);
			//tv_title.setText(text);
			init(tv_title,text);
		}

	}*/
	private void init(TextView tv,String CONTENT) {  
		tv_title.setClickable(true);
		tv.setMovementMethod(LinkMovementMethod.getInstance());  
		SpannableString s = new SpannableString(CONTENT);     
		Matcher m = Pattern.compile(REG).matcher(s.toString());  
		Matcher m1 = Pattern.compile(landlinePhoneRegexp).matcher(s.toString());  
		while (m.find()) {          
			String text = m.group();  
			TextClickableSpan span = new TextClickableSpan(text);      
			s.setSpan(span,m.start(),m.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);      
		} 
		while (m1.find()) {          
			String text = m1.group();  
			TextClickableSpan span = new TextClickableSpan(text);      
			s.setSpan(span,m1.start(),m1.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);      
		} 
		tv.setText(s);   
	}    
	private static final String REG = "1\\d{10}"; 
	private static final String REG1 = "(\\d+)"; 
	/*
	 * 可接受的电话格式有：
	 */
	private static String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{5})$";
	/*
	 * 可接受的电话格式有：
	 */
	private static String expression2 = "^\\(?(\\d{3})\\)?[- ]?(\\d{4})[- ]?(\\d{4})$";
	private static String expression1 = "((^(13|15|18)[0-9]{9}$)|"
			+ "(^0[1,2]{1}\\d{1}-?\\d{8}$)|"
			+ "(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|"
			+ "(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|"
			+ "(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
	private static String expression3 = "((^(13|15|18)[0-9]{9}$)|"
			+ "(^0[1,2]{1}d{1}-?d{8}$)|"
			+ "(^0[3-9] {1}d{2}-?d{7,8}$)|"
			+ "(^0[1,2]{1}d{1}-?d{8}-(d{1,4})$)|"
			+ "(^0[3-9]{1}d{2}-? d{7,8}-(d{1,4})$))";
	//用于匹配手机号码
	private final static String REGEX_MOBILEPHONE = "^01[3458]\\d{9}$";
	//用于匹配固定电话号码
	private final static String REGEX_FIXEDPHONE = "^(010|02\\d|0[3-9]\\d{2})\\d{6,8}$";
	private final static String REGEX="(("+REGEX_MOBILEPHONE+")|"+"("+REGEX_FIXEDPHONE+"))";
	 //固定电话正则表达式
	private final static String landlinePhoneRegexp = "(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|" +
            "(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)";
	public static boolean isPhoneNumberValid(String phoneNumber) {
		boolean isValid = false;
		
		CharSequence inputStr = phoneNumber;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);

		Pattern pattern2 = Pattern.compile(expression2);
		Matcher matcher2 = pattern2.matcher(inputStr);
		if(matcher.matches() || matcher2.matches()) {
			isValid = true;
		}
		return isValid;
	}
	/** 验证号码 手机号 固话均可
	 * */
	 public static boolean isPhoneNumberValids(String phoneNumber) {
	 boolean isValid = false;

	 String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
	 CharSequence inputStr = phoneNumber;

	 Pattern pattern = Pattern.compile(expression);

	 Matcher matcher = pattern.matcher(inputStr);

	 if (matcher.matches() ) {
	 isValid = true;
	 }

	 return isValid;

	 }
	public class TextClickableSpan extends ClickableSpan {       
		private String text;        
		public TextClickableSpan(String text) {            
			this.text = text;       
		}       
		@Override        
		public void onClick(View view) {  

			String number = this.text;  
			//用intent启动拨打电话  
			Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+number)); 
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			((BaseActivity)context).startActivity(intent); 
		}    
	}
	/*private void filterNumber(Spannable s) { 
		Matcher m = Pattern.compile(REG).matcher(s.toString());      
		while (m.find()) {          
			String text = m.group();           
			TextClickableSpan span = new TextClickableSpan(text);      
			s.setSpan(span,m.start(),m.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);      
		}    
	}
	public String getPhoneNumber(String numer)
	{
		char[] temp = numer.toCharArray();
		String value="";
		int licz=0,licznik=0;

		for(int i=0;i<temp.length;i++)
		{
			if(licz<9)
			{
				if(Character.toString(temp[i]).matches("[0-9]"))
				{
					value+=Character.toString(temp[i]);
					licznik++;
				}
				else if(Character.toString(temp[i]).matches("\u0020|\\-|\\(|\\)"))
				{

				}
				else
				{
					value="";
					licz=0;
				}
			}
		}

		if(value.length()!=9) 
		{
			value=null;
		}
		else
		{
			value="tel:"+value.trim();
		}

		return value;
	}
*/
	/*********************/
	public void setContent(Spanned text) {
		//		if(text==null){
		//			tv_content.setVisibility(View.GONE);
		//		}
		//		else
		//		tv_content.setText(text);
		tv_content.setVisibility(View.GONE);
		if(text==null){
			tv_title.setVisibility(View.GONE);
		}
		else{
			tv_title.setVisibility(View.VISIBLE);
			tv_title.setText(text);
			tv_title.setClickable(true);
			tv_title.setMovementMethod(LinkMovementMethod.getInstance());
		}

	}
	
	/*public void setContentWV(String text) {
			
			tv_content.setVisibility(View.GONE);
			tv_title.setVisibility(View.GONE);
			tv_contentT.setVisibility(View.GONE);
			wv_contentT.setVisibility(View.VISIBLE);
			wv_contentT.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
			wv_contentT.getSettings().setDefaultTextEncodingName("UTF -8");
			wv_contentT.loadData(text, "text/html; charset=UTF-8", null);
	}*/
	public void setLeftButtonText(String text) {
		btn_cencel.setText(text);
	}

	public void setRightButtonText(String text) {
		btn_confirm.setText(text);
	}

	public void setOnClickLeftListener(onClickLeftListener honClickLeftListener) {
		monClickLeftListener = honClickLeftListener;
	}

	public void setOnClickRightListener(
			onClickRightListener honClickRightListener) {
		monClickRightListener = honClickRightListener;
	}

	public void setOnClickSingleListener(
			onClickSingleListener hOnClickSingleListener) {
		mOnClickSingleListener = hOnClickSingleListener;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_cencel:
			if (monClickLeftListener != null)
				monClickLeftListener.onClickLeft();
			dismiss();
			break;
		case R.id.btn_confirm:
			if (monClickRightListener != null)
				monClickRightListener.onClickRight();
			dismiss();
			break;
		case R.id.btn_single:
			if (mOnClickSingleListener != null)
				mOnClickSingleListener.onClickSingle();
			dismiss();
			break;
		}

	}
}
