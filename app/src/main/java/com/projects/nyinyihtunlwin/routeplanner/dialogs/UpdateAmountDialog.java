package com.projects.nyinyihtunlwin.routeplanner.dialogs;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.projects.nyinyihtunlwin.routeplanner.R;
import com.projects.nyinyihtunlwin.routeplanner.utils.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateAmountDialog extends BaseDialog implements View.OnClickListener {

    @BindView(R.id.btn_cancel)
    Button btnCancel;

    @BindView(R.id.btn_update)
    Button btnUpdate;

    @BindView(R.id.tip_amount)
    TextInputLayout tipAmount;

    private UpdateAmountDelegate mUpdateAmountDelegate;

    public UpdateAmountDialog(Context context, UpdateAmountDelegate updateAmountDelegate) {
        super(context);
        this.mUpdateAmountDelegate = updateAmountDelegate;
        initDialog();
    }

    private void initDialog() {
        getWindow();
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_update_amount);
        ButterKnife.bind(this, this);

        btnCancel.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        setCancelable(false);
        show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_update:
                if (tipAmount.getEditText() != null) {
                    String inputAmount = tipAmount.getEditText().getText().toString();
                    if (!inputAmount.equals("") && Double.parseDouble(inputAmount) != 0) {
                        mUpdateAmountDelegate.onUpdateAmount(inputAmount);
                        dismiss();
                    } else {
                        ScreenUtils.getInstance().showToast(getContext(), "Enter amount.");
                    }
                }
                break;
        }
    }

    public interface UpdateAmountDelegate {
        void onUpdateAmount(String amount);
    }
}
