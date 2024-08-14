* 练手级原生javafx 2D平台闯关游戏<br>
该项目适合想要熟悉javafx 以及想要使用java语言来做出一款属于自己的平台闯关游戏的朋友<br>
希望这个项目能给你们带来帮助<br>
***本项目中使用的素材来自bing pixiv以及Gacha Nox生成的模型 如构成侵权 请联系我删除 抱歉<br>
请先掌握部分javafx的知识再上手操作 或者配合Chatgpt查询代码逻辑<br>
<br>
*** src文件夹<br>
存储工程文件<br>
*** AllSource文件夹<br>
存储除人物素材外一切图片资源<br>
*** Chara文件夹<br>
其中的子文件夹存储各个具体人物的动画资源(.png)<br>
<br>
** FirstView类<br>
这个类的作用是作为游戏加载的主菜单入口<br>
以及生成该游戏所处的共用窗体(MainStage) (此处注意 只有运行FirstView类进入游戏才能在游戏失败后返回主菜单再进入管卡 FirstView需要向GameView类传入窗体指针)<br>
<br>
** GameView类<br>
游戏关卡的通用类 主要参数为ArrayList<Line> Road 该列表将储存路径信息<br>
ArrayList<Line> Road的存放规则为从左至右(Player类中的重力检测会获取路段来产生y坐标) 当前仅可做出单一线性路线 不可向上延展(需要在Player类中的getRoadY添加更加准确的碰撞)<br>
<br>
** Player类<br>
玩家控制器类 所有的自定义玩家类需要继承这个Player类以获取键盘控制 重力检测等逻辑<br>
A为向左 D为向右 SPACE为跳跃 长按SHIFT为加速<br>





