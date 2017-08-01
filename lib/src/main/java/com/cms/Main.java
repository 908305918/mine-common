package com.cms;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.Base64;

public class Main {

    public static void main(String[] args) {
        String a = read("C:\\Users\\WD\\Desktop\\a.txt");
        long startTime = System.currentTimeMillis();
        String b = compressForGzip(a);
        String c = decompressForGzip(b);
        System.out.println("ѹ����ѹ��" + (System.currentTimeMillis() - startTime));
        System.out.println(c);
    }


    /**
     * Gzip ѹ������
     *
     * @param unGzipStr
     * @return
     */
    public static String compressForGzip(String unGzipStr) {

        if (unGzipStr == null || unGzipStr.length() == 0) {
            return null;
        }
        try {
            long startTime = System.currentTimeMillis();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(baos);
            gzip.write(unGzipStr.getBytes());
            gzip.close();
            byte[] encode = baos.toByteArray();
            baos.flush();
            baos.close();
            System.out.println("ѹ����" + (System.currentTimeMillis() - startTime));
            return Base64.getEncoder().encodeToString(encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gzip��ѹ����
     *
     * @param gzipStr
     * @return
     */
    public static String decompressForGzip(String gzipStr) {
        if (gzipStr == null || gzipStr.length() == 0) {
            return null;
        }
        long startTime = System.currentTimeMillis();
        byte[] t = Base64.getDecoder().decode(gzipStr);
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(t);
            GZIPInputStream gzip = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int n = 0;
            while ((n = gzip.read(buffer, 0, buffer.length)) > 0) {
                out.write(buffer, 0, n);
            }
            gzip.close();
            in.close();
            out.close();
            String result = out.toString();
            System.out.println("��ѹ:" + (System.currentTimeMillis() - startTime));
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String read(String filePath) {
        try {
            String encoding = "UTF-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //�ж��ļ��Ƿ����
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//���ǵ������ʽ
                BufferedReader bufferedReader = new BufferedReader(read);
                String line;
                StringBuffer sb = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                read.close();
                return sb.toString();
            } else {
                System.out.println("�Ҳ���ָ�����ļ�");
            }
        } catch (Exception e) {
            System.out.println("��ȡ�ļ����ݳ���");
            e.printStackTrace();
        }
        return null;
    }
}
