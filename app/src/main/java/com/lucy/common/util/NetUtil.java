package com.lucy.common.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

/**
 * Created by YJB on 2017/1/9.
 */

public class NetUtil {
    /**
     * @return
     * @author
     * @category 判断是否有外网连接（普通方法不能判断外网的网络是否连接，比如连接上局域网）
     */
    public static boolean ping() {
        try {
            String ip = "www.baidu.com";// ping 的地址，可以换成任何一种可靠的外网
            Process p = Runtime.getRuntime().exec("ping -c 1 -w 100 " + ip);// ping网址3次
            int status = p.waitFor();
            if (status == 0) {
                return true;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null) {
                return info.isConnectedOrConnecting();
            }
        }
        return false;
    }
}
