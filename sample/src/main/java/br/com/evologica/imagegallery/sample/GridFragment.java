package br.com.evologica.imagegallery.sample;

/**
 * Created by marcussales on 17/01/2017.
 */

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.camera.DefaultCameraModule;
import com.esafirm.imagepicker.features.camera.OnImageReadyListener;
import com.esafirm.imagepicker.model.Image;

import java.util.ArrayList;
import java.util.List;

import br.com.evologica.imagegallery.ImageGallery;

import static br.com.evologica.imagegallery.sample.MainActivity.REQUEST_CAMERA;
import static br.com.evologica.imagegallery.sample.MainActivity.SELECT_FILE;

/**
 * Created by marcussales on 04/01/2017.
 */

public class GridFragment extends Fragment{

    public DefaultCameraModule cameraModule = new DefaultCameraModule();
    public static final int MY_REQUEST_CODE = 1996;
    private ImageGallery mImageGallery;
    private MyAdapter myAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        myAdapter = new MyAdapter(this,R.id.grid_item_img,R.layout.item_img,R.id.grid_item_button,R.layout.item_button);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mImageGallery = (ImageGallery) rootView.findViewById(R.id.recyclerview);
        mImageGallery.setAdapter(myAdapter);


        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if(requestCode == SELECT_FILE){
                ArrayList<Image> photos = (ArrayList<Image>) ImagePicker.getImages(data);
                for(Image i : photos){
                    myAdapter.addImage(i.getPath());
                }
            }
            else if (requestCode == REQUEST_CAMERA) {
                cameraModule.getImage(getActivity(), data, new OnImageReadyListener() {
                    @Override
                    public void onImageReady(final List<Image> list) {
                        /*
                            Need to use this method to retrieve the image
                            taken by the camera. You may use another image picker.
                         */
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(list != null && list.size() > 0)
                                    myAdapter.addImage(list.get(0).getPath());
                            }
                        });
                    }
                });
            }
        }
    }


    /*
        Asking for permissions in newer Android versions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == MY_REQUEST_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startActivityForResult(cameraModule.getCameraIntent(getActivity()),REQUEST_CAMERA);
            }
        }
    }

}

