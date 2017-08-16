package com.game.helper.view.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.sdk.model.returns.GetMyGameOrder.GameOrder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class OrderDetialDailog extends Dialog implements
        View.OnClickListener {

    @BindView(R.id.game_order_state_tv)
    TextView gameOrderStateTv;
    @BindView(R.id.game_order_ordernumber_tv)
    TextView gameOrderOrdernumberTv;
    @BindView(R.id.game_order_time_tv)
    TextView gameOrderTimeTv;
    @BindView(R.id.game_order_game_name_tv)
    TextView gameOrderGameNameTv;
    @BindView(R.id.game_order_account_tv)
    TextView gameOrderAccountTv;
    @BindView(R.id.game_order_money_tv)
    TextView gameOrderMoneyTv;
    @BindView(R.id.game_order_paymoney_tv)
    TextView gameOrderPaymoneyTv;
    @BindView(R.id.btn_single)
    Button btnSingle;

    public interface onClickSingleListener {
        public void onClickSingle();
    }

    public onClickSingleListener mOnClickSingleListener;
    public LinearLayout layout_dailog;
    private LinearLayout layout_double;
    //private WebView wv_contentT;
    //private Button btn_single;
    //private boolean cancelable = true;
    private Context context;
    private boolean isFinish = false;
    private View msView;
    private GameOrder orderdate;

    public OrderDetialDailog(Context context, GameOrder orderdate) {
        super(context, R.style.Dialog_bocop);//:R.style.Dialog_bocop2
        this.context = context;
        this.orderdate = orderdate;
        init();
    }

    private void init() {
        View contentView = View.inflate(getContext(), R.layout.dailog_orders_detial,
                null);
        ButterKnife.bind(this,contentView);
        setContentView(contentView);
        msView = (View) findViewById(R.id.msView);
        //tv_title = (TextView) findViewById(R.id.tv_title);
        layout_double = (LinearLayout) findViewById(R.id.layout_double);
        layout_dailog = (LinearLayout) findViewById(R.id.layout_dailog);
        //btn_single = (Button) findViewById(R.id.btn_single);
        //btn_single.setOnClickListener(this);
        layout_dailog.setVisibility(View.GONE);
        setCanceledOnTouchOutside(true);
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(orderdate.status) && orderdate.status.equals("0")){

                }else {
                    dismiss();
                }
            }
        });

        if (!TextUtils.isEmpty(orderdate.dealStatus) && orderdate.dealStatus.equals("Y")) {
            //退款
            gameOrderStateTv.setText(" 已退款");
            gameOrderTimeTv.setText("充值时间："+orderdate.field7);
            btnSingle.setText("取消");
        } else {
            if (!TextUtils.isEmpty(orderdate.status) && orderdate.status.equals("2")) {//0  未支付  1  已支付  2 已受理
                gameOrderStateTv.setText(" 已到账");
                gameOrderTimeTv.setText("充值时间："+orderdate.payTimeString);
                btnSingle.setText("取消");
            } else if (!TextUtils.isEmpty(orderdate.status) && orderdate.status.equals("1")) {//0  未支付  1  已支付  2 已受理
                gameOrderStateTv.setText(" 已支付");
                gameOrderTimeTv.setText("充值时间："+orderdate.payTimeString);
                btnSingle.setText("取消");
            } else {
                gameOrderStateTv.setText(" 未支付");
                gameOrderTimeTv.setText("充值时间："+orderdate.createTimeString);
                btnSingle.setText("立即支付");
            }
        }

        gameOrderOrdernumberTv.setText("订单号："+orderdate.orderNo);
        gameOrderGameNameTv.setText("充值游戏："+orderdate.gameName);
        gameOrderAccountTv.setText("充值账号："+orderdate.gameAccount);
        gameOrderMoneyTv.setText("充值金额："+orderdate.money);
        gameOrderPaymoneyTv.setText("实付金额："+orderdate.realPay);

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
        /*int size=MessageDialog.list_dialog.size();
        if(size>0){
			MessageDialog.list_dialog.remove(size-1);
		}*/
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
        //btn_single.setVisibility(View.VISIBLE);
        //btn_single.setText(text);
    }

    /**
     * 设置没收所有按钮
     */
    public void setNoBtn() {
        layout_double.setVisibility(View.GONE);
        msView.setVisibility(View.INVISIBLE);
    }
/*
    @Override
    public void setCancelable(boolean flag) {
        cancelable = flag;
        super.setCancelable(flag);
    }*/

   /* public void setTitle(String text) {
        if (text == null) {
            tv_title.setVisibility(View.GONE);
        } else {
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(text);
        }

    }

    public void setTitle(String text, int mGravity) {
        if (text == null) {
            tv_title.setVisibility(View.GONE);
        } else {
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(text);
            tv_title.setGravity(mGravity);
        }

    }*/

   /* private void init(TextView tv, String CONTENT) {
        tv_title.setClickable(true);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableString s = new SpannableString(CONTENT);
        Matcher m = Pattern.compile(REG).matcher(s.toString());
        Matcher m1 = Pattern.compile(landlinePhoneRegexp).matcher(s.toString());
        while (m.find()) {
            String text = m.group();
            TextClickableSpan span = new TextClickableSpan(text);
            s.setSpan(span, m.start(), m.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        while (m1.find()) {
            String text = m1.group();
            TextClickableSpan span = new TextClickableSpan(text);
            s.setSpan(span, m1.start(), m1.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv.setText(s);
    }*/

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
