package com.zouwx.zouwxaicodemother.service;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 项目下载服务
 *
 * @author Tretar
 */
public interface ProjectDownloadService {
    /**
     * 下载项目为 ZIP 文件
     *
     * @param projectPath 项目路径
     * @param projectName 项目名称
     * @param response    HTTP 响应
     */
    void downloadProjectAsZip(String projectPath, String projectName, HttpServletResponse response);
}
