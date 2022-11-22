package com.zhqn.quarkus.mybatis.deployment;

import com.zhqn.quarkus.mybatis.runtime.MybatisConfig;
import com.zhqn.quarkus.mybatis.runtime.MybatisRecorder;
import io.quarkus.arc.deployment.SyntheticBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.recording.RecorderContext;
import io.quarkus.runtime.RuntimeValue;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

class MybatisProcessor {

    private static final String FEATURE = "mybatis";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }


    @BuildStep
    @Record(ExecutionTime.STATIC_INIT)
    public void mybatisSetup(MybatisConfig mybatisConfig,
                             BuildProducer<SyntheticBeanBuildItem> beansProducer,
                             MybatisRecorder mybatisRecorder,
                             RecorderContext recorderContext) {
        if (mybatisConfig.dsPath == null || mybatisConfig.dsPath.isEmpty()) {
            return;
        }
        mybatisConfig.dsPath.forEach((ds, path) -> {
            try (FileInputStream fis = new FileInputStream(path)){
                XMLConfigBuilder parser = new XMLConfigBuilder(fis, null, null);
                Configuration config = parser.parse();
//                new Environment.Builder(ds).dataSource().build();
                beansProducer.produce(SyntheticBeanBuildItem.configure(SqlSessionFactory.class).runtimeValue(mybatisRecorder.createSqlSessionFactory(config)).done());
                config.getMapperRegistry().getMappers().forEach(mapper -> {
                    Proxy.newProxyInstance(mapper.getClassLoader(), mapper.getInterfaces(), new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                            return null;
                        }
                    });
                });
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }



}
