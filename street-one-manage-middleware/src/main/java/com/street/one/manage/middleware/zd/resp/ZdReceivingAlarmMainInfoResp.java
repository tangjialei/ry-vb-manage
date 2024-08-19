package com.street.one.manage.middleware.zd.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ProjectName: xhxf-new
 * @Package: com.juwei.xhxf.resp
 * @ClassName: ReceivingAlarmMainInfoResp
 * @Author: tjl
 * @Description: 接处警_案件基本信息 总队数据对象(javaBean),使用json转javaBean
 * @Date: 2023/7/14 下午2:42
 * @modified modify person name
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZdReceivingAlarmMainInfoResp implements Serializable {

    private static final long serialVersionUID = 4140681059869799288L;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime sjc;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime hzsj;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime gdsj;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime jbpmsj;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime jsmlsj;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime xdsj;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime scsj;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime jssj;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime zdzksj;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime cdsj;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime lasj;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime dccssj;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime ddxcsj;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime hskzsj;
    private String jjjlID;
    private String hzqy;
    private String ajly;
    private String ywqkdm;
    private String czdx;
    private String JJTS;
    private String id;
    private String lajjxh;
    private String sjy;
    private String bsxfjgdm;
    private String zqlyxfjgdm;
    private String zhcs;
    private String hzqybh;
    private String afdz110;
    private String sfgjpc;
    private String ccss;
    private String zddwID;
    private String bz;
    private String ajzdm;
    private String ajlxdm;
    private String czdxjc;
    private String ajdj;
    private String bcxx;
    private String ajlx;
    private String zgjgbh;
    private String lhtx;
    private String lafsdm;
    private String scry;
    private String zddwlx;
    private String ajbh;
    private String ajms;
    private String ajxz;
    private String rybks;
    private String ajdjdm;
    private String zgjgmc;
    private String zysx;
    private String zzbh;
    private String czdxdm;
    private String qxxxms;
    private String sczx;
    private String blajbh;
    private String zhyy;
    private String afdz;
    private String jzjglxdm;
    private String ajzt;
    private String jjyxm;
    private Integer tdbz;
    private Integer jcrs;
    private Integer ajxzdm;
    private Integer ajlydm;
    private Integer cszt;
    private Integer rslc;
    private String GIS_Y;
    private Integer rsmj;
    private int shoushangrs;
    private Integer mgqy;
    private Integer yhyx;
    private Integer hbzt;
    private Integer zdwxy;
    private Integer zdyrs;
    private Integer jlzt;
    private int qzswrs;
    private Integer dfyx;
    private Integer qsqy;
    private Integer cczs;
    private Integer shusanrs;
    private Integer swqy;
    private Integer pdbz;
    private Integer XFBZ;
    private Integer zdsd;
    private int bdssrs;
    private String GIS_X;
    private int bdswrs;
    private Integer lfcs;
    private Integer bhmj;

}
