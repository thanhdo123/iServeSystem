package com.i.serve.iservesystem.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Admin on 5/27/2015.
 */
public class WrapLayout extends ViewGroup {

    public WrapLayout(Context context){
        super(context);
    }

    public WrapLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WrapLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Any layout manager that doesn't scroll will want this.
     */
    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        int curWidth, curHeight, curLeft, curTop, maxHeight;

        //get the available size of child view
        int childLeft = this.getPaddingLeft();
        int childTop = this.getPaddingTop();
        int childRight = this.getMeasuredWidth() - this.getPaddingRight();
        int childBottom = this.getMeasuredHeight() - this.getPaddingBottom();
        int childWidth = childRight - childLeft;
        int childHeight = childBottom - childTop;

        maxHeight = 0;
        curLeft = childLeft;
        curTop = childTop;
        //walk through each child, and arrange it from left to right
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                //Get the maximum size of the child
                child.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.AT_MOST),
                        MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST));
                curWidth = child.getMeasuredWidth();
                curHeight = child.getMeasuredHeight();
                //wrap is reach to the end
                if (curLeft + curWidth >= childRight) {
                    curLeft = childLeft;
                    curTop += maxHeight;
                    maxHeight = 0;
                }
                //do the layout
                child.layout(curLeft, curTop, curLeft + curWidth, curTop + curHeight);
                //store the max height
                if (maxHeight < curHeight)
                    maxHeight = curHeight;
                curLeft += curWidth;
            }
        }
    }
}
