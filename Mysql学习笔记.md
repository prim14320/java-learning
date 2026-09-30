# MySQL 学习笔记

> 按主题分节，方便后续补充。用到就查，不用死背。

---

## 📑 目录 / 学习进度

- [x] **SQL 基础**（通用语法、分类）
- [x] **DDL**（定义数据库 / 表 / 字段）← 本页重点
- [x] **DML**（增、删、改）
- [x] **DQL**（查询）
- [x] **DCL**（用户、权限）← 本页重点
- [x] **函数 / 约束 / 多表查询 / 事务**
- [x] **存储引擎 / 索引 / SQL 优化**
- [x] **视图 / 存储过程 / 触发器 / 锁**
- [x] **InnoDB 引擎**（逻辑结构 / 架构 / 事务原理 / MVCC）
- ✅ **MySQL 基础篇 + 进阶篇核心 —— 全部学完**

---

## 一、SQL 基础

### 1.1 SQL 通用语法

- SQL 语句可以单行或多行书写，**以分号 `;` 结尾**
- 可以用空格、缩进增强可读性
- MySQL 中 SQL **不区分大小写**（关键字建议大写，表名字段名建议小写）
- 注释写法：
  - 单行：`-- 注释内容`（`--` 后面要有空格）或 `# 注释内容`
  - 多行：`/* 注释内容 */`

### 1.2 SQL 分类

| 分类 | 全称 | 作用 |
|---|---|---|
| **DDL** | Data Definition Language | 数据定义：定义数据库、表、字段 |
| **DML** | Data Manipulation Language | 数据操作：增、删、改 |
| **DQL** | Data Query Language | 数据查询 |
| **DCL** | Data Control Language | 数据控制：创建用户、授权 |

> 💡 记忆：**DDL 管"结构"，DML 管"数据"，DQL 管"查"，DCL 管"权限"**。

---

## 二、DDL（数据定义语言）

### 2.1 数据库操作

```sql
-- 查询所有数据库
SHOW DATABASES;

-- 查询当前所在数据库
SELECT DATABASE();

-- 创建数据库
CREATE DATABASE 数据库名;

-- 创建数据库（推荐：IF NOT EXISTS 防止已存在报错，并指定字符集）
CREATE DATABASE IF NOT EXISTS 数据库名 DEFAULT CHARSET utf8mb4;

-- 删除数据库
DROP DATABASE 数据库名;
DROP DATABASE IF EXISTS 数据库名;

-- 使用 / 切换数据库
USE 数据库名;
```

> **字符集**：现在统一用 `utf8mb4`（能存 emoji，兼容性最好）。

### 2.2 表操作 - 创建

**语法**
```sql
CREATE TABLE 表名 (
    字段1  字段1类型  [约束]  [COMMENT '字段注释'],
    字段2  字段2类型  [约束]  [COMMENT '字段注释'],
    ...
) [COMMENT '表注释'];
```

**示例**
```sql
CREATE TABLE tb_user (
    id     INT          COMMENT '编号',
    name   VARCHAR(50)  COMMENT '姓名',
    age    INT          COMMENT '年龄',
    gender VARCHAR(1)   COMMENT '性别'
) COMMENT '用户表';
```

### 2.3 数据类型

#### ① 数值类型

| 类型 | 大小 | 说明 |
|---|---|---|
| `TINYINT` | 1 byte | 小整数 |
| `INT` / `INTEGER` | 4 bytes | 大整数（**最常用**） |
| `BIGINT` | 8 bytes | 极大整数 |
| `FLOAT` | 4 bytes | 单精度浮点 |
| `DOUBLE` | 8 bytes | 双精度浮点 |
| `DECIMAL(M,D)` | 变长 | 定点小数，**金额专用**（M 总位数，D 小数位） |

#### ② 字符串类型

| 类型 | 说明 |
|---|---|
| `CHAR(n)` | 定长字符串（长度固定，性能好） |
| `VARCHAR(n)` | 变长字符串（**最常用**，n 是最大长度） |
| `TEXT` | 长文本（文章、备注等） |

#### ③ 日期时间类型

| 类型 | 格式 | 说明 |
|---|---|---|
| `DATE` | YYYY-MM-DD | 日期 |
| `TIME` | HH:MM:SS | 时间 |
| `DATETIME` | YYYY-MM-DD HH:MM:SS | 日期 + 时间（**最常用**） |
| `TIMESTAMP` | YYYY-MM-DD HH:MM:SS | 时间戳 |

**综合示例（员工表）**
```sql
CREATE TABLE emp (
    id        INT           COMMENT '编号',
    name      VARCHAR(10)   COMMENT '姓名',
    gender    CHAR(1)       COMMENT '性别',
    age       TINYINT       COMMENT '年龄',
    salary    DECIMAL(10,2) COMMENT '工资',
    birthday  DATE          COMMENT '生日',
    entrydate DATETIME      COMMENT '入职时间'
) COMMENT '员工表';
```

### 2.4 表操作 - 查询

```sql
-- 查询当前数据库的所有表
SHOW TABLES;

-- 查询表结构（字段、类型、是否为空等）
DESC 表名;

-- 查询指定表的建表语句
SHOW CREATE TABLE 表名;
```

### 2.5 表操作 - 修改（ALTER TABLE）

```sql
-- 添加字段
ALTER TABLE 表名 ADD 字段名 类型 [COMMENT '注释'];

-- 修改字段类型（只改类型）
ALTER TABLE 表名 MODIFY 字段名 新类型;

-- 修改字段名和类型
ALTER TABLE 表名 CHANGE 旧字段名 新字段名 新类型 [COMMENT '注释'];

-- 删除字段
ALTER TABLE 表名 DROP 字段名;

-- 修改表名
ALTER TABLE 表名 RENAME TO 新表名;
```

> **记忆**：`ADD` 加，`MODIFY` 改类型，`CHANGE` 改名（也能顺带改类型），`DROP` 删，`RENAME` 改名（表）。

### 2.6 表操作 - 删除

