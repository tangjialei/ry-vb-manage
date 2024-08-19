package com.street.one.manage.job.adapter;

import com.alibaba.fastjson.JSON;
import com.street.one.manage.common.core.domain.model.AbstractInfo;
import com.street.one.manage.common.utils.JSONUtils;
import com.street.one.manage.job.thirdmode.ThirdConvertInfo;
import com.street.one.manage.job.thirdmode.ZdApiResult;
import com.street.one.manage.middleware.zd.entity.ZdTempReceivingAlarmMainDetailEntity;
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
 * @ClassName: TempReceivingAlarmMainInfoAdapter
 * @Author: tjl
 * @Description: (T-2)天的接处警_案件基本信息 适配器
 * @Date: 2024/7/11 11:32
 * @modified modify person name
 * @Version: 1.0
 */
@Component
public class TempReceivingAlarmMainInfoAdapter extends AbstractJobAdapter {

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
            buildTempReceivingAlarmMainInfo(successInfos,receivingAlarmMainInfoResps);
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
    private void buildTempReceivingAlarmMainInfo(List<AbstractInfo> successInfos, List<ZdReceivingAlarmMainInfoResp> convertDatas){
        if(!CollectionUtils.isEmpty(convertDatas)){
            //构建数据库对象
            for (ZdReceivingAlarmMainInfoResp resp : convertDatas) {
                ZdTempReceivingAlarmMainDetailEntity zdTempReceivingAlarmMainDetailEntity = buildInternalEntity(resp);
                successInfos.add(zdTempReceivingAlarmMainDetailEntity);
            }
        }
    }

