package ssh;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestTomcat {
    public static void main(String[] args) {

        String user = "root";
        String password = "wuqi5678";
        String host = "192.168.153.131";
        int port = 22;
        String command = "cat /opt/apache-tomcat-8.5.42/conf/tomcat-users.xml";
        ShellUtil su = new ShellUtil();
        try {
            try {
                su.connect(host,port,user,password);
                String result = su.command(command + "\r");
                result = result.replaceAll("\n", "");

                Pattern pattern = Pattern.compile("<tomcat-users.*</tomcat-users>");
                Matcher matcher = pattern.matcher(result);
                if (matcher.find()) {
                    result = matcher.group();
                }

                Pattern pattern1 = Pattern.compile("<!--(.*?)-->");
                Matcher matcher1 = pattern1.matcher(result);
                result = matcher1.replaceAll("");

                Pattern pattern2 = Pattern.compile("<user(.*?)/>");
                Matcher matcher2 = pattern2.matcher(result);
                while(matcher2.find()) {
                    String userpass = matcher2.group();
                    Pattern pattern3 = Pattern.compile("username=\"(.*?)\"");
                    Matcher matcher3 = pattern3.matcher(userpass);
                    if (matcher3.find()) {
                        System.out.println("username:" + matcher3.group(1));
                    }
                    Pattern pattern4 = Pattern.compile("password=\"(.*?)\"");
                    Matcher matcher4 = pattern4.matcher(userpass);
                    if (matcher4.find()) {
                        System.out.println("password1:" + matcher4.group(1));
                    }
                    System.out.println("------------------------");
                }
            } catch (Exception e) {
                System.err.println("采集异常");
                System.err.println(e);
            } finally {
                su.disconnect();
            }
        } catch (Exception e) {
            System.err.println("关闭采集连接异常");
            System.err.println(e);
        }
    }
}