```sql
-- 删除表（表结构和数据一起删）
DROP TABLE [IF EXISTS] 表名;

-- 清空表数据，只保留表结构
TRUNCATE TABLE 表名;
```

---

## 三、DML（数据操作语言）

> 作用：对表里的**数据**进行增、删、改。

### 3.1 添加数据 INSERT

```sql
-- ① 指定字段添加一条
INSERT INTO 表名 (字段1, 字段2, ...) VALUES (值1, 值2, ...);

-- ② 给全部字段添加（值的顺序必须和表字段顺序一致）
INSERT INTO 表名 VALUES (值1, 值2, ...);

-- ③ 批量添加
INSERT INTO 表名 (字段1, 字段2) VALUES (值1, 值2), (值3, 值4), ...;
```

**示例**
```sql
INSERT INTO user (id, name, age) VALUES (1, '张三', 20);

INSERT INTO user (id, name, age) VALUES
    (2, '李四', 21), (3, '王五', 19), (4, '赵六', 22);
```

> **注意**：
> - 字段顺序要和值的顺序一一对应
> - 字符串、日期要加引号
> - 值的大小要在字段的数据类型范围内

### 3.2 修改数据 UPDATE

```sql
UPDATE 表名 SET 字段1 = 值1, 字段2 = 值2, ... [WHERE 条件];
```

**示例**
```sql
-- 改 id=1 的姓名
UPDATE user SET name = '张三丰' WHERE id = 1;

-- 同时改多个字段
UPDATE user SET name = '李贤', age = 23 WHERE id = 2;

-- 不加 WHERE 会改所有行！
UPDATE user SET age = 18;
```

> ⚠️ **不加 `WHERE` 会修改整张表**，上线前一定要检查条件。

### 3.3 删除数据 DELETE

```sql
DELETE FROM 表名 [WHERE 条件];
```

**示例**
```sql
DELETE FROM user WHERE id = 1;
DELETE FROM user;   -- 删除整张表数据（危险）
```

> **DELETE vs TRUNCATE**：
> - `DELETE`：按条件删数据，可回滚，速度慢
> - `TRUNCATE`：清空整表，不能加条件，速度快

## 四、DQL（数据查询语言）

> 查询是 SQL **最重要**的部分。完整语法（书写顺序）：
> ```sql
> SELECT   字段列表
> FROM     表名
> WHERE    条件
> GROUP BY 分组字段
> HAVING   分组后条件
> ORDER BY 排序字段
> LIMIT    分页;
> ```

### 4.1 基础查询

```sql
-- 查询指定字段
SELECT 字段1, 字段2 FROM 表名;

-- 查询所有字段（实际开发尽量别用 *）
SELECT * FROM 表名;

-- 起别名（AS 可省略，别名用单引号更规范）
SELECT author AS '作者' FROM book;

-- 去重
SELECT DISTINCT author FROM book;
```

**示例**
```sql
SELECT book.author AS '作者' FROM book;
SELECT DISTINCT book.author '作者' FROM book;
```

### 4.2 条件查询 WHERE

| 运算符 | 说明 |
|---|---|
| `> < >= <= = !=` | 比较 |
| `BETWEEN ... AND ...` | 在范围内（含两端） |
| `IN (...)` | 在列表中，多选一 |
| `LIKE 占位符` | 模糊匹配（`%` 任意个字符，`_` 单个字符） |
| `IS NULL` / `IS NOT NULL` | 为空 / 非空 |
| `AND` / `OR` / `NOT` | 逻辑运算 |

**示例**
```sql
-- 日期范围
SELECT * FROM book WHERE publish_date >= '2000-1-1' AND publish_date <= '2015-2-5';

-- IN：价格是这几个值之一
SELECT * FROM book WHERE price IN (20, 40, 64, 68);
```

> 暂时用不上的字段可以直接排除，先掌握 `= > < AND OR IN`。

### 4.3 聚合函数

| 函数 | 作用 |
|---|---|
| `COUNT()` | 统计数量 |
| `MAX()` | 最大值 |
| `MIN()` | 最小值 |
| `SUM()` | 求和 |
| `AVG()` | 平均值 |

> ⚠️ **聚合函数不计算 NULL 值**。`COUNT(*)` 统计行数；`COUNT(字段)` 不会统计该字段为 NULL 的行。

**示例**
```sql
SELECT COUNT(*) FROM book;                        -- 总行数
SELECT MAX(price) FROM book;                      -- 最高价
SELECT MIN(price) FROM book;                      -- 最低价
SELECT SUM(stock) FROM book;                      -- 库存总和
SELECT COUNT(publisher) FROM book WHERE publisher = '人民文学出版社';
```

### 4.4 分组查询 GROUP BY

```sql
SELECT 分组字段, 聚合函数
FROM 表名
[WHERE 条件]
GROUP BY 分组字段
[HAVING 分组后条件];
```

**示例**
```sql
-- 每个分类的平均价格
SELECT category, AVG(price) FROM book GROUP BY category;

-- 人民文学出版社，各分类的数量
SELECT category, COUNT(*) FROM book WHERE publisher = '人民文学出版社' GROUP BY category;

-- 价格 < 35 的，按出版社分组，只保留数量 <= 8 的
SELECT publisher AS '出版社', COUNT(*) AS '合计'
FROM book
WHERE price < 35
GROUP BY publisher
HAVING COUNT(*) <= 8;
```

> **WHERE vs HAVING**：
> - `WHERE`：**分组前**过滤，**不能**跟聚合函数
> - `HAVING`：**分组后**过滤，**可以**跟聚合函数

### 4.5 排序查询 ORDER BY

```sql
SELECT ... ORDER BY 字段1 [ASC | DESC], 字段2 [ASC | DESC];
```

- `ASC`：升序（默认，可省略）
- `DESC`：降序

**示例**
```sql
SELECT * FROM book ORDER BY price ASC;      -- 按价格升序
SELECT * FROM book ORDER BY price DESC;     -- 按价格降序
SELECT * FROM book ORDER BY publish_date;   -- 按出版日期
```

### 4.6 分页查询 LIMIT

```sql
SELECT ... LIMIT 起始索引, 每页条数;
```

