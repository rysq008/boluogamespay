package com.game.helper.view.dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.game.helper.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class ShareDialog{		

	private AlertDialog dialog;
	private GridView gridView;
	private RelativeLayout cancelButton;
	private SimpleAdapter saImageItems;
	private int[] image={R.drawable.shouye_209,R.drawable.shouye_207,R.drawable.ssdk_oks_classic_sinaweibo,R.drawable.shouye_205,R.drawable.ssdk_oks_classic_wechatmoments};
	private String[] name={"QQ好友","QQ空间","新浪微博","微信好友","朋友圈"};

	public ShareDialog(Context context){

		dialog=new AlertDialog.Builder(context).create();
		dialog.show();
		Window window = dialog.getWindow();
		WindowManager.LayoutParams windowparams = window.getAttributes();  
		WindowManager m = ((Activity) context).getWindowManager();
		Rect rect = new Rect();  
		View view1 = window.getDecorView();  
		view1.getWindowVisibleDisplayFrame(rect);  
		Display d = m.getDefaultDisplay();
		windowparams.width = d.getWidth();  
		//windowparams.alpha = 0.5f;
		//window.setBackgroundDrawableResource(context.getResources().getColor(R.color.gh_pop_dialog_color));
		  ColorDrawable dw = new ColorDrawable(0x7F000000);  
		window.setBackgroundDrawable(dw);  
		window.setAttributes(  (WindowManager.LayoutParams) windowparams);
		window.setGravity(Gravity.BOTTOM);
		window.setContentView(R.layout.share_dialog);
		gridView=(GridView) window.findViewById(R.id.share_gridView);
		cancelButton=(RelativeLayout) window.findViewById(R.id.share_cancel);
		List<HashMap<String, Object>> shareList=new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<image.length;i++){
			HashMap<String, Object> map = new HashMap<String, Object>();  
			map.put("ItemImage", image[i]);//添加图像资源的ID  
			map.put("ItemText", name[i]);//按序号做ItemText  
			shareList.add(map);
		}

		saImageItems =new SimpleAdapter(context, shareList, R.layout.share_item, new String[] {"ItemImage","ItemText"}, new int[] {R.id.imageView1,R.id.textView1});
		gridView.setAdapter(saImageItems);
	}

	public void setCancelButtonOnClickListener(OnClickListener Listener){
		cancelButton.setOnClickListener(Listener);
	}

	public void setOnItemClickListener(OnItemClickListener listener){
		gridView.setOnItemClickListener(listener);
	}


	/**
	 * 关闭对话框
	 */
	public void dismiss() {
		dialog.dismiss();
	}
}