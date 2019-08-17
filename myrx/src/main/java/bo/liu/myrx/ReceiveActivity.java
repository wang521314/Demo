package bo.liu.myrx;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import bo.liu.myrx.adapter.DraAdapater;
import bo.liu.myrx.mode.EventMode;
import bo.liu.myrx.mode.Subject;
import bo.liu.myrx.myview.SwipeRecyclerView;
import bo.liu.myrx.util.CommonUtil;

import bo.liu.myrx.util.OnRecyclerItemClickListener;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class ReceiveActivity extends BaseActivity {


    private Button tv;
    private static final String TAG = "ReceiveActivity";
    private SwipeRecyclerView rv;
    private String[] titles = {"美食", "电影", "酒店住宿", "休闲娱乐", "外卖", "自助餐", "KTV", "机票/火车票", "周边游", "美甲美睫",
            "火锅", "生日蛋糕", "甜品饮品", "水上乐园", "汽车服务", "美发", "丽人", "景点", "足疗按摩", "运动健身", "健身", "超市", "买菜",
            "今日新单", "小吃快餐", "面膜", "洗浴/汗蒸", "母婴亲子", "生活服务", "婚纱摄影", "学习培训", "家装", "结婚", "全部分配"};
    private ArrayList<Subject> datas = new ArrayList<>();
    private ItemTouchHelper mItemTouchHelper;
    private DraAdapater da;

    private int mScreenDensity;
    private MediaProjectionManager mMediaProjectionManager;

    @Override
    public int initContentView() {
        return R.layout.activity_receive;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    private void initData() {
        for (int i = 0; i < titles.length; i++) {
            //动态获取资源ID，第一个参数是资源名，第二个参数是资源类型例如drawable，string等，第三个参数包名
            int imageId = getResources().getIdentifier("ic_category_" + i, "mipmap", getPackageName());
            datas.add(CommonUtil.<Subject>getSubject(titles[0],imageId));
        }
        da = new DraAdapater(datas, this);
        rv.setAdapter(da);

    }

    private void initListener() {
        tv.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
//                EventMode eventMode = new EventMode();
//                eventMode.setText("xisix;");
//                EventBus.getDefault().post(eventMode);
                Intent intent = new Intent(ReceiveActivity.this, ScreenShotActivity.class);
                startActivity(intent);

            }
        });
//
      rv.addOnItemTouchListener(new OnRecyclerItemClickListener(rv) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {

                CommonUtil.setToast(ReceiveActivity.this, datas.get(vh.getLayoutPosition()).getTitle());
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                //判断被拖拽的是否是前两个，如果不是则执行拖拽
                if (vh.getLayoutPosition() != 0 && vh.getLayoutPosition() != 1) {
                    mItemTouchHelper.startDrag(vh);
                }
            }
        });
        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            /**
             * 是否处理滑动事件 以及拖拽和滑动的方向 如果是列表类型的RecyclerView的只存在UP和DOWN，如果是网格类RecyclerView则还应该多有LEFT和RIGHT
             * @param recyclerView
             * @param viewHolder
             * @return
             */
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                } else {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    final int swipeFlags = 0;
//                    final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //得到当拖拽的viewHolder的Position
                int fromPosition = viewHolder.getAdapterPosition();
                //拿到当前拖拽到的item的viewHolder
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(datas, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(datas, i, i - 1);
                    }
                }

                da.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            /**
             * 重写拖拽可用
             * @return
             */
            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }

            /**
             * 长按选中Item的时候开始调用
             *
             * @param viewHolder
             * @param actionState
             */
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                    Log.d(TAG, "onSelectedChanged: " + viewHolder.itemView.getWidth());
                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            /**
             * 手指松开的时候还原
             * @param recyclerView
             * @param viewHolder
             */
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(0);
            }
        });
        mItemTouchHelper.attachToRecyclerView(rv);
        rv.setRightClickListener(new SwipeRecyclerView.OnRightClickListener() {
            @Override
            public void onRightClick(int position, String id) {
                datas.remove(position);
                da.notifyDataSetChanged();
                CommonUtil.setToast(ReceiveActivity.this, " position = " + position);
            }
        });
    }

    private void initView() {
        tv = (Button) findViewById(R.id.re_tv);
        rv = (SwipeRecyclerView) findViewById(R.id.re_rv);
        rv.setLayoutManager(new LinearLayoutManager(this));



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventMainThread(EventMode eventMode) {
        Log.d(TAG, "onEventMainThread: " + eventMode.getText());
    }

    /**
     * 对View截图
     */
    public static Bitmap getViewScreenshot(final View view) {
        Bitmap bmp = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        view.draw(canvas);
        return bmp;
    }

}
