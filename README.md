Chapter01-GEEKTIME
======
例子里集成了[Breakpad](https://github.com/google/breakpad)和 [NDCrash](https://github.com/ivanarh/ndcrash)两套 Crash 抓取框架，用来展示比较流行的几种抓取方式，unwind 机制。


项目构建
=======

例子采用 [CMAKE](https://developer.android.com/ndk/guides/cmake) 来构建 breakpad 和 ndcrash 库, 项目可直接导入 AndroidStudio 运行

NDCrash 是一款开源的 Native Crash 捕获框架，为了兼容各个系统版本实现了不同方式的 unwinder。

例子支持`armeabi-v7a`,`arm64-v8a`,`x86` 三种平台，不过在某些手机上会某些 unwind 方式会失败。


生成的 crash 都存放在 `/data/data/com.dodola.breakpad/files` 或者 `/sdcard/crashDump（给予权限）`下
