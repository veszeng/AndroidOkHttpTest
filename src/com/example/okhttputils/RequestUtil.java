package com.example.okhttputils;

import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;



class RequestUtil {
    private String mMetyodType;//����ʽ��Ŀǰֻ֧��get��post
    private String mUrl;//�ӿ�
    private Map<String, String> mParamsMap;//��ֵ�����͵Ĳ�����ֻ����һ�����������post��get��
    private String mJsonStr;//json���͵Ĳ�����post��ʽ
    private File mFile;//�ļ��Ĳ�����post��ʽ,ֻ��һ���ļ�
    private  List<File> mfileList;//�ļ����ϣ�������϶�Ӧһ��key����mfileKey
    private  String mfileKey;//�ϴ����������ļ���Ӧ��key
    private  Map<String, File> mfileMap;//�ļ����ϣ�ÿ���ļ���Ӧһ��key
    private String mFileType;//�ļ����͵Ĳ�������fileͬʱ����
    private Map<String, String> mHeaderMap;//ͷ����
    private CallBackUtil mCallBack;//�ص��ӿ�
    private OkHttpClient mOkHttpClient;//OKhttpClient����
    private Request mOkHttpRequest;//�������
    private Request.Builder mRequestBuilder;//�������Ĺ�����


    RequestUtil(String methodType, String url, Map<String, String> paramsMap, Map<String, String> headerMap, CallBackUtil callBack) {
        this(methodType,url,null,null,null,null,null,null,paramsMap,headerMap,callBack);
    }

    RequestUtil(String methodType, String url, String jsonStr, Map<String, String> headerMap, CallBackUtil callBack) {
        this(methodType,url,jsonStr,null,null,null,null,null,null,headerMap,callBack);
    }

    RequestUtil(String methodType, String url, Map<String, String> paramsMap, File file, String fileKey, String fileType, Map<String, String> headerMap, CallBackUtil callBack) {
        this(methodType,url,null,file,null,fileKey,null,fileType,paramsMap,headerMap,callBack);
    }
    RequestUtil(String methodType, String url, Map<String, String> paramsMap, List<File> fileList, String fileKey, String fileType, Map<String, String> headerMap, CallBackUtil callBack) {
        this(methodType,url,null,null,fileList,fileKey,null,fileType,paramsMap,headerMap,callBack);
    }
    RequestUtil(String methodType, String url, Map<String, String> paramsMap, Map<String, File> fileMap, String fileType, Map<String, String> headerMap, CallBackUtil callBack) {
        this(methodType,url,null,null,null,null,fileMap,fileType,paramsMap,headerMap,callBack);
    }

    private RequestUtil(String methodType, String url, String jsonStr ,File file ,List<File> fileList,String fileKey , Map<String,File> fileMap,String fileType,Map<String, String> paramsMap,Map<String, String> headerMap, CallBackUtil callBack) {
        mMetyodType = methodType;
        mUrl = url;
        mJsonStr = jsonStr;
        mFile =file;
        mfileList =fileList;
        mfileKey =fileKey;
        mfileMap =fileMap;
        mFileType =fileType;
        mParamsMap = paramsMap;
        mHeaderMap = headerMap;
        mCallBack = callBack;
        getInstance();
    }


    /**
     * ����OKhttpClientʵ����
     */
    private void getInstance(){
        mOkHttpClient = new OkHttpClient();
        mRequestBuilder = new Request.Builder();
        if(mFile != null || mfileList != null || mfileMap != null){//���ж��Ƿ����ļ���
            setFile();
        }else {
            if(mParamsMap != null){
                setKeyAndVelueParams();
            }
            if(TextUtils.isEmpty(mJsonStr)){
                setPostJson();
            }
        }
        mRequestBuilder.url(mUrl);
        if(mHeaderMap != null){
            setHeader();
        }
        mOkHttpRequest = mRequestBuilder.build();
    }

    /**
     * ֻ��paramsMap��ֵ�Բ���
     */
    private void setKeyAndVelueParams() {
        switch (mMetyodType){
            case "GET":
                setGetKeyAndVelueParams();
                break;
            case "POST":
                setPostKeyAndVelueParams();
                break;
            default:
                break;
        }
    }

    /**
     * get����ֻ�м�ֵ�Բ���
     */
    private void setGetKeyAndVelueParams() {
        if(mParamsMap != null){
            mUrl = mUrl+"?";
            for (String key: mParamsMap.keySet()){
                mUrl = mUrl + key+"="+mParamsMap.get(key)+"&";
            }
            mUrl = mUrl.substring(0,mUrl.length()-1);
        }
    }

    /**
     * post����ֻ�м�ֵ�Բ���
     */
    private void setPostKeyAndVelueParams() {
        if(mParamsMap != null){
            FormBody.Builder formBody = new FormBody.Builder();
            for (String key: mParamsMap.keySet()){
                formBody.add(key,mParamsMap.get(key));
            }
            mRequestBuilder.post(formBody.build());
        }
    }

    /**
     * ֻ��json��ʽ����
     */
    private void setPostJson() {
        if(!TextUtils.isEmpty(mJsonStr)){
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");//��������Ϊjson��ʽ��
            RequestBody body = RequestBody.create(JSON, mJsonStr);//json���ݣ�
            mRequestBuilder.post(body);
        }
    }

