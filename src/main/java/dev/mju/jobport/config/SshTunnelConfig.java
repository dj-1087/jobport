package dev.mju.jobport.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(SshTunnelProperties.class)
public class SshTunnelConfig {

    @Bean(name = "sshTunnelManager", initMethod = "start", destroyMethod = "stop")
    public SshTunnelManager sshTunnelManager(SshTunnelProperties props) {
        return new SshTunnelManager(props);
    }

    @Bean
    @DependsOn("sshTunnelManager")
    public DataSource dataSource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }
}
