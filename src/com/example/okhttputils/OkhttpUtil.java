package com.example.okhttputils;

import java.io.File;
import java.util.List;
import java.util.Map;


public class OkhttpUtil {

    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";

    public static final String FILE_TYPE_FILE = "file/*";
    public static final String FILE_TYPE_IMAGE = "image/*";
    public static final String FILE_TYPE_AUDIO = "audio/*";
    public static final String FILE_TYPE_VIDEO = "video/*";


    /**
     * get����
     * @param url��url
     * @param callBack���ص��ӿڣ�onFailure����������ʧ��ʱ���ã�onResponse����������ɹ�����ã�������������ִ����UI�̡߳�
     */
    public static void okHttpGet(String url, CallBackUtil callBack) {
        okHttpGet(url, null, null, callBack);
    }

    /**
     * get���󣬿��Դ��ݲ���
     * @param url��url
     * @param paramsMap��map���ϣ���װ��ֵ�Բ���
     * @param callBack���ص��ӿڣ�onFailure����������ʧ��ʱ���ã�onResponse����������ɹ�����ã�������������ִ����UI�̡߳�
     */
    public static void okHttpGet(String url, Map<String, String> paramsMap, CallBackUtil callBack) {
        okHttpGet(url, paramsMap, null, callBack);
    }

    /**
     * get���󣬿��Դ��ݲ���
     * @param url��url
     * @param paramsMap��map���ϣ���װ��ֵ�Բ���
     * @param headerMap��map���ϣ���װ����ͷ��ֵ��
     * @param callBack���ص��ӿڣ�onFailure����������ʧ��ʱ���ã�onResponse����������ɹ�����ã�������������ִ����UI�̡߳�
     */
    public static void okHttpGet(String url, Map<String, String> paramsMap, Map<String, String> headerMap, CallBackUtil callBack) {
        new RequestUtil(METHOD_GET, url, paramsMap, headerMap, callBack).execute();
    }

    /**
     * post����
     * @param url��url
     * @param callBack���ص��ӿڣ�onFailure����������ʧ��ʱ���ã�onResponse����������ɹ�����ã�������������ִ����UI�̡߳�
     */
    public static void okHttpPost(String url, CallBackUtil callBack) {
        okHttpPost(url, null, callBack);
    }

    /**
     * post���󣬿��Դ��ݲ���
     * @param url��url
     * @param paramsMap��map���ϣ���װ��ֵ�Բ���
     * @param callBack���ص��ӿڣ�onFailure����������ʧ��ʱ���ã�onResponse����������ɹ�����ã�������������ִ����UI�̡߳�
     */
    public static void okHttpPost(String url, Map<String, String> paramsMap, CallBackUtil callBack) {
        okHttpPost(url, paramsMap, null, callBack);
    }

    /**
     * post���󣬿��Դ��ݲ���
     * @param url��url
     * @param paramsMap��map���ϣ���װ��ֵ�Բ���
     * @param headerMap��map���ϣ���װ����ͷ��ֵ��
     * @param callBack���ص��ӿڣ�onFailure����������ʧ��ʱ���ã�onResponse����������ɹ�����ã�������������ִ����UI�̡߳�
     */
    public static void okHttpPost(String url, Map<String, String> paramsMap, Map<String, String> headerMap, CallBackUtil callBack) {
        new RequestUtil(METHOD_POST, url, paramsMap, headerMap, callBack).execute();
    }

    /**
     * post���󣬿��Դ��ݲ���
     * @param url��url
     * @param jsonStr��json��ʽ�ļ�ֵ�Բ���
     * @param callBack���ص��ӿڣ�onFailure����������ʧ��ʱ���ã�onResponse����������ɹ�����ã�������������ִ����UI�̡߳�
     */
    public static void okHttpPostJson(String url, String jsonStr, CallBackUtil callBack) {
        okHttpPostJson(url, jsonStr, null, callBack);
    }

