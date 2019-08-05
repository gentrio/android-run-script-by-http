# android-run-script-by-websocket



## 简介：

通过Websocket下发Java代码到Android设备上执行，配合IntelliJ插件使用。



## 依赖配置：

工程根目录下 `build.gradle`：

```groovy
allprojects {
    repositories {
        google()
        jcenter()
        maven { url 'https://dl.bintray.com/gentrio/maven'}
        maven { url 'https://jitpack.io' }
    }
}
```

添加依赖：

```groovy
implementation 'com.gentriolee.runscript:client:<latest-version>'
```

初始化：

```java
JavaExecutor.init(applicationContext)
```



## 插件支持：

**插件安装方式一**

`Install Plugin From Disk` [下载地址](https://github.com/gentrio/android-run-script-by-websocket/releases)

**插件安装方式二**

`Search Plugins In Marketplace` 搜索关键字 `RunScript` 



## 使用方法：

```java
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.gentrio.runscript.Script;
import com.gentrio.runscript.ScriptClass;
import com.gentrio.runscript.ScriptClassContext;


@ScriptClass
public class RunScriptTest extends ScriptClassContext {

    @Script
    public void run() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(application, "Hello World", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
```

继承`ScriptClassContext` 便于使用应用执行环境中注入的`application`和`activity`属性

`@ScriptClass`注解表示该类属于执行脚本类

`@Script`注解标注的方法代码块会被下发应用上执行

### 插件使用方法：

无设备在线时：

![](http://img.gentriolee.com/blog/20190801145424.png)

USB连接：打开应用，USB调试连接PC，`系统环境变量需配置ADB` ，点击以下按钮，完成连接。

![](http://img.gentriolee.com/blog/20190805133332.png)

主动连接：打开应用，点击绿色悬浮窗，输入WebSocket地址：`ws://{PC IP地址}:8222`，点击确认完成连接。



设备在线时：

![image-20190805133747747](/Users/gentrio/Library/Application Support/typora-user-images/image-20190805133747747.png)



执行脚本：

`Android Studio` 打开执行脚本类，点击红色执行按钮，应用便会执行，执行结果会在控制台显示：

![](http://img.gentriolee.com/blog/20190805134045.png)

应用执行脚本：
![](http://img.gentriolee.com/blog/20190805134230.png)