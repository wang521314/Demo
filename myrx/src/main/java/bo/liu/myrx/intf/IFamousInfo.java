package bo.liu.myrx.intf;

import bo.liu.myrx.mode.FamousInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public interface IFamousInfo {
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Call<FamousInfo> getFamousResult();


}
