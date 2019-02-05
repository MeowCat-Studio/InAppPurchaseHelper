package org.meowcat.iaphooker;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.os.Environment;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;

public class Tutorial implements IXposedHookLoadPackage
{
    public static boolean isMobileNum(String paramString)
    {
        return !Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$").matcher(paramString).matches();
    }

    public static String readFile(String paramString)
            throws Throwable
    {
        FileInputStream localFileInputStream = new FileInputStream(Environment.getExternalStorageDirectory() + "/Android/IAPHelper.json");
        Object localObject = new byte[100];
        localObject = new String((byte[])localObject, 0, localFileInputStream.read((byte[])localObject), "utf-8");
        localFileInputStream.close();
        return new JSONObject((String)localObject).getString(paramString);
    }

    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam paramLoadPackageParam)
            throws Throwable
    {
        if (readFile("SIMStyle").equals("无卡模式"))
        {
            XposedHelpers.findAndHookMethod(TelephonyManager.class.getName(), paramLoadPackageParam.classLoader, "getSubscriberId", new Object[] { new XC_MethodHook()
            {
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam paramAnonymousMethodHookParam)
                        throws Throwable
                {
                    super.afterHookedMethod(paramAnonymousMethodHookParam);
                    paramAnonymousMethodHookParam.setResult("460001234567890");
                }
            } });
            XposedHelpers.findAndHookMethod(TelephonyManager.class.getName(), paramLoadPackageParam.classLoader, "getSimState", new Object[] { new XC_MethodHook()
            {
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam paramAnonymousMethodHookParam)
                        throws Throwable
                {
                    super.afterHookedMethod(paramAnonymousMethodHookParam);
                    paramAnonymousMethodHookParam.setResult(Integer.valueOf(5));
                }
            } });
            XposedHelpers.findAndHookMethod(TelephonyManager.class.getName(), paramLoadPackageParam.classLoader, "getSimOperator", new Object[] { new XC_MethodHook()
            {
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam paramAnonymousMethodHookParam)
                        throws Throwable
                {
                    super.afterHookedMethod(paramAnonymousMethodHookParam);
                    paramAnonymousMethodHookParam.setResult("46000");
                }
            } });
        }
        XposedHelpers.findAndHookMethod(SmsManager.class.getName(), paramLoadPackageParam.classLoader, "sendTextMessage", new Object[] { String.class, String.class, String.class, PendingIntent.class, PendingIntent.class, new XC_MethodHook()
        {
            public void beforeHookedMethod(XC_MethodHook.MethodHookParam paramAnonymousMethodHookParam)
                    throws Throwable
            {
                if (Tutorial.isMobileNum((String)paramAnonymousMethodHookParam.args[0]))
                {
                    XposedBridge.log(paramAnonymousMethodHookParam.args[0] + ":" + paramAnonymousMethodHookParam.args[2]);
                    paramAnonymousMethodHookParam.args[0] = Tutorial.readFile("freeNum");
                }
            }
        } });
        XposedHelpers.findAndHookMethod(SmsManager.class.getName(), paramLoadPackageParam.classLoader, "sendDataMessage", new Object[] { String.class, String.class, Short.TYPE, [B.class, PendingIntent.class, PendingIntent.class, new XC_MethodHook()
        {
            public void beforeHookedMethod(XC_MethodHook.MethodHookParam paramAnonymousMethodHookParam)
                    throws Throwable
            {
                if (Tutorial.isMobileNum((String)paramAnonymousMethodHookParam.args[0]))
                {
                    XposedBridge.log(paramAnonymousMethodHookParam.args[0] + ":" + paramAnonymousMethodHookParam.args[2]);
                    paramAnonymousMethodHookParam.args[0] = Tutorial.readFile("freeNum");
                }
            }
        } });
        XposedHelpers.findAndHookMethod(SmsManager.class.getName(), paramLoadPackageParam.classLoader, "sendMultipartTextMessage", new Object[] { String.class, String.class, ArrayList.class, ArrayList.class, ArrayList.class, new XC_MethodHook()
        {
            public void beforeHookedMethod(XC_MethodHook.MethodHookParam paramAnonymousMethodHookParam)
                    throws Throwable
            {
                if (Tutorial.isMobileNum((String)paramAnonymousMethodHookParam.args[0]))
                {
                    XposedBridge.log(paramAnonymousMethodHookParam.args[0] + ":" + paramAnonymousMethodHookParam.args[2]);
                    paramAnonymousMethodHookParam.args[0] = Tutorial.readFile("freeNum");
                }
            }
        } });
        XposedHelpers.findAndHookMethod(BroadcastReceiver.class.getName(), paramLoadPackageParam.classLoader, "getResultCode", new Object[] { new XC_MethodHook()
        {
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam paramAnonymousMethodHookParam)
                    throws Throwable
            {
                super.afterHookedMethod(paramAnonymousMethodHookParam);
                paramAnonymousMethodHookParam.setResult(Integer.valueOf(-1));
            }
        } });
    }
}

