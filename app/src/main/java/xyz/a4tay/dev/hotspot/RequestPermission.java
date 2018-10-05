package xyz.a4tay.dev.hotspot;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.widget.Toast;

public class RequestPermission extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_permission);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //permission already granted... continue.
            startMapsActivity();
        } else {
            // Permission is missing and must be requested.
            Toast.makeText(this, "You need to give this permission..", 5);
            requestLocationPermission();
        }
    }
    private void startMapsActivity() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // BEGIN_INCLUDE(onRequestPermissionsResult)
        if (requestCode == 123) {
            // Request for camera permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start Maps Activity.
                startMapsActivity();
            } else {
                // Permission request was denied.
                Toast.makeText(this, "You should think again...", 5);
                requestLocationPermission();
            }
        }
        // END_INCLUDE(onRequestPermissionsResult)
    }
    private void requestLocationPermission() {
        // Permission has not been granted and must be requested.
        ActivityCompat.requestPermissions(RequestPermission.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
    }
}