- **起始索引 = (页码 - 1) × 每页条数**
- 查询第一页时，起始索引可省略：`LIMIT 5` 等价于 `LIMIT 0, 5`

**示例**
```sql
SELECT * FROM book LIMIT 0, 5;   -- 第 1 页，每页 5 条（第 1~5 条）
SELECT * FROM book LIMIT 5, 2;   -- 从第 6 条开始取 2 条
```

### 4.7 查询执行顺序（重要）

```
FROM → WHERE → GROUP BY → HAVING → SELECT → ORDER BY → LIMIT
```

> 注意：写的时候 `SELECT` 在最前面，但**执行**时是先 `FROM` 再 `WHERE` 再……最后才是 `SELECT`，这就是为什么 `WHERE` 里不能写 `SELECT` 里的别名。

## 五、DCL（数据控制语言）

> 作用：管理数据库的**用户**和**权限**。一般由 DBA（数据库管理员）操作，开发人员了解即可。

### 5.1 用户管理

用户信息存放在 MySQL 自带的 `mysql` 库的 `user` 表里。

```sql
-- 查询用户（先切到 mysql 库）
USE mysql;
SELECT * FROM user;

-- 创建用户
CREATE USER '用户名'@'主机名' IDENTIFIED BY '密码';

-- 修改用户密码
ALTER USER '用户名'@'主机名' IDENTIFIED WITH mysql_native_password BY '新密码';

-- 删除用户
DROP USER '用户名'@'主机名';
```

**示例**
```sql
-- 只能从本机访问的用户
CREATE USER 'itcast'@'localhost' IDENTIFIED BY '123456';

-- 可以从任意主机访问的用户（% 代表任意主机）
CREATE USER 'itcast'@'%' IDENTIFIED BY '123456';
```

> **主机名**：
> - `localhost`：只能本机访问
> - `%`：任意主机都能访问（远程连接用）
>
> ⚠️ 用户名和主机名**必须加引号**。

### 5.2 权限控制

**常用权限**

| 权限 | 说明 |
|---|---|
| `ALL` | 所有权限 |
| `SELECT` | 查询数据 |
| `INSERT` | 插入数据 |
| `UPDATE` | 修改数据 |
| `DELETE` | 删除数据 |
| `ALTER` | 修改表结构 |
| `DROP` | 删除数据库 / 表 |
| `CREATE` | 创建数据库 / 表 |

```sql
-- 查询权限
SHOW GRANTS FOR '用户名'@'主机名';

-- 授予权限
GRANT 权限列表 ON 数据库名.表名 TO '用户名'@'主机名';

-- 撤销权限
REVOKE 权限列表 ON 数据库名.表名 FROM '用户名'@'主机名';
```

**示例**
```sql
-- 授予 itcast 用户对 test 库所有表的查询、插入权限
GRANT SELECT, INSERT ON test.* TO 'itcast'@'localhost';

-- 授予所有库所有表的所有权限
GRANT ALL ON *.* TO 'itcast'@'localhost';

-- 撤销 itcast 对 test 库的查询权限
REVOKE SELECT ON test.* FROM 'itcast'@'localhost';
```

> **记忆**：
> - `GRANT` 授权，`REVOKE` 撤销，`SHOW GRANTS` 查权限
> - `数据库名.表名`：`test.*` 表示 test 库所有表，`*.*` 表示所有库所有表

### 5.3 DCL 小结

| 操作 | 语句 |
|---|---|
| 创建用户 | `CREATE USER '用户'@'主机' IDENTIFIED BY '密码';` |
| 删除用户 | `DROP USER '用户'@'主机';` |
| 修改密码 | `ALTER USER '用户'@'主机' IDENTIFIED WITH mysql_native_password BY '新密码';` |
| 查询权限 | `SHOW GRANTS FOR '用户'@'主机';` |
| 授予权限 | `GRANT 权限 ON 库.表 TO '用户'@'主机';` |
| 撤销权限 | `REVOKE 权限 ON 库.表 FROM '用户'@'主机';` |

## 六、函数

> 一段 SQL 里可以直接调用的内置函数，分四类：**字符串、数值、日期、流程**。

### 6.1 字符串函数

| 函数 | 作用 | 示例 |
|---|---|---|
| `CONCAT(s1, s2, ...)` | 拼接字符串 | `CONCAT('hello','mysql')` → `hellomysql` |
| `UPPER(s)` | 转大写 | `UPPER('hello')` → `HELLO` |
| `LOWER(s)` | 转小写 | `LOWER('HELLO')` → `hello` |
| `LPAD(s, n, pad)` | 左填充到 n 位 | `LPAD('001',6,'$')` → `$$$001` |
| `RPAD(s, n, pad)` | 右填充到 n 位 | `RPAD('001',6,'%')` → `001%%%` |
| `TRIM(s)` | 去掉首尾空格 | `TRIM('  hello world  ')` → `hello world` |
| `SUBSTRING(s, start, len)` | 截取子串 | `SUBSTRING('HELLO MYSQL',1,5)` → `HELLO` |

```sql
select concat('hello','mysql');
select upper('hello');
select lower('HELLO');
select lpad('001',6,'$');
select rpad('001',6,'%￥');
select trim('            hello  world          ');
select substring('HELLO MYSQL',1,5);
-- 实战：用左填充把 publisher / author 补齐到固定宽度
update book set publisher = lpad(publisher,9,' ');
update book set author = lpad(author,6,' ');
```

### 6.2 数值函数

| 函数 | 作用 | 示例 |
|---|---|---|
| `CEIL(x)` | 向上取整 | `CEIL(1.1)` → 2 |
| `FLOOR(x)` | 向下取整 | `FLOOR(1.1)` → 1 |
| `MOD(x, y)` | 取余 | `MOD(5,3)` → 2 |
| `RAND()` | 0~1 之间的随机数 | `RAND()` |
| `ROUND(x, n)` | 四舍五入保留 n 位小数 | `ROUND(2.344,2)` → 2.34 |

