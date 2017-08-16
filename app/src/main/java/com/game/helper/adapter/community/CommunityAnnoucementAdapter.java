package com.game.helper.adapter.community;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.game.helper.R;
import com.game.helper.sdk.model.returns.GetNoticeList.GetNoticeListData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommunityAnnoucementAdapter extends BaseAdapter {

    private Context mContext;
    protected LayoutInflater mInflater;
    private List<GetNoticeListData> data = new ArrayList<GetNoticeListData>();

    public CommunityAnnoucementAdapter(Context mContext, List<GetNoticeListData> data) {
        super();
        mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.data = data;
    }

    public List<GetNoticeListData> getData() {
        return data;
    }

    public void setData(List<GetNoticeListData> data) {
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
            convertView = mInflater.inflate(R.layout.item_listview_community_announcement_community, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setData(data.get(position));
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_msg)
        TextView tv_msg;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setData(GetNoticeListData mGetNoticeListData) {
            if (mGetNoticeListData != null) {
                tv_title.setText(mGetNoticeListData.noticeName);
                tv_time.setText(mGetNoticeListData.createTimeString);
                tv_msg.setText(mGetNoticeListData.content);
            }
        }
    }
}
