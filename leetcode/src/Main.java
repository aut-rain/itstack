import java.util.Scanner;

/**
 * @Author: zzy
 * @Date: 2026-03-14 10:23
 * @Description:
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int l = sc.nextInt();
        int r = sc.nextInt();
        int ls = (int)Math.sqrt(l-1);
        int rs = (int)Math.sqrt(r);
        System.out.println(rs-ls);
    }
}
