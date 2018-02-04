package com.lbs.nettyserver.handler;


import com.lbs.nettyserver.model.response.common.FileUploadResponse;
import com.lbs.nettyserver.model.sys.MediaSourceConstants;
import com.lbs.nettyserver.properties.SystemPropertyPlaceholderConfigurer;
import com.lbs.nettyserver.utils.sysutils.FileUtil.UploadFileResolver;
import com.lbs.nettyserver.utils.sysutils.PropUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import io.netty.util.CharsetUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static io.netty.buffer.Unpooled.copiedBuffer;
import static io.netty.handler.codec.http.HttpHeaderNames.*;

/**
 * Created by Administrator on 2017/7/28.
 */
@ChannelHandler.Sharable
@Component
public class FileServerHandler extends SimpleChannelInboundHandler<HttpObject> {

//    @Autowired
//    private SystemPropertyPlaceholderConfigurer sysConfigurer;

    private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);

    private String uri = null;

    private HttpRequest request = null;

    private HttpPostRequestDecoder decoder;

    //message、download、upload
    private String type = "upload";

    public static final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final String HTTP_DATE_GMT_TIMEZONE = "GMT";
    public static final int HTTP_CACHE_SECONDS = 60;
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static {
        DiskFileUpload.baseDirectory = System.getProperty("user.dir") + "/upload/";
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {
            request = (HttpRequest) msg;

            uri = sanitizeUri(request.uri());

            if (request.method() == HttpMethod.POST) {
                if (decoder != null) {
                    decoder.cleanFiles();
                    decoder = null;
                }
                try {
                    decoder = new HttpPostRequestDecoder(factory, request);
                } catch (Exception e) {
                    e.printStackTrace();
                    writeResponse(ctx.channel(), HttpResponseStatus.INTERNAL_SERVER_ERROR, e.toString());
                    ctx.channel().close();
                    return;
                }
            }
        }

        if (decoder != null && msg instanceof HttpContent) {
            HttpContent chunk = (HttpContent) msg;

            try {
                decoder.offer(chunk);
            } catch (Exception e) {
                e.printStackTrace();
                writeResponse(ctx.channel(), HttpResponseStatus.INTERNAL_SERVER_ERROR, e.toString());
                ctx.channel().close();
                return;
            }
            Map<String, Object> params;
            try {
                params = readHttpDataChunkByChunk();
            }catch (HttpPostRequestDecoder.NotEnoughDataDecoderException e){
                return;
            }
            if (params == null){
                writeResponse(ctx.channel(), HttpResponseStatus.INTERNAL_SERVER_ERROR, "未指定文件类型..");
                reset();
                return;
            }

            if (chunk instanceof LastHttpContent) {
                writeResponse(ctx.channel(), HttpResponseStatus.OK, params);
                reset();
                return;
            }
        }
    }

    private String sanitizeUri(String uri) {
        try {
            uri = URLDecoder.decode(uri, "UTF-8");
        } catch(UnsupportedEncodingException e) {
            try {
                uri = URLDecoder.decode(uri, "ISO-8859-1");
            } catch(UnsupportedEncodingException e1) {
                throw new Error();
            }
        }

        return uri;
    }

    private void reset() {
        request = null;

        //销毁decoder释放所有的资源
        decoder.destroy();

        decoder = null;
    }

    /**
     * 通过chunk读取request，获取chunk数据
     * @throws IOException
     */
    private Map<String, Object> readHttpDataChunkByChunk() throws HttpPostRequestDecoder.NotEnoughDataDecoderException,IOException
    {
        String filePath;
        String sessionCode = null;
        List<String> paths = new ArrayList<>();
        HashMap<String, Object> params = new HashMap<>();
        try {
            InterfaceHttpData moduleInfo = decoder.getBodyHttpData("mediaSource");
            InterfaceHttpData typeInfo = decoder.getBodyHttpData("mediaType");
            InterfaceHttpData sessionInfo = decoder.getBodyHttpData("sessionCode");
            if (moduleInfo != null && moduleInfo instanceof Attribute && typeInfo != null && typeInfo instanceof Attribute&& sessionInfo != null && sessionInfo instanceof Attribute){
                Attribute typeAttr = (Attribute) typeInfo;
                Attribute moduleAttr = (Attribute) moduleInfo;
                String path = resolveModulePath(moduleAttr.getValue());
                Attribute sessionAttr = (Attribute) sessionInfo;
                sessionCode = sessionAttr.getValue();

                filePath = path + '/' + typeAttr.getValue();
            }else {
                return null;
            }
            while (decoder.hasNext()) {
                InterfaceHttpData data = decoder.next();
                if (data != null) {
                    try {
                        String fp = writeHttpData(data, filePath);
                        if (fp != null){
                            paths.add(fp);
                        }
                    } finally {
                        data.release();
                    }
                }
            }
        } catch (HttpPostRequestDecoder.EndOfDataDecoderException e1) {
            System.out.println("end chunk");
        }
        params.put("sessionCode", sessionCode);
        params.put("paths", paths);
        return params;
    }

    private String resolveModulePath(String value) {
        switch (value){
            case MediaSourceConstants.LIVE_COMMENT:
            case MediaSourceConstants.LIVE_MESSAGE:
                // 现场消息评论
                return PropUtils.getProperty("sysfile.path.live_message");

            case MediaSourceConstants.VOMIT_FREE_MESSAGE:
                // 吐槽消息
                return PropUtils.getProperty("sysfile.path.vomit_freeMessage");

            case MediaSourceConstants.VOMIT_FREE_ATA:
                // 吐槽@消息
                return PropUtils.getProperty("sysfile.path.vomit_freeAta");

            case MediaSourceConstants.VOMIT_SPECIAL_ATA:
                // 吐槽专题@消息
                return PropUtils.getProperty("sysfile.path.vomit_specialAta");

            case MediaSourceConstants.VOMIT_SPECIAL_MESSAGE:
                // 吐槽专题消息
                return PropUtils.getProperty("sysfile.path.vomit_specialMessage");

            case MediaSourceConstants.VOMIT_SPECIAL_THEME:
                // 吐槽专题主题消息
                return PropUtils.getProperty("sysfile.path.vomit_specialThem");

            case MediaSourceConstants.USERS_AVATAR:
                // 用户头像
                return PropUtils.getProperty("sysfile.path.users_avatar");

            case MediaSourceConstants.USERS_BACKIMG:
                // 用户背景图片
                return PropUtils.getProperty("sysfile.path.users_backimg");

            case MediaSourceConstants.USERS_OTHER:
                // 用户其他
                return PropUtils.getProperty("sysfile.path.users_other");
            default:
                return PropUtils.getProperty("sysfile.path.users_other");
        }
    }

    private String writeHttpData(InterfaceHttpData data, String type) throws IOException {
        if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.FileUpload) {
            FileUpload fileUpload = (FileUpload) data;
            if (fileUpload.isCompleted()) {
                UploadFileResolver fileResolver = new UploadFileResolver(fileUpload.getFilename(), type,"/BBSYS");
                String folderPath = fileResolver.folderPath();
                File fp = new File(folderPath);
                if (!fp.exists()){
                    fp.mkdirs();
                }
                fileUpload.renameTo(new File(fileResolver.storagePath()));
                if (!fileResolver.typeCheck()){
                    // todo: 检查文件实际类型与上传指定的类型是否匹配
                }
                return fileResolver.relativePath();
            }
        }
        return null;