```sql
select ceil(1.1);
select floor(1.1);
select mod(5,3);
select rand();
select round(2.344,2);
-- 实战：生成 6 位随机数（不足 6 位前面补 0）
select lpad(round(rand()*100000,0),6,'0');
```

### 6.3 日期函数

| 函数 | 作用 |
|---|---|
| `CURDATE()` | 当前日期 |
| `CURTIME()` | 当前时间 |
| `NOW()` | 当前日期 + 时间 |
| `YEAR(d)` / `MONTH(d)` / `DAY(d)` | 取出年 / 月 / 日 |
| `DATE_ADD(d, INTERVAL n 类型)` | 日期加（如 70 个月） |
| `DATEDIFF(d1, d2)` | 两个日期的天数差（d1 - d2） |

```sql
select curdate();
select curtime();
select now();
select year(now());
select month(now());
select day(now());
select date_add(now(), interval 70 month);
select datediff('2021-12-01','2012-12-3');
select datediff('2012-12-3','2021-12-01');
-- 实战：计算每本书距今已出版多少天
select title, author, datediff(curdate(), book.publish_date) as '已出版时间'
from book order by 已出版时间 asc;
```

### 6.4 流程函数

| 函数 | 作用 |
|---|---|
| `IF(条件, 值1, 值2)` | 条件为真取值1，否则值2 |
| `IFNULL(值, 默认值)` | 值为 NULL 时取默认值 |
| `CASE WHEN 条件 THEN 结果 ... ELSE 结果 END` | 多分支判断 |

```sql
select if(true,'ok','error');
select if(false,'ok','error');
select ifnull('ok','default');
select ifnull('','default');     -- '' 不是 null，返回 ''
select ifnull(null,'default');   -- null，返回 default

-- 实战：按出版社分类
select book.author,
       book.title,
       (case publisher
            when '人民文学出版社' then '优秀出版社'
            when '南海出版公司' then '出版公司'
            else '普通出版社'
        end) as '出版社划分'
from book;
```

## 七、约束

> **约束**：作用于表中字段上的规则，用来限制存储的数据（保证数据的正确性、有效性）。

| 约束 | 关键字 | 说明 |
|---|---|---|
| 非空约束 | `NOT NULL` | 该字段不能为 NULL |
| 唯一约束 | `UNIQUE` | 该字段的值不能重复 |
| 主键约束 | `PRIMARY KEY` | 非空且唯一，一张表只能有一个 |
| 默认约束 | `DEFAULT` | 不指定时使用默认值 |
| 检查约束 | `CHECK` | 保证字段值满足某个条件 |
| 外键约束 | `FOREIGN KEY` | 关联另一张表的主键 |

**示例：建表时加约束**
```sql
create table user (
    id     int         primary key auto_increment comment '主键',
    name   varchar(10) not null unique comment '姓名',
    age    int         check (age > 0 && age <= 120) comment '年龄',
    status char(1)     default '1' comment '状态',
    gender char(1)     comment '性别'
) comment '用户表';
```
> `AUTO_INCREMENT`：主键自增，插入时不写 id 也会自动编号。

**外键约束**
```sql
-- 建表时添加外键
CREATE TABLE 表名 (
    ...,
    [CONSTRAINT 外键名] FOREIGN KEY (外键字段) REFERENCES 主表(主表字段)
);

-- 建表后添加外键
alter table book add column user_id int;
alter table book add constraint fk_book_user_id
    foreign key (user_id) references user(id);

-- 删除外键
ALTER TABLE book DROP FOREIGN KEY fk_book_user_id;
```

**外键的删除 / 更新行为**

| 行为 | 说明 |
|---|---|
| `RESTRICT` / `NO ACTION` | 有外键引用时，不允许删除（默认） |
| `CASCADE` | 级联：主表删除/更新，子表跟着删/改 |
| `SET NULL` | 主表删除/更新，子表外键设为 NULL |

## 八、多表查询

> 从多张表里查数据。前提是表之间有**关联字段**。

### 8.1 多表关系

| 关系 | 例子 | 实现方式 |
|---|---|---|
| 一对一 | 用户 ↔ 身份证 | 任意一方加外键（加唯一约束） |
| 一对多 | 部门 → 员工 | 在"多"的一方加外键 |
| 多对多 | 学生 ↔ 课程 | 建**中间表**，放两个外键 |

### 8.2 连接查询

**① 隐式内连接**（用逗号 + WHERE 连接）
```sql
SELECT 字段 FROM 表1, 表2 WHERE 表1.字段 = 表2.字段;
```
```sql
select course_name, course_code, student_name, score
from courses c, students s, student_courses sc
where s.student_id = sc.student_id and c.course_id = sc.course_id;
```

**② 显式内连接**（`INNER JOIN ... ON`）
```sql
SELECT 字段 FROM 表1 [INNER] JOIN 表2 ON 连接条件;
```

**③ 外连接**
```sql
-- 左外连接：左表全部 + 两表交集
SELECT 字段 FROM 表1 LEFT [OUTER] JOIN 表2 ON 条件;
-- 右外连接：右表全部 + 两表交集
SELECT 字段 FROM 表1 RIGHT [OUTER] JOIN 表2 ON 条件;
```
```sql
select course_name, course_code, student_name, score
from courses c
left join student_courses sc on sc.course_id = c.course_id
right join students s on sc.student_id = s.student_id;
```
> **左外以左表为准、右外以右表为准**（哪边写 LEFT/RIGHT，哪边的数据全都保留）。

**④ 自连接**（自己连自己，**必须起别名**）
```sql
SELECT 字段 FROM 表1 别名1 JOIN 表1 别名2 ON 条件;
```
```sql
select c1.course_code, c1.course_name, c2.course_code, c2.course_name
from courses c1 join courses c2 on c1.course_id = c2.course_id;
```

### 8.3 子查询

> 嵌套在 SQL 语句里的 `SELECT` 称为**子查询**。按返回结果分：

| 类型 | 返回 | 常用搭配 |
|---|---|---|
| 标量子查询 | 一个值 | `= > < >= <=` |
| 列子查询 | 一列 | `IN`、`NOT IN` |
| 行子查询 | 一行 | `=` |
| 表子查询 | 多行多列 | 当临时表用 |

