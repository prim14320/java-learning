package studentsystem;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentSystem {
    public static void main(String[] args) {
        boolean flag = true;//1.外部变量控制循环
        ArrayList<Student> student = new ArrayList<>();
        loop :while (flag) {//2.指定位置跳出循环
            System.out.println("-------------学生管理系统--------------------");
            System.out.println("1.添加学生");
            System.out.println("2.删除学生");
            System.out.println("3.查询学生");
            System.out.println("4.修改学生");
            System.out.println("5.退出");
            Scanner sc = new Scanner(System.in);
            String choice = sc.next();
            switch (choice) {
                case "1" -> addStudent(student);
                case "2" -> deleteStudent(student);
                case "3" -> queryStudent(student);
                case "4" -> updateStudent(student);
                case "5" -> {
                    System.out.println("退出");
//                    flag = false;
//                    break loop;
                    System.exit(0);//3.直接关闭虚拟机
                }
                default -> System.out.println("没有该选项");
            }
        }

    }

    public static void addStudent(ArrayList<Student> student) {
        System.out.println("添加学生");
        Scanner sc = new Scanner(System.in);
        Student s = new Student();
        while(true) {
            System.out.println("请输入学生学号");
            s.setSid(sc.next());
            if (!checkid(student, s.getSid())) {
                student.add(s);
                break;
            } else {
                System.out.println("该学号已存在，添加失败");
            }
        }
        System.out.println("请输入学生姓名");
        s.setName(sc.next());
        System.out.println("请输入学生年龄");
        s.setAge(sc.nextInt());
        System.out.println("请输入学生地址");
        s.setAddress(sc.next());
        System.out.println("添加成功");

    }

    public static void deleteStudent(ArrayList<Student> student) {
        System.out.println("删除学生");
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入要删除的学生学号");
        String sid = sc.next();
        int index = getindex(student, sid);
        if(index>=0){
            student.remove(index);
            System.out.println("删除成功");
        }else{
            System.out.println("该学号不存在，删除失败");
        }
    }

    public static void queryStudent(ArrayList<Student> student) {
        System.out.println("查询学生");
        if(student.size()==0){
            System.out.println("当前无学生信息,请添加后再查询");
            return;

        }
        System.out.println("id"+" \t"+"姓名"+" \t"+"年龄"+" \t"+"地址");
        for (int i = 0; i < student.size(); i++) {
            System.out.println(student.get(i).getSid()+"\t"+student.get(i).getName()+"\t"+student.get(i).getAge()+"\t"+student.get(i).getAddress()+"\t");
        }
    }

    public static void updateStudent(ArrayList<Student> student) {
        System.out.println("修改学生");
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入要修改的学生学号");
        String sid = sc.next();
        int index = getindex(student, sid);
        if(index>=0) {
            System.out.println("请输入修改后的学生信息");
            System.out.println("请输入学生姓名");
            student.get(index).setName(sc.next());
            System.out.println("请输入学生年龄");
            student.get(index).setAge(sc.nextInt());
            System.out.println("请输入学生地址");
            student.get(index).setAddress(sc.next());
            System.out.println("修改成功");
        }else{
            System.out.println("该学号不存在，修改失败");
        }
    }
    public static boolean checkid(ArrayList<Student> stu,String sid){
        return getindex(stu,sid)>=0;
    }
    public static  int  getindex(ArrayList<Student> stu,String sid){
        for (int i = 0; i < stu.size(); i++) {
            if(stu.get(i).getSid().equals(sid)){
                return i;
            }
        }
        return -1;
    }
}

