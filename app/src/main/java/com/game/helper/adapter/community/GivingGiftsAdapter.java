package com.game.helper.adapter.community;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.home.RechargeActivity;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.SaveGiveTask;
import com.game.helper.sdk.model.returns.GetGiftList.GetGiftListData;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.dialog.MyAlertDailog;
import com.game.helper.view.dialog.MyAlertDailog.onClickLeftListener;
import com.game.helper.view.dialog.MyAlertDailog.onClickRightListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description
 * @Path com.game.helper.adapter.community.GivingGiftsAdapter.java
 * @Author lbb
 * @Date 2016年8月26日 下午2:12:22
 * @Company
 */
public class GivingGiftsAdapter extends BaseAdapter {
    private Context mContext;
    protected LayoutInflater mInflater;
    private List<GetGiftListData> data = new ArrayList<GetGiftListData>();
    private String operId;

    public GivingGiftsAdapter(Context mContext, String operId, List<GetGiftListData> data) {
        super();
        mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.data = data;
        this.operId = operId;
    }

    public List<GetGiftListData> getData() {
        return data;
    }

    public void setData(List<GetGiftListData> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_listview_community_giving_gifts, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.setData(data.get(position));
        holder.tv_giving.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                final MyAlertDailog dialog1 = new MyAlertDailog(mContext);
                dialog1.setTitle("提示", Gravity.CENTER_VERTICAL | Gravity.LEFT);
                dialog1.setContent1("确定赠送礼物吗？"
                        , Gravity.CENTER_VERTICAL | Gravity.LEFT);
                dialog1.setLeftButtonText("取消");
                dialog1.setRightButtonText("确定");
                if (dialog1 != null && !dialog1.isShowing()) {
                    dialog1.show();
                }
                dialog1.setOnClickLeftListener(new onClickLeftListener() {
                    @Override
                    public void onClickLeft() {
                        dialog1.dismiss();

                    }
                });
                dialog1.setOnClickRightListener(new onClickRightListener() {

                    @Override
                    public void onClickRight() {
                        dialog1.dismiss();

                        LoginData user = DBManager.getInstance(mContext).getUserMessage();
                        new SaveGiveTask(mContext, operId, data.get(position).giftId, user.userId, "1", new Back() {

                            @Override
                            public void success(Object object, String msg) {
                                ToastUtil.showToast(mContext, "已赠送");

                            }

                            @Override
                            public void fail(String status, String msg, Object object) {
                                //判断status是哪个值
                                if (TextUtils.isEmpty(status)) {
                                    ToastUtil.showToast(mContext, msg);
                                } else {
                                    if (status.equals("confirm")) {
                                        final MyAlertDailog dialog = new MyAlertDailog(mContext);
                                        Resources res = mContext.getResources();
                                        dialog.setTitle("余额不足", Gravity.CENTER_VERTICAL | Gravity.LEFT);
                                        dialog.setContent1("" + msg
                                                , Gravity.CENTER_VERTICAL | Gravity.LEFT);
                                        dialog.setLeftButtonText("取消");
                                        dialog.setRightButtonText("充值");
                                        if (dialog != null && !dialog.isShowing()) {
                                            dialog.show();
                                        }
                                        dialog.setOnClickLeftListener(new onClickLeftListener() {
                                            @Override
                                            public void onClickLeft() {
                                                dialog.dismiss();
                                            }
                                        });
                                        dialog.setOnClickRightListener(new onClickRightListener() {

                                            @Override
                                            public void onClickRight() {
                                                dialog.dismiss();
                                                ((BaseActivity) mContext).startActivity(RechargeActivity.class);
                                            }
                                        });

                                    } else {
                                        ToastUtil.showToast(mContext, msg);
                                    }
                                }

                            }
                        }).start();

                    }
                });
            }
        });
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView iv_icon;
        @BindView(R.id.tv_money)
        TextView tv_money;
        @BindView(R.id.tv_giving)
        TextView tv_giving;
        @BindView(R.id.mView_last1)
        View mView_last1;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setData(GetGiftListData mGetGiftListData) {
            if (mGetGiftListData != null) {
                tv_money.setText(mGetGiftListData.diamond);
                Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                        .load("" + mGetGiftListData.fileAskPath + mGetGiftListData.img)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        //.centerCrop()// 长的一边撑满
                        //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                        .error(R.drawable.preview_card_pic_loading)//加载失败时显示的图片
                        //.crossFade()
                        .into(iv_icon);
            }
        }
    }
}
