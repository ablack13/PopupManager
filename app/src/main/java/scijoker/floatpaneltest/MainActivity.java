package scijoker.floatpaneltest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scijoker.popupmanager.PopupManager;

public class MainActivity extends AppCompatActivity {
    private Button btnDefault, btnCustom;
    private PopupManager panelManager;
    private PopupManager.ViewHolder viewHolderDefault, viewHolderCustom;
    private int count;
    private boolean isTimerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDefault = (Button) findViewById(R.id.btn_default);
        btnCustom = (Button) findViewById(R.id.btn_custom);

        attachPopupToView();

        btnDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!viewHolderDefault.isPopupShow()) {
                    viewHolderDefault.showPopup();
                } else {
                    viewHolderDefault.hidePopup();
                }
            }
        });
        btnCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!viewHolderCustom.isPopupShow()) {
                    viewHolderCustom.showPopup();
                    viewHolderCustom.setProperty(new CustomProperty());
                    isTimerRunning = true;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (isTimerRunning) {
                                try {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((TextView) viewHolderCustom.getPopupView().findViewById(R.id.tv_text)).setText("timer: " + count++);
                                        }
                                    });
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();
                } else {
                    isTimerRunning = false;
                    viewHolderCustom.hidePopup();
                }
            }
        });
    }

    private void attachPopupToView() {
        panelManager = new PopupManager(MainActivity.this);
        //inflate panel view. Can be attach any view
        LinearLayout panelView1 = (LinearLayout) LayoutInflater.from(MainActivity.this).inflate(R.layout.tooltip_layout, null, false);
        LinearLayout panelView2 = (LinearLayout) LayoutInflater.from(MainActivity.this).inflate(R.layout.tooltip_layout, null, false);

        viewHolderDefault = new PopupManager.ViewHolder(panelManager, btnDefault, panelView1);
        viewHolderCustom = new PopupManager.ViewHolder(panelManager, btnCustom, panelView2,new CustomProperty());
    }
}
