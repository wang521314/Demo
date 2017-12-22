package bo.liu.myrx.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import bo.liu.myrx.R;
import bo.liu.myrx.mode.Subject;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class DraAdapater extends Adapter<DraAdapater.MyHolder> {


    private ArrayList<Subject> datas;
    private Context mContext;

    public DraAdapater(ArrayList<Subject> data, Context context) {
        this.datas = data;
        this.mContext = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder hulder = new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_linear,parent,false));
        return hulder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.title.setText(datas.get(position).getTitle());
        holder.img.setImageResource(datas.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }
   public class MyHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private ImageView img;
       public LinearLayout ll_item,ll_hidden;

       public MyHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            title = (TextView) itemView.findViewById(R.id.tv_title);
            img = (ImageView) itemView.findViewById(R.id.img);
            ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
            ll_hidden = (LinearLayout) itemView.findViewById(R.id.ll_hidden);
        }
    }
}
