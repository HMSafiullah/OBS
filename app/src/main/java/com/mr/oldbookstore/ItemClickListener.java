package com.mr.oldbookstore;

import android.view.View;

public interface ItemClickListener {
    void onClickPaid(View view, int position);
    void onClickFree(View view, int position);
}