    /**
     * post���󣬿��Դ��ݲ���
     * @param url��url
     * @param jsonStr��json��ʽ�ļ�ֵ�Բ���
     * @param headerMap��map���ϣ���װ����ͷ��ֵ��
     * @param callBack���ص��ӿڣ�onFailure����������ʧ��ʱ���ã�onResponse����������ɹ�����ã�������������ִ����UI�̡߳�
     */
    public static void okHttpPostJson(String url, String jsonStr, Map<String, String> headerMap, CallBackUtil callBack) {
        new RequestUtil(METHOD_POST, url, jsonStr, headerMap, callBack).execute();
    }

    /**
     * post�����ϴ������ļ�
     * @param url��url
     * @param file��File����
     * @param fileKey���ϴ�����ʱfile��Ӧ�ļ�
     * @param fileType��File���ͣ���image��video��audio��file
     * @param callBack���ص��ӿڣ�onFailure����������ʧ��ʱ���ã�onResponse����������ɹ�����ã�������������ִ����UI�̡߳���������дonProgress�������õ��ϴ�����
     */
    public static void okHttpUploadFile(String url, File file,String fileKey, String fileType, CallBackUtil callBack) {
        okHttpUploadFile(url, file, fileKey,fileType, null, callBack);
    }

    /**
     * post�����ϴ������ļ�
     * @param url��url
     * @param file��File����
     * @param fileKey���ϴ�����ʱfile��Ӧ�ļ�
     * @param fileType��File���ͣ���image��video��audio��file
     * @param paramsMap��map���ϣ���װ��ֵ�Բ���
     * @param callBack���ص��ӿڣ�onFailure����������ʧ��ʱ���ã�onResponse����������ɹ�����ã�������������ִ����UI�̡߳���������дonProgress�������õ��ϴ�����
     */
    public static void okHttpUploadFile(String url, File file, String fileKey,String fileType, Map<String, String> paramsMap, CallBackUtil callBack) {
        okHttpUploadFile(url, file,fileKey, fileType, paramsMap, null, callBack);
    }

    /**
     * post�����ϴ������ļ�
     * @param url��url
     * @param file��File����
     * @param fileKey���ϴ�����ʱfile��Ӧ�ļ�
     * @param fileType��File���ͣ���image��video��audio��file
     * @param paramsMap��map���ϣ���װ��ֵ�Բ���
     * @param headerMap��map���ϣ���װ����ͷ��ֵ��
     * @param callBack���ص��ӿڣ�onFailure����������ʧ��ʱ���ã�onResponse����������ɹ�����ã�������������ִ����UI�̡߳���������дonProgress�������õ��ϴ�����
     */
    public static void okHttpUploadFile(String url, File file, String fileKey,String fileType, Map<String, String> paramsMap, Map<String, String> headerMap, CallBackUtil callBack) {
        new RequestUtil(METHOD_POST, url,paramsMap, file, fileKey,fileType,  headerMap, callBack).execute();
    }

    /**
     * post�����ϴ�����ļ�����list���ϵ���ʽ
     * @param url��url
     * @param fileList������Ԫ����File����
     * @param fileKey���ϴ�����ʱfileList��Ӧ�ļ�
     * @param fileType��File���ͣ���image��video��audio��file
     * @param callBack���ص��ӿڣ�onFailure����������ʧ��ʱ���ã�onResponse����������ɹ�����ã�������������ִ����UI�̡߳�
     */
    public static void okHttpUploadListFile(String url, List<File> fileList, String fileKey, String fileType, CallBackUtil callBack) {
        okHttpUploadListFile(url,null, fileList, fileKey, fileType, callBack);
    }

    /**
     * post�����ϴ�����ļ�����list���ϵ���ʽ
     * @param url��url
     * @param fileList������Ԫ����File����
     * @param fileKey���ϴ�����ʱfileList��Ӧ�ļ�
     * @param fileType��File���ͣ���image��video��audio��file
     * @param paramsMap��map���ϣ���װ��ֵ�Բ���
     * @param callBack���ص��ӿڣ�onFailure����������ʧ��ʱ���ã�onResponse����������ɹ�����ã�������������ִ����UI�̡߳�
     */
    public static void okHttpUploadListFile(String url, Map<String, String> paramsMap,List<File> fileList, String fileKey, String fileType,  CallBackUtil callBack) {
        okHttpUploadListFile(url, paramsMap,fileList, fileKey, fileType, null, callBack);
    }

