package com.game.helper.view.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.game.helper.R;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhoneChargeDailog extends Dialog implements
        View.OnClickListener {


    @BindView(R.id.btn_single)
    Button btnSingle;
    @BindView(R.id.gridview)
    GridView gridview;

    public interface onClickSingleListener {
        public void onClickSingle();
    }

    public onClickSingleListener mOnClickSingleListener;
    public LinearLayout layout_dailog;
    private LinearLayout layout_double;
    private Button btn_single;
    private Context context;
    private boolean isFinish = false;
    private View msView;

    //定义图标下方的名称数组
    private String[] name_m = {
            "30M",
            "50M",
            "100M",
            "300M",
            "500M",
            "1000M"
    };


    private String[] name_jf = {
            "300积分",
            "500积分",
            "1000积分",
            "2000积分",
            "3000积分",
            "4000积分"
    };

    public PhoneChargeDailog(Context context) {
        super(context, R.style.Dialog_bocop);//:R.style.Dialog_bocop2
        this.context = context;
        init();
    }

    private void init() {
        View contentView = View.inflate(getContext(), R.layout.phone_charge_detial,
                null);
        ButterKnife.bind(this, contentView);
        setContentView(contentView);
        msView = (View) findViewById(R.id.msView);
        layout_double = (LinearLayout) findViewById(R.id.layout_double);
        layout_dailog = (LinearLayout) findViewById(R.id.layout_dailog);
        btn_single = (Button) findViewById(R.id.btn_single);
        btn_single.setOnClickListener(this);
        layout_dailog.setVisibility(View.GONE);
        setCanceledOnTouchOutside(true);


        int length = name_m.length;

        //生成动态数组，并且转入数据
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("Item_m", name_m[i]);
            map.put("Item_jf", name_jf[i]);//按序号做ItemText
            lstImageItem.add(map);
        }
        //生成适配器的ImageItem 与动态数组的元素相对应
        SimpleAdapter saImageItems = new SimpleAdapter(context, lstImageItem,//数据来源
                R.layout.phone_detial_item,//item的XML实现

                //动态数组与ImageItem对应的子项
                new String[]{"Item_m", "Item_jf"},

                //ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[]{R.id.phone_detial_item_m_tv, R.id.phone_detial_item_jifen_tv});
        //添加并且显示
        gridview.setAdapter(saImageItems);
        //添加消息处理
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context,name_m[position],Toast.LENGTH_LONG).show();
                LinearLayout linearLayout=(LinearLayout) gridview.getAdapter().getView(position,view,null);
                linearLayout.setBackgroundResource(R.drawable.gh_maincolor_bg_shape_5);
            }
        });

    }


    /**
     * @Description act正在进行
     */
    public static boolean isRunningAct(Context context) {
        if (isAct(context)) {
            Activity activity = (Activity) context;
            if (!activity.isFinishing()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @Description 是否为activity
     */
    private static boolean isAct(Context context) {
        if (context instanceof Activity) {
            return true;
        }
        return false;
    }

    @Override
    public void show() {
        try {
            if (isRunningAct(context)) {
                super.show();
                Animation a = AnimationUtils.loadAnimation(context, R.anim.dialog_in);
                layout_dailog.startAnimation(a);
                layout_dailog.setVisibility(View.VISIBLE);
                isFinish = false;
                //MessageDialog.list_dialog.add(MessageDialog.DIALOG_SHOW);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressLint("NewApi")
    @Override
    public void dismiss() {
        if (isFinish)
            super.dismiss();
        Animation a = AnimationUtils.loadAnimation(context, R.anim.dialog_out);
        layout_dailog.startAnimation(a);
        a.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation anim) {
            }

            @Override
            public void onAnimationRepeat(Animation anim) {
            }

            @Override
            public void onAnimationEnd(Animation anim) {
                isFinish = true;
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

    public void setOnClickSingleListener(
            onClickSingleListener hOnClickSingleListener) {
        mOnClickSingleListener = hOnClickSingleListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_single:
                if (mOnClickSingleListener != null)
                    mOnClickSingleListener.onClickSingle();
                dismiss();
                break;
        }

    }
}
