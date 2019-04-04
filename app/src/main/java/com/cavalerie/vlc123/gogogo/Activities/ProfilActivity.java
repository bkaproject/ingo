package com.cavalerie.vlc123.gogogo.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cavalerie.vlc123.gogogo.R;
import com.cavalerie.vlc123.gogogo.Data.UserProfileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ProfilActivity extends AppCompatActivity {

    private TabLayout tabsprofil;
    private TextView txtPrFirstname, txtPrLastname;
    private UserProfileManager userProfileManager;
    private de.hdodenhof.circleimageview.CircleImageView imgprofil;
    private LinearLayout lnAbout, lnPref, lnHabit, lnAdress;
    private Integer REQUEST_CAMERA=1, SELECT_FILE=0;
    private File photoFile = null, imageFile;

    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        userProfileManager = new UserProfileManager(getApplicationContext());

        //Find ID
        imgprofil = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.imgprofil);
        txtPrFirstname = (TextView) findViewById(R.id.txtPrFirstName);
        txtPrLastname = (TextView) findViewById(R.id.txtPrLastname);
        lnAbout = (LinearLayout) findViewById(R.id.lnAbout);
        lnPref = (LinearLayout) findViewById(R.id.lnPref);
        lnHabit = (LinearLayout) findViewById(R.id.lnhabit);
        lnAdress = (LinearLayout) findViewById(R.id.lnAdress);

        lnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfilActivity.this, UserInformationActivity.class));
            }
        });

        lnPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfilActivity.this, user_PreferenceActivity.class));
            }
        });

        lnAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfilActivity.this, User_AdressActivity.class));
            }
        });

        setprofil();

        imgprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });
    }

    private void setprofil() {
        txtPrFirstname.setText(userProfileManager.getFirstname());
        txtPrLastname.setText(userProfileManager.getLastname());
        imgprofil.setImageBitmap(BitmapFactory.decodeFile(userProfileManager.getProfil()));
    }

    private void SelectImage(){

        final CharSequence[] select = {"Camera", "Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfilActivity.this);
        builder.setTitle("Modifier la photo de profil");

        builder.setItems(select, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (select[i].equals("Camera")) {

                    //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //startActivityForResult(intent, REQUEST_CAMERA);

                    captureImage();

                } else if (select[i].equals("Gallery")) {

                    selectImage();

                } else if (select[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();

    }

    private void captureImage()
    {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
        else
        {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                try {

                    photoFile = createImageFile();
                    displayMessage(getBaseContext(),photoFile.getAbsolutePath());
                    Log.i("chemin",photoFile.getAbsolutePath());

                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(this,
                                "com.cavalerie.vlc123.gogogo.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_CAMERA);
                    }

                } catch (Exception ex) {
                    // Error occurred while creating the File
                    displayMessage(getBaseContext(), "error : " + ex.getMessage().toString());
                }


            }else
            {
                displayMessage(getBaseContext(),"null");
            }
        }

    }

    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");

        if (intent.resolveActivity(getPackageManager()) != null)
        {
            try {

                photoFile = createImageFile();
                displayMessage(getBaseContext(),photoFile.getAbsolutePath());
                Log.i("chemin",photoFile.getAbsolutePath());

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(ProfilActivity.this,
                            "com.cavalerie.vlc123.gogogo.fileprovider",
                            photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(intent, SELECT_FILE);
                }

            } catch (Exception ex) {
                // Error occurred while creating the File
                displayMessage(getBaseContext(), "error : " + ex.getMessage().toString());
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Bundle extras = data.getExtras();
        //Bitmap imageBitmap = (Bitmap) extras.get("data");
        //imageView.setImageBitmap(imageBitmap);

        if(resultCode== Activity.RESULT_OK){

            if(requestCode == REQUEST_CAMERA){

                Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                imgprofil.setImageBitmap(myBitmap);
                userProfileManager.saveImgProfil(photoFile.getAbsolutePath());
                displayMessage(getApplicationContext(), photoFile.getAbsolutePath());

            }else if(requestCode == SELECT_FILE){

                try {
                    Uri selectedImageUri = data.getData();
                    imageFile = new File(getRealPathFromURI(selectedImageUri));

                    displayMessage(this, getRealPathFromURI(selectedImageUri));

                    //copy selected image from app directorie
                    copyFile(imageFile, photoFile);

                    //set selected image
                    Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                    imgprofil.setImageBitmap(bitmap);

                    userProfileManager.saveImgProfil(imageFile.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        else
        {
            displayMessage(getBaseContext(),"Requete annulé");
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        String imageFileName = txtPrFirstname.getText().toString().trim() + txtPrLastname.getText().toString().trim() + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void displayMessage(Context context, String message)
    {
        Toast.makeText(context,message, Toast.LENGTH_LONG).show();
    }

    /*public static void copyFileOrDirectory(String srcDir, String dstDir) {

        try {
            File src = new File(srcDir);
            File dst = new File(dstDir, src.getName());

            if (src.isDirectory()) {

                String files[] = src.list();
                int filesLength = files.length;
                for (int i = 0; i < filesLength; i++) {
                    String src1 = (new File(src, files[i]).getPath());
                    String dst1 = dst.getPath();
                    copyFileOrDirectory(src1, dst1);

                }
            } else {
                copyFile(src, dst);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public static void copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                captureImage();
            }
        }

    }
}
