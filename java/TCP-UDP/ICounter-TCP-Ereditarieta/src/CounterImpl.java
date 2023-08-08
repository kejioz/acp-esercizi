public class CounterImpl extends CounterSkeleton {
    private int counter;

    public void setCount(String mess, int s){
        System.out.println("[CounterImpl] Setto il counter al valore richiesto "+s);
        counter = s;
    }

    public int sum (int s){
        System.out.println("[CounterImpl] Addo al counter il valore richiesto ");
        counter = counter +s;
        return counter;
    }

    public int increment (){
        System.out.println("[CounterImpl] Addo al counter 1 ");
        counter = counter +1;
        return counter;
    }

}
