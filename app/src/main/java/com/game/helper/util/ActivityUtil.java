
package com.game.helper.util;

import com.game.helper.BbxBaseActivity;
import android.content.Context;


/**

 */
public class ActivityUtil {

	public static BbxBaseActivity isBaseActivity(Context context) {
		if (context != null && context instanceof BbxBaseActivity) {
			return (BbxBaseActivity) context;
		}
		return null;

	}
}