    /***
     * 构建内部对象
     * @param resp
     * @return
     */
    private ZdTempReceivingAlarmMainDetailEntity buildInternalEntity(ZdReceivingAlarmMainInfoResp resp){
        ZdTempReceivingAlarmMainDetailEntity zdReceivingAlarmMainDetailEntity = new ZdTempReceivingAlarmMainDetailEntity();
        zdReceivingAlarmMainDetailEntity.setThirdId(resp.getId());
        zdReceivingAlarmMainDetailEntity.setReceivingAlarmInfoId(resp.getJjjlID());
        zdReceivingAlarmMainDetailEntity.setReceivingAlarmMemberName(resp.getJjyxm());
        zdReceivingAlarmMainDetailEntity.setRegisterReceivingAlarmSeq(resp.getLajjxh());
        zdReceivingAlarmMainDetailEntity.setRegisterWayCode(resp.getLafsdm());
        zdReceivingAlarmMainDetailEntity.setRegisterTime(resp.getLasj());
        zdReceivingAlarmMainDetailEntity.setDispatchTime(resp.getCdsj());
        zdReceivingAlarmMainDetailEntity.setIncidentAddr(resp.getAfdz());
        zdReceivingAlarmMainDetailEntity.setIncidentAddrPolice(resp.getAfdz110());
        zdReceivingAlarmMainDetailEntity.setDisasterSite(resp.getZhcs());
        zdReceivingAlarmMainDetailEntity.setDisasterReason(resp.getZhyy());
        zdReceivingAlarmMainDetailEntity.setKeyUnitType(resp.getZddwID());
        zdReceivingAlarmMainDetailEntity.setSubmittedFireeCode(resp.getBsxfjgdm());
        zdReceivingAlarmMainDetailEntity.setDisasterOriginal(resp.getZqlyxfjgdm());
        zdReceivingAlarmMainDetailEntity.setManagerInstitutionNumber(resp.getZgjgbh());
        zdReceivingAlarmMainDetailEntity.setManagerInstitutionName(resp.getZgjgmc());
        zdReceivingAlarmMainDetailEntity.setAdministrativeRegionNumber(resp.getHzqybh());
        zdReceivingAlarmMainDetailEntity.setAdministrativeRegion(resp.getHzqy());
        zdReceivingAlarmMainDetailEntity.setHandleObject(resp.getCzdx());
        zdReceivingAlarmMainDetailEntity.setHandleObjectCode(resp.getCzdxdm());
        zdReceivingAlarmMainDetailEntity.setHandleObjectName(resp.getCzdxjc());
        zdReceivingAlarmMainDetailEntity.setIsElevatedCar(resp.getSfgjpc());
        zdReceivingAlarmMainDetailEntity.setReleaseTime(resp.getXdsj());
        zdReceivingAlarmMainDetailEntity.setReceiveTime(resp.getJssj());
        zdReceivingAlarmMainDetailEntity.setBattleUnitId(resp.getZddwID());
        zdReceivingAlarmMainDetailEntity.setCombatNumber(resp.getZzbh());
        zdReceivingAlarmMainDetailEntity.setBattleUnitPersonNumber(resp.getZdyrs());
        zdReceivingAlarmMainDetailEntity.setChannelFlag(resp.getPdbz());
        zdReceivingAlarmMainDetailEntity.setOutCarNumber(resp.getCczs());
        zdReceivingAlarmMainDetailEntity.setNote(resp.getZysx());
        zdReceivingAlarmMainDetailEntity.setHydropeniaRegion(null != resp.getQsqy() && Boolean.parseBoolean(resp.getQsqy().toString()));
        zdReceivingAlarmMainDetailEntity.setSensitiveRegion(null != resp.getMgqy() && Boolean.parseBoolean(resp.getMgqy().toString()));
        zdReceivingAlarmMainDetailEntity.setSevereColdRegion(null != resp.getYhyx() && Boolean.parseBoolean(resp.getYhyx().toString()));
        zdReceivingAlarmMainDetailEntity.setMajorDangerRegion(null != resp.getZdwxy() && Boolean.parseBoolean(resp.getZdwxy().toString()));
        zdReceivingAlarmMainDetailEntity.setGaleRegion(null != resp.getDfyx() && Boolean.parseBoolean(resp.getDfyx().toString()));
        zdReceivingAlarmMainDetailEntity.setSwRegion(null != resp.getSwqy() && Boolean.parseBoolean(resp.getSwqy().toString()));
        zdReceivingAlarmMainDetailEntity.setKeyPeriod(null != resp.getZdsd() && Boolean.parseBoolean(resp.getZdsd().toString()));
        zdReceivingAlarmMainDetailEntity.setDispatchFlag(null != resp.getTdbz() && Boolean.parseBoolean(resp.getTdbz().toString()));
        zdReceivingAlarmMainDetailEntity.setReachSceneTime(resp.getDdxcsj());
        zdReceivingAlarmMainDetailEntity.setBattleUnfoldTime(resp.getZdzksj());
        zdReceivingAlarmMainDetailEntity.setReceiveCommandTime(resp.getJsmlsj());
        zdReceivingAlarmMainDetailEntity.setReachSceneOutWaterTime(resp.getDccssj());
        zdReceivingAlarmMainDetailEntity.setFireControlTime(resp.getHskzsj());
        zdReceivingAlarmMainDetailEntity.setBasicExtinctionTime(resp.getJbpmsj());
        zdReceivingAlarmMainDetailEntity.setReceiptTime(resp.getHzsj());
        zdReceivingAlarmMainDetailEntity.setRegressionTime(resp.getGdsj());
        zdReceivingAlarmMainDetailEntity.setSmokeSituationCode(resp.getYwqkdm());
        zdReceivingAlarmMainDetailEntity.setWeatherDesc(resp.getQxxxms());
        zdReceivingAlarmMainDetailEntity.setBuildTypeCode(resp.getJzjglxdm());
        zdReceivingAlarmMainDetailEntity.setBurningFloor(resp.getRslc());
        zdReceivingAlarmMainDetailEntity.setBurningArea(resp.getRsmj());
        zdReceivingAlarmMainDetailEntity.setFloorNumber(resp.getLfcs());
        zdReceivingAlarmMainDetailEntity.setProtectArea(resp.getBhmj());
        zdReceivingAlarmMainDetailEntity.setPersonTrappedNumber(resp.getRybks());
        //受伤人数
        zdReceivingAlarmMainDetailEntity.setInjuredPersonNumber(resp.getShoushangrs());
        zdReceivingAlarmMainDetailEntity.setExtricatePersonNumber(resp.getJcrs());
        //疏散人数
        zdReceivingAlarmMainDetailEntity.setEvacuatePersonNumber(resp.getShusanrs());
        zdReceivingAlarmMainDetailEntity.setMassDeathsNumber(resp.getQzswrs());
        zdReceivingAlarmMainDetailEntity.setTroopsInjuredNumber(resp.getBdssrs());
        zdReceivingAlarmMainDetailEntity.setTroopsDeathsNumber(resp.getBdswrs());
        zdReceivingAlarmMainDetailEntity.setDeletePerson(resp.getScry());
        zdReceivingAlarmMainDetailEntity.setDeleteSeats(resp.getSczx());
        zdReceivingAlarmMainDetailEntity.setTransmissionStatus(resp.getCszt());
        zdReceivingAlarmMainDetailEntity.setMergeStatus(resp.getHbzt());
        zdReceivingAlarmMainDetailEntity.setLhtx(resp.getLhtx());
        zdReceivingAlarmMainDetailEntity.setXfbz(resp.getXFBZ());
        zdReceivingAlarmMainDetailEntity.setGisY(resp.getGIS_Y());
        zdReceivingAlarmMainDetailEntity.setGisX(resp.getGIS_X());
        zdReceivingAlarmMainDetailEntity.setCaseNumber(resp.getAjbh());
        zdReceivingAlarmMainDetailEntity.setCaseType(resp.getAjlx());
        zdReceivingAlarmMainDetailEntity.setCaseTypeCode(resp.getAjlxdm());
        zdReceivingAlarmMainDetailEntity.setCaseGrade(resp.getAjdj());
        zdReceivingAlarmMainDetailEntity.setCaseGradeCode(resp.getAjdjdm());
        zdReceivingAlarmMainDetailEntity.setCaseDesc(resp.getAjms());
        zdReceivingAlarmMainDetailEntity.setCaseNature(resp.getAjxz());
        zdReceivingAlarmMainDetailEntity.setCaseNatureCode(resp.getAjxzdm());
        zdReceivingAlarmMainDetailEntity.setCaseStatus(resp.getAjzt());
        zdReceivingAlarmMainDetailEntity.setCaseStatusCode(resp.getAjzdm());
        zdReceivingAlarmMainDetailEntity.setCaseOriginal(resp.getAjly());
        zdReceivingAlarmMainDetailEntity.setCaseOriginalCode(resp.getAjlydm());
        zdReceivingAlarmMainDetailEntity.setJjts(resp.getJJTS());
        zdReceivingAlarmMainDetailEntity.setPropertyLoss(resp.getCcss());
        zdReceivingAlarmMainDetailEntity.setSupplementInfo(resp.getBcxx());
        zdReceivingAlarmMainDetailEntity.setStatus(resp.getJlzt());
        zdReceivingAlarmMainDetailEntity.setReserveCaseNumber(resp.getBlajbh());
        zdReceivingAlarmMainDetailEntity.setSjy(resp.getSjy());
        zdReceivingAlarmMainDetailEntity.setCreateTime(LocalDateTime.now());
        zdReceivingAlarmMainDetailEntity.setUpdateTime(LocalDateTime.now());
        zdReceivingAlarmMainDetailEntity.setRemark(resp.getBz());
        return zdReceivingAlarmMainDetailEntity;
    }
}
