package JVMDemo;

public class FinalizationDemo {

    public static FinalizationDemo active_obj=null;

    public static void islive(){
        System.out.println("----------------is live");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method is execute");
        FinalizationDemo.active_obj=this;
    }

    public static void main(String[] args) throws  Exception{

        active_obj=new FinalizationDemo();

        active_obj=null;
        Thread.sleep(1000);
        System.gc(); //Invoke the Java garbage collector

        if (active_obj!=null){
            islive();
        }else{
            System.out.println("------------is dead");
        }



        active_obj=null;
        System.gc(); //Invoke the Java garbage collector
        Thread.sleep(1000);
        if (active_obj!=null){
            islive();
        }else{
            System.out.println("------------is dead");
        }
    }
}