    /**
     * �����ϴ��ļ�
     */
    private void setFile() {
        if(mFile != null){//ֻ��һ���ļ�����û���ļ���
            if(mParamsMap == null){
                setPostFile();
            }else {
                setPostParameAndFile();
            }
        }else if(mfileList != null){//�ļ����ϣ�ֻ��һ���ļ������������Ҳ֧�ֵ������ļ������ļ�
            setPostParameAndListFile();
        }else if(mfileMap != null){//����ļ���ÿ���ļ���Ӧһ���ļ���
            setPostParameAndMapFile();
        }

    }

    /**
     * ֻ��һ���ļ������ύ������ʱ����ָ������û�в���
     */
    private void setPostFile() {
        if(mFile != null && mFile.exists()) {
            MediaType fileType = MediaType.parse(mFileType);
            RequestBody body = RequestBody.create(fileType, mFile);//json���ݣ�
            mRequestBuilder.post(new ProgressRequestBody(body,mCallBack));
        }
    }

    /**
     * ֻ��һ���ļ������ύ������ʱ����ָ����������ֵ�Բ���
     */
    private void setPostParameAndFile() {
        if(mParamsMap != null && mFile != null){
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            for (String key: mParamsMap.keySet()){
                builder.addFormDataPart(key,mParamsMap.get(key));
            }
            builder.addFormDataPart(mfileKey,mFile.getName(), RequestBody.create(MediaType.parse(mFileType), mFile));
            mRequestBuilder.post(new ProgressRequestBody(builder.build(),mCallBack));
        }
    }

    /**
     * �ļ����ϣ����ܴ��м�ֵ�Բ���
     */
    private void setPostParameAndListFile() {
        if(mfileList != null){
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            if(mParamsMap != null) {
                for (String key : mParamsMap.keySet()) {
                    builder.addFormDataPart(key, mParamsMap.get(key));
                }
            }
            for (File f : mfileList){
                builder.addFormDataPart(mfileKey,f.getName(), RequestBody.create(MediaType.parse(mFileType), f));
            }
            mRequestBuilder.post(builder.build());
        }
    }

    /**
     * �ļ�Map�����ܴ��м�ֵ�Բ���
     */
    private void setPostParameAndMapFile() {
        if(mfileMap != null){
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            if(mParamsMap != null) {
                for (String key : mParamsMap.keySet()) {
                    builder.addFormDataPart(key, mParamsMap.get(key));
                }
            }

            for (String key : mfileMap.keySet()){
                builder.addFormDataPart(key,mfileMap.get(key).getName(), RequestBody.create(MediaType.parse(mFileType), mfileMap.get(key)));
            }
            mRequestBuilder.post(builder.build());
        }
    }


    /**
     * ����ͷ����
     */
    private void setHeader() {
        if(mHeaderMap != null){
            for (String key: mHeaderMap.keySet()){
                mRequestBuilder.addHeader(key,mHeaderMap.get(key));
            }
        }
    }




    void execute(){
        mOkHttpClient.newCall(mOkHttpRequest).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                if(mCallBack != null){
                    mCallBack.onError(call,e);
                }
            }
            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                if(mCallBack != null){
                    mCallBack.onSeccess(call,response);
                }
            }

        });
    }




    /**
     * �Զ���RequestBody�࣬�õ��ļ��ϴ��Ľ���
     */
    private static class ProgressRequestBody extends RequestBody {
        //ʵ�ʵĴ���װ������
        private final RequestBody requestBody;
        //��װ��ɵ�BufferedSink
        private BufferedSink bufferedSink;
        private CallBackUtil callBack;

        ProgressRequestBody(RequestBody requestBody, CallBackUtil callBack) {
            this.requestBody = requestBody;
            this.callBack = callBack;
        }

        /** ��д����ʵ�ʵ���Ӧ���contentType*/
        @Override
        public MediaType contentType() {
            return requestBody.contentType();
        }

        /**��д����ʵ�ʵ���Ӧ���contentLength ��������ļ������ֽ��� */
        @Override
        public long contentLength() throws IOException {
            return requestBody.contentLength();
        }

        /** ��д����д��*/
        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            if (bufferedSink == null) {
                bufferedSink = Okio.buffer(sink(sink));
            }
            requestBody.writeTo(bufferedSink);
            //�������flush���������һ�������ݿ��ܲ��ᱻд��
            bufferedSink.flush();
        }

        /** д�룬�ص����Ƚӿ�*/
        private Sink sink(BufferedSink sink) {
            return new ForwardingSink(sink) {
                //��ǰд���ֽ���
                long bytesWritten = 0L;
                //���ֽڳ��ȣ������ε���contentLength()����
                long contentLength = 0L;
                @Override
                public void write(Buffer source, long byteCount) throws IOException {
                    super.write(source, byteCount);//���������ѭ�����ã�byteCount��ÿ�ε����ϴ����ֽ�����
                    if (contentLength == 0) {
                        //������ֽڳ���
                        contentLength = contentLength();
                    }
                    //���ӵ�ǰд����ֽ���
                    bytesWritten += byteCount;
                    final float progress = bytesWritten*1.0f / contentLength;
                    CallBackUtil.mMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.onProgress(progress,contentLength);
                        }
                    });
                }
            };
        }
    }



}
