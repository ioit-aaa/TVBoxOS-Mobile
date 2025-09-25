package com.github.tvbox.osc.ui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.tvbox.osc.R;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.JustifyContent;
import com.lxj.xpopup.impl.PartShadowPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.util.List;

public class SearchSuggestionsDialog extends PartShadowPopupView {

    private List<String> mList;
    private OnSelectListener onSelectListener;
    private FlexboxLayout mFl;

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
        mFl.setFlexDirection(FlexDirection.ROW);
        mFl.setFlexWrap(FlexWrap.WRAP);
        mFl.setJustifyContent(JustifyContent.FLEX_START);
        updateSuggestions(mList);
    }

    public void updateSuggestions(List<String> list) {
        mList = list;
        if (mFl != null) {
            mFl.removeAllViews();
            for (int i = 0; i < mList.size(); i++) {
                final int position = i;
                final String text = mList.get(i);
                TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_search_word_hot, mFl, false);
                tv.setText(text);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onSelectListener != null) {
                            onSelectListener.onSelect(position, text);
                        }
                    }
                });
                mFl.addView(tv);
            }
        }
    }
}