package com.lucy.common.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PhoneSimUtil {

    public static List<SimInfo> getSimInfo(Context context) {
        //SubscriptionManager
        Uri uri = Uri.parse("content://telephony/siminfo");
        ContentResolver contentResolver = context.getApplicationContext().getContentResolver();
        Cursor cursor = contentResolver.query(uri,
                new String[]{"_id", "sim_id", "icc_id", "display_name", "carrier_name"},
                "sim_id > -1", new String[]{}, "sim_id asc");//"sim_id asc"
        List<SimInfo> list = new ArrayList<>();
        if (null != cursor) {
            while (cursor.moveToNext()) {
                SimInfo simInfo = new SimInfo();
                simInfo.mIccId = cursor.getString(cursor.getColumnIndex("icc_id"));
                simInfo.mDisplayName = cursor.getString(cursor.getColumnIndex("display_name"));
                simInfo.mCarrierName = cursor.getString(cursor.getColumnIndex("carrier_name"));
                simInfo.mSimSlotIndex = cursor.getInt(cursor.getColumnIndex("sim_id"));
                simInfo.mSubId = cursor.getInt(cursor.getColumnIndex("_id"));
                simInfo.mImsi = getSimImsi(context, simInfo.mSubId);
                Log.e("SimInfo", simInfo.toString());
                list.add(simInfo);
            }
            cursor.close();
        }
        return list;
    }


    /**
     * 拨打电话
     *
     * @param context
     * @param phone
     * @param slot_index 0、卡1 1、卡2
     */
    public static void callPhoneNumber(Context context, String phone, int slot_index) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        List<SimInfo> list = PhoneSimUtil.getSimInfo(context);
        if (list.size() == 2) {
            intent.putExtra("com.android.phone.extra.slot", slot_index);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    private static String getSimImsi(Context context, int subId) {

        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);// 取得相关系统服务
        Class<?> tmClazz;
        String imsi = null;
        try {
            tmClazz = Class.forName("android.telephony.TelephonyManager");

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Method method = tmClazz.getMethod("getSubscriberId", int.class);
                imsi = (String) method.invoke(telephonyManager, subId);
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
                Method method = tmClazz.getMethod("getSubscriberId", long.class);
                imsi = (String) method.invoke(telephonyManager, (long) subId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return imsi;
    }


    public static class SimInfo {
        public String mIccId; //ICCID是卡的标识，由20位数字组成
        public int mSubId; //数据库主键 也是subscription_id
        public int mSimSlotIndex; //卡槽序号 0表示卡1，1表示卡2
        public String mDisplayName; //显示名。这个一般可以改，但是默认的是读取的运营商的名字，比如：中国移动，中国联通，中国电信
        public String mCarrierName; //运营商名字
        public String mImsi;

        @Override
        public String toString() {
            return "SimInfo{" +
                    "mIccId='" + mIccId + '\'' +
                    ", mSubId=" + mSubId +
                    ", mSimSlotIndex=" + mSimSlotIndex +
                    ", mDisplayName='" + mDisplayName + '\'' +
                    ", mCarrierName='" + mCarrierName + '\'' +
                    ", mImsi='" + mImsi + '\'' +
                    '}';
        }
    }
}
