package com.game.helper.adapter.community;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.model.mine.PersonaImg;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description 个人主页的个人头像
 * @Path com.game.helper.adapter.community.PersonaImgAdapter.java
 * @Author lbb
 * @Date 2016年8月30日 下午1:23:55
 * @Company
 */
public class PersonaImgAdapter extends BaseAdapter {

    private Context mContext;
    protected LayoutInflater mInflater;
    private ArrayList<PersonaImg> mList = new ArrayList<PersonaImg>();

    public PersonaImgAdapter(Context mContext, ArrayList<PersonaImg> mList) {
        super();
        mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mList = mList;
    }

    public void setSize(ArrayList<PersonaImg> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mList.get(position);
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
            convertView = mInflater.inflate(R.layout.item_listview_community_person_img, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setData(mList.get(position));
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView iv_icon;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setData(PersonaImg mMineCollect) {
            if (mMineCollect != null) {
                Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                        .load("" + mMineCollect.fileAskPath + mMineCollect.icon)
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
