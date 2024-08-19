package com.street.one.manage.job.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.street.one.manage.common.enums.TaskCodeEnum;
import com.street.one.manage.common.exception.TaskException;
import com.street.one.manage.common.utils.DateUtil;
import com.street.one.manage.common.utils.SpringApplicationContext;
import com.street.one.manage.common.utils.StringUtil;
import com.street.one.manage.job.entity.BasisJobEntity;
import com.street.one.manage.job.service.IBasisJobService;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * @ProjectName: xhxf-new
 * @Package: com.juwei.xhxf.common.util
 * @ClassName: TaskTimeUtils
 * @Author: tjl
 * @Description: 查询时间工具类
 * @Date: 2023/7/14 下午6:12
 * @modified modify person name
 * @Version: 1.0
 */
public class TaskTimeUtils {

    private static IBasisJobService basisJobService = SpringApplicationContext.getBean(IBasisJobService.class);

    /***
     * 获取计划任务开始拉取时间
     * @param jobId
     * @return
     */
    public static Date getStartPullTime(Integer jobId) throws TaskException {
        BasisJobEntity jobEntity = basisJobService.getBaseMapper().selectOne(new QueryWrapper<BasisJobEntity>().lambda().eq(BasisJobEntity::getJobId, jobId));
        if (jobEntity == null) {
            throw new TaskException(String.format("定时任务%s未做配置过滤参数！", jobId), TaskCodeEnum.CONFIG_ERROR);
        }
        String filterParams = jobEntity.getFilterParams();
        if(StringUtil.isEmptyOrNull(filterParams)){
            return DateUtil.getNow();
        }
       return  DateUtil.convertToDate(filterParams);
    }

    /***
     * 塞值拉取时间
     * @param jobId 计划任务id
     * @param period 时间段
     * @param type 时间类型 {@link Calendar}类定义的各种常量，年、月、周、日、时、分、秒、毫秒
     * @return
     */
    public static void setTaskPullTime(int jobId, Date actualEndTime,
                                                        Integer period, Integer type) throws TaskException {
        BasisJobEntity jobEntity = basisJobService.getBaseMapper().selectOne(new QueryWrapper<BasisJobEntity>().lambda().eq(BasisJobEntity::getJobId, jobId));
        if (jobEntity == null) {
            throw new TaskException(String.format("定时任务%s未做配置！", jobId), TaskCodeEnum.CONFIG_ERROR);
        }
        String filterParams = jobEntity.getFilterParams();
        if(StringUtil.isEmptyOrNull(filterParams)){
               return;
        }
        Date originDate = DateUtil.convertToDate(filterParams);
        Date adjustTime = DateUtil.adjust(originDate, type, period);
        Date nextStartTime = actualEndTime == null ? adjustTime : actualEndTime;
        jobEntity.setFilterParams(DateUtil.format(nextStartTime));
        jobEntity.setUpdateTime(LocalDateTime.now());
        basisJobService.updateById(jobEntity);
    }

    /***
     * 获取公共的开始时间，结束日期
     */
    public static JSONObject getReqStartEndTime(Integer jobId) throws ParseException, TaskException {
        JSONObject object = JSONUtil.createObj();
        //开始日期
        Date data = getStartPullTime(jobId);
        //结束日期
        Date endPullTime = DateUtil.adjust(DateUtil.toDate(DateUtil.format(data,DateUtil.DEF_DATETIME_FMT),DateUtil.DEF_DATETIME_FMT),  Calendar.DATE, 1);
        object.set("startPullTime",DateUtil.format(data));
        //查询结束时间不能大于当前时间 ，如果大于，取当前时间
        endPullTime = endPullTime.after(DateUtil.getNow())?DateUtil.getNow():endPullTime;
        object.set("endPullTime",DateUtil.format(endPullTime));
        return object;
    }

}
