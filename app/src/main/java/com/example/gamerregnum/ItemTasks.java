package com.example.gamerregnum;

import android.widget.Button;

public class ItemTasks {
    private String TitleTask;
    private String PointTask;
    private Button btnTask;

    public ItemTasks(String titleTask, String pointTask, Button btnTask) {
        TitleTask = titleTask;
        PointTask = pointTask;
        this.btnTask = btnTask;
    }

    public String getTitleTask() {
        return TitleTask;
    }

    public void setTitleTask(String titleTask) {
        TitleTask = titleTask;
    }

    public String getPointTask() {
        return PointTask;
    }

    public void setPointTask(String pointTask) {
        PointTask = pointTask;
    }

    public Button getBtnTask() {
        return btnTask;
    }

    public void setBtnTask(Button btnTask) {
        this.btnTask = btnTask;
    }
}
