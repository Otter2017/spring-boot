package org.springframework.boot.peach.test;

import org.springframework.boot.peach.MainRunner;
import org.springframework.boot.peach.PeachApplication;
import org.springframework.boot.peach.message.Message;
import org.springframework.boot.peach.message.MessageHeader;
import org.springframework.boot.peach.message.Segment;
import org.springframework.boot.peach.message.enums.*;
import org.springframework.boot.peach.message.util.SecurityUtil;
import org.springframework.boot.peach.message.util.EnumUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.SpringVersion;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainTest {

    public static void main(String[] args) {
//        testJSon();
//        testFormatDatetime();
//        testCountDownLatch();
//        testCyclicBarrier();
//        testBase64();
//
//        testEnum();
//        testEqual();
//        testVariant();
//        testSplit();

        testMessage();

//        testClassLoad();
//        testSystemCopy();

        testEncrypt();
        testSpringVersion();
    }


    public static void testSpringVersion(){
        System.out.println(SpringVersion.getVersion());
    }

    public static void testReadResource() {

        String currentPackage = PeachApplication.class.getPackage().getName();
        String searchPattern = "classpath*:" + currentPackage.replace('.', '/') + "/**/*.class";
        // searchPattern = classpath*:cn/acrab/peach/**/*.class
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources(searchPattern);
            ClassLoader classLoader = MainRunner.class.getClassLoader();
            for (Resource resource : resources) {

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

//        System.out.println(currentPackage);
    }


    public static void testJSon() {
        Phone phone = new Phone();
        if (!phone.lock.isLocked()) {
            phone.lock.lock();
            phone.setBrand("Apple");
            phone.setPrice(4300.20);
            phone.setSize(6);
            phone.lock.unlock();
        }

        System.out.println(JSONObject.toJSONString(phone));
    }

    private static void testCountDownLatch() {
        CountDownLatch countDown = new CountDownLatch(6);
        ExecutorService service = Executors.newFixedThreadPool(8);
        for (int i = 0; i < 10; i++) {
            CountDownInstance instance = new CountDownInstance(countDown);
            service.submit(instance);
        }
        try {
            countDown.await();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("testCountDown over.");
    }

    private static void testCyclicBarrier() {
        // *设置一个数目为5的等候队列，如果同时阻塞的线程数小于设置的数目，全部线程将不会继续执行
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        ExecutorService service = Executors.newFixedThreadPool(8);
        for (int i = 0; i < 10; i++) {
            CyclicBarrierInstance instance = new CyclicBarrierInstance(cyclicBarrier);
            service.submit(instance);
        }
        service.shutdown();
    }


    public static void testFormatDatetime() {
        final String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        System.out.println(JSONObject.toJSONString(dateFormat));
        Date now = new Date();
        String currentTime = dateFormat.format(now);
        System.out.println(currentTime);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        String dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
        System.out.println(dayOfWeek);
    }


    public static void openServer() {
        Server server = new Server();
    }


    private static void testBase64() {
        try {
            Charset defaultCharset = Charset.defaultCharset();
            System.out.println(defaultCharset.displayName());
            String from = "Hello world.你好";
            String to = Base64.getEncoder().encodeToString(from.getBytes("gb2312"));
            System.out.println(from + " >> " + to);

            from = new String(Base64.getDecoder().decode(to), Charset.forName("GB2312"));
            System.out.println(from);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private static void testEnum() {
        System.out.println("testEnum --------");
        for (MessageType weekday : MessageType.values()) {
            System.out.println(weekday);
        }
        try {
            System.out.println("MessageType[2] = " + EnumUtil.parse(MessageType.class, (byte) 2).toString());
            System.out.println("MessageDiagest[1] = " + EnumUtil.parse(HashType.class, (byte) 1).toString());
            System.out.println(Integer.MIN_VALUE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("testEnum --------");
    }


    private static void testReadInteger() {

        long now = System.currentTimeMillis();

        byte[] bytes = new byte[61];
        Random random = new Random();
        random.nextBytes(bytes);

        byte[] intBytes = Arrays.copyOfRange(bytes, 57, 61);


        System.out.println(new String(intBytes));

        System.out.println(EnumUtil.toByte(MessageType.CommandResponse));

        System.out.println((byte) MessageType.CommandResponse.ordinal());
    }

    private static void testMessage() {
        try {
            String segmentContent = "MSHф♮HELLO|World.|i'm here.";
            MessageHeader header = new MessageHeader();
            header.setDeviceToken("xyz4");
            header.setDeviceUID("JH3LW4nlF35G570D");
            header.setContentHash("JH3LW4nlF35G570DJH3LW4nlF35G570D");
            header.setCreateTimeMillis(System.currentTimeMillis());
            header.setContentLength(segmentContent.getBytes(Charset.forName("UTF-8")).length);
            header.setCharset(MessageCharset.UTF8);
            header.setMessageType(MessageType.ExecuteCommand);
            header.setMessageVersion(MessageVersion.Version10);
            header.setPriority(MessagePriority.Normal);
            header.setHashType(HashType.MD5);
            header.setEncryptMethod(EncryptMethod.NONE);
            String headerJson = JSONObject.toJSONString(header);
            System.out.println(headerJson);

            Message message = new Message();
            message.setHeader(header);

            Segment segment = Segment.parse(segmentContent);
            message.putSegment(segment);

            byte[] messageBytes = message.toBytes();

            Message newMessage = Message.parse(messageBytes);

            if (message.equals(newMessage))
                System.out.println("Test OK.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    static void testEqual() {
        //指向同一地址
        Phone phone = new Phone();
        Phone iphone = phone;
        phone.setBrand("o");
        iphone.setBrand("x");

        System.out.println(phone.getBrand());
        System.out.println(iphone.getBrand());
        if (phone.equals(iphone)) {
            System.out.println("EQUALS");
        }
        if (phone == iphone) {
            System.out.println("==");
        }
        System.out.println("*****");
        // 复制后 指向不同地址
        try {
            iphone = phone.clone();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (phone.equals(iphone)) {
            System.out.println("EQUALS");
        }
        if (phone == iphone) {
            System.out.println("==");
        }

        iphone.setBrand("m");

        System.out.println(phone.getBrand());
        System.out.println(iphone.getBrand());
        if (phone.equals(iphone)) {
            System.out.println("EQUALS");
        }
        if (phone == iphone) {
            System.out.println("==");
        }

        String hello = "hello";
        String hi = "hello";
        hi += "1";
        hello += "1";
        System.out.println(hi);
        System.out.println(hello);
        // equals比较值； == 比较引用指针
        if (hello.equals(hi)) {
            System.out.println("1 EQUALS");
        }
        if (hello == hi) {
            System.out.println("1 ==");
        }

    }


    private static void testVariant() {
        // 传址引用
        Phone phone = new Phone();
        phone.setBrand("apple");
        System.out.println(phone.getBrand());
        setBrand(phone);
        System.out.println(phone.getBrand());

        // 传值，注意变量作用域
        int x = 10;
        System.out.println("x=" + x);
        add(x);
        System.out.println("x=" + x);
    }

    private static void setBrand(Phone phone) {
        Phone temp = phone;
        temp.setBrand("vivo");
    }

    private static void add(int i) {
        i += 1;
    }

    private static void testClassLoad() {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        String classLoaderName = classLoader.getClass().getName();
        System.out.println("ClassLoader:" + classLoaderName);
        classLoader = classLoader.getParent();
        while (classLoader != null) {
            classLoaderName = classLoader.getClass().getName();
            System.out.println("Super loader:" + classLoaderName);
            classLoader = classLoader.getParent();
        }

        Phone phone = new Phone();
        classLoaderName = phone.getClass().getClassLoader().toString();
        System.out.println(classLoaderName);
    }


    public static void testSystemCopy() {
        char[] a = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g'};
        char[] b = new char[]{'1', '2', '3', '4', '5', '6', '7'};
        System.arraycopy(a, 0, b, 2, 4);
        for (char c : b) {
            System.out.println(c);
        }
    }


    private static void testSplit() {
        String str = "hello|world";
        String[] strs = str.split("\\|");
        for (String s : strs) {
            System.out.println(s);
        }
    }


    private static void testEncrypt() {
        String source = "你好,张三( ఠൠఠ )ﾉajld3.";
        String key = "王建峰";
        try {
            String encrypted = SecurityUtil.encrypt(source, key);
            System.out.println("AFTER ENCRYPT : " + encrypted);
            String decrypted = SecurityUtil.decrypt(encrypted, key);
            System.out.println("AFTER DECRYPT : " + decrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
