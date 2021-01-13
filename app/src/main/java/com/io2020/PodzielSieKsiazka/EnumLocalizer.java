package com.io2020.PodzielSieKsiazka;

import android.content.Context;
import android.content.res.Resources;

import com.io2020.PodzielSieKsiazka.schemas.TransactionStatus;

public class EnumLocalizer{

    public static String LocalizeTransactionStatus(TransactionStatus transactionStatus, Context context){
        Resources res = context.getResources();

        switch (transactionStatus){
            case Pending: return res.getString(R.string.statusPending);
            case Rented: return res.getString(R.string.statusRented);
            case Accepted: return res.getString(R.string.statusAccepted);
            case Declined: return res.getString(R.string.statusDeclined);
            case Finished: return res.getString(R.string.statusFinished);
            default: return null;
        }
    }
}
