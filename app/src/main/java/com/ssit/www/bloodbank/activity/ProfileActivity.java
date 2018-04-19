package com.ssit.www.bloodbank.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ssit.www.bloodbank.PreferenceManager.BBSharedPreferenceManager;
import com.ssit.www.bloodbank.R;
import com.ssit.www.bloodbank.User.ChangePicPojo_class;
import com.ssit.www.bloodbank.api_url.ApiClient;
import com.ssit.www.bloodbank.api_url.ApiInterface;
import com.ssit.www.bloodbank.api_url.CheckInternetConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Android on 16-01-2018.
 */

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = ProfileActivity.class.getSimpleName();

    private TextView tv_name, tv_email, tv_contact, tv_gender, tv_bloodgp, tv_address;

    //  ImageView iv_edit;

    ApiInterface apiInterface;

    CircleImageView profile_image;

    AlertDialog dialog;

    private Toolbar toolbar;
    ImageView iv_back;

    public static final int CAMERA_REQUEST = 0;
    public static final int GALLARY_REQUEST = 1;

    String image;
    private Bitmap photo;

    int MY_SOCKET_TIMEOUT_MS = 30000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//       toolbar=(Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        apiInterface= ApiClient.getClient().create(ApiInterface.class);

        iv_back = (ImageView) findViewById(R.id.iv_back);

        profile_image = (CircleImageView) findViewById(R.id.profile_image);


        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_contact = (TextView) findViewById(R.id.tv_me_phone);
        tv_gender = (TextView) findViewById(R.id.tv_gender);
        tv_bloodgp = (TextView) findViewById(R.id.tv_bloodgp);
        tv_address = (TextView) findViewById(R.id.tv_address);
        //   iv_edit=(ImageView)findViewById(R.id.iv_edit);

        //iv_edit.setOnClickListener(this);

        iv_back.setOnClickListener(this);
        profile_image.setOnClickListener(this);

        tv_name.setText(BBSharedPreferenceManager.getName("c_name", getApplicationContext()));
        tv_email.setText(BBSharedPreferenceManager.getEmail("c_email", getApplicationContext()));
        tv_contact.setText(BBSharedPreferenceManager.getMobile("c_mobile", getApplicationContext()));
        tv_gender.setText(BBSharedPreferenceManager.getGender("c_gender", getApplicationContext()));
        tv_bloodgp.setText(BBSharedPreferenceManager.getBloodgp("c_bloodgp", getApplicationContext()));
        tv_address.setText(BBSharedPreferenceManager.getAddress("c_address", getApplicationContext()) + ", Tq - " + BBSharedPreferenceManager.getCity("c_city", getApplicationContext()) + ", State - " + BBSharedPreferenceManager.getState("c_state", getApplicationContext()));


        String url = BBSharedPreferenceManager.getProfile("c_profile",this);
