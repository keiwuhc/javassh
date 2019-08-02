package ssh;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

import java.io.*;

public class Shell {

    public static void main(String[] arg) {
        String user = "root";
        String password = "wuqi5678";
        String host = "192.168.129.128";
        int port = 22;
        String command = "cd /opt\r mkdir 哈哈\r ll /opt\r";
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            session.setPassword(password);
            UserInfo ui = new ShellUserInfo();
            session.setUserInfo(ui);
            session.connect(30000);   // making a connection with timeout.
            Channel channel = session.openChannel("shell");
            PipedOutputStream pos = new PipedOutputStream();
            PipedInputStream pis = new PipedInputStream(pos);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            channel.setInputStream(pis);
            channel.setOutputStream(baos);
            channel.connect(3 * 1000);


            pos.write(command.getBytes());
            Thread.sleep(6000);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            BufferedReader reader = new BufferedReader(new InputStreamReader(bais, "UTF-8"));
            String buf = null;
            String result = "###:";
            while ((buf = reader.readLine()) != null) {
                result += new String(buf.getBytes("UTF-8"),"UTF-8") + "\n";
            }
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}