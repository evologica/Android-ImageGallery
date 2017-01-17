package br.com.evologica.imagegallery;

/**
 * Created by marcussales on 17/01/2017.
 */


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.ViewHolder> {

    /*
        Images you want to display, as strings (paths of the images).
     */
    protected List<String> mData;
    /*
        Contexts, necessary for intent calling, among other things.
     */
    protected Context mContext;
    protected Activity mActivity;
    protected Fragment mFragment;
    /*
        Inflater for the image XML file to be displayed.
     */
    protected LayoutInflater mInflater;
    /*
        Resources ID of the image XML file.
     */
    protected int mImageResourceID;
    protected int mImageLayoutID;

    public ImageGalleryAdapter(Context context,int imageResourceID,int imageLayoutID){
        mData = new ArrayList<String>();
        mContext = context;
        mActivity = null;
        mFragment = null;
        mInflater = LayoutInflater.from(context);
        mImageResourceID = imageResourceID;
        mImageLayoutID = imageLayoutID;
    }

    public ImageGalleryAdapter(Fragment fragment,int imageResourceID,int imageLayoutID){
        mData = new ArrayList<String>();
        mFragment = fragment;
        mActivity = fragment.getActivity();
        mContext = mActivity.getApplicationContext();
        mInflater = LayoutInflater.from(mActivity);
        mImageResourceID = imageResourceID;
        mImageLayoutID = imageLayoutID;
    }

    public ImageGalleryAdapter(Activity activity,int imageResourceID,int imageLayoutID){
        mData = new ArrayList<String>();
        mActivity = activity;
        mContext = mActivity.getApplicationContext();
        mFragment = null;
        mInflater = LayoutInflater.from(activity);
        mImageResourceID = imageResourceID;
        mImageLayoutID = imageLayoutID;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(mImageLayoutID,parent,false),mImageResourceID);
    }

    /*
        Method that displays the image. Here we use Glide, if you want
        to use another image loading library, you must override this method
        in your own Adapter class.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageView imageView = holder.mImageView;
        if(mFragment != null)
            Glide.with(mFragment)
                    .fromString()
                    .crossFade()
                    .centerCrop()
                    .load(mData.get(position))
                    .into(imageView);
        else if (mActivity != null)
            Glide.with(mActivity)
                    .fromString()
                    .crossFade()
                    .centerCrop()
                    .load(mData.get(position))
                    .into(imageView);
        else
            Glide.with(mContext)
                    .fromString()
                    .crossFade()
                    .centerCrop()
                    .load(mData.get(position))
                    .into(imageView);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    /*
        Methods to add images to the data set.
     */
    public void addImage(String image){
        mData.add(image);
        notifyDataSetChanged();
    }

    public void addListImage(List<String> images){
        mData.addAll(images);
        notifyDataSetChanged();
    }


    /*
        ViewHolder which contains the ImageView object, which will be used to display the images.
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;

        /*
            Constructor for extending this class and not using the ImageView object
        */
        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = null;
        }

        /*
            Default constructor.
         */
        public ViewHolder(View itemView, int imageResId){
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(imageResId);
        }

    }

}
