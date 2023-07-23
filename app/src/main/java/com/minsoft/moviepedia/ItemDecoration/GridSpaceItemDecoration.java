package com.minsoft.moviepedia.ItemDecoration;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int startTop;
    private final int top;
    private final int left;
    private final int right;

    public GridSpaceItemDecoration(int startTop, int top, int left) {
        this.startTop = startTop;
        this.top = top;
        this.left = left;
        right = 0;
    }

    public GridSpaceItemDecoration(int startTop, int top, int left, int right) {
        this.startTop = startTop;
        this.top = top;
        this.left = left;
        this.right = right;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = left;
        outRect.right = right;
        if (parent.getChildLayoutPosition(view) == 0 || parent.getChildLayoutPosition(view) == 1) {
            outRect.top = startTop;
        } else {
            outRect.top = top;
        }
    }
}