```sql
-- 标量子查询：查工资高于平均工资的员工
SELECT * FROM emp WHERE salary > (SELECT AVG(salary) FROM emp);
```

## 九、事务

> **事务**：一组操作的集合，要么**全部成功**，要么**全部失败**，是不可分割的工作单位。

### 9.1 事务操作

```sql
-- 开启事务
START TRANSACTION;   -- 或 BEGIN;

-- 提交事务（确认更改，永久生效）
COMMIT;

-- 回滚事务（撤销更改）
ROLLBACK;
```

**示例（转账：要么都成功，要么都失败）**
```sql
START TRANSACTION;
UPDATE account SET money = money - 1000 WHERE name = '张三';
UPDATE account SET money = money + 1000 WHERE name = '李四';
-- 一切正常就提交；中途出错就回滚
COMMIT;
-- ROLLBACK;
```

> MySQL 默认**自动提交**事务（每条语句自动算一个事务，执行完直接生效）。
> 查看：`SELECT @@autocommit;`（1 = 自动提交，0 = 手动）。

### 9.2 事务四大特性 ACID

| 特性 | 含义 |
|---|---|
| **原子性 A**（Atomicity） | 事务是不可分割的最小单位，要么全成功，要么全失败 |
| **一致性 C**（Consistency） | 事务完成后，数据必须从一个一致状态变到另一个一致状态 |
| **隔离性 I**（Isolation） | 多个事务并发执行时，互相不受影响 |
| **持久性 D**（Durability） | 事务一旦提交，对数据的改变就是永久的 |

### 9.3 并发事务问题

| 问题 | 描述 |
|---|---|
| **脏读** | 一个事务读到了另一个事务**还未提交**的数据 |
| **不可重复读** | 一个事务内两次读同一数据，结果不一样（别人改了并提交） |
| **幻读** | 一个事务内两次查询，第二次读到了别人**新插入**的数据 |

### 9.4 隔离级别

| 隔离级别 | 脏读 | 不可重复读 | 幻读 |
|---|:---:|:---:|:---:|
| `READ UNCOMMITTED`（读未提交） | ❌ | ❌ | ❌ |
| `READ COMMITTED`（读已提交） | ✅ | ❌ | ❌ |
| `REPEATABLE READ`（可重复读，**MySQL 默认**） | ✅ | ✅ | ❌ |
| `SERIALIZABLE`（串行化） | ✅ | ✅ | ✅ |

> ✅ = 能解决该问题；❌ = 不能解决。隔离级别越高越安全，但性能越低。

```sql
-- 查看当前隔离级别
select @@transaction_isolation;

-- 设置会话（当前连接）的隔离级别
set session transaction isolation level read uncommitted;
```

## 十、存储引擎

> **存储引擎**：数据库底层**负责数据存储和提取**的软件组件。不同引擎的存储机制、索引技巧、锁机制都不同。

**查看 / 指定**
```sql
SHOW ENGINES;                                   -- 查看所有存储引擎
SHOW VARIABLES LIKE 'default_storage_engine';   -- 查看默认引擎
CREATE TABLE 表名 (...) ENGINE = InnoDB;         -- 建表时指定
```

**三种常见引擎对比**

| 特点 | InnoDB | MyISAM | Memory |
|---|:---:|:---:|:---:|
| 事务 | ✅ | ❌ | ❌ |
| 外键 | ✅ | ❌ | ❌ |
| 锁 | 行锁 | 表锁 | 表锁 |
| 存储位置 | 磁盘 | 磁盘 | 内存 |

> MySQL 5.5 之后**默认 InnoDB**，开发基本都用它（支持事务、行锁、外键）。

## 十一、索引

> **索引**：帮助 MySQL **高效获取数据**的数据结构，本质是"排好序的快速查找结构"，底层是 **B+ 树**。

### 11.1 索引的优缺点

- **优点**：大幅提高查询/检索效率，降低排序成本
- **缺点**：占用磁盘空间；降低 `INSERT` / `UPDATE` / `DELETE` 的速度（要维护索引）

### 11.2 索引结构（面试重点）

| 结构 | 说明 |
|---|---|
| **B+ 树** | **InnoDB 默认**。多路平衡树，矮胖、磁盘 IO 少，叶子节点存数据且相连 |
| Hash | 等值查询快，不支持范围查询 |
| R-tree | 空间索引，少用 |
| 全文索引 | 文本检索 |

> **为什么用 B+ 树，不用红黑树**：B+ 树矮胖（一个节点存多个值），查一次磁盘 IO 就够；红黑树高瘦，IO 次数多。

### 11.3 索引分类

| 分类 | 关键字 | 说明 |
|---|---|---|
| 主键索引 | `PRIMARY` | 默认自动创建，一张表只有一个 |
| 唯一索引 | `UNIQUE` | 值不能重复 |
| 常规索引 | 无关键字 | 普通索引 |
| 全文索引 | `FULLTEXT` | 文本检索 |

按**存储形式**分：
- **聚集索引**：主键索引，叶子节点存整行数据
- **二级索引（辅助索引）**：非主键，叶子节点存主键值，查询需要**回表**

### 11.4 索引语法

```sql
-- 创建索引
CREATE [UNIQUE] INDEX 索引名 ON 表名(字段名, ...);

-- 查看索引
SHOW INDEX FROM 表名;

-- 删除索引
DROP INDEX 索引名 ON 表名;
```
```sql
-- 你的练习
show index from students;
create unique index index_students_students_name on students(student_name);
drop index index_students_students_name on students;
```

### 11.5 索引性能分析

**① 查看执行频次**
```sql
SHOW GLOBAL STATUS LIKE 'Com_______';   -- 看增删改查各执行了多少次
```

**② 慢查询日志**
```sql
SHOW VARIABLES LIKE 'slow_query_log';   -- 查看是否开启慢日志
```

**③ show profiles**
```sql
select @@profiling;
set profiling = 1;
show profiles;        -- 查看每条 SQL 的耗时
show profile cpu;     -- 查看最近一条 SQL 的 CPU 耗时
```

