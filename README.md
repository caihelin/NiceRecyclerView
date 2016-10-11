# NiceRecyclerView

一个针对

[EasyRecyclerView](https://github.com/Jude95/EasyRecyclerView)、

[BaseAdapter](https://github.com/hongyangAndroid/baseAdapter)、

[SwipeRecyclerView](https://github.com/yanzhenjie/SwipeRecyclerView)

的再次封装，方便开发安卓开发人员更简单地使用RecyclerView

## 先上一波效果图
    
不说话，先看图

<img src="gif/11.gif">

<img src="gif/12.gif">

<img src="gif/13.gif">

<img src="gif/14.gif">

<img src="gif/15.gif">

<img src="gif/16.gif">

<img src="gif/17.gif">

<img src="gif/18.gif">

<img src="gif/19.gif">

<img src="gif/20.gif">

看图可知，这个库的作用：

* 更加方便地构建RecyclerView的Adapter(构建CommonAdapter，熟悉鸿洋BaseAdapter库的同学应该知道怎么使用)
* 更加方便地为RecyclerView添加HeaderView和FooterView
* 完美地实现让RecyclerView具备下拉刷新和加载更多的功能
* 完美地为RecyclerView添加侧滑菜单，菜单的样式由开发人员来定
* 支持任意布局的刷新操作(严格意义上讲，NiceRecyclerView不是RecyclerView，它是对一些控件的包装)

## 使用