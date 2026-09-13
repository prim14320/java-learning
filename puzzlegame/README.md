# 拼图游戏（Puzzle Game）

> 用 Java Swing 实现的桌面拼图小游戏。学习 JavaSE 面向对象阶段的综合练习项目，跟着黑马教程边学边敲。

---

## 📋 功能

- ✅ 4×4 拼图，打乱图片后通过方向键移动
- ✅ 步数统计
- ✅ 按 `A` 键查看完整原图
- ✅ 胜利判定（拼回原图弹出胜利提示）
- ✅ 菜单栏：重新开始 / 重新登录 / 关闭游戏 / 公众号
- ✅ 按 `W` 键一键复原（作弊码）
- 🔄 登录界面、注册界面（暂为窗口骨架，待完善）

---

## 🛠️ 技术栈

- Java Swing（JFrame / JMenuBar / JLabel / ImageIcon）
- 事件监听（KeyListener 键盘事件、ActionListener 菜单事件）
- 面向对象封装

---

## 🚀 运行方式

1. 用 IntelliJ IDEA 打开项目
2. 运行 `src/APP.java` 的 `main` 方法
3. 用方向键移动拼图

> ⚠️ **注意**：代码中图片路径写的是绝对路径（`D:\ideapro\puzzlegame\image\...`）。
> 换到别的电脑运行时，需要把 `GameJFrame.java` 里的 `path` 变量和几处图片路径改成你自己电脑上的实际路径。

---

## 📂 文件结构

```
puzzlegame/
├── src/
│   ├── APP.java                # 程序入口
│   └── ui/
│       ├── GameJFrame.java     # 游戏主界面（拼图逻辑、键盘/菜单事件）
│       ├── LoginJFrame.java    # 登录界面（骨架）
│       └── RegisterJFrame.java # 注册界面（骨架）
└── image/                      # 图片资源（背景图、动物/女孩/运动拼图素材）
```

---

## 💡 知识点覆盖

- 面向对象：类、对象、封装、继承（`extends JFrame`）
- 接口实现：`KeyListener`、`ActionListener`
- 二维数组操作：拼图数据存储、位置交换
- 事件处理：键盘按下/释放、菜单点击
- Swing 组件：窗口、菜单、标签、对话框
- 随机打乱算法

---

## 📝 学习说明

这是学习过程中的练习项目，参考了黑马程序员的拼图游戏教程，自己动手实现了核心的游戏逻辑。
登录、注册界面以及图片路径优化还会继续完善。
