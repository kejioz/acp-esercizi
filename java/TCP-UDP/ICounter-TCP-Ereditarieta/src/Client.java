public class Client {
    public static void main(String[] args) {
        CounterProxy counter1 = new CounterProxy();
        int x=0;
        CounterProxy counter2 = new CounterProxy();
        CounterProxy counter3 = new CounterProxy();

        System.out.println("set count to 10");
        counter1.setCount("user-ACP",10);

        System.out.println("summing 5 to count");
        x=counter2.sum(5);
        System.out.println("current count: "+x);

        System.out.println("adding 1 to counter");
        x=counter3.increment();
        System.out.println("current count: "+x);
    }
    
}
