package com.lz.cloud.auth.client.service;

import com.lz.cloud.auth.client.po.Result;
import org.springframework.security.jwt.Jwt;

/**
 * @Description TODO
 * @author li zhe
 * @createTime 2019-08-25 20:19
 */
public interface AuthServcie {

    /**
     * 从认证信息中提取jwt token 对象
     *
     * @param authentication 认证信息  Authorization: bearer header.payload.signature
     * @return Jwt对象
     */
    Jwt getJwt(String authentication);

    boolean ignoreAuthentication(String url);

    boolean hasPermission(String authentication, String url, String method);

    boolean invalidJwtAccessToken(String authentication);

    boolean hasPermission(Result authResult);

    Result authenticate(String authentication, String url, String method);
}
