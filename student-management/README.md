# 学生管理系统

> 一个用 **Java 基础语法** 实现的简易控制台学生管理系统（含登录模块）。学习 JavaSE 的练习项目。

---

## 📋 功能

### 登录模块（`LoginSystem`）
- ✅ 登录（含图形验证码校验）
- ✅ 注册（用户名 / 身份证 / 手机号格式校验 + 密码二次确认）
- ✅ 忘记密码（凭身份证 + 手机号验证后重置）
- ✅ 退出

### 学生管理模块（`StudentSystem`）
- ✅ 添加学生（自动校验学号是否重复）
- ✅ 删除学生
- ✅ 查询学生（列表展示，学号/姓名/年龄/住址）
- ✅ 修改学生信息
- ✅ 退出

数据存储在内存中（`ArrayList`），退出后不保留。后续可扩展为数据库存储。

---

## 🛠️ 技术栈

- Java（面向对象 + ArrayList 集合 + Scanner 控制台输入）
- IDE：IntelliJ IDEA

---

## 🚀 使用方法

1. 把 `src` 目录下的 `.java` 文件放入 IDEA 的一个 Java 项目中
2. 运行 `LoginSystem.java` 的 `main` 方法
3. 先登录（内置账号：`swd` / `qwer123456`），登录成功后进入学生管理菜单
4. 按菜单提示输入数字操作

---

## 📂 文件结构

```
studentsystem/
├── LoginSystem.java      # 登录模块（登录/注册/忘记密码 + 验证码）
├── User.java             # 用户实体类（用户名/密码/身份证/手机号）
├── StudentSystem.java    # 学生管理模块（增删改查）
└── Student.java          # 学生实体类（学号/姓名/年龄/住址）
```

---

## 💡 知识点覆盖

- 面向对象封装（private + getter/setter + 构造方法）
- `ArrayList` 集合的增删改查
- `static` 静态变量、静态代码块初始化数据
- `Scanner` 控制台输入、`switch` 分支、`while` 循环
- `String` 常用方法（length / charAt / equals / equalsIgnoreCase）
- `StringBuilder` 拼接、`Random` 生成验证码
- 格式校验：用户名（3-15 位含字母）、身份证（18 位）、手机号（11 位）
- 方法抽取与复用（`findUser` / `getUserIndex` 等）

---

## 📝 犯过的错（学习经验）

我在写这个项目时踩过几个坑，包括：
- 添加学生时先 add 再填信息，造成"空壳学生"进列表
- `toString()` 类名写错、int 类型误加单引号
- 每个方法重复 `new Scanner`
- 用 `System.exit(0)` 强杀进程
- `checkid` / `getIndex` 代码冗余

完整解析（错误写法 + 正确写法 + 为什么错）见 **[代码问题清单.md](./代码问题清单.md)**。

---

## 📝 后续优化方向

- 用 MySQL 替代 `ArrayList` 持久化数据
- 用 SpringBoot + MyBatis 改造成 Web 接口版本
- 密码加密存储（MD5 / BCrypt）
- 登录状态用 Session 维护，登录后不再直接调用 `StudentSystem.main`
