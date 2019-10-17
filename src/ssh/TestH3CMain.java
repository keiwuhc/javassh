package ssh;

public class TestH3CMain {
    public static void main(String[] args) {

        String user = "fuxin";
        String password = "fuxin@1234";
        String host = "10.167.185.248";
        int port = 22;
        String command = "display current-configuration | include local-user|^ password";
        ShellUtil su = new ShellUtil();
        try {
            try {
                su.connect(host,port,user,password);
                String result = su.command(command + "\r");

                System.out.println("###############");
                System.out.println(result);
                System.out.println("###############");
                
                int indexof = result.indexOf(command) + command.length();
                result = result.substring(indexof);
                int lastindexof = result.indexOf("---- More ----");
                result = result.substring(0, lastindexof);
                
                String[] lines = result.split("local-user");
                for (int i = 0; i < lines.length; i++) {
					String line = lines[i];
					if (!"".equals(line) && null != line) {
						String[] userpass = line.split("password");
						if (userpass != null && userpass.length > 1) {
							String localuser = userpass[0];
							String passwordsimple = userpass[1];
							localuser = localuser.trim();
							passwordsimple = passwordsimple.replaceAll("simple", "");
							passwordsimple = passwordsimple.replaceAll("cipher", "");
							passwordsimple = passwordsimple.trim();
			                System.out.println("-----------");
			                System.out.println(localuser);
			                System.out.println(passwordsimple);
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