**④ EXPLAIN（最常用）**
```sql
explain select * from students;
```
重点看这些字段：

| 字段 | 含义 |
|---|---|
| `type` | 连接类型，越靠左越好：`system > const > eq_ref > ref > range > index > all` |
| `possible_keys` | 可能用到的索引 |
| `key` | 实际用到的索引（**NULL 表示没用上，需要优化**） |
| `key_len` | 索引使用长度 |
| `rows` | 预估扫描行数（越小越好） |

### 11.6 索引使用规则

- **最左前缀法则**：联合索引必须从最左列开始，不能跳过中间列
- **索引失效的情况**：
  - 在索引列上做运算 / 用函数
  - 字符串没加引号（隐式类型转换）
  - `LIKE '%xxx'`（以 % 开头）
  - `OR` 两边有一个字段没索引
- **覆盖索引**：查询的字段都在索引里，**不用回表**，效率高
- **前缀索引**：长字符串只取前几位建索引，省空间

### 11.7 索引设计原则

- 数据量大、查询频繁的表才建索引
- 常出现在 `WHERE`、`ORDER BY`、`GROUP BY` 的字段建索引
- 区分度高（重复值少）的字段适合建索引
- 尽量建**联合索引**，别建太多单列索引
- 字符串很长的字段，可建**前缀索引**

## 十二、SQL 优化

### 12.1 插入数据优化

```sql
-- ① 批量插入（一次插多条，减少连接开销）
INSERT INTO 表名 VALUES (..), (..), (..);

-- ② 手动提交事务（减少频繁提交）
START TRANSACTION;
INSERT ...;
INSERT ...;
COMMIT;
```
> 大批量可用 `LOAD DATA LOCAL INFILE` 导入；主键**顺序插入**比乱序快。

### 12.2 主键优化

- InnoDB 按主键顺序组织数据，**乱序插入**会造成**页分裂**，性能下降
- 主键设计原则：**尽量短**、**顺序插入**（用自增）、**避免 UUID 当主键**

### 12.3 order by 优化

```sql
explain select ... order by 字段;
```
- `Using filesort`：需要额外排序，**慢**
- `Using index`：靠索引顺序返回，**快**
- 做法：给 `ORDER BY` 字段建**合适的索引**，并遵守最左前缀

### 12.4 group by 优化

- 给 `GROUP BY` 的字段建索引，可减少临时表（`Using temporary`）的使用

### 12.5 limit 优化

> `LIMIT 2000000, 10` 会先扫前面 200 万行再取 10 行，很慢。
- 优化思路：**覆盖索引 + 子查询**（先查出主键，再关联查数据）

### 12.6 count 优化

| 写法 | 说明 |
|---|---|
| `count(*)` | **推荐**，InnoDB 专门优化过 |
| `count(1)` | 和 `count(*)` 差不多 |
| `count(字段)` | 不统计 NULL 值，**最慢** |

### 12.7 update 优化

- **根据索引字段更新**，走行锁，性能好
- 根据非索引字段更新，**行锁会升级为表锁**，性能差
- 结论：`UPDATE` 的 `WHERE` 条件字段**要有索引**

## 十三、视图

> **专业解释**：视图（View）是一种**虚拟表**，本身不存储数据，只保存一条 SELECT 语句的定义，查询视图时实际执行的是它定义里的 SQL。
>
> **大白话**：视图就是**把一段查询"存起来"起个名字**，以后直接查这个名字就行，相当于"保存好的查询"。数据还是存在原来的表里。

**作用**
- 简化复杂查询（最常用）
- 安全：只暴露部分字段，隐藏敏感数据
- 让不同用户看到不同的数据

**语法**
```sql
-- 创建视图
CREATE [OR REPLACE] VIEW 视图名 AS SELECT 语句;

-- 查询视图
SELECT * FROM 视图名;
SHOW CREATE VIEW 视图名;

-- 修改视图
ALTER VIEW 视图名 AS SELECT 语句;

-- 删除视图
DROP VIEW [IF EXISTS] 视图名;
```

**示例**
```sql
-- 把"学生-课程-成绩"的复杂查询存成视图
CREATE OR REPLACE VIEW v_student_score AS
SELECT s.name, c.course_name, sc.score
FROM students s, courses c, student_courses sc
WHERE s.student_id = sc.student_id AND c.course_id = sc.course_id;

-- 以后直接查视图，简单
SELECT * FROM v_student_score;
```

**检查选项**
- `WITH CASCADED CHECK OPTION`：更新时检查**所有**视图条件
- `WITH LOCAL CHECK OPTION`：只检查**当前**视图条件

**视图能不能更新？**
- 可以（本质是操作原表）
- 但含**聚合函数、DISTINCT、GROUP BY、子查询**等的视图**不能更新**

## 十四、存储过程

> **专业解释**：存储过程（Stored Procedure）是一段**预编译并存储在数据库中的 SQL 语句集合**，通过名字调用执行。
>
> **大白话**：存储过程就是**把一堆 SQL 打包存进数据库**，起个名字，以后调这个名字 = 执行那堆 SQL。类似编程里的"函数/方法"。

**优缺点**
- 优点：可复用、减少网络传输、封装逻辑
- 缺点：调试麻烦、可移植性差（**现在开发很少用**，业务逻辑一般写在 Java 里）

**语法**
```sql
-- 创建
CREATE PROCEDURE 存储过程名([参数列表])
BEGIN
    -- SQL 语句
END;

-- 调用
CALL 存储过程名([参数]);
```

**参数类型**：`IN`（输入，默认）、`OUT`（输出）、`INOUT`（输入输出）

**示例**
```sql
delimiter $$          -- 临时把结束符改成 $$（因为过程体里有分号）
CREATE PROCEDURE p_count_by_publisher(IN pub VARCHAR(50), OUT total INT)
BEGIN
    SELECT COUNT(*) INTO total FROM book WHERE publisher = pub;
END $$
delimiter ;           -- 改回分号

-- 调用
CALL p_count_by_publisher('人民文学出版社', @total);
SELECT @total;
```

