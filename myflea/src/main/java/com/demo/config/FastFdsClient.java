package com.demo.config;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

@Configuration
@Import(FdfsClientConfig.class)
//解决jmx重复注解bean的问题GlobalCorsConfig
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING )
public class FastFdsClient {
}
