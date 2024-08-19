package com.street.one.manage.job.adapter;

import com.alibaba.fastjson.JSON;
import com.street.one.manage.common.core.domain.model.AbstractInfo;
import com.street.one.manage.common.utils.JSONUtils;
import com.street.one.manage.job.thirdmode.ThirdConvertInfo;
import com.street.one.manage.job.thirdmode.ZdApiResult;
import com.street.one.manage.middleware.zd.entity.ZdReceivingAlarmMainDetailEntity;
import com.street.one.manage.middleware.zd.resp.ZdReceivingAlarmMainInfoResp;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.job.adapter
 * @ClassName: ReceivingAlarmMainInfoAdapter
 * @Author: tjl
 * @Description: 接处警_案件基本信息 适配器
 * @Date: 2024/7/5 14:59
 * @modified modify person name
 * @Version: 1.0
 */
@Component
public class ReceivingAlarmMainInfoAdapter extends AbstractJobAdapter {


    @Override
    public ThirdConvertInfo convertMiddleInfo(ZdApiResult datas) {
        //返回弱引用对象
        List<AbstractInfo> successInfos = new ArrayList<>();
        //数据转换成maps对象
        List<Map<String, Object>> maps = super.sourceDataConvertMaps(datas);
        if(CollectionUtils.isEmpty(maps)){
            return ThirdConvertInfo.success("ReceivingAlarmMainInfo data convert success ",successInfos,new ArrayList<>());
        }
        try{
            //转换成内部对象
            List<ZdReceivingAlarmMainInfoResp> receivingAlarmMainInfoResps = JSONUtils.json2List(JSON.toJSONString(maps), ZdReceivingAlarmMainInfoResp.class);
            //构建对象
            buildReceivingAlarmMainInfo(successInfos,receivingAlarmMainInfoResps);
        }catch (Exception e){
            e.printStackTrace();
            return ThirdConvertInfo.error("接处警_案件基本信息数据转换失败");
        }
        return ThirdConvertInfo.success("success",successInfos,new ArrayList<>());
    }

    /***
     * 构建报错对象
     * @param successInfos 成功的抽象对象
     * @param convertDatas javaBean 对象
     */
    private void buildReceivingAlarmMainInfo(List<AbstractInfo> successInfos, List<ZdReceivingAlarmMainInfoResp> convertDatas){
        if(!CollectionUtils.isEmpty(convertDatas)){
            //构建数据库对象
            for (ZdReceivingAlarmMainInfoResp resp : convertDatas) {
                ZdReceivingAlarmMainDetailEntity zdReceivingAlarmMainDetailsEntity = buildInternalEntity(resp);
                successInfos.add(zdReceivingAlarmMainDetailsEntity);
            }
        }
    }

