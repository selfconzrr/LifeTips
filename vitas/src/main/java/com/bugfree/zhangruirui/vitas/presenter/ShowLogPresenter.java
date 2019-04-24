package com.bugfree.zhangruirui.vitas.presenter;

import com.bugfree.zhangruirui.vitas.Vitas;
import com.bugfree.zhangruirui.vitas.view.IShowLogView;

public class ShowLogPresenter {
  IShowLogView mShowLogView;

  public ShowLogPresenter(IShowLogView showLogView) {
    mShowLogView = showLogView;
  }

  public void showLogList() {
    mShowLogView.showLogList(Vitas.getInstance().getLogRepository().queryDB());
  }

  public void showLogList(String queryText) {
    mShowLogView.showLogList(Vitas.getInstance().getLogRepository().queryDB(queryText));
  }

  public void clearLogList() {
    Vitas.getInstance().getLogRepository().clearDB();
  }
}
