# Libs

**该工程会陆续push一些custom widget以及tool lib**

2016.7.13: **LoopViewPager** 通过ViewPager实现自动轮播功能的轮播图控件

2016.7.13: **BaseRecyclerView** 对RecyclerView.Adapter进行了进一步封装, 可以添加多个Header条目

2016.7.14: **GalleryViewPager** 用ViewPager实现横向照片墙效果(有缩放效果)

----

Actionbar+RecyclerView(ListView)的联动效果系列

2016.7.18: **EnterAlwaysLinkage**   Actionbar跟随移动, 类似scroll|enter_always.

2016.7.20: **Pull2BlurLinkage**   下拉模糊联动效果： 当第一个完全可见的条目是HeaderView(头部的图片)时,继续下拉则放大.

松开或上拉恢复. 当第一个可见条目是HeaderView时,继续下拉逐渐改变Actionbar的背景透明度逐渐可见,上拉则逐渐不可见

----

2016.7.29: **Drag2DoubleUnfold** 控件, 实现二级展开的抽屉效果, 类似Android端知乎的分享或者百度地图的搜索结果页.

![A Start B][drag2doubleunfold]

使用方式:

在布局xml文件中添加 `com.wosloveslife.drag2doubleunfold.Drag2DoubleUnfoldLayout` 控件,
并在子节点写自己布局, **注意: Drag2DoubleUnfoldLayout只能有一个直接的子节点**, 类似ScrollLayout

示例:

```
<com.wosloveslife.drag2doubleunfold.Drag2DoubleUnfoldLayout
     android:id="@+id/id_sdl_spread"
     android:layout_width="match_parent"
     android:layout_height="match_parent">
     <LinearLayout
         android:layout_width="match_parent"
         android:layout_height="match_parent"
         android:background="#f41886"
         android:orientation="vertical">
         <TextView
             android:layout_width="wrap_content"
             android:layout_height="wrap_content"
             android:text="Hello World!"/>
         <ImageView
             android:layout_width="wrap_content"
             android:layout_height="wrap_content"
             android:src="@android:drawable/alert_dark_frame"/>
         <Button
             android:id="@+id/id_btn_toast"
             android:layout_width="wrap_content"
             android:layout_height="wrap_content"/>
     </LinearLayout>
 </com.wosloveslife.drag2doubleunfold.Drag2DoubleUnfoldLayout>
```

控制方法: <br/>

改变滑动控件的形态 有三种常量值: `FORM_PART` 展开部分; `FORM_COMP` 完全展开; `FORM_FOLD` 完全收起<br/>
`void controlForm(int form)`

以FORM_PART的形态弹出控件<br/>
`void show()`

以FORM_FOLD的形态收起控件<br/>
`void dismiss()`

获取当前形态<br/>
`int getForm()`

设置是否可以从底边边缘滑出, 默认不可以<br/>
`void setEdgeTrackingEnabled(boolean fromBottom)`

获取当前是否可以从边缘滑出<br/>
`boolean getEdgeTrackingEnabled()`

设置是否当控件收起时(FORM_FOLD)Visibility为GONE, 默认为true<br/>
`void setAutoDismissEnable(boolean autoDismiss)`

获取当前是否控件收起时Visibility为GONE<br/>
`boolean getAutoDismissEnable()`

增加对形态改变事件的监听<br/>
`void addOnFormChangeListener(OnFormChangeListener onFormChangeListener)`

移出对形态改变事件的监听<br/>
`void removeOnFormChangeListener(OnFormChangeListener onFormChangeListener)`

---