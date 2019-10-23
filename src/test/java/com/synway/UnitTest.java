package com.synway;

import com.synway.utils.Aesssss;
import org.junit.Test;

public class UnitTest {

    @Test
    public void unitTest(){
        String password = Aesssss.encrypt("1234567890",Aesssss.key);
        System.out.println(password);
        String decrypt = Aesssss.decrypt(password,Aesssss.key);
        System.out.println(decrypt);
    }

}
