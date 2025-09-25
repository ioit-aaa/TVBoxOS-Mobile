package com.github.tvbox.osc.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.github.tvbox.osc.R;
import com.google.android.flexbox.FlexboxLayout;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SelectableTagFlexLayout extends FlexboxLayout {

    private int maxSelectCount = 1;
    private Set<Integer> selectedIndices = new HashSet<>();
    private List<String> tagList;
    private OnTagSelectListener onTagSelectListener;

    public SelectableTagFlexLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SelectableTagFlexLayout);
        maxSelectCount = ta.getInt(R.styleable.SelectableTagFlexLayout_max_select, 1);
        ta.recycle();
    }

    public void setTags(List<String> tags) {
        this.tagList = tags;
        removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (int i = 0; i < tags.size(); i++) {
            final int position = i;
            final String text = tags.get(i);
            TextView tv = (TextView) inflater.inflate(R.layout.item_search_word_hot, this, false);
            tv.setText(text);
            updateTagStyle(tv, selectedIndices.contains(position));
            tv.setOnClickListener(v -> {
                boolean wasSelected = selectedIndices.contains(position);
                if (wasSelected) {
                    selectedIndices.remove(position);
                } else {
                    if (maxSelectCount == 1) {
                        selectedIndices.clear();
                    }
                    if (maxSelectCount <= 0 || selectedIndices.size() < maxSelectCount) {
                        selectedIndices.add(position);
                    }
                }
                setTags(tagList);
                if (onTagSelectListener != null) {
                    onTagSelectListener.onSelect(position, text);
                }
            });
            addView(tv);
        }
    }

    private void updateTagStyle(TextView tv, boolean selected) {
        if (selected) {
            tv.setBackgroundResource(R.drawable.bg_tag_selected);
            tv.setTextColor(getResources().getColor(R.color.white));
        } else {
            tv.setBackgroundResource(R.drawable.bg_tag_normal);
            tv.setTextColor(getResources().getColor(R.color.text_foreground));
        }
    }

    public void setOnTagSelectListener(OnTagSelectListener listener) {
        this.onTagSelectListener = listener;
    }

    public interface OnTagSelectListener {
        void onSelect(int position, String text);
    }

    public Set<Integer> getSelectedIndices() {
        return selectedIndices;
    }

    public void setMaxSelectCount(int count) {
        this.maxSelectCount = count;
    }
}