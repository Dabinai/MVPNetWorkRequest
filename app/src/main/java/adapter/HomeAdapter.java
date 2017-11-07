package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dabin.www.lianxiren.MainActivity;
import com.dabin.www.lianxiren.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import bean.NewBase;
import bean.UUU;

/**
 * Created by Dabin on 2017/11/6.
 */

public class HomeAdapter extends XRecyclerView.Adapter {
    private Context context;
    private List<NewBase.SongListBean> song_list;
    private List<UUU> list;
    private int flag = 0;
    MainActivity mm;

    public HomeAdapter(Context context, List<NewBase.SongListBean> song_list, List<UUU> list) {
        this.context = context;
        this.song_list = song_list;
        this.list = list;
        mm = (MainActivity) context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.textView.setText(song_list.get(position).getTitle());
        myViewHolder.simpleDraweeView.setImageURI(song_list.get(position).getPic_small());

        myViewHolder.checkBox.setChecked(list.get(position).isDuicuo());

        myViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                list.get(position).setDuicuo(!list.get(position).isDuicuo());

                flag = 0;
                //循环List集合，判断所有的boolean是否为true，若为true，则flag=0；否则为1；
                for (int i = 0; i < song_list.size(); i++) {
                    if (!list.get(i).isDuicuo()) {
                        flag = 1;
                        break;
                    }
                }
                if (flag == 1) {
                    mm.setCheckBoxall(false, "全选");  //改变底部文字和选择状态

                } else {
                    mm.setCheckBoxall(true, "取消全选");//改变底部文字和选择状态
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return song_list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        SimpleDraweeView simpleDraweeView;
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkboxs);
            simpleDraweeView = itemView.findViewById(R.id.simimage);
            textView = itemView.findViewById(R.id.texts);
        }
    }

}