//        else if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
////            Attribute attribute = (Attribute) data;
////            if("type".equals(attribute.getName())) {
////                attribute.getValue()
////            }
//        }
    }

    private void writeResponse(Channel channel, HttpResponseStatus httpResponseStatus, Object returnMsg) {
        JSONObject returnJson = new JSONObject();
        Map<String ,Object> retParams;
        List<String> paths;
        String sessionCode;
        if (returnMsg instanceof Map){
            retParams = (Map<String,Object>)returnMsg;
            paths = (List<String>) retParams.get("paths");
            sessionCode = (String) retParams.get("sessionCode");
        }else {
            paths = null;
            sessionCode = null;
        }
        JSONObject head = new JSONObject();
        head.put("sessionCode", sessionCode == null ? "" : sessionCode);
        head.put("resTime", df.format(new Date()));

        FileUploadResponse body = new FileUploadResponse();
        if(httpResponseStatus.code() == HttpResponseStatus.OK.code()) {
            body.setFlag("00");
            body.setCode("200");
            body.setData(paths);
            body.setMsg("文件上传成功");
            body.setSize(paths == null ? 0: paths.size());
        } else if(httpResponseStatus.code() == HttpResponseStatus.INTERNAL_SERVER_ERROR.code()) {
            body.setFlag("01");
            body.setCode("500");
            body.setData(paths);
            body.setMsg((String)returnMsg);
            body.setSize(paths == null ? 0: paths.size());
        }
        returnJson.put("head", head);
        returnJson.put("body", body);
        //将请求响应的内容转换成ChannelBuffer.
        ByteBuf buf = copiedBuffer(returnJson.toString(), CharsetUtil.UTF_8);

        //判断是否关闭请求响应连接
        boolean close = HttpHeaders.Values.CLOSE.equalsIgnoreCase(request.headers().get(CONNECTION))
                || request.protocolVersion().equals(HttpVersion.HTTP_1_0)
                && !HttpHeaders.Values.KEEP_ALIVE.equalsIgnoreCase(request.headers().get(CONNECTION));

        //构建请求响应对象
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, httpResponseStatus, buf);
        response.headers().set(CONTENT_TYPE, "application/json; charset=UTF-8");

        if (!close) {
            //若该请求响应是最后的响应，则在响应头中没有必要添加'Content-Length'
            response.headers().set(CONTENT_LENGTH, buf.readableBytes());
        }

        //发送请求响应
        ChannelFuture future = channel.writeAndFlush(response);
        //发送请求响应操作结束后关闭连接
        if (close) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.getCause().printStackTrace();
        writeResponse(ctx.channel(), HttpResponseStatus.INTERNAL_SERVER_ERROR, "数据文件通过过程中出现异常："+cause.getMessage().toString());
        ctx.channel().close();
    }
}