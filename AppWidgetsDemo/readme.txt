文件介绍：

该Demo源于Android SDk Sample 下面的例子，稍加改动得到

1,ExampleAppWidgetConfigure.java
    定义了一个设置AppWidget内容的Activity。当appWidget第一次添加是被调用。
    通过appwidget_provider.xml中,android:configure来指定

2,ExampleAppWidgetProvider.java
    定义一个AppWidget的提供者，需要在AndroidManifest.xml中声明，并制定特殊的Intent-Filter
    
3,ExampleBroadcastReceiver.java
    用来自定更新AppWidget上的内容的接收器。
