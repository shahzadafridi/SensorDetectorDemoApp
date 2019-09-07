package official.com.sensordetector;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerPrintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;
    private  ImageView fingerPrint;
    private TextView textView;

    public FingerPrintHandler(Context context){
        this.context = context;
    }

    public void startAuthentication(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject, ImageView fingerPrint, TextView textView){
        this.fingerPrint = fingerPrint;
        this.textView = textView;
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        fingerprintManager.authenticate(cryptoObject,cancellationSignal,0,this,null);
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        Toast.makeText(context,"Fingerprint, Authentication failed!",Toast.LENGTH_SHORT).show();
        fingerPrint.setBackground(context.getResources().getDrawable(R.drawable.ic_fingerprint_red_black_24dp));
        textView.setText("Fingerprint, Authentication Success!");
        textView.setTextColor(context.getResources().getColor(android.R.color.holo_red_light));
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        Toast.makeText(context,"Fingerprint, Authentication Success!",Toast.LENGTH_SHORT).show();
        fingerPrint.setBackground(context.getResources().getDrawable(R.drawable.ic_fingerprint_green_black_24dp));
        textView.setText("Fingerprint, Authentication Success!");
        textView.setTextColor(context.getResources().getColor(android.R.color.holo_green_light));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        },2000);

    }
}
