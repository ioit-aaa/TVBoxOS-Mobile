package com.github.tvbox.osc.ui.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.github.tvbox.osc.R;
import com.github.tvbox.osc.ui.widget.SelectableTagFlexLayout;
import com.lxj.xpopup.impl.PartShadowPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.util.List;

public class SearchSuggestionsDialog extends PartShadowPopupView {

    private List<String> mList;
    private OnSelectListener onSelectListener;
    private SelectableTagFlexLayout mFl;

    public SearchSuggestionsDialog(@NonNull Context context, List<String> list, OnSelectListener onSelectListener) {
        super(context);
        mList = list;
        this.onSelectListener = onSelectListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_search_uggestions;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        mFl = findViewById(R.id.fl_suggest);
        mFl.setTags(mList);
        mFl.setOnTagSelectListener((position, text) -> {
            if (onSelectListener != null) {
                onSelectListener.onSelect(position, text);
            }
        });
    }
}