package bo.liu.myrx;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import bo.liu.myrx.mode.EventMode;
import bo.liu.myrx.mode.FamousInfo;
import bo.liu.myrx.mode.FamousInfoModel;
import bo.liu.myrx.mode.FamousInfoReq;
import bo.liu.myrx.mode.HoleBean;
import bo.liu.myrx.mode.Student;
import bo.liu.myrx.myview.NewbieGuide;
import bo.liu.myrx.util.CommonUtil;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private Subscription imageSubscription;
   private Subscription subscription;
    private TextView ma;
    private Button iv;
    private EditText mEdit;
    private FamousInfoModel famousInfoModel;
    private NewbieGuide nb;
    private Button bt;

    @Override
    public int initContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        imageSubscription = Observable.interval(200, TimeUnit.MICROSECONDS).take(100).subscribeOn(Schedulers.io()).
//                observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
//            @Override
//            public void call(Long aLong) {
//                Log.d(TAG, "call: aLong"+aLong);
//            }
//        });

//        subscription = Observable.timer(22, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
//            @Override
//            public void call(Long aLong) {
//                CommonUtil.setToast(MainActivity.this,"jiehsul");
//                Intent in = new Intent(MainActivity.this,ReceiveActivity.class);
//                startActivity(in);
//                Log.d(TAG, "call: jieshu"+"结束");
//            }
//        });
//
//        Observable.timer(2000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Action1<Long>() {
//            @Override
//            public void call(Long aLong) {
//                Log.d(TAG, "call: +++++++++++++++++++++");
//
//
//            }
//        });
      //  final int drawable = R.mipmap.ic_launcher;

//        imageSubscription = (Subscription) Observable.create(new Observable.OnSubscribe<Drawable>() {
//            @Override
//            public void call(Subscriber<? super Drawable> subscriber) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    Drawable drawable1 = getTheme().getDrawable(drawable);
//                    subscriber.onNext(drawable1);
//                    subscriber.onCompleted();
//                }
//            }
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Drawable>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                CommonUtil.setToast(MainActivity.this, e.toString());
//            }
//
//            @Override
//            public void onNext(Drawable drawable) {
//                iv.setImageDrawable(drawable);
//            }
//        });
//        subscription = (Subscription) Observable.just("nihao").subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                Log.d(TAG, "call: s" + s);
//            }
//        });

//        Observable.just("").map(new Func1<String, Object>() {
//            @Override
//            public Object call(String s) {
//                return null;
//            }
//        }).subscribe(new Action1<Object>() {
//            @Override
//            public void call(Object o) {
//
//            }
//        });

//        List<Student.Curse> se = new ArrayList<>();
//        for (int i =0;i<5;i++){
//            Student.Curse s  = new Student.Curse("yuwen");
//            se.add(s);
//        }
//
//        Subscriber<Student> subscriber = new Subscriber<Student>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }

//                 @Override
//            public void onNext(Student student) {
////                List<Student.Curse> myCurse = student.getMyCurse();
////                for (int i=0 ;i<myCurse.size();i++){
////                    Student.Curse course = myCurse.get(i);
////                    Log.d(TAG, course.getCur());
////                }
//            }



      //  };
//        Student []students = new Student[5];
//        students[0] = new Student("小李",se);
//        students[1] = new Student("小王",se);
//        students[2] = new Student("小明",se);
//        students[3] = new Student("小赵",se);
//        students[4] = new Student("小孙",se);
//        Observable.from(students).flatMap(new Func1<Student, Observable<Student.Curse>>() {
//            @Override
//            public Observable<Student.Curse> call(Student student) {
//                return Observable.from(student.getMyCurse());
//            }
//        }).subscribe(new Subscriber<Student.Curse>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(Student.Curse curse) {
//                Log.d(TAG, curse.getCur());
//            }
//        });
        initView();
        initListener();
        famousInfoModel = FamousInfoModel.getInstance(this);
    }

    private void initListener() {
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                famousInfoModel.queryLookUp(initParams()).enqueue(new Callback<FamousInfo>() {
                    @Override
                    public void onResponse(Call<FamousInfo> call, Response<FamousInfo> response) {
                        FamousInfo result = response.body();
                        FamousInfo.content content = result.getContent();
                        Log.d(TAG, "RetrofitWrapper "+content.toString());
                        if(null != content) {
                            String out = content.getOut();
                            String from = content.getFrom();
                            Log.d(TAG, "RetrofitWrapper "+out+from);
                            ma.setText(out+from);
                        }
                    }

                    @Override
                    public void onFailure(Call<FamousInfo> call, Throwable t) {

                    }
                });
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,SideslipActivity.class);
                startActivity(in);
            }
        });
    }

    private void initView() {
        ma = (TextView) findViewById(R.id.txt_content);
        iv = (Button) findViewById(R.id.button_search);
        mEdit = (EditText) findViewById(R.id.edit_keyword);
        bt = (Button) findViewById(R.id.rl_bt);

    }

    private FamousInfoReq initParams() {
        FamousInfoReq mFamousInfoReq=null;
        mFamousInfoReq= new FamousInfoReq();

        return mFamousInfoReq;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (imageSubscription != null) {
            imageSubscription.unsubscribe();
        }
        if (subscription != null) {
            subscription.unsubscribe();
        }


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int x = (int) iv.getX();
        int y = (int) iv.getY();
//        if(NewbieGuideManager.isNeverShowed(this, NewbieGuideManager.TYPE_COLLECT)) {
//            new NewbieGuideManager(this, NewbieGuideManager.TYPE_COLLECT).addView(iv, HoleBean.TYPE_CIRCLE).addView(ma,
//                    HoleBean.TYPE_RECTANGLE).show();
//        }
        Log.d(TAG, "onWindowFocusChanged: x"+x+"y"+y);

        NewbieGuide nb = new NewbieGuide(this);
        if (isNeverShowed(this,1)){
            nb.setEveryWhereTouchable(false).addIndicateImg(R.mipmap.left_arrow,x+(x/4),y+(y/3)).addHighLightView(iv,HoleBean.TYPE_CIRCLE).addMsgAndKnowTv("haha",CommonUtil.dpToPx(this, 450))
                    .addHighLightView(ma,HoleBean.TYPE_RECTANGLE).show();
        }

    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventMainThread(EventMode eventMode) {
        ma.setText(eventMode.getText());
    }

    /**
     * 判断新手引导也是否已经显示了
     */
    public static boolean isNeverShowed(Activity activity, int type) {
        return activity.getSharedPreferences(TAG, Activity.MODE_PRIVATE).getBoolean(TAG + type, true);
    }
}
