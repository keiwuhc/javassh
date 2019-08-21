package ssh;

public class TestMain {
    public static void main(String[] args) {

        String user = "root";
        String password = "wuqi5678";
        String host = "192.168.129.130";
        int port = 22;
        String command = "grep -v -e '!' -e '*' /etc/shadow|awk -F \":\" '{print \"HASH=\"$1\",\"$2\",\"$3\";\"}'";
        ShellUtil su = new ShellUtil();
        try {
            try {
                su.connect(host,port,user,password);
                String result = su.command(command + "\r");
                result = result.replaceAll("\n", "");
                int index = result.lastIndexOf(command);
                result = result.substring(index + command.length());
                System.out.println("-----------");
                System.out.println(result);
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
