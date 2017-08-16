package com.game.helper.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

public class StringUtil {

	// private static final char[] myFilter = { '!', '@', '#', '$', '%', '^',
	// '&', '*', '(', ')', '{', '}', '[', ']', '|',
	// '.', '/', '?', '`', '~', ',', '，', '。', '"', '“', '、', '：', '？', '+',
	// '-', '！', '￥', '…', '（', '）', '—',
	// '】', '【', '”', '《', '》', '>', '<', '\'', ':' };

	private final static char KOREAN_UNICODE_START = '가';
	private final static char KOREAN_UNICODE_END = '힣';
	private final static char KOREAN_UNIT = '까' - '가';
	private final static char[] KOREAN_INITIAL = { 'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ',
			'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ',
			'ㅎ' };

	// 国标码和区位码转换常量
	static final int GB_SP_DIFF = 160;
	// 存放国标一级汉字不同读音的起始区位码
	static final int[] secPosValueList = { 1601, 1637, 1833, 2078, 2274, 2302,
			2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027,
			4086, 4390, 4558, 4684, 4925, 5249, 5600 };
	// 存放国标一级汉字不同读音的起始区位码对应读音
	static final char[] firstLetter = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'w', 'x',
			'y', 'z' };

	// 获取一个字符串的拼音码
	public static String getFirstLetter(String oriStr) {
		String str = oriStr.toLowerCase();
		StringBuffer buffer = new StringBuffer();
		char ch;
		char[] temp;
		for (int i = 0; i < str.length(); i++) { // 依次处理str中每个字符
			ch = str.charAt(i);
			temp = new char[] { ch };
			byte[] uniCode = null;
			try {
				uniCode = new String(temp).getBytes("GBK");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (uniCode[0] < 128 && uniCode[0] > 0) { // 非汉字
				buffer.append(temp);
			} else {
				buffer.append(convert(uniCode));
			}
		}
		return buffer.toString();
	}

	/**
	 * 获取一个汉字的拼音首字母。 GB码两个字节分别减去160，转换成10进制码组合就可以得到区位码
	 * 例如汉字“你”的GB码是0xC4/0xE3，分别减去0xA0（160）就是0x24/0x43
	 * 0x24转成10进制就是36，0x43是67，那么它的区位码就是3667，在对照表中读音为‘n’
	 */
	static char convert(byte[] bytes) {

		char result = '-';
		int secPosValue = 0;
		int i;
		for (i = 0; i < bytes.length; i++) {
			bytes[i] -= GB_SP_DIFF;
		}
		secPosValue = bytes[0] * 100 + bytes[1];
		for (i = 0; i < 23; i++) {
			if (secPosValue >= secPosValueList[i]
					&& secPosValue < secPosValueList[i + 1]) {
				result = firstLetter[i];
				break;
			}
		}
		return result;
	}

	/**
	 * 判断是否为汉字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isChiness(String str) {
		int length = str.length();
		for (int i = 0; i < length; i++) {
			if (str.substring(i, i + 1).matches("[\u4e00-\u9fa5]+")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 验证码长度验证
	 * 
	 * @param verifyLength
	 * @param verify
	 * @return
	 */
	public static boolean isVerify(int verifyLength, String verify) {
		Pattern p = Pattern.compile("\\d{" + verifyLength + "}$");
		Matcher m = p.matcher(verify);
		return m.matches();
	}

	/**
	 * 验证是否为合法的密码
	 * 
	 * @param minLength
	 *            密码最小长度
	 * @param maxLength
	 *            密码最大长度
	 * @param psw
	 * @return
	 */
	public static boolean isRightPsw(int minLength, int maxLength, String psw) {

		if (TextUtils.isEmpty(psw))
			return false;
		// 不包含空格 回车换行符 中文
		Pattern pa = Pattern.compile("^[^\\s\u4e00-\u9fa5]{" + minLength + ","
				+ maxLength + "}$");
		Matcher m = pa.matcher(psw);
		return m.matches();
	}

	/**
	 * 
	 * @param minLength
	 *            最小匹配长度
	 * @param maxLength
	 *            最大匹配长度
	 * @param str
	 * @return
	 */
	public static boolean isInLength(int minLength, int maxLength, String str) {
		if (isNotNullString(str)) {
			int strLength = str.length();
			if (strLength >= minLength && strLength <= maxLength) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是不是一个合法的电子邮件地址
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		Pattern emailer = Pattern
				.compile("^([a-z0-9A-Z]+[-\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
		// email = email.toLowerCase();
		// if(email.endsWith(".con")) return false;
		// if(email.endsWith(".cm")) return false;
		// if(email.endsWith("@gmial.com")) return false;
		// if(email.endsWith("@gamil.com")) return false;
		// if(email.endsWith("@gmai.com")) return false;
		return emailer.matcher(email).matches();
	}

	/**
	 * 由数字 字母 下划线组成
	 * 
	 * @param userName
	 * @return
	 */
	public static boolean isUserName(String userName) {
		Pattern emailer = Pattern.compile("^[0-9a-zA-Z_]{1,}$");
		// email = email.toLowerCase();
		// if(email.endsWith(".con")) return false;
		// if(email.endsWith(".cm")) return false;
		// if(email.endsWith("@gmial.com")) return false;
		// if(email.endsWith("@gamil.com")) return false;
		// if(email.endsWith("@gmai.com")) return false;
		return emailer.matcher(userName).matches();
	}

	/**
	 * 判断是否为手机号码
	 * 
	 * @param mobiles
	 *            手机号
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		return isRule("^((14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$",
				mobiles);
	}

	public static boolean isMobileNO2(String mobiles) {
		return isRule("1\\d{10}", mobiles);
	}

	public static boolean isRule(String matcher, String mobiles) {
		Pattern p = Pattern.compile(matcher);
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	public static boolean isRule(Context context,String rule,String str,int toastId){
		if (isRule(rule, str)) {
			return true;
		}
		if(toastId != -1)
			ToastUtil.showToast(context, toastId);
		return false;
	}
	/**
	 * 判断是否为空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotNullString(String... str) {
		for (String temp : str) {
			if (temp == null || "".equals(temp) || temp.length() == 0) {
				return false;
			}
		}
		return true;
	}

	public static boolean match(String value, String keyword) {
		if (value == null || keyword == null)
			return false;
		if (keyword.length() > value.length())
			return false;

		int i = 0, j = 0;
		do {
			if (isKorean(value.charAt(i)) && isInitialSound(keyword.charAt(j))) {
				if (keyword.charAt(j) == getInitialSound(value.charAt(i))) {
					i++;
					j++;
				} else if (j > 0)
					break;
				else
					i++;
			} else {
				if (keyword.charAt(j) == value.charAt(i)) {
					i++;
					j++;
				} else if (j > 0)
					break;
				else
					i++;
			}
		} while (i < value.length() && j < keyword.length());

		return (j == keyword.length()) ? true : false;
	}

	private static boolean isKorean(char c) {
		if (c >= KOREAN_UNICODE_START && c <= KOREAN_UNICODE_END)
			return true;
		return false;
	}

	private static boolean isInitialSound(char c) {
		for (char i : KOREAN_INITIAL) {
			if (c == i)
				return true;
		}
		return false;
	}

	private static char getInitialSound(char c) {
		return KOREAN_INITIAL[(c - KOREAN_UNICODE_START) / KOREAN_UNIT];
	}

	public static String setAdd(String addr, String splitWord) {
		String addrStr = addr;
		if (!TextUtils.isEmpty(addr)) {
			String addr2[] = addr.split(splitWord);
			if (addr2.length > 1) {
				addrStr = addr2[1];
			}
		}
		return addrStr;
	}

	public static String getCodePhone(String tel) {
		if(TextUtils.isEmpty(tel)) {
			return null;
		}
		String pcode = tel.toString();
		if (!TextUtils.isEmpty(pcode) && pcode.length() > 7) {
			String tag = pcode.substring(3, 3 + 4);
			pcode = pcode.replaceFirst(tag, "****");
		}
		return pcode;
	}
	/**
	 * *现存复姓
	*/
	public static String[] fuXing = new String[] {
	"欧阳","太史","端木","上官","司马","东方","独孤","南宫","万俟","闻人","夏侯","诸葛","尉迟",
	"公羊","赫连","澹台","皇甫","宗政","濮阳","公冶","太叔","申屠","公孙","慕容","仲孙","钟离",
	"长孙","宇文","司徒","鲜于","司空","闾丘","子车","亓官","司寇","巫马","公西","颛孙","壤驷",
	"公良","漆雕","乐正","宰父","谷梁","拓跋","夹谷","轩辕","令狐","段干","百里","呼延","东郭",
	"南门","羊舌","微生","公户","公玉","公仪","梁丘","公仲","公上","公门","公山","公坚","左丘",
	"公伯","西门","公祖","第五","公乘","贯丘","公皙","南荣","东里","东宫","仲长","子书","子桑",
	"即墨","达奚","褚师","吴铭" };
	
}
