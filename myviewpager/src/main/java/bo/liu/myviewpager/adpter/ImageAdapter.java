package bo.liu.myviewpager.adpter;

import android.content.Context;
import android.icu.text.LocaleDisplayNames;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.ArrayList;

import bo.liu.myviewpager.R;
import bo.liu.myviewpager.bean.Folder;
import bo.liu.myviewpager.bean.Image;

/**
 * Created by Administrator on 2017/11/16 0016.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Image> mImages;
    private LayoutInflater mInflater;
    private boolean isSingle;
    //保存选中的图片
    private ArrayList<Image> mSelectImages = new ArrayList<>();
    private OnImageSelectListener mSelectListener;
    private OnItemClickListener mItemClickListener;
   // private int mMaxCount;


    /**
     * @param maxCount 图片的最大选择数量，小于等于0时，不限数量，isSingle为false时才有用。
     * @param isSingle 是否单选
     */
    public ImageAdapter(Context context, boolean isSingle, ArrayList<Image> images) {
        mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        Log.d("ClientActivity", "ImageAdapter: +++++++++");
//        mMaxCount = maxCount;
        this.mImages = images;
        this.isSingle = isSingle;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_images_item, parent, false);
        Log.d("ClientActivity", "onCreateViewHolder: ++++");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.d("ClientActivity", "onBindViewHolder: "+ mImages.get(position).getPath());
        final Image image = mImages.get(position);
        Glide.with(mContext).load(new File(image.getPath()))
                .diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.ivImage);
        setItemSelect(holder, mSelectImages.contains(image));
        holder.ivSelectIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectImages.contains(image)) {
                    //如果图片已经选中，就取消选中
                    unSelectImage(image);
                    setItemSelect(holder, false);
                } else if (isSingle) {
                    //如果是单选，就先清空已经选中的图片，再选中当前图片
                    clearImageSelect();
                    selectImage(image);
                    setItemSelect(holder,true);
                }

            }
        });

    }

    private void clearImageSelect() {
        if (mImages != null && mSelectImages.size() == 1) {
            int index = mImages.indexOf(mSelectImages.get(0));
            if(index != -1){
                mSelectImages.clear();
                notifyItemChanged(index);
            }
        }
    }
    /**
     * 选中图片
     * @param image
     */
    private void selectImage(Image image){
        mSelectImages.add(image);
        if (mSelectListener != null) {
            mSelectListener.OnImageSelect(image, true, mSelectImages.size());
        }
    }

    /**
     * 取消选中图片
     * @param image
     */
    private void unSelectImage(Image image){
        mSelectImages.remove(image);
        if (mSelectListener != null) {
            mSelectListener.OnImageSelect(image, false, mSelectImages.size());
        }
    }

    /**
     * 设置图片选中和未选中的效果
     */
    private void setItemSelect(ViewHolder holder, boolean isSelect) {
        if (isSelect) {
            holder.ivSelectIcon.setImageResource(R.mipmap.ic_pageup_enable);
            holder.ivMasking.setAlpha(0.5f);
        } else {
            holder.ivSelectIcon.setImageResource(R.mipmap.ic_pageup_noenable);
            holder.ivMasking.setAlpha(0.2f);
        }
    }

    public ArrayList<Image> getSelectImages() {
        return mSelectImages;
    }
    public void setOnImageSelectListener(OnImageSelectListener listener) {
        this.mSelectListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        Log.d("ClientActivity", "getItemCount: "+mImages.size());
        return mImages == null ? 0 : mImages.size();
    }
    public interface OnImageSelectListener {
        void OnImageSelect(Image image, boolean isSelect, int selectCount);
    }

    public interface OnItemClickListener{
        void OnItemClick(Image image, int position);
    }
    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        ImageView ivSelectIcon;
        ImageView ivMasking;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_image);
            ivSelectIcon = (ImageView) itemView.findViewById(R.id.iv_select);
            ivMasking = (ImageView) itemView.findViewById(R.id.iv_masking);
        }
    }
}
