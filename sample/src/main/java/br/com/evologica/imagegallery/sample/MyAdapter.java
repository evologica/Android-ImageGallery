package br.com.evologica.imagegallery.sample;

/**
 * Created by marcussales on 17/01/2017.
 */

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.esafirm.imagepicker.features.ImagePicker;

import br.com.evologica.imagegallery.ImageGalleryAdapter;

import static br.com.evologica.imagegallery.sample.GridFragment.MY_REQUEST_CODE;
import static br.com.evologica.imagegallery.sample.MainActivity.REQUEST_CAMERA;
import static br.com.evologica.imagegallery.sample.MainActivity.SELECT_FILE;

/**
 * Created by marcussales on 16/01/2017.
 */

public class MyAdapter extends ImageGalleryAdapter {

    private int mButtonResourceID;
    private int mButtonLayoutID;

    public static final int IMAGE_VIEW = 0;
    public static final int BUTTON_VIEW = 1;

    public MyAdapter(Context context, int imageResourceID, int imageLayoutID, int buttonResourceID, int buttonLayoutID) {
        super(context, imageResourceID,imageLayoutID);
        mButtonResourceID = buttonResourceID;
        mButtonLayoutID = buttonLayoutID;
    }

    public MyAdapter(Fragment fragment, int imageResourceID, int imageLayoutID, int buttonResourceID, int buttonLayoutID) {
        super(fragment, imageResourceID,imageLayoutID);
        mButtonResourceID = buttonResourceID;
        mButtonLayoutID = buttonLayoutID;
    }

    public MyAdapter(Activity activity, int imageResourceID, int imageLayoutID, int buttonResourceID, int buttonLayoutID) {
        super(activity, imageResourceID,imageLayoutID);
        mButtonResourceID = buttonResourceID;
        mButtonLayoutID = buttonLayoutID;
    }

    @Override
    public int getItemViewType(int position) {
        if(position < mData.size()) return IMAGE_VIEW;
        return BUTTON_VIEW;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == IMAGE_VIEW){
            //return super.onCreateViewHolder(parent,viewType);
            return new ImageViewHolder(mInflater.inflate(mImageLayoutID,parent,false),mImageResourceID);
        }
        return new ButtonViewHolder(mInflater.inflate(mButtonLayoutID,parent,false),mButtonResourceID);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(getItemViewType(position) == IMAGE_VIEW){
            super.onBindViewHolder(holder,position);
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    public class ImageViewHolder extends ViewHolder{

        public ImageViewHolder(View itemView, int imageResId) {
            super(itemView, imageResId);
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final CharSequence[] items = { "Excluir","Cancelar" };
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mFragment.getActivity()); //Read Update
                    alertDialog.setTitle("Escolha uma ação");

                    alertDialog.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (items[item].equals("Excluir")) {
                                mData.remove(getAdapterPosition());
                                notifyDataSetChanged();
                            } else if (items[item].equals("Cancelar")) {
                                dialog.dismiss();
                            }
                        }
                    });

                    alertDialog.show();
                }
            });
        }
    }

    public class ButtonViewHolder extends ViewHolder{
        public ImageButton mImageButton;

        public ButtonViewHolder(View itemView,int buttonResourceID) {
            super(itemView);
            mImageButton = (ImageButton) itemView.findViewById(buttonResourceID);
            mImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final CharSequence[] items = {"Câmera", "Galeria", "Cancelar"};
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mFragment.getActivity()); //Read Update
                    alertDialog.setTitle("Escolha uma ação");

                    alertDialog.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (items[item].equals("Câmera")) {
                                /*
                                    Asking for permission at runtime.
                                 */
                                if (Build.VERSION.SDK_INT >= 23 &&
                                        (mContext.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                                    mFragment.requestPermissions(new String[]{Manifest.permission.CAMERA},
                                            MY_REQUEST_CODE);
                                } else {
                                    ((GridFragment) mFragment).startActivityForResult(((GridFragment) mFragment).cameraModule.getCameraIntent(mFragment.getActivity()), REQUEST_CAMERA);
                                }

                            } else if (items[item].equals("Galeria")) {
                                ImagePicker.create(mFragment.getActivity())
                                        .folderMode(true)
                                        .folderTitle("Galeria")
                                        .imageTitle("Toque para selecionar")
                                        .limit(10)
                                        .start(SELECT_FILE);
                            } else if (items[item].equals("Cancelar")) {
                                dialog.dismiss();
                            }
                        }
                    });

                    alertDialog.show();  //<-- See This!
                }
            });
        }
    }
}

