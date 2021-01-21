package com.io2020.PodzielSieKsiazka;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;

import com.io2020.PodzielSieKsiazka.schemas.TransactionStatus;
import org.junit.Test;

import static org.junit.Assert.*;

public class enumLocalizerTest {
    Context context = ApplicationProvider.getApplicationContext();
    @Test
    public void localization_isCorrect() {
        assertEquals(context.getString(R.string.statusAccepted), EnumLocalizer.LocalizeTransactionStatus(TransactionStatus.Accepted, context));
        assertEquals(context.getString(R.string.statusDeclined), EnumLocalizer.LocalizeTransactionStatus(TransactionStatus.Declined, context));
        assertEquals(context.getString(R.string.statusPending), EnumLocalizer.LocalizeTransactionStatus(TransactionStatus.Pending, context));
        assertEquals(context.getString(R.string.statusRented), EnumLocalizer.LocalizeTransactionStatus(TransactionStatus.Rented, context));
        assertEquals(context.getString(R.string.statusFinished), EnumLocalizer.LocalizeTransactionStatus(TransactionStatus.Finished, context));

    }
}