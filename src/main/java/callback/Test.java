package callback;

public class Test {
    public static void main(String[] args) {
        System.out.println("启动");
        MyMaster myMaster = new MyMaster();
        myMaster.sendMessage("给我去买饭");
    }
}
