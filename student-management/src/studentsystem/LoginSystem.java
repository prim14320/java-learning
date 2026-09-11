package studentsystem;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class LoginSystem {
    private static final String LOGIN = "1";
    private static final String REGISTER = "2";
    private static final String FORGETPASSWORD = "3";
    private static final String EXIT = "4";
    private static ArrayList<User> user = new ArrayList<>();
    //静态代码块初始化用户列表
    static{
        user.add(new User("swd","qwer123456","44030420000101001X","13800000000"));
        user.add(new User("admin","123456","44030420000101002X","13800000001"));
        user.add(new User("user","123456","44030420000101003X","13800000002"));
    }
    public static void main(String[] args) {

        while (true) {
            System.out.println("欢迎来到学生管理系统！");
            System.out.println("请选择你的操作1.登录2.注册3.忘记密码4.退出");
            Scanner sc = new Scanner(System.in);
            String choice = sc.next();
            switch (choice) {
                case LOGIN -> login(user);
                case REGISTER -> register(user);
                case FORGETPASSWORD -> forgetPassword(user);
                case EXIT -> System.exit(0);
                default -> System.out.println("没有该选项");
            }
        }
    }
    private static void forgetPassword(ArrayList<User> user) {
        System.out.println("忘记密码");
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名");
        String username = sc.next();
        boolean flag = checkUsername(user,username);
        if (flag) {
            System.out.println("未注册");
            return;
        }
        User newUser = findUser(user,username);
        System.out.println("请输入身份证号码");
        String personId = sc.next();
        System.out.println("请输入手机号");
        String phoneNumber = sc.next();
        boolean flag1 = newUser.getPhoneNumber().equals(phoneNumber)&&newUser.getPersonId().equals(personId);
        if(!flag1){
            System.out.println("账号信息不匹配，修改失败");
        }
        while (true) {
            System.out.println("输入密码进行修改");
            String newPassword = sc.next();
            System.out.println("请再次输入密码确认");
            String confirmPassword = sc.next();
            if(!newPassword.equals(confirmPassword)){
                System.out.println("两次密码不一样，请重新输入");
                continue;
            }
            newUser.setPassword(newPassword);
            break;
        }
        System.out.println("密码修改成功");


    }
    public static User findUser (ArrayList<User> user, String username){
        int index = getuserIndex(user, username);
        if(index==-1){
            return null;
        }
        return user.get(index);
    }
    public static int getuserIndex (ArrayList<User> user, String username) {
        for (int i = 0; i < user.size(); i++) {
            if (user.get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    private static void register (ArrayList < User > user) {
        System.out.println("注册");
        Scanner sc = new Scanner(System.in);
        User u = new User();
        System.out.println("请输入用户名");
        String newUsername = sc.next();
        boolean flag = checkUsername(user, newUsername);
        if (flag) {
            System.out.println("用户名符合格式");
            u.setUsername(newUsername);
        }
        System.out.println("请输入身份证号");
        String newpersonId = sc.next();
        boolean flag1 = checkPersonId(newpersonId);
        if(flag1){
            u.setPersonId(newpersonId);
        }
        System.out.println("请输入手机号");
        String newPhoneNumber = sc.next();
        boolean flag2 = checkPhoneNumber(newPhoneNumber);
        if(flag2){
            u.setPhoneNumber(newPhoneNumber);
        }
        String newPassword;
        while (true) {
            System.out.println("请输入密码");
            newPassword = sc.next();
            System.out.println("请确认密码");
            String confirmPassword = sc.next();
            if(!newPassword.equals(confirmPassword)){
                System.out.println("两次密码不一致，请重新输入");
                continue;
            }
            break;
        }
        u.setPassword(newPassword);
        user.add(u);
        System.out.println("注册成功");
    }

    private static boolean checkPhoneNumber(String  newPhoneNumber) {
        int len = newPhoneNumber.length();
        if (len != 11) {
            System.out.println("手机号长度必须为11位");
            return false;
        }
        if(newPhoneNumber.charAt(0) == '0') {
            System.out.println("手机号不能以0开头");
            return false;
        }
        for (int i = 0; i < newPhoneNumber.length(); i++) {
            char c = newPhoneNumber.charAt(i);
            if (c < '0' || c > '9') {
                System.out.println("手机号只能包含数字");
                return false;
            }
        }
        return true;

    }

    private static boolean checkPersonId(String newpersonId) {
        int len = newpersonId.length();
        if (len != 18) {
            System.out.println("身份证号长度必须为18位");
            return false;
        }
        if(newpersonId.charAt(0) == '0'){
            System.out.println("身份证号不能以0开头");
            return false;
        }
        for (int i = 0; i < newpersonId.length()-1; i++) {
            char c = newpersonId.charAt(i);
            if (c < '0' || c > '9') {
                System.out.println("身份证号前17位只能包含数字");
                return false;
            }
        }
        char last = newpersonId.charAt(len - 1);
        if((last>='0'&&last<='9')||(last == 'X')||(last == 'x')){
            return true;
        }else{
            System.out.println("身份证号最后一位只能为数字或X或x");
            return false;
        }


    }
    private static boolean checkUsername (ArrayList < User > user, String newUsername) {
        int len = newUsername.length();
        if (!(len >= 3 && len <= 15)) {
            System.out.println("用户名长度必须在3-15之间");
            return false;
        }
        int count = 0;
        for (int i = 0; i < newUsername.length(); i++) {
            char c = newUsername.charAt(i);
            if ((c > 'a' && c < 'z') || (c > 'A' && c < 'Z')) count++;
            if (!((c > 'a' && c < 'z') || (c > 'A' && c < 'Z') || (c > '0' && c < '9'))) {
                System.out.println("用户名只能包含字母、数字");
                return false;
            }
        }
        if (count > 0) {
            for (int i = 0; i < user.size(); i++) {
                if (user.get(i).getUsername().equals(newUsername)) {
                    System.out.println("用户名已存在");
                    return false;
                }
            }
            return true;
        }
        System.out.println("用户名不符合格式");
        return false;
    }
    private static void login (ArrayList < User > user) {
        System.out.println("登录");
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名");
        String username = sc.next();
        System.out.println("请输入密码");
        String password = sc.next();
        if(getuserIndex(user,username) == -1){
            System.out.println("用户名不存在，请注册");
            return;
        }
        String confirmCode = verifyCode();
        System.out.println("验证码为：" + confirmCode);
        System.out.println("请输入验证码");
        while (true) {
            String code = sc.next();

            if(!code.equalsIgnoreCase(confirmCode)){
                System.out.println("验证码错误请重新输入");
                continue;
            }
            break;
        }
        for (int i = 0; i < user.size(); i++) {
            if (!user.get(i).getUsername().equals(username) || !user.get(i).getPassword().equals(password)) {
                System.out.println("用户名或密码错误");
                continue;
            }else{
                System.out.println("登录成功");
                StudentSystem.main( new String[0]);
                return;
            }
        }


    }

    private static String verifyCode() {
        char [] c = new char[52];
        for (int i = 0; i < 26; i++) {
            c[i] = (char) ('a' + i);
            c[i + 26] = (char) ('A' + i);
        }
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(52);
            sb.append(c[index]);
        }
        int num_index = random.nextInt(10);
        sb.append(num_index);
        int swap = random.nextInt(4);
        char temp;
        char temp1 = sb.charAt(swap);
        char temp2 = sb.charAt(4);
        temp = temp1;
        temp1 = temp2;
        temp2= temp;
        return sb.toString();
    }
}