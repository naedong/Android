package kr.main.heydr.view.map;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import kr.main.heydr.R;
import kr.main.heydr.adapter.IntentResource;
import kr.main.heydr.controller.vo.AddressDTO;

public class SearchActivity extends AppCompatActivity {

    private EditText mEtAddress;
    private AddressDTO address;
    private Button btn_address_push;
    private IntentResource intentResource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        btn_address_push = findViewById(R.id.btn_address_push);
        mEtAddress = findViewById(R.id.et_address);
        address = new AddressDTO();

        btn_address_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEtAddress == null){
                    Toast.makeText(getApplicationContext(), "주소를 검색하세요", Toast.LENGTH_SHORT).show();

                }else{
                    Intent intent = new Intent(SearchActivity.this, GPSActivity.class);
                    intent.putExtra("address", mEtAddress.getText().toString());
                    setResult(Activity.RESULT_OK);
                    startActivity(intent);
                    finish();
                }
            }
        });

        mEtAddress.setFocusable(false);
        mEtAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, KaKaoAdresActivity.class);
                getSearchResult.launch(intent);

            }
        });

    }
    private final ActivityResultLauncher<Intent> getSearchResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {

                if(result.getData() != null){
                    String data = result.getData().getStringExtra("data");
                    mEtAddress.setText(data);


                }else{
                    Toast.makeText(getApplicationContext(),"주소를 입력해주세요.",Toast.LENGTH_SHORT).show();
                }

            }
    );
}

