package com.iykeafrica.echange.ui.model.request;

import java.util.List;

public class AddUserExtrasRequest {

    private List<TransactionRequestModel> extras;

    public List<TransactionRequestModel> getExtras() {
        return extras;
    }

    public void setExtras(List<TransactionRequestModel> extras) {
        this.extras = extras;
    }
}
