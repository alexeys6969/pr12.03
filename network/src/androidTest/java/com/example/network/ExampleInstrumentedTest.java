package com.example.network;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.example.network.datas.users.UserLogin;
import com.example.network.domains.callbacks.MyResponseCallback;
import com.example.network.domains.models.User;
/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void UserLogin() throws InterruptedException {
        final Boolean[] Success = {false};
        CountDownLatch Latch = new CountDownLatch(1);
        User User = new User("testing@mail.ru", "Asdfg123*");

        new UserLogin(
                User,
                new MyResponseCallback() {
                    @Override
                    public void onCompile(String result) {
                        Log.d("USER LOGIN", result);
                        Success[0] = true;
                        Latch.countDown();
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("USER LOGIN", error);
                        Latch.countDown();
                    }
                }
        ).execute();
        Boolean Completed = Latch.await(60, TimeUnit.SECONDS);
        assertTrue(Success[0]);
    }
}