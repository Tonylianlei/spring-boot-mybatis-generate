package com.example.demo.controller.file;

import com.example.demo.config.result.ResultCode;
import com.example.demo.config.result.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.security.provider.MD5;

import java.io.File;
import java.io.IOException;
import java.nio.charset.CoderResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 创建人:连磊
 * 日期: 2019/3/27. 10:44
 * 描述：
 */
@Api(value = "3用户信息查询", description = "用户基本信息操作API", tags = "用户信息查询")
@RestController
public class FileController {

    @ApiOperation("文件上传测试")
    @RequestMapping(value = "/file",method = RequestMethod.POST)
    public ResultMessage fileUpload(@RequestParam("multipartFile") MultipartFile multipartFile){
        //判断当前文件数据是否为空
        if (null == multipartFile){
            return ResultMessage.setResult(ResultCode.RESULT_REQUEST_NULL);
        }
        //获取文件名称
        String originalFilename = multipartFile.getOriginalFilename();
        System.out.println(originalFilename);
        String filePath = "D:\\zeal\\";
        //处理文件，保存到本地
        //重置文件名
        String[] split = originalFilename.split("\\.");
        if (split.length >= 2){
            String uuid = UUID.randomUUID().toString().replace("-","")+split[0];
            uuid = DigestUtils.md5DigestAsHex(uuid.getBytes());
            String fileName = Arrays.asList(split).stream().skip(1).collect(Collectors.joining("."));
            String filePathAndName = filePath+uuid+"."+fileName;
            System.out.println(filePathAndName);
            File file = new File(filePathAndName);
            try {
                multipartFile.transferTo(file);
                System.out.println("*****");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            return ResultMessage.setResult(ResultCode.RESULT_PARAMETER_ERROR);
        }
        return ResultMessage.setResult(ResultCode.RESULT_SUCCESS);
    }

}
