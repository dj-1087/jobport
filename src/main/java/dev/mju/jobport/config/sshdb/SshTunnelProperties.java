package dev.mju.jobport.config.sshdb;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

@Setter
@Getter
@Profile("dev")
@ConfigurationProperties(prefix = "ssh")
public class SshTunnelProperties {
    private String host;
    private int port = 22;
    private String username;
    private String password;

    @Setter
    @Getter
    public static class Tunnel {
        private String remoteHost = "127.0.0.1";
        private int remotePort;
        private int localPort;

    }

    private Tunnel tunnel = new Tunnel();

}