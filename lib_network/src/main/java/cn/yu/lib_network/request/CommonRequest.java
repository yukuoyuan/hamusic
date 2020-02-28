package cn.yu.lib_network.request;

import java.io.File;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created on 2020-02-27
 * 这是一个通用请求类
 *
 * @author yukuoyuan
 * @link github https://github.com/yukuoyuan
 */
public final class CommonRequest implements CommonRequestInterface {
    /**
     * 文件类型
     */
    private final MediaType FILE_TYPE = MediaType.parse("application/octet-stream");
    /**
     * json类型
     */
    private final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
    /**
     * 当前类实例对象
     */
    private static CommonRequest mCommonRequest = null;

    private CommonRequest() {
    }

    public static CommonRequest getInstance() {
        synchronized (CommonRequest.class) {
            if (mCommonRequest == null) {
                mCommonRequest = new CommonRequest();
            }
        }
        return mCommonRequest;
    }

    @Override
    public Request createPostFormRequest(RequestParams requestParams) {
        /*
         * 参数不可以为空
         */
        if (requestParams == null) {
            return null;
        }

        /*
         * 创建一个表单,把数据塞进去
         */
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (requestParams.getParams() != null && !requestParams.getParams().isEmpty()) {
            for (Map.Entry<String, String> entry : requestParams.getParams().entrySet()) {
                formBodyBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        /*
         * 创建一个header,把头数据塞进去
         */
        Headers.Builder headersBuilder = new Headers.Builder();
        if (requestParams.getHeaders() != null && !requestParams.getHeaders().isEmpty()) {
            for (Map.Entry<String, String> entry : requestParams.getHeaders().entrySet()) {
                headersBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        /*
         * 构建一个request
         */
        Request.Builder requestBuilder = new Request.Builder()
                .url(requestParams.getUrl())
                .headers(headersBuilder.build())
                .post(formBodyBuilder.build());

        return requestBuilder.build();
    }

    @Override
    public Request createPostJsonRequest(RequestParams requestParams) {
        /*
         * 参数不可以为空
         */
        if (requestParams == null) {
            return null;
        }

        /*
         * 创建一个表单,把数据塞进去
         */
        RequestBody requestBody = RequestBody.create(JSON_TYPE, "");
        /*
         * 创建一个header,把头数据塞进去
         */
        Headers.Builder headersBuilder = new Headers.Builder();
        if (requestParams.getHeaders() != null && !requestParams.getHeaders().isEmpty()) {
            for (Map.Entry<String, String> entry : requestParams.getHeaders().entrySet()) {
                headersBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        /*
         * 构建一个request
         */
        Request.Builder requestBuilder = new Request.Builder()
                .url(requestParams.getUrl())
                .headers(headersBuilder.build())
                .post(requestBody);

        return requestBuilder.build();
    }

    @Override
    public Request createGetRequest(RequestParams requestParams) {
        /*
         * 参数不可以为空
         */
        if (requestParams == null) {
            return null;
        }

        /*
         * 创建一个表单,把数据塞进去
         */
        StringBuilder stringBuilder = new StringBuilder().append(requestParams.getUrl()).append("?");

        if (requestParams.getParams() != null && !requestParams.getParams().isEmpty()) {
            for (Map.Entry<String, String> entry : requestParams.getParams().entrySet()) {
                stringBuilder.append(entry.getKey()).append("=").append(entry.getValue());
            }
        }
        /*
         * 创建一个header,把头数据塞进去
         */
        Headers.Builder headersBuilder = new Headers.Builder();
        if (requestParams.getHeaders() != null && !requestParams.getHeaders().isEmpty()) {
            for (Map.Entry<String, String> entry : requestParams.getHeaders().entrySet()) {
                headersBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        /*
         * 构建一个request
         */
        Request.Builder requestBuilder = new Request.Builder()
                .url(requestParams.getUrl())
                .headers(headersBuilder.build())
                .get();
        return requestBuilder.build();
    }

    @Override
    public Request createRequest(RequestParams requestParams) {
        /*
         * 根据请求的方式创建不同的请求
         */
        RequestMethod requestMethod = requestParams.getRequestMethod();
        switch (requestMethod) {
            case GET:
                return createGetRequest(requestParams);
            case POST:
            default:
                return createPostFormRequest(requestParams);
        }
    }

    @Override
    public Request createMultipartRequest(RequestParams requestParams) {
        /*
         * 参数不可以为空
         */
        if (requestParams == null) {
            return null;
        }
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        /*
         * 设置类型
         */
        multipartBodyBuilder.setType(MultipartBody.FORM);
        if (requestParams.getFileParams() != null && !requestParams.getFileParams().isEmpty()) {
            for (Map.Entry<String, Object> entry : requestParams.getFileParams().entrySet()) {
                /*
                 * 如果是一个文件
                 */
                if (entry.getValue() instanceof File) {
                    multipartBodyBuilder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                            RequestBody.create(FILE_TYPE, (File) entry.getValue()));
                } else if (entry.getValue() instanceof String) {
                    /*
                     * 如果是字符串
                     */
                    multipartBodyBuilder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
                            RequestBody.create(null, (String) entry.getValue()));
                }
            }
        }
        /*
         * 创建一个header,把头数据塞进去
         */
        Headers.Builder headersBuilder = new Headers.Builder();
        if (requestParams.getHeaders() != null && !requestParams.getHeaders().isEmpty()) {
            for (Map.Entry<String, String> entry : requestParams.getHeaders().entrySet()) {
                headersBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        /*
         * 构建一个request
         */
        Request.Builder requestBuilder = new Request.Builder()
                .url(requestParams.getUrl())
                .headers(headersBuilder.build()).post(multipartBodyBuilder.build());
        return requestBuilder.build();
    }
}
