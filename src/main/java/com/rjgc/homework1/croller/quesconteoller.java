package com.rjgc.homework1.croller;


import com.rjgc.homework1.bean.question;
import com.rjgc.homework1.bean.questions;
import com.rjgc.homework1.util.quesutil;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/floor")
public class quesconteoller {

    @RequestMapping(value = "/fileDownLoad2/{floorid}/{level}")
    public ResponseEntity fileDemo(@PathVariable int floorid,@PathVariable int level){
        try {
            quesutil util=new quesutil();
            util.write(util.getques(floorid,level));
            String fileName = this.getClass().getResource("/test.doc").getPath();
            // 获取本地文件系统中的文件资源
            FileSystemResource resource = new FileSystemResource(fileName);
            // 解析文件的 mime 类型
            String mediaTypeStr = URLConnection.getFileNameMap().getContentTypeFor(fileName);
            // 无法判断MIME类型时，作为流类型
            mediaTypeStr = (mediaTypeStr == null) ? MediaType.APPLICATION_OCTET_STREAM_VALUE : mediaTypeStr;
            // 实例化MIME
            MediaType mediaType = MediaType.parseMediaType(mediaTypeStr);
            /*
             * 构造响应的头
             */
            HttpHeaders headers = new HttpHeaders();
            // 下载之后需要在请求头中放置文件名，该文件名按照ISO_8859_1编码。
            String filenames = new String("test.doc".getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            headers.setContentDispositionFormData("attachment", filenames);
            headers.setContentType(mediaType);

            /*
             * 返还资源
             */
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.getInputStream().available())
                    .body(resource);
        } catch (IOException e) {

            return null;
        }
    }

        @GetMapping(value = "/{floorid}/{level}")
        public questions getStudent(@PathVariable int floorid,@PathVariable int level){
            if(level==1000){
                quesutil util = new quesutil();
                return new questions(util.getequation(floorid));
            }else {
                quesutil util = new quesutil();
                System.out.println(floorid);
                return new questions(util.getques(floorid, level));
            }
    }

}
