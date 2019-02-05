package org.meowcat.iaphooker;

import android.content.Context;
import android.telephony.TelephonyManager;

public class CheckIMSI {
    public static void getOperators()
    {
        String str = ((TelephonyManager)MeowCatApplication.getContext().getSystemService("phone")).getSubscriberId();
        if ((str == null) || (str.equals(""))) {
            MainActivity.freeNum = "10086";
        }
        do
        {
            return;
            if ((str.startsWith("46000")) || (str.startsWith("46002")) || (str.startsWith("46007")) || (str.startsWith("46020")))
            {
                MainActivity.freeNum = "10086";
                return;
            }
            if ((str.startsWith("46001")) || (str.startsWith("46006")))
            {
                MainActivity.freeNum = "10010";
                return;
            }
        } while ((!str.startsWith("46003")) && (!str.startsWith("46005")) && (!str.startsWith("46011")));
        MainActivity.freeNum = "10000";
    }
}
