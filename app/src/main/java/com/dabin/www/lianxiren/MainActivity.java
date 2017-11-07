package com.dabin.www.lianxiren;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import adapter.HomeAdapter;
import bean.NewBase;
import bean.UUU;
import presenter.Presenters;
import tools.API;
import view.IView;

public class MainActivity extends AppCompatActivity implements IView {
    private TextView quanText;
    private TextView bianji;
    private List<UUU> list;
    private List<NewBase.SongListBean> song_list;
    private XRecyclerView xrevycler;
    private LinearLayout linearLayout;
    private HomeAdapter homeAdapter;
    private CheckBox checkBoxall;
    private boolean aBoolean =true;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = (LinearLayout) findViewById(R.id.lll);
        checkBoxall = (CheckBox) findViewById(R.id.checkall);
        quanText = (TextView) findViewById(R.id.quanText);
        bianji = (TextView) findViewById(R.id.bianji);
        xrevycler = (XRecyclerView) findViewById(R.id.xrevycler);
        xrevycler.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();


        Presenters presenters = new Presenters(this);
        presenters.setUrl(API.BASE);
    }

    //编辑
    public void bianjis(View view) {
        if (aBoolean) {
            aBoolean = false;
            linearLayout.setVisibility(View.VISIBLE);
            bianji.setText("完成");

        } else {
            linearLayout.setVisibility(View.GONE);
            aBoolean = true;
            bianji.setText("编辑");
        }
    }

    //全选和全不选
    public void quanxuan(View view) {
        if (flag) {
            for (int i = 0; i < song_list.size(); i++) {
                list.get(i).setDuicuo(true);
            }
            flag = !flag;
            quanText.setText("取消全选");
        } else {
            for (int i = 0; i < song_list.size(); i++) {
                list.get(i).setDuicuo(false);
            }
            flag = !flag;
            quanText.setText("全选");
        }
        homeAdapter.notifyDataSetChanged();

    }

    //删除选中的
    public void deletes(View view) {
        for (int i = 0; i < song_list.size(); i++) {
            if (list.get(i).isDuicuo()) {
                song_list.remove(i);
                list.remove(i);
                i--;
            }
        }
        homeAdapter.notifyDataSetChanged();
    }

    @Override
    public void getData(List<NewBase.SongListBean> song_list) {
        this.song_list = song_list;
        for (int i = 0; i < song_list.size(); i++) {
            list.add(new UUU(false));
        }
        homeAdapter = new HomeAdapter(MainActivity.this, song_list, list);
        xrevycler.setAdapter(homeAdapter);
    }



    //设置全选状态和文字，该方法主要是用于HomeAdapter中调用，来改变MainActivity中的状态。
    public void setCheckBoxall(boolean b, String s) {
        checkBoxall.setChecked(b);
        quanText.setText(s);
    }

    private static Boolean isQuit = false;
    private Timer timer = new Timer();

    //若在编辑状态，若在2秒之内连续点击 ：则第一次退出编辑，第二次退出程序
    @Override
    public void onBackPressed() {
        if(aBoolean){
            finish();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }else {
            linearLayout.setVisibility(View.GONE);
            aBoolean = true;
            bianji.setText("编辑");
            Toast.makeText(getBaseContext(), "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
            //点击之后，过2秒让 aBoolean = false
            TimerTask task = null;
            task = new TimerTask() {
                @Override
                public void run() {
                    aBoolean = false;
                }
            };
            timer.schedule(task, 2000);
        }
    }
}
