package com.zzy.crack_private;

public class SecretService {
    private String encrypt(String data, int key) {
        StringBuilder sb = new StringBuilder();
        for (char c : data.toCharArray()) {
            sb.append((char)(c ^ key));
        }
        return sb.toString();
    }
}
