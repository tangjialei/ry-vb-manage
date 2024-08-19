package com.street.one.manage.common.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @ProjectName: xhxf-street-one-manage
 * @Package: com.street.one.manage.common.resp
 * @ClassName: TokenInfo
 * @Author: tjl
 * @Description: 系统生产的 token
 * @Date: 2024/5/13 16:50
 * @modified modify person name
 * @Version: 1.0
 */
@Data
public class TokenInfoResp implements Serializable {
    private static final long serialVersionUID = 4224470168819626605L;
    /**
     * token
     */
    private String token;

    /**
     * 有效期 时间戳
     */
    private long tokenExp;

}
