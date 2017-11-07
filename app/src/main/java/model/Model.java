package model;

import android.util.Log;

import java.util.List;

import bean.NewBase;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tools.Apiservers;
import tools.LoggingInterceptor;

/**
 * Created by Dabin on 2017/11/6.
 */

public class Model implements IModel{
    List<NewBase.SongListBean> song_list;

    private OnFinish onFinish;

    public void setOnFinish(OnFinish onFinish){
        this.onFinish = onFinish;
    }

    public interface OnFinish{
        void Finish(List<NewBase.SongListBean> song_list);
    }



    @Override
    public void getUrl(String url) {

        //添加拦截器
        OkHttpClient build = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .build();

        Retrofit build1 = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(build) //添加拦截器
                .build();
        Apiservers apiservers = build1.create(Apiservers.class);
        Observable<NewBase> newBase = apiservers.getNewBase();
        newBase.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewBase>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("AAAAAAAAAA","sorry");
                    }

                    @Override
                    public void onNext(NewBase newBase) {
                        song_list = newBase.getSong_list();
                        onFinish.Finish(song_list);
                    }
                });
    }
}
