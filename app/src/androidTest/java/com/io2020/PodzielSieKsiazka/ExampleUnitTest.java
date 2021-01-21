package com.io2020.PodzielSieKsiazka;

import android.app.Instrumentation;
import android.content.Context;


import androidx.appcompat.app.AppCompatActivity;
import androidx.test.InstrumentationRegistry;
import androidx.test.core.app.ApplicationProvider;

import com.io2020.PodzielSieKsiazka.schemas.TransactionStatus;
import com.io2020.PodzielSieKsiazka.ui.transactions.TransactionsFragment;

import junit.textui.TestRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    Context context = ApplicationProvider.getApplicationContext();
    @Test
    public void addition_isCorrect() {

        assertEquals(context.getString(R.string.transactionAccepted), EnumLocalizer.LocalizeTransactionStatus(TransactionStatus.Accepted, context));
        assertEquals(context.getString(R.string.transactionDeclined), EnumLocalizer.LocalizeTransactionStatus(TransactionStatus.Declined, context));
        assertEquals(context.getString(R.string.transactionPending), EnumLocalizer.LocalizeTransactionStatus(TransactionStatus.Pending, context));
        assertEquals(context.getString(R.string.transactionRented), EnumLocalizer.LocalizeTransactionStatus(TransactionStatus.Rented, context));
        assertEquals(context.getString(R.string.transactionFinished), EnumLocalizer.LocalizeTransactionStatus(TransactionStatus.Finished, context));
        assertNull(EnumLocalizer.LocalizeTransactionStatus(null, context));
    }
}