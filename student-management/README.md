# 学生管理系统

> 一个用 **Java 基础语法** 实现的简易控制台学生管理系统。学习 JavaSE 的第一个完整练习项目。

---

## 📋 功能

- ✅ 添加学生（自动校验学号是否重复）
- ✅ 删除学生
- ✅ 查询学生（列表展示，学号/姓名/年龄/住址）
- ✅ 修改学生信息
- ✅ 退出系统

数据存储在内存中（`ArrayList`），退出后不保留。后续可扩展为数据库存储。

---

## 🛠️ 技术栈

- Java（面向对象 + ArrayList 集合 + Scanner 控制台输入）
- IDE：IntelliJ IDEA

---

## 🚀 使用方法

1. 把 `src` 目录下的两个 `.java` 文件放入 IDEA 的一个 Java 项目中
2. 运行 `StudentSystem.java` 的 `main` 方法
3. 按菜单提示输入数字操作（1 添加、2 删除、3 查询、4 修改、5 退出）

---

## 📂 文件结构

```
studentsystem/
├── Student.java          # 学生实体类（学号/姓名/年龄/住址 + 封装）
└── StudentSystem.java    # 主程序（菜单循环 + 增删改查逻辑）
```

---

## 💡 知识点覆盖

- 面向对象封装（private + getter/setter + 构造方法）
- `ArrayList` 集合的增删改查
- `Scanner` 控制台输入、`switch` 分支、`while` 循环
- 方法抽取（`addStudent` / `deleteStudent` / `queryStudent` / `updateStudent`）
- 学号唯一性校验（`checkid`）
- 空列表判断（`null` 保护，避免遍历空列表）

---

## 📝 后续优化方向

- 用 MySQL 替代 `ArrayList` 持久化数据
- 用 SpringBoot + MyBatis 改造成 Web 接口版本
- 增加登录/权限功能
