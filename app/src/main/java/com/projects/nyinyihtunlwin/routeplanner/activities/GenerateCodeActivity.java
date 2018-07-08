package com.projects.nyinyihtunlwin.routeplanner.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.projects.nyinyihtunlwin.routeplanner.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GenerateCodeActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn_generate_barcode)
    Button btnGenerateBarCode;

    @BindView(R.id.et_input)
    EditText etInput;

    @BindView(R.id.iv_result_code)
    ImageView ivResultCode;

    @BindView(R.id.sp_encode)
    AppCompatSpinner spEncode;

    @BindView(R.id.sp_width)
    AppCompatSpinner spWidth;

    @BindView(R.id.ll_output_height)
    LinearLayout llOutputHeight;

    private BarcodeFormat mBarcodeFormat;
    private int barcodeHeight;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, GenerateCodeActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_code);
        ButterKnife.bind(this, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        btnGenerateBarCode.setOnClickListener(this);

        spEncode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        mBarcodeFormat = BarcodeFormat.QR_CODE;
                        barcodeHeight = 300;
                        break;
                    case 1:
                        mBarcodeFormat = BarcodeFormat.CODE_39;
                        break;
                    case 2:
                        mBarcodeFormat = BarcodeFormat.CODE_93;
                        break;
                    case 3:
                        mBarcodeFormat = BarcodeFormat.CODE_128;
                        break;
                    case 4:
                        mBarcodeFormat = BarcodeFormat.EAN_8;
                        break;
                    case 5:
                        mBarcodeFormat = BarcodeFormat.EAN_13;
                        break;
                }
                if (mBarcodeFormat == BarcodeFormat.QR_CODE) {
                    llOutputHeight.setVisibility(View.GONE);
                } else {
                    llOutputHeight.setVisibility(View.VISIBLE);
                    barcodeHeight = 50;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spWidth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                barcodeHeight = Integer.parseInt(getResources().getStringArray(R.array.output_height)[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_generate_barcode:

                // hide soft input
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(btnGenerateBarCode.getWindowToken(), 0);

                if (!etInput.getText().toString().equals("")) {
                    String textData = etInput.getText().toString();
                    if (mBarcodeFormat == BarcodeFormat.QR_CODE) {
                        generateBarcode(textData, mBarcodeFormat, barcodeHeight);
                    } else {
                        // for barcode
                        if (textData.matches("[0-9]+")) {
                            switch (mBarcodeFormat) {
                                case EAN_8:
                                    if (textData.length() == 8) {
                                        generateBarcode(textData, mBarcodeFormat, barcodeHeight);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "EAN_8 can generate exactly 8 numbers.", Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                case EAN_13:
                                    if (textData.length() == 13) {
                                        generateBarcode(textData, mBarcodeFormat, barcodeHeight);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "EAN_13 can generate exactly 13 numbers.", Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                default:
                                    generateBarcode(textData, mBarcodeFormat, barcodeHeight);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Barcode can generate only numbers.", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    etInput.setError("Enter text first!");
                }
                break;
        }
    }

    private void generateBarcode(String data, BarcodeFormat barcodeFormat, int imageHeight) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(data, barcodeFormat, 600, imageHeight);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ivResultCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

}
