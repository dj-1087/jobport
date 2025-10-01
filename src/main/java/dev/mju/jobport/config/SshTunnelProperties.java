package dev.mju.jobport.config;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Parameters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

@ConfigurationProperties(prefix = "ssh")
public class SshTunnelConfig {
    private String host;
    private int port = 22;
    private String username;
    private String password;

    public static class Tunnel {
        private String remoteHost = "127.0.0.1";
        private int remotePort;
        private int localPort;

        // getters/setters
        public String getRemoteHost() { return remoteHost; }
        public void setRemoteHost(String remoteHost) { this.remoteHost = remoteHost; }
        public int getRemotePort() { return remotePort; }
        public void setRemotePort(int remotePort) { this.remotePort = remotePort; }
        public int getLocalPort() { return localPort; }
        public void setLocalPort(int localPort) { this.localPort = localPort; }
    }

    private Tunnel tunnel = new Tunnel();

    // getters/setters
    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }
    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Tunnel getTunnel() { return tunnel; }
    public void setTunnel(Tunnel tunnel) { this.tunnel = tunnel; }
}