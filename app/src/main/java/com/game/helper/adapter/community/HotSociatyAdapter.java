package com.game.helper.adapter.community;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.JoinOrExitGuildTask;
import com.game.helper.sdk.model.returns.GetGuildList.GetGuildListData;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.net.base.JsonBuild;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.dialog.MyAlertDailog;
import com.game.helper.view.dialog.MyAlertDailog.onClickLeftListener;
import com.game.helper.view.dialog.MyAlertDailog.onClickRightListener;
import com.game.helper.view.widget.XCRoundImageViewByXfermode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description
 * @Path com.game.helper.adapter.community.HotCommunityAdapter.java
 * @Author lbb
 * @Date 2016年8月25日 上午10:23:53
 * @Company
 */
public class HotSociatyAdapter extends BaseAdapter {

    private Context mContext;
    protected LayoutInflater mInflater;
    protected List<GetGuildListData> data = new ArrayList<GetGuildListData>();
    int type;

    public HotSociatyAdapter(Context mContext, List<GetGuildListData> data, int type) {
        super();
        mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.data = data;
        this.type = type;
    }

    public List<GetGuildListData> getData() {
        return data;
    }

    public void setData(List<GetGuildListData> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();//
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
            convertView = mInflater.inflate(R.layout.item_listview_community_hot_community, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setData(data.get(position));
        if (type == 1) {
            holder.tv_add.setVisibility(View.INVISIBLE);
        } else {
            holder.tv_add.setVisibility(View.VISIBLE);
            holder.tv_add.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    final MyAlertDailog dialog = new MyAlertDailog(
                            mContext);
                    Resources res = mContext.getResources();
                    dialog.setTitle("提示", Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    dialog.setContent1("确定要加入该公会吗？每个用户只能加入一个公会哦~"
                            , Gravity.CENTER_VERTICAL | Gravity.LEFT);
                    dialog.setLeftButtonText("取消");
                    dialog.setRightButtonText("确定");
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
                            LoginData user = DBManager.getInstance(mContext).getUserMessage();
                            new JoinOrExitGuildTask(mContext, user.userId, data.get(position).guildId, "0", new Back() {

                                @Override
                                public void success(Object object, String msg) {
                                    ToastUtil.showToast(mContext, "已加入");
                                    //刷新个人信息（含我的公会字段）
                                    LoginData user = DBManager.getInstance(mContext).getUserMessage();
                                    user.jsonData = null;
                                    user.guildId = data.get(position).guildId;
                                    user.jsonData = new JsonBuild().setModel(user).getJsonString1();
                                    DBManager.getInstance(mContext).insert(user);
                                }

                                @Override
                                public void fail(String status, String msg, Object object) {
                                    ToastUtil.showToast(mContext, msg);

                                }
                            }).start();
                        }
                    });
                }
            });

        }
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.iv_item)
        XCRoundImageViewByXfermode iv_item;
        @BindView(R.id.tv_item)
        TextView tv_item;
        @BindView(R.id.tv_msg)
        TextView tv_msg;
        @BindView(R.id.tv_add)
        TextView tv_add;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setData(GetGuildListData mGetGuildListData) {
            if (mGetGuildListData != null) {
                iv_item.setType(XCRoundImageViewByXfermode.TYPE_ROUND);
                iv_item.setRoundBorderRadius(23);
                Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                        .load("" + mGetGuildListData.fileAskPath + mGetGuildListData.icon)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        //.centerCrop()// 长的一边撑满
                        //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                        .error(R.drawable.picture_defeated)//加载失败时显示的图片
                        //.crossFade()
                        .into(iv_item);
                tv_item.setText(mGetGuildListData.name);
                tv_msg.setText("公会ID：" + mGetGuildListData.guildId);

            }
        }
    }
}