**其他知识点**
- 变量：系统变量（`@@`）、用户变量（`@`）、局部变量（`DECLARE`）
- 流程控制：`IF`、`CASE`、循环（`WHILE`/`REPEAT`/`LOOP`）、游标（`CURSOR`）
- 存储函数：`CREATE FUNCTION`（和存储过程类似，但有返回值）

## 十五、触发器

> **专业解释**：触发器（Trigger）是**绑定在表上、在 INSERT / UPDATE / DELETE 前后自动执行的一段 SQL**。
>
> **大白话**：触发器就是**给表装了个"自动感应器"**——你往表里增删改数据时，它会**自动**帮你跑一段 SQL。比如"加一条订单，自动扣库存"。

**语法**
```sql
CREATE TRIGGER 触发器名
BEFORE | AFTER INSERT | UPDATE | DELETE
ON 表名 FOR EACH ROW
BEGIN
    -- 要自动执行的 SQL
END;
```

**示例（删除用户时自动记日志）**
```sql
CREATE TRIGGER trigger_user_delete
AFTER DELETE ON user FOR EACH ROW
BEGIN
    INSERT INTO user_log(op, optime)
    VALUES (CONCAT('删除了 id=', OLD.id), NOW());
END;
```

- `NEW`：新插入 / 修改后的数据
- `OLD`：修改 / 删除前的数据

> 同样要用 `delimiter` 处理分号问题。

> **注意**：触发器**现代开发基本不用**（逻辑写在 Java 更好调试维护），**了解即可**。

## 十六、锁

> MySQL 在**多线程并发访问**时，用锁来保证数据的一致性。按**粒度**分：全局锁、表级锁、行级锁。

### 16.1 锁介绍

| 粒度 | 说明 |
|---|---|
| 全局锁 | 锁整个数据库实例 |
| 表级锁 | 锁整张表 |
| 行级锁 | 锁单行记录（InnoDB 支持） |

> **总原则**：粒度越小，并发越高，但开销越大。

### 16.2 全局锁

> **专业解释**：全局锁锁定整个数据库实例，加锁后全库只读，所有 DML/DDL 语句阻塞。
>
> **大白话**：把整个数据库"锁死"，谁都不能改，只能看。一般只在**全库备份**时用。

```sql
FLUSH TABLES WITH READ LOCK;   -- 加全局锁
UNLOCK TABLES;                 -- 解锁
```
> 缺点：备份期间别人啥都干不了。所以现在更推荐 `mysqldump --single-transaction`（InnoDB 不加全局锁也能一致性备份）。

### 16.3 表级锁

#### ① 表锁
> **专业解释**：对整张表加锁，分「共享读锁」和「独占写锁」。
> 读锁：本连接只读不能写，其他连接可读、写阻塞；写锁：本连接可读写，其他连接读写都阻塞。
>
> **大白话**：锁整张表。加读锁 = 我只看，别人也只看；加写锁 = 我改，别人别动。

```sql
LOCK TABLES 表名 READ;    -- 加读锁
LOCK TABLES 表名 WRITE;   -- 加写锁
UNLOCK TABLES;            -- 解锁
```

#### ② 元数据锁（MDL）
> **专业解释**：MySQL 自动加，无需手动。对表做增删改查时加「MDL 读锁」，改动表结构时加「MDL 写锁」。
>
> **大白话**：你查数据时系统偷偷上把锁，防止别人改表结构；你要改表结构时系统锁住，防止别人查。**自动的，不用管。**

#### ③ 意向锁
> **专业解释**：表级锁，表示"表里某行被加了锁"。
>
> **大白话**：你给某行上了行锁，系统给整张表打个"标记"。别人想给整表加锁时，一看标记就知道"表里有行被锁了"，**不用一行行去查，提高加表锁的效率**。**自动的，不用管。**

### 16.4 行级锁

> **专业解释**：只锁住涉及的行记录（InnoDB 支持，MyISAM 不支持）。分：行锁、间隙锁、临键锁。
>
> **大白话**：比表锁精细，只锁你要动的那几行，别人动别的行不受影响，**并发性能好**。

#### ① 行锁（Record Lock）
> **专业解释**：锁定单行记录，加在**索引**上。
>
> **大白话**：我改第 3 行就锁第 3 行，别人改第 5 行没事。

> ⚠️ **重点**：行锁是加在**索引**上的！如果 `WHERE` 条件字段**没索引**，行锁会**升级为表锁**（这就是 SQL 优化里"update 要按索引更新"的原因）。

#### ② 间隙锁（Gap Lock）
> **专业解释**：锁定索引记录之间的"空隙"，阻止其他事务在空隙中插入数据（**只在可重复读 RR 级别存在**）。
>
> **大白话**：不光锁行，还锁"行与行之间的缝隙"，防止别人往缝里插数据。主要用来**解决幻读**。

#### ③ 临键锁（Next-Key Lock）
> **专业解释**：行锁 + 间隙锁的组合，锁住记录本身及它前面的间隙。RR 级别下 InnoDB 默认使用。
>
> **大白话**：就是"行 + 它前面的缝"一起锁，是 InnoDB 默认的锁方式。

### 16.5 锁小结

| 锁类型 | 粒度 | 使用场景 |
|---|---|---|
| 全局锁 | 整个库 | 全库备份 |
| 表级锁 | 整张表 | MyISAM；InnoDB 的 MDL / 意向锁 |
| 行级锁 | 单行 / 间隙 | InnoDB（默认） |

**一句话**：**粒度越小、并发越高、开销越大**。InnoDB 用行级锁，所以并发性能好。

## 十七、InnoDB 引擎

> InnoDB 是 MySQL 默认存储引擎。这一节讲它**内部怎么存数据、怎么保证事务**，是面试重点，也是进阶篇最绕的部分。下面尽量用大白话 + 例子讲。

### 17.1 逻辑存储结构（从大到小）

```
表空间 (Tablespace)
 └── 段 (Segment)
      └── 区 (Extent)      1 区 = 64 个页
           └── 页 (Page)    1 页 = 16KB（InnoDB 读写的最小单位）
                └── 行 (Row)  真正的一条数据
```

