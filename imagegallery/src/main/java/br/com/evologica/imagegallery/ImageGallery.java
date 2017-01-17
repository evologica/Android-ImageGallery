package br.com.evologica.imagegallery;

/**
 * Created by marcussales on 17/01/2017.
 */

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class ImageGallery extends RecyclerView {

    /*
        Attributes related to the number of columns
        based on orientation.
     */
    private int mColumnsNumberPortrait = 3;
    private int mColumnsNumberLandscape = 5;

    /*
        Spacing between elements, given in pixels.
     */
    private int mSpacing = 4;

    /*
        Current position seen in the screen.
     */
    private int mScrollPosition = 0;

    public ImageGallery(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public ImageGallery(Context context) {
        super(context);
        init(context,null);
    }

    public ImageGallery(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        /*
            XML attributes read
         */
        if (attrs != null) {
            TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ImageGallery, 0, 0);
            try {
                mColumnsNumberPortrait = attributes.getInt(R.styleable.ImageGallery_columnsNumberPortrait, mColumnsNumberPortrait);
                mColumnsNumberLandscape = attributes.getInt(R.styleable.ImageGallery_columnsNumberLandscape, mColumnsNumberLandscape);
                mSpacing = attributes.getDimensionPixelSize(R.styleable.ImageGallery_gap, mSpacing);
            } finally {
                attributes.recycle();
            }
        }

        GridLayoutManager mLayoutManager;

        /*
            Setting the configuration for the Layout Manager, given the orientation
         */
        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            mLayoutManager = new GridLayoutManager(context, mColumnsNumberPortrait);
        else
            mLayoutManager = new GridLayoutManager(context, mColumnsNumberLandscape);

        /*
            Set up of the scroll position
         */
        if(getLayoutManager() != null){
            mScrollPosition = ((GridLayoutManager) getLayoutManager()).findLastCompletelyVisibleItemPosition();
        }

        super.setLayoutManager(mLayoutManager);
        /*
            Set up of the spacing
         */
        super.addItemDecoration(new GridSpaceDecoration(mLayoutManager.getSpanCount(),mSpacing,true));
        super.scrollToPosition(mScrollPosition);

    }


    public void setScrollPosition(int scrollPosition){
        mScrollPosition = scrollPosition;
    }

    public int getColumnsNumberPortrait() {
        return mColumnsNumberPortrait;
    }

    public void setColumnsNumberPortrait(int columnsNumberPortrait) {
        if(columnsNumberPortrait < 1 || columnsNumberPortrait == mColumnsNumberPortrait) return;
        mColumnsNumberPortrait = columnsNumberPortrait;
        init(getContext(),null);
    }

    public int getSpacing() {
        return mSpacing;
    }

    public void setSpacing(int mSpacing) {
        if(mSpacing < 0) return;
        this.mSpacing = mSpacing;
        init(getContext(),null);
    }

    public int getColumnsNumberLandscape() {
        return mColumnsNumberLandscape;
    }

    public void setColumnsNumberLandscape(int columnsNumberLandscape) {
        if(columnsNumberLandscape < 1 || mColumnsNumberLandscape == columnsNumberLandscape ) return;
        this.mColumnsNumberLandscape = columnsNumberLandscape;
        init(getContext(),null);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {}

}

