package bo.liu.myviewpager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import bo.liu.myviewpager.adpter.UniversalAdapter;
import bo.liu.myviewpager.bean.SwipeCardBean;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mActivity_review;
    private UniversalAdapter mAdatper;
    private ArrayList<SwipeCardBean> mList;
    private Button bt,bt1,bu_bt,tj_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        setData();
    }

    private void initData() {
        /**这里的图片就上网百度了8张。本人比较懒就用本地图片代替。
         当然你要有现成的接口也可以网络加载解析。*/
        int[] intimage = {R.mipmap.ic_en_game,R.mipmap.ic_game,R.mipmap.ic_launcher1,R.mipmap.ic_launcher};
        for (int i = 0; i < 4; i++) {
            SwipeCardBean swpe = new SwipeCardBean();
            swpe.resoutimage = intimage[i];
            swpe.title = "美丽" + i;
            mList.add(swpe);
        }
    }

    private void initView() {
        mList = new ArrayList<>();
        mActivity_review = (RecyclerView) findViewById(R.id.activity_review);
        bt = (Button) findViewById(R.id.bt);
        bt1 = (Button) findViewById(R.id.ma_bt);
        bu_bt = (Button) findViewById(R.id.bu_bt);
        tj_bt = (Button) findViewById(R.id.tj_bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,ReMengActivity.class);
                startActivity(in);
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,ZiXingActivity.class);
                startActivity(in);
            }
        });
        bu_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,BuleActivity.class);
                startActivity(in);
            }
        });
        tj_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,TongJiActivity.class);
                startActivity(in);
            }
        });
    }
    private void setData() {
        SwipeCardLayoutManager swmanamger = new SwipeCardLayoutManager(this);
       // mActivity_review.setLayoutManager(new LinearLayoutManager(this,1,false));
        mActivity_review.setLayoutManager(swmanamger);



        mAdatper = new UniversalAdapter(mList, this);
        SwipeCardCallBack sw = new SwipeCardCallBack(mList,mAdatper);
        ItemTouchHelper it = new ItemTouchHelper(sw);
        it.attachToRecyclerView(mActivity_review);
        mActivity_review.setAdapter(mAdatper);
    }
}
