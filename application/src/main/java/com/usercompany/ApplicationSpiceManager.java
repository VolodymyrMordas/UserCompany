package com.usercompany;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.SpiceService;

public class ApplicationSpiceManager extends SpiceManager {
    public ApplicationSpiceManager(Class<? extends SpiceService> spiceServiceClass) {
        super(spiceServiceClass);
    }
}