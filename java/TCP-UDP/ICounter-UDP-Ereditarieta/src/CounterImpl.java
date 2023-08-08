public class CounterImpl extends CounterSkeleton {
    private int count;

    public void setCount (String id, int s){
        System.out.println(" [CounterImpl]: count set to "+s+" by client "+id);
        count = s;
    }

    public int sum (int s){
        System.out.println(" [CounterImpl]: adding "+s+"to count");
        count = count+s;
        return count;
    }

    public int increment(){
        System.out.println(" [CounterImpl]: adding 1 to count");
        count = count+1;
        return count;
    }
}