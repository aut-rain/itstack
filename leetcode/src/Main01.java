import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * @Author: zzy
 * @Date: 2026-03-14 10:23
 * @Description:
 */
public class Main01 {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(reader.readLine());
        int k = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        long mod = 1000000007;

        long[]nums = new long[1000005];
        long pres = 0;
        for (int i = 1; i <= k; i++) {
            nums[i] = 1;
            pres = pres+nums[i];
        }

        for (int i = k+1; i < 1000005; i++) {
            nums[i] = pres;
            pres = (pres+pres-nums[i-k])%mod;
        }
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < q; i++) {
            int x = Integer.parseInt(reader.readLine());
            sb.append(nums[x]).append("\n");
        }
        System.out.println(sb);
    }
}
