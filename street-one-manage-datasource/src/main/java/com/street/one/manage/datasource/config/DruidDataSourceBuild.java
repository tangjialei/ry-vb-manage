package com.street.one.manage.datasource.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.street.one.manage.common.core.domain.model.BaseDefaultDataSourceInfo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.web.config
 * @ClassName: DruidDataSource
 * @Author: tjl
 * @Description: Druid 数据源
 * @Date: 2024/5/30 11:17
 * @modified modify person name
 * @Version: 1.0
 */
public class DruidDataSourceBuild {

    /***
     * 创建Druid 数据源
     * @param info 数据源参数
     * @return
     * @throws Exception
     */
    public static DataSource createDataSource(BaseDefaultDataSourceInfo info) throws Exception {
        String connectUrl;
        String driverName;
        Map<String, Object> props = new HashMap<>();
        switch (info.getType().toLowerCase()) {
            case "oracle":
                connectUrl = "jdbc:oracle:thin:@" + info.getUrl() + ":" + info.getDbname();
                driverName = "oracle.jdbc.OracleDriver";
                //检查池中的连接是否仍可用的 SQL 语句,drui会连接到数据库执行该SQL, 如果正常返回，则表示连接可用，否则表示连接不可用
                props.put(DruidDataSourceFactory.PROP_VALIDATIONQUERY, "select 1 from dual ");
                break;
            case "mssql":
                connectUrl = "jdbc:sqlserver://" + info.getUrl() + "; DatabaseName=" + info.getDbname();
                driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                //检查池中的连接是否仍可用的 SQL 语句,drui会连接到数据库执行该SQL, 如果正常返回，则表示连接可用，否则表示连接不可用
                props.put(DruidDataSourceFactory.PROP_VALIDATIONQUERY, "SELECT 1 ");
                break;
            default:
                connectUrl = "jdbc:mysql://" + info.getUrl() + "/" + info.getDbname()
                        + "?useSSL=false&serverTimezone=Asia/Shanghai&&characterEncoding=utf-8&useUnicode=true&generateSimpleParameterMetadata=true&autoReconnect=true&zeroDateTimeBehavior=convertToNull&rewriteBatchedStatements=true&useOldAliasMetadataBehavior=true";
                driverName = "com.mysql.cj.jdbc.Driver";
                //检查池中的连接是否仍可用的 SQL 语句,drui会连接到数据库执行该SQL, 如果正常返回，则表示连接可用，否则表示连接不可用
                props.put(DruidDataSourceFactory.PROP_VALIDATIONQUERY, "SELECT 1 ");
                break;
        }

        //驱动
        props.put(DruidDataSourceFactory.PROP_DRIVERCLASSNAME, driverName);
        //ip+端口
        props.put(DruidDataSourceFactory.PROP_URL, connectUrl);
        //用户名
        props.put(DruidDataSourceFactory.PROP_USERNAME, info.getUsername());
        //密码
        props.put(DruidDataSourceFactory.PROP_PASSWORD, info.getPassword());
        //初始化大小
        props.put(DruidDataSourceFactory.PROP_INITIALSIZE, info.getInitsize());
        //最多活跃数
        props.put(DruidDataSourceFactory.PROP_MAXACTIVE, info.getMaxactive());
        //最小最小空闲
        props.put(DruidDataSourceFactory.PROP_MINIDLE, "1");
        //最大等待数
        props.put(DruidDataSourceFactory.PROP_MAXWAIT, "3000");
        //检查空闲连接的频率，单位毫秒, 非正整数时表示不进行检查
        props.put(DruidDataSourceFactory.PROP_TIMEBETWEENEVICTIONRUNSMILLIS, String.valueOf(60 * 1000));
        //池中某个连接的空闲时长达到 N 毫秒后, 连接池在下次检查空闲连接时，将回收该连接,要小于防火墙超时设置
        props.put(DruidDataSourceFactory.PROP_MINEVICTABLEIDLETIMEMILLIS, String.valueOf(60 * 1000));
        //当程序请求连接，池在分配连接时，是否先检查该连接是否有效。(高效)
        props.put(DruidDataSourceFactory.PROP_TESTWHILEIDLE, "true");
        //程序 申请 连接时,进行连接有效性检查（低效，影响性能）
        props.put(DruidDataSourceFactory.PROP_TESTONBORROW, "false");
        //	程序 返还 连接时,进行连接有效性检查（低效，影响性能）
        props.put(DruidDataSourceFactory.PROP_TESTONRETURN, "false");
        //事务
        props.put(DruidDataSourceFactory.PROP_DEFAULTTRANSACTIONISOLATION, "READ_COMMITTED");
        return DruidDataSourceFactory.createDataSource(props);

    }
}
