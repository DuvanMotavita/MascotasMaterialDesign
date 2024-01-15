package com.example.dm.mascotas;

import static android.Manifest.permission.CALL_PHONE;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.dm.mascotas.Pet;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailActivity extends AppCompatActivity {
    public static final String PET_KEY = "pet";
    private static final int CALL_PHONE_REQUEST_CODE = 0;
    private String ownerPhoneNumber = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_activity_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Bundle extras = getIntent().getExtras();
       final Pet pet = extras.getParcelable(PET_KEY);

        if (pet != null) {

            TextView petDescription = (TextView) findViewById(R.id.activity_detail_pet_description);
            TextView ownerName = (TextView) findViewById(R.id.activity_detail_owner_name);
            TextView ownerPhoneNumberTextView = (TextView) findViewById(R.id.activity_detail_owner_phone_number);
            ImageView petImage = (ImageView) findViewById(R.id.activity_detail_pet_image);


            petDescription.setText(pet.getDescription());
            ownerName.setText(pet.getOwnerName());
            ownerPhoneNumber = pet.getPhoneNumber();
            ownerPhoneNumberTextView.setText(pet.getPhoneNumber());
            petImage.setImageDrawable(ContextCompat.getDrawable(this, pet.getImageId()));

            CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.activity_detail_collapsing_toolbar);
            collapsingToolbarLayout.setTitle(pet.getName());

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.activity_detail_fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makeCall();
                }
            });
         }
    }

    private void makeCall(){
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel" + ownerPhoneNumber));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                startActivity(intent);
            }else{
                final String[] permissions = new String[]{CALL_PHONE};
                requestPermissions(permissions, CALL_PHONE_REQUEST_CODE);
            }
        }else{
            startActivity(intent);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PHONE_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall();
            } else if (shouldShowRequestPermissionRationale (CALL_PHONE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Hacer llamadas");
                builder.setMessage("Debes aceptar este permiso para hacer llamadas desde la app Mascotas");
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String[] permissions = new String[]{CALL_PHONE};
                        requestPermissions(permissions, CALL_PHONE_REQUEST_CODE);

                    }
                });
                builder.show();
            }
        }
    }
}