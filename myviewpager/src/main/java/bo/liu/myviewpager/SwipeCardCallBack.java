package bo.liu.myviewpager;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;

import bo.liu.myviewpager.adpter.UniversalAdapter;
import bo.liu.myviewpager.bean.SwipeCardBean;

/**
 * Created by Administrator on 2017/11/1 0001.
 */

public class SwipeCardCallBack extends ItemTouchHelper.SimpleCallback {
    ArrayList<SwipeCardBean> mDatas;
    RecyclerView.Adapter adapter;
    public SwipeCardCallBack(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    public SwipeCardCallBack(ArrayList list, RecyclerView.Adapter adp) {
        /*
        * 即我们对哪些方向操作关心。如果我们关心用户向上拖动，可以将
         填充swipeDirs参数为LEFT | RIGHT 。0表示从不关心。
        * */
        super(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.UP |
                        ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN
        );
        this.mDatas =list;
        this.adapter =adp;
    }
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        SwipeCardBean remove = mDatas.remove(viewHolder.getLayoutPosition());
        mDatas.add(0, remove);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        //监听话滑动的距离--控制动画的执行程度
        //灵界点
        double maxDistance = recyclerView.getWidth() * 0.5f;
        double distance = Math.sqrt(dX * dX);
        //动画执行的百分比
        double fraction = distance / maxDistance;
        if (fraction > 1) {
            fraction = 1;
        }
        int itemcount = recyclerView.getChildCount();
        for (int i = 0; i < itemcount; i++) {
            //执行
            View view = recyclerView.getChildAt(i);
            //几个view层叠的效果，错开的效果--便宜动画+缩放动画
            int level = itemcount - i - 1;
            if (level > 0) {
                if (level < CardConfig.MAX_SHOW_COUNT - 1) {
                    view.setTranslationY((float) (1 - CardConfig.TRANS_V_GAP * level + fraction * CardConfig.TRANS_V_GAP));
                    view.setScaleX((float) (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP));
                    view.setTranslationY((float) (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP));
                }
            }


        }
    }


}
