package com.wosloveslife.libs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.wosloveslife.drag2doubleunfold.Drag2DoubleUnfoldLayout;


public class Drag2DoubleUnfoldActivity extends AppCompatActivity {

    private Drag2DoubleUnfoldLayout mDrag2DoubleUnfoldLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_2_double_unfold);

        mDrag2DoubleUnfoldLayout = (Drag2DoubleUnfoldLayout) findViewById(R.id.id_sdl_spread);
        mDrag2DoubleUnfoldLayout.setEdgeTrackingEnabled(true);
        mDrag2DoubleUnfoldLayout.addOnFormChangeListener(new Drag2DoubleUnfoldLayout.OnFormChangeListener() {
            @Override
            public void onSlide(int form) {
            }
        });

        Button changeForm = (Button) findViewById(R.id.id_btn_change);
        changeForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int spreadState = Drag2DoubleUnfoldLayout.FORM_PART;
                switch (mDrag2DoubleUnfoldLayout.getForm()) {
                    case Drag2DoubleUnfoldLayout.FORM_PART:
                        spreadState = Drag2DoubleUnfoldLayout.FORM_COMP;
                        break;
                    case Drag2DoubleUnfoldLayout.FORM_COMP:
                        spreadState = Drag2DoubleUnfoldLayout.FORM_FOLD;
                        break;
                    case Drag2DoubleUnfoldLayout.FORM_FOLD:
                        spreadState = Drag2DoubleUnfoldLayout.FORM_PART;
                        break;
                }
                mDrag2DoubleUnfoldLayout.controlForm(spreadState);
            }
        });

        CheckBox edgeTouch = (CheckBox) findViewById(R.id.id_cb_edge_enable);
        edgeTouch.setChecked(mDrag2DoubleUnfoldLayout.getEdgeTrackingEnabled());
        edgeTouch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mDrag2DoubleUnfoldLayout.setEdgeTrackingEnabled(isChecked);
            }
        });

        CheckBox mAutoDismiss = (CheckBox) findViewById(R.id.id_cb_auto_dismiss);
        mAutoDismiss.setChecked(mDrag2DoubleUnfoldLayout.getAutoDismissEnable());
        mAutoDismiss.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mDrag2DoubleUnfoldLayout.setAutoDismissEnable(isChecked);
            }
        });

        Button button = (Button) findViewById(R.id.id_btn_toast);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Drag2DoubleUnfoldActivity.this, "test", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mDrag2DoubleUnfoldLayout.controlForm(Drag2DoubleUnfoldLayout.FORM_PART);
    }
}
