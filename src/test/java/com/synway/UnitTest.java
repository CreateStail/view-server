package com.synway;

import com.synway.utils.Aesssss;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UnitTest {

    @Test
    public void unitTest(){
        String password = Aesssss.encrypt("1234567890",Aesssss.key);
        System.out.println(password);
        String decrypt = Aesssss.decrypt(password,Aesssss.key);
        System.out.println(decrypt);
    }

    @Test
    public void pyTest(){
        try {
            System.out.println("start");
            String[] args1=new String[]{"python","D:\\work\\joggle\\joggle\\post_test.py"};
            Process pr=Runtime.getRuntime().exec(args1);

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            pr.waitFor();
            System.out.println("end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
