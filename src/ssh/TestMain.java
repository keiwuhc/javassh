package ssh;

public class TestMain {
    public static void main(String[] args) {

        String user = "root";
        String password = "wuqi5678";
        String host = "192.168.129.130";
        int port = 22;
        String command = "cd /opt\r mkdir 哈哈\r ll /opt\r";
        ShellUtil su = new ShellUtil();
        try {
            try {
                su.connect(host,port,user,password);
                String result = su.command(command);
                System.out.println(result);
            } catch (Exception e) {
                System.err.println(e);
            } finally {
                su.disconnect();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
