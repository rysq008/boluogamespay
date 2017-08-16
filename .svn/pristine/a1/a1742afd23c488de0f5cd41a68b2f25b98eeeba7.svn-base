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
import com.game.helper.sdk.model.returns.GetDynamic_1Info.ImageData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DyImgAdapter extends BaseAdapter {

    private Context mContext;
    protected LayoutInflater mInflater;
    private List<ImageData> data = new ArrayList<ImageData>();

    public DyImgAdapter(Context mContext, List<ImageData> data) {
        super();
        mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.data = data;
    }

    public List<ImageData> getData() {
        return data;
    }

    public void setData(List<ImageData> data) {
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
            convertView = mInflater.inflate(R.layout.item_gridview_community_dy_imglist, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setData(data.get(position));
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.iv_img2)
        ImageView iv_img2;



        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setData(ImageData mImageData) {
            if (mImageData != null) {
                Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                        .load("" + mImageData.fileAskPath + mImageData.imageAddress)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        //.centerCrop()// 长的一边撑满
                        //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                        .error(R.drawable.picture_defeated)//加载失败时显示的图片
                        //.crossFade()
                        .into(iv_img2);
            }
        }
    }
}
