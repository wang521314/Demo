package bo.liu.myrx.myview;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/12/13 0013.
 * 博客地址http://blog.csdn.net/u012551350/article/details/51601985
 */

public class VDHLayout extends ViewGroup {
    private static final int MIN_DRAWER_MARGIN = 80; // dp
    /**
     * Minimum velocity that will be detected as a fling
     */
    private static final int MIN_FLING_VELOCITY = 400; // dips per second
    private int mMinDrawerMargin;
    private ViewDragHelper mViewDragHelper;
    private View mLeftMenuView;
    private View mContentView;
    /**
     * drawer显示出来的占自身的百分比
     */
    private float mLeftMenuOnScreen;


    public VDHLayout(Context context) {
        this(context,null);
    }

    public VDHLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VDHLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        float density = getResources().getDisplayMetrics().density;//获得密度比
        float minVel = MIN_FLING_VELOCITY * density;  //1200
        mMinDrawerMargin = (int) (MIN_DRAWER_MARGIN*density+0.5f);
        //该方法第一个参数是当前的viewgroup，参数2是触摸的敏感度参数，主要用于设置touchSlop helper.mTouchSlop = (int) (helper.mTouchSlop * (1 / sensitivity));，
        // 可见sensitivity值越大，touchSlop值就越小。 参数3监听的回调
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {

                return  child == mLeftMenuView;//捕获view
            }

            @Override//水平移动
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                int newLeft = Math.max(-child.getWidth(), Math.min(left, 0));
                //始终都是取left的值，初始值为-child.getWidth()，当向右拖动的时候left值增大，当left大于0的时候取0
                return newLeft;
            }

            @Override//竖直移动
            public int clampViewPositionVertical(View child, int top, int dy) {
                return super.clampViewPositionVertical(child, top, dy);
            }

            @Override//手指松开时的回调
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                int childWidth = releasedChild.getWidth();
                //0~1f
                float offset = (childWidth + releasedChild.getLeft()) * 1.0f / childWidth;

                mViewDragHelper.settleCapturedViewAt(xvel > 0 || xvel == 0 && offset > 0.5f ? 0 : -childWidth,
                        releasedChild.getTop());
                //由于offset 取值为0~1，所以settleCapturedViewAt初始值为 -childWidth，滑动小于0.5取值也为-childWidth，
                //大于0.5取值为0
                invalidate();

            }
            //在边界拖动时回调
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
               mViewDragHelper.captureChildView(mLeftMenuView,pointerId);
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                int childWidth = changedView.getWidth();
                float offset = (float) (childWidth + left) / childWidth;
                mLeftMenuOnScreen = offset;
                changedView.setVisibility(offset == 0 ? View.INVISIBLE : View.VISIBLE);
                //offset 为0 的时候隐藏 ， 不为0显示
                invalidate();
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                //始终取值为child.getWidth()
                return mLeftMenuView == child ? child.getWidth() : 0;
            }
        });
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        mViewDragHelper.setMinVelocity(minVel);

    }

    @Override
    public void computeScroll() {
     if (mViewDragHelper.continueSettling(true)){
         invalidate();
     }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);

        View leftMenuView = getChildAt(1);
        MarginLayoutParams lp = (MarginLayoutParams)
                leftMenuView.getLayoutParams();

        final int drawerWidthSpec = getChildMeasureSpec(widthMeasureSpec,
                mMinDrawerMargin + lp.leftMargin + lp.rightMargin,
                lp.width);
        final int drawerHeightSpec = getChildMeasureSpec(heightMeasureSpec,
                lp.topMargin + lp.bottomMargin,
                lp.height);

        //确定侧滑栏尺寸
        leftMenuView.measure(drawerWidthSpec, drawerHeightSpec);

        View contentView = getChildAt(0);
        lp = (MarginLayoutParams) contentView.getLayoutParams();
        final int contentWidthSpec = MeasureSpec.makeMeasureSpec(
                widthSize - lp.leftMargin - lp.rightMargin, MeasureSpec.EXACTLY);
        final int contentHeightSpec = MeasureSpec.makeMeasureSpec(
                heightSize - lp.topMargin - lp.bottomMargin, MeasureSpec.EXACTLY);
        //确定主界面尺寸
        contentView.measure(contentWidthSpec, contentHeightSpec);
        mLeftMenuView = leftMenuView;
        mContentView = contentView;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View menuView = mLeftMenuView;
        View contentView = mContentView;
        MarginLayoutParams lp = (MarginLayoutParams)contentView.getLayoutParams();
        contentView.layout(lp.leftMargin, lp.topMargin,
                lp.leftMargin + contentView.getMeasuredWidth(),
                lp.topMargin + contentView.getMeasuredHeight());

        lp = (MarginLayoutParams) menuView.getLayoutParams();

        final int menuWidth = menuView.getMeasuredWidth();
        int childLeft = -menuWidth + (int) (menuWidth * mLeftMenuOnScreen);
        //确定侧滑栏尺寸
        menuView.layout(childLeft, lp.topMargin, childLeft + menuWidth,
                lp.topMargin + menuView.getMeasuredHeight());
    }
    //关闭
    public void closeDrawer() {
        View menuView = mLeftMenuView;
        mLeftMenuOnScreen = 0.f;
        mViewDragHelper.smoothSlideViewTo(menuView, -menuView.getWidth(), menuView.getTop());
    }
    //打开
    public void openDrawer() {
        View menuView = mLeftMenuView;
        mLeftMenuOnScreen = 1.0f;
        mViewDragHelper.smoothSlideViewTo(menuView, 0, menuView.getTop());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {//该方法是在所有View中所有的子控件均被映射成xml后触发，在activity的setcontentview后执行
        super.onFinishInflate();
        mContentView = getChildAt(0);
        mLeftMenuView = getChildAt(1);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {//调用addView的方法时，不指定LayoutParams，是通过generateDefaultLayoutParams()获取默认的LayoutParams属性的。
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }
    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
