package dev.mju.jobport.config;

import lombok.extern.slf4j.Slf4j;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.LocalPortForwarder;
import net.schmizz.sshj.connection.channel.direct.Parameters;
import org.springframework.context.SmartLifecycle;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

@Slf4j
public class SshTunnelManager implements SmartLifecycle {

    private final SshTunnelProperties props;
    private volatile boolean running = false;
    private ServerSocket serverSocket;
    private SSHClient sshClient;
    private Thread forwarderThread;

    public SshTunnelManager(SshTunnelProperties props) {
        this.props = props;
    }

    @Override
    public void start() {
        try {
            sshClient = new SSHClient();

            sshClient.loadKnownHosts();

            sshClient.connect(props.getHost(), props.getPort());
            sshClient.authPassword(props.getUsername(), props.getPassword());

            final Parameters params = new Parameters(
                    "127.0.0.1",                                // 로컬 바인드
                    props.getTunnel().getLocalPort(),
                    props.getTunnel().getRemoteHost(),
                    props.getTunnel().getRemotePort()
            );
            serverSocket = new ServerSocket();
            serverSocket.setReuseAddress(true);
            serverSocket.bind(new InetSocketAddress(params.getLocalHost(), params.getLocalPort()));

            LocalPortForwarder forwarder = sshClient.newLocalPortForwarder(params, serverSocket);

            running = true;
            forwarderThread = new Thread(() -> {
                try {
                    // listen()은 블로킹 → 데몬 스레드로 실행
                    forwarder.listen();
                } catch (IOException e) {
                    if (running) {
                        e.printStackTrace();
                    }
                }
            }, "ssh-tunnel-forwarder");
            forwarderThread.setDaemon(true);
            forwarderThread.start();

            log.info("SSH LPF up: 127.0.0.1:{} -> {}:{} via {}:{}",
                    params.getLocalPort(), params.getRemoteHost(), params.getRemotePort(),
                    props.getHost(), props.getPort());

        } catch (IOException e) {
            throw new IllegalStateException("SSH 터널 생성 실패: " + e.getMessage(), e);
        }
    }

    @Override
    public void stop() {
        running = false;
        try { if (serverSocket != null) serverSocket.close(); } catch (IOException ignored) {}
        try { if (sshClient != null) sshClient.close(); } catch (IOException ignored) {}
        if (forwarderThread != null) forwarderThread.interrupt();
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    // 가장 먼저(startup phase가 낮을수록 먼저) 시작되도록
    @Override
    public int getPhase() {
        return Integer.MIN_VALUE;
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }
}
