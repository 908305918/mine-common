package com.lucy.common.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.alipay.sdk.app.PayTask;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

public class AlipayObserver {
	// 商户私钥
	public static final String RSA_PRIVATE = "dsajdasjddadldasjd";

	private Activity mActivity;
	private AlipayListener listener;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			PayResult payResult = new PayResult((String) msg.obj);
			String resultStatus = payResult.getResultStatus();
			boolean success = false;
			// 判断resultStatus 为“9000”则代表支付成功
			if (TextUtils.equals(resultStatus, "9000")) {
				success = true;
			}
			if (listener != null) {
				listener.onAlipayResult(success);
			}
			Log.d("AlipayObserver", payResult.toString());
		};
	};

	public AlipayObserver(Activity activity, AlipayListener listener) {
		this.mActivity = activity;
		this.listener = listener;
	}

	/**
	 * 支付宝支付
	 * 
	 * @param partner
	 *            签约合作者身份ID
	 * @param sellerId
	 *            签约卖家家支付宝账号
	 * @param outTradeNo
	 *            商户网站唯一订单号
	 * @param subject
	 *            商品名称
	 * @param body
	 *            商品详情
	 * @param price
	 *            商品金额
	 * @param notifyUrl
	 *            服务器异 步通知页 面路径
	 */
	public void pay(String partner, String sellerId, String outTradeNo, String subject,
			String body, String price, String notifyUrl) {
		String orderInfo = createOrderInfo(partner, sellerId, outTradeNo, subject, body, price,
				notifyUrl);
		String sign = SignUtils.sign(orderInfo, RSA_PRIVATE);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			sign = "sdsadsasdsjkkkkkkkkksadhskajhdsajkhdkjashdkas";
		}
		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=" + sign + "\"" + getSignType();
		Runnable payRunnable = new Runnable() {
			@Override
			public void run() {
				PayTask alpay = new PayTask(mActivity);
				// 调用支付接口，获取支付结果
				String result = alpay.pay(payInfo);
				Message msg = new Message();
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};
		// 必须异步调用
		new Thread(payRunnable).start();
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	private String createOrderInfo(String partner, String sellerId, String outTradeNo,
			String subject, String body, String price, String notifyUrl) {
		StringBuffer sb = new StringBuffer();
		// 签约合作者身份ID
		sb.append("partner=\"").append(partner).append("\"");
		// 签约卖家支付宝账号
		sb.append("&seller_id=\"").append(sellerId).append("\"");
		// 商户网站唯一订单号
		sb.append("&out_trade_no=\"").append(outTradeNo).append("\"");
		// 商品名称
		sb.append("&subject=\"").append(subject).append("\"");
		// 商品详情
		sb.append("&body=\"").append(body).append("\"");
		// 商品金额
		sb.append("&total_fee=\"").append(price).append("\"");
		// 服务器异步通知页面路径
		sb.append("&notify_url=\"").append(notifyUrl).append("\"");
		// 服务接口名称， 固定值
		sb.append("&service=\"mobile.securitypay.pay\"");
		// 支付类型， 固定值
		sb.append("&payment_type=\"1\"");
		// 参数编码， 固定值
		sb.append("&_input_charset=\"utf-8\"");
		// 设置未付款交易的超时时间
		sb.append("&it_b_pay=\"30m\"");
		return sb.toString();
	}

	private String getSignType() {
		return "&sign_type=\"RSA\"";
	}

	public interface AlipayListener {
		void onAlipayResult(boolean success);
	}

}
