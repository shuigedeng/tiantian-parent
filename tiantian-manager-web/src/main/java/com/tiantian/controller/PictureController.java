package com.tiantian.controller;

import com.tiantian.common.utils.FastDFSClient;
import com.tiantian.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 戴礼明
 * @create 2018/4/11 11:03
 */
@Controller
public class PictureController {
    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    @RequestMapping(value = "/pic/upload", produces = MediaType.TEXT_PLAIN_VALUE+ ";charset=UTF-8")
    @ResponseBody
    public String uploadPic(MultipartFile uploadFile){
        Map result = new HashMap();
        try{
            String originalFilename = uploadFile.getOriginalFilename();
            String extFilename = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

            FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/client.conf");
            byte[] bytes = uploadFile.getBytes();
            String url = fastDFSClient.uploadFile(bytes, extFilename);

            result.put("error", 0);
            result.put("url", IMAGE_SERVER_URL + url);
        }catch (Exception e){
            e.printStackTrace();
            result.put("error", 1);
            result.put("message", "图片上传失败");
        }
        return JsonUtils.objectToJson(result);
    }
}
