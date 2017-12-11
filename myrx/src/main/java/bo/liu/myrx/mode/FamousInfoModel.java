package bo.liu.myrx.mode;

import android.content.Context;

import bo.liu.myrx.Constant;
import bo.liu.myrx.RetrofitWrapper;
import bo.liu.myrx.intf.IFamousInfo;
import retrofit2.Call;

/**
 * Created by Administrator on 2017/12/5 0005.
 * http://blog.csdn.net/jdsjlzx/article/details/52015347博客地址
 */

public class FamousInfoModel {
    private static FamousInfoModel famousInfoModel;
    private IFamousInfo mIFamousInfo;
    public FamousInfoModel(Context context) {
        mIFamousInfo = RetrofitWrapper.getInstance(Constant.BASEURL).create(IFamousInfo.class);
    }

    public static FamousInfoModel getInstance(Context context){
        if(famousInfoModel == null) {
            famousInfoModel = new FamousInfoModel(context);
        }
        return famousInfoModel;
    }

    public Call<FamousInfo> queryLookUp(FamousInfoReq famousInfoReq) {
        Call<FamousInfo> infoCall = mIFamousInfo.getFamousResult();
        return infoCall;
    }
}
