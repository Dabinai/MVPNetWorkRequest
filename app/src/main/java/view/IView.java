package view;

import java.util.List;

import bean.NewBase;

/**
 * Created by Dabin on 2017/11/6.
 */

public interface IView {
    void getData( List<NewBase.SongListBean> song_list);
}
