package bo.liu.myrx.myview;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

import bo.liu.myrx.R;
import bo.liu.myrx.adapter.DraAdapater;


/**
 * Created by Administrator on 2017/12/22 0022.
 */

public class SwipeRecyclerView extends RecyclerView {

    private Context mContext;
    private int mTouchSlop;
    private int maxLeft;
    private Scroller mScroller;
    /**
     * 当前选中的item索引（这个很重要）
     */
    private int curSelectPosition;

    /**
     * 是否是第一次touch
     */
    private boolean isFirst = true;
    private LinearLayout mCurItemLayout, mLastItemLayout;
    /**
     * 记录连续移动的长度
     */

    private int mMoveWidth=0;
    /**
     * 隐藏部分长度
     */
    private int mHiddenWidth;
    private LinearLayout mLlHidden;//隐藏的控件
    private LinearLayout mItemDelete;
    private int xDown;
    private int yDown;
    private int xMove;
    private int yMove;
    private static final String TAG = "SwipeRecyclerView";

    /**
     * 删除的监听事件
     */
    private OnRightClickListener mRightListener;

    public void setRightClickListener(OnRightClickListener listener){
        this.mRightListener = listener;
    }

    public SwipeRecyclerView(Context context) {
        this(context,null);
    }

    public SwipeRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwipeRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext =context;
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();//获得触发事件移动触的最短距离，如果小于这个距离就不触发移动控件
        //得到密度比
        maxLeft = ((int) (180 * context.getResources().getDisplayMetrics().density + 0.5f));
        //获取滑动事件，第二个参数是一个差值器，如果用一个参数的构造会默认插值器
        mScroller = new Scroller(context,new LinearInterpolator(context, null));
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                xDown = x;
                yDown = y;
                //计算选中哪个Item
                int firstPosition = ((LinearLayoutManager)getLayoutManager()).findFirstVisibleItemPosition();
                Rect itemRect = new Rect();
                int count = getChildCount();//获得子控件的个数
                for (int i=0; i<count; i++) {
                    final View child = getChildAt(i);
                    if (child.getVisibility() == View.VISIBLE) {
                        child.getHitRect(itemRect);
                        if (itemRect.contains(x, y)) {
                            curSelectPosition = firstPosition + i;
                            break;
                        }
                    }
                }

                if (isFirst){//第一次时，不用重置上一次的Item
                    isFirst = false;
                }else {
                    //屏幕再次接收到点击时，恢复上一次Item的状态

                    if (mLastItemLayout != null && mMoveWidth > 0) {
                        //将Item右移，恢复原位
                        scrollRight(mLastItemLayout, (0 - mMoveWidth));
                        //清空变量
                        mHiddenWidth = 0;
                        mMoveWidth = 0;
                    }

                }
                //取到当前选中的Item，赋给mCurItemLayout，以便对其进行左移
                View item = getChildAt(curSelectPosition - firstPosition);
                if (item != null) {
                    //获取当前选中的Item
                    DraAdapater.MyHolder viewHolder = (DraAdapater.MyHolder) getChildViewHolder(item);
                    mCurItemLayout = viewHolder.ll_item;
                    //找到具体元素（这与实际业务相关了~~）
                    mLlHidden = (LinearLayout) mCurItemLayout.findViewById(R.id.ll_hidden);
                    mItemDelete = (LinearLayout) mCurItemLayout.findViewById(R.id.ll_hidden);
                    mItemDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mRightListener != null){
                                //删除
                                mRightListener.onRightClick(curSelectPosition, "");
                            }
                        }
                    });

                    //这里将删除按钮的宽度设为可以移动的距离
                    mHiddenWidth = mLlHidden.getWidth();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = x;
                yMove = y;
                int dx = xMove - xDown;//为负时：手指向左滑动；为正时：手指向右滑动。这与Android的屏幕坐标定义有关
                int dy = yMove - yDown;//

                //左滑
                if (dx < 0 && Math.abs(dx) > mTouchSlop && Math.abs(dy) < mTouchSlop){
                    int newScrollX = Math.abs(dx);
                    if (mMoveWidth >= mHiddenWidth){//超过了，不能再移动了
                        newScrollX = 0;
                    } else if (mMoveWidth + newScrollX > mHiddenWidth){//这次要超了，
                        newScrollX = mHiddenWidth - mMoveWidth;
                    }
                    //左滑，每次滑动手指移动的距离
                    scrollLeft(mCurItemLayout, newScrollX);
                    //对移动的距离叠加
                    mMoveWidth = mMoveWidth + newScrollX;
                }else if (dx > 0){//右滑
                    //执行右滑，这里没有做跟随，瞬间恢复
                    scrollRight(mCurItemLayout, 0 - mMoveWidth);
                    mMoveWidth = 0;
                }
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = mCurItemLayout.getScrollX();

                if (mHiddenWidth > mMoveWidth) {
                    int toX = (mHiddenWidth - mMoveWidth);
                    if (scrollX > mHiddenWidth / 2) {//超过一半长度时松开，则自动滑到左侧
                        scrollLeft(mCurItemLayout, toX);
                        mMoveWidth = mHiddenWidth;
                    } else {//不到一半时松开，则恢复原状
                        scrollRight(mCurItemLayout, 0 - mMoveWidth);
                        mMoveWidth = 0;
                    }
                }
                mLastItemLayout = mCurItemLayout;
                break;


        }
        return super.onTouchEvent(e);
    }
    /**
     * 通过invalidate操纵,此方法通过draw方法调用
     */
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {

            Log.e(TAG, "computeScroll getCurrX ->" + mScroller.getCurrX());

            mCurItemLayout.scrollBy(mScroller.getCurrX(), 0);
            invalidate();
        }
    }

    /**
     * 向左滑动
     */
    private void scrollLeft(View item, int scorllX){
        Log.e(TAG, " scroll left -> " + scorllX);
        item.scrollBy(scorllX, 0);
    }

    /**
     * 向右滑动
     */
    private void scrollRight(View item, int scorllX){
        Log.e(TAG, " scroll right -> " + scorllX);
        item.scrollBy(scorllX, 0);
    }

    public interface OnRightClickListener{
        void onRightClick(int position, String id);
    }
}
