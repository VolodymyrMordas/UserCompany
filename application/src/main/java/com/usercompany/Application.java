package com.usercompany;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;

import com.applog.LogUtils;
import com.facebook.FacebookSdk;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by volodymyrmordas on 10/13/16.
 */
@ReportsCrashes(
//        formKey = "", // Will not be used
        mailTo = "volodymyrmordas@gmail.com",
//		logcatArguments = { "-t", "1000", "-v", "long", "MainActivity:D", "*:S" },
        customReportContent =
                {
                        ReportField.APP_VERSION_CODE,
                        ReportField.APP_VERSION_NAME,
                        ReportField.ANDROID_VERSION,
                        ReportField.PHONE_MODEL,
                        ReportField.CUSTOM_DATA,
                        ReportField.STACK_TRACE,
                        ReportField.USER_COMMENT
//					,ReportField.LOGCAT
                },
        mode = ReportingInteractionMode.NOTIFICATION
//        resToastText = R.string.crash_toast_text,
//        resDialogText = R.string.crash_toast_text,
//        resDialogIcon = R.drawable.ic_launcher,
//        resDialogTitle = R.string.crash_dialog_title,
//        resDialogCommentPrompt = R.string.crash_dialog_comment_prompt,
//        resDialogOkToast = R.string.crash_dialog_send
)
public class Application extends android.app.Application {
    private static final String TAG = Application.class.getSimpleName();

    private static Application mApplication = null;

    private ApplicationPreferences mAppPreferences = null;

    private ApplicationSpiceManager mSpiceManager;

    private static Gson mGson = null;

    @Override
    public void onCreate() {
//        ACRA.init(this);

        super.onCreate();

        LogUtils.LOGI(TAG, " - onCreate - ");

        mApplication = Application.this;
        mAppPreferences = new ApplicationPreferences(this);

        mSpiceManager = new ApplicationSpiceManager(ApplicationApiService.class);

        FacebookSdk.sdkInitialize(getApplicationContext());
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "net.mycar.location",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                LogUtils.LOGI("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.LOGI(TAG, "ERROR:" + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            LogUtils.LOGI(TAG, "ERROR:" + e.getMessage());
        }

        initGson();
    }

    synchronized public static Application getApplication() {
        return mApplication;
    }

    public ApplicationPreferences getAppPreferences() {
        if (mAppPreferences == null) {
            mAppPreferences = new ApplicationPreferences(this);
        }

        return mAppPreferences;
    }

    synchronized public ApplicationSpiceManager getSpiceManager() {
        return mSpiceManager;
    }

    private void initGson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create();
        gsonBuilder.registerTypeAdapter(Timestamp.class, new JsonDeserializer<Timestamp>() {
            @Override
            public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                long time = Long.parseLong(json.getAsString());

                if (time < 10000000000L) {
                    // Time in second
                    time = time * 1000;
                }

                return new Timestamp(time);
            }
        });

        gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                try {
                    return formatter.parse(json.getAsString());
                } catch (ParseException e) {
                    LogUtils.LOGE(TAG, "Exeption : " + e.getMessage());
                }

                return null;
            }
        });

        mGson = gsonBuilder.create();
    }

    synchronized public Gson getGson() {
        return mGson;
    }
}
