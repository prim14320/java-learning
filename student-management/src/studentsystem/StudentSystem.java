package studentsystem;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * 简易控制台学生管理系统
 * 功能：添加 / 删除 / 查询 / 修改 / 退出
 */
public class StudentSystem {
    public static void main(String[] args) {
        // 全局唯一的 Scanner，供所有方法使用（避免每个方法都 new 一个）
        Scanner sc = new Scanner(System.in);
        // 用来存储学生对象的集合
        ArrayList<Student> student = new ArrayList<>();

        // 主菜单循环：当 flag 为 true 时持续运行
        loop:
        while (true) {
            System.out.println("-------------学生管理系统------------------");
            System.out.println("1.添加学生");
            System.out.println("2.删除学生");
            System.out.println("3.查询学生");
            System.out.println("4.修改学生");
            System.out.println("5.退出");

            String choice = sc.next();

            switch (choice) {
                case "1" -> addStudent(student, sc);
                case "2" -> deleteStudent(student, sc);
                case "3" -> queryStudent(student);
                case "4" -> updateStudent(student, sc);
                case "5" -> {
                    System.out.println("退出");
                    break loop;   // 跳出外层循环，程序正常结束（不再用 System.exit 强杀）
                }
                default -> System.out.println("没有该选项");
            }
        }
    }

    /**
     * 添加学生：先填完所有信息，最后再 add，避免出现"空壳学生"进列表
     */
    public static void addStudent(ArrayList<Student> student, Scanner sc) {
        System.out.println("添加学生");

        // 先创建空学生对象，等学号验证通过后再填入其他信息
        Student s = new Student();

        // 循环录入学号，直到学号不重复为止
        while (true) {
            System.out.println("请输入学生学号");
            String sid = sc.next();
            if (!checkid(student, sid)) {      // 学号不存在，可以添加
                s.setSid(sid);
                break;
            } else {
                System.out.println("该学号已存在，添加失败");
            }
        }

        // 再录入其他信息
        System.out.println("请输入学生姓名");
        s.setName(sc.next());
        System.out.println("请输入学生年龄");
        s.setAge(sc.nextInt());
        System.out.println("请输入学生地址");
        s.setAddress(sc.next());

        // 所有信息都填好后，最后才加入集合
        student.add(s);
        System.out.println("添加成功");
    }

    /**
     * 删除学生：根据学号找到索引后删除
     */
    public static void deleteStudent(ArrayList<Student> student, Scanner sc) {
        System.out.println("删除学生");
        System.out.println("请输入要删除的学生学号");
        String sid = sc.next();

        int index = findIndex(student, sid);
        if (index >= 0) {
            student.remove(index);
            System.out.println("删除成功");
        } else {
            System.out.println("该学号不存在，删除失败");
        }
    }

    /**
     * 查询学生：列表展示所有学生信息（含空列表保护）
     */
    public static void queryStudent(ArrayList<Student> student) {
        System.out.println("查询学生");
        // 空列表保护：没有学生时直接返回，避免下面 for 循环无意义输出
        if (student.size() == 0) {
            System.out.println("当前无学生信息，请添加后再查询");
            return;
        }

        // 打印表头
        System.out.println("学号\t姓名\t年龄\t住址");
        for (int i = 0; i < student.size(); i++) {
            Student s = student.get(i);
            System.out.println(s.getSid() + "\t" + s.getName() + "\t" + s.getAge() + "\t" + s.getAddress());
        }
    }

    /**
     * 修改学生：根据学号定位后，只修改姓名/年龄/住址（学号不变）
     */
    public static void updateStudent(ArrayList<Student> student, Scanner sc) {
        System.out.println("修改学生");
        System.out.println("请输入要修改的学生学号");
        String sid = sc.next();

        int index = findIndex(student, sid);
        if (index >= 0) {
            System.out.println("请输入修改后的学生信息");
            System.out.println("请输入学生姓名");
            student.get(index).setName(sc.next());
            System.out.println("请输入学生年龄");
            student.get(index).setAge(sc.nextInt());
            System.out.println("请输入学生地址");
            student.get(index).setAddress(sc.next());
            System.out.println("修改成功");
        } else {
            System.out.println("该学号不存在，修改失败");
        }
    }

    /**
     * 校验学号是否已存在：存在返回 true，不存在返回 false
     * （内部直接复用 findIndex，避免重复代码）
     */
    public static boolean checkid(ArrayList<Student> student, String sid) {
        return findIndex(student, sid) >= 0;
    }

    /**
     * 根据学号在集合中查找对应学生下标，找不到返回 -1
     */
    public static int findIndex(ArrayList<Student> student, String sid) {
        // 学号是 String，必须用 equals 判断内容，不能用 ==（== 比较的是地址）
        for (int i = 0; i < student.size(); i++) {
            if (student.get(i).getSid().equals(sid)) {
                return i;
            }
        }
        return -1;
    }
}
