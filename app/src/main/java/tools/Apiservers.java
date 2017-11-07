package tools;

import bean.NewBase;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Dabin on 2017/11/6.
 */

public interface Apiservers {
    @GET("ting?method=baidu.ting.billboard.billList&type=1&size=10&offset=0")
    Observable<NewBase> getNewBase();
}
