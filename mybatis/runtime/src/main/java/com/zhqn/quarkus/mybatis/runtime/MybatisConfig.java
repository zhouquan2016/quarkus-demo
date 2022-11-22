package com.zhqn.quarkus.mybatis.runtime;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

import java.util.Map;

@ConfigRoot( name = "mybatis", phase = ConfigPhase.BUILD_AND_RUN_TIME_FIXED)
public class MybatisConfig {

    /**
     * path
     */
    @ConfigItem
    public Map<String, String> dsPath;
}
