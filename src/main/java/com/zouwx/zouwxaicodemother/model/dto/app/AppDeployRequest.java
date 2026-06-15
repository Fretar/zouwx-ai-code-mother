package com.zouwx.zouwxaicodemother.model.dto.app;

import lombok.Data;

import java.io.Serializable;

/**
 * 应用部署请求
 * @author Tretar
 */
@Data
public class AppDeployRequest implements Serializable {

    /**
     * 应用 id
     */
    private Long appId;

    private static final long serialVersionUID = 1L;
}
