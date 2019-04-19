package com.zscat.mallplus.marking.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.marking.entity.SmsGroup;
import com.zscat.mallplus.marking.service.ISmsGroupService;
import com.zscat.mallplus.utils.CommonResult;
import com.zscat.mallplus.utils.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Slf4j
@RestController
@Api(tags = "SmsGroupController", description = "管理")
@RequestMapping("/marking/SmsGroup")
public class SmsGroupController {
    @Resource
    private ISmsGroupService ISmsGroupService;

    @SysLog(MODULE = "marking", REMARK = "根据条件查询所有列表")
    @ApiOperation("根据条件查询所有列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('marking:SmsGroup:read')")
    public Object getSmsGroupByPage(SmsGroup entity,
                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize
    ) {
        try {
            return new CommonResult().success(ISmsGroupService.page(new Page<SmsGroup>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "marking", REMARK = "保存")
    @ApiOperation("保存")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('marking:SmsGroup:create')")
    public Object saveSmsGroup(@RequestBody SmsGroup entity) {
        try {
            if (ISmsGroupService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "marking", REMARK = "更新")
    @ApiOperation("更新")
    @PutMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('marking:SmsGroup:update')")
    public Object updateSmsGroup(@RequestBody SmsGroup entity) {
        try {
            if (ISmsGroupService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "marking", REMARK = "删除")
    @ApiOperation("删除")
    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('marking:SmsGroup:delete')")
    public Object deleteSmsGroup(@ApiParam("id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("id");
            }
            if (ISmsGroupService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "marking", REMARK = "给分配")
    @ApiOperation("查询明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('marking:SmsGroup:read')")
    public Object getSmsGroupById(@ApiParam("id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("id");
            }
            SmsGroup coupon = ISmsGroupService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除")
    @PreAuthorize("hasAuthority('marking:SmsGroup:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = ISmsGroupService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

}
