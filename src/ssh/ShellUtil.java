package ssh;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

import java.io.*;
import java.util.Properties;

public class ShellUtil {
    private Session session = null;
    private Channel channel = null;
    private PipedOutputStream pos = null;
    private ByteArrayOutputStream baos = null;
    private PipedInputStream pis = null;

    /**
     * 打开连接
     * @param host
     * @param port
     * @param user
     * @param password
     * @return
     */
    public void connect(String host, int port, String user, String password) throws Exception {
        JSch jsch = new JSch();
        session = jsch.getSession(user, host, port);

        //指令通道方式
//        Properties config = new Properties();
//        config.put("StrictHostKeyChecking","no");
//        session.setConfig(config);

        //密码方式
        session.setPassword(password);
        UserInfo ui = new ShellUserInfo();
        session.setUserInfo(ui);

        session.connect(30000);   // making a connection with timeout.
        channel = session.openChannel("shell");
        pos = new PipedOutputStream();
        pis = new PipedInputStream(pos);
        baos = new ByteArrayOutputStream();
        channel.setInputStream(pis);
        channel.setOutputStream(baos);
        channel.connect(3 * 1000);
    }

    /**
     * 执行命令
     * @param command
     * @return
     */
    public String command(String command) throws Exception {
        pos.write(command.getBytes());
        Thread.sleep(4000);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        BufferedReader reader = new BufferedReader(new InputStreamReader(bais, "UTF-8"));
        String line = null;
        StringBuffer sb = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            sb.append(new String(line.getBytes("UTF-8"),"UTF-8"));
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * 关闭连接
     */
    public void disconnect() throws Exception {
        if (pos != null) {
            pos.close();
        }
        if (baos != null) {
            baos.close();
        }
        if (pis != null) {
            pis.close();
        }
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
    }

}
