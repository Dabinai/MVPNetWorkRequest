package presenter;

import java.util.List;

import bean.NewBase;
import model.Model;
import view.IView;

/**
 * Created by Dabin on 2017/11/6.
 */

public class Presenters implements Model.OnFinish{

    private final IView iView;
    private final Model model;

    public Presenters(IView iView) {
        this.iView = iView;
        model = new Model();
        model.setOnFinish(this);
    }

    public void setUrl(String url){
        model.getUrl(url);
    }



    @Override
    public void Finish(List<NewBase.SongListBean> song_list) {
        iView.getData(song_list);
    }
}
