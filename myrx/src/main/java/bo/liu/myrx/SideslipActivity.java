package bo.liu.myrx;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bo.liu.myrx.frament.LeftMenuFragment;


public class SideslipActivity extends AppCompatActivity {
    private LeftMenuFragment mLeftMenuFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sideslip);
        initView();
    }

    private void initView() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        mLeftMenuFragment = (LeftMenuFragment) supportFragmentManager.findFragmentById(R.id.id_container_menu);
        if (mLeftMenuFragment == null) {
            supportFragmentManager.beginTransaction().add(R.id.id_container_menu, mLeftMenuFragment = new LeftMenuFragment())
                    .commit();
        }
    }
}
