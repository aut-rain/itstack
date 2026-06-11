package simple;

import com.zzy.simple.SimplePayFactory;
import com.zzy.simple.pay.Ipay;
import com.zzy.simple.pay.impl.WeChatPay;
import org.junit.jupiter.api.Test;

public class SimpleTest {
    @Test
    public void simpleFactoryTest_1() {
        Ipay alipay = SimplePayFactory.create("alipay");
        alipay.pay("orderId", 100.0);
    }

    @Test
    public void simpleFactoryTest_2() {
        Ipay wechat = SimplePayFactory.create(WeChatPay.class);
        wechat.pay("orderId", 100.0);
    }
}
