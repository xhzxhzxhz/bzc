package com.folkestone.bzcx.controller.front.userlogin.wechatinterface;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
//���JDK�汾��1.8,��ʹ��ԭ��Base64��
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
public class SendMsgUtil {
	 //�����޸�,���ڸ�ʽ����Ȩͷ��,��"X-WSSE"������ֵ
    private static final String WSSE_HEADER_FORMAT = "UsernameToken Username=\"%s\",PasswordDigest=\"%s\",Nonce=\"%s\",Created=\"%s\"";
    //�����޸�,���ڸ�ʽ����Ȩͷ��,��"Authorization"������ֵ
    private static final String AUTH_HEADER_VALUE = "WSSE realm=\"SDP\",profile=\"UsernameToken\",type=\"Appkey\"";
   
    
    
    /**
     * ���Ͷ���
     * @param phone
     * @param vCode
     * @throws Exception
     */
    public static void sendMsg(String phone,String vCode) throws Exception {
        //����,��ο�"����׼��"��ȡ��������,�滻Ϊʵ��ֵ
       /* String url = "https://api.rtc.huaweicloud.com:10443/sms/batchSendSms/v1"; //APP�����ַ+�ӿڷ���URI
        String appKey = "c8RWg3ggEcyd4D3p94bf3Y7x1Ile"; //APP_Key
        String appSecret = "q4Ii87BhST9vcs8wvrzN80SfD7Al"; //APP_Secret
        String sender = "csms12345678"; //���ڶ���ǩ��ͨ���Ż����/�۰�̨����ͨ����
        String templateId = "8ff55eac1d0b478ab3c06c3c6a492300"; //ģ��ID
*/        String url = "https://api.rtc.huaweicloud.com:10443/sms/batchSendSms/v1"; //APP�����ַ+�ӿڷ���URI
        String appKey = "6cXEqQ50gdKtHyVvsjPb4g1pH79K"; //APP_Key
        String appSecret = "H6u3e9wAz40zm14Uv2E6S88Kt4xe"; //APP_Secret
        String sender = "10690549122901595"; //���ڶ���ǩ��ͨ���Ż����/�۰�̨����ͨ����
        String templateId = "c37c434e47484907a89fa1d1ab448727"; //ģ��ID

        //��������,���ڶ��Ź�ע,��templateIdָ����ģ������Ϊͨ��ģ��ʱ��Ч�ұ���,�����������ͨ����,��ģ������һ�µ�ǩ������
        //����/�۰�̨���Ų��ù�ע�ò���
        String signature = "��Ϊ�ƶ��Ų���"; //ǩ������

        //����,ȫ�ֺ����ʽ(����������),ʾ��:+8615123456789,�������֮����Ӣ�Ķ��ŷָ�
        String receiver = "+86"+phone; //���Ž����˺���

        //ѡ��,����״̬������յ�ַ,�Ƽ�ʹ������,Ϊ�ջ��߲����ʾ������״̬����
        String statusCallBack = "";

       
       // String templateParas = "[\"123456\"]"; //ģ�����
        String templateParas = "["+vCode+"]"; //ģ�����
        //����Body,��Я��ǩ������ʱ,signature����null
        String body = buildRequestBody(sender, receiver, templateId, templateParas, statusCallBack, signature);
        if (null == body || body.isEmpty()) {
            System.out.println("body is null.");
            return;
        }

        //����Headers�е�X-WSSE����ֵ
        String wsseHeader = buildWsseHeader(appKey, appSecret);
        if (null == wsseHeader || wsseHeader.isEmpty()) {
            System.out.println("wsse header is null.");
            return;
        }

        DataOutputStream out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        HttpsURLConnection connection = null;
        InputStream is = null;

        //Ϊ��ֹ��HTTPS֤����֤ʧ�����API����ʧ��,��Ҫ�Ⱥ���֤����������
        HostnameVerifier hv = new HostnameVerifier() {

            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        trustAllHttpsCertificates();

        try {
            URL realUrl = new URL(url);
            connection = (HttpsURLConnection) realUrl.openConnection();

            connection.setHostnameVerifier(hv);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(true);
            //���󷽷�
            connection.setRequestMethod("POST");
            //����Headers����
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Authorization", AUTH_HEADER_VALUE);
            connection.setRequestProperty("X-WSSE", wsseHeader);

            connection.connect();
            out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(body); //��������Body����
            out.flush();
            out.close();

            int status = connection.getResponseCode();
            if (200 == status) { //200
                is = connection.getInputStream();
            } else { //400/401
                is = connection.getErrorStream();
            }
            in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = "";
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            System.out.println(result.toString()); //��ӡ��Ӧ��Ϣʵ��
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
                if (null != is) {
                    is.close();
                }
                if (null != in) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    
    	
    }
/*    public static void main(String[] args) throws Exception {

        //����,��ο�"����׼��"��ȡ��������,�滻Ϊʵ��ֵ
        String url = "https://api.rtc.huaweicloud.com:10443/sms/batchSendSms/v1"; //APP�����ַ+�ӿڷ���URI
        String appKey = "c8RWg3ggEcyd4D3p94bf3Y7x1Ile"; //APP_Key
        String appSecret = "q4Ii87BhST9vcs8wvrzN80SfD7Al"; //APP_Secret
        String sender = "csms12345678"; //���ڶ���ǩ��ͨ���Ż����/�۰�̨����ͨ����
        String templateId = "8ff55eac1d0b478ab3c06c3c6a492300"; //ģ��ID
        String url = "https://api.rtc.huaweicloud.com:10443/sms/batchSendSms/v1"; //APP�����ַ+�ӿڷ���URI
        String appKey = "6cXEqQ50gdKtHyVvsjPb4g1pH79K"; //APP_Key
        String appSecret = "H6u3e9wAz40zm14Uv2E6S88Kt4xe"; //APP_Secret
        String sender = "10690549122901595"; //���ڶ���ǩ��ͨ���Ż����/�۰�̨����ͨ����
        String templateId = "c37c434e47484907a89fa1d1ab448727"; //ģ��ID

        //��������,���ڶ��Ź�ע,��templateIdָ����ģ������Ϊͨ��ģ��ʱ��Ч�ұ���,�����������ͨ����,��ģ������һ�µ�ǩ������
        //����/�۰�̨���Ų��ù�ע�ò���
        String signature = "��Ϊ�ƶ��Ų���"; //ǩ������

        //����,ȫ�ֺ����ʽ(����������),ʾ��:+8615123456789,�������֮����Ӣ�Ķ��ŷָ�
        String receiver = "+8615097115518"; //���Ž����˺���

        //ѡ��,����״̬������յ�ַ,�Ƽ�ʹ������,Ϊ�ջ��߲����ʾ������״̬����
        String statusCallBack = "";

       
        String templateParas = "[\"123456\"]"; //ģ�����

        //����Body,��Я��ǩ������ʱ,signature����null
        String body = buildRequestBody(sender, receiver, templateId, templateParas, statusCallBack, signature);
        if (null == body || body.isEmpty()) {
            System.out.println("body is null.");
            return;
        }

        //����Headers�е�X-WSSE����ֵ
        String wsseHeader = buildWsseHeader(appKey, appSecret);
        if (null == wsseHeader || wsseHeader.isEmpty()) {
            System.out.println("wsse header is null.");
            return;
        }

        DataOutputStream out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        HttpsURLConnection connection = null;
        InputStream is = null;

        //Ϊ��ֹ��HTTPS֤����֤ʧ�����API����ʧ��,��Ҫ�Ⱥ���֤����������
        HostnameVerifier hv = new HostnameVerifier() {

            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        trustAllHttpsCertificates();

        try {
            URL realUrl = new URL(url);
            connection = (HttpsURLConnection) realUrl.openConnection();

            connection.setHostnameVerifier(hv);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(true);
            //���󷽷�
            connection.setRequestMethod("POST");
            //����Headers����
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Authorization", AUTH_HEADER_VALUE);
            connection.setRequestProperty("X-WSSE", wsseHeader);

            connection.connect();
            out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(body); //��������Body����
            out.flush();
            out.close();

            int status = connection.getResponseCode();
            if (200 == status) { //200
                is = connection.getInputStream();
            } else { //400/401
                is = connection.getErrorStream();
            }
            in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = "";
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            System.out.println(result.toString()); //��ӡ��Ӧ��Ϣʵ��
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
                if (null != is) {
                    is.close();
                }
                if (null != in) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/

    /**
     * ��������Body��
     * @param sender
     * @param receiver
     * @param templateId
     * @param templateParas
     * @param statusCallBack
     * @param signature | ǩ������,ʹ�ù��ڶ���ͨ��ģ��ʱ��д
     * @return
     */
    static String buildRequestBody(String sender, String receiver, String templateId, String templateParas,
            String statusCallBack, String signature) {
        if (null == sender || null == receiver || null == templateId || sender.isEmpty() || receiver.isEmpty()
                || templateId.isEmpty()) {
            System.out.println("buildRequestBody(): sender, receiver or templateId is null.");
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();

        map.put("from", sender);
        map.put("to", receiver);
        map.put("templateId", templateId);
        if (null != templateParas && !templateParas.isEmpty()) {
            map.put("templateParas", templateParas);
        }
        if (null != statusCallBack && !statusCallBack.isEmpty()) {
            map.put("statusCallback", statusCallBack);
        }
        if (null != signature && !signature.isEmpty()) {
            map.put("signature", signature);
        }

        StringBuilder sb = new StringBuilder();
        String temp = "";

        for (String s : map.keySet()) {
            try {
                temp = URLEncoder.encode(map.get(s), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            sb.append(s).append("=").append(temp).append("&");
        }

        return sb.deleteCharAt(sb.length()-1).toString();
    }

    /**
     * ����X-WSSE����ֵ
     * @param appKey
     * @param appSecret
     * @return
     */
    static String buildWsseHeader(String appKey, String appSecret) {
        if (null == appKey || null == appSecret || appKey.isEmpty() || appSecret.isEmpty()) {
            System.out.println("buildWsseHeader(): appKey or appSecret is null.");
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String time = sdf.format(new Date()); //Created
        String nonce = UUID.randomUUID().toString().replace("-", ""); //Nonce

        MessageDigest md;
        byte[] passwordDigest = null;

        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update((nonce + time + appSecret).getBytes());
            passwordDigest = md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //���JDK�汾��1.8,�����ԭ��Base64��,��ʹ�����´���
        String passwordDigestBase64Str = Base64.getEncoder().encodeToString(passwordDigest); //PasswordDigest
        //���JDK�汾����1.8,������������ṩBase64��,��ʹ�����´���
        //String passwordDigestBase64Str = Base64.encodeBase64String(passwordDigest); //PasswordDigest
        //��passwordDigestBase64Str�а������з�,��ִ�����´����������
        //passwordDigestBase64Str = passwordDigestBase64Str.replaceAll("[\\s*\t\n\r]", "");
        return String.format(WSSE_HEADER_FORMAT, appKey, passwordDigestBase64Str, nonce, time);
    }

    /**
     * ����֤����������
     * @throws Exception
     */
    static void trustAllHttpsCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        return;
                    }
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        return;
                    }
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }
        };
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }
}
