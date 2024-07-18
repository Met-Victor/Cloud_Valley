package cn.com.post.controller;


import cn.com.core.feign.IFeignSystemController;
import cn.com.core.system.po.SysResource;
import cn.com.core.util.AuthUtil;
import cn.com.core.util.FileUtil;
import cn.com.core.util.JwtUser;
import cn.com.core.util.Result;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件上传下载(帖子服务)
 *
 * @author dashengz
 * @since 2024-02-05
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private IFeignSystemController feignSystemController;

    /**
     * 文件上传
     *
     * @param file
     * @return {@link Result}
     */
    @PostMapping("/upload")
    public Result upload(@RequestPart("file") MultipartFile file) {
        logger.debug("FileController.upload");
        JwtUser userInfo = AuthUtil.getUserInfo();
        String fileType = FilenameUtils.getExtension(file.getOriginalFilename());
        String hash = "";
        String name = file.getOriginalFilename();
        try {
            InputStream inputStream = file.getInputStream();
            hash = DigestUtils.sha1Hex(inputStream);
        } catch (IOException e) {
            logger.error("错误：{}", e.getMessage());
        }
        SysResource resource = feignSystemController.getByHashAndName(hash, name);
        if (resource == null) {
            String filePath = FileUtil.upload(file, hash, fileType);
            SysResource sysResource = SysResource
                    .builder()
                    .hash(hash)
                    .name(name)
                    .type(fileType)
                    .size(FileUtil.fileSize(filePath))
                    .path(filePath)
                    .uploader(userInfo.getId())
                    .build();
            resource = feignSystemController.saveResource(sysResource);
            logger.debug("保存的resource：{}", resource);
        }
        return Result.ok(resource);
    }

    /**
     * 文件删除
     *
     * @param id
     * @return {@link Result}
     */
    @DeleteMapping("/delete")
    public Result delete(@RequestParam String id) {
        logger.debug("FileController.delete：{}", id);
        boolean flag = feignSystemController.deleteResource(id);
        return flag ? Result.ok() : Result.error("删除失败！");
    }

}
