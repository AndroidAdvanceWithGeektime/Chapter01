Chapter01-GEEKTIME
======
例子里集成了[Breakpad](https://github.com/google/breakpad) 来获取发生 native crash 时候的系统信息和线程堆栈信息。

编译环境
=======
Android Studio 3.2
CMAKE
NDK(使用ndk 16-19版本)

项目构建
=======

例子采用 [CMAKE](https://developer.android.com/ndk/guides/cmake) 来构建 breakpad  库, 项目可直接导入 AndroidStudio 运行

例子支持`armeabi-v7a`,`arm64-v8a`,`x86` 三种平台。

生成的 crash 都存放在 `/data/data/com.dodola.breakpad/files/crashDump` 下


![截图](screen.png)


