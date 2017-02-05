# Libs

**该工程会陆续push一些custom widget以及tool lib**

**更新总览:**

2016.7.13: **LoopViewPager** 通过ViewPager实现自动轮播功能的轮播图控件, 被触摸是暂停自动轮播, 松开继续.

2016.7.13: **BaseRecyclerView** 对RecyclerView.Adapter进行了进一步封装, 可以添加多个Header条目

2016.7.14: **GalleryViewPager** 用ViewPager实现横向照片墙效果(有缩放效果)

===

Actionbar+RecyclerView(ListView)的联动效果系列

2016.7.18: **EnterAlwaysLinkage**   Actionbar跟随移动, 类似scroll|enter_always.

2016.7.20: **Pull2BlurLinkage**   下拉模糊联动效果： 当第一个完全可见的条目是HeaderView(头部的图片)时,继续下拉则放大.

松开或上拉恢复. 当第一个可见条目是HeaderView时,继续下拉逐渐改变Actionbar的背景透明度逐渐可见,上拉则逐渐不可见

===

2016.7.29: **Drag2DoubleUnfold** 控件, 实现二级展开的抽屉效果, 类似Android端知乎的分享或者百度地图的搜索结果页.

2016.8.11: **SimpleFloatingLayout** 控件, 一个简单的流式布局, 子控件按照水平方向排列, 当子控件的宽度超过一行后, 自动换行. 可以用于做热门标签选择, 搜索历史等

----

**展示/使用**


===

**LoopViewPager:**

<img src="https://github.com/WosLovesLife/Libs/blob/master/screenshots/Libs_LoopView.gif"/>

使用方法:

将loopviewpager的Module导入到项目中, 在Project Structure中添加依赖, 然后在布局xml文件中添加 `com.wosloveslife.loopviewpager.view.LoopViewPager` 控件.

在代码中调用LoopViewPager的`void setAdapter(PagerAdapter adapter)`方法, 实现`PagerAdapter`类的抽象方法.

示例:

```
private void init() {
    /* 模拟数据 */
    List<Bitmap> data = DataUtils.getBitmaps(getApplicationContext());

    mLoopViewPager.setAdapter(new MyLoopAdapter(data));
    mLoopViewPager.setDuration(3000);
}

class MyLoopAdapter extends LoopViewPagerAdapter<Bitmap> {
    public MyLoopAdapter(List<Bitmap> data) {
        super(data);
    }

    @Override
    protected View onCreateView(ViewGroup container, int position) {
        final ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageBitmap(mData.get(position));
        return imageView;
    }
}
```

控制方法:

开始轮播, 建议在`onStart()`生命周期中调用<br/>
`void startLoop()`

停止轮播, 建议在`onStop()`生命周期中调用<br/>
`void stopLoop()`

**注意!一定要在适当的生命周期方法中调用stopLoop()方法结束轮播,否则会造成内存泄漏.**

设置轮播的间隔时间(毫秒)<br/>
`void setDuration(int duration)`

===

**BaseRecyclerView:**

<img src="https://github.com/WosLovesLife/Libs/blob/master/screenshots/Libs_baseRecyclerViewAdapter.gif"/>

使用方法:

将baserecyclerview的Module导入到项目中,在Project Structure中添加依赖.

之后在使用RecyclerView的时候, Adapter继承自`com.wosloveslife.baserecyclerview.adapter.BaseRecyclerViewAdapter`.
ViewHolder继承自`com.wosloveslife.baserecyclerview.viewHolder.BaseRecyclerViewHolder`

示例:

```
private void initView() {
    mRecyclerView = (RecyclerView) findViewById(R.id.id_rv_header_recycler_view);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    BaseRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(data);
    mRecyclerView.setAdapter(adapter);

    Button button = new Button(this);
    button.setText("这是一个新的Header ,下面是Header轮播图");
    button.setTextSize(16);
    adapter.addHeader(button);
}
class MyRecyclerViewAdapter extends BaseRecyclerViewAdapter {

    public MyRecyclerViewAdapter(List<String> data) {
        super(data);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateItemViewHolder(ViewGroup parent) {
        Button textView = new Button(parent.getContext());
        ViewGroup.LayoutParams params = parent.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        textView.setLayoutParams(params);
        return new HeaderRecyclerViewHolder(textView);
    }
}
class HeaderRecyclerViewHolder extends BaseRecyclerViewHolder<String> {
    protected TextView mTextView;

    public HeaderRecyclerViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView;
    }

    public void onBind(String s) {
        mTextView.setText(s);
    }
}
```

===

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

