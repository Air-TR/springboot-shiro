package com.tr.springboot.shiro;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;

/**
 * @author TR
 * @date 2022/7/5 下午4:36
 */
public class CreatePassword {

    public static void main(String[] args) {
        String password = "000000";
        String salt = "SALT";
        int hashIterations = 1;

        System.out.println("Sha256Hash: " + sha256Hash(password, salt, hashIterations));
        System.out.println("Md5Hash: " + md5Hash(password, salt, hashIterations));
    }

    private static Sha256Hash sha256Hash(String password, String salt, Integer hashIterations) {
        return new Sha256Hash(password, salt, hashIterations);
    }

    private static Md5Hash md5Hash(String password, String salt, Integer hashIterations) {
        return new Md5Hash(password, salt, hashIterations);
    }

}
