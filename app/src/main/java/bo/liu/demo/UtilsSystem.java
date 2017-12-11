package bo.liu.demo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 一些工具类
 * 使用{@link #getInstance()} 调用
 * Created by dlh on 2016/11/16 10:36.
 */

public class UtilsSystem {

    private static UtilsSystem instance = null;
    /**
     * 是否启动Log打印
     * 测试时启动
     * 上线时关闭
     */
    public boolean startOrStopLog = true;

    /**
     * 私有的构造方法
     */
    private UtilsSystem() {
    }

    /**
     * UtilsSystem提供公共静态方法调用
     *
     * @return UtilsSystem的实例
     */
    public static UtilsSystem getInstance() {
        if (instance == null) {
            instance = new UtilsSystem();
        }
        return instance;
    }

    /**
     * 分段打印出较长log文本
     *
     * @param tag 提示标签
     * @param log 原log文本
     */
    public void showLogCompletion(String tag, String log) {
        if (log.length() > 4000) {
            String show = log.substring(0, 4000);
            //System.out.println(show);
            Log.i(tag, show + "");
            if ((log.length() - 4000) > 4000) {//剩下的文本还是大于规定长度
                String partLog = log.substring(4000, log.length());
                showLogCompletion(tag, partLog);
            } else {
                String surplusLog = log.substring(4000, log.length());
                //System.out.println(surplusLog);
                Log.i(tag, surplusLog + "");
            }
        } else {
            //System.out.println(log);
            Log.i(tag, log + "");
        }
    }

    public void hideSofeInput(Activity activity) {

    }

//    /**
//     * 显示图片的toast
//     *
//     * @param context  上下文
//     * @param msg      提示信息
//     * @param toastPic 提示图片
//     */
//    public void showPicToast(Context context, String msg, int toastPic) {
//        Log.e("toast", "context=" + context);
//        Toast toast = new Toast(context);
//        View view = LayoutInflater.from(context).inflate(R.layout.view_toast, null);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        ImageView iv_toast_pic = (ImageView) view.findViewById(R.id.iv_toast_pic);
//        if (toastPic != 0) {//0取默认图片
//            iv_toast_pic.setImageResource(toastPic);
//        }
//        TextView tv_toast_prompt = (TextView) view.findViewById(R.id.tv_toast_prompt);
//        tv_toast_prompt.setText("" + msg);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.setView(view);
//        toast.show();
//    }
//
//    /**
//     * 显示居中的toast
//     *
//     * @param context 上下文
//     * @param msg     提示信息
//     */
//    public void showCenterToast(Context context, String msg) {
//        Log.e("toast", "context=" + context);
//        Toast toast = new Toast(context);
//        View view = LayoutInflater.from(context).inflate(R.layout.view_toastnopic, null);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        TextView tv_toast_prompt = (TextView) view.findViewById(R.id.tv_toast_prompt);
//        tv_toast_prompt.setText("" + msg);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.setView(view);
//        toast.show();
//    }

    /**
     * 获取要显示的菜单序号
     *
     * @param classArray
     * @param menu
     * @return
     */
    public List<Integer> getMenuFunctionAccreditIndex(TypedArray classArray, String menu) {
        List<Integer> menus = new ArrayList();
        if (classArray != null) {
            int len = classArray.length();
            for (int i = 0; i < len; i++) {
                if (menu.indexOf(classArray.getString(i)) != -1) {
                    menus.add(i);
                }
            }
        }
        return menus;
    }

    /**
     * 获取要显示的菜单类名称
     *
     * @param classArray
     * @param menu
     * @return
     */
    public List<String> getMenuFunctionAccreditClass(TypedArray classArray, String menu) {
        List<String> classNames = new ArrayList();
        if (classArray != null) {
            int len = classArray.length();
            for (int i = 0; i < len; i++) {
                if (menu.indexOf(classArray.getString(i)) != -1) {
                    classNames.add(classArray.getString(i));
                }
            }
        }
        return classNames;
    }

//    /**
//     * 获取拥有权限的模块列表
//     *
//     * @param context
//     * @param allFunction 本部分所有模块
//     * @return
//     */
//    public ArrayList<GvAppManageEntity> getMenuFunctionAccreditClass(Context context, ArrayList<GvAppManageEntity> allFunction) {
//        ArrayList<GvAppManageEntity> modules = new ArrayList<>();
//        String[] functions = UtilsSharePre.getInstance().getPreferenceString(context, "MenuFunction", "ERROR").split(",");
//        List<String> menufunctions = Arrays.asList(functions);
//        for (GvAppManageEntity entity : allFunction) {
//            if (menufunctions.contains(entity.getAppClass())) {
//                modules.add(entity);
//            }
//        }
//        return modules;
//    }

    /**
     * 显示提示信息
     *
     * @param context
     * @param message
     */
    public void showShortToast(Context context, String message) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

//    public void showShortAlertDialog(Context context, String str) {
//        new AppleStyleDialog(context, str) {
//            @Override
//            public void doConfirm() {
//                this.dismiss();
//            }
//        }.show();
//    }
//
//    public void showShortAlertDialog(Context context, String str, int gravity) {
//        new AppleStyleDialog(context, str, gravity) {
//            @Override
//            public void doConfirm() {
//                this.dismiss();
//            }
//        }.show();
//    }

    /**
     * 显示提示信息
     *
     * @param context
     * @param message
     */
    public void showLongToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 调用百度地图
     *
     * @param context
     * @param latitude   纬度
     * @param longitude  经度
     * @param endName    名称
     * @param endAddress 地址
     */
    public void startMap(Context context, String latitude, String longitude, String endName, String endAddress) {
        Intent intent = null;
        Log.e("info", "startMap..." + latitude + "..." + longitude);

        if (TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude)) {
            //直接跳转地图，显示当前位置
            try {
                intent = Intent.parseUri("intent://map#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end",
                        Intent.URI_INTENT_SCHEME);
            } catch (URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            //显示终端位置&src=yourCompanyName|yourAppName116.307629,40.058359 &zoom=14
            //"intent://map/marker?location=40.047669,116.313082&title=我的位置&content=百度奎科大厦&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end"
            try {
                intent = Intent.parseUri("intent://map/marker?location=" + latitude + "," +
                                longitude + "&title=" + endName + "&content=" + endAddress +
                                "#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end",
                        Intent.URI_INTENT_SCHEME);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }

        if (intent != null) {
            context.startActivity(intent);
        }
    }

    /**
     * 拨打电话
     *
     * @param context
     * @param numStr
     */
//    public void callPhone(final Context context, final String numStr) {
//        ((BaseActivity) context).requestPermissonWithCode(new BaseActivity.onRequestPermissonResultListener() {
//            @Override
//            public void onAllowPermission() {
//                Log.e("adapter", "拨打电话：" + numStr);
//                //拨打电话
//                Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + numStr));
//                context.startActivity(intent);
//            }
//
//            @Override
//            public void onRefusePermission() {
//
//            }
//        }, Constants.CALL_PHONE, new String[]{Manifest.permission.CALL_PHONE});
//
//    }

    /**
     * 获取16进制RGB值
     *
     * @param num 手机号
     * @return RGB
     */
    @NonNull
    public String getRGB(String num) {
        int number;
        try {
            num = num.replace(" ", "");
            if (num.length() < 6) {
                num = num + "000000";
            }
            number = Integer.parseInt(num.substring(0, 6));
        } catch (Exception e) {
            number = 0;
        }
        String hexRGB = Integer.toHexString(number);
        if (hexRGB.length() < 6) {
            hexRGB = hexRGB + "000000";
        }
        hexRGB = "#" + hexRGB.substring(0, 6);
        Log.i("RGB", hexRGB);
        return hexRGB;
    }


    public static void setBackgroundColor(View v, String color) {
        //1. 先新建一个圆角矩形，并包装成一个ShapeDrawable
        ShapeDrawable backgroundDrawable = new ShapeDrawable(new RoundRectShape(new float[]{10, 10, 10, 10, 10, 10, 10, 10},
                null, null));
        //2. 设置背景颜色
        backgroundDrawable.getPaint().setColor(Color.parseColor(color));
        //3. 设置透明度
        // backgroundDrawable.setAlpha(50);
        //4. 给需要用的ViewGroup/View设置背景
        v.setBackgroundDrawable(backgroundDrawable);
    }
}
