# Libs

**该工程会陆续push一些custom widget以及tool lib**

**展示/使用**

**MultiViewPager**

<img src="https://github.com/WosLovesLife/Libs/blob/master/screenshots/Intro_MultiViewPager.gif"/>

使用方式:

将multiviewpager的Module导入到项目中,在Project Structure中添加依赖, 然后在布局xml文件中添加 `com.wosloveslife.multiviewpager.view.MultiViewPager` 控件.
调用`void setAdapter(PagerAdapter pagerAdapter)`方法设置页面内容

xml示例:
```
<com.wosloveslife.galleryviewpager.view.GalleryViewPager
    android:id="@+id/id_gvp_gallery"
    android:layout_width="match_parent"
    android:layout_height="200dp">
</com.wosloveslife.galleryviewpager.view.GalleryViewPager>
```

java示例:
```
private void initView() {
    MultiViewPager multiViewPager = (MultiViewPager) findViewById(R.id.banner_layout);
    final Adapter adapter = new Adapter(getSupportFragmentManager());
    multiViewPager.setAdapter(adapter);
    SimpleZoomOutPageTransformer transformer = new SimpleZoomOutPageTransformer();
    multiViewPager.setTransformer(transformer);
    transformer.setScaleValue(0.85f);
    multiViewPager.setPageDistance(10);
}
```

控制方法:

控制缩放的比例,在0~1之间<br/>
`void setScaleValue(float scaleValue)`<br/>
控制两页之间的间距,单位dp<br/>
void setPageDistance(int pageDistance)<br/>

=========

**Drag2DoubleUnfold:**

<img src="https://github.com/WosLovesLife/Libs/blob/master/screenshots/Intro_DragToDoubleUnfold.gif"/>

使用方式:

将drag2doubleunfold的Module导入到项目中,在Project Structure中添加依赖, 然后在布局xml文件中添加 `com.wosloveslife.drag2doubleunfold.Drag2DoubleUnfoldLayout` 控件,
并在子节点写自己布局, **注意: Drag2DoubleUnfoldLayout只能有一个直接的子节点**, 类似ScrollLayout

示例:

```
<com.wosloveslife.drag2doubleunfold.Drag2DoubleUnfoldLayout
    android:id="@+id/id_sdl_spread"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <include layout="@layout/layout_share_board"/>
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

===

----

