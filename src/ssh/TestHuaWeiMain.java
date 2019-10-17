package ssh;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestHuaWeiMain {
    public static void main(String[] args) {

        String user = "fuxin";
        String password = "fuxin@1234";
        String host = "10.167.185.247";
        int port = 22;
        String command = "display current-configuration | include local-user .* password";
        ShellUtil su = new ShellUtil();
        try {
            try {
                su.connect(host,port,user,password);
                String result = su.command(command + "\r");
                int commandIndex = result.lastIndexOf(command) + command.length();
                result = result.substring(commandIndex);
                
                int lastindex = result.lastIndexOf("%$%$")+4;
                result = result.substring(0,lastindex);
                String[] lines = result.split("local-user");
                for (int i = 0; i < lines.length; i++) {
					String line = lines[i];
					if (!"".equals(line) && line != null) {
                        String[] userpass = line.split("password");
                        if (userpass != null && userpass.length > 1) {
                            String localuser = userpass[0];
                            String passwordcipher = userpass[1];
                            localuser = localuser.trim();

                            passwordcipher = passwordcipher.replaceAll("cipher", "");
                            passwordcipher = passwordcipher.replaceAll("simple", "");
                            passwordcipher = passwordcipher.trim();
                            System.out.println("-----------");
                            System.out.println(localuser);
                            System.out.println(passwordcipher);
                            System.out.println("-----------");
                        }
                    }
				}
            } catch (Exception e) {
                System.err.println("閲囬泦寮傚父");
                System.err.println(e);
            } finally {
                su.disconnect();
            }
        } catch (Exception e) {
            System.err.println("鍏抽棴閲囬泦杩炴帴寮傚父");
            System.err.println(e);
        }
    }
}