//        String url="http://atmthub.com/self_secure/images/user_1517378301_45.jpg";
        Glide.with(this)
                .load(url)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profile_image);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                startActivity(new Intent(ProfileActivity.this, DashBoard.class));
                finish();
                break;

            case R.id.profile_image:
                showPictureDialog();
                break;

        }
    }

    private void showPictureDialog() {


        showToast("Tack Picture");


        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.profile_camera, null);

        TextView tv_camera = (TextView) view.findViewById(R.id.tv_camera);
        TextView tv_gallery = (TextView) view.findViewById(R.id.tv_gallery);
        TextView tv_head = (TextView) view.findViewById(R.id.tv_head);
        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);

        tv_head.setShadowLayer(4.3f, 5.0f, 4.0f, Color.parseColor("#000000"));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhotoFromCamera();
            }
        });

        tv_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhotoFromGallery();
            }
        });

    }

    private void showToast(String msg) {
        Toast.makeText(this, "" + msg, Toast.LENGTH_SHORT).show();
    }

    private void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLARY_REQUEST);
    }

    private void takePhotoFromCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    public Bitmap scaleCenterCrop(Bitmap source, int newHeight, int newWidth) {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();

        // Compute the scaling factors to fit the new height and width, respectively.
        // To cover the final image, the final scaling will be the bigger
        // of these two.
        float xScale = (float) newWidth / sourceWidth;
        float yScale = (float) newHeight / sourceHeight;
        float scale = Math.max(xScale, yScale);

        // Now get the size of the source bitmap when scaled
        float scaledWidth = scale * sourceWidth;
        float scaledHeight = scale * sourceHeight;

        // Let's find out the upper left coordinates if the scaled bitmap
        // should be centered in the new size give by the parameters
        float left = (newWidth - scaledWidth) / 2;
        float top = (newHeight - scaledHeight) / 2;

        // The target rectangle for the new, scaled version of the source bitmap will now
        // be
        RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);

        // Finally, we create a new bitmap of the specified size and draw our new,
        // scaled bitmap onto it.
        Bitmap dest = Bitmap.createBitmap(newWidth, newHeight, source.getConfig());
        Canvas canvas = new Canvas(dest);
        canvas.drawBitmap(source, null, targetRect, null);

        return dest;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == CAMERA_REQUEST) {
                photo = (Bitmap) data.getExtras().get("data");
                profile_image.setImageBitmap(photo);
                image = getStringImage(photo);
                try {
                    Boolean result= CheckInternetConnection.onInternetCheck(this);
                    if (result) {
                        uploadImage();
                    }else {
                        showToast("Cannot connect to Internet...Please check your connection!");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Upload image call ", e);
                }

                Log.d(">>>>>>ENCODED", image);
            } else if (requestCode == GALLARY_REQUEST) {
                if (data != null) {

                    Uri contentURI = data.getData();
                    try {
                        photo = MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI);
                        // profile_image.setImageBitmap(photo);
                        // profile_image.setImageURI(contentURI);
                        // profile_image.setImageBitmap(photo);
                        Bitmap scaledphoto = scaleCenterCrop(photo, 500, 500);
                        profile_image.setImageBitmap(scaledphoto);

                        image = getStringImage(scaledphoto);

                        try {
                            Boolean result= CheckInternetConnection.onInternetCheck(this);
                            if (result) {
                                uploadImage();
                            }else {
                                showToast("Cannot connect to Internet...Please check your connection!");
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Upload image call ", e);
                        }
                        Log.d(">>>>>>ENCODED", image);

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }


            }

        } catch (Exception e) {
        }

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage() {

        final ProgressDialog progressDialog = ProgressDialog.show(this, "Upload Image", "Please wait...", false);

        Call<ChangePicPojo_class> call = apiInterface.CHANGE_PIC_POJO_CLASS_CALL(BBSharedPreferenceManager.getClientID("c_id", getApplicationContext()), image, "");
        call.enqueue(new Callback<ChangePicPojo_class>() {
            @Override
            public void onResponse(Call<ChangePicPojo_class> call, retrofit2.Response<ChangePicPojo_class> response) {
                progressDialog.dismiss();
                ChangePicPojo_class changePicPojo_class = response.body();

                String status, message, client_pic;
                status = changePicPojo_class.getStatus();
                message = changePicPojo_class.getMessage();

                Log.d(TAG, "Status = " + status + " Message = " + message);

                if (status.equals("200Ok") && message.equals("Profile Pic Changed")) {
                    client_pic = changePicPojo_class.getClient_pic();
                    BBSharedPreferenceManager.setProfile("c_profile", client_pic, getApplicationContext());
                    showToast("Profile Pic Chenged");
                    dialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<ChangePicPojo_class> call, Throwable t) {

                progressDialog.dismiss();
                dialog.dismiss();
                Log.d(TAG, "Change Your Profile image OnFailure Message = " + t.getMessage());
            }
        });

    }
//
//        final ProgressDialog progressDialog = ProgressDialog.show(this, "Uploading Image", "Please wait...", false);
//
//        StringRequest request = new StringRequest(Request.Method.POST, "http://atmthub.com/blood_bank/changeprofilepic.php", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                progressDialog.dismiss();
//
//                Log.d(TAG, response);
//
//                JSONObject jsonObject = null;
//
//                String status = null, message = null, client_pic = null;
//
//                try {
//                    jsonObject = new JSONObject(response);
//                    status = jsonObject.getString("status");
//                    message = jsonObject.getString("message");
//
//                    if (jsonObject.has("status") || jsonObject.has("message")) {
//                        if (status.equals("200Ok") || message.equals("Profile Pic Chenged")) {
//                            client_pic = jsonObject.getString("client_pic");
//                            BBSharedPreferenceManager.setProfile("c_profile", client_pic, getApplicationContext());
//                            showToast("Profile Pic Chenged");
//                            dialog.dismiss();
//                        }
//                    }
//
//                } catch (JSONException e) {
//                    Log.d(TAG, "Response Exception :: " + e);
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                String message = null;
//                progressDialog.dismiss();
//                dialog.dismiss();
//
//                if (error instanceof NetworkError) {
//                    message = "Cannot connect to Internet...Please check your connection!";
//                } else if (error instanceof ServerError) {
//                    message = "The server could not be found. Please try again after some time!!";
//                } else if (error instanceof AuthFailureError) {
//                    message = "Cannot connect to Internet...Please check your connection!";
//                } else if (error instanceof ParseError) {
//                    message = "Parsing error! Please try again after some time!!";
//                } else if (error instanceof NoConnectionError) {
//                    message = "Cannot connect to Internet...Please check your connection!";
//                } else if (error instanceof TimeoutError) {
//                    message = "Connection TimeOut! Please check your internet connection.";
//                }
//
//                showToast(message);
//            }
//        }) {
//
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("client_id", BBSharedPreferenceManager.getClientID("c_id",getApplicationContext()));
//                params.put("image", image);
//                params.put("image_name", "");
//
//                return params;
//            }
//
//        };
//        request.setRetryPolicy(new DefaultRetryPolicy(
//                MY_SOCKET_TIMEOUT_MS,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(request);
//
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ProfileActivity.this,DashBoard.class));
        finish();
    }
}
