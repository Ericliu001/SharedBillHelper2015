package com.ericliudeveloper.sharedbillhelper.ui.presenter;

import de.greenrobot.event.EventBus;

/**
 * Created by liu on 21/06/15.
 */
public class BasePresenter {

    public void registerStickyEventBusListener(){EventBus.getDefault().registerSticky(this);}

    public void registerEventBusListener() {
        EventBus.getDefault().register(this);
    }

    public void unregisterEventBusListener() {
        EventBus.getDefault().unregister(this);
    }
}
