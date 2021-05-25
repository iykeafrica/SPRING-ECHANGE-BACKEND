package com.iykeafrica.echange.ui.model.request;

import java.util.List;

public class AddUserExtrasRequest {

    private List<ExtrasRequestModel> extras;

    public List<ExtrasRequestModel> getExtras() {
        return extras;
    }

    public void setExtras(List<ExtrasRequestModel> extras) {
        this.extras = extras;
    }
}
