package scijoker.floatpaneltest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.scijoker.popupmanager.PopupManager;

public class MainActivity extends AppCompatActivity {
    private Button btnOk;
    private PopupManager panelManager;
    private PopupManager.ViewHolder viewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOk = (Button) findViewById(R.id.btnOk);

        attachPopupToView();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!viewHolder.isPopupShow()) {
                    viewHolder.showPopup();
                    viewHolder.setProperty(new CustomProperty());
                } else {
                    viewHolder.hidePopup();
                }
            }
        });
    }

    private void attachPopupToView() {
        panelManager = new PopupManager(MainActivity.this);
        //inflate panel view. Can be attach any view
        LinearLayout panelView = (LinearLayout) LayoutInflater.from(MainActivity.this).inflate(R.layout.tooltip_layout, null, false);

        viewHolder = new PopupManager.ViewHolder(panelManager, btnOk, panelView);
    }
}