    /***
     * 构建内部对象
     * @param resp
     * @return
     */
    private ZdReceivingAlarmMainDetailEntity buildInternalEntity(ZdReceivingAlarmMainInfoResp resp){
        ZdReceivingAlarmMainDetailEntity receivingAlarmMainInfo = new ZdReceivingAlarmMainDetailEntity();
        receivingAlarmMainInfo.setThirdId(resp.getId());
        receivingAlarmMainInfo.setReceivingAlarmInfoId(resp.getJjjlID());
        receivingAlarmMainInfo.setReceivingAlarmMemberName(resp.getJjyxm());
        receivingAlarmMainInfo.setRegisterReceivingAlarmSeq(resp.getLajjxh());
        receivingAlarmMainInfo.setRegisterWayCode(resp.getLafsdm());
        receivingAlarmMainInfo.setRegisterTime(resp.getLasj());
        receivingAlarmMainInfo.setDispatchTime(resp.getCdsj());
        receivingAlarmMainInfo.setIncidentAddr(resp.getAfdz());
        receivingAlarmMainInfo.setIncidentAddrPolice(resp.getAfdz110());
        receivingAlarmMainInfo.setDisasterSite(resp.getZhcs());
        receivingAlarmMainInfo.setDisasterReason(resp.getZhyy());
        receivingAlarmMainInfo.setKeyUnitType(resp.getZddwID());
        receivingAlarmMainInfo.setSubmittedFireeCode(resp.getBsxfjgdm());
        receivingAlarmMainInfo.setDisasterOriginal(resp.getZqlyxfjgdm());
        receivingAlarmMainInfo.setManagerInstitutionNumber(resp.getZgjgbh());
        receivingAlarmMainInfo.setManagerInstitutionName(resp.getZgjgmc());
        receivingAlarmMainInfo.setAdministrativeRegionNumber(resp.getHzqybh());
        receivingAlarmMainInfo.setAdministrativeRegion(resp.getHzqy());
        receivingAlarmMainInfo.setHandleObject(resp.getCzdx());
        receivingAlarmMainInfo.setHandleObjectCode(resp.getCzdxdm());
        receivingAlarmMainInfo.setHandleObjectName(resp.getCzdxjc());
        receivingAlarmMainInfo.setIsElevatedCar(resp.getSfgjpc());
        receivingAlarmMainInfo.setReleaseTime(resp.getXdsj());
        receivingAlarmMainInfo.setReceiveTime(resp.getJssj());
        receivingAlarmMainInfo.setBattleUnitId(resp.getZddwID());
        receivingAlarmMainInfo.setCombatNumber(resp.getZzbh());
        receivingAlarmMainInfo.setBattleUnitPersonNumber(resp.getZdyrs());
        receivingAlarmMainInfo.setChannelFlag(resp.getPdbz());
        receivingAlarmMainInfo.setOutCarNumber(resp.getCczs());
        receivingAlarmMainInfo.setNote(resp.getZysx());
        receivingAlarmMainInfo.setHydropeniaRegion(null != resp.getQsqy() && Boolean.parseBoolean(resp.getQsqy().toString()));
        receivingAlarmMainInfo.setSensitiveRegion(null != resp.getMgqy() && Boolean.parseBoolean(resp.getMgqy().toString()));
        receivingAlarmMainInfo.setSevereColdRegion(null != resp.getYhyx() && Boolean.parseBoolean(resp.getYhyx().toString()));
        receivingAlarmMainInfo.setMajorDangerRegion(null != resp.getZdwxy() && Boolean.parseBoolean(resp.getZdwxy().toString()));
        receivingAlarmMainInfo.setGaleRegion(null != resp.getDfyx() && Boolean.parseBoolean(resp.getDfyx().toString()));
        receivingAlarmMainInfo.setSwRegion(null != resp.getSwqy() && Boolean.parseBoolean(resp.getSwqy().toString()));
        receivingAlarmMainInfo.setKeyPeriod(null != resp.getZdsd() && Boolean.parseBoolean(resp.getZdsd().toString()));
        receivingAlarmMainInfo.setDispatchFlag(null != resp.getTdbz() && Boolean.parseBoolean(resp.getTdbz().toString()));
        receivingAlarmMainInfo.setReachSceneTime(resp.getDdxcsj());
        receivingAlarmMainInfo.setBattleUnfoldTime(resp.getZdzksj());
        receivingAlarmMainInfo.setReceiveCommandTime(resp.getJsmlsj());
        receivingAlarmMainInfo.setReachSceneOutWaterTime(resp.getDccssj());
        receivingAlarmMainInfo.setFireControlTime(resp.getHskzsj());
        receivingAlarmMainInfo.setBasicExtinctionTime(resp.getJbpmsj());
        receivingAlarmMainInfo.setReceiptTime(resp.getHzsj());
        receivingAlarmMainInfo.setRegressionTime(resp.getGdsj());
        receivingAlarmMainInfo.setSmokeSituationCode(resp.getYwqkdm());
        receivingAlarmMainInfo.setWeatherDesc(resp.getQxxxms());
        receivingAlarmMainInfo.setBuildTypeCode(resp.getJzjglxdm());
        receivingAlarmMainInfo.setBurningFloor(resp.getRslc());
        receivingAlarmMainInfo.setBurningArea(resp.getRsmj());
        receivingAlarmMainInfo.setFloorNumber(resp.getLfcs());
        receivingAlarmMainInfo.setProtectArea(resp.getBhmj());
        receivingAlarmMainInfo.setPersonTrappedNumber(resp.getRybks());
        //受伤人数
        receivingAlarmMainInfo.setInjuredPersonNumber(resp.getShoushangrs());
        receivingAlarmMainInfo.setExtricatePersonNumber(resp.getJcrs());
        //疏散人数
        receivingAlarmMainInfo.setEvacuatePersonNumber(resp.getShusanrs());
        receivingAlarmMainInfo.setMassDeathsNumber(resp.getQzswrs());
        receivingAlarmMainInfo.setTroopsInjuredNumber(resp.getBdssrs());
        receivingAlarmMainInfo.setTroopsDeathsNumber(resp.getBdswrs());
        receivingAlarmMainInfo.setDeletePerson(resp.getScry());
        receivingAlarmMainInfo.setDeleteSeats(resp.getSczx());
        receivingAlarmMainInfo.setTransmissionStatus(resp.getCszt());
        receivingAlarmMainInfo.setMergeStatus(resp.getHbzt());
        receivingAlarmMainInfo.setLhtx(resp.getLhtx());
        receivingAlarmMainInfo.setXfbz(resp.getXFBZ());
        receivingAlarmMainInfo.setGisY(resp.getGIS_Y());
        receivingAlarmMainInfo.setGisX(resp.getGIS_X());
        receivingAlarmMainInfo.setCaseNumber(resp.getAjbh());
        receivingAlarmMainInfo.setCaseType(resp.getAjlx());
        receivingAlarmMainInfo.setCaseTypeCode(resp.getAjlxdm());
        receivingAlarmMainInfo.setCaseGrade(resp.getAjdj());
        receivingAlarmMainInfo.setCaseGradeCode(resp.getAjdjdm());
        receivingAlarmMainInfo.setCaseDesc(resp.getAjms());
        receivingAlarmMainInfo.setCaseNature(resp.getAjxz());
        receivingAlarmMainInfo.setCaseNatureCode(resp.getAjxzdm());
        receivingAlarmMainInfo.setCaseStatus(resp.getAjzt());
        receivingAlarmMainInfo.setCaseStatusCode(resp.getAjzdm());
        receivingAlarmMainInfo.setCaseOriginal(resp.getAjly());
        receivingAlarmMainInfo.setCaseOriginalCode(resp.getAjlydm());
        receivingAlarmMainInfo.setJjts(resp.getJJTS());
        receivingAlarmMainInfo.setPropertyLoss(resp.getCcss());
        receivingAlarmMainInfo.setSupplementInfo(resp.getBcxx());
        receivingAlarmMainInfo.setStatus(resp.getJlzt());
        receivingAlarmMainInfo.setReserveCaseNumber(resp.getBlajbh());
        receivingAlarmMainInfo.setSjy(resp.getSjy());
        receivingAlarmMainInfo.setCreateTime(LocalDateTime.now());
        receivingAlarmMainInfo.setUpdateTime(LocalDateTime.now());
        receivingAlarmMainInfo.setRemark(resp.getBz());
        return receivingAlarmMainInfo;
    }

}
