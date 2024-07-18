package cn.com.system.controller;


import cn.com.core.util.Result;
import cn.com.system.service.ISysDictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 字典
 *
 * @author dashengz
 * @since 2024-03-10
 */
@RestController
@RequestMapping("/sys-dict")
public class SysDictController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private ISysDictService sysDictService;


    /**
     * 根据类型获取字典
     *
     * @param type
     * @return {@link Result}
     */
    @GetMapping("/getDictByType")
    public Result getDictByType(@RequestParam String type) {
        logger.debug("SysDictController.getDictByType:{}",type);
        return Result.ok(sysDictService.getDictByType(type));
    }

}
