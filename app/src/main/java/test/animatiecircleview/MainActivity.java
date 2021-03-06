package test.animatiecircleview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circleView view = (circleView) findViewById(R.id.circleView);
        List<SimpleInfos> simpleInfos = new ArrayList<>();
        simpleInfos.add(new SimpleInfos(300, Color.BLUE));
        simpleInfos.add(new SimpleInfos(500,Color.YELLOW));
        simpleInfos.add(new SimpleInfos(200,Color.GREEN));
        simpleInfos.add(new SimpleInfos(100,Color.parseColor("#ffd24d")));
        simpleInfos.add(new SimpleInfos(600,Color.parseColor("#66ccff")));
        simpleInfos.add(new SimpleInfos(150,Color.parseColor("#ff66d9")));
        simpleInfos.add(new SimpleInfos(400,Color.RED));
        simpleInfos.add(new SimpleInfos(280,Color.GRAY));
        view.setSimpleInfos(simpleInfos);
    }
}