    /**
     * post�����ϴ�����ļ�����list���ϵ���ʽ
     * @param url��url
     * @param fileList������Ԫ����File����
     * @param fileKey���ϴ�����ʱfileList��Ӧ�ļ�
     * @param fileType��File���ͣ���image��video��audio��file
     * @param paramsMap��map���ϣ���װ��ֵ�Բ���
     * @param headerMap��map���ϣ���װ����ͷ��ֵ��
     * @param callBack���ص��ӿڣ�onFailure����������ʧ��ʱ���ã�onResponse����������ɹ�����ã�������������ִ����UI�̡߳�
     */
    public static void okHttpUploadListFile(String url, Map<String, String> paramsMap, List<File> fileList, String fileKey, String fileType, Map<String, String> headerMap, CallBackUtil callBack) {
        new RequestUtil(METHOD_POST, url,  paramsMap,fileList, fileKey, fileType, headerMap, callBack).execute();
    }

    /**
     * post�����ϴ�����ļ�����map���ϵ���ʽ
     * @param url��url
     * @param fileMap������key��File�����Ӧ�ļ�������value��File����
     * @param fileType��File���ͣ���image��video��audio��file
     * @param callBack���ص��ӿڣ�onFailure����������ʧ��ʱ���ã�onResponse����������ɹ�����ã�������������ִ����UI�̡߳�
     */
    public static void okHttpUploadMapFile(String url, Map<String, File> fileMap, String fileType, CallBackUtil callBack) {
        okHttpUploadMapFile(url, fileMap, fileType, null, callBack);
    }

    /**
     * post�����ϴ�����ļ�����map���ϵ���ʽ
     * @param url��url
     * @param fileMap������key��File�����Ӧ�ļ�������value��File����
     * @param fileType��File���ͣ���image��video��audio��file
     * @param paramsMap��map���ϣ���װ��ֵ�Բ���
     * @param callBack���ص��ӿڣ�onFailure����������ʧ��ʱ���ã�onResponse����������ɹ�����ã�������������ִ����UI�̡߳�
     */
    public static void okHttpUploadMapFile(String url, Map<String, File> fileMap, String fileType, Map<String, String> paramsMap, CallBackUtil callBack) {
        okHttpUploadMapFile(url, fileMap, fileType, paramsMap, null, callBack);
    }

    /**
     * post�����ϴ�����ļ�����map���ϵ���ʽ
     * @param url��url
     * @param fileMap������key��File�����Ӧ�ļ�������value��File����
     * @param fileType��File���ͣ���image��video��audio��file
     * @param paramsMap��map���ϣ���װ��ֵ�Բ���
     * @param headerMap��map���ϣ���װ����ͷ��ֵ��
     * @param callBack���ص��ӿڣ�onFailure����������ʧ��ʱ���ã�onResponse����������ɹ�����ã�������������ִ����UI�̡߳�
     */
    public static void okHttpUploadMapFile(String url, Map<String, File> fileMap, String fileType, Map<String, String> paramsMap, Map<String, String> headerMap, CallBackUtil callBack) {
        new RequestUtil(METHOD_POST, url,paramsMap, fileMap, fileType,  headerMap, callBack).execute();
    }

    /**
     * �����ļ�,��������
     */
    public static void okHttpDownloadFile(String url,CallBackUtil.CallBackFile callBack) {
        okHttpDownloadFile(url,null,callBack);
    }

    /**
     * �����ļ�,������
     */
    public static void okHttpDownloadFile(String url,Map<String, String> paramsMap,  CallBackUtil.CallBackFile callBack) {
        okHttpGet(url, paramsMap, null, callBack);
    }
    /**
     * ����ͼƬ
     */
    public static void okHttpGetBitmap(String url, CallBackUtil.CallBackBitmap callBack) {
        okHttpGetBitmap(url, null, callBack);
    }
    /**
     * ����ͼƬ��������
     */
    public static void okHttpGetBitmap(String url,Map<String, String> paramsMap,  CallBackUtil.CallBackBitmap callBack) {
        okHttpGet(url, paramsMap, null, callBack);
    }


}
