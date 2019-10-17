package ssh;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestMain {
    public static void main(String[] args) {

        String user = "root";
        String password = "wuqi5678";
        String host = "192.168.153.131";
        int port = 22;
        String command = "grep -v -e '!' -e '*' /etc/shadow|awk -F \":\" '{print \"HASH=\"$1\",\"$2\",\"$3\";\"}'";
        ShellUtil su = new ShellUtil();
        try {
            try {
                su.connect(host,port,user,password);
                String result = su.command(command + "\r");
                result = result.replaceAll("\n", "");
                int index = result.lastIndexOf(command) + command.length();
                result = result.substring(index);

                Pattern pattern = Pattern.compile("\\[.*@.*\\]");
                Matcher matcher = pattern.matcher(result);
                if (matcher.find()) {
                    int lastindex = result.indexOf(matcher.group());
                    result = result.substring(0, lastindex);
                    result = result.replaceAll("HASH=", "");

                    String[] users = result.split(";");
                    if (users != null) {
                        for (int i = 0; i < users.length; i++) {
                            String[] userpass = users[i].split(",");
                            System.out.println("username:" + userpass[0]);
                            System.out.println("password:" + userpass[1]);
                        }
                    }
                    System.out.println("-----------");
                    System.out.println(result);
                } else {
                    System.out.println("no match");
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
