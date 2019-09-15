package com.lz.cloud.auth.client.service.impl;

import com.lz.cloud.auth.client.po.Result;
import com.lz.cloud.auth.client.provider.AuthProvider;
import com.lz.cloud.auth.client.service.AuthServcie;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @Description TODO
 * @author li zhe
 * @createTime 2019-08-25 20:06
 */
@Service
@Slf4j
public class AuthServcieImpl implements AuthServcie {

    @Autowired
    private AuthProvider authProvider;

    /**
     * Authorization认证开头是"bearer "
     */
    private static final int BEARER_BEGIN_INDEX = 7;

    @Value("${spring.security.oauth2.jwt.signingKey}")
    private String signingKey;

    /**
     * 不需要网关签权的url配置(/oauth,/open)
     * 默认/oauth开头是不需要的
     */
    @Value("${gate.ignore.authentication.startWith}")
    private String ignoreUrls = "/oauth";

    private MacSigner verifier;

    @Override
    public Jwt getJwt(String authentication) {
        return JwtHelper.decode(StringUtils.substring(authentication, BEARER_BEGIN_INDEX));
    }

    @Override
    public boolean ignoreAuthentication(String url) {
        return Stream.of(this.ignoreUrls.split(",")).anyMatch(ignoreUrl -> url.startsWith(StringUtils.trim(ignoreUrl)));
    }

    @Override
    public boolean hasPermission(String authentication, String url, String method) {
        if(invalidJwtAccessToken(authentication)){
            return Boolean.FALSE;
        }
        //从认证服务获取是否有权限
//        return hasPermission(authenticate(authentication, url, method));
        return Boolean.TRUE;
    }

    @Override
    public boolean invalidJwtAccessToken(String authentication) {
        verifier = Optional.ofNullable(verifier).orElse(new MacSigner(signingKey));

        //是否无效true表示无效
        boolean invalid = Boolean.TRUE;

        try {
            Jwt jwt = getJwt(authentication);
            jwt.verifySignature(verifier);
            invalid = Boolean.FALSE;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return invalid;
    }

    @Override
    public boolean hasPermission(Result authResult) {
        log.debug("签权结果:", authResult);
        return authResult.isSuccess() && (boolean) authResult.getData();
    }

    @Override
    public Result authenticate(String authentication, String url, String method) {
        return authProvider.auth(authentication, url, method);
    }
}
