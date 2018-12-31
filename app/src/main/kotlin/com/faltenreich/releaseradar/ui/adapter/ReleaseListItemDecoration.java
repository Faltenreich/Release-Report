package com.faltenreich.releaseradar.ui.adapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.faltenreich.releaseradar.R;

/**
 * @see [https://stackoverflow.com/a/43665611](stackoverflow)
 */
public class ReleaseListItemDecoration extends RecyclerView.ItemDecoration {

    private final SectionCallback sectionCallback;

    private View headerView;
    private TextView header;
    private int padding;
    private int spanCount;

    public ReleaseListItemDecoration(int padding, int spanCount, @NonNull SectionCallback sectionCallback) {
        this.padding = padding;
        this.spanCount = spanCount;
        this.sectionCallback = sectionCallback;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        int column = position % spanCount;

        if (sectionCallback.isSection(position) || sectionCallback.isSection(position - 1)) {
            outRect.top = headerView != null ? headerView.getHeight() + padding : 0;
        }
        outRect.bottom = padding;
        outRect.left = column % spanCount == 0 ? padding : padding / 2;
        outRect.right = column % spanCount != 0 ? padding : padding / 2;
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);

        if (headerView == null) {
            headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_release_date, parent, false);
            header = headerView.findViewById(R.id.releaseDateTextView);
            fixLayoutSize(headerView, parent);
        }

        CharSequence previousHeader = "";
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            final int position = parent.getChildAdapterPosition(child);

            CharSequence title = sectionCallback.getSectionHeader(position);
            header.setText(title);
            if (!previousHeader.equals(title) || sectionCallback.isSection(position)) {
                canvas.save();
                canvas.translate(0, Math.max(0, child.getTop() - headerView.getHeight() - padding));
                headerView.draw(canvas);
                canvas.restore();
                previousHeader = title;
            }
        }
    }

    /**
     * Measures the header view to make sure its size is greater than 0 and will be drawn
     * https://yoda.entelect.co.za/view/9627/how-to-android-recyclerview-item-decorations
     */
    private void fixLayoutSize(View view, ViewGroup parent) {
        int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);

        int childWidth = ViewGroup.getChildMeasureSpec(widthSpec, parent.getPaddingLeft() + parent.getPaddingRight(), view.getLayoutParams().width);
        int childHeight = ViewGroup.getChildMeasureSpec(heightSpec, parent.getPaddingTop() + parent.getPaddingBottom(), view.getLayoutParams().height);

        view.measure(childWidth, childHeight);

        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    public interface SectionCallback {
        boolean isSection(int position);
        CharSequence getSectionHeader(int position);
    }
}