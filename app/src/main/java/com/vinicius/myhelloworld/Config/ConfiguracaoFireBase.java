package com.vinicius.myhelloworld.Config;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public final class ConfiguracaoFireBase {
    private static DatabaseReference referenceFireBase;

    public static DatabaseReference getFirebase(){
        if(referenceFireBase == null){
            referenceFireBase = FirebaseDatabase.getInstance().getReference();
        }

        return referenceFireBase;
    }

}
