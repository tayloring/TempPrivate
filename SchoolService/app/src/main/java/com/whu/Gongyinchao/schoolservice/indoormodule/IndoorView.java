package com.whu.Gongyinchao.schoolservice.indoormodule;

/**
 * Created by panfei on 16/4/14.
 */
/*public class IndoorView extends Fragment implements IndoorCallBack, DestoryListener{

    private SSPickerView mPicker;
    private IndoorBanner banner1;
    private IndoorBanner banner2;
    private IndoorBanner banner3;
    private IndoorBanner banner4;
    private WeatherView weatherView;
    private IndoorPresenter mPresenter;
    private CloudPoiResult mCurrentCloudPoiResult;

    public IndoorView(Context context) {
       this(context, null);
    }

    public IndoorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.indoor_main, container, false);
        mPicker = ((SSPickerView) v.findViewById(R.id.indoor_picker)).setDrawableLeft(R.drawable.outdoor_search);
        banner1 = ((IndoorBanner) v.findViewById(R.id.banner_class)).setTextView("骚年,,,\n该上课了!").setBtn("Are")
                .addOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(getContext(), IndoorClassActivity.class);
                        getContext().startActivity(intent);
                    }
                });

        banner2 = ((IndoorBanner) v.findViewById(R.id.banner_study)).setTextView("此楼自习室,\n请戳我!").setBtn("You")
                .addOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), IndoorStudyActivity.class);
                        intent.putExtra("poi", mCurrentCloudPoiResult);
                        getContext().startActivity(intent);
                    }
                });

        banner3 = ((IndoorBanner) v.findViewById(R.id.banner_course)).setTextView("多学多得\n看这里!").setBtn("Ready")
                .addOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(getContext(), IndoorCourseActivity.class);
                        getContext().startActivity(intent);
                    }
                });

     /*   //banner4 = ((IndoorBanner) findViewById(R.id.banner_share)).setTextView("讲座信息\n在这里!").setBtn("Go")
                .addOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(getContext(), IndoorShareActivity.class);
                        getContext().startActivity(intent);
                    }
                });*/

      /*  weatherView = (WeatherView) v.findViewById(R.id.indoor_weather);

        mPresenter = new IndoorPresenter(getContext(), this);


        return v;

    }









   private void init() {
        inflate(getContext(), R.layout.indoor_main, this);
        mPicker = ((SSPickerView) findViewById(R.id.indoor_picker)).setDrawableLeft(R.drawable.outdoor_search);
        banner1 = ((IndoorBanner) findViewById(R.id.banner_class)).setTextView("骚年,,,\n该上课了!").setBtn("Are")
                .addOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(getContext(), IndoorClassActivity.class);
                        getContext().startActivity(intent);
                    }
                });

        banner2 = ((IndoorBanner) findViewById(R.id.banner_study)).setTextView("此楼自习室,\n请戳我!").setBtn("You")
                .addOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), IndoorStudyActivity.class);
                        intent.putExtra("poi", mCurrentCloudPoiResult);
                        getContext().startActivity(intent);
                    }
                });

        banner3 = ((IndoorBanner) findViewById(R.id.banner_course)).setTextView("多学多得\n看这里!").setBtn("Ready")
                .addOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(getContext(), IndoorCourseActivity.class);
                        getContext().startActivity(intent);
                    }
                });*/

     /*   //banner4 = ((IndoorBanner) findViewById(R.id.banner_share)).setTextView("讲座信息\n在这里!").setBtn("Go")
                .addOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(getContext(), IndoorShareActivity.class);
                        getContext().startActivity(intent);
                    }
                });*/

       // weatherView = (WeatherView) findViewById(R.id.indoor_weather);

      //  mPresenter = new IndoorPresenter(getContext(), this);
   // }*/

   /* @Override
    public void locating() {
        mPicker.setText("正在定位...");
        mPicker.locating();
    }

    @Override
    public void locateFailed() {
        mPicker.setText("定位失败...");
        mPicker.locateFailed();
    }

    @Override
    public void locateSuccess(BDLocation location) {
        weatherView.request();
        mPresenter.request();
      //  mPicker.locateSuccess();
    }

    @Override
    public Object getRequestTag() {
        return null;
    }

    @Override
    public void indoorSuccess(CloudPoiResult cloudPoiResult) {
        if (cloudPoiResult == null
                || cloudPoiResult.getContents() == null
                || cloudPoiResult.getContents().size() == 0
                || TextUtils.isEmpty(cloudPoiResult.getContents().get(0).getTitle())
                || DistanceUtil.getDistance(BaseApi.getInstance().getCurrentLocLat(),
                        new LatLng(cloudPoiResult.getContents().get(0).getLocation().get(1), cloudPoiResult.getContents().get(0).getLocation().get(0))) > 70) {
            indoorFailed(1001, "请检查您是否在室内");
            mCurrentCloudPoiResult = cloudPoiResult;

            if (mCurrentCloudPoiResult == null) {
                mCurrentCloudPoiResult = new CloudPoiResult();
            }

            if (mCurrentCloudPoiResult.getContents() == null) {
                List<CloudPoiResult.ContentsBean> list = new ArrayList<>();
                mCurrentCloudPoiResult.setContents(list);
            }

            CloudPoiResult.ContentsBean bean = new CloudPoiResult.ContentsBean();
            bean.setTitle("不在室内");
            mCurrentCloudPoiResult.getContents().add(0, bean);
            return;
        }

        mCurrentCloudPoiResult = cloudPoiResult;
        mPicker.setText(cloudPoiResult.getContents().get(0).getTitle());
    }

    @Override
    public void indoorFailed(int errorCode, String message) {
        mCurrentCloudPoiResult = null;
        mPicker.setText("请检查您是否在室内");
    }

    public CloudPoiResult getCurrentCloudPoiResult() {
        return mCurrentCloudPoiResult;
    }

    @Override
    public void onDestory() {
        weatherView.onDestory();
        mPresenter.onDestory();
    }
}*/
