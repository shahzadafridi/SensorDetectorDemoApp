package official.com.sensordetector;


import android.app.KeyguardManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class LoginScreen extends AppCompatActivity {

    private KeyStore keyStore;
    private static final String KEY_NAME = "sensor";
    private Cipher cipher;
    private TextView textView;
    private ImageView fingerPrint;
    String TAG = "LoginScreen";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        fingerPrint = (ImageView) findViewById(R.id.fingerPrint);
        textView = (TextView) findViewById(R.id.textView);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

            if (!fingerprintManager.isHardwareDetected()){
                Toast.makeText(this,"Fingerprint authentication permission not enabled",Toast.LENGTH_SHORT).show();
            }else {
                if (!fingerprintManager.hasEnrolledFingerprints()){
                    Toast.makeText(this,"Register at least one fingerprint in Settings",Toast.LENGTH_SHORT).show();
                }else {
                    if (!keyguardManager.isKeyguardSecure()){
                        Toast.makeText(this,"Lock screen security not enabled in Settings",Toast.LENGTH_SHORT).show();
                    }else {
                        getKey();
                        boolean isChiperInit = cipherInit();
                        if (isChiperInit){
                            FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                            FingerPrintHandler handler = new FingerPrintHandler(this);
                            handler.startAuthentication(fingerprintManager,cryptoObject,fingerPrint,textView);
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getKey(){
        try{
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        KeyGenerator keyGenerator = null;
        try{
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,"AndroidKeyStore");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME,KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).
                    setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).build());
            keyGenerator.generateKey();
        } catch (CertificateException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        }
    }

    private boolean cipherInit() {
        try{
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES+"/"+KeyProperties.BLOCK_MODE_CBC+"/"+KeyProperties.ENCRYPTION_PADDING_PKCS7);
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,null);
            cipher.init(Cipher.ENCRYPT_MODE,key);
            return true;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        try{
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,null);
            cipher.init(Cipher.ENCRYPT_MODE,key);
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            Log.e(TAG,ex.getMessage());
            return false;
        } catch (CertificateException ex) {
            ex.printStackTrace();
            Log.e(TAG,ex.getMessage());
            return false;
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            Log.e(TAG,ex.getMessage());
            return false;
        } catch (UnrecoverableKeyException ex) {
            ex.printStackTrace();
            Log.e(TAG,ex.getMessage());
            return false;
        } catch (KeyStoreException ex) {
            ex.printStackTrace();
            Log.e(TAG,ex.getMessage());
            return false;
        } catch (InvalidKeyException ex) {
            ex.printStackTrace();
            Log.e(TAG,ex.getMessage());
            return false;
        }

    }
}
