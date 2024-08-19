package com.street.one.manage.common.core.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


/**
 * @author tjl
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BasePageResponse<T> implements Serializable {
    private static final long serialVersionUID = -7913118671091497801L;
    private long current;
    private long size;
    private long total;
    private List<T> records;
}