**大白话类比（图书馆）**
- **表空间** = 一整座图书馆
- **段** = 图书馆里的分类区（如"中国文学区"）
- **区** = 一排书架（1 排 = 64 页）
- **页** = 一页纸（16KB，InnoDB 存取的最小单位）
- **行** = 纸上的一行字（真正的一条记录）

> ✅ **重点**：InnoDB 读写磁盘的最小单位是**页（16KB）**，不是"行"。

### 17.2 架构（内存 + 磁盘 + 后台线程）

**① 内存结构（重点看 Buffer Pool）**

| 内存区 | 作用 | 大白话 |
|---|---|---|
| **Buffer Pool**（缓冲池） | 缓存磁盘上的页数据 | **最重要**！把常用数据缓存在内存，读得快 |
| Change Buffer | 缓存非唯一二级索引的写操作 | 改索引先记内存，回头再刷盘，少 IO |
| Adaptive Hash Index | 自适应哈希索引 | 给热点数据加个"快速查找表" |
| **Log Buffer**（日志缓冲） | 缓存 redo log | redo log 先写这里，再刷盘 |

**② 磁盘结构**
- 各类表空间（System / File-Per-Table / General / Undo）
- **Redo Log**（重做日志）
- **Undo Log**（回滚日志）
- 双写缓冲（Doublewrite Buffer）

**③ 后台线程**：Master Thread、IO Thread、Purge Thread、Page Cleaner Thread
> 负责把内存里的脏数据刷回磁盘、清理无用 undo 等。**了解即可，不用记。**

### 17.3 事务原理（大白话版）

事务的 ACID 分别靠这些实现：

| 特性 | 靠什么实现 |
|---|---|
| **原子性 A** | **undo log**（回滚日志） |
| **持久性 D** | **redo log**（重做日志） |
| **隔离性 I** | **锁 + MVCC** |
| **一致性 C** | 靠上面三个共同保证 |

#### ① redo log（重做日志）→ 保证「持久性」

**问题**：每次改数据都直接写磁盘，太慢。
**做法**：改数据时，先改内存（Buffer Pool），同时把"改了什么"记进 **redo log**。只要 redo log 落盘了，**即使数据还没写磁盘、突然断电，也能靠 redo log 恢复**。

**大白话**：
> 像写作业，先在**草稿本**打个草稿（redo log），往作业本上誊抄（写磁盘）可以慢慢来。**中途停电，照着草稿本就能补回来。**

#### ② undo log（回滚日志）→ 保证「原子性」

**做法**：改数据**前**，先把**旧值**写进 undo log。事务要回滚时，照着 undo log 把数据改回去。undo log 还用于 **MVCC**（见下）。

**大白话**：
> 像编辑文档前先"**存个档**"，改坏了可以**一键撤销**，回到改之前的样子。

### 17.4 MVCC（多版本并发控制）—— 最绕，慢慢看

> **解决的问题**：让"读"和"写"**不互相阻塞**，还能读到正确的数据。
> **一句话**：每个事务看到的，都是**"它自己那个时刻的数据快照"**。

#### ① 三个隐藏字段（每行记录都偷偷带着）

InnoDB 给每行数据都加了隐藏字段：

| 隐藏字段 | 作用 | 大白话 |
|---|---|---|
| `DB_TRX_ID` | 最后修改这行的事务 ID | "这行是谁改的" |
| `DB_ROLL_PTR` | 回滚指针，指向 undo log | 一根"链子"，能顺藤摸瓜找到这行的**历史版本** |
| `DB_ROW_ID` | 隐藏主键（表没主键时用） | 备用编号 |

#### ② undo log 版本链（历史版本串成一条链）

每次修改一行，旧版本就通过 `DB_ROLL_PTR` 串起来，形成一条**版本链**。

**例子**：假设「name」这一列被改过几次
```
事务100：name = '张三'   ← 最新版本
   ↑ DB_ROLL_PTR 指向
事务80 ：name = '李四'
   ↑ DB_ROLL_PTR 指向
事务50 ：name = '王五'   ← 最初的版本
```
这样任何事务都能顺着链，找到**自己该看的那个版本**。

#### ③ ReadView（读视图）

ReadView 记录了「生成这一刻，哪些事务还**活跃**（没提交）」。

**大白话**：
> ReadView 就像拍照时的「**在场名单**」——记下此刻还有谁在忙。你只能看到"名单之外、已经完成的"那些数据版本。

#### ④ RC vs RR 的区别（★ 关键考点）

区别就在**什么时候生成 ReadView**：

| 隔离级别 | 生成 ReadView 的时机 | 效果 |
|---|---|---|
| **RC**（读已提交） | **每次** SELECT 都生成新的 ReadView | 每次都能读到别人**最新提交**的数据 → 会出现**不可重复读** |
| **RR**（可重复读，MySQL 默认） | **只在第一次** SELECT 时生成，之后**复用** | 一直用同一个快照 → **可重复读** |

**举例**：同一个事务里连续查两次，中间别的事务提交了一条修改：
- **RC**：两次结果**可能不一样**（第二次读到了新提交的）
- **RR**：两次结果**一样**（用的是第一次的快照）

> 这就是 MySQL 默认用 **RR** 的原因——同一个事务内，读到的数据始终一致。

### 17.5 InnoDB 小结

- **逻辑结构**：表空间 → 段 → 区 → 页（16KB）→ 行
- **架构**：内存（Buffer Pool 最重要）+ 磁盘（redo / undo log）
- **事务原理**：
  - 原子性 → **undo log**
  - 持久性 → **redo log**
  - 隔离性 → **锁 + MVCC**
- **MVCC** = 隐藏字段 + 版本链 + ReadView
  - **RC**：每次读都建 ReadView
  - **RR**：只在第一次读建 ReadView（所以能重复读）

## 十八、后续（可选，非开发重点）

- 日志（错误日志 / binlog / 慢查询日志）
- 主从复制、分库分表、读写分离（运维方向，**面试前了解原理即可**）
